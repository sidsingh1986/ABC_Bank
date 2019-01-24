package com.abc.bank.abc.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TokenProcessParameters {

    @NotNull
    EmployeeDTO employee;

    @NotNull
    @Size(min = 1, max = 500)
    String comments;
}
