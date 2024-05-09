package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class VendorCategoryModel implements Serializable {

    public String getVend_cat_id() {
        return vend_cat_id;
    }

    public void setVend_cat_id(String vend_cat_id) {
        this.vend_cat_id = vend_cat_id;
    }

    public String getVend_cat_name() {
        return vend_cat_name;
    }

    public void setVend_cat_name(String vend_cat_name) {
        this.vend_cat_name = vend_cat_name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    @SerializedName("vend_cat_id")
    private String vend_cat_id;
    @SerializedName("vend_cat_name")
    private String vend_cat_name;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("updated_on")
    private String updated_on;



}

