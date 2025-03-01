package test_data.gadgets;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

@Data
public class GadgetDataPojo {
    @SerializedName("color")
    private String color;

    @SerializedName("price")
    private Double price;  // double -> Double
}
