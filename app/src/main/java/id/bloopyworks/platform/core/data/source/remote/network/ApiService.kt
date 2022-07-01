package id.bloopyworks.platform.core.data.source.remote.network

import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.GetAuthenticationUserResponse
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    //Login User
    @POST("passport/login")
    suspend fun loginUser(
        @Body body: LoginAPIRequest
    ): Response<LoginAPIResponse>

    //Authenticated User
    @GET("passport/user")
    suspend fun authenticationUser(
        @Header("Authorization") token: String
    ): Response<GetAuthenticationUserResponse>
}