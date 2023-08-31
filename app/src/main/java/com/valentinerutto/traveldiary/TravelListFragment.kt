package com.valentinerutto.traveldiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.databinding.FragmentFirstBinding
import com.valentinerutto.traveldiary.ui.OnTravelEntryClicked
import com.valentinerutto.traveldiary.ui.TravelAdapter
import com.valentinerutto.traveldiary.ui.TravelViewModel
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
            binding.entryList.adapter = travelAdapter.apply {
                submitList(it)
            }
        })
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