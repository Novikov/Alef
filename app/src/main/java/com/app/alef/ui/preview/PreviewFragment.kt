package com.app.alef.ui.preview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.alef.BaseApplication
import com.app.alef.R
import com.app.alef.di.view_models.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject


class PreviewFragment : Fragment() {

    private lateinit var viewModel: PreviewViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var picasso: Picasso

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
        val itemsRecyclerView:RecyclerView = view.findViewById(R.id.items_recycler_view)

        val isTablet = resources.getBoolean(R.bool.isTablet)
        val spanCount = if (isTablet) {
            3
        } else {
            2
        }

        itemsRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), spanCount)
        val adapter = PreviewItemsAdapter(picasso, this.requireContext(),spanCount)
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