package com.parkjin.github_bookmark.domain.usecase

import app.cash.turbine.test
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetBookmarkUsersUseCaseTest {

    private lateinit var useCase: GetBookmarkUsersUseCase
    private lateinit var repository: BookmarkUserRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetBookmarkUsersUseCase(testDispatcher, repository)
    }

    @Test
    fun `should return bookmarked users from repository`() = runTest(testDispatcher) {
        // Given
        val searchName = "test"
        val expectedUsers = listOf(
            User(name = "testUser1", profileImageUrl = "url1", bookmarked = true),
            User(name = "testUser2", profileImageUrl = "url2", bookmarked = true)
        )
        every { repository.getUsers(searchName) } returns flowOf(expectedUsers)

        // When & Then
        useCase(searchName).test {
            assertEquals(expectedUsers, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.getUsers(searchName) }
    }

    @Test
    fun `should return empty list when no bookmarked users found`() = runTest(testDispatcher) {
        // Given
        val searchName = "nonexistent"
        every { repository.getUsers(searchName) } returns flowOf(emptyList())

        // When & Then
        useCase(searchName).test {
            assertEquals(emptyList<User>(), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should filter users by name`() = runTest(testDispatcher) {
        // Given
        val searchName = "john"
        val filteredUsers = listOf(
            User(name = "john_doe", profileImageUrl = "url1", bookmarked = true)
        )
        every { repository.getUsers(searchName) } returns flowOf(filteredUsers)

        // When & Then
        useCase(searchName).test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("john_doe", result[0].name)
            awaitComplete()
        }
    }
}
