package com.example.ttsreader.ui.searchbar

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
) {
    var textValue by remember { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChanged(it)
        },
        label = { Text(text = "TTS search") },
        placeholder = { Text(text = "Locale or country") },
    )

}