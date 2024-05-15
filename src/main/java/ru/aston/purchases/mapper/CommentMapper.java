package ru.aston.purchases.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.aston.purchases.dto.CommentDto;
import ru.aston.purchases.dto.CommentOutDto;
import ru.aston.purchases.model.Comment;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mappings({
            @Mapping(source = "commentDto.authorId", target = "author.id"),
            @Mapping(source = "commentDto.itemId", target = "item.id")
    })
    Comment toModelCom(CommentDto commentDto);

    @Mapping(source = "comment.author.name", target = "authorName")
    CommentOutDto toOutDTOCom(Comment comment);

    @Mapping(source = "comment.author.name", target = "authorName")
    List<CommentOutDto> toListOutDTOCom(List<Comment> commentList);
}
