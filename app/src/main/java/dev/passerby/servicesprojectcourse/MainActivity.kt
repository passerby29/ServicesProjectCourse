package dev.passerby.servicesprojectcourse

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.passerby.servicesprojectcourse.MyJobService.Companion.JOB_ID
import dev.passerby.servicesprojectcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            simpleService.setOnClickListener {
                startService(MyService.newIntent(this@MainActivity, 25))
            }
            foregroundService.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyForegroundService.newIntent(this@MainActivity)
                )
            }
            intentService.setOnClickListener {
                ContextCompat.startForegroundService(
                    this@MainActivity,
                    MyIntentService.newIntent(this@MainActivity)
                )
            }
            jobScheduler.setOnClickListener {
                val componentName = ComponentName(this@MainActivity, MyJobService::class.java)

                val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .build()

                val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                jobScheduler.schedule(jobInfo)
            }
        }
    }
}