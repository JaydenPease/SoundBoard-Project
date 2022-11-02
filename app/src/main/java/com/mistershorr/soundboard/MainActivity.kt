package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mistershorr.soundboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"


    //instance variable for the view binding
    private lateinit var binding: ActivityMainBinding
    
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
        // define the binding  variable
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initializeSoundPool()
        setListeners()
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        binding.buttonMainA.setOnClickListener(soundBoardListener)
        binding.buttonMainBb.setOnClickListener(soundBoardListener)
        binding.buttonMainB.setOnClickListener(soundBoardListener)
        binding.buttonMainC.setOnClickListener(soundBoardListener)
        binding.buttonMainCs.setOnClickListener(soundBoardListener)
        binding.buttonMainD.setOnClickListener(soundBoardListener)
        binding.buttonMainDs.setOnClickListener(soundBoardListener)
        binding.buttonMainE.setOnClickListener(soundBoardListener)
        binding.buttonMainF.setOnClickListener(soundBoardListener)
        binding.buttonMainFs.setOnClickListener(soundBoardListener)
        binding.buttonMainG.setOnClickListener(soundBoardListener)
        binding.buttonMainGs.setOnClickListener(soundBoardListener)
        binding.buttonMainLg.setOnClickListener((soundBoardListener))
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
        CsNote = soundPool.load(this, R.raw.scalecs, 1)
        DNote = soundPool.load(this, R.raw.scaled, 1)
        DsNote = soundPool.load(this, R.raw.scaleds, 1)
        ENote = soundPool.load(this, R.raw.scalee, 1)
        FNote = soundPool.load(this, R.raw.scalef, 1)
        FsNote = soundPool.load(this, R.raw.scalefs, 1)
        GNote = soundPool.load(this, R.raw.scaleg, 1)
        GsNote = soundPool.load(this, R.raw.scalegs, 1)
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
                R.id.button_main_cs -> playNote(CsNote)
                R.id.button_main_d -> playNote(DNote)
                R.id.button_main_ds -> playNote(DsNote)
                R.id.button_main_e -> playNote(ENote)
                R.id.button_main_f -> playNote(FNote)
                R.id.button_main_fs -> playNote(FsNote)
                R.id.button_main_g -> playNote(GNote)
                R.id.button_main_gs -> playNote(GsNote)
            }
        }

    }

    private fun playSong(song: List<Note>) {
        //loop through a list of notes
          //play the note
                //note you get is a string
                //to play the note, you need the corresponding soundPool object
           //delay for the delay
    }

    private fun delay(time: Long) {
        try{
            Thread.sleep(time)
        } catch(e: InterruptedException) {
            e.printStackTrace()
        }

    }
}