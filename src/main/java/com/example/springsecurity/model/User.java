package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User", uniqueConstraints = {
        // 2 trường trong bảng không có dữ liệu trung nhau
        @UniqueConstraint(columnNames = "Username"),
        @UniqueConstraint(columnNames = "Email")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Size(max = 20, message = "Username maximum 20 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(max = 50, message = "Email maximum 50 characters")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(max = 100, message = "Maximum password 100 characters")
    @Column(name = "Password")
    @NotBlank(message = "Password cannot be left blank")
    @JsonIgnore
    private String password;

    // Tao bảng trung gian quan hệ nhiều nhiều
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
