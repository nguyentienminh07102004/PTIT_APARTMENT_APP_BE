package com.apartmentbuilding.PTIT;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import com.apartmentbuilding.PTIT.Model.Entity.RoleEntity;
import com.apartmentbuilding.PTIT.Service.Building.IBuildingService;
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

import java.sql.Date;

@SpringBootApplication
@EnableJpaRepositories
@EnableRedisRepositories
@EnableScheduling
@EnableAsync
@EnableJpaAuditing
@RequiredArgsConstructor
public class PtitApplication {
    private final IRoleService roleService;
    private final IBuildingService buildingService;

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
            if (this.buildingService.isEmpty()) {
                for (int i = 0; i < 100; i++) {
                    this.buildingService.save(BuildingEntity.builder()
                            .address("Hà Nội")
                            .name("B00%d".formatted(i + 1))
                            .buildAt(new Date(System.currentTimeMillis()))
                            .description("Rất đẹp")
                            .unitNumber(2000000.0D)
                            .build());
                }
            }
        };
    }

}
