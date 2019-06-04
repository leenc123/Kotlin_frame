package com.leen.model_mine.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leen.kotlin_library.base.BaseFgt
import com.leen.kotlin_library.util.L
import com.leen.model_mine.R
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
class FindFgt :BaseFgt(){
    override fun onClick(v: View?) {

    }

    companion object {
        fun newInstance():SupportFragment{
            val args = Bundle()
            val fragment = FindFgt()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fgt_find,container,false)
        return view
    }
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        L.e("find")
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        initImmersionBar(R.color.colorWhite)
    }

}