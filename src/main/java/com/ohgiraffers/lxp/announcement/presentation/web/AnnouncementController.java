package com.ohgiraffers.lxp.announcement.presentation.web;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.command.UpdateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.DeleteAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.UpdateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.presentation.dto.AnnouncementResponse;
import com.ohgiraffers.lxp.announcement.presentation.dto.CreateAnnouncementRequest;
import com.ohgiraffers.lxp.announcement.presentation.dto.DeleteAnnouncementResponse;
import com.ohgiraffers.lxp.announcement.presentation.dto.UpdateAnnouncementRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;
    private final DeleteAnnouncementUseCase deleteAnnouncementUseCase;

    public AnnouncementController(CreateAnnouncementUseCase createAnnouncementUseCase,
                                  UpdateAnnouncementUseCase updateAnnouncementUseCase,
                                  DeleteAnnouncementUseCase deleteAnnouncementUseCase) {
        this.createAnnouncementUseCase = createAnnouncementUseCase;
        this.updateAnnouncementUseCase = updateAnnouncementUseCase;
        this.deleteAnnouncementUseCase = deleteAnnouncementUseCase;
    }

    @PostMapping
    public ResponseEntity<AnnouncementResponse> create(@RequestBody @Valid CreateAnnouncementRequest request) {
        CreateAnnouncementCommand command = new CreateAnnouncementCommand(
                request.adminId(),
                request.title(),
                request.content(),
                request.status()
        );
        AnnouncementResult result = createAnnouncementUseCase.createAnnouncement(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(AnnouncementResponse.from(result));
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> update(
            @PathVariable Long announcementId,
            @RequestBody @Valid UpdateAnnouncementRequest request) {
        UpdateAnnouncementCommand command = new UpdateAnnouncementCommand(
                announcementId,
                request.title(),
                request.content(),
                request.status()
        );
        AnnouncementResult result = updateAnnouncementUseCase.updateAnnouncement(command);
        return ResponseEntity.ok(AnnouncementResponse.from(result));
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<DeleteAnnouncementResponse> delete(@PathVariable Long announcementId) {
        Long id = deleteAnnouncementUseCase.deleteAnnouncement(announcementId);
        return ResponseEntity.ok(new DeleteAnnouncementResponse(id));
    }
}