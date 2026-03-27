package com.evcar.repository.user;

import com.evcar.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.loginId = :loginId")
	boolean existsByLoginId(@Param("loginId") String loginId);
    boolean existsByEmail(String email);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNameAndEmail(String name, String email);
    Optional<User> findByLoginIdAndNameAndEmail(String loginId, String name, String email);
}