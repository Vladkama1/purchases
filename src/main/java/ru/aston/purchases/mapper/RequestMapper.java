package ru.aston.purchases.mapper;

import org.mapstruct.Mapper;
import ru.aston.purchases.dto.RequestDto;
import ru.aston.purchases.model.Request;


@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestDto toDTO(Request request);
}
