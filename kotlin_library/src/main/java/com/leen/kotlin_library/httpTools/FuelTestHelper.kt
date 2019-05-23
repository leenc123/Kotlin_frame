package com.leen.kotlin_library.httpTools

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.leen.kotlin_library.util.L

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/17
 * 描述：初始化fuel网络请求
 *
 */
object FuelTestHelper {
    fun initFuel(baseUrl:String,token:String,timeoutInMillisecond:Int) {
        //val requestInterceptors: MutableList<((Request)-> Request)-> ((Request)->Request)> = mutableListOf()
        //服务器接口地址
        FuelManager.instance.basePath = baseUrl
        //超时时间20秒
        FuelManager.instance.timeoutInMillisecond = timeoutInMillisecond
        //FuelManager.instance.baseHeaders = mapOf("token" to "BearerAbCdEf123456")
        //添加header拦截器
        FuelManager.instance.addRequestInterceptor(tokenInterceptor("token",token))
        //添加请求日志拦截器
        FuelManager.instance.addRequestInterceptor(cUrlLoggingRequestInterceptor())
        //foldRight 是 List 的一个扩展函数 从右往左，对列表中的每一个元素执行 operation 操作，
        // 每个操作的结果是下一次操作的入参，第一次 operation 的初始值是 initial。
        //requestInterceptors.foldRight({r: Request -> r}){f,acc-> f(acc)}
    }

    /**
     * @Author :xqt
     * @Description :日志拦截器
     * @Return :
     * @Params :
     */
    private fun cUrlLoggingRequestInterceptor() = { next: (Request) -> Request ->
        { r: Request ->
            var logging = StringBuffer()
            logging.append("\n-----Method = ${r.method}")
            logging.append("\n-----mediaTypes = ${r.parameters}")
            logging.append("\n-----headers = ${r.headers}")
            logging.append("\n-----url---->${r.url}")
            when (r.method) {
                Method.POST -> {
                    logging.append("\n-----request parameters:")
                    r.parameters.forEach {
                        logging.append("\n-----${it.first}=${it.second}")
                    }
                }
            }
            L.d(logging.toString())
            next(r)
        }
    }

    /**
     * @Author :xqt
     * @Description :添加header
     * @Return :
     * @Params :
     */
    private fun tokenInterceptor(key:String,value:String) = { next: (Request) -> Request ->
        { req: Request ->
            //"Content-Type:application/x-www-form-urlencoded; charset=UTF-8"
            if (value!=""){
                req.header(mapOf(key to value))//变量替换
            }
            next(req)
        }
    }
}