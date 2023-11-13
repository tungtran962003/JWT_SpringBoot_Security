package com.example.springsecurity.security.service;

import com.example.springsecurity.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    // Role liên quan đến hàm getAuthorities() của interface UserDetails nên phải để kiểu dữ liệu giống với hàm đó
    private Collection<? extends GrantedAuthority> roles;


    // Sau khi login cần build User trong package model trên hệ thống và cần vùng nhớ để lưu lại => static
    public static UserDetailsImpl build(User user) {
        //Convert role trong model từ Set -> List vì kiểu dữ liệu Author hệ thống là List
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    // Sửa dữ liệu trả về trong các hàm ghi đè từ interface UserDetail thành dữ liệu giống với model
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    // Các phương thức kiểm tra tính toàn vẹn của người dùng đều sửa thành trả về true
    // Nghĩa là người dùng luôn được coi là "đang hoạt đông" và "không bị khóa"
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
