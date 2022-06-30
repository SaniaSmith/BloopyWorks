package id.bloopyworks.platform.ui.landing.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val repo: bloopyRepository,
    private val dataStore: DataStoreRepository

) : ViewModel() {

    val data = MutableStateFlow<ResponseModel<Response<LoginAPIRequest>>>(ResponseModel.Idle("Idle State"))

    suspend fun login(request: LoginAPIRequest) {
        data.emit(ResponseModel.Loading())
        repo.loginUser(request).collect() {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    data.emit(ResponseModel.Success(it))
                } else {
                    data.emit(ResponseModel.Error(it.message()))
                }
            }
        }
    }

    fun saveTokenKey(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveTokenKey(token)
        }
    }