package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberGroupModel implements Serializable {


    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SerializedName("group_id")
    private String group_id;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("is_parent")
    private String is_parent;
    @SerializedName("is_default")
    private String is_default;
    @SerializedName("package_id")
    private String package_id;
    @SerializedName("status")
    private String status;
    @SerializedName("dateCreated")
    private String dateCreated;

}
