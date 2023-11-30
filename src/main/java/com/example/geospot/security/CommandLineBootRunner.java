package com.example.geospot.security;

import com.example.geospot.role.Role;
import com.example.geospot.role.RoleRepository;
import com.example.geospot.user.UserEntity;
import com.example.geospot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
public class CommandLineBootRunner implements CommandLineRunner {
    UserRepository userRepository;
    RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CommandLineBootRunner(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = createRole("ROLE_ADMIN");
        Role userRole = createRole("ROLE_USER");

        createUser("admin", Set.of(adminRole, userRole));
        createUser("user", Set.of(userRole));
    }

    @Transactional
    public Role createRole(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()) {
            Role role = new Role(name);
            roleRepository.save(role);
            return role;
        }

        return roleOptional.get();
    }

    @Transactional
    public void createUser(String name, Set<Role> roles) {
        if (userRepository.findByUsername(name).isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername(name);
            admin.setPassword(passwordEncoder.encode(name));
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
