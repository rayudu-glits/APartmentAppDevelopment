package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class ExpenseCategoryModel implements Serializable {
    public String getExp_id() {
        return exp_id;
    }

    public void setExp_id(String exp_id) {
        this.exp_id = exp_id;
    }

    public String getExp_cat_id() {
        return exp_cat_id;
    }

    public void setExp_cat_id(String exp_cat_id) {
        this.exp_cat_id = exp_cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("exp_id")
    private String exp_id;
    @SerializedName("exp_cat_id")
    private String exp_cat_id;
    @SerializedName("name")
    private String name;
    @SerializedName("dateCreated")
    private String dateCreated;



}

