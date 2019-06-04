package com.leen.model_mine.helper

import android.content.Intent
import android.support.v4.app.Fragment
import com.sjtu.yifei.annotation.*


/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/6/3
 * 描述：
 *
 */
interface RouteService {
    //Activity 跳转，支持注解传入参数/Flags/requestCode，参数解析遵循android机制
    @Flags(Intent.FLAG_ACTIVITY_NEW_TASK)
    @Go("/mine/setting")
    fun launchSettingActivity(@Extra("para1") para1: String, @Extra("para2") para2: Int): Boolean

    @Go("/mine/setting")
    fun launchSettingActivityForResult(@Extra("para1") para1: String, @Extra("para2") para2: Int, @RequestCode requestCode: Int): Boolean

    //支持直接解析标准URL进行跳转 传入 注解@Uri 参数
    fun launchSchemeActivity(@Uri uristring: String): Boolean

    //Fragment初始化，支持注解传入参数，参数解析遵循android机制
    @Go("/login-module/TestFragment")
    fun getTestFragment(@Extra("param1") para1: String, @Extra("param2") para2: IntArray): Fragment

}