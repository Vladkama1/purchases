package ru.aston.purchases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.purchases.enums.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    @Future
    @NotNull(message = "IsPositive can`t null!")
    private LocalDateTime start;
    @Future
    @NotNull(message = "IsPositive can`t null!")
    private LocalDateTime end;
    @NotNull(message = "IsPositive can`t null!")
    private Long itemId;
    private Long bookerId;
    private Status status;
}
