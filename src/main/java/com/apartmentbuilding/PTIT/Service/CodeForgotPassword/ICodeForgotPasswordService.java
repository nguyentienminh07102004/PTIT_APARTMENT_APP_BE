package com.apartmentbuilding.PTIT.Service.CodeForgotPassword;

import com.apartmentbuilding.PTIT.Model.RedisHash.CodeForgotPassword;

public interface ICodeForgotPasswordService {
    void setCodeForgotPassword(CodeForgotPassword codeForgotPassword);
    CodeForgotPassword getCodeForgotPassword(String code);
    void deleteCodeForgotPassword(String code);
}
