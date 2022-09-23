package com.example.webdeneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.webdeneme.databinding.ActivityMainBinding
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), MessageListener {

    private val serverUrl = "ws://192.168.18.145:8086/socketServer/abc"
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super .onCreate (savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebSocketManager.init(serverUrl, this)
        binding.connectBtn.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }
        binding.clientSendBtn.setOnClickListener {
            if ( WebSocketManager .sendMessage( " Client send " )) {
                addText( " Send from the client \n " )
            }
        }
        binding.closeConnectionBtn.setOnClickListener {
            WebSocketManager.close()
        }
    }


    private fun addText(text: String?) {
        runOnUiThread {
            binding.contentEt.text.append(text)
        }
    }

    override fun onDestroy() {
        super .onDestroy ()
        WebSocketManager.close()
    }

    override fun onConnectSuccess() {
        addText( " Connected successfully \n " )
    }

    override fun onConnectFailed() {
        addText( " Connection failed \n " )
    }

    override fun onClose() {
        addText( " Closed successfully \n " )
    }

    override fun onMessage(text: String?) {
        addText( " Receive message: $text \n " )
    }

}