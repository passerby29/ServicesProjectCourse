package dev.passerby.servicesprojectcourse

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.passerby.servicesprojectcourse.MyJobService.Companion.JOB_ID
import dev.passerby.servicesprojectcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var page = 0

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
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .build()

                val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val intent = MyJobService.newIntent(page++)
                    jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
                } else {
                    startService(MyIntentService2.newIntent(this@MainActivity, page++))
                }
            }
            jobIntentService.setOnClickListener {
                MyJobIntentService.enqueue(this@MainActivity, page++)
            }
        }
    }
}