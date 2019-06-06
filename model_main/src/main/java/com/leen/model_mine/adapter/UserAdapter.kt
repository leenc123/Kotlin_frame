package com.leen.model_mine.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leen.model_mine.R
import com.leen.model_mine.bean.User

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/6/5
 * 描述：
 *
 */
class UserAdapter(layoutResId: Int, data: MutableList<User>?,context: Context) :
    BaseQuickAdapter<User, BaseViewHolder>(layoutResId, data) {
    var context:Context=context
    override fun convert(helper: BaseViewHolder?, item: User?) {
        helper!!.setText(R.id.tv_name,item!!.name)
        Glide.with(context).load(item!!.pic).into(helper.getView(R.id.img_pic))
    }
}