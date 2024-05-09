package complaints.com.aparmentapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import complaints.com.aparmentapp.Models.EmployeeModel
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private var user_name: EditText? = null
    var password: EditText? = null
    private var login_btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        user_name = findViewById<View>(R.id.user_name) as EditText
        password = findViewById<View>(R.id.password) as EditText
        login_btn = findViewById<View>(R.id.login_btn) as Button
        login_btn!!.setOnClickListener {
            if (user_name!!.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please Enter User name", Toast.LENGTH_SHORT)
                    .show()
            } else if (password!!.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please Enter Password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                checkLoginCredentials(user_name!!.text.toString(), password!!.text.toString())
            }
        }
    }

    private fun checkLoginCredentials(uname: String, pwd: String) {
        val progressdialog = BaseUrlClass.createProgressDialog(this@LoginActivity)
        progressdialog.show()
        progressdialog.setCancelable(false)
        val URL = BaseUrlClass.getBaseUrl() + "employee"
        Log.d("TAG", "login clicked")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL,
            Response.Listener<String?> { response ->
                println(response)
                var jsonObject: JSONObject? = null
                Log.d("TAG", "login clicked $response")
                try {
                    jsonObject = JSONObject(response)
                    Log.d("TAG", "login clicked 6 $jsonObject")
                    try {
                        val message = jsonObject.getString("msg")
                        Log.d("TAG", "login clicked 7 $message")
                        if (message == "Success") {
                            val operatorLoginData = Gson().fromJson<EmployeeModel>(
                                jsonObject.getString("0").toString(),
                                object : TypeToken<EmployeeModel?>() {}.type
                            )
                            SaveAppData.saveEmpLoginData(operatorLoginData)
                            progressdialog.dismiss()
                            val regIntent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(regIntent)
                            finish()
                        } else {
                            progressdialog.dismiss()
                            Toast.makeText(
                                this@LoginActivity,
                                "Invalid Login Credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("TAG", "login clicked 2")
                        }
                        //Process os success response
                    } catch (e: JSONException) {
                        progressdialog.dismiss()
                        e.printStackTrace()
                        Log.d("TAG", "login clicked 3 $e")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressdialog.dismiss()
                    Log.d("TAG", "login clicked 4 $e")
                    // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                }
            },
            Response.ErrorListener { error ->
                progressdialog.dismiss()
                Log.d("TAG", "login clicked 5 $error")
                //                            createDialoges(error.toString());
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = uname
                params["password"] = pwd
                return params
            }
        }
        DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}