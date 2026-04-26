package com.example.offlineengine.presentation.features.select_country.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectYourCountryViewModel @Inject constructor() : ViewModel(){
    val countries = listOf(
        Country("ae", "United Arab Emirates", "🇦🇪"),
        Country("ar", "Argentina", "🇦🇷"),
        Country("at", "Austria", "🇦🇹"),
        Country("au", "Australia", "🇦🇺"),
        Country("be", "Belgium", "🇧🇪"),
        Country("bg", "Bulgaria", "🇧🇬"),
        Country("br", "Brazil", "🇧🇷"),
        Country("ca", "Canada", "🇨🇦"),
        Country("ch", "Switzerland", "🇨🇭"),
        Country("cn", "China", "🇨🇳"),
        Country("co", "Colombia", "🇨🇴"),
        Country("cu", "Cuba", "🇨🇺"),
        Country("cz", "Czech Republic", "🇨🇿"),
        Country("de", "Germany", "🇩🇪"),
        Country("eg", "Egypt", "🇪🇬"),
        Country("fr", "France", "🇫🇷"),
        Country("gb", "United Kingdom", "🇬🇧"),
        Country("gr", "Greece", "🇬🇷"),
        Country("hk", "Hong Kong", "🇭🇰"),
        Country("hu", "Hungary", "🇭🇺"),
        Country("id", "Indonesia", "🇮🇩"),
        Country("ie", "Ireland", "🇮🇪"),
        Country("il", "Israel", "🇮🇱"),
        Country("in", "India", "🇮🇳"),
        Country("it", "Italy", "🇮🇹"),
        Country("jp", "Japan", "🇯🇵"),
        Country("kr", "South Korea", "🇰🇷"),
        Country("lt", "Lithuania", "🇱🇹"),
        Country("lv", "Latvia", "🇱🇻"),
        Country("ma", "Morocco", "🇲🇦"),
        Country("mx", "Mexico", "🇲🇽"),
        Country("my", "Malaysia", "🇲🇾"),
        Country("ng", "Nigeria", "🇳🇬"),
        Country("nl", "Netherlands", "🇳🇱"),
        Country("no", "Norway", "🇳🇴"),
        Country("nz", "New Zealand", "🇳🇿"),
        Country("ph", "Philippines", "🇵🇭"),
        Country("pl", "Poland", "🇵🇱"),
        Country("pt", "Portugal", "🇵🇹"),
        Country("ro", "Romania", "🇷🇴"),
        Country("rs", "Serbia", "🇷🇸"),
        Country("ru", "Russia", "🇷🇺"),
        Country("sa", "Saudi Arabia", "🇸🇦"),
        Country("se", "Sweden", "🇸🇪"),
        Country("sg", "Singapore", "🇸🇬"),
        Country("si", "Slovenia", "🇸🇮"),
        Country("sk", "Slovakia", "🇸🇰"),
        Country("th", "Thailand", "🇹🇭"),
        Country("tr", "Turkey", "🇹🇷"),
        Country("tw", "Taiwan", "🇹🇼"),
        Country("ua", "Ukraine", "🇺🇦"),
        Country("us", "United States", "🇺🇸"),
        Country("ve", "Venezuela", "🇻🇪"),
        Country("za", "South Africa", "🇿🇦")
    )

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _selectedCountryCode = MutableStateFlow<String?>(null)
    val selectedCountryCode = _selectedCountryCode.asStateFlow()


    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun selectCountryCode(code: String) {
        _selectedCountryCode.value = code
    }
}

data class Country(
    val code: String,
    val name: String,
    val flag: String
)

