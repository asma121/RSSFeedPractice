package com.example.rssfeedpractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var rvmain:RecyclerView
    var RecentQuestions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvmain=findViewById(R.id.rvmain)
        FetchRecentQuestions().execute()
    }

    private inner class FetchRecentQuestions : AsyncTask<Void, Void, MutableList<Question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            RecentQuestions =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as MutableList<Question>
            return RecentQuestions
        }

        override fun onPostExecute(result: MutableList<Question>?) {
            super.onPostExecute(result)
            val adapter =
                myAdapter(RecentQuestions)
            rvmain.adapter = adapter
            rvmain.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

}