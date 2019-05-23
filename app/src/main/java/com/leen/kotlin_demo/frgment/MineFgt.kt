package com.leen.kotlin_demo.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leen.kotlin_demo.R
import com.leen.kotlin_library.base.BaseFgt
import com.leen.kotlin_library.util.L
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
class MineFgt :BaseFgt() {
    override fun onClick(v: View?) {
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
        showLoadingDialog("哈哈")
        L.e("mine")
    }
}