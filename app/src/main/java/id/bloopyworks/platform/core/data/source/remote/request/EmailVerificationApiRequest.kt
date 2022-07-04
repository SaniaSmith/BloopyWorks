package id.bloopyworks.platform.core.data.source.remote.request

data class EmailVerificationApiRequest(
    val user_id : Int,
    val verification_token : String,
    val code : String
)
