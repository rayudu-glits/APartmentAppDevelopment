package complaints.com.aparmentapp.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import complaints.com.aparmentapp.Models.EmployeeModel;

/**
 * Created by Hari krishna on 6/27/2017.
 */


public class SaveAppData {
    private static SaveAppData sessionData;
    public static String DIGITAL_APP = "EToll";
    public static String SHPREF_KEY_EMP_LOGIN="EMP_LOGIN";
    private SaveAppData() {}
    public static SaveAppData getSessionDataInstance() {
        if (sessionData == null) {
            sessionData = new SaveAppData();
        }
        return sessionData;
    }

    public static void saveEmpLoginData(EmployeeModel emplogin) {
        SharedPreferences.Editor e = AppController.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (emplogin != null) {
            Gson gson = new Gson();
            String json = gson.toJson(emplogin);
            e.putString(SHPREF_KEY_EMP_LOGIN, json);
        } else {
            e.putString(SHPREF_KEY_EMP_LOGIN, null);
        }
        e.commit();
    }
    public static EmployeeModel getLoginData() {
        Gson gson = new Gson();
        String json = AppController.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_EMP_LOGIN, null);
        EmployeeModel operatorLogin = gson.fromJson(json, EmployeeModel.class);
        return operatorLogin;
    }
}
