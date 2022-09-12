package com.lauruscorp.kmm_example.android

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lauruscorp.kmm_example.data.database.DatabaseDriverFactory
import com.lauruscorp.kmm_example.data.repository.RocketLaunchesRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope = MainScope()
    private val launchesRecyclerView: RecyclerView by lazy { findViewById(R.id.launchesRecyclerView) }
    private val progressBarView: FrameLayout by lazy { findViewById(R.id.progressBar) }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeContainer) }
    private val rocketLaunchesAdapter = RocketLaunchesAdapter(emptyList())
    private val rocketLaunchesRepository = RocketLaunchesRepository(DatabaseDriverFactory(this))
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)
        launchesRecyclerView.adapter = rocketLaunchesAdapter
        launchesRecyclerView.layoutManager = LinearLayoutManager(this)
        
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayRocketLaunches(needReload = true)
        }
        
        displayRocketLaunches(needReload = false)
    }
    
    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
    
    private fun displayRocketLaunches(needReload: Boolean) {
        progressBarView.isVisible = true
        scope.launch {
            kotlin.runCatching {
                rocketLaunchesRepository.getLaunches(needReload)
            }
                .onSuccess {
                    rocketLaunchesAdapter.launches = it
                    rocketLaunchesAdapter.notifyDataSetChanged()
                }
                .onFailure {
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            
            progressBarView.isVisible = false
        }
    }
}
