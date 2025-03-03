package test_data.petstore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * This POJO is designed to construct the following Request body:
 *
 *"category": {
 *  "id": 0,
 *  "name": "string"
 * }
 *
 */


@Data
public class CategoryPojo {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;
}
