package TeaclubAppServer.services;

import TeaclubAppServer.models.ShopCart;
import TeaclubAppServer.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import TeaclubAppServer.output.RegisterRequest;
import TeaclubAppServer.output.UserAlreadyExistsException;
import TeaclubAppServer.models.User;
import TeaclubAppServer.models.UserRealize;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addNewUser(RegisterRequest request) throws UserAlreadyExistsException {
        String userName = request.getUserName();
        if (userExists(userName)) {
            throw new UserAlreadyExistsException(userName);
        }

        ShopCart shopCart = new ShopCart();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        shopCart.setUser(user);
        //Проверить на правильность, тк не уверен в корректности реализации связи м/ж корзиной и пользователем
        user.setShopCart(shopCart);
        userRepository.save(user);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public boolean userExists(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found: " + userName)
                );
        return new UserRealize(user);
    }
}