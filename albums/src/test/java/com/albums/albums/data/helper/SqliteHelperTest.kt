package com.albums.albums.data.helper

import androidx.test.core.app.ApplicationProvider
import com.albums.albums.data.model.AlbumItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class SqliteHelperTest {
    lateinit var database: SqliteHelper

    @Before
    fun setUp() {
        database = SqliteHelper(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test initial database`() {
        val albums: List<AlbumItem> = database.getAllAlbums()
        assertEquals(albums.size, 0)
    }

    @Test
    fun `test get one album in database`() {
        database.insertAlbums(listOf(AlbumItem(1, 1, "Hello Test")))
        val albums: List<AlbumItem> = database.getAllAlbums()
        assertEquals(albums.size, 1)
    }

    @Test
    fun `test get all albums in database`() {
        database.insertAlbums(listOf(AlbumItem(1, 1, "Hello Test")))
        database.insertAlbums(listOf(AlbumItem(2, 3, "Hello Test2")))

        val albums: List<AlbumItem> = database.getAllAlbums()
        assertEquals(albums.size, 1)
    }

}