package com.albums.albums.repository

import com.albums.albums.data.model.AlbumItem
import com.albums.albums.data.service.AlbumsService
import com.albums.core.network.Resource
import com.albums.core.network.ResponseHandler
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException


@RunWith(MockitoJUnitRunner::class)
class AlbumsRepositoryTest {
    @Mock
    private lateinit var mockException: HttpException

    @Mock
    private lateinit var albumsService: AlbumsService

    private val responseHandler = ResponseHandler()

    private lateinit var repository: AlbumsRepository

    private val albums = listOf(AlbumItem(1, 1, "test1"), AlbumItem(2, 2, "test2"))

    private val albumResponse = Resource.success(albums)
    private val errorResponse = Resource.error(null, "Unauthorised")

    @Before
    fun setUp() {
        repository = AlbumsRepository(albumsService, responseHandler)
    }

    @Test
    fun getAlbums() {
        runBlocking {
            Mockito.`when`(albumsService.getAlbums()).thenReturn((albums))
            assertEquals(albumResponse, repository.getAlbums())
        }
    }

    @Test
    fun getError() {
        runBlocking {
            Mockito.`when`(mockException.code()).thenReturn(401)
            Mockito.`when`(albumsService.getAlbums()).thenThrow(mockException)
            assertEquals(errorResponse, repository.getAlbums())
        }
    }
}