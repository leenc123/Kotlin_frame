package com.leen.model_mine.frgment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.leen.kotlin_library.base.BaseFgt
import com.leen.model_mine.R
import com.leen.model_mine.adapter.UserAdapter
import com.leen.model_mine.bean.User
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fgt_find.*
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
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        val images= listOf("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2277717338,214268974&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2277717338,214268974&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2277717338,214268974&fm=26&gp=0.jpg")
        super.onSupportVisible()
        initImmersionBar(R.color.colorWhite)
        //设置图片集合
        banner.setImages(images)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(1500)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()
        var userList: MutableList<User> = ArrayList()
        for (i in 0..30){
            userList.add(User("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2277717338,214268974&fm=26&gp=0.jpg","王力宏"))
        }
        recyclerView.setAdapter(UserAdapter(R.layout.item_user,userList, this!!.context!!))
    }

    override fun onSupportVisible() {


    }
    class GlideImageLoader:ImageLoader(){
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }

    }

}