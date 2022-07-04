package id.bloopyworks.platform.core.data.source.remote

import android.util.JsonToken
import android.util.Log
import id.bloopyworks.platform.core.data.source.remote.network.ApiService
import id.bloopyworks.platform.core.data.source.remote.request.EmailVerificationApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.SignUpAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RemoteDataSource(
    private val apiService: ApiService
) {

    //Login User
    suspend fun loginUserNew(request : LoginAPIRequest) : Flow<Response<LoginApiNewResponse>> {
        return flow<Response<LoginApiNewResponse>> {
            try {
                val response = apiService.loginUser(request)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }

    //Authentication User
    suspend fun authenticationUser(token: String) : Flow<Response<GetAuthenticationUserResponse>> {
        return flow<Response<GetAuthenticationUserResponse>> {
            try {
                val response = apiService.authenticationUser("Bearer $token")
                emit(response)
            } catch (e: java.lang.Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }

    //Sign Up User
    suspend fun signUpUser(request : SignUpAPIRequest) : Flow<Response<SignUpAPIResponse>> {
        return flow<Response<SignUpAPIResponse>> {
            try {
                val response = apiService.signUpUser(request)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }

    //Login User
//    suspend fun loginUser(request : LoginAPIRequest) : Flow<Response<LoginAPIResponse>> {
//        return flow<Response<LoginAPIResponse>> {
//            try {
//                val response = apiService.loginUser(request)
//                emit(response)
//            } catch (e: Exception) {
//                Log.e("REMOTE", "Error: ${e.message}")
//            }
//        }
//    }

    //Logout User
    //Logout User
    suspend fun logoutUser(token: String) : Flow<Response<LogoutAPIResponse>> {
        return flow<Response<LogoutAPIResponse>> {
            try {
                val response = apiService.logoutUser(token)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }
}