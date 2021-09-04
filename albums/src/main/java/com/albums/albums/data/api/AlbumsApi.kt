package com.albums.albums.data.api

import com.albums.albums.data.model.AlbumItem
import retrofit2.http.GET

interface AlbumsApi {

    @GET("albums")
    suspend fun getAlbums(): List<AlbumItem>
}
