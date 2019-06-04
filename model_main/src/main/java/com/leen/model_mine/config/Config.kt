package com.leen.model_mine.config

import com.leen.kotlin_library.util.AppManager
import com.leen.kotlin_library.util.L
import com.leen.kotlin_library.util.PreferencesUtils
import org.w3c.dom.NodeList
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/17
 * 描述：app基础配置
 *
 */
class Config private constructor(){
    val APP_IS_DEBUG="APP_IS_DEBUG"//获取app运行环境标识
    var baseUrl:String?=null//后台接口baseUrl
    var timeoutInMillisecond:Int=0//网络请求超时时间
    val PREF_KEY_LOGIN_STATE="PREF_KEY_LOGIN_STATE"//登录状态标识
    init {
        var io: InputStream
        io= AppManager.instance.topActivity.assets.open(if(PreferencesUtils.getBoolean(AppManager.instance.topActivity, APP_IS_DEBUG, true)) "config-debug.xml" else "config-release.xml")
        val factory= DocumentBuilderFactory.newInstance()
        val builder=factory.newDocumentBuilder()
        val document= builder.parse(io)
        val nodelist=document.getElementsByTagName("config")
        for (i in 0..nodelist.length){
            var node=nodelist.item(i)
            if (node!=null){
                var childNodes: NodeList?=node.childNodes
                if (childNodes!=null){
                    for (j in 0..childNodes.length){
                        var childNode=childNodes.item(j)
                        if (childNode!=null){
                            when(childNode.nodeName){
                                "baseUrl"->baseUrl=childNode.textContent
                                "timeoutInMillisecond"->timeoutInMillisecond=childNode.textContent.toInt()
                                else->{}
                            }
                        }
                    }
                }
            }
        }
    }
    companion object {
        val instance:Config by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            L.e("初始化配置类")
            Config()
        }
    }
}