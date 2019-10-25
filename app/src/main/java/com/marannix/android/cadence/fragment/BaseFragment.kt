package com.marannix.android.cadence.fragment

import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}