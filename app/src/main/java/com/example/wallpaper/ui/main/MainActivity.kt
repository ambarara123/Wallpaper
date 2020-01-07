package com.example.wallpaper.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityMainBinding
import com.example.wallpaper.network.model.ImageModel
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.ui.detail.DetailActivity
import com.example.wallpaper.ui.rx.TestRXActivity
import com.example.wallpaper.utils.KEY_INTENT_BUNDLE

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    MainRecyclerAdapter.ItemClickListener {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        addObservers()
        viewModel.getData()
    }

    private fun initRecyclerView() {
        with(binding.imageRecyclerView) {
            layoutManager =
                GridLayoutManager(this@MainActivity,2)
            adapter = MainRecyclerAdapter(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun updateAdapter(list: PagedList<ImageModel>) {
        val adapter = binding.imageRecyclerView.adapter
        (adapter as? MainRecyclerAdapter)?.submitList(list)
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionRxActivity){
            startActivity(Intent(this, TestRXActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}

