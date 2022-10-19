package com.example.android.wordsgame.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long>
        get() = _time

    val currentTimeString = Transformations.map(time,{ time ->
//        DateUtils.formatElapsedTime(time) doesn't work as shown
        ((time+1000)/1000).toString()
    })

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

//    private val _INC = MutableLiveData<Boolean>()
//    val INC: LiveData<Boolean>
//        get() = _INC

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _score.value = 0
        _time.value = COUNTDOWN_TIME
//        _INC.value = true
//        _word.value = wordList[0]
        _gameFinished.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _time.value = _time.value!! - ONE_SECOND
            }

            override fun onFinish() {
                _time.value = DONE
                _gameFinished.value = true
//                _INC.value = false
            }
        }
        timer.start()
//        DateUtils.formatElapsedTime()
    }
    /**
     * Resets the list of words and randomizes the order
     */
    fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }
    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
//            _gameFinished.value = true
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }
    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
//        if (_INC.value!!){
        _score.value = (_score.value)?.plus(1)
        nextWord()
//        }
    }

    fun gameFinished(){
        _gameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}