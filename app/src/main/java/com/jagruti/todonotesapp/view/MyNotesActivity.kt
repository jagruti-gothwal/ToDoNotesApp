package com.jagruti.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jagruti.todonotesapp.NotesApp
import com.jagruti.todonotesapp.utils.AppConstant
import com.jagruti.todonotesapp.utils.PrefConstant
import com.jagruti.todonotesapp.R
import com.jagruti.todonotesapp.adapter.NotesAdapter
import com.jagruti.todonotesapp.clicklistner.ItemClickListener
import com.jagruti.todonotesapp.db.Notes
import com.jagruti.todonotesapp.workmanager.MyWorker
import kotlinx.android.synthetic.main.activity_my_notes.*
import java.util.*
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {
    var fullName: String? = null
    var fabAddNotes: FloatingActionButton? = null
    var sharedPreferences: SharedPreferences? = null
    var recyclerView: RecyclerView? = null
    val ADD_NOTES_CODE=100
    var notesList = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setupSharedPreference()
        getIntentData()
        setupRecyclerView()
        getDataFromDatabase()
        clickListeners()
        setupToolbarText()
        setupWorkManager()
        
    }

    private fun setupWorkManager() {
        val constraint = androidx.work.Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java,1,TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)

    }

    private fun setupToolbarText() {
        if(supportActionBar != null){
            supportActionBar?.title=fullName
        }
    }

    private fun clickListeners() {
           // setupDialogBox()
            fabAddNotes?.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    val intent = Intent(this@MyNotesActivity,AddNotesActivity::class.java)
                    startActivityForResult(intent,ADD_NOTES_CODE)
                }

            })
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        }
        if (TextUtils.isEmpty(fullName)) {
            fullName = sharedPreferences!!.getString(PrefConstant.FULL_NAME, "")
        }
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }



    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerView = findViewById(R.id.recyclerViewNotes)
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.activity_add_notes, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                val notes = Notes(title = title,description = description)
                notesList.add(notes)
                recyclerView?.adapter?.notifyItemChanged(notesList.size - 1)
                addNotesToDb(notes)

            dialog.hide()
        }
        dialog.show()
    }

    private fun addNotesToDb(notes: Notes) {
        //Insert Notes
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                    //update
                    val notesApp = applicationContext as NotesApp
                    val notesDao = notesApp.getNotesDb().notesDao()
                    notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.adapter = notesAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==ADD_NOTES_CODE){
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)
            val notes = Notes(title = title!!,description = description!!, imgPath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
            notesList.add(notes)
            // For adding nth no. of notes
            recyclerView?.adapter?.notifyItemChanged(notesList.size - 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blog){
            val intent =  Intent(this@MyNotesActivity,BlogActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }
}