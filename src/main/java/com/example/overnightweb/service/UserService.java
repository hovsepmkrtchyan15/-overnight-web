package com.example.overnightweb.service;


import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import com.example.overnightweb.exception.DuplicateResourceException;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;


    public void save(User user) throws DuplicateResourceException, MessagingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists!!");

        }
        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        userRepository.save(user);
        mailService.sendHtmlEmail(user.getEmail(), "Please verify your email",
                "Hi " + user.getName() + "\n" +
                        "Please verify your account clicking on this link <a href=\"http://localhost:8080/user/verify?email="
                        + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");

    }
    public void verifyUser(String email, String token) throws Exception {
        Optional<User> userOptional = userRepository.findByEmailAndVerifyToken(email, token);
        if (userOptional.isEmpty()) {
            throw new Exception("User does not exists with and token");
        }
        User user = userOptional.get();
        if (user.isEnabled()) {
            throw new Exception("User already enabled");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleUser.SELLER);
        user.setStatus(StatusSeller.IN_PENDING);
        user.setEnabled(true);
        user.setVerifyToken(null);
        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

}
