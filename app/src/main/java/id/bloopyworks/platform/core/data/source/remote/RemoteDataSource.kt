package id.bloopyworks.platform.core.data.source.remote

import android.util.Log
import id.bloopyworks.platform.core.data.source.remote.network.ApiService
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RemoteDataSource(
    private val apiService: ApiService
) {

    //Login User
    suspend fun loginUser(request : LoginAPIRequest) : Flow<Response<LoginAPIResponse>> {
        return flow<Response<LoginAPIResponse>> {
            try {
                val response = apiService.loginUser(request)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }
}