package ru.aston.purchases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    @NotBlank(message = "Текст не может быть пустым!")
    private String text;
    private Long itemId;
    private Long authorId;
    private LocalDateTime created;
}