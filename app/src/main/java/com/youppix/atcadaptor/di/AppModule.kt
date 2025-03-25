package com.youppix.atcadaptor.di

import android.app.Application
import android.telecom.Call.Details
import com.youppix.atcadaptor.data.manager.LocaleUserEntryManagerImpl
import com.youppix.atcadaptor.data.remote.auth.AuthService
import com.youppix.atcadaptor.data.remote.details.DetailsService
import com.youppix.atcadaptor.data.remote.home.HomeService
import com.youppix.atcadaptor.data.repository.details.DetailsRepositoryImpl
import com.youppix.atcadaptor.data.repository.forgotPassword.ForgotPasswordRepositoryImpl
import com.youppix.atcadaptor.data.repository.home.HomeRepositoryImpl
import com.youppix.atcadaptor.data.repository.login.LoginRepositoryImpl
import com.youppix.atcadaptor.data.repository.signUp.SignUpRepositoryImpl
import com.youppix.atcadaptor.domain.manager.LocaleUserEntryManager
import com.youppix.atcadaptor.domain.repository.details.DetailsRepository
import com.youppix.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import com.youppix.atcadaptor.domain.repository.home.HomeRepository
import com.youppix.atcadaptor.domain.repository.login.LoginRepository
import com.youppix.atcadaptor.domain.repository.signUp.SignUpRepository
import com.youppix.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.youppix.atcadaptor.domain.useCases.auth.CheckPasswordUseCase
import com.youppix.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import com.youppix.atcadaptor.domain.useCases.appEntry.GetAppEntryUseCase
import com.youppix.atcadaptor.domain.useCases.login.LoginUseCase
import com.youppix.atcadaptor.domain.useCases.login.LoginUseCases
import com.youppix.atcadaptor.domain.useCases.appEntry.SaveAppEntryUseCase
import com.youppix.atcadaptor.domain.useCases.details.DetailsUseCases
import com.youppix.atcadaptor.domain.useCases.details.GetDetailsUseCase
import com.youppix.atcadaptor.domain.useCases.forgotPassword.ForgotPasswordUseCases
import com.youppix.atcadaptor.domain.useCases.forgotPassword.ResetPasswordUseCase
import com.youppix.atcadaptor.domain.useCases.home.HomeSearchUseCase
import com.youppix.atcadaptor.domain.useCases.home.HomeUseCases
import com.youppix.atcadaptor.domain.useCases.signUp.AddUserUseCase
import com.youppix.atcadaptor.domain.useCases.signUp.CheckPhoneUseCase
import com.youppix.atcadaptor.domain.useCases.signUp.CheckUserNameUseCase
import com.youppix.atcadaptor.domain.useCases.signUp.SignUpUseCases
import com.youppix.atcadaptor.domain.useCases.signUp.VerifyCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Ktor Client
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
        val client = HttpClient(CIO) {

            install(ContentNegotiation) {
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000L
                connectTimeoutMillis = 30_000L
                socketTimeoutMillis = 30_000L
            }


            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }


        }
        return client
    }

    @Provides
    @Singleton
    fun provideLocaleUserEntryManager(application: Application): LocaleUserEntryManager =
        LocaleUserEntryManagerImpl(application)

    @Provides
    @Singleton
    fun provideLocaleUserEntryUseCases(localeUserEntryManager: LocaleUserEntryManager): AppEntryUseCases =
        AppEntryUseCases(
            getAppEntryUseCase = GetAppEntryUseCase(localeUserEntryManager),
            saveAppEntryUseCase = SaveAppEntryUseCase(localeUserEntryManager)
        )

    @Provides
    @Singleton
    fun provideAuthService(client: HttpClient): AuthService =
        AuthService(client)


    // Login
    @Provides
    @Singleton
    fun provideLoginValidatorRepository(authService: AuthService): LoginRepository =
        LoginRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideLoginUseCases(
        loginRepository: LoginRepository,
        localeUserEntryManager: LocaleUserEntryManager? = null,
    ): LoginUseCases {
        return LoginUseCases(
            checkEmail = CheckEmailUseCase(loginRepository),
            checkPassword = CheckPasswordUseCase(loginRepository),
            login = LoginUseCase(loginRepository),
            saveAppEntry = if (localeUserEntryManager != null) SaveAppEntryUseCase(
                localeUserEntryManager
            ) else null
        )
    }


    // SignUp
    @Provides
    @Singleton
    fun provideSignUpRepository(authService: AuthService): SignUpRepository =
        SignUpRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideSignUpUseCases(signUpRepository: SignUpRepository): SignUpUseCases =
        SignUpUseCases(
            checkEmail = CheckEmailUseCase(signUpRepository = signUpRepository),
            checkPassword = CheckPasswordUseCase(signUpRepository = signUpRepository),
            checkUserName = CheckUserNameUseCase(signUpRepository = signUpRepository),
            checkPhone = CheckPhoneUseCase(signUpRepository = signUpRepository),
            addUser = AddUserUseCase(signUpRepository = signUpRepository),
            verifyCode = VerifyCodeUseCase(signUpRepository = signUpRepository)
        )


    // ForgotPassword
    @Provides
    @Singleton
    fun provideForgotPasswordRepository(authService: AuthService): ForgotPasswordRepository =
        ForgotPasswordRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideForgotPasswordUseCases(forgotPasswordRepository: ForgotPasswordRepository): ForgotPasswordUseCases =
        ForgotPasswordUseCases(
            checkEmail = CheckEmailUseCase(forgotPasswordRepository = forgotPasswordRepository),
            checkPassword = CheckPasswordUseCase(forgotPasswordRepository = forgotPasswordRepository),
            resetPassword = ResetPasswordUseCase(forgotPasswordRepository = forgotPasswordRepository),
            checkEmailDb = com.youppix.atcadaptor.domain.useCases.forgotPassword.CheckEmailUseCase(
                forgotPasswordRepository
            ),
            verifyCode = com.youppix.atcadaptor.domain.useCases.forgotPassword.VerifyCodeUseCase(
                forgotPasswordRepository
            )
        )

    // Home
    @Provides
    @Singleton
    fun provideHomeService(client: HttpClient): HomeService = HomeService(client)

    @Provides
    @Singleton
    fun provideHomeRepository(homeService: HomeService): HomeRepository =
        HomeRepositoryImpl(homeService)

    @Provides
    @Singleton
    fun provideHomeUseCases(homeRepository: HomeRepository , localeUserEntryManager: LocaleUserEntryManager? = null,): HomeUseCases = HomeUseCases(
        search = HomeSearchUseCase(homeRepository),
        saveAppEntry =  if (localeUserEntryManager != null) SaveAppEntryUseCase(
            localeUserEntryManager
        ) else null
    )

    // Details
    @Provides
    @Singleton
    fun provideDetailsService(client: HttpClient): DetailsService = DetailsService(client)

    @Provides
    @Singleton
    fun provideDetailsRepository(detailsService: DetailsService): DetailsRepository =
        DetailsRepositoryImpl(detailsService)

    @Provides
    @Singleton
    fun provideDetailsUseCases(detailsRepository: DetailsRepository): DetailsUseCases =
        DetailsUseCases(
            getDetails = GetDetailsUseCase(detailsRepository)
        )

}