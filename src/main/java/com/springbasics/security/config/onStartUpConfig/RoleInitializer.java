package com.springbasics.security.config.onStartUpConfig;

import com.springbasics.security.model.Authority;
import com.springbasics.security.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoleInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    public RoleInitializer(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRolesIfNotExists();
    }

    private void createRolesIfNotExists() {
        if (!roleExists("ADMIN")) {
            log.info("Initializing ADMIN Role");
            createRoles("ADMIN","ROLE_ADMIN");

        }
        if (!roleExists("USER")) {
            log.info("Initializing USER Role");
            createRoles("USER","ROLE_USER");
        }
    }

    private boolean roleExists(String roleName) {
        return authorityRepository.existsByRoleCode(roleName);
    }
    private void createRoles(String roleName,String roleDescription){
        Authority authority=new Authority(roleName,roleDescription);
        authorityRepository.save(authority);
    }
}
