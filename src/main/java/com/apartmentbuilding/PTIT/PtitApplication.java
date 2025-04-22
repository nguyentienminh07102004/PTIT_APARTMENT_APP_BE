package com.apartmentbuilding.PTIT;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import com.apartmentbuilding.PTIT.Model.Entity.RoleEntity;
import com.apartmentbuilding.PTIT.Service.Building.IBuildingService;
import com.apartmentbuilding.PTIT.Service.Role.IRoleService;
import com.apartmentbuilding.PTIT.Service.User.IUserService;
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
    private final IUserService userService;

    public static void main(String[] args) {
        SpringApplication.run(PtitApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (!roleService.existsByCode(ConstantConfig.ADMIN_ROLE)) {
                this.roleService.save(new RoleEntity(ConstantConfig.ADMIN_ROLE, "Quản trị viên"));
            }
            if (!roleService.existsByCode(ConstantConfig.USER_ROLE)) {
                this.roleService.save(new RoleEntity(ConstantConfig.USER_ROLE, "Người dùng"));
            }
            if (!this.roleService.existsByCode(ConstantConfig.ACCOUNTING_ROLE)) {
                this.roleService.save(new RoleEntity(ConstantConfig.ACCOUNTING_ROLE, "Kế toán"));
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
            if (!this.userService.existEmail("thaiphuca1pdl@gmail.com")) {
                this.userService.register(UserRegister.builder()
                        .email("thaiphuca1pdl@gmail.com")
                        .dateOfBirth(Date.valueOf("2004-05-25"))
                        .fullName("Thái Hữu Phúc")
                        .identityNumber("01234587954655")
                        .password("123456789")
                        .confirmPassword("123456789")
                        .build());
            }

            if (!this.userService.existEmail("congduan2554@gmail.com")) {
                this.userService.register(UserRegister.builder()
                        .email("congduan2554@gmail.com")
                        .dateOfBirth(Date.valueOf("2004-05-25"))
                        .fullName("Nguyễn Công Duẩn")
                        .identityNumber("0123458795465885")
                        .password("123456789")
                        .confirmPassword("123456789")
                        .roleCode(ConstantConfig.ADMIN_ROLE)
                        .build());
            }
        };
    }

}
