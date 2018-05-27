package io.husaynhakeem.tradur

import android.app.Application
import io.husaynhakeem.tradurlibrary.Tradur


class TradurSampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Tradur.init("<API_KEY>")
    }
}