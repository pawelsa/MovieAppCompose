package com.example.movieappcompose.widgets

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.AnimationClockObservable
import androidx.compose.animation.core.TargetAnimation
import androidx.compose.foundation.animation.AndroidFlingDecaySpec
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.gestures.ScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
        screenWidth = ConfigurationAmbient.current.screenWidthDp.dp
    }
    val state = rememberCarouselState()
    state.update(items.size, screenWidth, topYDelta, bottomYDelta)

    val spacingPx = state.spacingPx
    val animatedOffset = state.animatedOffset

    val centers = (0..items.size).map { spacingPx * it }
    val currentPage = remember(selectedPage) { mutableStateOf(selectedPage) }

    Box(
        Modifier.fillMaxSize()
            .scrollable(Orientation.Horizontal, state.scrollableController)
    ) {
        items.forEachIndexed { index, item ->
            val center = spacingPx * index
            Column(
                Modifier
                    .zIndex(1f)
                    .offset(getX = {
                        val indexOfCurrentPage = centers.indexOf(abs(animatedOffset.value))
                        if (indexOfCurrentPage != -1 && indexOfCurrentPage != currentPage.value){
                            currentPage.value = indexOfCurrentPage
                            onPageChanged(currentPage.value)
                        }
                        center + animatedOffset.value
                    }, getY = {
                        val distFromCenter = abs(animatedOffset.value + center) / spacingPx
                        lerp(state.topYDelta, state.bottomYDelta, distFromCenter)
                    }).align(Alignment.TopCenter)
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
        screenWidth = ConfigurationAmbient.current.screenWidthDp.dp
    }
    val state = rememberCarouselState()
    state.update(noItems, screenWidth, topYDelta, bottomYDelta)

    val spacingPx = state.spacingPx
    val animatedOffset = state.animatedOffset

    val centers = (0..noItems).map { spacingPx * it }
    val currentPage = remember(selectedPage) { mutableStateOf(selectedPage) }

    Box(
        Modifier.fillMaxSize()
                .scrollable(Orientation.Horizontal, state.scrollableController)
    ) {
        (0 until noItems).forEach { index ->
            val center = spacingPx * index
            Column(
                Modifier
                        .zIndex(1f)
                        .offset(getX = {
                            val indexOfCurrentPage = centers.indexOf(abs(animatedOffset.value))
                            if (indexOfCurrentPage != -1 && indexOfCurrentPage != currentPage.value){
                                currentPage.value = indexOfCurrentPage
                                onPageChanged(currentPage.value)
                            }
                            center + animatedOffset.value
                        }, getY = {
                            val distFromCenter = abs(animatedOffset.value + center) / spacingPx
                            lerp(state.topYDelta, state.bottomYDelta, distFromCenter)
                        }).align(Alignment.TopCenter)
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
    ): MeasureScope.MeasureResult {
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
    val density = DensityAmbient.current
    val clock = AnimationClockAmbient.current.asDisposableClock()
    val animatedOffset = animatedFloat(0f)

    return remember(clock, density) {
        ViewPagerState(
            density,
            animatedOffset,
            clock,
        )
    }
}

class ViewPagerState(
    private val density: Density,
    val animatedOffset: AnimatedFloat,
    private val clock: AnimationClockObservable
) {
    private val flingConfig = FlingConfig(AndroidFlingDecaySpec(density)) { adjustTarget(it) }
    internal val scrollableController =
        ScrollableController({ consumeScrollDelta(it) }, flingConfig, clock)

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

    private fun adjustTarget(target: Float): TargetAnimation? {
        return TargetAnimation((target / spacingPx).roundToInt() * spacingPx)
    }

    private fun consumeScrollDelta(delta: Float): Float {

        var target = animatedOffset.value + delta
        var consumed = delta
        when {
            target > upperBound -> {
                consumed = upperBound - animatedOffset.value
                target = upperBound
            }
            target < lowerBound -> {
                consumed = lowerBound - animatedOffset.value
                target = lowerBound
            }
        }

        animatedOffset.snapTo(target)
        return consumed
    }
}