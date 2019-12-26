package com.example.wallpaper.ui.detail

import android.content.BroadcastReceiver
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityDetailBinding
import com.example.wallpaper.model.ImageModel
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.utils.*
import android.content.Intent
import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.Observer


class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override fun getViewModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_detail

    companion object {
        fun getBundle(imageModel: ImageModel): Bundle {
            return Bundle().apply {
                putString(KEY_IMAGE_URL_SMALL, imageModel.urls.small)
                putString(KEY_IMAGE_DESCRIPTION, imageModel.description)
                putString(KEY_IMAGE_BIO, imageModel.user.bio)
                putString(KEY_IMAGE_NAME, imageModel.id)
                putString(KEY_IMAGE_URL_LARGE, imageModel.urls.full)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()

        transitionImageSetup()
        addListeners()
        addObserver()
    }

    private fun addListeners() {
        with(binding) {
            downloadBtn.setOnClickListener {
                downloadFile()
            }

            setWallpaperBtn.setOnClickListener {
                val imageName = getImageName()
                val downloadUrl = getDownloadURL()

                requireNotNull(imageName)
                requireNotNull(downloadUrl)

                viewModel.setWallpaper(downloadUrl, imageName, "png")
            }
        }
    }

    private fun addObserver() {
        viewModel.liveDownloadID.observe(this@DetailActivity, Observer {
            setBroadcastReceiver(it)
        })
    }

    private fun setBroadcastReceiver(id: Long) {
        val imageName = getImageName()
        val downloadUrl = getDownloadURL()

        requireNotNull(imageName)
        requireNotNull(downloadUrl)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                val receivedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (receivedId == id) {
                    Toast.makeText(this@DetailActivity, "IMAGE DOWNLOADED", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.setWallpaper(downloadUrl, imageName, "png")
                }
            }
        }
        registerBroadcastReceiver(broadcastReceiver)
    }

    private fun registerBroadcastReceiver(broadcastReceiver: BroadcastReceiver) {
        registerReceiver(broadcastReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun downloadFile() {
        val url = getDownloadURL()
        val imageName = getImageName()

        requireNotNull(url)
        requireNotNull(imageName)

        if (viewModel.checkIfFileExists(imageName, "png")) {
            Toast.makeText(this, "ALREADY DOWNLOADED", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.downloadWallpaper(url, imageName)
    }

    private fun transitionImageSetup() {

        val bundle = getBundle()
        val url = bundle?.getString(KEY_IMAGE_URL_SMALL)
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
                    return false
                }
            }).into(binding.imageView)
        }

        bundle?.getString(KEY_IMAGE_BIO)?.let {
            binding.detailTextView.text = it
        }

        bundle?.getString(KEY_IMAGE_DESCRIPTION).let {
            binding.detailTextView.text = it
        }

    }

    private fun getImageName() = getBundle()?.getString(KEY_IMAGE_NAME)

    private fun getDownloadURL() = getBundle()?.getString(KEY_IMAGE_URL_LARGE)

    private fun getBundle() = intent.getBundleExtra(KEY_INTENT_BUNDLE)

    override fun onBackPressed() {
        // super.onBackPressed()
        supportFinishAfterTransition()
    }
}
