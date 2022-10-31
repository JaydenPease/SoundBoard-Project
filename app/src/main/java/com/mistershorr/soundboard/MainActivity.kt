package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var buttonA : Button
    lateinit var buttonBb : Button
    lateinit var buttonB : Button
    lateinit var buttonC : Button
    lateinit var buttonCs: Button
    lateinit var buttonD: Button
    lateinit var buttonDs: Button
    lateinit var buttonE: Button
    lateinit var buttonF: Button
    lateinit var buttonFs: Button
    lateinit var buttonG: Button
    lateinit var buttonGs: Button
    
    lateinit var soundPool : SoundPool
    var ANote = 0
    var BbNote = 0
    var BNote = 0
    var CNote = 0
    var CsNote = 0
    var DNote = 0
    var DsNote = 0
    var ENote = 0
    var FNote = 0
    var FsNote = 0
    var GNote = 0
    var GsNote = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()
        initializeSoundPool()
        setListeners()
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        buttonA.setOnClickListener(soundBoardListener)
        buttonBb.setOnClickListener(soundBoardListener)
        buttonB.setOnClickListener(soundBoardListener)
        buttonC.setOnClickListener(soundBoardListener)
    }


    private fun wireWidgets() {
        buttonA = findViewById(R.id.button_main_a)
        buttonBb = findViewById(R.id.button_main_bb)
        buttonB = findViewById(R.id.button_main_b)
        buttonC = findViewById(R.id.button_main_c)
    }

    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
//        })
        ANote = soundPool.load(this, R.raw.scalea, 1)
        BbNote = soundPool.load(this, R.raw.scalebb, 1)
        BNote = soundPool.load(this, R.raw.scaleb, 1)
        CNote =  soundPool.load(this, R.raw.scalec, 1)
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.button_main_a -> playNote(ANote)
                R.id.button_main_bb -> playNote(BbNote)
                R.id.button_main_b -> playNote(BNote)
                R.id.button_main_c -> playNote(CNote)
            }
        }
    }
}