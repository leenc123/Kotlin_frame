package com.leen.kotlin_library.httpTools

import com.alibaba.fastjson.JSONObject
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.leen.kotlin_library.util.L


/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/17
 * 描述：http请求工具
 *
 */
object HttpTool {
    fun httpGet(url: String, parameters: Parameters, httpListener: HttpListener) {
        httpListener.onStarted()
        url.httpGet(parameters)
            .responseString { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        httpListener.onFailure(request.url.toString())
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val (data, error) = result
                        val map = JSONObject.parse(data.toString()) as Map<*, *>
                        when (map.get("respCode")) {
                            "0" -> httpListener.onSuccess(request.url.toString(), data.toString())
                            "-1"->httpListener.onOverTime(map)
                            else->httpListener.onError(request.url.toString(),map)
                        }

                    }
                    else->{

                    }
                }
            }
    }
    fun httpPost(url:String,parameters: Parameters,httpListener: HttpListener){
        url.httpPost(parameters)
            .response { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        httpListener.onFailure(request.url.toString())
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val (data, error) = result
                        val map = JSONObject.parse(data.toString()) as Map<*, *>
                        when (map.get("respCode")) {
                            "0" -> httpListener.onSuccess(request.url.toString(), data.toString())
                            "-1"->httpListener.onOverTime(map)
                            else->httpListener.onError(request.url.toString(),map)
                        }

                    }
                }
            }
    }
    fun httpPostJson(url: String,jsonStr: String,httpListener: HttpListener){
        url.httpPost()
            .jsonBody(jsonStr)
            .response { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        httpListener.onFailure(request.url.toString())
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val (data, error) = result
                        val map = JSONObject.parse(data.toString()) as Map<*, *>
                        when (map.get("respCode")) {
                            "0" -> httpListener.onSuccess(request.url.toString(), data.toString())
                            "-1"->httpListener.onOverTime(map)
                            else->httpListener.onError(request.url.toString(),map)
                        }

                    }
                }
            }
    }
    fun httpPut(url: String,parameters: Parameters,httpListener: HttpListener){
        url.httpPut(parameters)
            .response { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        httpListener.onFailure(request.url.toString())
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val (data, error) = result
                        val map = JSONObject.parse(data.toString()) as Map<*, *>
                        when (map.get("respCode")) {
                            "0" -> httpListener.onSuccess(request.url.toString(), data.toString())
                            "-1"->httpListener.onOverTime(map)
                            else->httpListener.onError(request.url.toString(),map)
                        }

                    }
                }
            }
    }
    fun httpDel(url: String,parameters: Parameters,httpListener: HttpListener){
        url.httpDelete(parameters)
            .response { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        httpListener.onFailure(request.url.toString())
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val (data, error) = result
                        val map = JSONObject.parse(data.toString()) as Map<*, *>
                        when (map.get("respCode")) {
                            "0" -> httpListener.onSuccess(request.url.toString(), data.toString())
                            "-1"->httpListener.onOverTime(map)
                            else->httpListener.onError(request.url.toString(),map)
                        }

                    }
                }
            }
    }
}