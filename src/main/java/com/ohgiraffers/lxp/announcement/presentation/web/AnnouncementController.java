package com.ohgiraffers.lxp.announcement.presentation.web;

import com.ohgiraffers.lxp.announcement.application.dto.AnnouncementResult;
import com.ohgiraffers.lxp.announcement.application.port.command.CreateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.command.UpdateAnnouncementCommand;
import com.ohgiraffers.lxp.announcement.application.port.in.CreateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.DeleteAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.GetAnnouncementListUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.GetAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.application.port.in.UpdateAnnouncementUseCase;
import com.ohgiraffers.lxp.announcement.presentation.dto.AnnouncementResponse;
import com.ohgiraffers.lxp.announcement.presentation.dto.CreateAnnouncementRequest;
import com.ohgiraffers.lxp.auth.presentation.support.RequireRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.announcement.presentation.dto.DeleteAnnouncementResponse;
import com.ohgiraffers.lxp.announcement.presentation.dto.UpdateAnnouncementRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;
    private final DeleteAnnouncementUseCase deleteAnnouncementUseCase;
    private final GetAnnouncementListUseCase getAnnouncementListUseCase;
    private final GetAnnouncementUseCase getAnnouncementUseCase;

    public AnnouncementController(CreateAnnouncementUseCase createAnnouncementUseCase,
                                  UpdateAnnouncementUseCase updateAnnouncementUseCase,
                                  DeleteAnnouncementUseCase deleteAnnouncementUseCase,
                                  GetAnnouncementListUseCase getAnnouncementListUseCase,
                                  GetAnnouncementUseCase getAnnouncementUseCase) {
        this.createAnnouncementUseCase = createAnnouncementUseCase;
        this.updateAnnouncementUseCase = updateAnnouncementUseCase;
        this.deleteAnnouncementUseCase = deleteAnnouncementUseCase;
        this.getAnnouncementListUseCase = getAnnouncementListUseCase;
        this.getAnnouncementUseCase = getAnnouncementUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAll() {
        return ResponseEntity.ok(
                getAnnouncementListUseCase.getAnnouncements().stream()
                        .map(AnnouncementResponse::from)
                        .toList()
        );
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> get(@PathVariable Long announcementId) {
        return ResponseEntity.ok(AnnouncementResponse.from(getAnnouncementUseCase.getAnnouncement(announcementId)));
    }

    @RequireRole(MemberRole.ADMIN)
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

    @RequireRole(MemberRole.ADMIN)
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

    @RequireRole(MemberRole.ADMIN)
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<DeleteAnnouncementResponse> delete(@PathVariable Long announcementId) {
        Long id = deleteAnnouncementUseCase.deleteAnnouncement(announcementId);
        return ResponseEntity.ok(new DeleteAnnouncementResponse(id));
    }
}