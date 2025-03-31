package com.example.kueskichagenge.core

import com.example.kueskichagenge.core.extensions.DEFAULT_VALUE
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class MockWebServerRule : TestRule {

    private val server = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                server.start()
                base.evaluate()
                server.shutdown()
            }
        }
    }

    fun mockRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(server.url(DEFAULT_PATH))
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    fun loadMockResponse(responseCode: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode)
        fileName?.let { mockResponse.setBody(readFile(it)) }
        server.enqueue(mockResponse)
    }

    fun assertRequestMethod(path: String, method: String) = getTakeRequest().run {
        assertThatEquals(this.path, path)
        assertThatEquals(this.method, method)
    }

    fun assertRequestContainsHeader(key: String, expectedValue: String, requestIndex: Int = 0) {
        val recordedRequest = getRecordedRequestByIndex(requestIndex)
        val value = recordedRequest.getHeader(key)
        assertThatEquals(value, expectedValue)
    }

    fun assertRequestContainsHeaders(headers: Map<String, String>, requestIndex: Int = 0) {
        val recordedRequest = getRecordedRequestByIndex(requestIndex)
        val requestHeaders = recordedRequest.headers
        for (header in headers) {
            val actualValue = requestHeaders[header.key]
            assertThatEquals(actualValue, header.value)
        }
    }

    fun assertRequestBodyEquals(fileName: String) = getTakeRequest().run {
        assertThatEquals(body.readUtf8(), readFile(fileName))
    }

    private fun getRecordedRequestByIndex(requestIndex: Int) = (DEFAULT_VALUE..requestIndex).map { getTakeRequest() }.last()

    private fun readFile(name: String) = this::class.java.classLoader?.getResource(name)?.readText().orEmpty()

    private fun getTakeRequest() = server.takeRequest()

    companion object {
        private const val DEFAULT_PATH = "/"
        const val GET = "GET"
        const val POST = "POST"
        const val PATCH = "PATCH"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
    }
}