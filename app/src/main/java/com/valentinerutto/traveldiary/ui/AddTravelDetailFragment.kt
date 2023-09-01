package com.valentinerutto.traveldiary.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.auth.FirebaseAuth
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.data.model.SelectedImages
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.databinding.FragmentAddTravelDetailBinding
import com.valentinerutto.traveldiary.util.ImageUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTravelDetailFragment : Fragment() {

    private var _binding: FragmentAddTravelDetailBinding? = null
    val viewmodel by sharedViewModel<TravelViewModel>()
    private lateinit var imageAdapter: SelectedImageAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isAllFabsVisible: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude: Double? = null
    var longitude: Double? = null
    var selectedImages = mutableListOf<Uri>()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkPermissions()

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
        imageAdapter = SelectedImageAdapter()

        binding.imageList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewmodel.selectedPhotos.observe(viewLifecycleOwner, Observer { images ->
            selectedImages.addAll(images)
            val selectedimages = images.map { SelectedImages(uri = it) }
            binding.imageList.adapter = imageAdapter.apply {
                submitList(selectedimages)
            }
        })
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        val locationRequest = LocationRequest.create() // Create location request.
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // Set priority.

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude

            } else {
                getCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token
        ).addOnSuccessListener {
            val location: Location = it
            latitude = location.latitude
            longitude = location.longitude

            viewmodel._currentLocation.value = " $longitude , $latitude"

        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED -> {
                getLastLocation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showAlertDialog(
                )
            }

            else -> {
                locationPermissionLauncher.launch(permissionsList)
            }
        }


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

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation()
        } else {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Location Permission is Required")
            builder.setMessage("Location Permission is required for better .... Please allow location in settings...")

            // Positive Button
            builder.setPositiveButton("Enable") { dialog: DialogInterface, which: Int ->
                openAppSettings()
            }

            // Negative Button
            builder.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
    private val appSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED
        ) {
            getLastLocation()
        }
    }

    private fun openAppSettings() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        appSettingsLauncher.launch(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun setOnEventClickListener() {
        binding.saveFab.setOnClickListener {
            addEntry()
        }

        binding.addPicFab.setOnClickListener {
            multiplePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.addLocFab.setOnClickListener {

            viewmodel.currentLocation.observe(viewLifecycleOwner) { location ->
                Toast.makeText(requireContext(), location, Toast.LENGTH_LONG).show()

//                if (location.isNullOrEmpty()) {
//                    Toast.makeText(
//                        requireActivity(), getString(R.string.turn_on_gps), Toast.LENGTH_LONG
//                    ).show()
//                } else {
                    binding.txtLocation.setText(  "Location: $location")
              //  }
            }

        }

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
        val location = binding.txtLocation.text.toString().trim()

        if (TextUtils.isEmpty(title)) {
            binding.entryDetailsTitle.error = getString(R.string.title_cannot_be_empty)
            binding.entryDetailsTitle.requestFocus()
        } else if (TextUtils.isEmpty(description)) {
            binding.entryDetailsDescription.error = getString(R.string.description_cannot_be_empty)
            binding.entryDetailsDescription.requestFocus()
        } else if (selectedImages.isEmpty()) {
            Toast.makeText(requireContext(), "Enter photos", Toast.LENGTH_LONG).show()
        }
//        else if (location.isEmpty()) {
//            Toast.makeText(requireContext(), "Enter Location", Toast.LENGTH_LONG).show()
//        }
        else {
            val entry = TravelDetailsEntity(
                id = 0,
                date = date,
                location = location,
                photo =  selectedImages[0].toString(),
                title = title,
                notes = description
            )
            viewmodel.insertDetails(entry)
            clearEntryFields()
            findNavController().navigate(R.id.action_AddEntryFragment_to_FirstFragment)
        }
    }

    private fun clearEntryFields() {

        binding.entryDetailsTitle.text.clear()
        binding.entryDetailsDescription.text.clear()
        binding.txtDateCreated.text = ""
        viewmodel._selectedPhotos.value= emptyList()
        selectedImages.clear()

    }

    private fun getFormattedDate(): String {
        val dateTime = Date()
        val pattern = "MMM d, yyyy h:mma"
        val locale = Locale.ENGLISH
        val sdf = SimpleDateFormat(pattern, locale)
        return sdf.format(dateTime)
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Location Permission is Required")
        builder.setMessage("Location Permission is required for better .... Please allow location in settings...")

        // Positive Button
        builder.setPositiveButton("Enable") { dialog: DialogInterface, which: Int ->
            locationPermissionLauncher.launch(permissionsList)

        }

        // Negative Button
        builder.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val permissionsList = Manifest.permission.ACCESS_COARSE_LOCATION

        @JvmStatic
        fun newInstance() = AddTravelDetailFragment()
    }
}