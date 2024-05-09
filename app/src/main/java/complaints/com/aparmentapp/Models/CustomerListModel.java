package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerListModel implements Serializable {

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

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getMobile_no2() {
        return mobile_no2;
    }

    public void setMobile_no2(String mobile_no2) {
        this.mobile_no2 = mobile_no2;
    }

    public String getCust_image() {
        return cust_image;
    }

    public void setCust_image(String cust_image) {
        this.cust_image = cust_image;
    }

    @SerializedName("cust_id")
    private String cust_id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("mobile_no")
    private String mobile_no;
    @SerializedName("mobile_no2")
    private String mobile_no2;
    @SerializedName("cust_image")
    private String cust_image;
}
