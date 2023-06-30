package com.example.innobuzztask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import androidx.navigation.Navigation
import com.example.innobuzztask.databinding.FragmentAccessibilityPermissionBinding


class AccessibilityPermissionFragment : Fragment() {

    lateinit var binding: FragmentAccessibilityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isAccessibilityPermissionGranted(requireContext())) {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_accessibilityPermissionFragment_to_postListFragment) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccessibilityPermissionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPermission.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
        binding.tvSkip.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_accessibilityPermissionFragment_to_postListFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        if(context?.let { isAccessibilityPermissionGranted(it) } == true) {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_accessibilityPermissionFragment_to_postListFragment) }
        }
    }
    private fun isAccessibilityPermissionGranted(context: Context): Boolean {
        val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val packageName = context.packageName

        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )

        return enabledServices?.contains(packageName) == true && accessibilityManager.isEnabled
    }
}