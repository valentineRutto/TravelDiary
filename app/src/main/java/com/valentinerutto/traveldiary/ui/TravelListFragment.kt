package com.valentinerutto.traveldiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TravelListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel by sharedViewModel<TravelViewModel>()
    private lateinit var travelAdapter: TravelAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDetails()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnEventClickListeners()

        travelAdapter = TravelAdapter(object : OnTravelEntryClicked {
            override fun showTravelDetails(travel: TravelDetailsEntity) {
                viewModel._selectedTravelDetails.value = travel
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        })

        viewModel.mAllDetails?.observe(viewLifecycleOwner, Observer {
            setUpViews(it)
        })

        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredList(newText)
                return true
            }
        })


    }

    fun filteredList(query: String) {
        lifecycleScope.launch {
            val filteredList = viewModel.searchEntries(query)
            setUpViews(filteredList, query, true)
        }
    }

    private fun setUpViews(
        entries: List<TravelDetailsEntity>,
        searchInput: String = "",
        search: Boolean = false,
    ) {

        if (entries.isEmpty()) {
            binding.entryList.isVisible = false
            binding.entryErrorTextView.isVisible = true

            binding.entryListEmptyText.isVisible = true

            if (search && searchInput.isNotBlank()) {
                binding.entryErrorTextView.text = "No Entry Found. Click + to add"
                binding.entryListEmptyText.isVisible = false

            }


        } else {

            binding.entryList.isVisible = true
            binding.entryErrorTextView.isVisible = false

            if (search) {
                binding.entryList.smoothScrollToPosition(0)
            }

            binding.entryList.adapter = travelAdapter.apply {
                submitList(entries)
            }
        }
    }

    private fun setOnEventClickListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AddEntryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}