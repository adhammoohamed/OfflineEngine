package com.example.offlineengine.presentation.features.select_category.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.offlineengine.presentation.componenets.ScreenWithStatusBar
import com.example.offlineengine.R
import com.example.offlineengine.presentation.features.select_category.viewmodel.SelectCategoryViewModel

@Composable
fun SelectCategory(
    modifier: Modifier = Modifier,
    viewModel: SelectCategoryViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {

    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    ScreenWithStatusBar(
        statusBarColor = Color.White,
        darkIcons = true
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                    Box(modifier = modifier.weight(1f)) {
                        Text(
                            text = "Select Category",
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                FlowRow {
                    repeat(viewModel.categories.size) {
                        val isSelected = viewModel.categories[it] == selectedCategory
                        val category = viewModel.categories[it]
                        Card(
                            modifier = Modifier
                                .padding(8.dp).clickable {
                                    viewModel.selectCategory(category)
                                },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) colorResource(R.color.primary) else Color.White,
                                contentColor = if (isSelected) Color.White else colorResource(R.color.primary),
                            ),
                            border = BorderStroke(
                                color = colorResource(R.color.primary),
                                width = 1.dp
                            )

                        ) {
                            Text(category, modifier = Modifier.padding(16.dp))

                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedButton(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primary)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            viewModel.saveUserCategory()
                            onNext()
                        }
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}