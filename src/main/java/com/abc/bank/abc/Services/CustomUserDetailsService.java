package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.Employee;
import com.abc.bank.abc.DataModels.Roles;
import com.abc.bank.abc.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username);

        if (employee == null)
            throw new UsernameNotFoundException("user not found for username " + username);

        List<Roles> roles = employee.getRoles();
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.size());

        User user = new User(employee.getName(), employee.getPasswordHash(),employee.isEnabled(), true, true, true, authorities);

        return user;
    }
}