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
import java.util.Locale;
import java.util.Map;

import complaints.com.aparmentapp.Models.VendorCategoryModel;
import complaints.com.aparmentapp.Models.VendorModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.MyViewHolder> {

    Context context;
   private ArrayList<VendorModel> vendorList;
    private ArrayList<VendorModel> vendororiginalList;
    EditText tv_name,tv_addr,tv_mobile,tv_email;
    Spinner category;
    ArrayList<VendorCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    AlertDialog alertDialogAndroid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_flat,tv_comptype,tv_mobile,edit;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_flat = (TextView) view.findViewById(R.id.tv_flat);
            tv_comptype = (TextView) view.findViewById(R.id.tv_comptype);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
            edit = (TextView) view.findViewById(R.id.edit);



        }
    }
    public VendorAdapter(Context visitorlistFragment, ArrayList<VendorModel> tripList) {
        this.vendorList = tripList;
        vendororiginalList = new ArrayList<>();
        context = visitorlistFragment;
        vendororiginalList.addAll(tripList);
    }

    @Override
    public VendorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_lis_item, parent, false);
        return new VendorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VendorAdapter.MyViewHolder holder, final int position) {
        final VendorModel tag = vendorList.get(position);
        holder.tv_name.setText(tag.getVend_name());
        holder.tv_flat.setText(tag.getVend_cat_name());
        holder.tv_comptype.setText("Address : " + tag.getVend_address());
        holder.tv_mobile.setText(tag.getVend_mobile_no());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final VendorModel tag = vendorList.get(position);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.edit_vendor_dialog, null);
                Getpackages(tag);
                tv_name=(EditText)mView.findViewById(R.id.tv_name);
                tv_addr=(EditText)mView.findViewById(R.id.tv_addr);
                tv_mobile=(EditText)mView.findViewById(R.id.tv_mobile);
                tv_email=(EditText)mView.findViewById(R.id.tv_email);
                category=(Spinner) mView.findViewById(R.id.category);
                tv_name.setText(tag.getVend_name());
                tv_addr.setText(tag.getVend_address());
                tv_mobile.setText(tag.getVend_mobile_no());
                tv_email.setText(tag.getVend_email());
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                Button submit_btn = (Button) mView.findViewById(R.id.submit_btn);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String Name =tv_name.getText().toString();
                        final String Address =tv_addr.getText().toString();
                        final String Mobile =tv_mobile.getText().toString();
                        final String Email =tv_email.getText().toString();
                        final String category =getpersonid;
                        final String evnt_id=tag.getVendor_id();
                        if(Name.isEmpty()){
                            tv_name.setError("Please Enter Name");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(Address.isEmpty()){
                            tv_addr.setError("Please Enter Address");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                        }if(Mobile.isEmpty()){
                            tv_mobile.setError("Please Enter Mobile");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }if(Email.isEmpty()){
                            tv_email.setError("Please Enter Email Id");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }else {
                            AddEvent(Name,Address,Mobile,Email,category,evnt_id);
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
        public int getItemCount () {
            return vendorList.size();
        }
    private void Getpackages(final VendorModel tag) {
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
                                    model.setVend_cat_name("Select Category");
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

                                    msoadapter = new ArrayAdapter<String>(context, R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    category.setAdapter(msoadapter);
                                    String selection = tag.getVend_cat_name();
                                    int pos = -1;

                                    for (int i = 0; i < packagelist.size(); i++) {
                                        if (packagelist.get(i).getVend_cat_name().equals(selection)) {
                                            pos = i;
                                            break;
                                        }
                                    }
                                    category.setSelection(pos);
                                    // category.setSelection(0);

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
    private void AddEvent(final String Name,final String Address,final String Mobile,final String Email,final String category,final String evnt_id) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(context);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/update_vendor";

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
                                Toast.makeText(context,"Vendor Details updated Successfully",Toast.LENGTH_SHORT).show();

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
                params.put("email", Email);
                params.put("vendor_id", evnt_id);
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
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        vendorList.clear();
        if (charText.length() == 0) {
            vendorList.addAll(vendororiginalList);
        }
        else {
            for (VendorModel wp : vendororiginalList) {
                if(wp.getVend_name()!=null) {
                    if (wp.getVend_name().toLowerCase(Locale.getDefault()).contains(charText) ||
                            wp.getVend_address().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getVend_mobile_no().toLowerCase(Locale.getDefault()).contains(charText)
                            ) {
                        vendorList.add(wp);
                    }
                }else {
                    if (wp.getVend_address().toLowerCase(Locale.getDefault()).contains(charText)
                            || wp.getVend_mobile_no().toLowerCase(Locale.getDefault()).contains(charText)) {
                        vendorList.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    }


