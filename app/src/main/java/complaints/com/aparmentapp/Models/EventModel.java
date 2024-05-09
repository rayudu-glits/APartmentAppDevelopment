package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class EventModel implements Serializable {
    @SerializedName("event_id")
    private String event_id;
    @SerializedName("ecat_id")
    private String ecat_id;
    @SerializedName("ecat_name")
    private String ecat_name;
    @SerializedName("event_amount")
    private String event_amount;
    @SerializedName("event_date")
    private String event_date;
    @SerializedName("event_end_date")
    private String event_end_date;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("event_status")
    private String event_status;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

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

    public String getEvent_amount() {
        return event_amount;
    }

    public void setEvent_amount(String event_amount) {
        this.event_amount = event_amount;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_end_date() {
        return event_end_date;
    }

    public void setEvent_end_date(String event_end_date) {
        this.event_end_date = event_end_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("dateCreated")
    private String dateCreated;



}

