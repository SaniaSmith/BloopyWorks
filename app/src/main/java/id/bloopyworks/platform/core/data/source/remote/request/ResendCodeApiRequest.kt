package id.bloopyworks.platform.core.data.source.remote.request

data class ResendCodeApiRequest(
    val token : String,
    val email : String
)
