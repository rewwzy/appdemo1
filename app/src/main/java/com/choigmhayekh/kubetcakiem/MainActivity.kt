package com.choigmhayekh.kubetcakiem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.webkit.WebViewClient
import android.widget.EditText
import com.choigmhayekh.kubetcakiem.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.web_view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var link :String =""
    var tawkTo :String= ""
    val STORETEXT = "firstcheckd.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide() // hide the title bar
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        readFileInEditor()
        web_view.visibility = View.INVISIBLE;

        fab.visibility = View.VISIBLE;
        webview.webViewClient = WebViewClient()
        doGet()
        webview.loadUrl(tawkTo)
        webview.settings.javaScriptEnabled = true
        webview.settings.setSupportZoom(true)
        binding.fab.setOnClickListener {
            OnChatClick()
        }
        close_chat.setOnClickListener {
            web_view.visibility = View.INVISIBLE;
            fab.visibility = View.VISIBLE;
        }
        button_confirm.setOnClickListener{
            if(answer.text.toString()=="1"){
                saveClicked(answer.text.toString())
                overlayPanel.visibility = View.INVISIBLE;
                fab.visibility = View.VISIBLE;
            }
        }

    }
    fun OnChatClick(){
        web_view.visibility = View.VISIBLE;
        fab.visibility = View.INVISIBLE;

    }
    fun saveClicked(txtEditor:String) {
        try {
            val out = OutputStreamWriter(openFileOutput(STORETEXT, 0))
            out.write(txtEditor.toString())
            out.close()
        } catch (t: Throwable) {
//            Toast
//                .makeText(this, "Exception: $t", Toast.LENGTH_LONG)
//                .show()
        }
    }
    data class resdata(
        val packagename:String,
        val appname:String?=null,
        val date:String?=null,
        val status:String?=null,
        var link: String,
        var tawkto: String? = null,
    ) {
    }
    fun doGet(){
        overlayPanel.visibility = View.VISIBLE;
        ApiInterface.create().getActive("00b7691d86d96aebd21dd9e138f90840").enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    val typeToken = object : TypeToken<List<resdata>>() {}.type
                    val resp = Gson().fromJson<List<resdata>>(response.body()?.string(), typeToken);
                    resp.forEach(){
                        if(packageName == it.packagename){
                            link = it.link.toString()
                            tawkTo = it.tawkto.toString()
                            overlayPanel.visibility = View.GONE
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
            }
        });
    }
    fun readFileInEditor() {
        try {

            val `in`: InputStream? = openFileInput(STORETEXT)
            if (`in` != null) {
                val tmp = InputStreamReader(`in`)
                val reader = BufferedReader(tmp)
                var str: String = reader.readLine()
                `in`.close()
                if(str.toString()=="1"){
                    overlayPanel.visibility = View.GONE;
                    fab.visibility = View.VISIBLE;
                }
                else{
                    overlayPanel.visibility = View.VISIBLE;
                    fab.visibility = View.INVISIBLE;
                }

            }
        } catch (e: FileNotFoundException) {

// that's OK, we probably haven't created it yet
        } catch (t: Throwable) {
//            Toast
//                .makeText(this, "Exception: $t", Toast.LENGTH_LONG)
//                .show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}