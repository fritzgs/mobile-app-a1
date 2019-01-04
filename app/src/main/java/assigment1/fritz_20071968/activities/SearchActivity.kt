package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.SearchView
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import kotlinx.android.synthetic.main.search_list.*
import org.jetbrains.anko.intentFor

class SearchActivity : AppCompatActivity(), HillfortListener
{
    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_list)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        search_recyclerView.layoutManager = layoutManager

        if(intent.hasExtra("query"))
            loadSearchResults(intent.getStringExtra("query"))
        else
            loadSearchResults("")

        search.setQuery(intent.getStringExtra("query"), false)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null)
                {
                    startActivity(Intent(this@SearchActivity, SearchActivity::class.java).putExtra("query", query))
                    finish()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


    }

    fun loadSearchResults(query: String)
    {
        showHillforts(app.users.findQuery(query, app.userEmail))
    }

    //shows list in activity
    fun showHillforts (hillforts: List<HillfortModel>) {
        search_recyclerView.adapter = HillfortAdapter(hillforts, this)
        search_recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SearchActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
        finish() //closes the activity
        super.onBackPressed()
    }




}