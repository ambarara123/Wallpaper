package com.example.wallpaper.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityMainBinding
import com.example.wallpaper.model.ImageModel
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.ui.detail.DetailActivity
import com.example.wallpaper.utils.KEY_INTENT_BUNDLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        with(binding) {
            imageRecyclerView.layoutManager =
                GridLayoutManager(this@MainActivity,2)
            imageRecyclerView.adapter = MainRecyclerAdapter(this@MainActivity)
            imageRecyclerView.itemAnimator = DefaultItemAnimator()
        }
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

    override fun onClick(position: Int, imageModel: ImageModel, sharedImageView: ImageView) {
        val intentData: Bundle = DetailActivity.getBundle(imageModel)
        val intent = Intent(this, DetailActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            getString(R.string.transition_hello)
        )
        intent.putExtra(KEY_INTENT_BUNDLE, intentData)
        startActivity(intent, options.toBundle())
        CoroutineScope(Dispatchers.Default).launch {

        }
    }
}

