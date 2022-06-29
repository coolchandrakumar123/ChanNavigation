package com.chan.chantoolbar.ui.main

import android.graphics.Color
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.chan.chantoolbar.R
import android.widget.Toast


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        requireView().findViewById<Button>(R.id.button).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, ToolbarFragment.newInstance())
                .addToBackStack("toolBarFragment")
                .commit()
        }
        // TODO: Use the ViewModel

        requireView().findViewById<ImageView>(R.id.deleteIcon).setOnLongClickListener {
            showToastAboveButton(it, "delete")
            /*val snackbar = Snackbar.make(it, "Snackbar over BottomAppBar", Snackbar.LENGTH_LONG)
            snackbar.anchorView = it
            snackbar.show()*/
            true
        }

        requireView().findViewById<ImageView>(R.id.moreIcon).setOnClickListener {
            //checkReflection()
            it.showPopUpMenu()
        }
    }

    private fun checkReflection() {
        reflectionClassVariable = Ticket(
            ticketId = 101,
            subject = "This is Ticket Title..."
        )
        reflectionClassVariable?.apply {
            val field = this::class.java.getDeclaredField("subject")
            field.isAccessible = true
            Log.d("ChanLog", "checkReflection: ${field.get(this)}")
        }
    }

    private fun View.showPopUpMenu() {
        val popupMenu = PopupMenu(requireContext(), this)
        popupMenu.menu.apply {
            add(0, 103, 2, "More")
            add(0, 104, 3, "Settings")
        }
        popupMenu.setOnMenuItemClickListener {
            val text = when(it.itemId) {
                103 -> "More"
                104 -> "Settings"
                else -> ""
            }
            Toast.makeText(requireContext(), "$text Clicked", Toast.LENGTH_SHORT).show()
            true
        }
        popupMenu.show()
    }

    private fun showToastAboveButton(infoView: View, message: String) {
        var xOffset = 0
        var yOffset = 0
        val gvr = Rect()
        val parentHeight = (infoView.parent as View).height
        if (infoView.getGlobalVisibleRect(gvr)) {
            val root = infoView.rootView
            val halfWidth = root.right / 2
            val halfHeight = root.bottom / 2
            val parentCenterX: Int = (gvr.right - gvr.left) / 2 + gvr.left
            val parentCenterY: Int = (gvr.bottom - gvr.top) / 2 + gvr.top
            yOffset = if (parentCenterY <= halfHeight) {
                -(halfHeight - parentCenterY) - parentHeight
            } else {
                parentCenterY - halfHeight - parentHeight
            }

            yOffset += (2*infoView.bottom)

            if (parentCenterX < halfWidth) {
                xOffset = -(halfWidth - parentCenterX)
            }
            if (parentCenterX >= halfWidth) {
                xOffset = parentCenterX - halfWidth
            }
        }
        val context = infoView.context
        val child = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(10, 10, 10, 10)
            setBackgroundColor(Color.BLACK)
        }
        child.addView(TextView(context).apply {
            text = message
            setTextColor(Color.WHITE)
        })
        Toast(context).apply {
            this.duration = Toast.LENGTH_SHORT
            this.view = child
            setGravity(Gravity.CENTER, xOffset, yOffset)
        }.show()
        /*val toast: Toast = Toast.makeText(activity, messageId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, xOffset, yOffset)
        toast.show()*/
    }

}