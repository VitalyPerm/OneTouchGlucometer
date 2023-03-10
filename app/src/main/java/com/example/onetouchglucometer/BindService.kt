package com.example.onetouchglucometer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class BindService : Service() {
    private val binder = LocalBinder()


    val messanger = MutableSharedFlow<Int>()

    override fun onBind(p0: Intent?): IBinder = binder

    inner class LocalBinder : Binder() {
        val service get() = this@BindService
    }

    override fun onCreate() {
        super.onCreate()
        log("service onCreate")

        GlobalScope.launch {
            while (true) {
                delay(1000)
                messanger.emit(Random.nextInt())
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("service onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("service on destroy")
    }

    fun serviceCommand(){
        log("${Random.nextInt(1111)} + ${this.hashCode()}")
    }
}