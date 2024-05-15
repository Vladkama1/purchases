package ru.aston.purchases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.purchases.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItem_IdOrderByCreatedDesc(Long itemId);
}
