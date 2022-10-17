package com.example.workmanager_example

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val message = inputData.getString("WORK_MESSAGE")
        Thread.sleep(1000)
        WorkStatusSingleton.workComplete = true

        if (message != null){
            WorkStatusSingleton.workMessage = message
        }
        return Result.success()
    }
}
