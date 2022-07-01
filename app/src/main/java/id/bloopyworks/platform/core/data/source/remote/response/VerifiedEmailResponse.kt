package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VerifiedEmailResponse(

	@field:SerializedName("message")
	val message: String
)
