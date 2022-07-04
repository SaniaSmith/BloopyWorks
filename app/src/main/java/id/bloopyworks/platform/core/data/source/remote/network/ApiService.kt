package id.bloopyworks.platform.core.data.source.remote.network

import id.bloopyworks.platform.core.data.source.remote.request.EmailVerificationApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.ResendCodeApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.SignUpAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //Login User
//    @POST("passport/login")
//    suspend fun loginUser(
//        @Body body: LoginAPIRequest
//    ): Response<LoginAPIResponse>

    //Login User Baru
    @POST("passport/login")
    suspend fun loginUser(
        @Body body: LoginAPIRequest
    ): Response<LoginApiNewResponse>

    //Authenticated User
    @GET("passport/user")
    suspend fun authenticationUser(
        @Header("Authorization") token: String
    ): Response<GetAuthenticationUserResponse>

    //Sign Up User
    @POST("passport/register?platform=works&app=mobile")
    suspend fun signUpUser(
        @Body body: SignUpAPIRequest
    ): Response<SignUpAPIResponse>

    //Verify Email Code
//    @POST("passport/verify-email/")
//    suspend fun verifEmail(
//        @Query("user_id") userId: Int?,
//        @Query("verification_token") verificationToken: String?,
//        @Query("token") token: String?
//    ): Response<SignUpAPIResponse>

    //Verify Email Code Baru
    @POST("passport/verify-email?type=code&platform=works&app=mobile")
    suspend fun verificationEmail(
        @Body body: EmailVerificationApiRequest
    ): Response<EmailVerifApiNewResponse>

    //Resend Verify Code
    @POST("/passport/email/verification-notification?type=code&platform=works&app=mobile")
    suspend fun resendCode(
        @Header("tokenBody") body : ResendCodeApiRequest
    ): Response<ResendCodeApiResponse>

    //Logout User
    @POST("/passport/logout")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ) : Response<LogoutAPIResponse>
}