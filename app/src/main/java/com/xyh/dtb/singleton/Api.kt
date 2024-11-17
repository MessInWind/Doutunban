package com.xyh.dtb.singleton

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xyh.dtb.pojo.entity.MoviesData
import com.xyh.dtb.pojo.entity.PersonData
import com.xyh.dtb.pojo.entity.RandomMoviesData
import com.xyh.dtb.pojo.entity.UserData
import com.xyh.dtb.pojo.response.ResponseData
import com.xyh.dtb.store.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.reflect.Type
import kotlin.reflect.full.memberProperties

// API
// val coroutineScope = rememberCoroutineScope() // 携程scope
// coroutineScope.launch { // 协同空间
//     userData = Api.login(LoginDTO("xyh", "666666")) as? UserData
// }

object Api{
    private val baseURL = "http://49.234.52.237:8090"
    private val client = OkHttpClient()
    private val gson = Gson()

     suspend fun login(params: Any): Any?{ // 挂起函数
        return request(
            url = "/login/login",
            method = "get",
            params = params,
            recvClass = UserData::class.java
        )
     }

    suspend fun registry(params: Any): Any?{
        return request(
            url = "/registry/UsernameRegistry",
            method = "get",
            params = params,
            recvClass = UserData::class.java
        )
    }

    suspend fun getMoviesRandom(params: Any): Any?{
        val type = object : TypeToken<List<RandomMoviesData>>() {}.type
        return request(
            url = "/movie/getMoviesRandom",
            method = "get",
            params = params,
            recvClass = type
        )
    }

    suspend fun getMovieInfoById(params: Any): Any?{
        return request(
            url = "/movie/getMovieInfoById",
            method = "get",
            params = params,
            recvClass = MoviesData::class.java
        )
    }

    suspend fun getPersonInfoById(params: Any): Any?{
        return request(
            url = "/person/getPersonInfoById",
            method = "get",
            params = params,
            recvClass = PersonData::class.java
        )
    }

    private suspend fun request(
        url: String,
        method: String,
        params: Any,
        recvClass: Type
    ): Any? { // 暂时仅考虑get请求
        val reqStr = if(params is Unit) "" else params.toReqStr()
        val request = Request.Builder()
            .url(baseURL + url + reqStr)
            .addHeader("token", Store.userData.token) // jwt
            .get()
            .build()
        return try { // 0 error 1 success 2 redirect
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful)
                        throw IOException("Unexpected code $response")
                    val responseBody = response.body!!.string()
                    var responseData: ResponseData<*> = gson.fromJson(responseBody, ResponseData::class.java)!!
                    if(responseData.code == 0){ // 检测是否error
                        GlobalMessage.showMessage(responseData.msg?:"$url$reqStr 请求失败 error")
                        null
                    }else{ // success
                        responseData = responseBody.let { // 将body里的string转为通用response类
                            val type = com.google.gson.reflect.TypeToken.getParameterized(ResponseData::class.java, recvClass).type
                            gson.fromJson(it, type)
                        }
                        responseData.data
                    }
                }
            }
        } catch (e: IOException) {
            GlobalMessage.showMessage("$url$reqStr 请求失败 404")
            println("mystd:" + e.message)
            return null
        }
    }

    private fun Any.toReqStr(): String { // data转为get请求参数 {username: "xyh", password: "666666"} => "?username=xyh&password=666666"
        return this::class.memberProperties.joinToString("&", prefix = "?") { prop ->
            val value = prop.getter.call(this)
            "${prop.name}=${value.toString()}"
        }
    }
}

