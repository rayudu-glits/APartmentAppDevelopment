
package complaints.com.aparmentapp.Fragments;

/**
 * Created by Hari krishna on 4/20/2018.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
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
import android.widget.DatePicker;
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

import complaints.com.aparmentapp.Adapter.EventsAdapter;
import complaints.com.aparmentapp.Customviews.DividerItemDecoration;
import complaints.com.aparmentapp.Customviews.VerticalSpaceItemDecoration;
import complaints.com.aparmentapp.Models.EventCategoryModel;
import complaints.com.aparmentapp.Models.EventModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class EventsFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton addbutton;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rv_complaints;
    TextView tv_Norecords;
    EventsAdapter complaintsListAdapter;
    ArrayList<EventModel> list_visitors = new ArrayList<>();
    Spinner category;
    EditText date_edtv,description_edtv,end_edtv,event_amnt;
    private int mHour, mMinute, mYear, mMonth, mDay;
    ArrayList<EventCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    private static final int VERTICAL_ITEM_SPACE = 48;
    EditText et_searchuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_myunit, container, false);
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
        dialog.setContentView(R.layout.add_event_dialog);
        Getpackages();
        dialog.show();
        description_edtv=(EditText)dialog.findViewById(R.id.description_edtv);
        end_edtv=(EditText)dialog.findViewById(R.id.end_edtv);
        event_amnt=(EditText)dialog.findViewById(R.id.event_amnt);
        date_edtv=(EditText)dialog.findViewById(R.id.date_edtv);
        category=(Spinner)dialog.findViewById(R.id.category);
        date_edtv.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_edtv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        end_edtv.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                end_edtv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                end_edtv.findFocus();
            }
        });

        // TextView tv_message = (TextView) dialog .findViewById(R.id.textViewMessage);

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
                final String Amount =event_amnt.getText().toString();
                final String startdate =date_edtv.getText().toString();
                final String enddate =end_edtv.getText().toString();
                final String reason =description_edtv.getText().toString();
                final String category =getpersonid;
                if(Amount.isEmpty()){
                    event_amnt.setError("Please Enter Amount");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                }if(startdate.isEmpty()){
                    date_edtv.setError("Please Enter Date");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                }if(enddate.isEmpty()){
                    end_edtv.setError("Please Enter Date");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter reason",Toast.LENGTH_SHORT).show();
                }if(reason.isEmpty()){
                    description_edtv.setError("Please Enter Remarks");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }else {
                    AddEvent(Amount,startdate,enddate,reason,category);
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

            final String url = BaseUrlClass.getBaseUrl() + "employee/event_list";

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
                                                EventModel statesData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<EventModel>() {
                                                }.getType());
                                                list_visitors.add(statesData);

                                            }
                                        }
                                        complaintsListAdapter = new EventsAdapter(getActivity(), list_visitors);
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

            final String url = BaseUrlClass.getBaseUrl() + "employee/event_cat";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                EventCategoryModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    EventCategoryModel model = new EventCategoryModel();
                                    model.setEcat_name("Select Event");
                                    model.setEcat_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<EventCategoryModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getEcat_name();
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
                                            getpersonname = packagelist.get(position).getEcat_name();
                                            getpersonid = packagelist.get(position).getEcat_id();

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
    private void AddEvent(final String Amount,final String startdate,final String enddate,final String reason,final String category) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/add_event";

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
                params.put("amount", Amount);
                params.put("start_date", startdate);
                params.put("end_date", enddate);
                params.put("remarks", reason);
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
