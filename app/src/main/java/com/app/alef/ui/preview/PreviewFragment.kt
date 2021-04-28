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
import com.app.alef.data.repository.NetworkState
import com.app.alef.di.view_models.ViewModelProviderFactory
import com.app.alef.ui.activity.Activity
import com.squareup.picasso.Picasso
import javax.inject.Inject


class PreviewFragment : Fragment() {
    private var activityContract: Activity? = null
    private lateinit var viewModel: PreviewViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var picasso: Picasso

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            activityContract = context as Activity
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "Activity have to implement interface Activity")
        }

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
        val view = inflater.inflate(R.layout.fragment_preview, container, false)
        val itemsRecyclerView: RecyclerView = view.findViewById(R.id.items_recycler_view)

        val orientation = resources.configuration.orientation
        when (orientation) {
            1 -> {
                activityContract?.showActionBar()
            }
            2 -> {
                activityContract?.hideActionBar()
            }
        }

        val isTablet = resources.getBoolean(R.bool.isTablet)
        val spanCount = if (isTablet) {
            3
        } else {
            2
        }

        itemsRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), spanCount)
        val adapter = PreviewItemsAdapter(picasso, this.requireContext(), spanCount)
        itemsRecyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.dataSource = it
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            when (it) {
                NetworkState.LOADING -> {
                    activityContract?.showProgressBar()
                }
                NetworkState.LOADED -> {
                    activityContract?.hideProgressBar()
                }
                NetworkState.NO_INTERNET -> {
                    activityContract?.hideProgressBar()
                    activityContract?.showErrorMessage(NetworkState.NO_INTERNET.msg)
                }
                NetworkState.ERROR -> {
                    activityContract?.hideProgressBar()
                    activityContract?.showErrorMessage(NetworkState.ERROR.msg)
                }
            }
        })

        return view
    }

    companion object {
        private const val TAG = "PreviewFragment"
    }

}