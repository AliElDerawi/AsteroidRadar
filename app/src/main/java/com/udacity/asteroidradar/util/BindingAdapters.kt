package com.udacity.asteroidradar.util

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidApiStatus
import com.udacity.asteroidradar.api.models.AsteroidModel
import com.udacity.asteroidradar.api.models.ImageOfTodayModel
import com.udacity.asteroidradar.features.main.AsteroidItemAdapter

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(isHazardous: Boolean) {
    if (isHazardous) {
        setImageResource(R.drawable.ic_status_potentially_hazardous)
        contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        setImageResource(R.drawable.ic_status_normal)
        contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("text")
fun TextView.setContext(text: String) {
    this.text = text
    contentDescription = text
}

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<AsteroidModel>?) {
    val adapter = adapter as AsteroidItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(isHazardous: Boolean) {
    if (isHazardous) {
        setImageResource(R.drawable.asteroid_hazardous)
        contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        setImageResource(R.drawable.asteroid_safe)
        contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun TextView.bindTextViewToAstronomicalUnit(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.astronomical_unit_format), number)
    contentDescription = text
}

@BindingAdapter("kmUnitText")
fun TextView.bindTextViewToKmUnit(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.km_unit_format), number)
    contentDescription = text
}

@BindingAdapter("velocityText")
fun TextView.bindTextViewToDisplayVelocity(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.km_s_unit_format), number)
    contentDescription = text
}

@BindingAdapter("imageOfToday", "progressBar")
fun ImageView.setImageOfToday(imageOfTodayModel: ImageOfTodayModel?, progress: ProgressBar) {
    if (imageOfTodayModel != null && imageOfTodayModel.mediaType == Constants.MEDIA_TYPE_IMAGE) {
        contentDescription = String.format(
            context.getString(R.string.nasa_picture_of_day_content_description_format),
            imageOfTodayModel.title
        )
        progress.visibility = View.VISIBLE
        Picasso.with(this.context).load(imageOfTodayModel.url)
            .into(this, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progress.visibility = View.GONE
                }

                override fun onError() {
                    progress.visibility = View.GONE
                }
            })
    } else {
        contentDescription =
            context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }

}

@BindingAdapter("loadingStatus")
fun ProgressBar.setLoadingStatus(asteroidApiStatus: AsteroidApiStatus?) {
    if (asteroidApiStatus != null) {
        visibility = when (asteroidApiStatus) {
            AsteroidApiStatus.LOADING -> {
                View.VISIBLE
            }

            AsteroidApiStatus.DONE -> {
                View.GONE
            }

            else -> {
                View.GONE
            }
        }
    }
}
