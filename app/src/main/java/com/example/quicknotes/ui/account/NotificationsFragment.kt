package com.example.quicknotes.ui.account

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quicknotes.databinding.FragmentNotificationsBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import java.io.File
import java.io.InputStream


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val baseUrl = "http://10.0.2.2:5000/"

    private fun SavePreferences(key: String, value: String) {
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences(
            "APP_PREFERENCES", MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun GetPreference(key: String): String {
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences(
            "APP_PREFERENCES", MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "").toString()
    }


    fun login(username: String, password: String) {
        FuelManager.instance.baseHeaders = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0"
        )

        val bodyJson = """{
            "username": "${username}",
            "password": "${password}"
        }"""

        Fuel.post(baseUrl + "login").body(bodyJson).response { _, response, result ->
            result.fold(
                { str ->
                    val token = String(result.get())
                    SavePreferences("token", token)
                    SavePreferences("username", username)
                },
                { fuelError ->
                    println("got error: $fuelError")
                })
        }
        Log.d("Accounts:", "Logged in ${username}")
        println("Logged in")
    }

    fun register(username: String, password: String) {
        FuelManager.instance.baseHeaders = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0"
        )

        val bodyJson = """{
            "username": "${username}",
            "password": "${password}"
        }"""

        Fuel.post(baseUrl + "register").body(bodyJson).response { _, response, result ->
            result.fold(
                { str ->
                    val token = String(result.get())
                    SavePreferences("token", token)
                    SavePreferences("username", username)
                },
                { fuelError ->
                    println("got error: $fuelError")
                })
        }
        Log.d("Accounts:", "Registered ${username}")
        println("Registered")
    }

    fun post_notes() {
        FuelManager.instance.baseHeaders = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0"
        )

        val inputStream: InputStream = File(context!!.getFilesDir().getPath().toString() + "notes.json").inputStream()
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val token = GetPreference("token")

        val bodyJson = """{"token": "${token}",
            "notes": ${jsonString}}
            """.trimIndent()

        Fuel.post(baseUrl + "post_notes").body(bodyJson).response { _, response, result ->
            result.fold(
                { str ->
                    println("OK")
                },
                { fuelError ->
                    println("got error: $fuelError")
                })
        }
        Log.d("Accounts:", "Notes posted")
        println("Notes posted")
    }

    fun get_notes() {
        FuelManager.instance.baseHeaders = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0"
        )
        val token = GetPreference("token")

        Fuel.post(baseUrl + "get_notes").body(token).response { _, response, result ->
            result.fold(
                { str ->
                    val jsonString = String(result.get())
                    print(jsonString)
                    val myFile = File(context!!.getFilesDir().getPath().toString() + "notes.json")
                    myFile.writeText(jsonString)
                    println("OK")
                },
                { fuelError ->
                    println("got error: $fuelError")
                })
        }
        Log.d("Accounts:", "Notes downloaded")
        println("Notes downloaded")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val username: TextView = binding.username

        if (GetPreference("username") == "") {
            username.text = "Login to sync your notes"
        }else {
            username.text = "You are logged in as " + GetPreference("username")
        }

        val login_input: EditText = binding.loginInput
        val password_input: EditText = binding.passwordInput
        val login_button: Button = binding.loginButton

        val switchLogin = binding.switch1

        var buttonStateLogin = false

        switchLogin.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                buttonStateLogin = true
                login_button.text = "register"
            }else{
                buttonStateLogin = false
                login_button.text = "login"
            }
        }

        login_button.setOnClickListener {
            if (buttonStateLogin) {
                register(login_input.text.toString(), password_input.text.toString())
            }else {
                login(login_input.text.toString(), password_input.text.toString())
            }
        }

        val post_button: Button = binding.postButton

        post_button.setOnClickListener {
            post_notes()
        }

        val get_button: Button = binding.getButton

        get_button.setOnClickListener {
            get_notes()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}