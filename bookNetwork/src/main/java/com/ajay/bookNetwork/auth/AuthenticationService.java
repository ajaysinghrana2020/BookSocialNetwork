package com.ajay.bookNetwork.auth;

import com.ajay.bookNetwork.email.EmailService;
import com.ajay.bookNetwork.email.EmailTemplateName;
import com.ajay.bookNetwork.role.RoleRepository;

import com.ajay.bookNetwork.user.Token;
import com.ajay.bookNetwork.user.TokenRepository;
import com.ajay.bookNetwork.user.User;
import com.ajay.bookNetwork.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.mailing.frontend.activation-url}")
    String activationUrl;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
//    private final EmailTemplatename emailTemplatename;


    public void register(RegistrationRequest request) throws MessagingException {
        var userRole=  roleRepository.findByName("USER")
                .orElseThrow(()->new IllegalStateException("ROLL USER was Not initialized"));
        var user = User.builder().firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken= generateAndSaveActivationToken(user);
        //here we are going to send email
        System.out.println("email sending Started");
        System.out.println("EmailTemplateName.ACTIVATE_ACCOUNT "+EmailTemplateName.ACTIVATE_ACCOUNT);
        emailService.sendEmail(user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation");
        System.out.println("email sending finish "+activationUrl);

    }

    private String generateAndSaveActivationToken(User user){
        String generateToken= generateActivationCode(7);
        var token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(20))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length){
        String chars="0123456789";
        StringBuilder codeBuilder =new StringBuilder();
        SecureRandom secureRandom =new SecureRandom();
        for(int i=0;i<length;i++){
            int randomIndex=secureRandom.nextInt(chars.length());// gives us 0 to 9 value
            codeBuilder.append(chars.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
