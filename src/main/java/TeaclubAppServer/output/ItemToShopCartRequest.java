package TeaclubAppServer.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
public class ItemToShopCartRequest {
    @NotBlank
    private final String nameOfItem;
    @NotBlank
    private final int priceOfItem;
    @NotBlank
    private final int numOfItem;
    @NotBlank
    private final Long shopCartId;
}
