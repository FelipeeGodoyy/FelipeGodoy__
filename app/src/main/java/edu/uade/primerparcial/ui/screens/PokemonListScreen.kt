package edu.uade.primerparcial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.uade.primerparcial.data.model.Pokemon
import edu.uade.primerparcial.data.repository.PokemonRepository
import edu.uade.primerparcial.ui.components.PokemonItem
import edu.uade.primerparcial.viewmodel.PokemonViewModel

@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel = viewModel {
        PokemonViewModel(PokemonRepository())
    }
) {
    val pokemons by viewModel.pokemons.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    PokemonListContent(
        pokemons = pokemons,
        searchQuery = searchQuery,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListContent(
    pokemons: List<Pokemon>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pokédex",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar Pokémon por nombre o ID...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(
                    items = pokemons,
                    key = { it.id }
                ) { pokemon ->
                    PokemonItem(pokemon = pokemon)
                }
            }
        }
    }
}
