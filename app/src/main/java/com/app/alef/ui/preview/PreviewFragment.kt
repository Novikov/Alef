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
            .create().inject(this)
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

        viewModel.items.observe(viewLifecycleOwner, {
            Log.i(TAG, "converted data $it")
        })

        return view
    }

    companion object {
        private const val TAG = "PreviewFragment"
    }

}