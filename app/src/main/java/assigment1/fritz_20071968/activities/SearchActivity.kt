package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.SearchView
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import kotlinx.android.synthetic.main.search_list.*
import org.jetbrains.anko.intentFor

class SearchActivity : AppCompatActivity(), HillfortListener
{
    //open hillfort edit
    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_list)

        app = application as MainApp

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        search_recyclerView.layoutManager = layoutManager


        if(intent.hasExtra("query"))
            loadSearchResults(intent.getStringExtra("query"))
        else
            loadSearchResults("")


        //put query string in search bar when loading activity
        search.setQuery(intent.getStringExtra("query"), false)

        //search query listener
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            //on query submit, reload activity with extra query
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

    //load hillforts that contain the search query
    fun loadSearchResults(query: String)
    {
        showHillforts(app.users.findQuery(query, app.userEmail))
    }

    //shows list in activity
    fun showHillforts (hillforts: List<HillfortModel>) {
        search_recyclerView.adapter = HillfortAdapter(hillforts, this)
        search_recyclerView.adapter?.notifyDataSetChanged()
    }

    //open list as normal
    override fun onBackPressed() {
        startActivity(Intent(this@SearchActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
        finish() //closes the activity
        super.onBackPressed()
    }




}