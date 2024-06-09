package com.example.covay_project.covay

import com.example.covay_project.graphic.Vector


class PieceInfo(v: Vector, tag: String, rp2: Int, s: PieceStatus, list: List<Vector>) {
    private var mAdjacentVectorList: List<Vector> = ArrayList()
    private var mStatus: PieceStatus = PieceStatus.NO_SELECT
    private var mTag: String = ""
    private var mVec: Vector
    private var rp: Int

    init {
        mVec = v
        mStatus = s
        mAdjacentVectorList = list
        rp = rp2
        mTag = tag
    }
    fun getposition(): Pair<Int, Int> {
        return Pair(mVec.x().toInt(), mVec.y().toInt())
    }
    val position: Pair<Int, Int>
        get() = getposition()
    fun getRp(): Int {
        return rp
    }

    fun getVector(): Vector {
        return mVec
    }

    fun getCurrentStatus(): PieceStatus {
        return mStatus
    }

    fun setCurrentStatus(currentStatus: PieceStatus) {
        mStatus = currentStatus
    }

    fun getAdjacentVectorList(): List<Vector> {
        return mAdjacentVectorList
    }

    fun isAdjacentContain(vv: Vector): Boolean {
        return mAdjacentVectorList.any { v -> v == vv }
    }

    fun isContainPoint(x: Int, y: Int): Boolean {
        val r = rp * 1.5f
        return x.toFloat() in mVec.x() - r..mVec.x() + r &&
                y.toFloat() in mVec.y() - r..mVec.y() + r
    }

    fun getTag(): String {
        return mTag
    }
}
