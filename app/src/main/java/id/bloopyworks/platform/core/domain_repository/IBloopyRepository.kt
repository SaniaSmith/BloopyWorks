package id.bloopyworks.platform.core.domain_repository

import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import id.bloopyworks.platform.core.data.source.remote.response.LoginApiNewResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IBloopyRepository {

    //Login
//    suspend fun loginUser(request: LoginAPIRequest): Flow<Response<LoginAPIResponse>>

    //Login Baru
    suspend fun loginUserNew(request: LoginAPIRequest): Flow<Response<LoginApiNewResponse>>

}