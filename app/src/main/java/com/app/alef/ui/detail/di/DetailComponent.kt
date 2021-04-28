package com.app.alef.ui.detail.di

import com.app.alef.ui.detail.DetailFragment
import dagger.Subcomponent

@DetailScope
@Subcomponent()
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(detailFragment: DetailFragment)
}