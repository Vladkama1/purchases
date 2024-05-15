package ru.aston.purchases.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.purchases.model.Request;
import ru.aston.purchases.model.User;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findAllByRequesterNotOrderByCreatedDesc(User requester, Pageable pageable);

    List<Request> findAllByRequesterOrderByCreatedDesc(User requester);
}
