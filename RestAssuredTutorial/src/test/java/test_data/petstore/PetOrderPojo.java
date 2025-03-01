package test_data.petstore;


import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class PetOrderPojo {
    @SerializedName("id")
    private Integer id;

    @SerializedName("petId")
    private Integer petId;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("shipDate")
    private String shipDate;

    @SerializedName("status")
    private String status;

    @SerializedName("complete")
    private Boolean complete;

}
