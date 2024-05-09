

package complaints.com.aparmentapp.Fragments;

/**
 * Created by Hari krishna on 4/20/2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import complaints.com.aparmentapp.Adapter.VendorAdapter;
import complaints.com.aparmentapp.Customviews.DividerItemDecoration;
import complaints.com.aparmentapp.Customviews.VerticalSpaceItemDecoration;
import complaints.com.aparmentapp.Models.VendorCategoryModel;
import complaints.com.aparmentapp.Models.VendorModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class VendorsFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton addbutton;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rv_complaints;
    TextView tv_Norecords;
    private VendorAdapter complaintsListAdapter;
    ArrayList<VendorModel> list_visitors = new ArrayList<>();
    Spinner category;
    //TextView tv_name,tv_addr,tv_mobile,tv_email;
    EditText tv_name,tv_addr,tv_mobile,tv_email;
    private int mHour, mMinute, mYear, mMonth, mDay;
    ArrayList<VendorCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    private static final int VERTICAL_ITEM_SPACE = 48;
    EditText et_searchuser;
    public VendorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_myunit, container, false);
        addbutton = (FloatingActionButton)view.findViewById(R.id.fab);
        rv_complaints = (RecyclerView) view.findViewById(R.id.rv_complaints);
        tv_Norecords = (TextView) view.findViewById(R.id.tv_Norecords);
        et_searchuser = (EditText)view.findViewById(R.id.et_searchuser);
        et_searchuser.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_searchuser.getText().toString().toLowerCase(Locale.getDefault());
                if (complaintsListAdapter != null) {
                    complaintsListAdapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getComplaintsList();

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setLayoutManager(mLayoutManager);
        rv_complaints.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        rv_complaints.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        initListeners();
        getComplaintsList();
        return view;
    }

    private void initListeners() {
        addbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_vendor_dialog);
        Getpackages();
        dialog.show();
        tv_name=(EditText)dialog.findViewById(R.id.tv_name);
        tv_addr=(EditText)dialog.findViewById(R.id.tv_addr);
        tv_mobile=(EditText)dialog.findViewById(R.id.tv_mobile);
        tv_email=(EditText)dialog.findViewById(R.id.tv_email);
        category=(Spinner) dialog.findViewById(R.id.category);
        Button cancel = (Button)dialog.findViewById(R.id.cancel_btn);
        Button submit = (Button)dialog.findViewById(R.id.submit_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name =tv_name.getText().toString();
                final String Mobile =tv_mobile.getText().toString();
                final String Address =tv_addr.getText().toString();
                final String email =tv_email.getText().toString();
                final String category =getpersonid;
                if(Name.isEmpty()){
                    tv_name.setError("Please Enter Name");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                }if(Mobile.isEmpty()){
                    tv_mobile.setError("Please Enter Mobile No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                }if(Address.isEmpty()){
                    tv_addr.setError("Please Enter Address");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter reason",Toast.LENGTH_SHORT).show();
                }if(email.isEmpty()){
                    tv_email.setError("Please Enter Email");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }else {
                    AddEvent(Name,Mobile,Address,email,category);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
    }
    private void getComplaintsList() {

        try {
            final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);

            final String url = BaseUrlClass.getBaseUrl() + "employee/vendor_list";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                try {
                                    list_visitors = new ArrayList<>();
                                    String message = jsonObject.getString("msg");
                                    if (message.equalsIgnoreCase("Success")) {
                                        Iterator<String> keys = jsonObject.keys();
                                        while (keys.hasNext()) {
                                            String key = keys.next();
                                            if (!key.equalsIgnoreCase("msg")) {
                                                VendorModel statesData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<VendorModel>() {
                                                }.getType());
                                                list_visitors.add(statesData);

                                            }
                                        }
                                        complaintsListAdapter = new VendorAdapter(getActivity(), list_visitors);
                                        rv_complaints.setAdapter(complaintsListAdapter);
                                        tv_Norecords.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getActivity(), "No History Found", Toast.LENGTH_SHORT).show();
                                        tv_Norecords.setVisibility(View.VISIBLE);
                                    }
                                    progressdialog.dismiss();
                                    swipeRefreshLayout.setRefreshing(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    progressdialog.dismiss();
                                    swipeRefreshLayout.setRefreshing(false);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                swipeRefreshLayout.setRefreshing(false);
                                progressdialog.dismiss();
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressdialog.dismiss();
                            swipeRefreshLayout.setRefreshing(false);

                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getSessionDataInstance().getLoginData().getEmp_id());
                    params.put("type", SaveAppData.getSessionDataInstance().getLoginData().getUser_type());
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Getpackages() {
        try {
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/vendor_cat";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                VendorCategoryModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    VendorCategoryModel model = new VendorCategoryModel();
                                    model.setVend_cat_name("Select Type");
                                    model.setVend_cat_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<VendorCategoryModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getVend_cat_name();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    msoadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    category.setAdapter(msoadapter);
                                    category.setSelection(0);

                                    category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //StatesModel getStateId = statesList.get(position);
                                            //if(getStbmakename!=null)
                                            getpersonname = packagelist.get(position).getVend_cat_name();
                                            getpersonid = packagelist.get(position).getVend_cat_id();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                                String compareValue = getpersonname;

                                if (compareValue != null) {
                                    int spinnerPosition = msoadapter.getPosition(compareValue);
                                    category.setSelection(spinnerPosition);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getSessionDataInstance().getLoginData().getEmp_id());
                    params.put("type", SaveAppData.getSessionDataInstance().getLoginData().getUser_type());
                    //params.put("cust_id", getpersonid);
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void AddEvent(final String Name,final String Mobile,final String Address,final String email,final String category) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/add_vendor";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            try {
                                String message = jsonObject.getString("msg");
                                progressdialog.dismiss();
                                Toast.makeText(getActivity(),"Complaint Created  Successfully",Toast.LENGTH_SHORT).show();

                                //Process os success response
                            } catch (JSONException e) {
                                progressdialog.dismiss();
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressdialog.dismiss();
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressdialog.dismiss();
//                            createDialoges(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("emp_id", SaveAppData.getSessionDataInstance().getLoginData().getEmp_id());
                params.put("type", SaveAppData.getSessionDataInstance().getLoginData().getUser_type());
                params.put("category", category);
                params.put("name", Name);
                params.put("mobile", Mobile);
                params.put("address", Address);
                params.put("email", email);
                return params;

            }
        };
        new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
