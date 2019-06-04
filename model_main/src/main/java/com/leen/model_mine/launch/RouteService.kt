package com.leen.model_main.launch

import android.content.Intent
import com.sjtu.yifei.annotation.Extra
import com.sjtu.yifei.annotation.Flags
import com.sjtu.yifei.annotation.Go

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/6/3
 * 描述：页面跳转接口 *
 */
interface RouteService {
    //Activity 跳转，支持注解传入参数/Flags/requestCode，参数解析遵循android机制
    @Flags(Intent.FLAG_ACTIVITY_NEW_TASK)
    @Go("/model_main/main_activity")
    fun launchMainActivity(@Extra("para2") para2: Int): Boolean
}