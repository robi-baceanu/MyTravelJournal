package com.example.mytraveljournal.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraveljournal.R
import com.example.mytraveljournal.data.TripViewModel
import com.example.mytraveljournal.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        val buttonAdd: FloatingActionButton = binding.fabAddTrip
        buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_add)
        }

        val recyclerViewTrips: RecyclerView = binding.recyclerViewTrips

        recyclerViewTrips.layoutManager = LinearLayoutManager(this.context)

        val myAdapter = MyAdapter()
        recyclerViewTrips.adapter = myAdapter

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        mTripViewModel.readAllData.observe(viewLifecycleOwner, Observer { tripList ->
            myAdapter.setData(tripList)
        })

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllTrips()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTrips() {
        val builder = AlertDialog.Builder(this.context)
        builder.setPositiveButton("Yes") { _, _ ->
            mTripViewModel.deleteAllTrips()
            Toast.makeText(this.context, "All trips deleted successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all trips")
        builder.setMessage("Are you sure you want to delete all trips?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}