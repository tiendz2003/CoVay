package com.example.covay_project.covay

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.covay_project.R


class BoardActivity : AppCompatActivity() {
    private lateinit var btnRegret:Button
    private lateinit var btnRestart:Button
    private lateinit var bv:WatermelonChessBoardView
    private var gameTypeLatest:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_chess_board)
        bv = findViewById(R.id.board_view) ?: return
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showGameGiveUpDialog(true)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        // Move bv initialization here
        val b = intent.extras
        gameTypeLatest = b?.getInt("Type") ?: 0
        when (gameTypeLatest) {
            -1 -> bv.setCurrentPlayMode(PlayMode.DOUBLE)
            0 -> bv.setCurrentPlayMode(PlayMode.SINGLE_0)
            1 -> bv.setCurrentPlayMode(PlayMode.SINGLE_1)
            2 -> bv.setCurrentPlayMode(PlayMode.SINGLE_2)
        }
        bv.setParentActivity(this)

        // Now you can safely use bv
        btnRestart = findViewById(R.id.button1)
        btnRestart.setOnClickListener {
            showCustomDiaLog(false)
        }
        btnRegret = findViewById(R.id.button2)
        btnRegret.setOnClickListener {
            bv.regret()
        }
    }
    fun showGameFinishDialog(winner: PieceStatus, pm: PlayMode) {
        val finishGameContentStr: String = when {
            pm == PlayMode.DOUBLE -> {
                if (winner == PieceStatus.N_SIDE) {
                    resources.getString(R.string.finish_game_double_n_win)
                } else {
                    resources.getString(R.string.finish_game_double_s_win)
                }
            }
            winner == PieceStatus.N_SIDE -> resources.getString(R.string.finish_game_content_lose)
            else -> resources.getString(R.string.finish_game_content_win)
        }

        val finishGameTitleStr = resources.getString(R.string.finish_game_title)
        val newGameStr = resources.getString(R.string.finish_game_start_new)
        val backMainStr = resources.getString(R.string.finish_game_back_main)

        AlertDialog.Builder(this)
            .setMessage(finishGameContentStr)
            .setTitle(finishGameTitleStr)
            .setPositiveButton(newGameStr) { dialog, _ ->
                dialog.dismiss()
                bv.init()
            }
            .setNegativeButton(backMainStr) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .create()
            .show()
    }
   private fun showCustomDiaLog(isBack:Boolean){
       val dialog = Dialog(this)
       dialog.setContentView(R.layout.custom_dialog_layout)
       val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
       val btnYes = dialog.findViewById<Button>(R.id.btnYes)
       val btnNo = dialog.findViewById<Button>(R.id.btnNo)

       tvMessage.text = "Bạn muốn bỏ cuộc ?"
       btnYes.text = "Đồng ý"
       btnNo.text = "Không"

       btnYes.setOnClickListener {
           if(bv.getCurrentPlaymode() != PlayMode.DOUBLE){
               val rd = Record.getRecord(this,bv.getCurrentPlaymode())
               rd.addGiveup()
               Record.saveRecord(this,bv.getCurrentPlaymode(),rd)
           }
           dialog.dismiss()

           if(isBack){
               finish()
           }else{
               bv.init()
           }
       }
       btnNo.setOnClickListener {
           dialog.dismiss()
       }
       dialog.show()
   }
    private fun showGameGiveUpDialog(isBack :Boolean){
        if(bv.getHistoryPieceList().size != 0){
            showCustomDiaLog(isBack)
        }else if(isBack){
            finish()
        }else{
            finish()
        }
    }
}



