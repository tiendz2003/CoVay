package com.example.covay_project.covay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.covay_project.R
import com.example.covay_project.graphic.Vector
import java.util.Stack
import java.util.Timer
import java.util.TimerTask
import kotlin.math.acos
import kotlin.math.sqrt
import com.example.covay_project.graphic.Circle
import com.example.covay_project.graphic.CircleCircleIntersection


@SuppressLint("DefaultLocale","ClickableViewAccessibility")

class WatermelonChessBoardView
    (context: Context) : View(context) {
    private lateinit var c1: Vector
    private lateinit var c2: Vector
    private lateinit var c3: Vector
    private lateinit var c4: Vector
    private lateinit var c5: Vector
    private lateinit var c6: Vector
    private lateinit var c7: Vector

    private var currentPlayMode: PlayMode
    private var currentSelectingPiece: PieceInfo? = null
    private var currentTempTargetPiece: PieceInfo? = null
    private var currentTurn: PieceStatus? = null
    private var handler: Handler? = null
    private var historyofPieceList: Stack<HistoryItem> = Stack()
    private var isInit = false
    private var isRegreting = false
    private var mBitmapColon: Bitmap? = null
    private var mBitmapNumList: List<Bitmap> = ArrayList()
    private var mpMove: MediaPlayer? = null
    private var mpSelect: MediaPlayer? = null
    private var mCrurrX: Int = 0
    private var mCrurrY: Int = 0
    private lateinit var n1: Vector
    private lateinit var n2: Vector
    private lateinit var n3: Vector
    private lateinit var n4: Vector
    private lateinit var n5: Vector
    private lateinit var n6: Vector
    private lateinit var n7: Vector
    private lateinit var paintBackground: Paint
    private lateinit var paintBoard: Paint
    private lateinit var paintN: Paint
    private lateinit var paintNS: Paint
    private lateinit var paintNT: Paint
    private var paintNoSelect: Paint? = null
    private lateinit var paintS: Paint
    private lateinit var paintSS: Paint
    private lateinit var paintST: Paint
    private lateinit var paintText: Paint
    private  var parentActivity: BoardActivity? = null
    private var pieceList: MutableList<PieceInfo> = ArrayList()
    private var rb = 0
    private lateinit var recs1: RectF
    private lateinit var recs2: RectF
    private lateinit var recs3: RectF
    private lateinit var recs4: RectF
    private var restCountN = 0
    private var restCountS = 0
    private var rp = 0
    private var rs = 0
    private lateinit var s1: Vector
    private lateinit var s2: Vector
    private lateinit var s3: Vector
    private lateinit var s4: Vector
    private lateinit var s5: Vector
    private lateinit var s6: Vector
    private lateinit var s7: Vector
    private var screenHeight = 0
    private var screenWidth = 0
    private var startAngel1 = 0f
    private var startAngel2 = 0f
    private var startAngel3 = 0f
    private var startAngel4 = 0f
    private var startTime: Long = 0
    private var strokeWidthOfSelecting = 0
    private var sweepAngle = 0.0
    private var timeInfoVec: Vector
    private var timeTask: TimerTask? = null
    private var timer: Timer? = null

    private var timerHandler: Handler? = null
    private var turnInfoVec: Vector
    private var v2p: HashMap<Vector, PieceInfo> = HashMap()

    var winner: PieceStatus? = null
    fun getCurrentPlaymode(): PlayMode {
        return currentPlayMode
    }

    fun getHistoryPieceList(): Stack<HistoryItem> {
        return historyofPieceList
    }

    fun getwinner(): PieceStatus? {
        return winner
    }

    fun setParentActivity(a: BoardActivity) {
        parentActivity = a
    }

    fun getBitmapWithTransparentBG(srcBitMap: Bitmap, bgColor: Int): Bitmap {
        val result = srcBitMap.copy(Bitmap.Config.ARGB_8888, true)
        val nWidth = result.width
        val nHeight = result.height
        for (y in 0 until nHeight) {
            for (x in 0 until nWidth) {
                if (result.getPixel(x, y) == bgColor) {
                    result.setPixel(x, y, 0)
                }
            }
        }
        return result
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context) {
        mBitmapNumList = ArrayList()
        v2p = HashMap()
        pieceList = ArrayList()
        historyofPieceList = Stack()
        currentPlayMode = PlayMode.SINGLE_0
        mCrurrX = 0
        mCrurrY = 0
        strokeWidthOfSelecting = 10
        currentTurn = null
        currentSelectingPiece = null
        currentTempTargetPiece = null
        winner = null
        restCountN = 0
        restCountS = 0
        isInit = false
        isRegreting = false
        parentActivity = null
        mpMove = null
        mpSelect = null
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.i(GV.TAG, "handleMessage")
                invalidate()
            }
        }
        timer = null
        timerHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                invalidate()
            }
        }
        timeTask = object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = 1
                timerHandler?.sendMessage(message)
            }
        }

    }

    init {
        mBitmapNumList = ArrayList()
        v2p = HashMap()
        pieceList = ArrayList()
        historyofPieceList = Stack()
        currentPlayMode = PlayMode.SINGLE_0
        mCrurrX = 0
        mCrurrY = 0
        strokeWidthOfSelecting = 10
        currentTurn = null
        currentSelectingPiece = null
        currentTempTargetPiece = null
        winner = null
        restCountN = 0
        restCountS = 0
        isInit = false
        isRegreting = false
        parentActivity
        mpMove = null
        mpSelect = null
        timer = null
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.i(GV.TAG, "handleMessage")
                invalidate()
            }
        }
        timerHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                invalidate()
            }
        }
        timeTask = object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = 1
                timerHandler?.sendMessage(message)
            }
        }
        startTime = SystemClock.elapsedRealtime()
        timer = Timer()
        timer?.schedule(timeTask, 0, 1000)

        timeInfoVec = Vector(20.0, 0.0)
        turnInfoVec = Vector(0.0, 0.0)

        isSoundEffectsEnabled = true
        mpMove = MediaPlayer.create(getContext(), R.raw.capture)
        mpSelect = MediaPlayer.create(getContext(), R.raw.moveselect)
    }

    fun setCurrentPlayMode(currenPlayMode2: PlayMode) {
        currentPlayMode = currenPlayMode2
    }

    private fun getAngle(center: Point, px: Point, py: Point): Double {
        val v1x = (px.x - center.x).toFloat()
        val v1y = (px.y - center.y).toFloat()
        val l1 = sqrt((v1x * v1x + v1y * v1y).toDouble()).toFloat()
        val v2x = (py.x - center.x).toFloat()
        val v2y = (py.y - center.y).toFloat()
        val l2 = sqrt((v2x * v2x + v2y * v2y).toDouble()).toFloat()
        return Math.toDegrees(acos(((v1x / l1) * (v2x / l2) + (v1y / l1) * (v2y / l2)).toDouble()))
    }

    fun regret() {
        if (!historyofPieceList.empty() && !isRegreting) {
            historyofPieceList.pop()
            isRegreting = true
        }
        if (!historyofPieceList.empty()) {
            val hi = historyofPieceList.pop()
            pieceList = hi.mPieceList
            currentTurn = hi.currentTurn
            restCountN = hi.restCountN
            restCountS = hi.restCountS
            invalidate()
            return
        }
        init(currentPlayMode)
    }

    fun init() {
        init(currentPlayMode)
    }

    fun init(mode: PlayMode) {
        currentPlayMode = mode
        winner = null
        restCountN = 6
        restCountS = 6
        screenWidth = width
        screenHeight = height
        currentTurn = PieceStatus.S_SIDE
        pieceList.clear()
        historyofPieceList.clear()
        v2p.clear()
        paintText = Paint()
            paintText.style = Paint.Style.FILL
            paintText.color = -1
            paintText.textSize = 50.0f

        paintBackground = Paint()
            paintBackground.style = Paint.Style.FILL
            paintBackground.pathEffect = CornerPathEffect(10.0f)
            paintBackground.color = ContextCompat.getColor(context, R.color.gold)

        paintBoard = Paint()
            paintBoard.style = Paint.Style.STROKE
            paintBoard.pathEffect = CornerPathEffect(10.0f)
            paintBoard.color = Color.BLACK
            paintBoard.strokeWidth = 6.0f
            paintBoard.isAntiAlias = true

        paintNoSelect = Paint()
            paintNoSelect?.style = Paint.Style.STROKE
            paintNoSelect?.color = -1

        paintN = Paint()
            paintN.style = Paint.Style.FILL_AND_STROKE
            paintN.color = Color.WHITE
            paintN.strokeWidth = strokeWidthOfSelecting.toFloat()

        paintNT = Paint()
            paintNT.style = Paint.Style.FILL_AND_STROKE
            paintNT.color = -16776961
            paintNT.strokeWidth = strokeWidthOfSelecting.toFloat()

        paintNS = Paint()
            paintNS.style = Paint.Style.STROKE
            paintNS.color = -1
            paintNS.strokeWidth = strokeWidthOfSelecting.toFloat()

        paintS = Paint()
            paintS.color = Color.BLACK
            paintS.style = Paint.Style.FILL_AND_STROKE

        paintSS = Paint()
            paintSS.style = Paint.Style.STROKE
            paintSS.strokeWidth = strokeWidthOfSelecting.toFloat()
            paintSS.color = -1

        paintST = Paint()
            paintST.style = Paint.Style.FILL_AND_STROKE
            paintST.color = -16711936
            paintST.strokeWidth = strokeWidthOfSelecting.toFloat()

        rb = ((screenWidth / 2) * 6) / 7
        rs = rb / 3
        rp = rs / 3

        c1 = Vector(screenWidth / 2.toDouble(), screenHeight / 2.toDouble())
        c6 = Vector(c1.x() - rb.toDouble(), c1.y().toDouble())
        c7 = Vector(c1.x().toDouble()+ rb.toDouble(), c1.y().toDouble())
        n1 = Vector(c1.x().toDouble(), c1.y() - rb.toDouble())
        s1 = Vector(c1.x().toDouble(), c1.y() + rb.toDouble())
        c2 = Vector(c1.x().toDouble() - rs.toDouble(), c1.y().toDouble())
        c3 = Vector(c1.x() + rs.toDouble(), c1.y().toDouble())
        c4 = Vector(c6.x() + rs.toDouble(), c1.y().toDouble())
        c5 = Vector(c7.x() - rs.toDouble(), c1.y().toDouble())
        n6 = Vector(c1.x().toDouble(), n1.y() + rs.toDouble())
        s6 = Vector(c1.x().toDouble(), s1.y() - rs.toDouble())
        n7 = Vector(c1.x().toDouble(), c1.y() - rs.toDouble())
        s7 = Vector(c1.x().toDouble(), c1.y() + rs.toDouble())

        val circle = Circle(c1, rb.toDouble())
        val circle2 = Circle(n1, rs.toDouble())
        recs1 = RectF(
            n1.x() - rs.toFloat(),
            n1.y() - rs.toFloat(),
            n1.x() + rs.toFloat(),
            n1.y() + rs.toFloat()
        )
        val circle3 = Circle(s1, rs.toDouble())
        recs2 = RectF(
            s1.x() - rs.toFloat(),
            s1.y() - rs.toFloat(),
            s1.x() + rs.toFloat(),
            s1.y() + rs.toFloat()
        )
        val circle4 = Circle(c6, rs.toDouble())
        recs3 = RectF(
            c6.x() - rs.toFloat(),
            c6.y() - rs.toFloat(),
            c6.x() + rs.toFloat(),
            c6.y() + rs.toFloat()
        )
        val circle5 = Circle(c7, rs.toDouble())
        recs4 = RectF(
            c7.x() - rs.toFloat(),
            c7.y() - rs.toFloat(),
            c7.x() + rs.toFloat(),
            c7.y() + rs.toFloat()
        )

        val va1:Array<Vector> = CircleCircleIntersection(circle, circle2).getIntersectionPoints()
        n2 = va1[0]
        n3 = va1[1]
        this.sweepAngle = getAngle(
            Point(n1.x().toInt(), n1.y().toInt()), Point(n2.x().toInt(), n2.y().toInt()),
            Point(n3.x().toInt(), n3.y().toInt())
        )
        val sweepAngleRest:Float = ((180.0 - this.sweepAngle) / 2.0f).toFloat()
        startAngel1 = sweepAngleRest

        val va2:Array<Vector> = CircleCircleIntersection(circle, circle3).getIntersectionPoints()
        s2 = va2[0]
        s3 = va2[1]
        startAngel2 = (180.0+sweepAngleRest).toFloat()

        val va3:Array<Vector> = CircleCircleIntersection(circle, circle4).getIntersectionPoints()
        n4 = va3[0]
        s4 = va3[1]
        startAngel3 = (270.0 + sweepAngleRest).toFloat()

        val va4:Array<Vector> = CircleCircleIntersection(circle, circle5).getIntersectionPoints()
        n5 = va4[0]
        s5 = va4[1]
        startAngel4 = (90.0 + sweepAngleRest).toFloat()

        val pc1 = PieceInfo(c1, "c1", rp, PieceStatus.NO_SELECT, listOf(c2, c3, s7, n7))
        pieceList.add(pc1)
        v2p[c1] = pc1

        val pc2 = PieceInfo(c2, "c2", rp, PieceStatus.NO_SELECT, listOf(c1, c4, s7, n7))
        pieceList.add(pc2)
        v2p[c2] = pc2

        val pc3 = PieceInfo(c3, "c3", rp, PieceStatus.NO_SELECT, listOf(c1, c5, s7, n7))
        pieceList.add(pc3)
        v2p[c3] = pc3

        val pc4 = PieceInfo(c4, "c4", rp, PieceStatus.NO_SELECT, listOf(c2, c6, s4, n4))
        pieceList.add(pc4)
        v2p[c4] = pc4

        val pc5 = PieceInfo(c5, "c5", rp, PieceStatus.NO_SELECT, listOf(c3, c7, s5, n5))
        pieceList.add(pc5)
        v2p[c5] = pc5

        val pc6 = PieceInfo(c6, "c6", rp, PieceStatus.NO_SELECT, listOf(c4, s4, n4))
        pieceList.add(pc6)
        v2p[c6] = pc6

        val pc7 = PieceInfo(c7, "c7", rp, PieceStatus.NO_SELECT, listOf(c5, s5, n5))
        pieceList.add(pc7)
        v2p[c7] = pc7

        val pn1 = PieceInfo(n1, "n1", rp, PieceStatus.N_SIDE, listOf(n2, n3, n6))
        pieceList.add(pn1)
        v2p[n1] = pn1

        val pn2 = PieceInfo(n2, "n2", rp, PieceStatus.N_SIDE, listOf(n1, n4, n6))
        pieceList.add(pn2)
        v2p[n2] = pn2

        val pn3 = PieceInfo(n3, "n3", rp, PieceStatus.N_SIDE, listOf(n1, n5, n6))
        pieceList.add(pn3)
        v2p[n3] = pn3

        val pn4 = PieceInfo(n4, "n4", rp, PieceStatus.N_SIDE, listOf(c4, c6, n2))
        pieceList.add(pn4)
        v2p[n4] = pn4

        val pn5 = PieceInfo(n5, "n5", rp, PieceStatus.N_SIDE, listOf(c5, c7, n3))
        pieceList.add(pn5)
        v2p[n5] = pn5


        val pn6 = PieceInfo(n6, "n6", rp, PieceStatus.N_SIDE, listOf(n1, n2, n3, n7))
        pieceList.add(pn6)
        v2p[n6] = pn6

        val pn7 = PieceInfo(n7, "n7", rp, PieceStatus.NO_SELECT, listOf(c1, c2, c3, n6))
        pieceList.add(pn7)
        v2p[n7] = pn7

        val ps1 = PieceInfo(s1, "s1", rp, PieceStatus.S_SIDE, listOf(s2, s3, s6))
        pieceList.add(ps1)
        v2p[s1] = ps1

        val ps2 = PieceInfo(s2, "s2", rp, PieceStatus.S_SIDE, listOf(s1, s4, s6))
        pieceList.add(ps2)
        v2p[s2] = ps2

        val ps3 = PieceInfo(s3, "s3", rp, PieceStatus.S_SIDE, listOf(s1, s5, s6))
        pieceList.add(ps3)
        v2p[s3] = ps3

        val ps4 = PieceInfo(s4, "s4", rp, PieceStatus.S_SIDE, listOf(c4, c6, s2))
        pieceList.add(ps4)
        v2p[s4] = ps4

        val ps5 = PieceInfo(s5, "s5", rp, PieceStatus.S_SIDE, listOf(c5, c7, s3))
        pieceList.add(ps5)
        v2p[s5] = ps5

        val ps6 = PieceInfo(s6, "s6", rp, PieceStatus.S_SIDE, listOf(s1, s2, s3, s7))
        pieceList.add(ps6)
        v2p[s6] = ps6

        val ps7 = PieceInfo(s7, "s1", rp, PieceStatus.NO_SELECT, listOf(c1, c2, c3, s6))
        pieceList.add(ps7)
        v2p[s7] = ps7

        turnInfoVec = Vector((screenWidth/2-rb).toDouble(),(screenHeight/2 - rb).toDouble())
        timeInfoVec = Vector((screenWidth-200).toDouble(),(turnInfoVec.y().toDouble()))
        startTime = SystemClock.elapsedRealtime()
        invalidate()
    }

    private fun drawElapsedTime(canvas: Canvas, elapsedSecond: Double, x: Float, y: Float) {
        val min = (elapsedSecond.toInt()) / 60
        val second = (elapsedSecond.toInt()) % 60
        paintText.let {
            canvas.drawText(
                String.format("%d%d:%d%d", min / 10, min % 10, second / 10, second % 10), x, y, it
            )
        }
    }
    /* access modifiers changed from: protected */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!this.isInit) {
            init(this.currentPlayMode)
            this.isInit = true
        }
        canvas.drawPaint(this.paintBackground)
        drawElapsedTime(
            canvas,
            (SystemClock.elapsedRealtime() - this.startTime) / 1000.0,
            this.timeInfoVec.x(),
            this.timeInfoVec.y()
        )
        if (this.currentTurn == PieceStatus.N_SIDE) {
            canvas.drawCircle(this.turnInfoVec.x(), this.turnInfoVec.y(), this.rp.toFloat(), this.paintN)
        } else {
            canvas.drawCircle(this.turnInfoVec.x(), this.turnInfoVec.y(), this.rp.toFloat(), this.paintS)
        }
        canvas.drawCircle(this.c1.x(), this.c1.y(), this.rb.toFloat(), this.paintBoard)
        canvas.drawCircle(this.c1.x(), this.c1.y(), this.rs.toFloat(), this.paintBoard)
        canvas.drawLine(this.n1.x(), this.n1.y(), this.s1.x(), this.s1.y(), this.paintBoard)
        canvas.drawLine(this.c6.x(), this.c6.y(), this.c7.x(), this.c7.y(), this.paintBoard)
        canvas.drawArc(this.recs1, this.startAngel1, this.sweepAngle.toFloat(), false, this.paintBoard)
        canvas.drawArc(this.recs2, this.startAngel2, this.sweepAngle.toFloat(), false, this.paintBoard)
        canvas.drawArc(this.recs3, this.startAngel3, this.sweepAngle.toFloat(), false, this.paintBoard)
        canvas.drawArc(this.recs4, this.startAngel4, this.sweepAngle.toFloat(), false, this.paintBoard)
        for (pi in this.pieceList) {
            if (pi.getCurrentStatus() != PieceStatus.NO_SELECT) {
                if (pi.getCurrentStatus() == PieceStatus.N_SIDE) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintN)
                } else if (pi.getCurrentStatus() == PieceStatus.S_SIDE) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintS)
                } else if (pi.getCurrentStatus() == PieceStatus.N_SELECTING) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintNS)
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintN)
                } else if (pi.getCurrentStatus() == PieceStatus.S_SELECTING) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintSS)
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintS)
                } else if (pi.getCurrentStatus() == PieceStatus.N_TEMP) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintNT)
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintN)
                } else if (pi.getCurrentStatus() == PieceStatus.S_TEMP) {
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintST)
                    canvas.drawCircle(pi.getVector().x(), pi.getVector().y(), this.rp.toFloat(), this.paintS)
                }
            }
        }
        invalidate()
    }
    private fun isKilled(
        vAdj:Vector,
        vMe:Vector,
        ps:PieceStatus,
        listCheckedV:MutableList<Vector>,
        listKillV:MutableList<Vector>,
        v2pClone:HashMap<Vector,PieceInfo>):Boolean{
        val piAdj = v2pClone[vAdj]
        val piMe = v2pClone[vMe]
        var c =" "
        listCheckedV.forEach { v-> c += "${v2pClone[v]?.getTag()}" }
        var k = " "
        listCheckedV.forEach { v2 -> k += "${v2pClone[v2]?.getTag()}" }

        Log.d(GV.TAG, String.format("isKilled check %s %s ,%s, %s",piAdj?.getTag(),piMe?.getTag(),c,k))

        var isKilled = true
        for (vAdjAdj in piAdj?.getAdjacentVectorList() ?: emptyList()) {
            if(vAdjAdj != vMe){
                val piAdjAdj = v2pClone[vAdjAdj]
                if(piAdjAdj?.getCurrentStatus() == PieceStatus.NO_SELECT){
                    isKilled = false
                    break
                }else if(piAdjAdj?.getCurrentStatus() != ps && !listCheckedV.contains(vAdjAdj)){
                    listCheckedV.add(vAdjAdj)
                    if(!isKilled(vAdjAdj,vAdj,ps,listCheckedV,listCheckedV,v2pClone)){
                        isKilled = false
                        break
                    }
                }
            }
        }
        if(isKilled){
            listKillV.add(vAdj)
        }
        return isKilled
    }

    private fun checkAnyPieceIsKilled(pi:PieceInfo,vec2pieceInfo:HashMap<Vector,PieceInfo>):Int{
        val psComp:PieceStatus =   if(pi.getCurrentStatus() == PieceStatus.N_SIDE){
            PieceStatus.S_SIDE
        }else{
            PieceStatus.N_SIDE
        }
        var killNum:Int = 0

        val listAdjV: List<Vector> = pi.getAdjacentVectorList()
        val listKilledV: MutableList<Vector> = mutableListOf()
        for (vAdj in listAdjV){
            if(vec2pieceInfo[vAdj]?.getCurrentStatus() == psComp){
                val listCheckedV : MutableList<Vector> = mutableListOf()
                val listKilledVTemp :MutableList<Vector> = mutableListOf()
                if(isKilled(vAdj,pi.getVector(),pi.getCurrentStatus(), listCheckedV,listKilledVTemp,vec2pieceInfo)){
                    listKilledVTemp.add(vAdj)
                    listKilledV.addAll(listKilledVTemp)
                    var k :String = " "
                    for (v2 in listKilledV){
                        k += vec2pieceInfo[v2]?.getTag()+" "

                    }
                    Log.d(GV.TAG, String.format("check %s and find it's killed, %s", vec2pieceInfo[vAdj]?.getTag(), k))
                }else{
                    Log.d(GV.TAG, String.format("check %s and find it's not killed, %s",vAdj, vec2pieceInfo[vAdj]?.getTag()))
                }
            }
        }
        for (v3 in listKilledV) {
            if (vec2pieceInfo[v3]?.getCurrentStatus() == PieceStatus.N_SIDE) {
                killNum++
            }
            vec2pieceInfo[v3]?.setCurrentStatus(PieceStatus.NO_SELECT)
        }
       return killNum
    }
    private fun checkGameisFinished(){
        this.restCountN = 0
        this.restCountS = 0
        val iterator = pieceList.iterator()
        while (iterator.hasNext()){
            val pi2 = iterator.next()
            if(pi2.getCurrentStatus() == PieceStatus.N_SIDE){
                restCountN++
            }else if(pi2.getCurrentStatus() == PieceStatus.S_SIDE){
                restCountS++
            }
        }
        if(this.restCountS<=2){
            this.winner = PieceStatus.N_SIDE
        }else if(this.restCountN <= 2){
            this.winner = PieceStatus.S_SIDE
        }
        if(this.winner != null){
            if(this.currentPlayMode != PlayMode.DOUBLE){
                val rd:Recordetail = Record.getRecord(context,this.currentPlayMode)
                if(this.winner == PieceStatus.N_SIDE){
                    rd.addLose()
                }else{
                    rd.addWin()
                }
                Record.saveRecord(context,this.currentPlayMode,rd)

            }
            this.parentActivity?.showGameFinishDialog(this.winner!!,currentPlayMode)
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.mCrurrX = event.x.toInt()
        this.mCrurrY = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(GV.TAG, "ACTION_DOWN ${this.mCrurrX}, ${this.mCrurrY}")
                if (this.currentSelectingPiece != null) {
                    if (this.currentSelectingPiece!!.getCurrentStatus() == PieceStatus.N_SELECTING) {
                        this.currentSelectingPiece!!.setCurrentStatus(PieceStatus.N_SIDE)
                    } else {
                        this.currentSelectingPiece!!.setCurrentStatus(PieceStatus.S_SIDE)
                    }
                    this.currentSelectingPiece = null
                    if (this.currentTempTargetPiece != null) {
                        this.currentTempTargetPiece!!.setCurrentStatus(PieceStatus.NO_SELECT)
                        this.currentTempTargetPiece = null
                    }
                } else {
                    for (pi in this.pieceList) {
                        if (pi.isContainPoint(this.mCrurrX, this.mCrurrY) && pi.getCurrentStatus() == this.currentTurn) {
                            if (this.currentTurn == PieceStatus.N_SIDE) {
                                pi.setCurrentStatus(PieceStatus.N_SELECTING)
                            } else {
                                pi.setCurrentStatus(PieceStatus.S_SELECTING)
                            }
                            this.currentSelectingPiece = pi
                            this.mpSelect?.start()
                            break
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(GV.TAG, "ACTION_UP ${this.mCrurrX}, ${this.mCrurrY}")
                if (this.currentTempTargetPiece != null) {
                    this.currentTempTargetPiece!!.setCurrentStatus(PieceStatus.NO_SELECT)
                    this.currentTempTargetPiece = null
                }
                if (this.currentSelectingPiece != null) {
                    for (pi2 in this.pieceList) {
                        if (pi2.isContainPoint(this.mCrurrX, this.mCrurrY) && pi2.getCurrentStatus() == PieceStatus.NO_SELECT
                            && pi2.isAdjacentContain(this.currentSelectingPiece!!.getVector())) {
                            if (this.currentSelectingPiece!!.getCurrentStatus() == PieceStatus.N_SELECTING) {
                                pi2.setCurrentStatus(PieceStatus.N_SIDE)
                            } else {
                                pi2.setCurrentStatus(PieceStatus.S_SIDE)
                            }
                            this.currentSelectingPiece!!.setCurrentStatus(PieceStatus.NO_SELECT)
                            this.currentSelectingPiece = null
                            if (this.currentTurn == PieceStatus.N_SIDE) {
                                this.currentTurn = PieceStatus.S_SIDE
                            } else {
                                this.currentTurn = PieceStatus.N_SIDE
                            }
                            this.mpMove?.start()
                            val checkAnyPieceIsKilled = checkAnyPieceIsKilled(pi2, this.v2p)
                            checkGameisFinished()
                            val hi = HistoryItem()
                            hi.currentTurn = this.currentTurn
                            hi.restCountN = this.restCountN
                            hi.restCountS = this.restCountS
                            val pieceListClone = ArrayList<PieceInfo>()
                            for (pi22 in this.pieceList) {
                                pieceListClone.add(PieceInfo(pi22.getVector(), pi22.getTag(), pi22.getRp(), pi22.getCurrentStatus(), pi22.getAdjacentVectorList()))
                            }
                            hi.mPieceList = pieceListClone
                            this.historyofPieceList.push(hi)
                            this.isRegreting = false
                        }
                    }
                    if (this.currentSelectingPiece != null) {
                        if (this.currentSelectingPiece!!.getCurrentStatus() == PieceStatus.N_SELECTING) {
                            this.currentSelectingPiece!!.setCurrentStatus(PieceStatus.N_SIDE)
                        } else {
                            this.currentSelectingPiece!!.setCurrentStatus(PieceStatus.S_SIDE)
                        }
                        this.currentSelectingPiece = null
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (this.currentSelectingPiece != null) {
                    for (pi3 in this.pieceList) {
                        if (pi3.isContainPoint(this.mCrurrX, this.mCrurrY) && pi3.getCurrentStatus() == PieceStatus.NO_SELECT && pi3.isAdjacentContain(this.currentSelectingPiece!!.getVector())) {
                            if (this.currentSelectingPiece!!.getCurrentStatus() == PieceStatus.N_SELECTING) {
                                pi3.setCurrentStatus(PieceStatus.N_TEMP)
                            } else {
                                pi3.setCurrentStatus(PieceStatus.S_TEMP)
                            }
                            if (this.currentTempTargetPiece != null) {
                                this.currentTempTargetPiece!!.setCurrentStatus( PieceStatus.NO_SELECT)
                                this.currentTempTargetPiece = null
                            }
                            this.currentTempTargetPiece = pi3
                        }
                    }
                }
            }
        }
        invalidate()
        return true
    }


}






