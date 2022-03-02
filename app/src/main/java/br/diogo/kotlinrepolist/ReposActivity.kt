package br.diogo.kotlinrepolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.repos_activity.*

class ReposActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repos_activity)
        val fragment = createFragment()
        addFragmentTo(R.id.content_frame, fragment)
        setSupportActionBar(toolbar)
    }



    fun createFragment(): ReposFragment {
        return ReposFragment.newInstance()
    }


}
