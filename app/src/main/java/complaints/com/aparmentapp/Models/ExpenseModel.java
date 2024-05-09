package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class ExpenseModel implements Serializable {
    public String getExp_inv_id() {
        return exp_inv_id;
    }

    public void setExp_inv_id(String exp_inv_id) {
        this.exp_inv_id = exp_inv_id;
    }

    public String getExp_id() {
        return exp_id;
    }

    public void setExp_id(String exp_id) {
        this.exp_id = exp_id;
    }

    public String getInward_qty() {
        return inward_qty;
    }

    public void setInward_qty(String inward_qty) {
        this.inward_qty = inward_qty;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExp_cat_id() {
        return exp_cat_id;
    }

    public void setExp_cat_id(String exp_cat_id) {
        this.exp_cat_id = exp_cat_id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("exp_inv_id")
    private String exp_inv_id;
    @SerializedName("exp_id")
    private String exp_id;
    @SerializedName("inward_qty")
    private String inward_qty;
    @SerializedName("receipt_no")
    private String receipt_no;
    @SerializedName("receipt_date")
    private String receipt_date;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("exp_cat_id")
    private String exp_cat_id;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("name")
    private String name;



}

