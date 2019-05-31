package com.leen.kotlin_library.util

import java.math.BigDecimal

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/20
 * 描述：加减乘除运算工具类
 *
 */
object ArithUtil {
    // 除法运算默认精度
    private val DEF_DIV_SCALE = 10

    /**
     * 精确加法
     */
    fun add(value1: Double, value2: Double): Double {
        val b1 = BigDecimal.valueOf(value1)
        val b2 = BigDecimal.valueOf(value2)
        return b1.add(b2).toDouble()
    }

    /**
     * 精确减法
     */
    fun sub(value1: Double, value2: Double): Double {
        val b1 = BigDecimal.valueOf(value1)
        val b2 = BigDecimal.valueOf(value2)
        println("aaaaa")
        return b1.subtract(b2).toDouble()
    }

    /**
     * 精确乘法
     */
    fun mul(value1: Double, value2: Double): Double {
        val b1 = BigDecimal.valueOf(value1)
        val b2 = BigDecimal.valueOf(value2)
        return b1.multiply(b2).toDouble()
    }

    /**
     * 精确除法
     * @param scale 精度
     */
    @Throws(IllegalAccessException::class)
    @JvmOverloads
    fun div(value1: Double, value2: Double, scale: Int = DEF_DIV_SCALE): Double {
        if (scale < 0) {
            throw IllegalAccessException("精确度不能小于0")
        }
        val b1 = BigDecimal.valueOf(value1)
        val b2 = BigDecimal.valueOf(value2)
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 四舍五入
     * @param scale 小数点后保留几位
     */
    @Throws(IllegalAccessException::class)
    fun round(v: Double, scale: Int): Double {
        return div(v, 1.0, scale)
    }

    /**
     * 比较大小
     */
    fun equalTo(b1: BigDecimal?, b2: BigDecimal?): Boolean {
        return if (b1 == null || b2 == null) {
            false
        } else 0 == b1.compareTo(b2)
    }

}
/**
 * 精确除法 使用默认精度
 */
