package id.bloopyworks.platform.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetAuthenticationUserResponse(

	@field:SerializedName("data")
	val data: DataUser,

	@field:SerializedName("status")
	val status: String
)

data class DataUser(

	@field:SerializedName("user_identityNumber")
	val userIdentityNumber: Any,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("user_username")
	val userUsername: Any,

	@field:SerializedName("user_identityType")
	val userIdentityType: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("user_gender")
	val userGender: Any,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String,

	@field:SerializedName("user_identityExpiryDate")
	val userIdentityExpiryDate: Any,

	@field:SerializedName("user_role")
	val userRole: String,

	@field:SerializedName("user_birthDate")
	val userBirthDate: Any,

	@field:SerializedName("user_birthPlace")
	val userBirthPlace: Any,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_phone")
	val userPhone: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
