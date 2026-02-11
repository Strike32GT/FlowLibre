package com.mas.flowlibre.presentation.viewModel

sealed class AddToLibraryState {
    object Loading: AddToLibraryState()
    data class Success(val message: String) : AddToLibraryState()
    data class Error(val message: String) : AddToLibraryState()
}