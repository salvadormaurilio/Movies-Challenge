package com.example.kueskichagenge.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kueskichagenge.ui.theme.KueskiChagengeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreChip(genres: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        genres.forEach { genre ->
            SuggestionChip(
                border = SuggestionChipDefaults.suggestionChipBorder(
                    enabled = true,
                    borderWidth = 1.dp,
                    borderColor = MaterialTheme.colorScheme.primary
                ),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                label = {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                onClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenreChipPreview() {
    KueskiChagengeTheme {
        GenreChip(
            genres = listOf("Action", "Thriller")
        )
    }
}