package test_data.devices_data;

import lombok.Data;

@Data // Generates getters, setters, toString, equals, and hashCode
public class DataPojo {
    private int year;
    private double price;
    private String cpuModel;
    private String hardDiskSize;
}
