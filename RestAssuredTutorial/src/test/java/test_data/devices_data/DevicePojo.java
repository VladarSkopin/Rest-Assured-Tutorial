package test_data.devices_data;

import lombok.Data;


@Data // Generates getters, setters, toString, equals, and hashCode
public class DevicePojo {
    private String id;
    private String name;
    private DataPojo data;
}
