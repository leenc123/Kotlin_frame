package com.leen.model_main.ui

import android.view.View
import com.leen.kotlin_library.base.BaseAty
import com.leen.model_main.R
import com.sjtu.yifei.annotation.Route

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/6/3
 * 描述：设置页面
 *
 */
@Route(path = "/mine/setting")
class SettingAty :BaseAty() {
    override fun getLayoutResId(): Int {
        return R.layout.aty_setting
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
    }
}