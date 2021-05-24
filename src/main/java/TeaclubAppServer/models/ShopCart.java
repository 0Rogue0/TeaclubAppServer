package TeaclubAppServer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shopCart_table")
public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //////////////////////////Не трогать, связь с товаром
    @OneToMany(mappedBy = "shopCart", fetch = FetchType.LAZY) //Аналог ориентации на id
    private List<Item> items;
    //При создании пользователя параллельно создаю корзину
    //////////////////////////Не трогать, связь с пользователем
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    //////////////////////////

    /*@OneToOne
    @JoinColumn(name = "shopCart_owner", referencedColumnName = "userName", nullable = false)
    private User shopCart_owner;*/
}