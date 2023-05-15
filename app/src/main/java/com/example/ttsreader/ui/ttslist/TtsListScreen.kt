package com.example.ttsreader.ui.ttslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ttsreader.data.TtsVoice
import com.example.ttsreader.ui.searchbar.SearchBar
import com.example.ttsreader.ui.theme.TtsReaderTheme
import java.util.*

@Composable
fun TtsListScreen() {
    val viewModel: TtsListViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    TtsListScreen(
        ttsListState = state,
        onSearchQueryChange = { viewModel.filterTts(it) }
    )
}

@Composable
private fun TtsListScreen(
    ttsListState: TtsListState,
    onSearchQueryChange: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                onValueChanged = onSearchQueryChange
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(ttsListState.ttsVoices) { voice ->
                Column(
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = voice.voiceName,
                        style = MaterialTheme.typography.h5
                    )

                    Row {
                        Text(
                            modifier = Modifier.padding(end = 6.dp),
                            text = voice.locale.displayCountry,
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            modifier = Modifier.align(Alignment.Bottom),
                            text = "Locale: ${voice.locale}",
                            style = MaterialTheme.typography.body2,
                        )
                    }

                    Row {
                        Text(
                            modifier = Modifier.padding(end = 6.dp),
                            text = voice.locale.displayCountry,
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            modifier = Modifier.align(Alignment.Bottom),
                            text = "Normalized locale: ${voice.locale} ",
                            style = MaterialTheme.typography.body2,
                        )
                    }

                    Text(
                        text = "Network connection required: ${voice.isNetworkConnectionRequired} ",
                        style = MaterialTheme.typography.body2
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TtsListScreenPreview() {
    TtsReaderTheme {
        TtsListScreen(
            TtsListState(
                ttsVoices = listOf(
                    TtsVoice(
                        voiceName = "My voice",
                        locale = Locale.CANADA,
                        normalizedLocale = Locale.CHINA,
                        isNetworkConnectionRequired = true,
                    ),
                    TtsVoice(
                        voiceName = "My voice2",
                        locale = Locale.CANADA,
                        normalizedLocale = Locale.CHINA,
                        isNetworkConnectionRequired = true,
                    )
                )
            ),
            onSearchQueryChange = {}
        )
    }
}
