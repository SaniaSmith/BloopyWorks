package id.bloopyworks.platform.ui.landing.emailVerif

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.network.ApiService
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.request.EmailVerificationApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.ResendCodeApiRequest
import id.bloopyworks.platform.core.data.source.remote.response.EmailVerifApiNewResponse
import id.bloopyworks.platform.core.data.source.remote.response.GetAuthenticationUserResponse
import id.bloopyworks.platform.core.data.source.remote.response.LoginApiNewResponse
import id.bloopyworks.platform.core.data.source.remote.response.ResendCodeApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response

class EmailVerificationViewModel (
    private val apiService: ApiService
) : ViewModel() {

    val data =
        MutableStateFlow<ResponseModel<Response<EmailVerifApiNewResponse>>>(ResponseModel.Idle("Idle State"))
    val code = MutableStateFlow<ResponseModel<Response<ResendCodeApiResponse>>>(ResponseModel.Idle("Idle State"))

    suspend fun verifEmailUser(request: EmailVerificationApiRequest): Flow<Response<EmailVerifApiNewResponse>> {
        return flow<Response<EmailVerifApiNewResponse>> {
            try {
                val response = apiService.verificationEmail(request)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }

    suspend fun verifEmailUserViewModel(request: EmailVerificationApiRequest) {
        data.emit(ResponseModel.Loading())
        verifEmailUser(request).collect() {
            if (it.isSuccessful) {
                data.emit(ResponseModel.Success(it))
            } else {
                data.emit(ResponseModel.Error(it.message()))
            }
        }
    }

    //Resend Code
    suspend fun resendCode(request: ResendCodeApiRequest): Flow<Response<ResendCodeApiResponse>> {
        return flow<Response<ResendCodeApiResponse>> {
            try {
                val response = apiService.resendCode(request)
                emit(response)
            } catch (e: Exception) {
                Log.e("REMOTE", "Error: ${e.message}")
            }
        }
    }

    suspend fun resendCodeViewModel(request: ResendCodeApiRequest) {
        code.emit(ResponseModel.Loading())
        resendCode(request).collect() {
            if (it.isSuccessful) {
                code.emit(ResponseModel.Success(it))
            } else {
                code.emit(ResponseModel.Error(it.message()))
            }
        }
    }
}