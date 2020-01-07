package com.example.wallpaper.ui.rx

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityTestRxBinding
import com.example.wallpaper.exceptions.InvalidNameException
import com.example.wallpaper.exceptions.InvalidPasswordException
import com.example.wallpaper.ui.base.BaseActivity
import com.example.wallpaper.utils.StringValidation
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

                        first.containsSymbols && first.containsNumber && first.containsUpperCase -> {
                            updateSpannableTextAppearance(spannable,first)
                            showToast(R.string.message_form_valid)
                        }

                        else -> updateSpannableTextAppearance(spannable,first)

                    }
                }
                binding.spannableTextView.text = spannable
            }

        disposable.addAll(isValid)
    }

    private fun updateSpannableTextAppearance(spannable: SpannableString, stringValidation: StringValidation) {
        uppercaseSpan(spannable, stringValidation.containsUpperCase)
        numberSpan(spannable,stringValidation.containsNumber)
        symbolSpan(spannable,stringValidation.containsSymbols)
    }

    private fun uppercaseSpan(spannable: SpannableString, isColored: Boolean) {
        val color = when {
            isColored -> Color.GREEN
            else -> Color.GRAY
        }
        spannable.setSpan(
            ForegroundColorSpan(color),
            14, 24,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }

    private fun numberSpan(spannable: SpannableString, isColored: Boolean) {
        val color = when {
            isColored -> Color.GREEN
            else -> Color.GRAY
        }
        spannable.setSpan(
            ForegroundColorSpan(color),
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun symbolSpan(spannable: SpannableString, isColored: Boolean) {
        val color = when {
            isColored -> Color.GREEN
            else -> Color.GRAY
        }
        spannable.setSpan(
            ForegroundColorSpan(color),
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
