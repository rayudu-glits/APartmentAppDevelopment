package complaints.com.aparmentapp.Sharedpreferences;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import complaints.com.aparmentapp.InstantApprovalSecurity;
import complaints.com.aparmentapp.R;

/**
 * Created by Hari krishna on 10/14/2017.
 */

public class BaseUrlClass {
   // private static final String URL_BASE_DEBUG = "https://digitalrupay.com/radius/demo/webservices/";//demo
     //private static final String URL_BASE_DEBUG = "http://103.59.155.5/beta/webservices/"; //zoom cable
      private static final String URL_BASE_DEBUG = "http://aptdemo.digitalrupay.com/webservices/"; //madhira cable

    public static String getBaseUrl() {
        //change if debug' release or whichever flavor
        return URL_BASE_DEBUG;
    }
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog progressdialog = new ProgressDialog(mContext);
        try {
            progressdialog.show();
        } catch (Exception e) {
        }
        progressdialog.setCancelable(false);
        progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressdialog.setContentView(R.layout.progressdialog);
        // dialog.setMessage(Message);
        return progressdialog;
    }

}