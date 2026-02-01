package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BookmarkUserUseCaseTest {

    private lateinit var useCase: BookmarkUserUseCase
    private lateinit var repository: BookmarkUserRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        repository = mockk()
        useCase = BookmarkUserUseCase(testDispatcher, repository)
    }

    @Test
    fun `when user is bookmarked, should call disableUserBookmark`() = runTest(testDispatcher) {
        // Given
        val user = User(
            name = "testUser",
            profileImageUrl = "https://example.com/image.png",
            bookmarked = true
        )
        coEvery { repository.disableUserBookmark(user) } just runs

        // When
        useCase(user)

        // Then
        coVerify(exactly = 1) { repository.disableUserBookmark(user) }
    }

    @Test
    fun `when user is not bookmarked, should call activateUserBookmark`() = runTest(testDispatcher) {
        // Given
        val user = User(
            name = "testUser",
            profileImageUrl = "https://example.com/image.png",
            bookmarked = false
        )
        coEvery { repository.activateUserBookmark(user) } just runs

        // When
        useCase(user)

        // Then
        coVerify(exactly = 1) { repository.activateUserBookmark(user) }
    }
}
