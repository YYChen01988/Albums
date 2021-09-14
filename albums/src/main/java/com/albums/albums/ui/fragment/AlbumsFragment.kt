package com.albums.albums.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.albums.R
import com.albums.albums.data.helper.SqliteHelper
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.ui.adapter.AlbumsAdapter
import com.albums.albums.viewmodel.AlbumsViewModel
import com.albums.core.network.Status
import com.albums.core.ui.activity.BaseActivity
import com.albums.core.ui.fragments.BaseFragment
import com.albums.databinding.FragmentAlbumsBinding
import org.koin.android.ext.android.inject

class AlbumsFragment : BaseFragment() {

    private val albumsViewModel: AlbumsViewModel by inject()

    private lateinit var albumsAdapter: AlbumsAdapter

    private lateinit var sqliteHelper: SqliteHelper

    private lateinit var navController: NavController

    private lateinit var fragmentAlbumsBinding: FragmentAlbumsBinding

    override fun getErrorView(): View = fragmentAlbumsBinding.errorView.root

    override fun getErrorMessageTextView(): TextView = fragmentAlbumsBinding.errorView.tvError

    override fun getSuccessView(): View = fragmentAlbumsBinding.albumsSuccess.root

    override fun getWaitingView(): View = fragmentAlbumsBinding.waitView.root

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAlbumsBinding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return fragmentAlbumsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        sqliteHelper = context?.let { SqliteHelper(it) }!!
        subscribeToEvents()
        setUpAlbumsFromDb()
    }

    private fun subscribeToEvents() {
        albumsViewModel.getAlbums().observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        showSuccessResponse()
                        it.data?.let { albums ->
                            val sortedAlbums = albumsViewModel.sortedAlbums(albums)
                            sqliteHelper.insertAlbums(sortedAlbums)
                            loadData(sortedAlbums)                        }
                    }
                    Status.ERROR -> showError(it.message ?: getString(R.string.error_loading_data))
                }
            }
        )
        albumsViewModel.fetchAllAlbums()
    }

    private fun loadData(albums: List<AlbumItem>?) {
        albums?.let {
            loadAlbumsInRecyclerView(it)
        } ?: showError(getString(R.string.error_loading_data))
    }

    private fun loadAlbumsInRecyclerView(albums: List<AlbumItem>) {
        fragmentAlbumsBinding.albumsSuccess.rvAlbums.apply {
            albumsAdapter = AlbumsAdapter(activity as BaseActivity, albums)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = albumsAdapter
            albumsAdapter.setDataList(albums)
        }
    }

    private fun setUpAlbumsFromDb(){
        val albums = sqliteHelper.getAllAlbums()
        loadData(albums)
    }


}
