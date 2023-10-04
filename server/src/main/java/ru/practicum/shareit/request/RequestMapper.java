package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RequestMapper {

    Request toRequest(RequestDto requestDto);

    RequestWithProposalsDto toRequestWithProposalDto(Request request);

    RequestDto toDto(Request request);
}