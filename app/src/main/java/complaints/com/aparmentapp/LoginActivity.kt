package complaints.com.aparmentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import complaints.com.aparmentapp.Models.EmployeeModel;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class LoginActivity extends AppCompatActivity {
EditText user_name,password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_name=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.password);
        login_btn=(Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_name.getText().toString().length()==0){
                    Toast.makeText(LoginActivity.this,"Please Enter User name", Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().length()==0){
                    Toast.makeText(LoginActivity.this,"Please Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    CheckLoginCredentials(user_name.getText().toString(),password.getText().toString());
                }
            }
        });
    }
    private void CheckLoginCredentials(final String uname, final String pwd) {

        final ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(LoginActivity.this);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee";

        Log.d("TAG","login clicked");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONObject jsonObject = null;
                        Log.d("TAG","login clicked "+response);
                        try {
                            jsonObject = new JSONObject(response);
                            Log.d("TAG","login clicked 6 "+jsonObject);
                            try {
                                String message = jsonObject.getString("msg");
                                Log.d("TAG","login clicked 7 "+message);
                                if(message.equals("Success")){
                                   EmployeeModel operatorLoginData = new Gson().fromJson(jsonObject.getString("0").toString(), new TypeToken<EmployeeModel>() {
                                    }.getType());
                                    SaveAppData.getSessionDataInstance().saveEmpLoginData(operatorLoginData);
                                    progressdialog.dismiss();
                                    Intent regIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(regIntent);
                                    finish();
                                }
                                else{
                                    progressdialog.dismiss();
                                    Toast.makeText(LoginActivity.this,"Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG","login clicked 2");
                                }
                                //Process os success response
                            } catch (JSONException e) {
                                progressdialog.dismiss();
                                e.printStackTrace();
                                Log.d("TAG","login clicked 3 "+e);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressdialog.dismiss();
                            Log.d("TAG","login clicked 4 "+e);
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressdialog.dismiss();
                        Log.d("TAG","login clicked 5 "+error);
//                            createDialoges(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", uname);
                params.put("password", pwd);
                return params;

            }
        };
        new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
