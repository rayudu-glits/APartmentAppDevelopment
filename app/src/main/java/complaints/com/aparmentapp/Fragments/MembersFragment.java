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

import complaints.com.aparmentapp.Adapter.MemberAdapter;
import complaints.com.aparmentapp.Customviews.DividerItemDecoration;
import complaints.com.aparmentapp.Customviews.VerticalSpaceItemDecoration;
import complaints.com.aparmentapp.Models.MemberGroupModel;
import complaints.com.aparmentapp.Models.MemberModel;
import complaints.com.aparmentapp.Models.MemberPackageModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class MembersFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton addbutton;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rv_complaints;
    TextView tv_Norecords;
    MemberAdapter complaintsListAdapter;
    ArrayList<MemberModel> list_visitors = new ArrayList<>();
    private static final int VERTICAL_ITEM_SPACE = 48;
    ArrayList<MemberPackageModel> packagelist = new ArrayList<>();
    ArrayList<MemberGroupModel> grouplist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    ArrayAdapter<String> groupadapter;
    public String getPackname = null, getpackid = null;
    public String getgroupname = null, getgroupid = null;
    EditText First_name,last_name,email_id,mobile_no,elctric_no,water_no,municipal_no,address,flat;
    Spinner group_id,package_id,owenership;
    String usertype,type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bazar, container, false);
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
        return view;
    }

    private void initListeners() {
        addbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.member_reg);
        Getgroups();
        Getpackages();
        dialog.show();
        First_name=(EditText)dialog.findViewById(R.id.First_name);
        last_name=(EditText)dialog.findViewById(R.id.last_name);
        email_id=(EditText)dialog.findViewById(R.id.email_id);
        mobile_no=(EditText)dialog.findViewById(R.id.mobile_no);
        elctric_no=(EditText)dialog.findViewById(R.id.elctric_no);
        water_no=(EditText)dialog.findViewById(R.id.water_no);
        municipal_no=(EditText)dialog.findViewById(R.id.municipal_no);
        flat=(EditText)dialog.findViewById(R.id.flat);
        address=(EditText)dialog.findViewById(R.id.address);
        group_id=(Spinner)dialog.findViewById(R.id.group_id);
        package_id=(Spinner)dialog.findViewById(R.id.package_id);
        owenership=(Spinner)dialog.findViewById(R.id.owenership);
        owenership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usertype = owenership.getItemAtPosition(position).toString();

                if(usertype.equals("OWENER/TENENT")){
                    type="OWENER/TENENT";
                }else if(usertype.equals("TENENT")){
                    type="TENENT";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                final String firstName =First_name.getText().toString();
                final String LName =last_name.getText().toString();
                final String Email =email_id.getText().toString();
                final String Mobile =mobile_no.getText().toString();
                final String Electricno =elctric_no.getText().toString();
                final String waterno =water_no.getText().toString();
                final String municipalno =municipal_no.getText().toString();
                final String Flat =flat.getText().toString();
                final String Address =address.getText().toString();
                final String Package =getpackid;
                final String Group =getgroupid;
                final String Owenership=type;
                if(firstName.isEmpty()){
                    First_name.setError("Please Enter First Name");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                }if(LName.isEmpty()){
                    last_name.setError("Please Enter Last Name");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                }if(Email.isEmpty()){
                    email_id.setError("Please Enter Email id");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter reason",Toast.LENGTH_SHORT).show();
                }if(Mobile.isEmpty()){
                    mobile_no.setError("Please Enter Mobile No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Flat.isEmpty()){
                    flat.setError("Please Enter Flat No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Address.isEmpty()){
                    mobile_no.setError("Please Enter Address No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Electricno.isEmpty()){
                    elctric_no.setError("Please Enter Electric No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(waterno.isEmpty()){
                    water_no.setError("Please Enter Water No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(municipalno.isEmpty()){
                    municipal_no.setError("Please Enter Municipal No");
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Package.equals("")){
                    Toast.makeText(getActivity(),"Please Select Package",Toast.LENGTH_SHORT).show();
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Group.equals("")){
                    Toast.makeText(getActivity(),"Please Select Group",Toast.LENGTH_SHORT).show();
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }if(Owenership.equals("")){
                    Toast.makeText(getActivity(),"Please Select Owenership",Toast.LENGTH_SHORT).show();
                    return;
                    //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                }else {
                    AddEvent(firstName,LName,Email,Mobile,Electricno,waterno,municipalno,Package,Group,Flat,Address,Owenership);
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

            final String url = BaseUrlClass.getBaseUrl() + "employee/users_list";

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
                                                MemberModel statesData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<MemberModel>() {
                                                }.getType());
                                                list_visitors.add(statesData);

                                            }
                                        }
                                        complaintsListAdapter = new MemberAdapter(getActivity(), list_visitors);
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

            final String url = BaseUrlClass.getBaseUrl() + "employee/packages";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                MemberPackageModel stbMakeModel= null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    MemberPackageModel model = new MemberPackageModel();
                                    model.setPackage_name("Select Package");
                                    model.setPackage_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<MemberPackageModel>(){
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getPackage_name();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    msoadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    package_id.setAdapter(msoadapter);
                                    package_id.setSelection(0);

                                    package_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //StatesModel getStateId = statesList.get(position);
                                            //if(getStbmakename!=null)
                                            getPackname = packagelist.get(position).getPackage_name();
                                            getpackid = packagelist.get(position).getPackage_id();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                                String compareValue = getPackname;

                                if (compareValue != null) {
                                    int spinnerPosition = msoadapter.getPosition(compareValue);
                                    package_id.setSelection(spinnerPosition);

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
    private void Getgroups() {
        try {
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/groups";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                grouplist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                MemberGroupModel stbMakeModel= null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    MemberGroupModel model = new MemberGroupModel();
                                    model.setGroup_name("Select Group");
                                    model.setGroup_id("");
                                    grouplist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<MemberGroupModel>(){
                                                    }.getType());
                                            grouplist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[grouplist.size()];
                                    for (int i = 0; i < grouplist.size(); i++) {
                                        msoarray[i] = grouplist.get(i).getGroup_name();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    groupadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, msoitemsList);

                                    groupadapter.setDropDownViewResource(R.layout.spinner_item);
                                    group_id.setAdapter(groupadapter);
                                    group_id.setSelection(0);

                                    group_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //StatesModel getStateId = statesList.get(position);
                                            //if(getStbmakename!=null)
                                            getgroupname = grouplist.get(position).getGroup_name();
                                            getgroupid = grouplist.get(position).getGroup_id();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                                String compareValue = getgroupname;

                                if (compareValue != null) {
                                    int spinnerPosition = groupadapter.getPosition(compareValue);
                                    group_id.setSelection(spinnerPosition);

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
    private void AddEvent(final String firstName,final String LName,final String Email,final String Mobile,final String Electricno,final String waterno,final String municipalno,final String Package,final String Group,final String Flat,final String Address,final String Owenership) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/add_user";

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
                                Toast.makeText(getActivity(),"Member Created  Successfully",Toast.LENGTH_SHORT).show();

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
                params.put("firstname", firstName);
                params.put("lastname", LName);
                params.put("address", Address);
                params.put("email", Email);
                params.put("mobile", Mobile);
                params.put("ownership", Owenership);
                params.put("group_id", Group);
                params.put("package_id", Package);
                params.put("flat_no", Flat);
                params.put("electricity_meter", Electricno);
                params.put("water_meter", waterno);
                params.put("muncipal_tax_no", municipalno);
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
