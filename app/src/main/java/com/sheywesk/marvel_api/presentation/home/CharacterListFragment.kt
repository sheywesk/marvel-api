package com.sheywesk.marvel_api.presentation.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.databinding.ActivityMainBinding
import com.sheywesk.marvel_api.databinding.CharacterListFragmentBinding
import com.sheywesk.marvel_api.presentation.character_list.CharacterAdapter
import com.sheywesk.marvel_api.presentation.character_details.CharacterDetailsActivity
import com.sheywesk.marvel_api.presentation.dialogs.PurchaseConfirmationDialogFragment
import com.sheywesk.marvel_api.presentation.search_fragment.SearchFragment
import com.sheywesk.marvel_api.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterListFragment()
    }

    private val characterViewModel: CharacterViewModel by viewModel()
    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(
            onItemClick = { character ->
                startActivity(
                    CharacterDetailsActivity.getIntent(
                        activity as Context,
                        character.id
                    )
                )
            },
            onFavoriteClick = { character ->
                characterViewModel.updateFavorite(character)
            })
    }

    private lateinit var _binding: CharacterListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        setupActionBar()
        setUpRecyclerView()
        setUpObservables()
        return _binding.root
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        val actionBar = (activity as CharacterActivity).supportActionBar
        actionBar?.title = getString(R.string.app_name)
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setUpRecyclerView() {
        with(_binding.recyclerView) {
            itemAnimator = null
            val activity = activity as Context
            layoutManager = LinearLayoutManager(activity)
            adapter = characterAdapter
        }
    }

    private fun setUpObservables() {
        characterViewModel.marvelViewModel.observe(viewLifecycleOwner, {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        (activity as  CharacterActivity).progressBar(true)
                    }
                    Status.SUCCESS -> {
                        (activity as  CharacterActivity).progressBar(false)
                        characterAdapter.submitList(it.data)
                    }
                    Status.ERROR -> {
                        (activity as  CharacterActivity).progressBar(false)
                        PurchaseConfirmationDialogFragment().show(
                            childFragmentManager,
                            PurchaseConfirmationDialogFragment.TAG
                        )
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun dialogError(message: String) {
        val builder = activity?.let { AlertDialog.Builder(it) }
        builder?.apply {
            setTitle(getString(R.string.title_error_dialog))
            setMessage(message)
            setPositiveButton(
                getString(R.string.positive_button),
            ) { dialog, witch ->
                dialog.cancel()
            }
            create()
            show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_home -> {
                (activity as CharacterActivity).navigationTo(SearchFragment.newInstance(), true)
                true
            }
            else -> {
                Log.i("Error options", "id: ${item.itemId}")
                true
            }
        }
    }
}