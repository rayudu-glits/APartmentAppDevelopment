package complaints.com.aparmentapp.Adapter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import complaints.com.aparmentapp.Models.ExpenseCategoryModel;
import complaints.com.aparmentapp.Models.ExpenseModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    Context context;
    private ArrayList<ExpenseModel> vendorList;
    private  ArrayList<ExpenseModel> vendororiginalList;
    String fragment;
    AlertDialog alertDialogAndroid;
    Spinner category;
    EditText exp_name,exp_rcp,exp_date,exp_price,remarks;
    Button cancel_btn,submit_btn;
    private int mHour, mMinute, mYear, mMonth, mDay;
    ArrayList<ExpenseCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_uname,tv_flat,tv_add,in_time,tv_desc,date,tv_mobile,status,edit,tv_comptype;

        public MyViewHolder(View view) {
            super(view);
            tv_uname = (TextView) view.findViewById(R.id.tv_name);
            tv_flat = (TextView) view.findViewById(R.id.tv_flat);
            tv_add = (TextView) view.findViewById(R.id.date);
            tv_comptype = (TextView) view.findViewById(R.id.tv_comptype);
            edit = (TextView) view.findViewById(R.id.edit);


        }
    }
    public ExpenseAdapter(Context visitorlistFragment, ArrayList<ExpenseModel> tripList) {
        this.vendorList = tripList;
        vendororiginalList = new ArrayList<>();
        vendororiginalList.addAll(tripList);
        context = visitorlistFragment;
    }

    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_list_item, parent, false);
        return new ExpenseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExpenseAdapter.MyViewHolder holder, final int position) {
        final ExpenseModel tag = vendorList.get(position);
        holder.tv_uname.setText(tag.getName());
        holder.tv_flat.setText("Amount : " + tag.getInward_qty());
        holder.tv_add.setText("From : " + tag.getReceipt_date());
        holder.tv_comptype.setText("Remarks : " + tag.getRemarks());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ExpenseModel tag = vendorList.get(position);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.edit_expenses, null);
                Getpackages(tag);
                exp_name = (EditText) mView.findViewById(R.id.exp_name);
                exp_price = (EditText) mView.findViewById(R.id.exp_price);
                exp_date = (EditText) mView.findViewById(R.id.exp_date);
                category = (Spinner) mView.findViewById(R.id.category);
                remarks=(EditText)mView.findViewById(R.id.remarks);
                exp_price.setText(tag.getInward_qty());
                exp_date.setText(tag.getReceipt_date());
                remarks.setText(tag.getRemarks());
                exp_date.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        exp_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                Button submit_btn = (Button) mView.findViewById(R.id.submit_btn);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String Amount =exp_price.getText().toString();
                        final String startdate =exp_date.getText().toString();
                        final String reason =remarks.getText().toString();
                        final String category =getpersonid;
                        final String evnt_id=tag.getExp_inv_id();
                        if(Amount.isEmpty()){
                            exp_price.setError("Please Enter Amount");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(startdate.isEmpty()){
                            exp_date.setError("Please Enter Date");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                        }if(reason.isEmpty()){
                            remarks.setError("Please Enter Remarks");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }else {
                            AddEvent(Amount,startdate,reason,category,evnt_id);
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

    private void Getpackages(final ExpenseModel tag) {
        try {
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/expense_cat";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                ExpenseCategoryModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    ExpenseCategoryModel model = new ExpenseCategoryModel();
                                    model.setName("Select Category");
                                    model.setExp_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<ExpenseCategoryModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getName();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    msoadapter = new ArrayAdapter<String>(context, R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    category.setAdapter(msoadapter);
                                    String selection = tag.getName();
                                    int pos = -1;

                                    for (int i = 0; i < packagelist.size(); i++) {
                                        if (packagelist.get(i).getName().equals(selection)) {
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
                                            getpersonname = packagelist.get(position).getName();
                                            getpersonid = packagelist.get(position).getExp_id();

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
    private void AddEvent(final String Amount,final String startdate,final String reason,final String category,final String evnt_id) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(context);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/update_expense";

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
                                Toast.makeText(context,"Expense updated Successfully",Toast.LENGTH_SHORT).show();

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
                params.put("date", startdate);
                params.put("remarks", reason);
                params.put("exp_inv_id", evnt_id);
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
            for (ExpenseModel wp : vendororiginalList) {
                if(wp.getName()!=null) {
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        //wp.getVend_address().toLowerCase(Locale.getDefault()).contains(charText)||
                        // wp.getVend_mobile_no().toLowerCase(Locale.getDefault()).contains(charText)
                            ) {
                        vendorList.add(wp);
                    }
                }else {
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                        vendorList.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    }


