package com.app.alef.ui.preview.di

import androidx.lifecycle.ViewModel
import com.app.alef.di.view_models.ViewModelKey
import com.app.alef.ui.preview.PreviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PreviewMediaViewModulesModule {

    @Binds
    @IntoMap
    @ViewModelKey(PreviewViewModel::class)
    abstract fun bindAuthViewModel(viewModel: PreviewViewModel): ViewModel?
}