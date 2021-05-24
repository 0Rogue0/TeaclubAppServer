package TeaclubAppServer.controllers;

import TeaclubAppServer.models.ResponseCodes;
import TeaclubAppServer.output.RegisterRequest;
import TeaclubAppServer.output.UserAlreadyExistsException;
import TeaclubAppServer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping
    public String addUser(
            @Valid @ModelAttribute("signUpUser") RegisterRequest registerRequest,
            BindingResult result,
            Map<String, Object> model
    ) {
        if (!result.hasErrors()) {
            if (!registerRequest.getPassword().equals(registerRequest.getMatchingPassword())) {
                model.put("notMatched", true);
                return String.valueOf(ResponseCodes.messageHasNotMatched);
            }
            try {
                userService.addNewUser(registerRequest);
            } catch (UserAlreadyExistsException exp) {
                model.put("alreadyExists", true);
                return String.valueOf(ResponseCodes.messageAlreadyExists);
            }
        }
        return String.valueOf(ResponseCodes.messageAllRight);
    }
}