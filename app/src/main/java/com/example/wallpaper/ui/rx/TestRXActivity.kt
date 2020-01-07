package com.example.wallpaper.ui.rx

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityTestRxBinding
import com.example.wallpaper.exceptions.InvalidNameException
import com.example.wallpaper.exceptions.InvalidPasswordException
import com.example.wallpaper.ui.base.BaseActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TestRXActivity : BaseActivity<ActivityTestRxBinding, RxViewModel>() {

    override fun getViewModelClass(): Class<RxViewModel> = RxViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_test_rx

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.deBounceOperator(RxTextView.textChangeEvents(binding.editText).skipInitialValue())
        addObservable()
        addListeners()
        formValidation()
    }


    private fun addListeners() {
        with(binding) {
            zipButton.setOnClickListener {
                viewModel.zipOperator()
            }

            combineLatest.setOnClickListener {
                viewModel.combineLatestOperator()
            }
        }
    }

    private fun formValidation() {
        val userNameObservable = RxTextView.textChanges(binding.usernameEditText)
            .map {
                it.toString()
            }

        val passwordObservable = RxTextView.textChanges(binding.passwordEditText)
            .map {
                Timber.d(it.toString())
                it.toString()
            }

        val isValid = viewModel.formValidation(userNameObservable, passwordObservable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                with(it) {
                    when {
                        first -> {
                            showToast(R.string.message_form_valid)
                            binding.usernameEditText.error = null
                            binding.passwordEditText.error = null
                        }
                        second is InvalidNameException -> binding.usernameEditText.error =
                            second?.message
                        second is InvalidPasswordException -> binding.passwordEditText.error =
                            second?.message
                    }
                }
            }

        disposable.addAll(isValid)
    }

    private fun addObservable() {
        viewModel.textLiveData.observe(this, Observer {
            binding.textView.text = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}
