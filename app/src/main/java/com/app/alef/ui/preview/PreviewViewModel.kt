package com.app.alef.ui.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.alef.data.model.ItemsResponse
import com.app.alef.data.repository.NetworkState
import com.app.alef.ui.preview.di.PreviewScope
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@PreviewScope
class PreviewViewModel @Inject constructor(
    private val previewRepository: PreviewRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val items: LiveData<ItemsResponse> by lazy {
        previewRepository.getItems()
    }

    val networkState: LiveData<NetworkState> by lazy {
        previewRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}