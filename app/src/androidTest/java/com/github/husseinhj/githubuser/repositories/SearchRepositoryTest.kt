package com.github.husseinhj.githubuser.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.husseinhj.githubuser.services.repositories.SearchRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun foundUserBySearchWithSimpleStringQuery_CorrectResponse() {
//        runBlocking {
//            val query = "a"
//            val result = SearchRepository.searchUser(query)
//
//            assertTrue((result.body()?.items?.size ?: 0) > 0)
//        }
    }

    @Test
    fun notFoundUserBySearchWithSimpleStringQuery_CorrectResponse() {
//        runBlocking {
//            val query = "@"
//            val result = SearchRepository.searchUser(query)
//
//            assertTrue(result.body()?.items?.isNullOrEmpty() == true)
//        }
    }

}