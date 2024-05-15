package ru.aston.purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.purchases.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
}

