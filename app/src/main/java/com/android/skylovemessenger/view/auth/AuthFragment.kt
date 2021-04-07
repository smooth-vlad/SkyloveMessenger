package com.android.skylovemessenger.view.auth

import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.skylovemessenger.R
import com.android.skylovemessenger.databinding.FragmentAuthBinding
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.User
import com.android.skylovemessenger.view.user_chats.UserChatsViewModelFactory


private const val TAG = "AuthFragment"

class AuthFragment : Fragment() {

    lateinit var viewModel: AuthViewModel

    private var users: List<User>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAuthBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_auth,
            container,
            false
        )

        val spinner = binding.usersSpinner

        val db = MessengerDatabase.getInstance(requireContext())!!
        val viewModelFactory = AuthViewModelFactory(db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.users.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewModel.create3Users()
            }
        }

        viewModel.users.observe(viewLifecycleOwner) {
            users = it
            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it.map { "${it.name} (id = ${it.userId})" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.authButton.setOnClickListener {
            val position = spinner.selectedItemPosition
            users?.let {
                val userId = it[position].userId
                val action = AuthFragmentDirections.actionAuthFragmentToUserChatsFragment(userId)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}