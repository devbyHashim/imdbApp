package com.example.imdb.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImdbConfiguration(

	@field:SerializedName("images")
	val imagesConfiguration: ImagesConfiguration? = null,

	@field:SerializedName("change_keys")
	val changeKeys: List<String>? = null
) : Parcelable

@Parcelize
data class ImagesConfiguration(

	@field:SerializedName("poster_sizes")
	val posterSizes: List<String>? = null,

	@field:SerializedName("secure_base_url")
	val secureBaseUrl: String? = null,

	@field:SerializedName("backdrop_sizes")
	val backdropSizes: List<String>? = null,

	@field:SerializedName("base_url")
	val baseUrl: String? = null,

	@field:SerializedName("logo_sizes")
	val logoSizes: List<String>? = null,

	@field:SerializedName("still_sizes")
	val stillSizes: List<String>? = null,

	@field:SerializedName("profile_sizes")
	val profileSizes: List<String>? = null
) : Parcelable{
	val posterSizeLength = if (posterSizes?.size == null ) 0  else posterSizes.size
}
