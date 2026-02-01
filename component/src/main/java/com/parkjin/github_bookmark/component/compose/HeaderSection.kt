package com.parkjin.github_bookmark.component.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parkjin.github_bookmark.component.compose.theme.GithubBookmarkTheme
import com.parkjin.github_bookmark.component.compose.theme.LightGray

@Composable
fun HeaderSection(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .background(LightGray)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun HeaderSectionPreview() {
    GithubBookmarkTheme {
        HeaderSection(text = "A")
    }
}
