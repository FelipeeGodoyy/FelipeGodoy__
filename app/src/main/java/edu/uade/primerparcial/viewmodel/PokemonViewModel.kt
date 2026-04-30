package edu.uade.primerparcial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uade.primerparcial.data.model.Pokemon
import edu.uade.primerparcial.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _allPokemons = MutableStateFlow<List<Pokemon>>(emptyList())

    val pokemons: StateFlow<List<Pokemon>> = combine(_allPokemons, _searchQuery) { list, query ->
        if (query.isBlank()) {
            list
        } else {
            list.filter { 
                it.name.contains(query, ignoreCase = true) || it.id.toString() == query
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        _allPokemons.value = repository.getPokemons()
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
