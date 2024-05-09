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
import java.util.Map;

import complaints.com.aparmentapp.Adapter.StaffListAdapter;
import complaints.com.aparmentapp.Customviews.DividerItemDecoration;
import complaints.com.aparmentapp.Customviews.VerticalSpaceItemDecoration;
import complaints.com.aparmentapp.Models.StaffCategoryModel;
import complaints.com.aparmentapp.Models.StaffListModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class StaffFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton addbutton;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rv_complaints;
    TextView tv_Norecords;
    StaffListAdapter complaintsListAdapter;
    ArrayList<StaffListModel> list_visitors = new ArrayList<>();
    EditText ed_name,ed_mobile,ed_email,ed_addr,ed_designation,ed_lname;
    String usertype,userrole;
    Spinner category,employee_role;
    private static final int VERTICAL_ITEM_SPACE = 48;
    String type;
    ArrayList<StaffCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rent, container, false);
         addbutton = (FloatingActionButton)view.findViewById(R.id.fab);
         rv_complaints = (RecyclerView) view.findViewById(R.id.rv_complaints);
         tv_Norecords = (TextView) view.findViewById(R.id.tv_Norecords);
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
         initListeners();
         return view;
     }

    private void initListeners() {
        addbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_staff);
        Getpackages();
        dialog.show();


       ed_name=(EditText)dialog.findViewById(R.id.ed_name);
        ed_lname=(EditText)dialog.findViewById(R.id.ed_lname);
       ed_mobile=(EditText)dialog.findViewById(R.id.ed_mobile);
       ed_addr=(EditText)dialog.findViewById(R.id.ed_addr);
       ed_email=(EditText)dialog.findViewById(R.id.ed_email);
       ed_designation=(EditText)dialog.findViewById(R.id.ed_designation);
        category=(Spinner)dialog.findViewById(R.id.category);
        employee_role=(Spinner)dialog.findViewById(R.id.employee_role);



        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usertype = category.getItemAtPosition(position).toString();

                if(usertype.equals("Admin")){
                    type="1";
                }else if(usertype.equals("Employee")){
                    type="2";
                }else if(usertype.equals("Technician")){
                    type="3";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //tv_message.setText(message);

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
                final String Name =ed_name.getText().toString();
                final String LName =ed_lname.getText().toString();
                final String Mobile =ed_mobile.getText().toString();
                final String email =ed_email.getText().toString();
                final String Address =ed_addr.getText().toString();
                final String Designation=ed_designation.getText().toString();
                final String UserType=type;
                final String UserRole=getpersonname;
                if(Name.isEmpty()){
                    ed_name.setError("Please Enter Name");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                }if(LName.isEmpty()){
                    ed_name.setError("Please Enter Last Name");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                }if(Mobile.isEmpty()){
                    ed_mobile.setError("Please Enter Mobile");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                }if(email.isEmpty()){
                    ed_email.setError("Please Enter Email id");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter reason",Toast.LENGTH_SHORT).show();
                }if(Address.isEmpty()){
                    ed_addr.setError("Please Enter Address");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Designation.isEmpty()){
                    ed_designation.setError("Please Enter Designation");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }else {
                    AddEvent(Name,Mobile,email,Address,Designation,UserType,LName,UserRole);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
    }
    private void Getpackages() {
        try {
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/employee_roles";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                StaffCategoryModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    StaffCategoryModel model = new StaffCategoryModel();
                                    model.setEmployeerole("Select Role");
                                    model.setEmp_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<StaffCategoryModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getEmployeerole();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    msoadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    employee_role.setAdapter(msoadapter);
                                    employee_role.setSelection(0);

                                    employee_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //StatesModel getStateId = statesList.get(position);
                                            //if(getStbmakename!=null)
                                            getpersonname = packagelist.get(position).getEmployeerole();
                                            getpersonid = packagelist.get(position).getEmp_id();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                                String compareValue = getpersonname;

                                if (compareValue != null) {
                                    int spinnerPosition = msoadapter.getPosition(compareValue);
                                    employee_role.setSelection(spinnerPosition);

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
    private void getComplaintsList() {

        try {
            final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);

            final String url = BaseUrlClass.getBaseUrl() + "employee/staff_list";

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
                                                StaffListModel statesData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<StaffListModel>() {
                                                }.getType());
                                                list_visitors.add(statesData);

                                            }
                                        }
                                        complaintsListAdapter = new StaffListAdapter(getActivity(), list_visitors);
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
    private void AddEvent(final String Name,final String Mobile,final String email,final String Address,final String Designation,final String UserType,final String LName,final String UserRole) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/add_employee";

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
                params.put("firstname", Name);
                params.put("lastname", LName);
                params.put("address", Address);
                params.put("email", email);
                params.put("mobile", Mobile);
                params.put("usertype", UserType);
                params.put("role", UserRole);
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
