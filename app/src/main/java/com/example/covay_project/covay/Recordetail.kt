package com.example.covay_project.covay

class Recordetail( var giveup: Int, var lose: Int, var win: Int) {
    fun addWin() {
        win++
    }

    fun addLose() {
        lose++
    }

    fun addGiveup() {
        giveup++
    }

    override fun toString(): String {
        return "RecordDetail [Win=$win, Lose=$lose, Give Up=$giveup]"
    }
}
