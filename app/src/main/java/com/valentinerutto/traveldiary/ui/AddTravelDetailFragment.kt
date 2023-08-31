package com.valentinerutto.traveldiary.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.databinding.FragmentAddTravelDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTravelDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTravelDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddTravelDetailBinding? = null
    val viewmodel by sharedViewModel<TravelViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isAllFabsVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTravelDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtDateCreated.text = getFormattedDate()
        setOnEventClickListener()
        setVisibilityfalse()
    }

    private fun setOnEventClickListener() {
        binding.saveFab.setOnClickListener {
            addEntry()
        }

        binding.addPicFab.setOnClickListener { }
        binding.addLocFab.setOnClickListener { }
        binding.moreActionFab.setOnClickListener {
            setActionFabVisibility(isAllFabsVisible)
        }

    }

    private fun setActionFabVisibility(isActionsVisible: Boolean) {

        if (!isActionsVisible) {
            setVisibilityTrue()
        } else {
            setVisibilityfalse()
        }
    }

    private fun setVisibilityTrue() {
        binding.saveFab.show()
        binding.addLocFab.show()
        binding.addPicFab.show()

        binding.addLocActionText.isVisible = true
        binding.addPicActionText.isVisible = true
        binding.saveActionText.isVisible = true

        binding.moreActionFab.extend()
        isAllFabsVisible = true
    }

    private fun setVisibilityfalse() {
        binding.saveFab.hide()
        binding.addLocFab.hide()
        binding.addPicFab.hide()

        binding.addLocActionText.isVisible = false
        binding.addPicActionText.isVisible = false
        binding.saveActionText.isVisible = false

        binding.moreActionFab.shrink()

        isAllFabsVisible = false
    }

    private fun addEntry() {
        val title = binding.entryDetailsTitle.text.toString().trim()
        val description = binding.entryDetailsDescription.text.toString().trim()
        val date = binding.txtDateCreated.text.toString().trim()

        if (TextUtils.isEmpty(title)) {
            binding.entryDetailsTitle.error = getString(R.string.title_cannot_be_empty)
            binding.entryDetailsTitle.requestFocus()
        } else if (TextUtils.isEmpty(description)) {
            binding.entryDetailsDescription.error = getString(R.string.description_cannot_be_empty)
            binding.entryDetailsDescription.requestFocus()
        } else {
            findNavController().navigate(R.id.action_AddEntryFragment_to_FirstFragment)
        }

    }

    private fun getFormattedDate(): String {
        val dateTime = Date()
        val pattern = "MMM d, yyyy h:mma"
        val locale = Locale.ENGLISH
        val sdf = SimpleDateFormat(pattern, locale)
        return sdf.format(dateTime)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTravelDetailFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) = AddTravelDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}