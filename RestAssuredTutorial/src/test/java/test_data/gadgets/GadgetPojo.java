package test_data.gadgets;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

@Data
public class GadgetPojo {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("data")
    private GadgetDataPojo data;
}
