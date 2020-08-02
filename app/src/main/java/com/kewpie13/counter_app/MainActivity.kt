package com.kewpie13.counter_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_VOLUME_DOWN
import android.view.KeyEvent.KEYCODE_VOLUME_UP
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdListener

class MainActivity : AppCompatActivity() {
    // 広告収入のためのビルダー準備
    lateinit var mAdView : AdView


    // カウンターのデフォルトの値をセット
    private var counter = 0

    // アプリ起動時、初めに呼ばれる処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Google の広告収入用のadMobを導入
        MobileAds.initialize(this){}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        // テキストビューのidを登録
        val counterDisplay = findViewById<TextView>(R.id.counter_display)

        // 各種ボタンのidを登録
        val buttonCountUp = findViewById<Button>(R.id.count_up)
        val buttonCountDown = findViewById<Button>(R.id.count_down)
        val buttonCountClear = findViewById<Button>(R.id.count_clear)

        // カウンターのデフォルト値を設定
        counterDisplay.text = counter.toString()

        // [+]ボタン選択時のアクションを登録
        buttonCountUp.setOnClickListener {
            counterDisplay.text = countUp(counter).toString()
        }

        // [-]ボタン選択時のアクションを登録
        buttonCountDown.setOnClickListener {
            counterDisplay.text = countDown(counter).toString()
        }

        // [C]ボタン選択時のアクションを登録
        buttonCountClear.setOnClickListener {
            counterDisplay.text = countClear().toString()
        }

        // 広告イベントの処理を記述
        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    // キーイベント取得
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event != null && event.action == KeyEvent.ACTION_DOWN) {
            // 処理をしない
            return true
        } else if (event != null && event.action == KeyEvent.ACTION_UP) {
            // テキストビューのidを登録
            val counterDisplay = findViewById<TextView>(R.id.counter_display)
            if (event.keyCode == KEYCODE_VOLUME_UP) {
                // 音量ボタン(UP)が押された場合の処理
                counterDisplay.text = countUp(counter).toString()
                // 音量を変更させないためにここでreturn
                return true
            } else if (event.keyCode == KEYCODE_VOLUME_DOWN) {
                // 音量ボタン(DOWN)が押された場合の処理
                counterDisplay.text = countDown(counter).toString()
                // 音量を変更させないためにここでreturn
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    // カウントアップ処理関数
    private fun countUp(counter: Int): Int {
        // 上限99999まで
        return if (counter >= 99999) {
            this.counter
        } else {
            this.counter = counter + 1
            this.counter
        }
    }

    // カウントダウン処理関数
    private fun countDown(counter: Int): Int {
        // 下限-99999まで
        return if (counter <= -99999) {
            this.counter
        } else {
            this.counter = counter - 1
            this.counter
        }
    }

    // カウントクリア処理関数
    private fun countClear(): Int {
        this.counter = 0
        return this.counter
    }
}
