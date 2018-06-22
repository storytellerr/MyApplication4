package com.example.shashank.myapplication.Activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.shashank.myapplication.Contacts.ContactModel
import com.example.shashank.myapplication.ListView.DataAdapter
import com.example.shashank.myapplication.ListView.Model
import com.example.shashank.myapplication.ListView.ModelObject
import com.example.shashank.myapplication.ListView.RequestInterface
import com.example.shashank.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException


class MainActivity : AppCompatActivity(), DataAdapter.Listener {

    private val TAGS = MainActivity::class.java.simpleName

    private val BASE_URL = "http://www.androidbegin.com"

    private var mCompositeDisposable: CompositeDisposable? = null

    private var mArrayList: ArrayList<ModelObject>? = null
    private var contactModelArrayList: ArrayList<ContactModel>? = null
    private val TAG = "tag"

    private val CSV_HEADER = "name,number"

    private var mAdapter: DataAdapter? = null
    private lateinit var list_view: RecyclerView
    var path="error"
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_view = findViewById(R.id.rv_list)
        mCompositeDisposable = CompositeDisposable()
        val name = getSharedPreferences("mypref", MODE_PRIVATE).getString("loged_in","")
        initRecyclerView()
        loadJSON()
        RxView.clicks(fabb).subscribe(
               {

           Log.d("hello","start")
           path=getContacts()
           Log.d("hello",path)
           snackbar(path)


        },{

        },{
           Log.d("hello","oncomplete")
           if(path!=="error")
           {
               Log.d("hello","oncomplete")
               snackbar(path)
           }

       })
    }
    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logout->{
                auth.signOut()
                getSharedPreferences("mypref", 0).edit().clear().commit()
                val intent = Intent(this@MainActivity, StartUp::class.java)
                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    public fun snackbar(path:String){
        Log.d("hello","snackbar")
        Snackbar.make(activitymain,"your File is ready",Snackbar.LENGTH_LONG).setAction("Open",View.OnClickListener { openfile(path)  }).show()
    }

    public fun openfile(uri:String){
        val file = File(uri)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(file), "text/csv")
        startActivity(intent)
    }


    private fun initRecyclerView() {

        list_view.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        list_view.layoutManager = layoutManager
    }

    private fun loadJSON() {

        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)

        mCompositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))

    }

    private fun handleResponse(listData: Model) {
        Log.d("hello", listData.toString())
        listData.worldpopulation.forEach {
            Log.d("country", it.country)

        }
        mArrayList = ArrayList(listData.worldpopulation)
        mAdapter = DataAdapter(mArrayList!!, this)

        list_view.adapter = mAdapter
    }

    private fun handleError(error: Throwable) {

        Log.d(TAGS, error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
        Log.d("error", error.localizedMessage)
    }

    override fun onItemClick(item: ModelObject) {

        Toast.makeText(this, "${item.country} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }



    private fun getContacts():String {
        var task="error"
        var filename: String = Environment.getExternalStorageDirectory().toString() + "/" + "ContactsCSV.csv"
        if (checkAndRequestPermissions()) {
            // Permission is not granted
            // contactModelArrayList = ArrayList()

            var fileWriter: FileWriter? = null
            fileWriter = FileWriter(filename)
            fileWriter.append(CSV_HEADER)
            fileWriter.append('\n')

            try {
                val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
                while (phones!!.moveToNext()) {
                    val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    fileWriter.append(name)
                    fileWriter.append(',')
                    fileWriter.append(phoneNumber)
                    fileWriter.append('\n')
                    Log.d("name>>", name + "  " + phoneNumber)

                }
                phones.close()
                task="sucessfull"
            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()

            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }


        }
        if(task=="sucessfull")
            return filename
        else
            return "error"

    }

    private fun checkAndRequestPermissions(): Boolean {

        val writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)


        val listPermissionsNeeded = ArrayList<String>()

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS)
        }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
                return false
            }
            return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_CONTACTS] = PackageManager.PERMISSION_GRANTED
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.READ_CONTACTS] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, " services permission granted")

                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ")

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                            showDialogOK("Service Permissions are required for this app",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                            DialogInterface.BUTTON_NEGATIVE ->
                                                finish()
                                        }
                                    })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")

                        }
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }

    private fun explain(msg: String) {
        val dialog = android.support.v7.app.AlertDialog.Builder(this)
        dialog.setMessage(msg)
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    //  permissionsclass.requestPermission(type,code);
                    startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.shashank.myapplication")))
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    companion object {

        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    }


}
