package com.example.wallpaper.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityMainBinding
import com.example.wallpaper.model.ImageModel
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.ui.detail.DetailActivity
import com.example.wallpaper.utils.KEY_IMAGE_BIO
import com.example.wallpaper.utils.KEY_IMAGE_DESCRIPTION
import com.example.wallpaper.utils.KEY_IMAGE_URL
import com.example.wallpaper.utils.KEY_URL_DOWNLOAD

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    MainRecyclerAdapter.ItemClickListener {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        viewModel.getData()
        addObservers()
    }

    private fun initRecyclerView() {
        binding.imageRecyclerView.layoutManager =
            GridLayoutManager(this, 2)
        binding.imageRecyclerView.adapter = MainRecyclerAdapter(this)
        binding.imageRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun updateAdapter(list: PagedList<ImageModel>) {
        val adapter = binding.imageRecyclerView.adapter
        if (adapter is MainRecyclerAdapter) {
            adapter.submitList(list)
        }
    }

    private fun addObservers() {
        viewModel.mainLiveData.observe(this, Observer { list ->
            updateAdapter(list)
        })
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(position: Int, imageModel: ImageModel, sharedImageView: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(KEY_IMAGE_URL, imageModel.urls.small)
        intent.putExtra(KEY_IMAGE_DESCRIPTION,imageModel.description)
        intent.putExtra(KEY_IMAGE_BIO,imageModel.user.bio)
        intent.putExtra(KEY_URL_DOWNLOAD,imageModel.urls.full)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            getString(R.string.transition_hello)
        )
        startActivity(intent, options.toBundle())
    }
}
