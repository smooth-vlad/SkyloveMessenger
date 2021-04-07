package com.android.skylovemessenger.view.auth

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.skylovemessenger.R
import com.android.skylovemessenger.databinding.FragmentAuthBinding
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


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
                GlobalScope.launch {
                    db.userDao()
                        .insert(
                            User(0, "vladOS", getByteArrayFromDrawable(R.drawable._36_man))
                        )
                    db.userDao()
                        .insert(User(0, "mary_s", getByteArrayFromDrawable(R.drawable._47_woman)))
                    db.userDao()
                        .insert(User(0, "van4el", getByteArrayFromDrawable(R.drawable._42_man)))
                }
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

    fun getByteArrayFromDrawable(drawableId: Int): ByteArray {
        val bitmap1 = (resources.getDrawable(drawableId, null) as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap1.compress(Bitmap.CompressFormat.PNG, 25, stream)
        return stream.toByteArray()
    }
}