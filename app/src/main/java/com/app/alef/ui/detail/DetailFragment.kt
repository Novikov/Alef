package com.app.alef.ui.detail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.app.alef.BaseApplication
import com.app.alef.R
import com.app.alef.ui.activity.Activity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailFragment : Fragment() {
    var uri:Uri? = null
    private var activityContract: Activity? = null

    @Inject
    lateinit var picasso: Picasso

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            activityContract = context as Activity
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "Activity have to implement interface Activity")
        }

        (requireActivity().application as BaseApplication).appComponent.getDetailComponent()
            .create().inject(this)

        if (arguments != null) {
            val args = DetailFragmentArgs.fromBundle(requireArguments())
            uri = Uri.parse(args.url)
        } else {
            throw Exception("arguments can't be null")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val orientation = resources.configuration.orientation
        when(orientation){
            1 -> {
                activityContract?.showActionBar()
            }
            2-> {
                activityContract?.hideActionBar()
            }
        }

        val itemDetailView = view.findViewById<ImageView>(R.id.item_detail_view)

        picasso.load(uri).into(itemDetailView)

        return view;
    }
}