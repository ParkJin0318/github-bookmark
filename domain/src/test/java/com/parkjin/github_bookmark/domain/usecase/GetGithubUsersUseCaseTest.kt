package com.parkjin.github_bookmark.domain.usecase

import app.cash.turbine.test
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGithubUsersUseCaseTest {

    private lateinit var useCase: GetGithubUsersUseCase
    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var bookmarkUserRepository: BookmarkUserRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        githubUserRepository = mockk()
        bookmarkUserRepository = mockk()
        useCase = GetGithubUsersUseCase(testDispatcher, githubUserRepository, bookmarkUserRepository)
    }

    @Test
    fun `should return github users with bookmark status`() = runTest(testDispatcher) {
        // Given
        val searchName = "test"
        val githubUsers = listOf(
            User(name = "user1", profileImageUrl = "url1", bookmarked = false),
            User(name = "user2", profileImageUrl = "url2", bookmarked = false),
            User(name = "user3", profileImageUrl = "url3", bookmarked = false)
        )
        val bookmarkedUsers = listOf(
            User(name = "user1", profileImageUrl = "url1", bookmarked = true)
        )

        every { githubUserRepository.getUsers(searchName) } returns flowOf(githubUsers)
        every { bookmarkUserRepository.getUsers(searchName) } returns flowOf(bookmarkedUsers)

        // When & Then
        useCase(searchName).test {
            val result = awaitItem()

            assertEquals(3, result.size)
            assertTrue(result.find { it.name == "user1" }?.bookmarked == true)
            assertFalse(result.find { it.name == "user2" }?.bookmarked == true)
            assertFalse(result.find { it.name == "user3" }?.bookmarked == true)

            awaitComplete()
        }
    }

    @Test
    fun `should return github users without bookmark when no bookmarks exist`() = runTest(testDispatcher) {
        // Given
        val searchName = "test"
        val githubUsers = listOf(
            User(name = "user1", profileImageUrl = "url1", bookmarked = false),
            User(name = "user2", profileImageUrl = "url2", bookmarked = false)
        )

        every { githubUserRepository.getUsers(searchName) } returns flowOf(githubUsers)
        every { bookmarkUserRepository.getUsers(searchName) } returns flowOf(emptyList())

        // When & Then
        useCase(searchName).test {
            val result = awaitItem()

            assertEquals(2, result.size)
            result.forEach { user ->
                assertFalse(user.bookmarked)
            }

            awaitComplete()
        }
    }

    @Test
    fun `should return empty list when no github users found`() = runTest(testDispatcher) {
        // Given
        val searchName = "nonexistent"

        every { githubUserRepository.getUsers(searchName) } returns flowOf(emptyList())
        every { bookmarkUserRepository.getUsers(searchName) } returns flowOf(emptyList())

        // When & Then
        useCase(searchName).test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `should mark all users as bookmarked when all are in bookmark list`() = runTest(testDispatcher) {
        // Given
        val searchName = "test"
        val githubUsers = listOf(
            User(name = "user1", profileImageUrl = "url1", bookmarked = false),
            User(name = "user2", profileImageUrl = "url2", bookmarked = false)
        )
        val bookmarkedUsers = listOf(
            User(name = "user1", profileImageUrl = "url1", bookmarked = true),
            User(name = "user2", profileImageUrl = "url2", bookmarked = true)
        )

        every { githubUserRepository.getUsers(searchName) } returns flowOf(githubUsers)
        every { bookmarkUserRepository.getUsers(searchName) } returns flowOf(bookmarkedUsers)

        // When & Then
        useCase(searchName).test {
            val result = awaitItem()

            assertEquals(2, result.size)
            result.forEach { user ->
                assertTrue(user.bookmarked)
            }

            awaitComplete()
        }
    }
}
