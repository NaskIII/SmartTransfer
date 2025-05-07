package com.wayon.SmartTransfer.config.data;

import com.wayon.SmartTransfer.entity.user.Profile;
import com.wayon.SmartTransfer.entity.user.User;
import com.wayon.SmartTransfer.entity.user.UserStatus;
import com.wayon.SmartTransfer.repository.IProfileRepository;
import com.wayon.SmartTransfer.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, IProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initUser() {
        return args -> {
            String email = "admin@smart.local";

            if (userRepository.findByEmail(email).isEmpty()) {

                Profile adminProfile = profileRepository.findByName("ADMINISTRATOR")
                        .orElseGet(() -> {
                            Profile p = new Profile();
                            p.setName("ADMINISTRATOR");
                            return profileRepository.save(p);
                        });

                profileRepository.findByName("COMMON")
                        .orElseGet(() -> {
                            Profile p = new Profile();
                            p.setName("COMMON");
                            return profileRepository.save(p);
                        });

                User user = new User(adminProfile, "Admin", "Smart", email, passwordEncoder.encode("admin123"));
                user.setStatus(UserStatus.ACTIVE.name());

                userRepository.save(user);

                System.out.println("Usuário admin criado com sucesso.");
            }
        };
    }
}
