package com.parkjin.github_bookmark.presentation.ui.bookmark

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parkjin.github_bookmark.presentation.ui.common.UserListContent

@Composable
fun BookmarkUserListScreen(modifier: Modifier = Modifier, viewModel: BookmarkUserListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    UserListContent(
        userListModels = state.userListModels,
        onSearch = { keyword ->
            viewModel.setAction(BookmarkUserListViewModel.Action.SearchUserList(keyword))
        },
        onBookmarkClick = { user ->
            viewModel.setAction(BookmarkUserListViewModel.Action.BookmarkUser(user))
        },
        modifier = modifier
    )
}
