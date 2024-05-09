package complaints.com.aparmentapp.Fragments;

/**
 * Created by Hari krishna on 4/20/2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import complaints.com.aparmentapp.Adapter.ComplaintsListAdapter;
import complaints.com.aparmentapp.Adapter.StaffAdapter;
import complaints.com.aparmentapp.Customviews.DividerItemDecoration;
import complaints.com.aparmentapp.Customviews.VerticalSpaceItemDecoration;
import complaints.com.aparmentapp.Models.ComplaintsListModel;
import complaints.com.aparmentapp.Models.CustomerListModel;
import complaints.com.aparmentapp.Models.GroupModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class ComplaintsFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton addbutton;
    Dialog dialog;
    static final int DATE_DIALOG_ID = 0;
    private int mYear, mMonth, mDay;
    LinearLayout ll_info;
    TextView emp_name,emp_mobile,emp_addr1,emp_desig,tv_noinfo,emp_hdesig,emp_haddr1,emp_hmobile,emp_head,tv_Norecords;
    ArrayList<CustomerListModel> usersList = new ArrayList<>();
    StaffAdapter userListAdapter;
    ComplaintsListAdapter complaintsListAdapter;
    ArrayList<ComplaintsListModel> list_visitors = new ArrayList<>();
    AutoCompleteTextView atv_searchuser;
    ImageView iv_search;
    Spinner category;
    JSONObject obj;
    ArrayList<GroupModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    EditText remarks,description_edtv;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rv_complaints;
    private static final int VERTICAL_ITEM_SPACE = 80;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab);
        rv_complaints = (RecyclerView) view.findViewById(R.id.rv_complaints);
        tv_Norecords = (TextView) view.findViewById(R.id.tv_Norecords);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getComplaintsList();

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setLayoutManager(mLayoutManager);
        rv_complaints.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        rv_complaints.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        initListeners();
        getComplaintsList();
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        return view;
    }

    private void initListeners() {
        addbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Getpackages();
        dialog = new Dialog(getActivity());
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        // lp.alpha = 0.7f; // Transparency
        window.setAttributes(lp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_complaint_dialog);
        GetUsersFilter();
        dialog.show();
        atv_searchuser = (AutoCompleteTextView)dialog.findViewById(R.id.atv_searchuser);
        iv_search = (ImageView)dialog.findViewById(R.id.iv_search);
        emp_name=(TextView)dialog.findViewById(R.id.emp_name);
        emp_head=(TextView)dialog.findViewById(R.id.emp_head);
        emp_hmobile=(TextView)dialog.findViewById(R.id.emp_hmobile);
        emp_haddr1=(TextView)dialog.findViewById(R.id.emp_haddr1);
        emp_hdesig=(TextView)dialog.findViewById(R.id.emp_hdesig);
        emp_mobile=(TextView)dialog.findViewById(R.id.emp_mobile);
        emp_addr1=(TextView)dialog.findViewById(R.id.emp_addr1);
        emp_desig=(TextView)dialog.findViewById(R.id.emp_desig);
        ll_info=(LinearLayout) dialog.findViewById(R.id.ll_info);
        remarks=(EditText) dialog.findViewById(R.id.remarks);
        description_edtv=(EditText) dialog.findViewById(R.id.description_edtv);
        category=(Spinner) dialog.findViewById(R.id.category);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchUser(atv_searchuser.getText().toString().trim());

            }
        });

        /*EditText event = (EditText) dialog.findViewById(R.id.event_edtv);
        EditText date = (EditText) dialog.findViewById(R.id.date_edtv);
        EditText description = (EditText) dialog.findViewById(R.id.description_edtv);*/

        Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);
        Button submit = (Button) dialog.findViewById(R.id.submit_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasendtoserver();
                dialog.dismiss();
            }

            private void datasendtoserver() {
                final String category=getpersonid;
                final String categoryname=description_edtv.getText().toString();
                final String getdisplayname=atv_searchuser.getText().toString().trim();
                final String remark=remarks.getText().toString().trim();
                AddAttendence(category,categoryname,getdisplayname,remark);
            }
        });
    }

    private void GetUsersFilter() {
        try {
            final ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);

            final String url = BaseUrlClass.getBaseUrl()+"employee/users_short_info";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {
                            System.out.println(res);
                            JSONObject response = null;
                            try {
                                response = new JSONObject(res);

                                String message = response.getString("msg");
                                if (message.equalsIgnoreCase("Success")) {
                                    Iterator<String> keys = response.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            CustomerListModel cchData = new Gson().fromJson(response.getJSONObject(key).toString(), new TypeToken<CustomerListModel>() {
                                            }.getType());
                                            usersList.add(cchData);
                                        }
                                    }

                                    userListAdapter = new StaffAdapter(getActivity(), usersList);
                                    atv_searchuser.setAdapter(userListAdapter);
                                    atv_searchuser.setThreshold(1);

                                    atv_searchuser.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                                long arg3) {
                                            //StatesModel getStateId = statesList.get(position);
                                            //getdisplayname = usersList.get(position).getUsername();
                                        }
                                    });

                                }else{
                                    Toast.makeText(getActivity(),"No Users found",Toast.LENGTH_SHORT).show();
                                }
                                progressdialog.dismiss();
                            } catch (Exception e) {
                                progressdialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressdialog.dismiss();
                            Toast.makeText(getActivity(),"Slow Internet connection",Toast.LENGTH_SHORT).show();
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

    private void SearchUser(final String getdisplayname) {
        try {
            final ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);

            final String url = BaseUrlClass.getBaseUrl()+"employee/user_info";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {
                            System.out.println(res);
                            JSONObject response = null;
                            try {
                                response = new JSONObject(res);
                                usersList = new ArrayList<>();
                                String message = response.getString("msg");
                                if (message.equalsIgnoreCase("Success")) {
                                    ll_info.setVisibility(View.VISIBLE);
                                    obj = response.getJSONObject("0");
                                    emp_name.setText(obj.getString("first_name"));
                                    emp_mobile.setText(obj.getString("mobile_no"));
                                    emp_addr1.setText(obj.getString("addr1"));
                                    emp_desig.setText(obj.getString("email_id"));

                                    // et_amount.setText(obj.getString("pendingAmount"));
                                }else{
                                    ll_info.setVisibility(View.GONE);
                                    tv_noinfo.setText("No User Found !");
                                    tv_noinfo.setVisibility(View.VISIBLE);
                                    // et_amount.setText("");
                                    Toast.makeText(getActivity(),"No Users found",Toast.LENGTH_SHORT).show();
                                }
                                progressdialog.dismiss();
                                View view = getActivity().getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                            } catch (Exception e) {
                                progressdialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressdialog.dismiss();
                            //et_amount.setText("");
                            VolleyLog.e("Error: ", error.getMessage());
                            View view = getActivity().getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            Toast.makeText(getActivity(),"Slow Internet connection",Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getSessionDataInstance().getLoginData().getEmp_id());
                    params.put("type", SaveAppData.getSessionDataInstance().getLoginData().getUser_type());
                    params.put("cust_id", getdisplayname);
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
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(VisitorsActivity.this);
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/complaint_cat";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                GroupModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    GroupModel model = new GroupModel();
                                    model.setCategory("Whom to Meet");
                                    model.setId("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<GroupModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getCategory();
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
                                            getpersonname = packagelist.get(position).getCategory();
                                            getpersonid = packagelist.get(position).getId();

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
    private void AddAttendence(final String category,final String categoryname,final String getdisplayname,final String remark) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/add_complaint";

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
                                    emp_name.setText("");
                                    emp_mobile.setText("");
                                    emp_addr1.setText("");
                                    emp_desig.setText("");
                                    ll_info.setVisibility(View.GONE);
                                    atv_searchuser.setText("");

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
                params.put("complaint", categoryname);
                params.put("cust_id", getdisplayname);
                params.put("remarks", remark);
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
    private void getComplaintsList() {

        try {
            final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);

            final String url = BaseUrlClass.getBaseUrl() + "employee/complaints";

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
                                                ComplaintsListModel statesData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<ComplaintsListModel>() {
                                                }.getType());
                                                list_visitors.add(statesData);

                                            }
                                        }
                                        complaintsListAdapter = new ComplaintsListAdapter(getActivity(), list_visitors);
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
}
