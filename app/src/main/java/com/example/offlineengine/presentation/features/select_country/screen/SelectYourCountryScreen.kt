package com.example.offlineengine.presentation.features.select_country.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.offlineengine.R
import com.example.offlineengine.presentation.componenets.ScreenWithStatusBar
import com.example.offlineengine.presentation.features.select_country.viewmodel.SelectYourCountryViewModel

@Composable
fun SelectYourCountryScreen(modifier: Modifier = Modifier, onNext: () -> Unit) {

    val viewModel: SelectYourCountryViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val selectedCountryCode by viewModel.selectedCountryCode.collectAsState()
    val countries by viewModel.filteredCountries.collectAsState()

    ScreenWithStatusBar(
        modifier = modifier, statusBarColor = Color.White, darkIcons = true
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 150.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null
                    )
                    Text(
                        text = "Select your County",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = searchText,
                    label = {
                        Text("Search")
                    },
                    suffix = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                    },
                    onValueChange = { text -> viewModel.updateSearchText(text) },
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(countries) { country ->
                        val isSelected = country.code == selectedCountryCode
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
//                                    viewModel.updateSearchText(country.name)
                                    viewModel.selectCountryCode(country.code)
                                }, colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) colorResource(R.color.teal_200) else Color.White
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                Text(text = country.flag)
                                Text(
                                    text = country.name,
                                    style = TextStyle(color = if (isSelected) Color.White else Color.Black)
                                )
                            }
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
                            containerColor = colorResource(R.color.teal_200)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            viewModel.saveUserCountry()
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

@Preview(showBackground = true)
@Composable
fun SelectYourCountryScreenPreview() {
    SelectYourCountryScreen {

    }
}