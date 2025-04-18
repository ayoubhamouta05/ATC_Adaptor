package com.youppix.atcadaptor.data.remote.alert

import android.util.Log
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.common.Urls.GET_NOTIFICATIONS_URL
import com.youppix.atcadaptor.common.Urls.UPDATE_NOTIFICATION_URL
import com.youppix.atcadaptor.data.remote.alert.dto.NotificationsResponse
import com.youppix.atcadaptor.data.remote.dto.StandardResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class AlertService (private val client : HttpClient){


     fun getNotifications(userId: Int): Flow<Resource<NotificationsResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                @Serializable
                data class Body(
                    val userId: Int,
                )

                val body = Body(
                    userId = userId
                )

                val response = client.post(GET_NOTIFICATIONS_URL) {
                    setBody(body)
                }
                val responseBody = response.body<NotificationsResponse>()

                emit(Resource.Successful(responseBody))
                Log.d("SearchService", response.body())
            } catch (e: ClientRequestException) {
                emit(Resource.Error("Client request error"))
                Log.d("SignUpService", "Client request error: ${e.localizedMessage}")
            } catch (e: ServerResponseException) {
                emit(Resource.Error("Server response error"))
                Log.d("SignUpService", "Server response error: ${e.localizedMessage}")
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server"))
                Log.d("SignUpService", "Couldn't reach server: ${e.message}")
            } catch (e: SerializationException) {
                emit(Resource.Error("Serialization error"))
                Log.d("SignUpService", "Serialization error: ${e.localizedMessage}")
            }
        }

      fun updateNotifications(userId: Int) : Flow<Resource<StandardResponse>> = flow {

        try {
            @Serializable
            data class Body(
                val userId: Int,
            )

            val body = Body(
                userId = userId
            )

            val response = client.post(UPDATE_NOTIFICATION_URL) {
                setBody(body)
            }
            Log.d("SearchService", response.body())
        } catch (e: ClientRequestException) {
            Log.d("SignUpService", "Client request error: ${e.localizedMessage}")
        } catch (e: ServerResponseException) {
            Log.d("SignUpService", "Server response error: ${e.localizedMessage}")
        } catch (e: IOException) {
            Log.d("SignUpService", "Couldn't reach server: ${e.message}")
        } catch (e: SerializationException) {
            Log.d("SignUpService", "Serialization error: ${e.localizedMessage}")
        }

    }
}