package com.app.alef.ui.preview.di

import com.app.alef.di.view_models.ViewModelFactoryModule
import com.app.alef.ui.preview.PreviewFragment
import dagger.Subcomponent

@PreviewScope
@Subcomponent(modules = [ViewModelFactoryModule::class, PreviewMediaViewModulesModule::class, PreviewModule::class])
interface PreviewComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PreviewComponent
    }

    fun inject(previewFragment: PreviewFragment)
}