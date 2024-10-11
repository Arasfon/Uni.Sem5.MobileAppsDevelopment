package com.arasfon.uni.sem5.drivenext.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.Accent
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.common.theme.Secondary
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.OnboardingViewModel

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val viewModel: OnboardingViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    val currentPage by viewModel.currentPage.collectAsState()

    val pagerState = rememberPagerState(initialPage = currentPage) { viewModel.pageCount }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
            when (event) {
                OnboardingViewModel.NavigationEvent.FinishOnboarding -> {
                    onFinish()
                }
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 24.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 24.dp),
                    onClick = { viewModel.onSkipClicked() }
                ) {
                    Text(stringResource(R.string.skip))
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    PagerSection(
                        modifier = Modifier.fillMaxWidth(),
                        pagerState = pagerState
                    )

                    Spacer(
                        modifier = Modifier.height(64.dp)
                    )

                    BottomNavigation(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        currentPage = currentPage,
                        pageCount = viewModel.pageCount,
                        onNextClick = { viewModel.onNextClicked() }
                    )
                }
            }
        }
    }
}

@Composable
fun PagerSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    HorizontalPager(
        pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { page ->
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imagesAspectRatio = 390f / 307f

            val painter = when (page) {
                0 -> painterResource(R.drawable.onboarding1)
                1 -> painterResource(R.drawable.onboarding2)
                2 -> painterResource(R.drawable.onboarding3)
                else -> throw IllegalArgumentException()
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(imagesAspectRatio),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(48.dp)
            )

            val text = when (page) {
                0 -> stringResource(R.string.onboarding_title1) to stringResource(R.string.onboarding_subtitle1)
                1 -> stringResource(R.string.onboarding_title2) to stringResource(R.string.onboarding_subtitle2)
                2 -> stringResource(R.string.onboarding_title3) to stringResource(R.string.onboarding_subtitle3)
                else -> throw IllegalArgumentException()
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = text.first,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(text.second)
            }
        }
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until pageCount) {
                val isSelected = i == currentPage

                val targetWidth = if (isSelected) 36.dp else 16.dp
                val animatedWidth by animateDpAsState(
                    targetValue = targetWidth,
                    animationSpec = tween(durationMillis = 300)
                )

                val targetColor = if (isSelected) Accent else Secondary
                val animatedColor by animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .width(animatedWidth)
                        .height(8.dp)
                        .background(animatedColor, RoundedCornerShape(8.dp))
                        .align(Alignment.CenterVertically)
                )
            }
        }

        DriveNextButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = onNextClick
        ) {
            Text(text = if (currentPage == pageCount - 1) {
                stringResource(R.string.onboarding_finish)
            } else {
                stringResource(R.string.onboarding_continue)
            })
        }
    }
}
