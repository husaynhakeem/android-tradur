package io.husaynhakeem.tradur

import android.app.Application
import io.husaynhakeem.tradurlibrary.Tradur


class TradurSampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Tradur.init("AIzaSyCYf85QKk6x2ZbZroZRp_YemJaDT0gLpUQ")
    }
}