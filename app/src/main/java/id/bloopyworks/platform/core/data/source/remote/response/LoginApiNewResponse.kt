package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginApiNewResponse(

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataLogin(

	@field:SerializedName("is_verified")
	val isVerified: Boolean,

	@field:SerializedName("token")
	val token: String
)
