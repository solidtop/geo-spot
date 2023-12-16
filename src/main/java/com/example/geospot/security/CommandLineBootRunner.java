package com.example.geospot.security;

import com.example.geospot.category.Category;
import com.example.geospot.category.CategoryRepository;
import com.example.geospot.category.CategoryService;
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
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Component
public class CommandLineBootRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CommandLineBootRunner(UserRepository userRepository, RoleRepository roleRepository,
                                 CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = createRole("ROLE_ADMIN");
        Role userRole = createRole("ROLE_USER");

        createUser("admin", Set.of(adminRole, userRole));
        createUser("user", Set.of(userRole));

        createCategories();
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

    @Transactional
    public void createCategories() {
        Category favourites = new Category("Favourites", "❤️", "My favourite places");
        favourites.setId(0L);
        if (categoryRepository.findByName(favourites.getName()).isEmpty()) {
            categoryRepository.save(favourites);
        }
    }
}
