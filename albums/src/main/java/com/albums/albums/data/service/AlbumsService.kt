package com.albums.albums.data.service

import com.albums.albums.data.api.AlbumsApi

class AlbumsService(private val albumsApi: AlbumsApi) {
    suspend fun getAlbums() = albumsApi.getAlbums()
}
