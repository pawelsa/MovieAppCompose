package com.example.movieappcompose.widgets

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun <T> ViewPager(
    items: List<T>,
    width: Dp = 0.dp,
    topYDelta: Float = 0f,
    bottomYDelta: Float = 0f,
    selectedPage: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    pagerContent: @Composable (item: T, itemWidthInDp: Dp) -> Unit
) {

    var screenWidth = width
    if (screenWidth == 0.dp) {
        screenWidth = LocalConfiguration.current.screenWidthDp.dp
    }
    val state = rememberCarouselState()
    state.update(items.size, screenWidth, topYDelta, bottomYDelta)

    val spacingPx = state.spacingPx
//    val animatedOffset = state.animatedOffset

    val centers = (0..items.size).map { spacingPx * it }
    val currentPage = remember(selectedPage) { mutableStateOf(selectedPage) }

    Box(
        Modifier
                .fillMaxSize()
                .scrollable(orientation = Orientation.Horizontal,
                    state = state.scrollableController)
    ) {
        items.forEachIndexed { index, item ->
            val center = spacingPx * index
            Column(
                Modifier
                        .zIndex(1f)
                        .offset(getX = {
                            val indexOfCurrentPage = 1//centers.indexOf(abs(animatedOffset.value))
                            if (indexOfCurrentPage != -1 && indexOfCurrentPage != currentPage.value) {
                                currentPage.value = indexOfCurrentPage
                                onPageChanged(currentPage.value)
                            }
//                        center + animatedOffset.value
                            center
                        }, getY = {
                            val distFromCenter = abs(/*animatedOffset.value + */center) / spacingPx
                            lerp(state.topYDelta, state.bottomYDelta, distFromCenter)
                        })
                        .align(Alignment.TopCenter)
            ) {
                pagerContent(item, state.initWithInDp)
            }
        }
    }
}

@Composable
fun ViewPager(
    noItems: Int,
    width: Dp = 0.dp,
    topYDelta: Float = 0f,
    bottomYDelta: Float = 0f,
    selectedPage: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    pagerContent: @Composable (index: Int, itemWidthInDp: Dp) -> Unit
) {
    var screenWidth = width
    if (screenWidth == 0.dp) {
        screenWidth = LocalConfiguration.current.screenWidthDp.dp
    }
    val state = rememberCarouselState()
    state.update(noItems, screenWidth, topYDelta, bottomYDelta)

    val spacingPx = state.spacingPx
//    val animatedOffset = state.animatedOffset

    val centers = (0..noItems).map { spacingPx * it }
    val currentPage = remember(selectedPage) { mutableStateOf(selectedPage) }

    Box(
        Modifier
                .fillMaxSize()
                .scrollable(orientation = Orientation.Horizontal,
                    state = state.scrollableController)
    ) {
        (0 until noItems).forEach { index ->
            val center = spacingPx * index
            Column(
                Modifier
                        .zIndex(1f)
                        .offset(getX = {
                            val indexOfCurrentPage = 1//centers.indexOf(abs(animatedOffset.value))
                            if (indexOfCurrentPage != -1 && indexOfCurrentPage != currentPage.value) {
                                currentPage.value = indexOfCurrentPage
                                onPageChanged(currentPage.value)
                            }
//                            center + animatedOffset.value
                            center
                        }, getY = {
                            val distFromCenter = abs(/*animatedOffset.value +*/ center) / spacingPx
                            lerp(state.topYDelta, state.bottomYDelta, distFromCenter)
                        })
                        .align(Alignment.TopCenter)
            ) {
                pagerContent(index, state.initWithInDp)
            }
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

fun Modifier.offset(
    getX: () -> Float,
    getY: () -> Float,
    rtlAware: Boolean = true
) = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            if (rtlAware) {
                placeable.placeRelative(getX().roundToInt(), getY().roundToInt())
            } else {
                placeable.place(getX().roundToInt(), getY().roundToInt())
            }
        }
    }
}

@Composable
fun rememberCarouselState(): ViewPagerState {
    val density = LocalDensity.current
//    val clock = AnimationClockAmbient.current.asDisposableClock()
    val animatedOffset = androidx.compose.animation.core.Animatable(0f)

    return remember(density) {
        ViewPagerState(
            density,
//            animatedOffset,
//            clock,
        )
    }
}

class ViewPagerState(
    private val density: Density,
//    val animatedOffset: Animatable,
) {
    internal val scrollableController =
        ScrollableState { consumeScrollDelta(it) }

    private var itemCount: Int = 0
    var spacingPx: Float = 0f

    var initWithInDp: Dp = 100.dp
    var topYDelta: Float = 0f
    var bottomYDelta: Float = 0f

    internal fun update(count: Int, spacing: Dp, top: Float, bottom: Float) {
        itemCount = count
        initWithInDp = spacing
        spacingPx = with(density) { spacing.toPx() }
        topYDelta = top
        bottomYDelta = bottom
    }

    private val upperBound: Float = 0f
    private val lowerBound: Float get() = -1 * (itemCount - 1) * spacingPx

//    private fun adjustTarget(target: Float): TargetAnimation? {
//        return TargetAnimation((target / spacingPx).roundToInt() * spacingPx)
//    }

    private fun consumeScrollDelta(delta: Float): Float {

        var target = /*animatedOffset.value +*/ delta
        var consumed = delta
        when {
            target > upperBound -> {
                consumed = upperBound// - animatedOffset.value
                target = upperBound
            }
            target < lowerBound -> {
                consumed = lowerBound// - animatedOffset.value
                target = lowerBound
            }
        }

//        animatedOffset.snapTo(target)
        return consumed
    }
}