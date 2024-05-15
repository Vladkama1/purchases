package ru.aston.purchases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.purchases.enums.Status;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingOutDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemOutDto item;
    private UserOutDto booker;
    private Status status;
}
