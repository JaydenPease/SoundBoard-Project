package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"


    //instance variable for the view binding
    private lateinit var binding: ActivityMainBinding

    var noteValues = NoteValues()

    lateinit var soundPool : SoundPool
    lateinit var song: MutableList<Note>

    var songBeingWritten: ArrayList<Note> = ArrayList()


    var currentlyWriting: Boolean = false

//    var ANote = 1
//    var BbNote = 2
//    var BNote = 3
//    var CNote = 4
//    var CsNote = 5
//    var DNote = 6
//    var DsNote = 7
//    var ENote = 8
//    var FNote = 9
//    var FsNote = 10
//    var GNote = 11
//    var GsNote = 12
//    var LowGNote = 13






    var noteMap = HashMap<String, Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // define the binding  variable
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initializeSoundPool()
        loadSong()
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

        binding.buttonMainPlaySong.setOnClickListener(soundBoardListener)


        binding.buttonMainStartStopWriting.setOnClickListener {
            currentlyWriting = !currentlyWriting
        }


    }




    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
//        })
        noteValues.a0 = soundPool.load(this, R.raw.a0, 1)
        noteValues.a1 = soundPool.load(this, R.raw.a1, 1)

//        BNote = soundPool.load(this, R.raw.scaleb, 1)
//        CNote =  soundPool.load(this, R.raw.scalec, 1)
//        CsNote = soundPool.load(this, R.raw.scalecs, 1)
//        DNote = soundPool.load(this, R.raw.scaled, 1)
//        DsNote = soundPool.load(this, R.raw.scaleds, 1)
//        ENote = soundPool.load(this, R.raw.scalee, 1)
//        FNote = soundPool.load(this, R.raw.scalef, 1)
//        FsNote = soundPool.load(this, R.raw.scalefs, 1)
//        GNote = soundPool.load(this, R.raw.scaleg, 1)
//        GsNote = soundPool.load(this, R.raw.scalegs, 1)
//        LowGNote = soundPool.load(this, R.raw.scalelowg, 1)

        // Maps use key-value pairs (just like the Bundle)
        noteMap.put("A", ANote)
        //kotlin lets you use array-like assignments
        noteMap["Bb"] = BbNote
        noteMap["B"] = BNote
        noteMap["C"] = CNote
        noteMap["Cs"] = CsNote
        noteMap["D"] = DNote
        noteMap["Ds"] = DsNote
        noteMap["E"] = ENote
        noteMap["F"] = FNote
        noteMap["Fs"] = FsNote
        noteMap["G"] = GNote
        noteMap["Gs"] = GsNote
        noteMap["LG"] = LowGNote

    }

    private fun playNote(note: String) {
        // ?: is the elvis operator. it lets you provide a default value if the value is null
        playNote(noteMap[note] ?: 0)
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if(currentlyWriting) {

                when(v?.id) {
                    R.id.button_main_a -> {
                        playNote(ANote)
                        songBeingWritten.add(Note("A", 500))
                    }
                    R.id.button_main_bb -> {
                        playNote(BbNote)
                        songBeingWritten.add(Note("Bb", 500))
                    }
                    R.id.button_main_b -> {
                        playNote(BNote)
                        songBeingWritten.add(Note("B", 500))
                    }
                    R.id.button_main_c -> {
                        playNote(CNote)
                        songBeingWritten.add(Note("C", 500))
                    }
                    R.id.button_main_cs -> {
                        playNote(CsNote)

                    }
                    R.id.button_main_d -> {
                        playNote(DNote)
                    }
                    R.id.button_main_ds -> {
                        playNote(DsNote)
                    }
                    R.id.button_main_e -> {
                        playNote(ENote)
                    }
                    R.id.button_main_f -> {
                        playNote(FNote)
                    }
                    R.id.button_main_fs -> {
                        playNote(FsNote)
                    }
                    R.id.button_main_g -> {
                        playNote(GNote)
                    }
                    R.id.button_main_gs -> {
                        playNote(GsNote)
                    }
                    R.id.button_main_lg -> {
                        playNote(LowGNote)
                    }


                }

            }
            else {

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
                R.id.button_main_lg -> playNote(LowGNote)

                R.id.button_main_playSong -> {
                    //launch a coroutine
                    GlobalScope.launch {
                        playSong(song)
                    }
                }
            }
            }
        }

    }

    // suspend keyword marks the function to be used in a coroutine
    // (so that the default delay function can work)
    private suspend fun playSong(song: List<Note>) {



        //0..10 - 0 to 10 inclusive both sides
        //0 until 10 - 0 to 9
        //i in song.indices - goes through the entire list
        //for(item in list) is the enhanced for loop
        //java is superior to kotlin because you can go BACKWARDS, SIDEWAYS, and ALL WAYS
        for(i in song.indices) {
            playNote(song[i].note)
            delay(song[i].duration)
        }


        //loop through a list of notes
          //play the note
                //note you get is a string
                //to play the note, you need the corresponding soundPool object
           //delay for the delay
    }



    private fun loadSong() {

        val inputStream = resources.openRawResource(R.raw.song)

        val jsonString = inputStream.bufferedReader().use {

            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonString")




        val gson = Gson()


        val type = object : TypeToken<List<Note>>() { }.type
        song = gson.fromJson<List<Note>>(jsonString, type) as MutableList<Note>
    }
}