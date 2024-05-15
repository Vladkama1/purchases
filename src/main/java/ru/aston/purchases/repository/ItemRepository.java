package ru.aston.purchases.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.purchases.model.Item;
import ru.aston.purchases.model.Request;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByIdAndOwner_Id(Long itemId, Long ownerId);

    Page<Item> findAllByOwner_IdOrderById(Long ownerId, Pageable pageable);

    Page<Item> findByDescriptionContainingIgnoreCaseAndAvailableIsTrue(String text, Pageable pageable);

    List<Item> findAllByRequestIn(List<Request> requestList);
}
