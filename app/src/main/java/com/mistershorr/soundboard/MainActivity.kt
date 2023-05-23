//create a system to cycle through the tutorial slides. then, work on the presentation.

package com.mistershorr.soundboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.BiPredicate


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"


    //instance variable for the view binding
    private lateinit var binding: ActivityMainBinding

    var noteValues = NoteValues()

    private lateinit var soundPool : SoundPool
    var song: ArrayList<Note> = ArrayList<Note>()

    var songBeingWritten: ArrayList<Note> = ArrayList<Note>()

    var octave: Int = 1

    private var selectedNoteType: Int = 4

    private var noteTypeButtons: ArrayList<Button> = ArrayList<Button>()

    private var dynamicButtons: ArrayList<Button> = ArrayList<Button>()

    var currentlyWriting: Boolean = false

    private var noteMap = HashMap<String, Int>()

    private var listOfSavedSongs: ArrayList<String> = ArrayList<String>()

    private var groupList: ArrayList<Group> = ArrayList<Group>()

    private var areSongsDisplayed: Boolean = false

    var isSongCurrentlyPlaying: Boolean = false

    private var currentTutorialSlide: Int = 1

    var writingVolume: Float = 0.6f

    private var notesUndone: ArrayList<Note> = ArrayList<Note>()

    private var redone: Boolean = false

    private val soundBoardListener = SoundBoardListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // define the binding  variable
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        loadNoteTypeButtonsArray()
        loadDynamicButtonsArray()

        loadGroupList()

        for(i in groupList.indices) {
            groupList[i].visibility = View.GONE
        }
        binding.groupMainNotes.visibility = View.VISIBLE

        initializeSoundPool()

        setListeners()


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



    private fun playNote(note: String, volume: Float) {
        // ?: is the elvis operator. it lets you provide a default value if the value is null
        playNote(noteMap[note] ?: 0, volume)
    }

    private fun playNote(noteId : Int, volume: Float) {
        soundPool.play(noteId, volume, volume, 1, 0, 1f)
    }

    private inner class SoundBoardListener : View.OnClickListener {
        @SuppressLint("SetTextI18n")
        override fun onClick(v: View?) {
            if(currentlyWriting) {

                if(notesUndone.isNotEmpty()) {
                    notesUndone = ArrayList<Note>()
                }

                when(octave) {
                    0 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a0, writingVolume)
                                songBeingWritten.add(Note("a0", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb0, writingVolume)
                                songBeingWritten.add(Note("asbb0", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b0, writingVolume)
                                songBeingWritten.add(Note("b0", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                    1 -> {
                        when(v?.id) {
                    R.id.button_main_a -> {
                        playNote(noteValues.a1, writingVolume)
                        songBeingWritten.add(Note("a1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_asbb -> {
                        playNote(noteValues.asbb1, writingVolume)
                        songBeingWritten.add(Note("asbb1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_b -> {
                        playNote(noteValues.b1, writingVolume)
                        songBeingWritten.add(Note("b1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_c -> {
                        playNote(noteValues.c1, writingVolume)
                        songBeingWritten.add(Note("c1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_csdb -> {
                        playNote(noteValues.csdb1, writingVolume)
                        songBeingWritten.add(Note("csdb1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_d -> {
                        playNote(noteValues.d1, writingVolume)
                        songBeingWritten.add(Note("d1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_dseb -> {
                        playNote(noteValues.dseb1, writingVolume)
                        songBeingWritten.add(Note("dseb1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_e -> {
                        playNote(noteValues.e1, writingVolume)
                        songBeingWritten.add(Note("e1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_f -> {
                        playNote(noteValues.f1, writingVolume)
                        songBeingWritten.add(Note("f1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_fsgb -> {
                        playNote(noteValues.fsgb1, writingVolume)
                        songBeingWritten.add(Note("fsgb1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_g -> {
                        playNote(noteValues.g1, writingVolume)
                        songBeingWritten.add(Note("g1", calculateNoteDuration(), writingVolume))
                    }
                    R.id.button_main_gsab -> {
                        playNote(noteValues.gsab1, writingVolume)
                        songBeingWritten.add(Note("gsab1", calculateNoteDuration(), writingVolume))
                    }
                }
                    }
                    2 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a2, writingVolume)
                                songBeingWritten.add(Note("a2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb2, writingVolume)
                                songBeingWritten.add(Note("asbb2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b2, writingVolume)
                                songBeingWritten.add(Note("b2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c2, writingVolume)
                                songBeingWritten.add(Note("c2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb2, writingVolume)
                                songBeingWritten.add(Note("csdb2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d2, writingVolume)
                                songBeingWritten.add(Note("d2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb2, writingVolume)
                                songBeingWritten.add(Note("dseb2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e2, writingVolume)
                                songBeingWritten.add(Note("e2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f2, writingVolume)
                                songBeingWritten.add(Note("f2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb2, writingVolume)
                                songBeingWritten.add(Note("fsgb2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g2, writingVolume)
                                songBeingWritten.add(Note("g2", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab2, writingVolume)
                                songBeingWritten.add(Note("gsab2", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                    3 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a3, writingVolume)
                                songBeingWritten.add(Note("a3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb3, writingVolume)
                                songBeingWritten.add(Note("asbb3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b3, writingVolume)
                                songBeingWritten.add(Note("b3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c3, writingVolume)
                                songBeingWritten.add(Note("c3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb3, writingVolume)
                                songBeingWritten.add(Note("csdb3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d3, writingVolume)
                                songBeingWritten.add(Note("d3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb3, writingVolume)
                                songBeingWritten.add(Note("dseb3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e3, writingVolume)
                                songBeingWritten.add(Note("e3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f3, writingVolume)
                                songBeingWritten.add(Note("f3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb3, writingVolume)
                                songBeingWritten.add(Note("fsgb3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g3, writingVolume)
                                songBeingWritten.add(Note("g3", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab3, writingVolume)
                                songBeingWritten.add(Note("gsab3", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                    4 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a4, writingVolume)
                                songBeingWritten.add(Note("a4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb4, writingVolume)
                                songBeingWritten.add(Note("asbb4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b4, writingVolume)
                                songBeingWritten.add(Note("b4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c4, writingVolume)
                                songBeingWritten.add(Note("c4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb4, writingVolume)
                                songBeingWritten.add(Note("csdb4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d4, writingVolume)
                                songBeingWritten.add(Note("d4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb4, writingVolume)
                                songBeingWritten.add(Note("dseb4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e4, writingVolume)
                                songBeingWritten.add(Note("e4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f4, writingVolume)
                                songBeingWritten.add(Note("f4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb4, writingVolume)
                                songBeingWritten.add(Note("fsgb4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g4, writingVolume)
                                songBeingWritten.add(Note("g4", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab4, writingVolume)
                                songBeingWritten.add(Note("gsab4", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                    5 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a5, writingVolume)
                                songBeingWritten.add(Note("a5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb5, writingVolume)
                                songBeingWritten.add(Note("asbb5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b5, writingVolume)
                                songBeingWritten.add(Note("b5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c5, writingVolume)
                                songBeingWritten.add(Note("c5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb5, writingVolume)
                                songBeingWritten.add(Note("csdb5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d5, writingVolume)
                                songBeingWritten.add(Note("d5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb5, writingVolume)
                                songBeingWritten.add(Note("dseb5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e5, writingVolume)
                                songBeingWritten.add(Note("e5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f5, writingVolume)
                                songBeingWritten.add(Note("f5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb5, writingVolume)
                                songBeingWritten.add(Note("fsgb5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g5, writingVolume)
                                songBeingWritten.add(Note("g5", calculateNoteDuration(), writingVolume))
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab5, writingVolume)
                                songBeingWritten.add(Note("gsab5", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                    6 -> {
                        when(v?.id) {
                            R.id.button_main_c -> {
                                playNote(noteValues.c6, writingVolume)
                                songBeingWritten.add(Note("c6", calculateNoteDuration(), writingVolume))
                            }
                        }
                    }
                }
                when(v?.id) {
                    R.id.button_main_playSong -> {
                        Toast.makeText(this@MainActivity, "Must not be currently writing a song in order to play song", Toast.LENGTH_LONG).show()
                    }
                }

            }
            else {

                when(octave) {
                    0 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a0, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb0, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b0, writingVolume)
                            }
                        }
                    }
                    1 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a1, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb1, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b1, writingVolume)
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c1, writingVolume)
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb1, writingVolume)
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d1, writingVolume)
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb1, writingVolume)
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e1, writingVolume)
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f1, writingVolume)
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb1, writingVolume)
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g1, writingVolume)
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab1, writingVolume)
                            }
                        }
                    }
                    2 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a2, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb2, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b2, writingVolume)
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c2, writingVolume)
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb2, writingVolume)
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d2, writingVolume)
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb2, writingVolume)
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e2, writingVolume)
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f2, writingVolume)
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb2, writingVolume)
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g2, writingVolume)
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab2, writingVolume)
                            }
                        }
                    }
                    3 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a3, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb3, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b3, writingVolume)
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c3, writingVolume)
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb3, writingVolume)
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d3, writingVolume)
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb3, writingVolume)
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e3, writingVolume)
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f3, writingVolume)
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb3, writingVolume)
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g3, writingVolume)
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab3, writingVolume)
                            }
                        }
                    }
                    4 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a4, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb4, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b4, writingVolume)
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c4, writingVolume)
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb4, writingVolume)
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d4, writingVolume)
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb4, writingVolume)
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e4, writingVolume)
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f4, writingVolume)
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb4, writingVolume)
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g4, writingVolume)
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab4, writingVolume)
                            }
                        }
                    }
                    5 -> {
                        when(v?.id) {
                            R.id.button_main_a -> {
                                playNote(noteValues.a5, writingVolume)
                            }
                            R.id.button_main_asbb -> {
                                playNote(noteValues.asbb5, writingVolume)
                            }
                            R.id.button_main_b -> {
                                playNote(noteValues.b5, writingVolume)
                            }
                            R.id.button_main_c -> {
                                playNote(noteValues.c5, writingVolume)
                            }
                            R.id.button_main_csdb -> {
                                playNote(noteValues.csdb5, writingVolume)
                            }
                            R.id.button_main_d -> {
                                playNote(noteValues.d5, writingVolume)
                            }
                            R.id.button_main_dseb -> {
                                playNote(noteValues.dseb5, writingVolume)
                            }
                            R.id.button_main_e -> {
                                playNote(noteValues.e5, writingVolume)
                            }
                            R.id.button_main_f -> {
                                playNote(noteValues.f5, writingVolume)
                            }
                            R.id.button_main_fsgb -> {
                                playNote(noteValues.fsgb5, writingVolume)
                            }
                            R.id.button_main_g -> {
                                playNote(noteValues.g5, writingVolume)
                            }
                            R.id.button_main_gsab -> {
                                playNote(noteValues.gsab5, writingVolume)
                            }
                        }
                    }
                    6 -> {
                        when(v?.id) {
                            R.id.button_main_c -> {
                                playNote(noteValues.c6, writingVolume)
                            }
                        }
                    }
                }

                when(v?.id) {
                    R.id.button_main_playSong -> {
                    //launch a coroutine

                        if(!isSongCurrentlyPlaying) {

                            isSongCurrentlyPlaying = true

                            binding.buttonMainPlaySong.text = "Stop Song"

                            if (binding.editTextMainSongName.text.isNotBlank()) {
                                loadSong()
                                GlobalScope.launch {
                                    playNote("rest", writingVolume)
                                    delay(500)
                                    playSong(song)
                                }
                            } else {
                                GlobalScope.launch {
                                    playNote("rest", writingVolume)
                                    delay(500)
                                    playSong(songBeingWritten)
                                }
                            }
                        }

                        else {
                            isSongCurrentlyPlaying = false
                        }
                }
                }
            }
        }

    }

    // suspend keyword marks the function to be used in a coroutine
    // (so that the default delay function can work)
    @SuppressLint("SetTextI18n")
    private suspend fun playSong(song: List<Note>) {



        //0..10 - 0 to 10 inclusive both sides
        //0 until 10 - 0 to 9
        //i in song.indices - goes through the entire list
        //for(item in list) is the enhanced for loop
        for(i in song.indices) {
            playNote(song[i].note, song[i].volume)
            delay(song[i].duration)
            if(!isSongCurrentlyPlaying) {
                break
            }
        }

        isSongCurrentlyPlaying = false
        //code from Mr Shorr
        GlobalScope.launch(Dispatchers.Main) {
            binding.buttonMainPlaySong.text = "Play Song"
        }


        //loop through a list of notes
          //play the note
                //note you get is a string
                //to play the note, you need the corresponding soundPool object
           //delay for the delay
    }





    private fun loadSong() {

        try {
            //i forgot where i took this code from
            val fis: FileInputStream = openFileInput(binding.editTextMainSongName.text.toString() + ".json")
            val jsonString = fis.bufferedReader().use {
                it.readText()
            }
            Log.d(TAG, "onCreate: $jsonString")




            val gson = Gson()


            val type = object : TypeToken<List<Note>>() { }.type
            song = gson.fromJson<List<Note>>(jsonString, type) as ArrayList<Note>
        } catch (e: Exception) {
            Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
        }


    }

    private fun loadSongToBeEdited() {
        try {
            //i forgot where i took this code from
            val fis: FileInputStream = openFileInput(binding.editTextMainSongName.text.toString() + ".json")
            val jsonString = fis.bufferedReader().use {
                it.readText()
            }
            Log.d(TAG, "onCreate: $jsonString")




            val gson = Gson()


            val type = object : TypeToken<List<Note>>() { }.type
            songBeingWritten = gson.fromJson<List<Note>>(jsonString, type) as ArrayList<Note>
        } catch (e: Exception) {
            Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateNoteDuration(): Long {
        return if(selectedNoteType != 0) {
            if (binding.editTextMainBpm.text.isNotBlank() && binding.editTextMainBpm.text.toString() != "0") {
                ((1000.0 / (binding.editTextMainBpm.text.toString().toDouble() / 60.0)) / (selectedNoteType.toDouble() / 4.0)).toLong()
            } else {
                ((1000.0 / (1.0 / 60.0)) / (selectedNoteType.toDouble() / 4.0)).toLong()
            }
        } else {
            0
        }
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

    private fun setListeners() {

        setNoteButtonListeners()
        setDurationButtonListeners()
        setDynamicButtonListeners()
        setSongControlButtonListeners()
        setRightHandMenuButtonListeners()

    }

    private fun setNoteButtonListeners() {
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

        binding.buttonMainOctaveUp.setOnClickListener {
            if(octave < 6) {
                octave++
                updateNoteButtons()
            }
        }
        binding.buttonMainOctaveDown.setOnClickListener {
            if(octave > 0) {
                octave--
                updateNoteButtons()
            }
        }
        binding.buttonMainRest.setOnClickListener {
            if(currentlyWriting) {
                songBeingWritten.add(Note("rest", calculateNoteDuration(), writingVolume))
            }
        }
    }

    private fun setDurationButtonListeners() {
        binding.buttonMainWholeNote.setOnClickListener {
            selectedNoteType = 1
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainHalfNote.setOnClickListener {
            selectedNoteType = 2
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainQuarterNote.setOnClickListener {
            selectedNoteType = 4
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainEighthNote.setOnClickListener {
            selectedNoteType = 8
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainSixteenthNote.setOnClickListener {
            selectedNoteType = 16
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainThirtySecondNote.setOnClickListener {
            selectedNoteType = 32
            setNoteTypeButtonColors(it as Button)
        }
        binding.buttonMainInstant.setOnClickListener {
            selectedNoteType = 0
            setNoteTypeButtonColors(it as Button)
        }
    }

    private fun setDynamicButtonListeners() {
        binding.buttonMainFfff.setOnClickListener {
            writingVolume = 1f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainFff.setOnClickListener {
            writingVolume = 0.9f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainFf.setOnClickListener {
            writingVolume = 0.8f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainForte.setOnClickListener {
            writingVolume = 0.7f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainMf.setOnClickListener {
            writingVolume = 0.6f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainMp.setOnClickListener {
            writingVolume = 0.5f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainP.setOnClickListener {
            writingVolume = 0.4f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainPp.setOnClickListener {
            writingVolume = 0.3f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainPpp.setOnClickListener {
            writingVolume = 0.2f
            setDynamicButtonColors(it as Button)
        }
        binding.buttonMainPppp.setOnClickListener {
            writingVolume = 0.1f
            setDynamicButtonColors(it as Button)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSongControlButtonListeners() {
        binding.buttonMainPlaySong.setOnClickListener(soundBoardListener)

        binding.buttonMainStartStopWriting.setOnClickListener {
            currentlyWriting = !currentlyWriting
            if(currentlyWriting) {
                binding.buttonMainStartStopWriting.text = "Stop Writing"
                if(binding.editTextMainSongName.text.isNotBlank()) {
                    loadSongToBeEdited()
                }
            }
            else {
                binding.buttonMainStartStopWriting.text = "Start Writing"
            }
        }

        binding.buttonMainDelete.setOnClickListener {
            deleteSong()
        }

        binding.buttonMainSave.setOnClickListener {
            saveSong()
        }


    }

    private fun setRightHandMenuButtonListeners() {
        binding.buttonMainDurationSelection.setOnClickListener {
            for(i in groupList.indices) {
                groupList[i].visibility = View.GONE
            }
            binding.groupMainDurations.visibility = View.VISIBLE
            areSongsDisplayed = false
        }
        binding.buttonMainNoteSelection.setOnClickListener {
            for(i in groupList.indices) {
                groupList[i].visibility = View.GONE
            }
            binding.groupMainNotes.visibility = View.VISIBLE
            areSongsDisplayed = false
        }

        binding.buttonMainDynamicSelection.setOnClickListener {
            for(i in groupList.indices) {
                groupList[i].visibility = View.GONE
            }
            binding.groupMainDynamics.visibility = View.VISIBLE
            areSongsDisplayed = false
        }

        binding.buttonMainDisplayAllSongs.setOnClickListener {
            displayAllSongs()
        }

        binding.buttonMainUndo.setOnClickListener {
            if(redone) {
                notesUndone = ArrayList<Note>()
                redone = false
            }
            if(songBeingWritten.isNotEmpty()) {
                notesUndone.add(songBeingWritten.removeLast())
            }
        }

        binding.buttonMainRedo.setOnClickListener {
            if(notesUndone.isNotEmpty()) {
                songBeingWritten.add(notesUndone.removeLast())
            }
            redone = true
        }

        binding.buttonMainTutorialNext.setOnClickListener {
            if(currentTutorialSlide < 14) {
                currentTutorialSlide++
                updateTutorial()
            }
        }

        binding.buttonMainTutorialPrevious.setOnClickListener {
            if(currentTutorialSlide > 1) {
                currentTutorialSlide--
                updateTutorial()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTutorial() {
        when (currentTutorialSlide) {
            1 -> {
                binding.textViewMainTutorials.text = "If the song name is blank, the play, delete, and write controls affect the current unnamed in-progress song."
            }
            2 -> {
                binding.textViewMainTutorials.text = "To save the current unnamed song, type in a name and then press save."
            }
            3 -> {
                binding.textViewMainTutorials.text = "If the song name is not blank, the play, delete, and write controls affect the named song."
            }
            4 -> {
                binding.textViewMainTutorials.text = "Pressing \"Start Writing\" when the song name box is not blank will overwrite any unnamed, unsaved song you've been working on."
            }
            5 -> {
                binding.textViewMainTutorials.text = "Additionally, pressing \"Start Writing\" when the song name box is not blank will remove any edits you have made to the named song you are working on."
            }
            6 -> {
                binding.textViewMainTutorials.text = "Saving a song with the same name as a song that has already been saved will overwrite the latter."
            }
            7 -> {
                binding.textViewMainTutorials.text = "Available redos are deleted when you write a new note."
            }
            8 -> {
                binding.textViewMainTutorials.text = "If you undo a note addition after redoing one, all other available redos will be deleted."
            }
            9 -> {
                binding.textViewMainTutorials.text = "Undoes and Redos only affect notes. You cannot undo and redo other actions."
            }
            10 -> {
                binding.textViewMainTutorials.text = "If you want to write triplets, set the bpm to 3/2 of its original value. Then write using the duration that corresponds to the triplet duration you want."
            }
            11 -> {
                binding.textViewMainTutorials.text = "Example: for 8th note triplets at 120 bpm, set the bpm to 180 and write using eight notes."
            }
            12 -> {
                binding.textViewMainTutorials.text = "If you want to write chords, use the \"Instant\" duration. The last note of the chord should be the duration you want the chord to last for."
            }
            13 -> {
                binding.textViewMainTutorials.text = "Example: For a quarter note C Major chord, the C and E should be instant, and the G should be a quarter note."
            }
            14 -> {
                binding.textViewMainTutorials.text = "End of tutorial slides."
            }

        }
    }

    private fun setNoteTypeButtonColors(button: Button) {
        for(i in noteTypeButtons.indices) {
            noteTypeButtons[i].setBackgroundColor(Color.rgb(110, 0, 248))
        }
        button.setBackgroundColor(Color.rgb(0, 200, 0))
    }

    private fun loadNoteTypeButtonsArray() {
        noteTypeButtons.add(binding.buttonMainWholeNote)
        noteTypeButtons.add(binding.buttonMainHalfNote)
        noteTypeButtons.add(binding.buttonMainQuarterNote)
        noteTypeButtons.add(binding.buttonMainEighthNote)
        noteTypeButtons.add(binding.buttonMainSixteenthNote)
        noteTypeButtons.add(binding.buttonMainThirtySecondNote)
        noteTypeButtons.add(binding.buttonMainInstant)
    }

    private fun setDynamicButtonColors(button: Button) {
        for(i in dynamicButtons.indices) {
            dynamicButtons[i].setBackgroundColor(Color.rgb(110, 0, 248))
        }
        button.setBackgroundColor(Color.rgb(0,200,0))
    }

    private fun loadDynamicButtonsArray() {
        dynamicButtons.add(binding.buttonMainFfff)
        dynamicButtons.add(binding.buttonMainFff)
        dynamicButtons.add(binding.buttonMainFf)
        dynamicButtons.add(binding.buttonMainForte)
        dynamicButtons.add(binding.buttonMainMf)
        dynamicButtons.add(binding.buttonMainMp)
        dynamicButtons.add(binding.buttonMainP)
        dynamicButtons.add(binding.buttonMainPp)
        dynamicButtons.add(binding.buttonMainPpp)
        dynamicButtons.add(binding.buttonMainPppp)
    }

    @SuppressLint("SetTextI18n")
    private fun updateNoteButtons() {
        when (octave) {
            0 -> {
                binding.buttonMainA.text = "A$octave"
                binding.buttonMainAsbb.text = "A♯/B♭$octave"
                binding.buttonMainB.text = "B$octave"
                binding.buttonMainC.text = "N/A"
                binding.buttonMainCsdb.text = "N/A"
                binding.buttonMainD.text = "N/A"
                binding.buttonMainDseb.text = "N/A"
                binding.buttonMainE.text = "N/A"
                binding.buttonMainF.text = "N/A"
                binding.buttonMainFsgb.text = "N/A"
                binding.buttonMainG.text = "N/A"
                binding.buttonMainGsab.text = "N/A"
            }
            6 -> {
                binding.buttonMainA.text = "N/A"
                binding.buttonMainAsbb.text = "N/A"
                binding.buttonMainB.text = "N/A"
                binding.buttonMainC.text = "C$octave"
                binding.buttonMainCsdb.text = "N/A"
                binding.buttonMainD.text = "N/A"
                binding.buttonMainDseb.text = "N/A"
                binding.buttonMainE.text = "N/A"
                binding.buttonMainF.text = "N/A"
                binding.buttonMainFsgb.text = "N/A"
                binding.buttonMainG.text = "N/A"
                binding.buttonMainGsab.text = "N/A"
            }
            else -> {
                binding.buttonMainA.text = "A$octave"
                binding.buttonMainAsbb.text = "A♯/B♭$octave"
                binding.buttonMainB.text = "B$octave"
                binding.buttonMainC.text = "C$octave"
                binding.buttonMainCsdb.text = "C♯/D♭$octave"
                binding.buttonMainD.text = "D$octave"
                binding.buttonMainDseb.text = "D♯/E♭$octave"
                binding.buttonMainE.text = "E$octave"
                binding.buttonMainF.text = "F$octave"
                binding.buttonMainFsgb.text = "F♯/G♭$octave"
                binding.buttonMainG.text = "G$octave"
                binding.buttonMainGsab.text = "G♯/A♭$octave"
            }
        }
    }

    private fun saveSong() {

        val gson = Gson()

        Log.d(TAG, songBeingWritten.toString())
        val jsonArrayOfSong = gson.toJson(songBeingWritten)

        Log.d(TAG, jsonArrayOfSong.toString())
        val songJsonString: String = jsonArrayOfSong.toString()
        Log.d(TAG, songJsonString)


        //i forgot where i took this code from
        try {
            var output: Writer? = null
            val file =
                File(this.filesDir, binding.editTextMainSongName.text.toString() + ".json")
            output = BufferedWriter(FileWriter(file))
            output.write(songJsonString)
            output.close()
            Toast.makeText(applicationContext, "Composition saved", Toast.LENGTH_LONG).show()
            if(areSongsDisplayed) {
                displayAllSongs()
            }
        } catch (e: Exception) {
            Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
        }

    }


    private fun displayAllSongs() {
        listOfSavedSongs = ArrayList<String>()

        //this code is taken from https://www.techiedelight.com/traverse-directory-to-list-files-kotlin/
        val dir = this.filesDir.toString()
        Files.find(
            Paths.get(dir), Int.MAX_VALUE,
            BiPredicate { _, file: BasicFileAttributes -> file.isRegularFile }
        ).use { paths -> paths.forEach {
            Log.d(TAG, it.toString())
            listOfSavedSongs.add(it.toString())
        } }

        for(i in listOfSavedSongs.indices) {
            listOfSavedSongs[i] =
                listOfSavedSongs[i].replace(this.filesDir.toString() + "/", "").replace(".json", "")
            Log.d(TAG, listOfSavedSongs[i])
        }

        for(i in groupList.indices) {
            groupList[i].visibility = View.GONE
        }
        binding.groupMainSongDisplay.visibility = View.VISIBLE

        binding.textViewMainDisplayedSongs.text = listOfSavedSongs.toString()

        areSongsDisplayed = true

    }

    private fun loadGroupList() {
        groupList.add(binding.groupMainNotes)
        groupList.add(binding.groupMainDurations)
        groupList.add(binding.groupMainSongDisplay)
        groupList.add(binding.groupMainDynamics)
    }

    private fun deleteSong() {

        if(binding.editTextMainSongName.text.isBlank()) {
            songBeingWritten = ArrayList<Note>()
            notesUndone = ArrayList<Note>()
        }
        else {

            try {

                //i took this code from https://stackoverflow.com/questions/3554722/how-to-delete-internal-storage-file-in-android
                val dir = filesDir
                val file = File(dir, binding.editTextMainSongName.text.toString() + ".json")
                file.delete()

                if(areSongsDisplayed) {
                    displayAllSongs()
                }


            } catch (e: Exception) {
                Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}