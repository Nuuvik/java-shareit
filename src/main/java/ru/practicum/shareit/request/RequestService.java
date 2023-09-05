package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    RequestDto save(Long userId, RequestDto requestDto);

    RequestWithProposalsDto getRequest(Long userId, Long requestId);

    List<RequestWithProposalsDto> getRequests(Long userId);

    List<RequestWithProposalsDto> getPartOfRequests(Long userId, Pageable pageable);
}