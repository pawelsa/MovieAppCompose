package com.example.movieappcompose.widgets.pager_temp

import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.roundToInt

private const val LogTag = "PagerState"

/**
 * Creates a [PagerState] that is remembered across compositions.
 *
 * Changes to the provided values for [initialPage] and [initialPageOffset] will **not** result
 * in the state being recreated or changed in any way if it has already been created.
 * Changes to [pageCount] will result in the [PagerState] being updated.
 *
 * @param pageCount the initial value for [PagerState.pageCount]
 * @param initialPage the initial value for [PagerState.currentPage]
 * @param initialPageOffset the initial value for [PagerState.currentPageOffset]
 */
@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from = 0) initialPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
    pageChanged: (Int) -> Unit = { },
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
        pageChanged = pageChanged
    )
}.apply {
    this.pageCount = pageCount
}

/**
 * A state object that can be hoisted to control and observe scrolling for [HorizontalPager].
 *
 * In most cases, this will be created via [rememberPagerState].
 *
 * @param pageCount the initial value for [PagerState.pageCount]
 * @param currentPage the initial value for [PagerState.currentPage]
 * @param currentPageOffset the initial value for [PagerState.currentPageOffset]
 */
@ExperimentalPagerApi
@Stable
class PagerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from = 0) currentPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) currentPageOffset: Float = 0f,
    val pageChanged: (Int) -> Unit = { },
) {
    private var _pageCount by mutableStateOf(pageCount)
    private var _currentPage by mutableStateOf(currentPage)
    private val _currentPageOffset = mutableStateOf(currentPageOffset)
    internal var pageSize by mutableStateOf(0)

    init {
        require(pageCount >= 0) { "pageCount must be >= 0" }
        requireCurrentPage(currentPage, "currentPage")
        requireCurrentPageOffset(currentPageOffset, "currentPageOffset")
    }

    /**
     * The number of pages to display.
     */
    @get:IntRange(from = 0)
    var pageCount: Int
        get() = _pageCount
        set(@IntRange(from = 0) value) {
            require(value >= 0) { "pageCount must be >= 0" }
            _pageCount = value
            currentPage = currentPage.coerceIn(0, lastPageIndex)
        }

    internal val lastPageIndex: Int
        get() = (pageCount - 1).coerceAtLeast(0)

    /**
     * The index of the currently selected page.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:IntRange(from = 0)
    var currentPage: Int
        get() = _currentPage
        private set(value) {
            _currentPage = value.coerceIn(0, lastPageIndex)
            pageChanged(_currentPage)
        }

    /**
     * The current offset from the start of [currentPage], as a fraction of the page width.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:FloatRange(from = 0.0, to = 1.0)
    var currentPageOffset: Float
        get() = _currentPageOffset.value
        private set(value) {
            _currentPageOffset.value = value.coerceIn(
                minimumValue = 0f,
                maximumValue = if (currentPage == lastPageIndex) 0f else 1f,
            )
        }

    /**
     * Animate (smooth scroll) to the given page.
     *
     * Cancels the currently running scroll, if any, and suspends until the cancellation is
     * complete.
     *
     * @param page the page to snap to. Must be between 0 and [pageCount] (inclusive).
     * @param pageOffset the percentage of the page width to offset, from the start of [page]
     * @param initialVelocity Initial velocity in pixels per second, or `0f` to not use a start velocity.
     * Must be in the range 0f..1f.
     */
    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
        initialVelocity: Float = 0f,
    ) {
        requireCurrentPage(page, "page")
        requireCurrentPageOffset(pageOffset, "pageOffset")

        if (page == currentPage) return

        // We don't specifically use the DragScope's dragBy, but
        // we do want to use it's mutex
        draggableState.drag {
            animateToPage(
                page = page.coerceIn(0, lastPageIndex),
                pageOffset = pageOffset.coerceIn(0f, 1f),
                initialVelocity = initialVelocity,
            )
        }
    }

    /**
     * Instantly brings the item at [page] to the middle of the viewport, offset by [pageOffset]
     * percentage of page width.
     *
     * Cancels the currently running scroll, if any, and suspends until the cancellation is
     * complete.
     *
     * @param page the page to snap to. Must be between 0 and [pageCount] (inclusive).
     * @param pageOffset the percentage of the page width to offset, from the start of [page].
     * Must be in the range 0f..1f.
     */
    suspend fun scrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        requireCurrentPage(page, "page")
        requireCurrentPageOffset(pageOffset, "pageOffset")

        // We don't specifically use the DragScope's dragBy, but
        // we do want to use it's mutex
        draggableState.drag {
            currentPage = page
            currentPageOffset = pageOffset
        }
    }

    private fun snapToNearestPage() {
        if (DebugLog) {
            Log.d(LogTag, "snapToNearestPage. currentPage:$currentPage, offset:$currentPageOffset")
        }
        currentPage += currentPageOffset.roundToInt()
        currentPageOffset = 0f
    }

    private suspend fun animateToPage(
        page: Int,
        pageOffset: Float = 0f,
        animationSpec: AnimationSpec<Float> = spring(),
        initialVelocity: Float = 0f,
    ) {
        animate(
            initialValue = currentPage + currentPageOffset,
            targetValue = page + pageOffset,
            initialVelocity = initialVelocity,
            animationSpec = animationSpec
        ) { value, _ ->
            currentPage = floor(value).toInt()
            currentPageOffset = value - currentPage
        }
        snapToNearestPage()
    }

    private fun determineSpringBackOffset(
        velocity: Float,
        offset: Float = currentPageOffset,
    ): Float = when {
        // If the offset exceeds the scroll threshold (in either direction), we want to
        // move to the next/previous item
        offset < ScrollThreshold -> 0f
        offset > 1 - ScrollThreshold -> 1f
        // Otherwise we look at the velocity for scroll direction
        velocity < 0 -> 1f
        else -> 0f
    }

    internal val draggableState = DraggableState { delta ->
        dragByOffset(delta / pageSize.coerceAtLeast(1))
    }

    private fun dragByOffset(deltaOffset: Float) {
        val targetedOffset = currentPageOffset - deltaOffset

        if (targetedOffset < 0) {
            // If the target offset is < 0, we're trying to cross the boundary to the previous page
            if (currentPage > 0) {
                // We can only move to the previous page if we're not at page 0
                currentPage--
                currentPageOffset = targetedOffset + 1
            } else {
                // If we're at page 0, pin to 0f offset
                currentPageOffset = 0f
            }
        } else if (targetedOffset >= 1) {
            // If the target offset is > 1, we're trying to cross the boundary to the next page
            if (currentPage < pageCount - 1) {
                // We can only move to the next page if we're not on the last page
                currentPage++
                currentPageOffset = targetedOffset - 1
            } else {
                // If we're on the last page, pin to 0f offset
                currentPageOffset = 0f
            }
        } else {
            // Otherwise, we can use the offset as-is
            currentPageOffset = targetedOffset
        }

        if (DebugLog) {
            Log.d(
                LogTag,
                "dragByOffset. delta:%.4f, targetOffset:%.4f, new-page:%d, new-offset:%.4f"
                        .format(deltaOffset, targetedOffset, currentPage, currentPageOffset),
            )
        }
    }

    /**
     * TODO make this public?
     */
    internal suspend fun performFling(
        initialVelocity: Float,
        animationSpec: DecayAnimationSpec<Float>,
    ) = draggableState.drag {
        // We calculate the target offset using pixels, rather than using the offset
        val targetOffset = animationSpec.calculateTargetValue(
            initialValue = currentPageOffset * pageSize,
            initialVelocity = initialVelocity * -1
        ) / pageSize

        if (DebugLog) {
            Log.d(
                LogTag,
                "fling. velocity:%.4f, page: %d, offset:%.4f, targetOffset:%.4f"
                        .format(initialVelocity, currentPage, currentPageOffset, targetOffset)
            )
        }

        // If the animation can naturally end outside of current page bounds, we will
        // animate with decay.
        if (targetOffset.absoluteValue >= 1) {
            // Animate with the decay animation spec using the fling velocity

            val targetPage = when {
                targetOffset > 0 -> {
                    (currentPage + 1).coerceAtMost(lastPageIndex)
                }
                else -> currentPage
            }

            AnimationState(
                initialValue = currentPageOffset * pageSize,
                initialVelocity = initialVelocity * -1
            ).animateDecay(animationSpec) {
                if (DebugLog) {
                    Log.d(
                        LogTag,
                        "fling. decay. value:%.4f, page: %d, offset:%.4f"
                                .format(value, currentPage, currentPageOffset)
                    )
                }

                val coerced = value.coerceIn(0f, pageSize.toFloat())
                dragBy((currentPageOffset * pageSize) - coerced)

                val pastLeftBound = initialVelocity > 0 &&
                        (currentPage < targetPage || (currentPage == targetPage && currentPageOffset == 0f))

                val pastRightBound = initialVelocity < 0 &&
                        (currentPage > targetPage || (currentPage == targetPage && currentPageOffset > 0f))

                if (pastLeftBound || pastRightBound) {
                    // If we reach the bounds of the allowed offset, cancel the animation
                    cancelAnimation()

                    currentPage = targetPage
                    currentPageOffset = 0f
                }
            }
        } else {
            // Otherwise we animate to the next item, or spring-back depending on the offset
            animate(
                initialValue = currentPageOffset * pageSize,
                targetValue = pageSize * determineSpringBackOffset(
                    velocity = initialVelocity * -1,
                    offset = targetOffset
                ),
                initialVelocity = initialVelocity,
                animationSpec = spring()
            ) { value, _ ->
                dragBy((currentPageOffset * pageSize) - value)
            }
        }

        snapToNearestPage()
    }

    override fun toString(): String = "PagerState(" +
            "pageCount=$pageCount, " +
            "currentPage=$currentPage, " +
            "currentPageOffset=$currentPageOffset" +
            ")"

    private fun requireCurrentPage(value: Int, name: String) {
        if (pageCount == 0) {
            require(value == 0) { "$name must be 0 when pageCount is 0" }
        } else {
            require(value in 0 until pageCount) {
                "$name must be >= 0 and < pageCount"
            }
        }
    }

    private fun requireCurrentPageOffset(value: Float, name: String) {
        if (pageCount == 0) {
            require(value == 0f) { "$name must be 0f when pageCount is 0" }
        } else {
            require(value in 0f..1f) { "$name must be >= 0 and <= 1" }
        }
    }

    companion object {
        /**
         * The default [Saver] implementation for [PagerState].
         */
        val Saver: Saver<PagerState, *> = listSaver(
            save = { listOf<Any>(it.pageCount, it.currentPage, it.currentPageOffset) },
            restore = {
                PagerState(
                    pageCount = it[0] as Int,
                    currentPage = it[1] as Int,
                    currentPageOffset = it[2] as Float
                )
            }
        )
    }
}