package com.excamp.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * 九宫格控件
 */
class LockView : View {
    private var mIsInit = false;

    //    外圆的半径
    private var mDorRadius = 0

    var array = Array<Point?>(3, { null })
    //    二维数组初始化
    private var mPoints: Array<Array<Point?>> = Array(3) {array}

    //    画笔
    private var mLinePaint: Paint? = null
    private var mPressedPaint: Paint? = null
    private var mErrorPaint: Paint? = null
    private var mNormalPaint: Paint? = null
    private var mArrowPaint: Paint? = null

    //颜色
    private val mOuterPressedColor = 0xff8cbad8.toInt()
    private val mInnnerPressColor = 0xff0596f6.toInt()
    private val mOutNorMalColor = 0xffd9d9d9.toInt()
    private val mInnerNorMalColor = 0xff929292.toInt()
    private val mOuterErrorColor = 0xff0596f6.toInt()
    private val mInnnerErrorColor = 0xff0596f6.toInt()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        初始化九宫格
        if (!mIsInit) {
            initDot();
            initPaint();
            mIsInit =true
        }
//        绘制九宫格
        drawShow(canvas)
    }

    private fun drawShow(canvas: Canvas?) {
        for (i in 0..2) {
            for (point in mPoints[i]) {
                Log.i("DEBUG", "drawShow: ")
                    // 先绘制外圆
                    mNormalPaint!!.color = mOutNorMalColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(),
                        mDorRadius.toFloat(), mNormalPaint!!)
                    // 后绘制内圆
                    mNormalPaint!!.color = mInnerNorMalColor
                    canvas!!.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(),
                        mDorRadius / 6.toFloat(), mNormalPaint!!)

//
//                if (point!!.statusIsPressed()) {
//                    // 先绘制外圆
//                    mPressedPaint.color = mOuterPressedColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(),
//                        mDotRadius.toFloat(), mPressedPaint)
//                    // 后绘制内圆
//                    mPressedPaint.color = mInnerPressedColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(),
//                        mDotRadius / 6.toFloat(), mPressedPaint)
//                }
//
//                if (point!!.statusIsError()) {
//                    // 先绘制外圆
//                    mErrorPaint.color = mOuterErrorColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(),
//                        mDotRadius.toFloat(), mErrorPaint)
//                    // 后绘制内圆
//                    mErrorPaint.color = mInnerErrorColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(),
//                        mDotRadius / 6.toFloat(), mErrorPaint)
//                }
            }
        }
    }

    /**
     * 初始化画笔
     * 3个点状态的画笔，线的画笔，剪头的画笔
     */
    private fun initPaint() {
//线的画笔
        mLinePaint = Paint()
        mLinePaint!!.color = mInnnerPressColor
        mLinePaint!!.style = Paint.Style.STROKE
        mLinePaint!!.isAntiAlias = true
        mLinePaint!!.strokeWidth = (mDorRadius / 9).toFloat()
//        按下的画笔
        mPressedPaint = Paint()
        mPressedPaint!!.style = Paint.Style.STROKE
        mPressedPaint!!.isAntiAlias = true
        mPressedPaint!!.strokeWidth = (mDorRadius / 6).toFloat()
//        错误的画笔
        mErrorPaint = Paint()
        mErrorPaint!!.style = Paint.Style.STROKE
        mErrorPaint!!.isAntiAlias = true
        mErrorPaint!!.strokeWidth = (mDorRadius / 6).toFloat()
//        默认的画笔
        mNormalPaint = Paint()
        mNormalPaint!!.style = Paint.Style.STROKE
        mNormalPaint!!.isAntiAlias = true
        mNormalPaint!!.strokeWidth = (mDorRadius / 9).toFloat()
//        箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint!!.color = mInnnerPressColor
        mArrowPaint!!.style = Paint.Style.FILL
        mArrowPaint!!.isAntiAlias = true

    }

    /**
     *初始化点
     */
    private fun initDot() {
//        九个宫格，存到集合 point
        // 九个宫格，存到集合  Point[3][3]
        // 不断绘制的时候这几个点都有状态，而且后面肯定需要回调密码点都有下标 点肯定是一个对象
        // 计算中心位置
        var width = this.width
        var height = this.height

        // 兼容横竖屏
        var offsetX = 0
        var offsetY = 0
        if (height > width) {
            offsetY = (height - width) / 2
            height = width
        } else {
            offsetX = (width - height) / 2
            width = height
        }

        var squareWidth = width / 3

        // 外圆的大小，根据宽度来
        mDorRadius = width / 12
// 计算和指定点的中心点位置
        mPoints[0][0] =
            Point(offsetX + squareWidth / 2, offsetY + squareWidth / 2, 0)
        mPoints[0][1] =
            Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth / 2, 1)
        mPoints[0][2] =
            Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth / 2, 2)

        mPoints[1][0] =
            Point(offsetX + squareWidth / 2, offsetY + squareWidth * 3 / 2, 3)
        mPoints[1][1] =
            Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 3 / 2, 4)
        mPoints[1][2] =
            Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 3 / 2, 5)

//        mPoints[2][0] =
//            Point(offsetX + squareWidth / 2, offsetY + squareWidth * 5 / 2, 6)
//        mPoints[2][1] =
//            Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 5 / 2, 7)
//        mPoints[2][2] =
//            Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 5 / 2, 8)
    }

    /**
     * 宫格的类
     */
    class Point(var centerX: Int, var centerY: Int, var index: Int) {
        private val STATUS_NORMAL = 1
        private val STATUS_PRESSED = 2
        private val STATUS_ERROR = 3
//        当前点的状态
        private var status = STATUS_NORMAL
    }
}