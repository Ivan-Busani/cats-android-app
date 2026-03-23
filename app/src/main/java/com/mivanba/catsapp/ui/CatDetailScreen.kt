package com.mivanba.catsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: CatDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Gato") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is CatDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CatDetailUiState.Error -> {
                    val error = (uiState as CatDetailUiState.Error).message
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CatDetailUiState.Success -> {
                    val cat = (uiState as CatDetailUiState.Success).cat

                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(cat.url)
                                .crossfade(true)
                                .crossfade(500)
                                .build(),
                            contentDescription = "Foto del gato en detalle",
                            modifier = Modifier.fillMaxWidth().height(350.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "ID: ${cat.id}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Resolución: ${cat.width} x ${cat.height}", fontSize = 16.sp)

                            if (!cat.breeds.isNullOrEmpty()) {
                                val breed = cat.breeds.first()
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "Raza:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text(text = breed.name, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Temperamento:", fontWeight = FontWeight.Bold)
                                Text(text = breed.temperament)
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Raza desconocida (Gato misterioso 🐈)",
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}