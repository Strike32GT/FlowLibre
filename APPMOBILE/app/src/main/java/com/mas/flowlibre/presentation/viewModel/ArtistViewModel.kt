package com.mas.flowlibre.presentation.viewModel



import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope

import com.mas.flowlibre.data.repository.ArtistRepositoryImpl

import com.mas.flowlibre.domain.model.Artist

import com.mas.flowlibre.domain.repository.ArtistRepository

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce

import kotlinx.coroutines.launch

import java.text.DecimalFormat



class ArtistViewModel(

    private val artistRepository: ArtistRepository = ArtistRepositoryImpl()

) : ViewModel() {



    private val _searchQuery = MutableStateFlow("")

    val searchQuery : StateFlow<String> = _searchQuery


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _artists = MutableStateFlow<List<Artist>>(emptyList())

    val artists: StateFlow<List<Artist>> = _artists



    fun onQueryChange(query: String) {

        _searchQuery.value = query

    }





    init {
        observeSearchQuery()
    }



    fun loadPopularArtists() {

        viewModelScope.launch {

            try {

                val popularArtists = artistRepository.getPopularArtists()

                _artists.value = popularArtists.map { dto ->

                    Artist(
                        id = dto.id,
                        name = dto.name,
                        verified = false,
                        description = "",
                        followers = 0,
                        profileImageUrl = dto.image_url ?: ""

                    )

                }

            } catch (e: Exception) {

                println("Error loading popular artists: ${e.message}")

            }

        }

    }



    fun searchArtists() {

        viewModelScope.launch {

            if (searchQuery.value.isBlank()) {

                _artists.value = emptyList()
                return@launch

            }



            try {

                val searchResult = artistRepository.searchArtists(searchQuery.value)

                _artists.value = searchResult.map { dto ->

                    Artist(
                        id = dto.id,
                        name = dto.name,
                        verified = false,
                        description = "",
                        followers = 0,
                        profileImageUrl = dto.image_url ?: ""
                    )

                }

            } catch (e: Exception) {

                println("Error searching artists: ${e.message}")

                _artists.value = emptyList()

            }

        }

    }


    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300L)
                .collect { query ->
                    if (query.isBlank()) {
                        _artists.value = emptyList()
                    } else {
                        searchArtists()
                    }
                }
        }
    }

}



fun formatFollowers(followers: Long): String {

    return when {

        followers >= 1_000_000 -> {

            val millions = followers / 1_000_000.0

            val decimalFormat = DecimalFormat("#.#M")

            decimalFormat.format(millions)

        }

        followers >= 1_000 -> {

            val thousands = followers / 1_000.0

            val decimalFormat = DecimalFormat("#.#K")

            decimalFormat.format(thousands)

        }

        else -> followers.toString()

    }

}

