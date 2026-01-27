package com.mas.flowlibre.presentation.viewModel



fun formatFollowers(followers: Long): String {
    return when {
        followers >= 1_000_000 -> "${followers / 1_000_000}M"
        followers >= 1_000 -> "${followers / 1_000}K"
        else -> followers.toString()
    }
}