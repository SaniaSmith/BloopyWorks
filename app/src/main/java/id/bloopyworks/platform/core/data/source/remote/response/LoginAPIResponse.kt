package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginAPIResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("token")
	val token: String
)
