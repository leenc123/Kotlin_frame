package com.leen.model_mine.http

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
        /**
         * 单例
         */
        @Volatile
        private var INSTANCE:HttpUser?=null
        /**
         * 获取单例
         */
        val instance:HttpUser
            get() {
                if (INSTANCE==null){
                    synchronized(HttpUser::class.java){
                        if (INSTANCE==null){
                            INSTANCE= HttpUser()
                        }
                    }
                }
                return INSTANCE!!
            }
    }

    /**
     * 用户登录
     */
    fun login(parameters: Parameters,httpListener: HttpListener){
        HttpTool.httpGet("login",parameters,httpListener)
    }
}