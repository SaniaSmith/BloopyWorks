package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpAPIResponse(

	@field:SerializedName("data")
	val data: DataSignUp,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class User(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("verification_token")
	val verificationToken: String
)

data class DataSignUp(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)
