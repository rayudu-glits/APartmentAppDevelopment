package complaints.com.aparmentapp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import complaints.com.aparmentapp.Models.PromotionCategoryModel;
import complaints.com.aparmentapp.Models.PromotionModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.AppController;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;
import complaints.com.aparmentapp.Sharedpreferences.MarshmallowPermissions;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.MyViewHolder> {

    Context context;
    Context mContext;
    ArrayList<PromotionModel> vendorList;
    String fragment;
    AlertDialog alertDialogAndroid;
    Spinner category;
    EditText ed_amount,ed_stdate,ed_date,remarks;
    public static ImageView promo_image;
    Button cancel_btn,submit_btn;
    private int mHour, mMinute, mYear, mMonth, mDay;
    ArrayList<PromotionCategoryModel> packagelist = new ArrayList<>();
    ArrayAdapter<String> msoadapter;
    public String getpersonname = null, getpersonid = null;

    public static String imgname1 = "", imgname2 = "", imgname3 = "", imgname4 = "", imgidname;
    public static String encodedimage1, encodedimage2, encodedimage3, encodedimage4;
    public static Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    private int CAMERA_Select = 215;
    int RESULT_CODE = 110;
    MarshmallowPermissions marshMallowPermission=new MarshmallowPermissions((Activity) context);
    int count = 0;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "HOMES";
    Button btn_uploadfiles;
    private LocationManager locationManager;
    private int PERMISSION_CAMERA = 1002;
    private String provider;
    RadioButton rbtn_checkgps,rbtn_nocheckgps;
    public static Uri mImage_of_camera;
    ImageView promo_ad;
    PromotionsAdapter promotionsFragment=this;
    int requestCode,resultCode;
    Intent data;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_amount,edit,strt_date,date,tv_remark;
        ImageView promo_ad,promo_dummy;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            strt_date = (TextView) view.findViewById(R.id.strt_date);
            date = (TextView) view.findViewById(R.id.date);
            edit = (TextView) view.findViewById(R.id.edit);
            promo_ad = (ImageView) view.findViewById(R.id.promo_ad);
            promo_dummy = (ImageView) view.findViewById(R.id.promo_dummy);


        }
    }
    public PromotionsAdapter(Context visitorlistFragment, ArrayList<PromotionModel> tripList) {
        this.vendorList = tripList;
        context = visitorlistFragment;

    }

    @Override
    public PromotionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_lis_item, parent, false);
        mContext = parent.getContext();
        //onActivityResult(requestCode, resultCode,data);
        return new PromotionsAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final PromotionsAdapter.MyViewHolder holder, final int position) {
        final PromotionModel tag = vendorList.get(position);
        final String url= BaseUrlClass.getBaseUrl()+"uploads/";
        holder.tv_name.setText(tag.getProm_cat_name());
        holder.tv_amount.setText("Amount : " + tag.getProm_amount());
        holder.strt_date.setText("From : " + tag.getProm_date());
        holder.date.setText("To : " + tag.getProm_end_date());
        holder.tv_remark.setText("Remarks : " + tag.getRemarks());
        if(tag.getProm_image()!=null ){
            if(!tag.getProm_image().equals("")){
                Glide.with(context).load(url+tag.getProm_image())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.promo_ad);
                holder.promo_ad.setVisibility(View.VISIBLE);
                holder.promo_dummy.setVisibility(View.GONE);
            }else{
                holder.promo_ad.setVisibility(View.GONE);
                holder.promo_dummy.setVisibility(View.VISIBLE);
            }

        }else{
            holder.promo_ad.setVisibility(View.GONE);
            holder.promo_dummy.setVisibility(View.VISIBLE);

        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PromotionModel tag = vendorList.get(position);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.edit_promotion_dialog, null);
                Getpackages(tag);
                ed_amount = (EditText) mView.findViewById(R.id.ed_amount);
                ed_stdate = (EditText) mView.findViewById(R.id.ed_stdate);
                ed_date = (EditText) mView.findViewById(R.id.ed_date);
                category = (Spinner) mView.findViewById(R.id.category);
                remarks=(EditText)mView.findViewById(R.id.remarks);
                ed_amount.setText(tag.getProm_amount());
                ed_stdate.setText(tag.getProm_date());
                ed_date.setText(tag.getProm_date());
                remarks.setText(tag.getRemarks());
                promo_image=(ImageView) mView.findViewById(R.id.promo_image);
                promo_ad=(ImageView) mView.findViewById(R.id.promo_ad);
                if(tag.getProm_image()!=null ){
                    if(!tag.getProm_image().equals("")){
                        Glide.with(context).load(url+tag.getProm_image())
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.promo_ad);
                        promo_ad.setVisibility(View.VISIBLE);
                        promo_image.setVisibility(View.GONE);
                    }else{
                        promo_ad.setVisibility(View.GONE);
                        promo_image.setVisibility(View.VISIBLE);
                    }

                }else{
                    promo_ad.setVisibility(View.GONE);
                    promo_image.setVisibility(View.VISIBLE);

                }
                promo_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RESULT_CODE = 112;
                        CAMERA_Select = 216;
                        mImage_of_camera = null;
                        checkCameraPermssions();
                    }
                });
                promo_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RESULT_CODE = 112;
                        CAMERA_Select = 216;
                        mImage_of_camera = null;
                        checkCameraPermssions();
                    }
                });
                ed_stdate.setOnClickListener(new View.OnClickListener() {

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

                                        ed_stdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                ed_date.setOnClickListener(new View.OnClickListener() {

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

                                        ed_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

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
                        final String Amount =ed_amount.getText().toString();
                        final String startdate =ed_stdate.getText().toString();
                        final String enddate =ed_date.getText().toString();
                        final String reason =remarks.getText().toString();
                        final String image=imgname2;
                        final String category =getpersonid;
                        final String evnt_id=tag.getProm_id();
                        if(Amount.isEmpty()){
                            ed_amount.setError("Please Enter Amount");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter name",Toast.LENGTH_SHORT).show();
                        }if(startdate.isEmpty()){
                            ed_stdate.setError("Please Enter Date");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                        }if(enddate.isEmpty()){
                            ed_date.setError("Please Enter Date");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter location",Toast.LENGTH_SHORT).show();
                        }if(reason.isEmpty()){
                            remarks.setError("Please Enter Remarks");
                            return;
                            //Toast.makeText(getActivity(),"Please Enter time",Toast.LENGTH_SHORT).show();
                        }else {
                            AddEvent(Amount,startdate,reason,category,evnt_id,enddate,image);
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

    private void checkCameraPermssions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {

                selectImage();

            } else {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
            }
        } else {
            selectImage();
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Device",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = null;
                    try {
                        file = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (file.exists()) {
                        file.delete();
                    } else {
                        file.mkdir();
                    }
                    mImage_of_camera = FileProvider.getUriForFile(mContext,
                            mContext.getResources().getString(R.string.file_provider_authority),
                            file);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImage_of_camera);

                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        ((Activity) mContext).startActivityForResult(takePictureIntent, RESULT_CODE);

                    }
                } else if (items[item].equals("Choose from Device")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "Radius1";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }
    public void captureImage() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            ((Activity) context).startActivityForResult(intent, RESULT_CODE);
        } else {
            Toast.makeText(context, "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);*/
        //startActivityForResult(Intent.createChooser(intent, "Select File"),CAMERA_Select);
        ((Activity) context).startActivityForResult(intent, CAMERA_Select);

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    public static Bitmap getBitmapFromUri(Context context, Uri uri)
            throws FileNotFoundException {
        final InputStream imageStream = context.getContentResolver()
                .openInputStream(uri);
        try {

            return BitmapFactory.decodeStream(imageStream);

        } finally {
            //  Closeables.closeQuietly(imageStream);
        }

    }



    private void Getpackages(final PromotionModel tag) {
        try {
           /* final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(getActivity());
            progressdialog.show();
            progressdialog.setCancelable(false);*/

            final String url = BaseUrlClass.getBaseUrl() + "employee/promotion_cat";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                packagelist = new ArrayList<>();
                                JSONObject customersObj = new JSONObject(response);
                                PromotionCategoryModel stbMakeModel = null;
                                Iterator<String> keys = customersObj.keys();
                                String message = customersObj.getString("msg");
                                if (message.equalsIgnoreCase("success")) {
                                    PromotionCategoryModel model = new PromotionCategoryModel();
                                    model.setProm_cat_name("Select Category");
                                    model.setProm_cat_id("");
                                    packagelist.add(model);
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (!key.equalsIgnoreCase("msg")) {
                                            stbMakeModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                                    new TypeToken<PromotionCategoryModel>() {
                                                    }.getType());
                                            packagelist.add(stbMakeModel);
                                        }
                                    }
                                    String[] msoarray = new String[packagelist.size()];
                                    for (int i = 0; i < packagelist.size(); i++) {
                                        msoarray[i] = packagelist.get(i).getProm_cat_name();
                                    }
                                    final List<String> msoitemsList = new ArrayList<>(Arrays.asList(msoarray));

                                    msoadapter = new ArrayAdapter<String>(context, R.layout.spinner_item, msoitemsList);

                                    msoadapter.setDropDownViewResource(R.layout.spinner_item);
                                    category.setAdapter(msoadapter);
                                    String selection = tag.getProm_cat_name();
                                    int pos = -1;

                                    for (int i = 0; i < packagelist.size(); i++) {
                                        if (packagelist.get(i).getProm_cat_name().equals(selection)) {
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
                                            getpersonname = packagelist.get(position).getProm_cat_name();
                                            getpersonid = packagelist.get(position).getProm_cat_id();

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
    private void AddEvent(final String Amount,final String startdate,final String reason,final String category,final String evnt_id,final String enddate,final String image) {

        final ProgressDialog progressdialog=BaseUrlClass.createProgressDialog(context);
        progressdialog.show();
        progressdialog.setCancelable(false);

        final String URL = BaseUrlClass.getBaseUrl()+"employee/update_promotion";

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
                                Toast.makeText(context,"Promotion updated Successfully",Toast.LENGTH_SHORT).show();
                                if(!imgname2.equals("")){
                                    VolleyPost(context, encodedimage2, imgname2, 1);
                                }
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
                params.put("promotion_id", evnt_id);
                params.put("image", image);
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
    private void VolleyPost(Context con, final String encodedstr,final String ImageName, final int request) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please Wait, uploading details!");
        pd.setCancelable(false);
        pd.show();
        String url = BaseUrlClass.getBaseUrl()+"uploads/upload.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        pd.cancel();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.cancel();
                        String error1 = error.toString();
                        // error
                        // Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("base64", encodedstr);
                params.put("ImageName", ImageName);

                return params;
            }
        };
        postRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        AppController.getInstance().addToRequestQueue(postRequest);
    }


    }


