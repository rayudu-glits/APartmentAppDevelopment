package complaints.com.aparmentapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import complaints.com.aparmentapp.Models.ComplaintsListModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class ComplaintsListAdapter extends RecyclerView.Adapter<ComplaintsListAdapter.MyViewHolder> {

    Context context;
    ArrayList<ComplaintsListModel> vendorList;
    String fragment;
    TextView emp_name,emp_mobile,emp_addr1,emp_desig,tv_noinfo,emp_hdesig,emp_haddr1,emp_hmobile,emp_head,tv_Norecords,category_type,emp_cat;
    Spinner category;
    EditText remarks,description_edtv;
    String status,type;
    AlertDialog alertDialogAndroid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_uname,tv_flat,tv_add,in_time,tv_desc,date,tv_mobile,status;
        ImageView edit;
        public MyViewHolder(View view) {
            super(view);
            tv_uname = (TextView) view.findViewById(R.id.tv_name);
            tv_flat = (TextView) view.findViewById(R.id.tv_flat);
            tv_add = (TextView) view.findViewById(R.id.tv_comptype);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
            status = (TextView) view.findViewById(R.id.status);
            date = (TextView) view.findViewById(R.id.date);
            edit = (ImageView) view.findViewById(R.id.edit);

        }
    }
    public ComplaintsListAdapter(Context visitorlistFragment, ArrayList<ComplaintsListModel> tripList) {
        this.vendorList = tripList;
        context = visitorlistFragment;
    }

    @Override
    public ComplaintsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaints_list_item, parent, false);
        return new ComplaintsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ComplaintsListAdapter.MyViewHolder holder, final int position) {
        final ComplaintsListModel tag = vendorList.get(position);
        holder.tv_uname.setText(tag.getFirst_name());
        holder.tv_mobile.setText(tag.getMobile_no());
        holder.tv_flat.setText(tag.getAddr1());
        holder.tv_add.setText(tag.getComplaint());
        holder.tv_desc.setText(tag.getComp_remarks());
        holder.date.setText(tag.getCreated_date());
    if(tag.getComp_status().equals("0")){
        holder.status.setText("Pending");
    }else if(tag.getComp_status().equals("1")){
        holder.status.setText("Processing");
    }else if(tag.getComp_status().equals("2")){
        holder.status.setText("Completed");
    }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ComplaintsListModel tag = vendorList.get(position);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.edit_complaint_dialog, null);
                emp_name=(TextView)mView.findViewById(R.id.emp_name);
                emp_head=(TextView)mView.findViewById(R.id.emp_head);
                emp_hmobile=(TextView)mView.findViewById(R.id.emp_hmobile);
                emp_haddr1=(TextView)mView.findViewById(R.id.emp_haddr1);
                emp_hdesig=(TextView)mView.findViewById(R.id.emp_hdesig);
                emp_mobile=(TextView)mView.findViewById(R.id.emp_mobile);
                emp_addr1=(TextView)mView.findViewById(R.id.emp_addr1);
                emp_desig=(TextView)mView.findViewById(R.id.emp_desig);
                remarks=(EditText) mView.findViewById(R.id.remarks);
                emp_cat=(TextView) mView.findViewById(R.id.emp_cat);
                description_edtv=(EditText) mView.findViewById(R.id.description_edtv);
                category=(Spinner) mView.findViewById(R.id.employee_role);
                category.setSelection(Integer.parseInt(tag.getComp_status()));
                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        status = category.getItemAtPosition(position).toString();

                        if(status.equals("Pending")){
                            type="0";
                        }else if(status.equals("Processing")){
                            type="1";
                        }else if(status.equals("Completed")){
                            type="2";
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                emp_name.setText(tag.getFirst_name());
                emp_mobile.setText(tag.getMobile_no());
                emp_addr1.setText(tag.getAddr1());
                emp_desig.setText(tag.getAddr2());
                remarks.setText((tag.getComp_remarks()));
                emp_cat.setText(tag.getComplaint());
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                Button submit_btn = (Button) mView.findViewById(R.id.submit_btn);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String Name =emp_name.getText().toString();
                        final String remark =remarks.getText().toString();
                        final String Mobile =emp_mobile.getText().toString();
                        final String Email =emp_desig.getText().toString();
                        final String Address =emp_addr1.getText().toString();
                        final String category =type;
                        final String evnt_id=tag.getComplaint_id();
                        /*if(Name.isEmpty()){
                            emp_name.setError("Please Enter First Name");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(remark.isEmpty()){
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
                            AddEvent(Name,Lname,Address,Mobile,Email,category,evnt_id);
                            alertDialogAndroid.dismiss();
                        }*/
                        AddEvent(Name,remark,Address,Mobile,Email,category,evnt_id);
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


    private void AddEvent(final String Name,final String remark,final String Address,final String Mobile,final String Email,final String category,final String evnt_id) {

        final ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(context);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/update_complaint";

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
                                Toast.makeText(context,"Complaint Updated  Successfully",Toast.LENGTH_SHORT).show();

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
                params.put("remarks", remark);
                params.put("complaint_id", evnt_id);
                params.put("status", type);
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
