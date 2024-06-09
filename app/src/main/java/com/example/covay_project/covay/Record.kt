package com.example.covay_project.covay


import android.content.Context
import org.json.JSONException
import org.json.JSONObject

class Record {
    companion object {
        private fun getPlayModeStr(pm: PlayMode): String? {
            return when (pm) {
                PlayMode.SINGLE_0 -> "single0"
                PlayMode.SINGLE_1 -> "single1"
                PlayMode.SINGLE_2 -> "single2"
                else -> null
            }
        }

        fun getRecord(c: Context, pm: PlayMode): Recordetail {
            val recordDetailStr =
                c.getSharedPreferences(c.packageName, 0).getString(getPlayModeStr(pm), null)
                    ?: return Recordetail(0, 0, 0)
            return try {
                val jObject = JSONObject(recordDetailStr)
                val w = jObject.optInt("win", 0)
                val l = jObject.optInt("lose", 0)
                val g = jObject.optInt("give up", 0)
                Recordetail(w, l, g)
            } catch (e: JSONException) {
                Recordetail(0, 0, 0)
            }
        }

        fun saveRecord(c: Context, pm: PlayMode, rd: Recordetail) {
            c.getSharedPreferences(c.packageName, 0).edit().putString(
                getPlayModeStr(pm),
                "{\"Win\":${rd.win}, \"Lose\":${rd.lose}, \"Give Up\":${rd.giveup}}"
            ).apply()
        }
    }
}

