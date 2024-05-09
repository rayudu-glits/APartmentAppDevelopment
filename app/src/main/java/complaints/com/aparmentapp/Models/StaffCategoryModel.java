package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class StaffCategoryModel implements Serializable {
    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmployeerole() {
        return employeerole;
    }

    public void setEmployeerole(String employeerole) {
        this.employeerole = employeerole;
    }

    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("employeerole")
    private String employeerole;



}

