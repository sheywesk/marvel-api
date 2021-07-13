package com.sheywesk.marvel_api.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.databinding.ActivityMainBinding

class CharacterActivity : AppCompatActivity(),NavigationHost {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            navigationTo(CharacterListFragment.newInstance(),false)
        }
    }

    fun progressBar(isVisible:Boolean){
        binding.progressCircular.isVisible = isVisible
    }

    override fun navigationTo(fragment: Fragment, addBackStack: Boolean) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.character_list_fragment,fragment)
            if(addBackStack){
                addToBackStack(null)
            }
        }
    }

}