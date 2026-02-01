package com.parkjin.github_bookmark.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parkjin.github_bookmark.component.compose.HeaderSection
import com.parkjin.github_bookmark.component.compose.LoadingIndicator
import com.parkjin.github_bookmark.component.compose.SearchTextField
import com.parkjin.github_bookmark.component.compose.UserItemCard
import com.parkjin.github_bookmark.domain.model.User

@Composable
fun UserListContent(
    userListModels: List<UserListModel>,
    onSearch: (String) -> Unit,
    onBookmarkClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTextField(
            onSearch = onSearch,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = userListModels,
                key = { item ->
                    when (item) {
                        is UserListModel.HeaderModel -> "header_${item.value}"
                        is UserListModel.UserModel -> "user_${item.user.name}"
                        is UserListModel.LoadingModel -> "loading"
                    }
                }
            ) { item ->
                when (item) {
                    is UserListModel.HeaderModel -> {
                        HeaderSection(text = item.value?.toString() ?: "")
                    }
                    is UserListModel.UserModel -> {
                        UserItemCard(
                            name = item.user.name,
                            profileImageUrl = item.user.profileImageUrl,
                            isBookmarked = item.user.bookmarked,
                            onBookmarkClick = { onBookmarkClick(item.user) }
                        )
                    }
                    is UserListModel.LoadingModel -> {
                        LoadingIndicator()
                    }
                }
            }
        }
    }
}
