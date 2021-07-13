package com.sheywesk.marvel_api.presentation.search_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.databinding.CharacterListFragmentBinding
import com.sheywesk.marvel_api.presentation.character_list.CharacterAdapter
import com.sheywesk.marvel_api.presentation.home.CharacterActivity
import com.sheywesk.marvel_api.presentation.character_details.CharacterDetailsActivity
import com.sheywesk.marvel_api.presentation.dialogs.PurchaseConfirmationDialogFragment
import com.sheywesk.marvel_api.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(
           onItemClick= { character ->
                startActivity(
                    CharacterDetailsActivity.getIntent(
                        activity as Context,
                        character.id
                    )
                )
            },
            onFavoriteClick= { character ->
                searchViewModel.updateFavorite(character)
            })
    }

    private lateinit var binding: CharacterListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        setupObservables()
        setupActionBar()
        return binding.root
    }

    private fun setupActionBar() {
        val actionBar = (activity as CharacterActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.search_title)
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            itemAnimator = null
            val activity = activity as Context
            layoutManager = LinearLayoutManager(activity)
            adapter = characterAdapter
        }
    }

    private fun setupObservables() {
        searchViewModel.filteredCharacter.observe(viewLifecycleOwner, {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        characterAdapter.submitList(it.data)

                    }
                    Status.ERROR -> {
                        PurchaseConfirmationDialogFragment().show(
                            childFragmentManager,
                            PurchaseConfirmationDialogFragment.TAG
                        )
                    }
                }
            }
        })
    }

    private fun onChange(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                observableSearch(query = newText)
                return false
            }

        })
    }

    private fun observableSearch(query: String?) {
        try {
            if (query != null) {
                searchViewModel.filterByName(query)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.isFocusable = true;
        searchView.isIconified = false;
        onChange(searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

}