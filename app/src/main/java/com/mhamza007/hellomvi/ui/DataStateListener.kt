package com.mhamza007.hellomvi.ui

import com.mhamza007.hellomvi.util.DataState

interface DataStateListener {

    fun onDataStateChanged(dataState: DataState<*>?) {

    }
}