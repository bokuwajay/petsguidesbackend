package live.codeland.petsguidesbackend.auth;

import jakarta.validation.Valid;
import live.codeland.petsguidesbackend.dto.ApiResponseDto;
import live.codeland.petsguidesbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponseDto<String>> register(@Valid @RequestBody User user) {
        String jwtToken = authService.register(user);
        ApiResponseDto<String> response = new ApiResponseDto<>(HttpStatus.OK, 200, jwtToken, "Successfully registered!",
                LocalDateTime.now());
        return response.toClient();
    }

    @PostMapping("/authentication")
    public ResponseEntity<ApiResponseDto<String>> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        String jwtToken = authService.authenticate(authRequest);
        ApiResponseDto<String> response = new ApiResponseDto<>(HttpStatus.OK, 200, jwtToken, "Successfully Authenticated!",
                LocalDateTime.now());
        return response.toClient();
    }

}
