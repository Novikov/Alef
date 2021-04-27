package com.app.alef.ui.preview.di

import androidx.lifecycle.MutableLiveData
import com.app.alef.data.model.ItemsResponse
import com.app.alef.data.repository.NetworkState
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class PreviewModule {

    @PreviewScope
    @Provides
    fun providesCompositeDisposable():CompositeDisposable{
        return CompositeDisposable()
    }

    @PreviewScope
    @Provides
    fun provideItemsResponseMutableLiveData(): MutableLiveData<ItemsResponse> {
        return MutableLiveData<ItemsResponse>()
    }

    @PreviewScope
    @Provides
    fun providesItemsNetworkState(): MutableLiveData<NetworkState> {
        return MutableLiveData<NetworkState>()
    }
}