package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class VendorModel implements Serializable {
    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVend_cat_id() {
        return vend_cat_id;
    }

    public void setVend_cat_id(String vend_cat_id) {
        this.vend_cat_id = vend_cat_id;
    }

    public String getVend_name() {
        return vend_name;
    }

    public void setVend_name(String vend_name) {
        this.vend_name = vend_name;
    }

    public String getVend_mobile_no() {
        return vend_mobile_no;
    }

    public void setVend_mobile_no(String vend_mobile_no) {
        this.vend_mobile_no = vend_mobile_no;
    }

    public String getVend_address() {
        return vend_address;
    }

    public void setVend_address(String vend_address) {
        this.vend_address = vend_address;
    }

    public String getVend_email() {
        return vend_email;
    }

    public void setVend_email(String vend_email) {
        this.vend_email = vend_email;
    }

    public String getVend_remarks() {
        return vend_remarks;
    }

    public void setVend_remarks(String vend_remarks) {
        this.vend_remarks = vend_remarks;
    }

    public String getVend_image() {
        return vend_image;
    }

    public void setVend_image(String vend_image) {
        this.vend_image = vend_image;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("vendor_id")
    private String vendor_id;
    @SerializedName("vend_cat_id")
    private String vend_cat_id;
    @SerializedName("vend_name")
    private String vend_name;
    @SerializedName("vend_mobile_no")
    private String vend_mobile_no;
    @SerializedName("vend_address")
    private String vend_address;
    @SerializedName("vend_email")
    private String vend_email;
    @SerializedName("vend_remarks")
    private String vend_remarks;
    @SerializedName("vend_image")
    private String vend_image;
    @SerializedName("dateCreated")
    private String dateCreated;

    public String getVend_cat_name() {
        return vend_cat_name;
    }

    public void setVend_cat_name(String vend_cat_name) {
        this.vend_cat_name = vend_cat_name;
    }

    @SerializedName("vend_cat_name")
    private String vend_cat_name;

}

