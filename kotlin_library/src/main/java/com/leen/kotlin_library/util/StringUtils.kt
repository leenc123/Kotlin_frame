package com.leen.kotlin_library.util

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/20
 * 描述：字符串工具类
 *
 */
object StringUtils {
    /**
     * 是否为空
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.length == 0
    }
}