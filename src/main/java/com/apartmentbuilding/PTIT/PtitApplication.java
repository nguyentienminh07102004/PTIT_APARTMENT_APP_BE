package com.apartmentbuilding.PTIT;

import com.apartmentbuilding.PTIT.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Domains.RoleEntity;
import com.apartmentbuilding.PTIT.Service.Role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableRedisRepositories
@EnableScheduling
@EnableAsync
@EnableJpaAuditing
@RequiredArgsConstructor
public class PtitApplication {
    private final IRoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(PtitApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (!roleService.existsByCode(ConstantConfig.ADMIN_ROLE)) {
                roleService.save(new RoleEntity(ConstantConfig.ADMIN_ROLE, "Quản trị viên"));
            }
            if (!roleService.existsByCode(ConstantConfig.USER_ROLE)) {
                roleService.save(new RoleEntity(ConstantConfig.USER_ROLE, "Người dùng"));
            }
        };
    }

}
