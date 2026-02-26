package com.mas.flowlibre.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.data.model.ArtistProfileDto
import com.mas.flowlibre.data.repository.ArtistProfileRepository
import com.mas.flowlibre.data.repository.ArtistProfileRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistProfileViewModel(
    private val repository: ArtistProfileRepository = ArtistProfileRepositoryImpl()
): ViewModel() {
    private val _artistProfile = MutableStateFlow<ArtistProfileDto?>(null)
    val artistProfile: StateFlow<ArtistProfileDto?> = _artistProfile
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    fun loadArtistProfile(artistId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _artistProfile.value = repository.getArtistProfile(artistId)
            }catch (e: Exception){
                println("Error loading artist profile: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}