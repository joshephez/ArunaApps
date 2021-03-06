package com.gudangada.arunaapps.util

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.gudangada.arunaapps.viewmodels.ViewModelContract
import com.xoxoer.lifemarklibrary.Lifemark
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RxSingleHandler @Inject constructor(
    private val application: Application,
    private val lifemark: Lifemark,
    private val vm: ViewModelContract
) {

    private val tag = "RxSingleHandler"

    private fun <T> errorDispatcher(
        errorReason: String,
        targetMutable: MutableLiveData<T>
    ) {
        vm.error.set(true)
        vm.errorReason.set(errorReason)
        targetMutable.value = null
        Toast.makeText(application, vm.errorReason.get(), Toast.LENGTH_SHORT).show()
        Log.e(tag, "Reason from view model ${vm.errorReason.get()}")
    }

    fun <T> handler(targetMutable: MutableLiveData<T>) =
        object : SingleObserver<T>(CompositeDisposable()) {
            override fun onResult(data: T) {
                targetMutable.value = data
            }

            override fun onError(e: Error) {
                when (lifemark.isNetworkConnected()) {
                    true -> errorDispatcher(
                        e.message,
                        targetMutable
                    )
                    false -> errorDispatcher(
                        "No Internet Connection",
                        targetMutable
                    )
                }
            }
        }
}