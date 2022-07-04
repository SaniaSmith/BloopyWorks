package id.bloopyworks.platform.ui.landing.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.SignUpAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.GetAuthenticationUserResponse
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import id.bloopyworks.platform.core.data.source.remote.response.SignUpAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(
    private val repo: bloopyRepository,
) : ViewModel() {

    val data =
        MutableStateFlow<ResponseModel<Response<SignUpAPIResponse>>>(ResponseModel.Idle("Idle State"))

    suspend fun signUp(request: SignUpAPIRequest) {
        data.emit(ResponseModel.Loading())
        repo.signUpUser(request).collect() {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    data.emit(ResponseModel.Success(it))
                } else {
                    data.emit(ResponseModel.Error(it.message()))
                }
            }
        }
    }

//    fun saveTokenVerif(tokenVerif: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStore.saveTokenVerif(tokenVerif)
//        }
//    }
//
//    fun saveUserID(userId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStore.saveUserId(userId)
//        }
//    }
}