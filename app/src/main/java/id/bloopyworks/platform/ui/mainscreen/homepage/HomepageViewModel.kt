package id.bloopyworks.platform.ui.mainscreen.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.response.LogoutAPIResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response

class HomepageViewModel(
    private val repo: bloopyRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    suspend fun deleteToken() {
        dataStore.deleteTokenKey()
    }

    val token : String = ""

    val responseLogout = MutableStateFlow<ResponseModel<Response<LogoutAPIResponse>>>(ResponseModel.Idle("Idle State"))

    suspend fun logoutUser(token: String) {
        responseLogout.emit(ResponseModel.Loading())
        repo.logoutUser(token).collect {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    responseLogout.emit(ResponseModel.Success(it))
                } else {
                    responseLogout.emit(ResponseModel.Error(it.message()))
                }
        }
        }
    }
}