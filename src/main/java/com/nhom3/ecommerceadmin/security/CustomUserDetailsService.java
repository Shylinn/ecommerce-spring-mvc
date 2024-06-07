package com.nhom3.ecommerceadmin.security;

import com.nhom3.ecommerceadmin.models.Role;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.repository.StaffRepository;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private StaffRepository staffRepository;

    @Autowired
    public CustomUserDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff user = staffRepository.findFirstByUsername(username);
        if (user != null) {
            User authUser = new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
            return authUser;
        } else {
            throw new UsernameNotFoundException("Sai tên đăng nhập hoặc mật khẩu");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(String username) {
        List<Role> roles = staffRepository.findFirstByUsername(username).getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

}
