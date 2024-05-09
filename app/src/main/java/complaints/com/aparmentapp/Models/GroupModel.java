package complaints.com.aparmentapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupModel implements Serializable {


    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @SerializedName("category")
    private String category;

}
