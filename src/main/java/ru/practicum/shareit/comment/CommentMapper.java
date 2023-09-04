package ru.practicum.shareit.comment;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "authorName", target = "author.name")
    Comment toComment(CommentDto commentDto);

    @InheritInverseConfiguration(name = "toComment")
    CommentDto toDto(Comment comment);

    Set<CommentDto> map(Set<Comment> comments);
}