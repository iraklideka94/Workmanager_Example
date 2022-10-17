package com.example.workmanager_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.example.workmanager_example.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startWork.setOnClickListener {
            val constraints = Constraints.Builder().setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val data = workDataOf("WORK_MESSAGE" to "work completed!")
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>().setInputData(data).build()
            val periodicWork = PeriodicWorkRequestBuilder<SimpleWorker>(
                5,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            ).setConstraints(constraints)
                .build()
            workManager.enqueue(workRequest)
        }

        binding.workStatus.setOnClickListener {
            Toast.makeText(
                this,
                "the work status is ${WorkStatusSingleton.workMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.resetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        binding.uiThread.setOnClickListener {
            Thread.sleep(1000)
            WorkStatusSingleton.workComplete = true
        }
    }
}