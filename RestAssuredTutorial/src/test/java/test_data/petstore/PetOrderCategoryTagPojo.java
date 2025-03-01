package test_data.petstore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;


/**
 * This POJO is designed to construct the following Request body:
 *
 *
 * {
 *   "id": 0,
 *   "category": {
 *     "id": 0,
 *     "name": "string"
 *   },
 *   "name": "doggie",
 *   "photoUrls": [
 *     "string"
 *   ],
 *   "tags": [
 *     {
 *       "id": 0,
 *       "name": "string"
 *     }
 *   ],
 *   "status": "available"
 * }
 */

@Data
public class PetOrderCategoryTagPojo {

    @SerializedName("id")
    private Integer id;

    @SerializedName("category")
    private CategoryPojo category;

    @SerializedName("name")
    private String name;

    @SerializedName("photoUrls")
    private List<String> photoUrls;

    @SerializedName("tags")
    private List<TagPojo> tags;

    @SerializedName("status")
    private String status;
}
