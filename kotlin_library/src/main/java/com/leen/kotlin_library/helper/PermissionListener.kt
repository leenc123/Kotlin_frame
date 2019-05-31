package com.leen.kotlin_library.helper

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/30
 * 描述：
 *
 */
interface PermissionListener {

    fun onGranted()

    fun onDenied(deniedPermissions: List<String>)

}
