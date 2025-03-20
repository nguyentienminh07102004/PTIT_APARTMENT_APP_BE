package com.apartmentbuilding.PTIT.Redis.Service.CodeForgotPassword;

import com.apartmentbuilding.PTIT.Redis.RedisHash.CodeForgotPassword;

public interface ICodeForgotPasswordService {
    void setCodeForgotPassword(CodeForgotPassword codeForgotPassword);
    CodeForgotPassword getCodeForgotPassword(String code);
    void deleteCodeForgotPassword(String code);
}
