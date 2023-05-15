package com.example.ttsreader.domain

import com.example.ttsreader.data.TtsRepository
import com.example.ttsreader.data.TtsVoice
import javax.inject.Inject

class GetTtsUseCase @Inject constructor(private val ttsRepository: TtsRepository) {

    suspend operator fun invoke(): List<TtsVoice> = ttsRepository.getVoiceList()

}