package com.example.astonlesson2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.astonlesson2.databinding.FragmentAuthorisationBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AuthorisationFragment : Fragment() {

    private var _binding: FragmentAuthorisationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorisationBinding.inflate(inflater, container, false)
        return  _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()

    }

    private fun checkIfLoginAndPasswordEmpty(): Boolean{
        var isEmpty = false
        if(binding.tieLogin.text.isNullOrEmpty() || binding.tiePassword.text.isNullOrEmpty())
            isEmpty = true
        return isEmpty
    }

    private fun setUpListeners(){
        binding.tieLogin.doOnTextChanged { _, _, _, _ ->
            changeButtonState()
            hideError(binding.tilLogin)

        }
        binding.tiePassword.doOnTextChanged { _, _, _, _ ->
            changeButtonState()
            hideError(binding.tilPassword)

        }
        binding.btnEntry.setOnClickListener {
            if(binding.tieLogin.text.toString() != LOGIN){
                showError(binding.tilLogin)
            }
            else if(binding.tiePassword.text.toString() != PASSWORD){
                showError(binding.tilPassword)
            } else{
                binding.progressBar.visibility = View.VISIBLE
                MainScope().launch {
                    delay(2000)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun changeButtonState() {
        val enabled = !checkIfLoginAndPasswordEmpty()
        if(enabled){
            with(binding.btnEntry){
                alpha = 1f
                isClickable = true
            }
        }else{
            with(binding.btnEntry){
                alpha = 0.5f
                isClickable = false
            }
        }
    }

    private fun showError(textInputLayout: TextInputLayout) {
        when(textInputLayout.id){
            binding.tilLogin.id -> {
                textInputLayout.error = getString(R.string.login_error)
            }
            binding.tilPassword.id -> {
                textInputLayout.error = getString(R.string.password_error)
            }
        }
    }

    private fun hideError(textInputLayout: TextInputLayout) {
        when(textInputLayout.id){
            binding.tilLogin.id -> {
                textInputLayout.error = EMPTY_STRING
            }
            binding.tilPassword.id -> {
                textInputLayout.error = EMPTY_STRING
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val LOGIN = "User"
        private const val PASSWORD = "12345"

        fun newInstance() = AuthorisationFragment()
    }
}