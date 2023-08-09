package com.example.anfibios.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anfibios.R
import com.example.anfibios.model.Anfibio
import com.example.anfibios.ui.theme.AnfibiosTheme

@Composable
fun HomeScreen(
    anfibiosUiState: AnfibiosUiState, modifier: Modifier = Modifier
) {
    when (anfibiosUiState) { //No exemplo a parte de erro está distinta
        is AnfibiosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AnfibiosUiState.Success -> AnfibiosGridScreen(anfibiosUiState.anfibios, modifier)
        is AnfibiosUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun AnfibiosCard(anfibio: Anfibio, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            //o aspectRadio estaba mal no exemplo (1.f)
            .aspectRatio(1.5f),
        //a elevation é distinta que no exemplo por ser este versión 3 do Material
        elevation =  CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(anfibio.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.anfibio),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AnfibiosGridScreen(anfibios: List<Anfibio>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ){
        items(items = anfibios, key = { anfibio -> anfibio.name }) {
                anfibio -> AnfibiosCard(anfibio)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AnfibiosTheme {
        LoadingScreen()
    }
}
@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AnfibiosTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AnfibiosGridScreenPreview() {
    AnfibiosTheme {
        val mockData = List(10) { Anfibio("$it", "", "", "") }
        AnfibiosGridScreen(mockData) //simula datos baleiros
    }
}
