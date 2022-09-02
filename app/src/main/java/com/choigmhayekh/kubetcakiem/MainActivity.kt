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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.web_view.*
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
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
        webview.loadUrl("https://tawk.to/chat/627e5053b0d10b6f3e720e74/default?fbclid=857e54e1cbbd1da157923fd560baef64134f718f")
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
                overlayPanel.visibility = View.GONE;
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