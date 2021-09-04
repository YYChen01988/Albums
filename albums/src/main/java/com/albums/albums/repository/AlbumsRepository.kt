package com.albums.albums.repository

import com.albums.core.network.ResponseHandler
import com.albums.albums.data.service.AlbumsService

class AlbumsRepository(
    private val albumsService: AlbumsService,
    private val responseHandler: ResponseHandler
) {
    suspend fun getAlbums() = try {
        val response = albumsService.getAlbums()
        responseHandler.handleSuccess(data = response)
    } catch (e: Exception) {
        responseHandler.handleException(e)
    }
}
