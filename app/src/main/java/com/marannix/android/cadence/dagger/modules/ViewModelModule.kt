package com.marannix.android.cadence.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marannix.android.cadence.viewmodel.ViewModelKey
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import com.marannix.android.cadence.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GithubRepoViewModel::class)
    internal abstract fun bindingGithubViewModel(viewModel: GithubRepoViewModel): ViewModel

}