package com.leen.kotlin_demo.http

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import com.leen.kotlin_library.httpTools.HttpListener
import com.leen.kotlin_library.httpTools.HttpTool
import com.leen.kotlin_library.util.L

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/17
 * 描述：用户模块
 *
 */
class HttpUser private constructor(){
    companion object {
        val instance:HttpUser by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            HttpUser()
        }
    }

    /**
     * 用户登录
     */
    fun login(parameters: Parameters,httpListener: HttpListener){
        HttpTool.httpGet("login",parameters,httpListener)
    }
}