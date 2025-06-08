package com.asemlab.simpleorder.utils

import android.util.Log
import com.asemlab.simpleorder.models.ServerResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> performRequest(request: () -> HttpResponse): ServerResponse<T> {

    return try {
        val response = request()

        if (response.status.value in 200..299)
            ServerResponse.Success(response.body<T>())
        else
            ServerResponse.Error(
                response.status.description, response.status.value
            )

    } catch (e: Exception) {

        Log.e("performRequest", "${e.message}")
        ServerResponse.Error(e.message.takeIf { it?.isNotEmpty() == true } ?: "Unknown error", 999)

    }
}