package com.abc.bank.abc.viewmodels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TokenProcessParameters {

    @NotNull
    EmployeeModel employee;

    @NotNull
    @Size(min = 1, max = 500)
    String comments;

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        if (employee == null) {
            throw new NullPointerException("The employee to process token should not be null");
        }
        this.employee = employee;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
