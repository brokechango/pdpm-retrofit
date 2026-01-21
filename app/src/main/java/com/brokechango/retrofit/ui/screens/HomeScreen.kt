package com.brokechango.retrofit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokechango.retrofit.datamanager.repository.KanyeQueryRepository
import com.brokechango.retrofit.models.KanyeQuotes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
fun HomeScreen(
    model: HomeScreenViewModel,
) {
    val uiState by model.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 0, 13))
    ) {
        // --- CAPA 1: Marca de agua "YE" ---
        Text(
            text = "YE",
            fontSize = 300.sp,
            fontWeight = FontWeight.Black,
            color = Color.DarkGray.copy(0.55f),
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(-2f)
        )

        // --- CAPA 2: Icono de comillas superior ---
        Text(
            text = "“",
            fontSize = 120.sp,
            color = Color.White.copy(alpha = 0.3f),
            modifier = Modifier
                .padding(start = 24.dp, top = 40.dp)
                .align(Alignment.TopStart)
        )
        // --- CAPA 2: Icono de comillas superior ---
        Text(
            text = "“",
            fontSize = 120.sp,
            color = Color.White.copy(alpha = 0.3f),
            modifier = Modifier
                .padding(end = 24.dp, bottom = 40.dp)
                .align(Alignment.BottomEnd)
                .rotate(180f)
        )

        // --- CAPA 3: Contenido Central ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Frase de Kanye
            val quoteText = uiState.kanyeQuote?.quote?.uppercase() ?: "LOADING..."

            uiState.kanyeQuote?.let {
                Text(
                    text = "\"${quoteText}\"",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 54.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtítulo de la Era
            Text(
                text = "THE LIFE OF PABLO ERA",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Botón personalizado
            Button(
                onClick = { model.getQuery() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(64.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "YE SAID IT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }


        }
    }

}

data class HomeScreenUiState(
    val kanyeQuote: KanyeQuotes? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HomeScreenViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val repository = KanyeQueryRepository()

    fun update(action: HomeScreenUiState.() -> HomeScreenUiState) {
        _uiState.update(action)
    }

    fun getQuery() {
        update { copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val quote = repository.getQuote()
                update {
                    copy(
                        kanyeQuote = quote
                    )
                }
            } catch (e: Exception) {
                update { copy(errorMessage = "Error fetching quote: ${e.message}") }
            } finally {
                update {
                    copy(
                        isLoading = false
                    )
                }
            }
        }
    }


}