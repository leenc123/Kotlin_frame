package com.leen.kotlin_demo.frgment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leen.kotlin_demo.R
import com.leen.kotlin_library.base.BaseFgt
import com.leen.kotlin_library.util.AppManager
import com.leen.kotlin_library.util.L
import kotlinx.android.synthetic.main.fgt_home.*
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.enums.PopupAnimation


/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
class HomeFgt :BaseFgt() {
    var stringList= arrayOf("1","2","3","4")
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_done_dialog->{
                XPopup.Builder(context).asConfirm("提示", "我是内容",
                        object : OnConfirmListener {
                            override fun onConfirm() {
                                for ((index,value) in stringList.withIndex()){
                                    L.e("索引为${index}的元素是${value}")
                                }
                            }
                        })
                        .show()
            }
            R.id.btn_base_dialog->{
                XPopup.Builder(context)
                        .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                        .autoOpenSoftInput(true)
                        .asCustom(CustomPopup(context!!))
                        .show()
            }
        }
    }

    companion object {
        fun newInstance(): HomeFgt {
            val args = Bundle()
            val fragment = HomeFgt()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val  view=inflater.inflate(R.layout.fgt_home,container,false)
        return view
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        tv_finish.setOnClickListener {
            AppManager.instance.killAllActivity()
        }
        btn_done_dialog.setOnClickListener(this)
        btn_base_dialog.setOnClickListener(this)
        L.e("home")
    }
    class CustomPopup(context: Context) : CenterPopupView(context) {

        override fun getImplLayoutId(): Int {
            return R.layout.layout_love
        }

        override fun onCreate() {
            super.onCreate()

        }

        override fun onShow() {
            super.onShow()
        }

        //        @Override
        //        protected int getMaxHeight() {
        //            return 200;
        //        }
        //
        //返回0表示让宽度撑满window，或者你可以返回一个任意宽度
        //        @Override
        //        protected int getMaxWidth() {
        //            return 1200;
        //        }
    }

}