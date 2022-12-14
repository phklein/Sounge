package com.sounge.soungeapp.actitivy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.activity_notificacao.*

class NotificacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacao)

        recycler_view_notification.adapter = NotificationAdapter(fakeNotificacao())
        recycler_view_notification.layoutManager = LinearLayoutManager(this)
    }
}