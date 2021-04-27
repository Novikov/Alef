package com.app.alef.di

import com.app.alef.ui.preview.di.PreviewComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun getPreviewComponent(): PreviewComponent.Factory
}