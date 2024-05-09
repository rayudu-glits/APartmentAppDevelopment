package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class PromotionCategoryModel implements Serializable {

    public String getProm_cat_id() {
        return prom_cat_id;
    }

    public void setProm_cat_id(String prom_cat_id) {
        this.prom_cat_id = prom_cat_id;
    }

    public String getProm_cat_name() {
        return prom_cat_name;
    }

    public void setProm_cat_name(String prom_cat_name) {
        this.prom_cat_name = prom_cat_name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("prom_cat_id")
    private String prom_cat_id;
    @SerializedName("prom_cat_name")
    private String prom_cat_name;
    @SerializedName("dateCreated")
    private String dateCreated;



}

