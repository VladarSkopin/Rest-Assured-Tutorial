package test_data.devices_data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class DataPojo {
    private Integer year;
    private Double price;

    @SerializedName("CPU model")
    private String cpuModel;

    @SerializedName("Hard disk size")
    private String hardDiskSize;
}
