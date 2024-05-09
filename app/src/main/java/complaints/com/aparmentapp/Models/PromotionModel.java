package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class PromotionModel implements Serializable {
    public String getProm_id() {
        return prom_id;
    }

    public void setProm_id(String prom_id) {
        this.prom_id = prom_id;
    }

    public String getProm_cat_id() {
        return prom_cat_id;
    }

    public void setProm_cat_id(String prom_cat_id) {
        this.prom_cat_id = prom_cat_id;
    }

    public String getProm_amount() {
        return prom_amount;
    }

    public void setProm_amount(String prom_amount) {
        this.prom_amount = prom_amount;
    }

    public String getProm_date() {
        return prom_date;
    }

    public void setProm_date(String prom_date) {
        this.prom_date = prom_date;
    }

    public String getProm_end_date() {
        return prom_end_date;
    }

    public void setProm_end_date(String prom_end_date) {
        this.prom_end_date = prom_end_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProm_status() {
        return prom_status;
    }

    public void setProm_status(String prom_status) {
        this.prom_status = prom_status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getProm_cat_name() {
        return prom_cat_name;
    }

    public void setProm_cat_name(String prom_cat_name) {
        this.prom_cat_name = prom_cat_name;
    }

    @SerializedName("prom_id")
    private String prom_id;
    @SerializedName("prom_cat_id")
    private String prom_cat_id;
    @SerializedName("prom_amount")
    private String prom_amount;
    @SerializedName("prom_date")
    private String prom_date;
    @SerializedName("prom_end_date")
    private String prom_end_date;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("prom_status")
    private String prom_status;

    public String getProm_image() {
        return prom_image;
    }

    public void setProm_image(String prom_image) {
        this.prom_image = prom_image;
    }

    @SerializedName("prom_image")
    private String prom_image;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("prom_cat_name")
    private String prom_cat_name;



}

