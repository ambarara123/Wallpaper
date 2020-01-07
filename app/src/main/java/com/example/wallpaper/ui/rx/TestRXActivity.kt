package com.example.wallpaper.ui.rx

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.text.clearSpans
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

        val spannable = SpannableString("should contain uppercase,number,symbols")//6,7,9,6,7

        val isValid = viewModel.formValidation(userNameObservable, passwordObservable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                with(it) {
                    when {
                        second is InvalidNameException -> binding.usernameEditText.error =
                            second?.message

                        second is InvalidPasswordException -> binding.passwordEditText.error =
                            second?.message

                        first == 4 -> {
                            showToast(R.string.message_form_valid)
                            binding.passwordEditText.error = null
                            binding.usernameEditText.error = null
                            uppercaseSpan(spannable)
                            numberSpan(spannable)
                            symbolSpan(spannable)
                        }
                        first == 1 -> uppercaseSpan(spannable)

                        first == 2 -> numberSpan(spannable)

                        first == 3 -> symbolSpan(spannable)

                        first == 0 -> spannable.clearSpans()
                    }
                }
                binding.spannableTextView.text = spannable
            }

        disposable.addAll(isValid)
    }

    private fun uppercaseSpan(spannable: SpannableString) {
        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            14, 24,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }

    private fun numberSpan(spannable: SpannableString) {
        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.clearSpans()
    }

    private fun symbolSpan(spannable: SpannableString) {
        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            32, 39,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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
