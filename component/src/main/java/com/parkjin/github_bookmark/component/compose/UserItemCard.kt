package com.parkjin.github_bookmark.component.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.parkjin.github_bookmark.component.R
import com.parkjin.github_bookmark.component.compose.theme.Blue
import com.parkjin.github_bookmark.component.compose.theme.GithubBookmarkTheme
import com.parkjin.github_bookmark.component.compose.theme.Gray

@Composable
fun UserItemCard(
    name: String,
    profileImageUrl: String,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = "Profile image of $name",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_user),
                error = painterResource(R.drawable.ic_user)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onBookmarkClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                    tint = if (isBookmarked) Blue else Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserItemCardPreview() {
    GithubBookmarkTheme {
        UserItemCard(
            name = "John Doe",
            profileImageUrl = "",
            isBookmarked = true,
            onBookmarkClick = {}
        )
    }
}
