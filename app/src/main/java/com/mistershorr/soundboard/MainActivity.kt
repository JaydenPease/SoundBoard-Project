//finish octave changing

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

    var octave: Int = 1

    var currentlyWriting: Boolean = false

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
        binding.buttonMainAsbb.setOnClickListener(soundBoardListener)
        binding.buttonMainB.setOnClickListener(soundBoardListener)
        binding.buttonMainC.setOnClickListener(soundBoardListener)
        binding.buttonMainCsdb.setOnClickListener(soundBoardListener)
        binding.buttonMainD.setOnClickListener(soundBoardListener)
        binding.buttonMainDseb.setOnClickListener(soundBoardListener)
        binding.buttonMainE.setOnClickListener(soundBoardListener)
        binding.buttonMainF.setOnClickListener(soundBoardListener)
        binding.buttonMainFsgb.setOnClickListener(soundBoardListener)
        binding.buttonMainG.setOnClickListener(soundBoardListener)
        binding.buttonMainGsab.setOnClickListener(soundBoardListener)

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

        loadSoundPoolThingy1()

        loadSoundPoolThingy2()

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

//                when(v?.id) {
//                    R.id.button_main_a -> {
//                        playNote(ANote)
//                        songBeingWritten.add(Note("A", 500))
//                    }
//                    R.id.button_main_bb -> {
//                        playNote(BbNote)
//                        songBeingWritten.add(Note("Bb", 500))
//                    }
//                    R.id.button_main_b -> {
//                        playNote(BNote)
//                        songBeingWritten.add(Note("B", 500))
//                    }
//                    R.id.button_main_c -> {
//                        playNote(CNote)
//                        songBeingWritten.add(Note("C", 500))
//                    }
//                    R.id.button_main_cs -> {
//                        playNote(CsNote)
//
//                    }
//                    R.id.button_main_d -> {
//                        playNote(DNote)
//                    }
//                    R.id.button_main_ds -> {
//                        playNote(DsNote)
//                    }
//                    R.id.button_main_e -> {
//                        playNote(ENote)
//                    }
//                    R.id.button_main_f -> {
//                        playNote(FNote)
//                    }
//                    R.id.button_main_fs -> {
//                        playNote(FsNote)
//                    }
//                    R.id.button_main_g -> {
//                        playNote(GNote)
//                    }
//                    R.id.button_main_gs -> {
//                        playNote(GsNote)
//                    }
//
//
//
//                }

            }
            else {

//            when(v?.id) {
//                R.id.button_main_a -> playNote(ANote)
//                R.id.button_main_bb -> playNote(BbNote)
//                R.id.button_main_b -> playNote(BNote)
//                R.id.button_main_c -> playNote(CNote)
//                R.id.button_main_cs -> playNote(CsNote)
//                R.id.button_main_d -> playNote(DNote)
//                R.id.button_main_ds -> playNote(DsNote)
//                R.id.button_main_e -> playNote(ENote)
//                R.id.button_main_f -> playNote(FNote)
//                R.id.button_main_fs -> playNote(FsNote)
//                R.id.button_main_g -> playNote(GNote)
//                R.id.button_main_gs -> playNote(GsNote)

//
//                R.id.button_main_playSong -> {
//                    //launch a coroutine
//                    GlobalScope.launch {
//                        playSong(song)
//                    }
//                }
//            }
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


    private fun loadSoundPoolThingy1() {
        noteValues.a0 = soundPool.load(this, R.raw.a0, 1)
        noteValues.a1 = soundPool.load(this, R.raw.a1, 1)
        noteValues.a2 = soundPool.load(this, R.raw.a2, 1)
        noteValues.a3 = soundPool.load(this, R.raw.a3, 1)
        noteValues.a4 = soundPool.load(this, R.raw.a4, 1)
        noteValues.a5 = soundPool.load(this, R.raw.a5, 1)
        noteValues.asbb0 = soundPool.load(this, R.raw.asbb0, 1)
        noteValues.asbb1 = soundPool.load(this, R.raw.asbb1, 1)
        noteValues.asbb2 = soundPool.load(this, R.raw.asbb2, 1)
        noteValues.asbb3 = soundPool.load(this, R.raw.asbb3, 1)
        noteValues.asbb4 = soundPool.load(this, R.raw.asbb4, 1)
        noteValues.asbb5 = soundPool.load(this, R.raw.asbb5, 1)
        noteValues.b0 = soundPool.load(this, R.raw.b0, 1)
        noteValues.b1 = soundPool.load(this, R.raw.b1, 1)
        noteValues.b2 = soundPool.load(this, R.raw.b2, 1)
        noteValues.b3 = soundPool.load(this, R.raw.b3, 1)
        noteValues.b4 = soundPool.load(this, R.raw.b4, 1)
        noteValues.b5 = soundPool.load(this, R.raw.b5, 1)
        noteValues.c1 = soundPool.load(this, R.raw.c1, 1)
        noteValues.c2 = soundPool.load(this, R.raw.c2, 1)
        noteValues.c3 = soundPool.load(this, R.raw.c3, 1)
        noteValues.c4 = soundPool.load(this, R.raw.c4, 1)
        noteValues.c5 = soundPool.load(this, R.raw.c5, 1)
        noteValues.c6 = soundPool.load(this, R.raw.c6, 1)
        noteValues.csdb1 = soundPool.load(this, R.raw.csdb1, 1)
        noteValues.csdb2 = soundPool.load(this, R.raw.csdb2, 1)
        noteValues.csdb3 = soundPool.load(this, R.raw.csdb3, 1)
        noteValues.csdb4 = soundPool.load(this, R.raw.csdb4, 1)
        noteValues.csdb5 = soundPool.load(this, R.raw.csdb5, 1)
        noteValues.d1 = soundPool.load(this, R.raw.d1, 1)
        noteValues.d2 = soundPool.load(this, R.raw.d2, 1)
        noteValues.d3 = soundPool.load(this, R.raw.d3, 1)
        noteValues.d4 = soundPool.load(this, R.raw.d4, 1)
        noteValues.d5 = soundPool.load(this, R.raw.d5, 1)
        noteValues.dseb1 = soundPool.load(this, R.raw.dseb1, 1)
        noteValues.dseb2 = soundPool.load(this, R.raw.dseb2, 1)
        noteValues.dseb3 = soundPool.load(this, R.raw.dseb3, 1)
        noteValues.dseb4 = soundPool.load(this, R.raw.dseb4, 1)
        noteValues.dseb5 = soundPool.load(this, R.raw.dseb5, 1)
        noteValues.e1 = soundPool.load(this, R.raw.e1, 1)
        noteValues.e2 = soundPool.load(this, R.raw.e2, 1)
        noteValues.e3 = soundPool.load(this, R.raw.e3, 1)
        noteValues.e4 = soundPool.load(this, R.raw.e4, 1)
        noteValues.e5 = soundPool.load(this, R.raw.e5, 1)
        noteValues.f1 = soundPool.load(this, R.raw.f1, 1)
        noteValues.f2 = soundPool.load(this, R.raw.f2, 1)
        noteValues.f3 = soundPool.load(this, R.raw.f3, 1)
        noteValues.f4 = soundPool.load(this, R.raw.f4, 1)
        noteValues.f5 = soundPool.load(this, R.raw.f5, 1)
        noteValues.fsgb1 = soundPool.load(this, R.raw.fsgb1, 1)
        noteValues.fsgb2 = soundPool.load(this, R.raw.fsgb2, 1)
        noteValues.fsgb3 = soundPool.load(this, R.raw.fsgb3, 1)
        noteValues.fsgb4 = soundPool.load(this, R.raw.fsgb4, 1)
        noteValues.fsgb5 = soundPool.load(this, R.raw.fsgb5, 1)
        noteValues.g1 = soundPool.load(this, R.raw.g1, 1)
        noteValues.g2 = soundPool.load(this, R.raw.g2, 1)
        noteValues.g3 = soundPool.load(this, R.raw.g3, 1)
        noteValues.g4 = soundPool.load(this, R.raw.g4, 1)
        noteValues.g5 = soundPool.load(this, R.raw.g5, 1)
        noteValues.gsab1 = soundPool.load(this, R.raw.gsab1, 1)
        noteValues.gsab2 = soundPool.load(this, R.raw.gsab2, 1)
        noteValues.gsab3 = soundPool.load(this, R.raw.gsab3, 1)
        noteValues.gsab4 = soundPool.load(this, R.raw.gsab4, 1)
        noteValues.gsab5 = soundPool.load(this, R.raw.gsab5, 1)



    }

    private fun loadSoundPoolThingy2() {
        // Maps use key-value pairs (just like the Bundle)
        //kotlin lets you use array-like assignments
        noteMap["a0"] = noteValues.a0
        noteMap["a1"] = noteValues.a1
        noteMap["a2"] = noteValues.a2
        noteMap["a3"] = noteValues.a3
        noteMap["a4"] = noteValues.a4
        noteMap["a5"] = noteValues.a5
        noteMap["asbb0"] = noteValues.asbb0
        noteMap["asbb1"] = noteValues.asbb1
        noteMap["asbb2"] = noteValues.asbb2
        noteMap["asbb3"] = noteValues.asbb3
        noteMap["asbb4"] = noteValues.asbb4
        noteMap["asbb5"] = noteValues.asbb5
        noteMap["b0"] = noteValues.b0
        noteMap["b1"] = noteValues.b1
        noteMap["b2"] = noteValues.b2
        noteMap["b3"] = noteValues.b3
        noteMap["b4"] = noteValues.b4
        noteMap["b5"] = noteValues.b5
        noteMap["c1"] = noteValues.c1
        noteMap["c2"] = noteValues.c2
        noteMap["c3"] = noteValues.c3
        noteMap["c4"] = noteValues.c4
        noteMap["c5"] = noteValues.c5
        noteMap["c6"] = noteValues.c6
        noteMap["csdb1"] = noteValues.csdb1
        noteMap["csdb2"] = noteValues.csdb2
        noteMap["csdb3"] = noteValues.csdb3
        noteMap["csdb4"] = noteValues.csdb4
        noteMap["csdb5"] = noteValues.csdb5
        noteMap["d1"] = noteValues.d1
        noteMap["d2"] = noteValues.d2
        noteMap["d3"] = noteValues.d3
        noteMap["d4"] = noteValues.d4
        noteMap["d5"] = noteValues.d5
        noteMap["dseb1"] = noteValues.dseb1
        noteMap["dseb2"] = noteValues.dseb2
        noteMap["dseb3"] = noteValues.dseb3
        noteMap["dseb4"] = noteValues.dseb4
        noteMap["dseb5"] = noteValues.dseb5
        noteMap["e1"] = noteValues.e1
        noteMap["e2"] = noteValues.e2
        noteMap["e3"] = noteValues.e3
        noteMap["e4"] = noteValues.e4
        noteMap["e5"] = noteValues.e5
        noteMap["f1"] = noteValues.f1
        noteMap["f2"] = noteValues.f2
        noteMap["f3"] = noteValues.f3
        noteMap["f4"] = noteValues.f4
        noteMap["f5"] = noteValues.f5
        noteMap["fsgb1"] = noteValues.fsgb1
        noteMap["fsgb2"] = noteValues.fsgb2
        noteMap["fsgb3"] = noteValues.fsgb3
        noteMap["fsgb4"] = noteValues.fsgb4
        noteMap["fsgb5"] = noteValues.fsgb5
        noteMap["g1"] = noteValues.g1
        noteMap["g2"] = noteValues.g2
        noteMap["g3"] = noteValues.g3
        noteMap["g4"] = noteValues.g4
        noteMap["g5"] = noteValues.g5
        noteMap["gsab1"] = noteValues.gsab1
        noteMap["gsab2"] = noteValues.gsab2
        noteMap["gsab3"] = noteValues.gsab3
        noteMap["gsab4"] = noteValues.gsab4
        noteMap["gsab5"] = noteValues.gsab5
    }


}