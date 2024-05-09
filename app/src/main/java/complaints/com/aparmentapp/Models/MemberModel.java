package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberModel implements Serializable {
    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
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

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getCustom_customer_no() {
        return custom_customer_no;
    }

    public void setCustom_customer_no(String custom_customer_no) {
        this.custom_customer_no = custom_customer_no;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(String visitorCount) {
        this.visitorCount = visitorCount;
    }

    @SerializedName("cust_id")
    private String cust_id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("ownership")
    private String ownership;
    @SerializedName("mobile_no")
    private String mobile_no;
    @SerializedName("custom_customer_no")
    private String custom_customer_no;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("visitorCount")
    private String visitorCount;
}

