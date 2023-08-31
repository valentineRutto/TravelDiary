package com.valentinerutto.traveldiary.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.databinding.FragmentAddTravelDetailBinding
import com.valentinerutto.traveldiary.util.ImageUtil.getImageFromUri
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTravelDetailFragment : Fragment() {

    private var _binding: FragmentAddTravelDetailBinding? = null
    val viewmodel by sharedViewModel<TravelViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isAllFabsVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        setVisibilityFalse()

        viewmodel.selectedPhotos.observe(viewLifecycleOwner, Observer {
            binding.img.load(it[0])
            binding.txtImgSize.text = requireContext().getString(R.string.photos_selected, it.size )
        })
    }

    private val multiplePhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            if (uris.isNotEmpty()) {
                viewmodel._selectedPhotos.value = uris
                Toast.makeText(
                    requireActivity(), "Media selected", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireActivity(), "No media selected", Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun setOnEventClickListener() {
        binding.saveFab.setOnClickListener {
            addEntry()
        }

        binding.addPicFab.setOnClickListener {
            multiplePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.addLocFab.setOnClickListener { }
        binding.moreActionFab.setOnClickListener {
            setActionFabVisibility(isAllFabsVisible)
        }

    }

    private fun setActionFabVisibility(isActionsVisible: Boolean) {

        if (!isActionsVisible) {
            setVisibilityTrue()
        } else {
            setVisibilityFalse()
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

    private fun setVisibilityFalse() {
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

        }

    }
}