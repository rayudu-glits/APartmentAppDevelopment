package complaints.com.aparmentapp.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChargesModel implements Serializable {
    @SerializedName("chargeId")
    private String chargeId;

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getChargeName() {
        return ChargeName;
    }

    public void setChargeName(String chargeName) {
        ChargeName = chargeName;
    }

    @SerializedName("ChargeName")
    private String ChargeName;
}
