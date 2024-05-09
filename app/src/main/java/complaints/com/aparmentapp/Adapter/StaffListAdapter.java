package complaints.com.aparmentapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import complaints.com.aparmentapp.Models.StaffCategoryModel;
import complaints.com.aparmentapp.Models.StaffListModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.MyViewHolder> {

    Context context;
    ArrayList<StaffListModel> vendorList;
    String fragment;
    EditText ed_name,ed_mobile,ed_email,ed_addr,ed_designation,ed_lname;
    String usertype,userrole;
    Spinner category,employee_role;
    String type;
    ArrayList<StaffCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    AlertDialog alertDialogAndroid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_uname,tv_flat,tv_add,in_time,tv_desc,date,tv_mobile,status;
        TextView edit;
        public MyViewHolder(View view) {
            super(view);
            tv_uname = (TextView) view.findViewById(R.id.tv_name);
            tv_flat = (TextView) view.findViewById(R.id.tv_flat);
            tv_add = (TextView) view.findViewById(R.id.tv_comptype);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
            status = (TextView) view.findViewById(R.id.status);
            date = (TextView) view.findViewById(R.id.tv_date);
            edit=(TextView)view.findViewById(R.id.edit);

        }
    }
    public StaffListAdapter(Context visitorlistFragment, ArrayList<StaffListModel> tripList) {
        this.vendorList = tripList;
        context = visitorlistFragment;
    }

    @Override
    public StaffListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_list_item, parent, false);
        return new StaffListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StaffListAdapter.MyViewHolder holder, final int position) {
        final StaffListModel tag = vendorList.get(position);
        holder.tv_uname.setText(tag.getEmp_first_name());
        holder.tv_mobile.setText(tag.getEmp_mobile_no());
        holder.tv_flat.setText(tag.getUser_role());
        holder.date.setText(tag.getDate_created());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StaffListModel tag = vendorList.get(position);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.update_staff, null);
                Getpackages(tag);
                ed_name=(EditText)mView.findViewById(R.id.ed_name);
                ed_lname=(EditText)mView.findViewById(R.id.ed_lname);
                ed_mobile=(EditText)mView.findViewById(R.id.ed_mobile);
                ed_addr=(EditText)mView.findViewById(R.id.ed_addr);
                ed_email=(EditText)mView.findViewById(R.id.ed_email);
                ed_designation=(EditText)mView.findViewById(R.id.ed_designation);
                category=(Spinner)mView.findViewById(R.id.category);
                category.setSelection(Integer.parseInt(tag.getUser_type())-1);
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



                employee_role=(Spinner)mView.findViewById(R.id.employee_role);
                ed_name.setText(tag.getEmp_first_name());
                ed_lname.setText(tag.getEmp_last_name());
                ed_mobile.setText(tag.getEmp_mobile_no());
                ed_addr.setText(tag.getEmp_add1());
                ed_email.setText(tag.getEmp_email());
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                Button submit_btn = (Button) mView.findViewById(R.id.submit_btn);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String Name =ed_name.getText().toString();
                        final String Lname =ed_lname.getText().toString();
                        final String Mobile =ed_mobile.getText().toString();
                        final String Email =ed_email.getText().toString();
                        final String Address =ed_addr.getText().toString();
                        final String category =type;
                        final String UserRole=getpersonname;
                        final String evnt_id=tag.getEmp_id();
                        if(Name.isEmpty()){
                            ed_name.setError("Please Enter First Name");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(Lname.isEmpty()){
                            ed_lname.setError("Please Enter Last Name");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(Address.isEmpty()){
                            ed_addr.setError("Please Enter Address");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                        }if(Mobile.isEmpty()){
                            ed_mobile.setError("Please Enter Mobile");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }if(Email.isEmpty()){
                            ed_email.setError("Please Enter Email Id");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }else {
                            AddEvent(Name,Lname,Address,Mobile,Email,category,UserRole,evnt_id);
                            alertDialogAndroid.dismiss();
                        }

                        alertDialogAndroid.dismiss();

                        //alertDialogAndroid.show();
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogAndroid.dismiss();
                    }
                });
                alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }


    private void Getpackages(final StaffListModel tag) {
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

                                    msoadapter = new ArrayAdapter<String>(context, R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    employee_role.setAdapter(msoadapter);
                                    String selection = tag.getUser_role();
                                    int pos = -1;

                                    for (int i = 0; i < packagelist.size(); i++) {
                                        if (packagelist.get(i).getEmployeerole().equals(selection)) {
                                            pos = i;
                                            break;
                                        }
                                    }
                                    employee_role.setSelection(pos);
                                    // category.setSelection(0);

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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void AddEvent(final String Name,final String Lname, final String Address,final String Mobile,final String Email,final String category,final String UserRole,final String evnt_id) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(context);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/update_employee";

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
                                Toast.makeText(context,"Staff Details updated Successfully",Toast.LENGTH_SHORT).show();

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
                params.put("firstname", Name);
                params.put("lastname", Lname);
                params.put("mobile", Mobile);
                params.put("address", Address);
                params.put("email", Email);
                params.put("usertype", category);
                params.put("role", UserRole);
                params.put("u_emp_id", evnt_id);
                return params;

            }
        };
        new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}
