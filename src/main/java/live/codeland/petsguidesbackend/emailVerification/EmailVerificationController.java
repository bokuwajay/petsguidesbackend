package live.codeland.petsguidesbackend.emailVerification;

import live.codeland.petsguidesbackend.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/verification/email")
@Validated
public class EmailVerificationController {

    private EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/code-delivery")
    public ResponseEntity<ApiResponseDto<Object>> emailVerificationCodeDelivery(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);
        String jwtToken = emailVerificationService.emailVerificationCodeDelivery(jwt);
        ApiResponseDto<Object> response = new ApiResponseDto<>(HttpStatus.OK, 200, jwtToken,
                "Successfully sent email verification code!", LocalDateTime.now());
        return response.toClient();
    }

    @PatchMapping("/code-confirmation")
    public ResponseEntity<ApiResponseDto<Object>> emailVerificationCodeConfirmation(HttpServletRequest request) {
        final String userInputCode = request.getParameter("userInputCode").toLowerCase();
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);
        String jwtToken = emailVerificationService.emailVerificationCodeConfirmation(jwt, userInputCode);
        ApiResponseDto<Object> response = new ApiResponseDto<>(HttpStatus.OK, 200, jwtToken,
                "Successfully validate email verification code!", LocalDateTime.now());
        return response.toClient();
    }

}
