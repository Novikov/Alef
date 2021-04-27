package com.app.alef.ui.preview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.alef.BaseApplication
import com.app.alef.R
import com.app.alef.di.view_models.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreviewFragment : Fragment() {

    private lateinit var viewModel: PreviewViewModel
    private lateinit var itemsRecyclerView: RecyclerView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: PreviewItemsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BaseApplication).appComponent.getPreviewComponent()
            .create(this.requireContext()).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, providerFactory).get(PreviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_preview, container, false)

        itemsRecyclerView = view.findViewById(R.id.items_recycler_view)
        itemsRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), 3)
        itemsRecyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, {
           adapter.dataSource = it
        })

        return view
    }

    companion object {
        private const val TAG = "PreviewFragment"
    }

}