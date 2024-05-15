package ru.aston.purchases.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ItemDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
    private Long ownerId;
    private Long requestId;
}
