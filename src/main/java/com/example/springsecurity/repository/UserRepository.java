package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); //Tìm kiếm User có tồn tại trong DB lúc đăng nhập chưa ?

    Boolean existsByUsername(String username); // Username đã có trong DB khi tạo dữ liệu chưa ?

    Boolean existsByEmail(String email); // Email đã có trong DB khi tạo dữ liệu chưa ?
}
