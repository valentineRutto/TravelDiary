package com.valentinerutto.traveldiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.load
import com.valentinerutto.traveldiary.databinding.FragmentSecondBinding
import com.valentinerutto.traveldiary.ui.TravelViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TravelDetailsFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    val viewmodel by sharedViewModel<TravelViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.selectedTravelDetails.observe(viewLifecycleOwner, Observer {
            binding.txtTitle.text = it?.title
            binding.txtDate.text = it?.date
            binding.txtDesc.text = it?.notes
            binding.txtLoc.text = it?.location
            binding.ivImage.load(it?.photo)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}