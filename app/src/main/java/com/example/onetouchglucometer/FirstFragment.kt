package com.example.onetouchglucometer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController

fun log(msg: String) {
    Log.d("check___", msg)
}

class FirstFragment : Fragment() {

    private lateinit var btn: Button
    private lateinit var bindServiceBtn: Button
    private lateinit var unBindServiceBtn: Button
    private lateinit var comm: Button

    private lateinit var mService: BindService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BindService.LocalBinder
            mService = binder.service
            mBound = true
            log("onServiceConnected")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
            log("onServiceDisconnected")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_first, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn = view.findViewById(R.id.btn1)
        bindServiceBtn = view.findViewById(R.id.ff_start_service)
        unBindServiceBtn = view.findViewById(R.id.ff_stop_service)
        comm = view.findViewById(R.id.comm_1)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            Toast.makeText(requireContext(), "Go to second!", Toast.LENGTH_SHORT).show()
        }
        bindServiceBtn.setOnClickListener {
            Intent(requireActivity(), BindService::class.java).apply {
                requireActivity().bindService(this, connection, Context.BIND_AUTO_CREATE)
            }
            Toast.makeText(requireContext(), "bind service", Toast.LENGTH_SHORT).show()
        }

        unBindServiceBtn.setOnClickListener {
            requireActivity().unbindService(connection)
            Toast.makeText(requireContext(), "unBind service", Toast.LENGTH_SHORT).show()
        }
        comm.setOnClickListener {
            mService.serviceCommand()
        }
    }

}