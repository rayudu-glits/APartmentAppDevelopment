package complaints.com.aparmentapp.Fragments;

/**
 * Created by Hari krishna on 4/20/2018.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;


public class CollectionsFragment extends Fragment{
    TextView eventcollection,tv_promotion,tv_totoutstanding,tv_totcollection,tv_online,tv_cheque,cash;
    public CollectionsFragment() {
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
        View mview=inflater.inflate(R.layout.fragment_dashboard, container, false);
        eventcollection=(TextView)mview.findViewById(R.id.eventcollection);
        tv_promotion=(TextView)mview.findViewById(R.id.tv_promotion);
        tv_totoutstanding=(TextView)mview.findViewById(R.id.tv_totoutstanding);
        tv_totcollection=(TextView)mview.findViewById(R.id.tv_totcollection);
        tv_online=(TextView)mview.findViewById(R.id.tv_online);
        tv_cheque=(TextView)mview.findViewById(R.id.tv_cheque);
        cash=(TextView)mview.findViewById(R.id.cash);
        GetCollections();
        return mview;

    }
    private void GetCollections() {
        final ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(getActivity());
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/collection_dashboard";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if(jsonObject.getString("TodayCashCollection")==null||jsonObject.getString("TodayCashCollection").equals("null")) {
                                cash.setText("Cash :Rs. " + "0");
                            }else{
                                cash.setText("Cash :Rs. " + " " + jsonObject.getString("TodayCashCollection"));
                            }
                            if(jsonObject.getString("TodayChequeCollection")==null||jsonObject.getString("TodayChequeCollection").equals("null")) {
                                tv_cheque.setText("Cheque :Rs. " + "0");
                            }else{
                                tv_cheque.setText("Cheque :Rs. " + " " + jsonObject.getString("TodayChequeCollection"));
                            }
                            if(jsonObject.getString("TodayOnlineCollection")==null||jsonObject.getString("TodayOnlineCollection").equals("null")) {
                                tv_online.setText("Online :Rs. " + "0");
                            }else{
                                tv_online.setText("Online :Rs. " + "  " + jsonObject.getString("TodayOnlineCollection"));
                            }if(jsonObject.getString("MonthCollection")==null||jsonObject.getString("MonthCollection").equals("null")) {
                                tv_totcollection.setText("Rs." + "0");
                            }else{
                                tv_totcollection.setText("Rs." + "  " + jsonObject.getString("MonthCollection"));
                            }if(jsonObject.getString("TotalOutstanding")==null||jsonObject.getString("TotalOutstanding").equals("null")) {
                                tv_totoutstanding.setText("Rs." + "0");
                            }else{
                                tv_totoutstanding.setText("Rs." + "  " + jsonObject.getString("TotalOutstanding"));
                            }if(jsonObject.getString("PromotionCollection")==null||jsonObject.getString("PromotionCollection").equals("null")) {
                                tv_promotion.setText("Rs." + "0");
                            }else{
                                tv_promotion.setText("Rs." + "  " + jsonObject.getString("PromotionCollection"));
                            }if(jsonObject.getString("EventCollection")==null||jsonObject.getString("EventCollection").equals("null")) {
                                eventcollection.setText("Rs." + "0");
                            }else{
                                eventcollection.setText("Rs." + "  " + jsonObject.getString("EventCollection"));
                            }
                            progressdialog.dismiss();

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
