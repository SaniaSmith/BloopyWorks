package id.bloopyworks.platform.ui.landing.getStarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.response.GetAuthenticationUserResponse
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response

class GetStartedViewModel (
    val repo: bloopyRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    suspend fun deleteToken() {
        dataStore.deleteTokenKey()
    }

    val getToken = dataStore.getTokenKey.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val responseAuthenticationUser = MutableStateFlow<ResponseModel<Response<GetAuthenticationUserResponse>>>(ResponseModel.Idle("Idle State"))

    suspend fun authenticationUser(token: String) {
        responseAuthenticationUser.emit(ResponseModel.Loading())
        repo.authenticationUser(token).collect {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    responseAuthenticationUser.emit(ResponseModel.Success(it))
                } else {
                    responseAuthenticationUser.emit(ResponseModel.Error(it.message()))
                }
            }
        }
    }
}