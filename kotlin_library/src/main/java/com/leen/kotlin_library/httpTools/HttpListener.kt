package com.leen.kotlin_library.httpTools

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/17
 * 描述：网络请求接口
 *
 */
interface HttpListener {
    //开始调用接口
    fun onStarted()
    //接口请求成功
    fun onSuccess(requestUrl:String,jsonStr:String)
    //超时、token过期
    fun onOverTime(map:Map<*, *>)
    //接口请求失败
    fun onFailure(requestUrl:String)
    //接口错误
    fun onError(requestUrl:String,map:Map<*, *>)
}