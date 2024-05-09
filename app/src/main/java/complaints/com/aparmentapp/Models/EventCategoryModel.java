package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class EventCategoryModel implements Serializable {
    public String getEcat_id() {
        return ecat_id;
    }

    public void setEcat_id(String ecat_id) {
        this.ecat_id = ecat_id;
    }

    public String getEcat_name() {
        return ecat_name;
    }

    public void setEcat_name(String ecat_name) {
        this.ecat_name = ecat_name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("ecat_id")
    private String ecat_id;
    @SerializedName("ecat_name")
    private String ecat_name;
    @SerializedName("dateCreated")
    private String dateCreated;



}

