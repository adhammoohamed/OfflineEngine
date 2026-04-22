package com.example.offlineengine.presentation.features.onboarding.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.offlineengine.R
import com.example.offlineengine.presentation.componenets.ScreenWithStatusBar

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {

    val pages = listOf(
        OnboardingPage(
            image = R.drawable.onboarding_1,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ),
        OnboardingPage(
            image = R.drawable.onboarding_2,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ),
        OnboardingPage(
            image = R.drawable.onboarding_3,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size }, initialPage = 0)

// In OnboardingScreen.kt
    ScreenWithStatusBar(
        modifier = modifier,
        statusBarColor = if (pagerState.currentPage == 0) colorResource(R.color.navy) else colorResource(
            R.color.white
        ),
        darkIcons = pagerState.currentPage != 0
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            HorizontalPager(state = pagerState) { page ->
                OnboardingPageItem(page = pages[page])
            }
            OnboardingBottomSection(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage
            )
        }
    }
}

@Composable
fun OnboardingPageItem(page: OnboardingPage) {
    Column(modifier = Modifier.wrapContentSize()) {
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
        )
        Text(
            text = page.title,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(all = 16.dp)
        )
        Text(
            text = page.description,
            modifier = Modifier.padding(16.dp),
            style = TextStyle(color = Color.Gray, fontSize = 16.sp)
        )
    }
}

@Composable
fun OnboardingBottomSection(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    totalDots: Int
) {
    val buttonText = remember(selectedIndex) {
        when {
            selectedIndex == 0 -> "Next"
            selectedIndex <= totalDots - 2 -> "Back"
            else -> "Finish"
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DotsIndicator(totalDots = totalDots, selectedIndex = selectedIndex)
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = colorResource(R.color.navy)
            )
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    Row {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(10.dp)
                    .background(
                        shape = CircleShape,
                        color = if (index == selectedIndex) colorResource(R.color.navy) else Color.Gray
                    )
            )
        }
    }
}

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)