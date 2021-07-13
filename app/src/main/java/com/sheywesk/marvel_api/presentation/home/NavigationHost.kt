package com.sheywesk.marvel_api.presentation.home

import androidx.fragment.app.Fragment

interface NavigationHost {
    fun navigationTo(fragment:Fragment,addBackStack:Boolean)
}