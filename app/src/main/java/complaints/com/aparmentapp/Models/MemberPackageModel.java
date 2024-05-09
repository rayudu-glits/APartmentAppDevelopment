package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberPackageModel implements Serializable {
    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_description() {
        return package_description;
    }

    public void setPackage_description(String package_description) {
        this.package_description = package_description;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPackage_tax1() {
        return package_tax1;
    }

    public void setPackage_tax1(String package_tax1) {
        this.package_tax1 = package_tax1;
    }

    public String getPackage_tax2() {
        return package_tax2;
    }

    public void setPackage_tax2(String package_tax2) {
        this.package_tax2 = package_tax2;
    }

    public String getPackage_tax3() {
        return package_tax3;
    }

    public void setPackage_tax3(String package_tax3) {
        this.package_tax3 = package_tax3;
    }

    public String getPackage_validity() {
        return package_validity;
    }

    public void setPackage_validity(String package_validity) {
        this.package_validity = package_validity;
    }

    public String getPackage_discount() {
        return package_discount;
    }

    public void setPackage_discount(String package_discount) {
        this.package_discount = package_discount;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getIsbase() {
        return isbase;
    }

    public void setIsbase(String isbase) {
        this.isbase = isbase;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("package_id")
    private String package_id;
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("package_description")
    private String package_description;
    @SerializedName("package_price")
    private String package_price;
    @SerializedName("package_tax1")
    private String package_tax1;
    @SerializedName("package_tax2")
    private String package_tax2;
    @SerializedName("package_tax3")
    private String package_tax3;
    @SerializedName("package_validity")
    private String package_validity;
    @SerializedName("package_discount")
    private String package_discount;
    @SerializedName("isdefault")
    private String isdefault;
    @SerializedName("isbase")
    private String isbase;
    @SerializedName("service_tax")
    private String service_tax;
    @SerializedName("dateCreated")
    private String dateCreated;

}
