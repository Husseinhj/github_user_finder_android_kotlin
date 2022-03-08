package com.github.husseinhj.githubuser.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.husseinhj.githubuser.services.repositories.UserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun getResponseUserDetails_CorrectResponse() {
        runBlocking {
            val username = "husseinhj"
            val result = UserRepository.getUserDetail(username)

            assertTrue(result.body()?.login?.lowercase() == username)
        }
    }

    @Test
    fun getNotExistsUser_CorrectResponse() {
        runBlocking {
            val username = "@"
            val result = UserRepository.getUserDetail(username)

            assertTrue(result.code() == 404)
        }
    }
}