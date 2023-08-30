package com.valentinerutto.traveldiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.databinding.FragmentFirstBinding
import com.valentinerutto.traveldiary.ui.OnTravelEntryClicked
import com.valentinerutto.traveldiary.ui.TravelAdapter
import com.valentinerutto.traveldiary.ui.TravelViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel by sharedViewModel<TravelViewModel>()
    private lateinit var travelAdapter: TravelAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        travelAdapter = TravelAdapter(object : OnTravelEntryClicked {
            override fun showTravelDetails(travel: TravelDetailsEntity) {
                Toast.makeText(
                    requireActivity(),
                   travel.title,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.selectedTravelDetails?.value = travel
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        })

        viewModel.mAllDetails?.observe(viewLifecycleOwner, Observer {
            binding.entryList.adapter = travelAdapter.apply {
                submitList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}