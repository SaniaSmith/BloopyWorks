package id.bloopyworks.platform.core.data

import id.bloopyworks.platform.core.data.source.remote.RemoteDataSource
import id.bloopyworks.platform.core.data.source.remote.request.EmailVerificationApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.SignUpAPIRequest
import id.bloopyworks.platform.core.data.source.remote.response.*
import id.bloopyworks.platform.core.domain_repository.IBloopyRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class bloopyRepository(
    private val remoteDataSource: RemoteDataSource
) : IBloopyRepository {

    //Login
//    override suspend fun loginUser(request: LoginAPIRequest) : Flow<Response<LoginAPIResponse>> {
//        return remoteDataSource.loginUser(request)
//    }

    override suspend fun loginUserNew(request: LoginAPIRequest): Flow<Response<LoginApiNewResponse>> {
        return remoteDataSource.loginUserNew(request)
    }

    //Logout
    suspend fun logoutUser(token: String): Flow<Response<LogoutAPIResponse>> {
        return remoteDataSource.logoutUser(token)
    }

    //Authentication User
    suspend fun authenticationUser(token : String) : Flow<Response<GetAuthenticationUserResponse>> {
        return remoteDataSource.authenticationUser(token)
    }

    //SignUp
    suspend fun signUpUser(request: SignUpAPIRequest) : Flow<Response<SignUpAPIResponse>> {
        return remoteDataSource.signUpUser(request)
    }

}