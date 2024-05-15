package ru.aston.purchases.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RequestDto {
    private Long id;
    @NotBlank
    private String description;
    private List<ItemOutDto> items;
    @JsonFormat
    private LocalDateTime created;
}
