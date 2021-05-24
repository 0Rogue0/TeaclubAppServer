package TeaclubAppServer.output;

import com.sun.istack.NotNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@ToString
public class ItemResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String nameOfItem;
    @NotBlank
    private int priceOfItem;
    @NotBlank
    private int numOfItem;
}