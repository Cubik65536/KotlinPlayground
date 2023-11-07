package utils

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

/*
 * HttpUtil.kt
 * KotlinPlayground
 *
 * Created by Qian Qian "Cubik" on Tuesday Nov. 07.
 */

object HttpUtil {
    private val httpClient = HttpClient(OkHttp)

    object GetRequests {
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.get(url, requestBuilder)
            }
            return httpResponse
        }

        fun getBody(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): String {
            val httpResponseBody: String
            runBlocking {
                httpResponseBody = request(url, requestBuilder).bodyAsText()
            }
            return httpResponseBody
        }

        fun getBodyJSON(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonObject {
            val parser: Parser = Parser.default()
            val stringBuilder: StringBuilder = StringBuilder(getBody(url, requestBuilder))
            return parser.parse(stringBuilder) as JsonObject
        }
    }

    object PostRequests {
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.post(url, requestBuilder)
            }
            return httpResponse
        }

        fun getBody(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): String {
            val httpResponseBody: String
            runBlocking {
                httpResponseBody = request(url, requestBuilder).bodyAsText()
            }
            return httpResponseBody
        }

        fun getBodyJSON(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonObject {
            val parser: Parser = Parser.default()
            val stringBuilder: StringBuilder = StringBuilder(getBody(url, requestBuilder))
            return parser.parse(stringBuilder) as JsonObject
        }
    }
}
