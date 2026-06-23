package com.ohgiraffers.lxp.announcement.presentation.web;

import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.presentation.dto.CreateAnnouncementRequest;
import com.ohgiraffers.lxp.announcement.presentation.dto.CreateAnnouncementResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final CreateAnnouncementUseCase createAnnouncementUseCase;

    public AnnouncementController(CreateAnnouncementUseCase createAnnouncementUseCase) {
        this.createAnnouncementUseCase = createAnnouncementUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateAnnouncementResponse> create(@RequestBody @Valid CreateAnnouncementRequest request) {
        CreateAnnouncementCommand command = new CreateAnnouncementCommand(
                request.adminId(),
                request.title(),
                request.content(),
                request.status()
        );
        Long id = createAnnouncementUseCase.createAnnouncement(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAnnouncementResponse(id));
    }
}