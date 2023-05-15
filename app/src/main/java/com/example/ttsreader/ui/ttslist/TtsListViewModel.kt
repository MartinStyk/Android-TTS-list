package com.example.ttsreader.ui.ttslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ttsreader.data.TtsVoice
import com.example.ttsreader.domain.GetTtsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TtsListViewModel @Inject constructor(
    private val getTtsUseCase: GetTtsUseCase,
) : ViewModel() {

    private lateinit var allTts: List<TtsVoice>

    private val _state = MutableStateFlow(TtsListState(emptyList()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            allTts = runCatching { getTtsUseCase() }.getOrElse { emptyList() }
                .sortedBy { it.locale.country }
            _state.value = TtsListState(ttsVoices = allTts)
        }
    }

    fun filterTts(query: String) {
        val filtered = allTts.filter {
            query.isBlank() ||
                it.voiceName.contains(query) ||
                it.locale.country.contains(query) ||
                it.locale.language.contains(query) ||
                it.locale.isO3Country.contains(query)
        }
        _state.value = TtsListState(ttsVoices = filtered)
    }

}