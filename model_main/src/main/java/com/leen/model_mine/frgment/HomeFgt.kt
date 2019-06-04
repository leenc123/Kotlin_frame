package com.leen.model_mine.frgment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leen.kotlin_library.base.BaseFgt
import com.leen.kotlin_library.event.ForceToLoginEvent
import com.leen.kotlin_library.helper.PermissionListener
import com.leen.kotlin_library.util.AppManager
import com.leen.kotlin_library.util.L
import com.leen.kotlin_library.util.MD5
import com.leen.model_mine.R
import com.leen.model_mine.http.HttpUser
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.interfaces.OnConfirmListener
import kotlinx.android.synthetic.main.fgt_home.*
import org.greenrobot.eventbus.EventBus


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
            R.id.btn_bb->{
                changeListener("哈哈哈","呵呵呵")
            }
            R.id.btn_login->{
                val event = ForceToLoginEvent()
                EventBus.getDefault().post(event)
            }
            R.id.btn_write->{
                handlePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), object : PermissionListener {
                    override fun onGranted() {
                        showToast("读写权限被授权")
                    }

                    override fun onDenied(deniedPermissions: List<String>) {
                        showToast("读写权限被拒绝")
                    }
                })
            }
            R.id.btn_get->{
                HttpUser.instance.login(listOf("verifyCode" to "u8p7","username" to "adminUser","userType" to "1","password" to MD5.encode("123456"),"random" to (System.currentTimeMillis().toString()),"appRegister" to "100d85590909e50a4e9"),this)
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
//            ActivityCollector.finishAll()
        }
        btn_done_dialog.setOnClickListener(this)
        btn_base_dialog.setOnClickListener(this)
        btn_bb.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        btn_write.setOnClickListener(this)
        btn_get.setOnClickListener(this)
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
    fun setOnChangeListener(e:(String,String)->Unit){
        this.changeListener=e
    }
    var  changeListener:(String,String)->Unit={a,b->Unit}
}