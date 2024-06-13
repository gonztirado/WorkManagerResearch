package com.gonztirado.research.workmanager

import PeriodicWorker
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.gonztirado.research.workmanager.ui.theme.WorkManagerResearchTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkManagerResearchTheme {
                MyApp {
                    RescheduleWorkButton()
                }
            }
        }
    }

    @Composable
    fun RescheduleWorkButton() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { reschedulePeriodicWorker() }) {
                Text("Reschedule Worker")
            }
        }
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        content()
    }

    private fun reschedulePeriodicWorker() {
        val interval = 15L
        val intervalTimeUnit = TimeUnit.MINUTES
        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(interval, intervalTimeUnit)
            .setInitialDelay(interval, intervalTimeUnit)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            PeriodicWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest,
        )

        Log.d(TAG, "Setup PeriodicWorker '${PeriodicWorker.UNIQUE_WORK_NAME}'. Interval: $interval $intervalTimeUnit")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}

