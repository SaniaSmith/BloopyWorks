package id.bloopyworks.platform.core.data

import id.bloopyworks.platform.core.data.source.remote.RemoteDataSource
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.LoginAPIResponse
import id.bloopyworks.platform.core.domain_repository.IBloopyRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class bloopyRepository(
    private val remoteDataSource: RemoteDataSource
) : IBloopyRepository {

    //Login
    override suspend fun loginUser(request: LoginAPIRequest) : Flow<Response<LoginAPIResponse>> {
        return remoteDataSource.loginUser(request)
    }

}