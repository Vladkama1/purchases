package ru.aston.purchases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ItemOutDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingShortOutDto lastBooking;
    private BookingShortOutDto nextBooking;
    private List<CommentOutDto> comments;
    private Long requestId;
}
