package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutAPIResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
