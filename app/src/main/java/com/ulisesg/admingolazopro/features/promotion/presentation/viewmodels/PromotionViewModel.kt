package com.ulisesg.admingolazopro.features.promotion.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion
import com.ulisesg.admingolazopro.features.promotion.domain.usecases.*
import com.ulisesg.admingolazopro.features.promotion.presentation.screens.PromotionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val createPromotionUseCase: CreatePromotionUseCase,
    private val updatePromotionUseCase: UpdatePromotionUseCase,
    private val deletePromotionUseCase: DeletePromotionUseCase,
    private val activatePromotionUseCase: ActivatePromotionUseCase,
    private val deactivatePromotionUseCase: DeactivatePromotionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PromotionUiState())
    val state: StateFlow<PromotionUiState> = _state.asStateFlow()

    init {
        loadPromotions()
    }

    fun loadPromotions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getPromotionsUseCase()
                .onSuccess { promotions ->
                    _state.update { it.copy(isLoading = false, promotions = promotions) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar promociones") }
                }
        }
    }

    fun createPromotion(promotion: Promotion) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            createPromotionUseCase(promotion)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                    loadPromotions()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun updatePromotion(id: Int, promotion: Promotion) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            updatePromotionUseCase(id, promotion)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                    loadPromotions()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun deletePromotion(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            deletePromotionUseCase(id)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    loadPromotions()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun togglePromotionStatus(id: Int, currentlyActive: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val result = if (currentlyActive) deactivatePromotionUseCase(id) else activatePromotionUseCase(id)
            result
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    loadPromotions()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}

