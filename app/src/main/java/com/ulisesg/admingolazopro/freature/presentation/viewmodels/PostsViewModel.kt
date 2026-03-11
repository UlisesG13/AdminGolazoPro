package com.alilopez.demo.features.jsonplaceholder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import com.alilopez.demo.features.jsonplaceholder.domain.usescases.GetPostsUseCase
import com.alilopez.demo.features.jsonplaceholder.presentation.screens.PostsUIState
import com.alilopez.kt_demohilt.core.hardware.domain.FlashManager
import com.alilopez.kt_demohilt.features.jsonplaceholder.domain.usescases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val flashManager: FlashManager
) : ViewModel() {
    private val _state = MutableStateFlow(PostsUIState())
    val state = _state.asStateFlow()

    init {
        getPosts()
        syncPosts()
    }

    private fun getPosts() {
        postUseCases.getPosts().onEach { posts ->
            _state.value = state.value.copy(
                posts = posts,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun syncPosts() {
        viewModelScope.launch {
            _state.value = state.value.copy(isSyncing = true)
            postUseCases.syncPosts()
            if (flashManager.hasFlash()) {
                flashManager.blink(100)
            }
            _state.value = state.value.copy(isSyncing = false)
        }
    }
}