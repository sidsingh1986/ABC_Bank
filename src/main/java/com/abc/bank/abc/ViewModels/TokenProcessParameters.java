package com.abc.bank.abc.ViewModels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TokenProcessParameters {

    @NotNull
    EmployeeModel employee;

    @NotNull
    @Size(min = 1, max = 500)
    String comments;
}
