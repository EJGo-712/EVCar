package com.evcar.repository.user;

import com.evcar.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByLoginIdAndNameAndEmail(String loginId, String name, String email);

    Optional<User> findByNameAndEmail(String name, String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);
}