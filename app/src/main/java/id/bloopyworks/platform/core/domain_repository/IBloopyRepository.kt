package id.bloopyworks.platform.core.domain_repository

import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IBloopyRepository {

    //Login
    suspend fun loginUser(request: LoginAPIRequest): Flow<Response<LoginAPIResponse>>

}