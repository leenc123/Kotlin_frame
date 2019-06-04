package com.leen.model_mine.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leen.kotlin_library.base.BaseFgt
import com.leen.model_mine.R
import kotlinx.android.synthetic.main.fgt_mine.*
import me.yokeyword.fragmentation.SupportFragment
import com.leen.model_mine.helper.RouteService
import com.sjtu.yifei.route.Routerfit



/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
class MineFgt :BaseFgt() {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_setting->{
                //路由操作
                Routerfit.register(RouteService::class.java).launchSettingActivity("aaaa", 1)
            }
        }
    }

    companion object {
        fun newInstance(): SupportFragment {
            val args = Bundle()
            val fragment = MineFgt()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fgt_mine,container,false)
        return view
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        tv_setting.setOnClickListener(this)
    }
}