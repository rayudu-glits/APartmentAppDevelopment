package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComplaintsListModel implements Serializable {
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("complaint_id")
    private String complaint_id;
    @SerializedName("comp_ticketno")
    private String comp_ticketno;
    @SerializedName("complaint")
    private String complaint;
    @SerializedName("comp_status")
    private String comp_status;
    @SerializedName("created_date")
    private String created_date;
    @SerializedName("last_edited_by")
    private String last_edited_by;
    @SerializedName("edited_on")
    private String edited_on;
    @SerializedName("comp_remarks")
    private String comp_remarks;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("addr2")
    private String addr2;
    @SerializedName("mobile_no")
    private String mobile_no;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComp_ticketno() {
        return comp_ticketno;
    }

    public void setComp_ticketno(String comp_ticketno) {
        this.comp_ticketno = comp_ticketno;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComp_status() {
        return comp_status;
    }

    public void setComp_status(String comp_status) {
        this.comp_status = comp_status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLast_edited_by() {
        return last_edited_by;
    }

    public void setLast_edited_by(String last_edited_by) {
        this.last_edited_by = last_edited_by;
    }

    public String getEdited_on() {
        return edited_on;
    }

    public void setEdited_on(String edited_on) {
        this.edited_on = edited_on;
    }

    public String getComp_remarks() {
        return comp_remarks;
    }

    public void setComp_remarks(String comp_remarks) {
        this.comp_remarks = comp_remarks;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    @SerializedName("ownership")
    private String ownership;
}
