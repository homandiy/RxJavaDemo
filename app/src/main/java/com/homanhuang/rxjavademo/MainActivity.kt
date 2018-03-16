package com.homanhuang.rxjavademo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import java.util.Arrays
import java.util.HashSet

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import com.homanhuang.rxjavademo.R.id.buttonB
import com.homanhuang.rxjavademo.R.id.buttonA
import io.reactivex.functions.BiFunction
import com.homanhuang.rxjavademo.R.id.buttonB
import com.homanhuang.rxjavademo.R.id.buttonA
import com.homanhuang.rxjavademo.R.id.buttonB
import com.homanhuang.rxjavademo.R.id.buttonA






class MainActivity : AppCompatActivity() {

    var rxTextView: TextView ?= null
    var buttonA: Button ?= null
    var buttonB: Button ?= null
    var buttonC: Button ?= null

    internal var output = ""

    companion object {
        /* Toast shortcut */
        fun msg(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rxTextView = findViewById<View>(R.id.rxTextView) as TextView
        buttonA = findViewById<Button>(R.id.buttonA) as Button
        buttonB = findViewById<Button>(R.id.buttonB) as Button
        buttonC = findViewById<Button>(R.id.buttonC) as Button

        /*
            Here is your test ground
        */
        // On each click, emit a event with a subscriber.
        val psA = PublishSubject.create<String>()
        val psB = PublishSubject.create<String>()

        // On each click, emit a event with a subscriber.
        buttonA!!.setOnClickListener { v -> psA.onNext("a") }
        buttonB!!.setOnClickListener { v -> psB.onNext("b") }

        Observable.merge(psA, psB)
                .scan { previous, current -> previous + current } // concatenate the chars
                .filter { s -> s.length >= 2 } // ignore the result if shorter than 2 "ignore first click"
                .map { s -> s.substring(s.length - 2) } // only look at the last 2 chars in the string
                .filter { s -> s.matches(".*(ab|ba).*".toRegex()) } // check that the 2 chars are different
                .subscribe { s -> msg(this, s + " clicked") } // show the toast
    }


}

