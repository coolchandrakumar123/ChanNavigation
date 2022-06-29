package com.chan.channavigation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.chan.channavigation.R


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var reflectionClassVariable: Any? = null

    data class Ticket(
        val ticketId: Int,
        val subject: String
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        requireView().findViewById<Button>(R.id.button).setOnClickListener {
            /*requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, ToolbarFragment.newInstance())
                .addToBackStack("toolBarFragment")
                .commit()*/
            navigateToPlant()
        }
    }

    private fun navigateToPlant() {
        requireView().findNavController().navigate("toolbarFragment")
    }

}