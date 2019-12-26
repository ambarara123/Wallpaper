package com.example.wallpaper.ui.detail

import android.annotation.TargetApi
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityDetailBinding
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.utils.KEY_IMAGE_BIO
import com.example.wallpaper.utils.KEY_IMAGE_DESCRIPTION
import com.example.wallpaper.utils.KEY_IMAGE_URL
import com.example.wallpaper.utils.KEY_URL_DOWNLOAD

class DetailActivity : BaseActivity<ActivityDetailBinding,DetailViewModel>() {

    override fun getViewModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_detail

    private var downloadUrl : String? = null
    private lateinit var wallpaperDrawable: Drawable

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()

        transitionImageSetup()
        addListeners()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addListeners(){
        binding.downloadBtn.setOnClickListener {
            downloadUrl?.let {
                viewModel.downloadWallpaper(it)
            }

        }

        binding.setWallpaperBtn.setOnClickListener {
            viewModel.setWallpaper(wallpaperDrawable)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun transitionImageSetup() {
        val url = intent.getStringExtra(KEY_IMAGE_URL)
        url?.let { imageUrl ->
            Glide.with(this).load(imageUrl).listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    resource?.let {
                        wallpaperDrawable = it
                    }
                    return false
                }
            }).into(binding.imageView)
        }

        val bio = intent.getStringExtra(KEY_IMAGE_BIO)

        val description = intent.getStringExtra(KEY_IMAGE_DESCRIPTION)
        description?.let {
            binding.detailTextView.text = it
        }
        bio?.let {
            binding.detailTextView.text = it
        }

        downloadUrl = intent.getStringExtra(KEY_URL_DOWNLOAD)
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        supportFinishAfterTransition()
    }
}
