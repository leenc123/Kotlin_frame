package com.leen.kotlin_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.leen.kotlin_demo.frgment.FindFgt
import com.leen.kotlin_demo.frgment.HomeFgt
import com.leen.kotlin_demo.frgment.MineFgt
import com.leen.kotlin_library.base.BaseAty
import kotlinx.android.synthetic.main.aty_fragment.*
import me.yokeyword.fragmentation.BuildConfig
import me.yokeyword.fragmentation.Fragmentation
import me.yokeyword.fragmentation.SupportFragment



/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *fragment碎片宿主activity
 */
class FragmentAty:BaseAty(){
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mFragments = arrayOfNulls<SupportFragment>(3)
    val FIRST = 0
    val SECOND = 1
    val THIRD = 2
    override fun getLayoutResId(): Int = R.layout.aty_fragment

    override fun initView() {
        if (findFragment(HomeFgt::class.java)==null){
            mFragments[FIRST]=HomeFgt.newInstance()
            mFragments[SECOND]=FindFgt.newInstance()
            mFragments[THIRD]=MineFgt.newInstance()

            loadMultipleRootFragment(R.id.fragment, FIRST,
                mFragments[FIRST],
                mFragments[SECOND],
                mFragments[THIRD])
        }else{
            mFragments[FIRST] = findFragment(HomeFgt::class.java)
            mFragments[SECOND] = findFragment(FindFgt::class.java)
            mFragments[THIRD] = findFragment(MineFgt::class.java)
        }
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_main.setOnClickListener {
            showHideFragment(mFragments[FIRST])
        }
        tv_find.setOnClickListener {
            showHideFragment(mFragments[SECOND])
        }
        tv_mine.setOnClickListener {
            showHideFragment(mFragments[THIRD])
        }
    }
    companion object {
        fun startActivity(activity:Activity){
            val intent = Intent(activity, FragmentAty::class.java)
            activity.startActivity(intent)
        }
    }

}