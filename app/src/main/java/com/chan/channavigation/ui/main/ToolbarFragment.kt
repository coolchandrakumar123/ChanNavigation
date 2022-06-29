package com.chan.channavigation.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import com.chan.channavigation.R


class ToolbarFragment : Fragment() {

    companion object {
        fun newInstance() = ToolbarFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.toolbar_fragment, container, false)
        toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "App ToolBar"
        (requireActivity() as? AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        toolbar.addView(TextView(requireContext()).apply {
            //setImageResource(android.R.drawable.ic_menu_compass)
            text = "En"
            textSize = 20f
            layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.END
            }
        })
        /*toolbar.addView(ImageView(requireContext()).apply {
            setImageResource(android.R.drawable.ic_menu_compass)
            layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.END
            }
        })*/
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(0, 101, 0, "edit").apply {
            setIcon(android.R.drawable.ic_menu_edit)
            setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        menu.add(0, 102, 1, "delete").apply {
            setIcon(android.R.drawable.ic_menu_delete)
            setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }


        menu.add(0, 103, 2, "More").apply {
            setIcon(android.R.drawable.ic_menu_edit)
            setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        menu.add(0, 104, 3, "Settings").apply {
            setIcon(android.R.drawable.ic_menu_edit)
            setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val text = when(item.itemId) {
            103 -> "More"
            104 -> "Settings"
            else -> ""
        }
        Toast.makeText(requireContext(), "$text Clicked", Toast.LENGTH_SHORT).show()
        return true
    }

}