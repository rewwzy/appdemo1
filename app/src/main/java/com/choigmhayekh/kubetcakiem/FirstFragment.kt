package com.choigmhayekh.kubetcakiem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.choigmhayekh.kubetcakiem.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.*
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readFileInEditor()

        binding.buttonFirst.setOnClickListener {
            val uri = Uri.parse("https://google.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
//            findNavController().navigate(R.id.action_FirstFragment_to_LoginFragment)
        }
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_RegisterFragment)
//        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun readFileInEditor() {
        try {
            val STORETEXT = "firstcheckd.txt"
            val `in`: InputStream? = getActivity()?.openFileInput(STORETEXT)
            if (`in` != null) {
                val tmp = InputStreamReader(`in`)
                val reader = BufferedReader(tmp)
                var str: String = reader.readLine()
                `in`.close()
                if(str.toString()=="1"){
                    overlayPanel.visibility = View.INVISIBLE;
                }
                else{
                    overlayPanel.visibility = View.VISIBLE;
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
}