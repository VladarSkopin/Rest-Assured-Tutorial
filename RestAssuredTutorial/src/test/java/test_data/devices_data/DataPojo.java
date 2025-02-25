package test_data.devices_data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data // Generates getters, setters, toString, equals, and hashCode
public class DataPojo {
    private int year;
    private double price;

    @SerializedName("CPU model")
    private String cpuModel;

    @SerializedName("Hard disk size")
    private String hardDiskSize;
}
