package com.apartmentbuilding.PTIT.Service.CodeForgotPassword;

import com.apartmentbuilding.PTIT.Configuration.Redis.BaseRedisRepositoryImpl;
import com.apartmentbuilding.PTIT.Model.RedisHash.CodeForgotPassword;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CodeForgotPasswordServiceImpl extends BaseRedisRepositoryImpl implements ICodeForgotPasswordService {
    @Value(value = "${code_forgot_password_duration}")
    private Long codeForgotPasswordDuration;
    public CodeForgotPasswordServiceImpl(RedisTemplate<String, Object> redisTemplate, HashOperations<String, String, Object> hashOperations) {
        super(redisTemplate, hashOperations);
    }

    @Override
    public void setCodeForgotPassword(CodeForgotPassword codeForgotPassword) {
        String key = "codeForgotPassword:%s".formatted(codeForgotPassword.getCode());
        this.hashSet(key, "email", codeForgotPassword.getEmail());
        this.hashSet(key, "code", codeForgotPassword.getCode());
        this.setTimeToLive(key, codeForgotPasswordDuration);
    }

    @Override
    public CodeForgotPassword getCodeForgotPassword(String code) {
        String key = "codeForgotPassword:%s".formatted(code);
        return new CodeForgotPassword(this.hashGet(key, "code").toString(), this.hashGet(key, "email").toString());
    }

    @Override
    public void deleteCodeForgotPassword(String code) {
        this.delete(String.format("codeForgotPassword:%s".formatted(code)));
    }
}
