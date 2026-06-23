package com.ohgiraffers.lxp.enrollment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ohgiraffers.lxp.enrollment.application.dto.CourseInfo;
import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.dto.MemberInfo;
import com.ohgiraffers.lxp.enrollment.application.dto.Role;
import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadCourseInfoPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadMemberInfoPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.SaveEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.service.EnrollmentService;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("수강신청 유스케이스 단위 테스트")
class EnrollmentServiceTest {

    private static final long MEMBER_ID = 1L;
    private static final long COURSE_ID = 2L;
    private static final EnrollCommand COMMAND = new EnrollCommand(MEMBER_ID, COURSE_ID);

    @Mock
    private LoadMemberInfoPort loadMemberInfoPort;
    @Mock
    private LoadCourseInfoPort loadCourseInfoPort;
    @Mock
    private LoadEnrollmentPort loadEnrollmentPort;
    @Mock
    private SaveEnrollmentPort saveEnrollmentPort;

    @InjectMocks
    private EnrollmentService service;

    private MemberInfo learner(boolean suspended) {
        return new MemberInfo(MEMBER_ID, Role.LEARNER, suspended);
    }

    @Test
    @DisplayName("모든 불변식 충족 시 수강을 저장하고 결과를 반환한다")
    void enrollHappyPath() {
        given(loadMemberInfoPort.load(MEMBER_ID)).willReturn(learner(false));
        given(loadCourseInfoPort.load(COURSE_ID)).willReturn(new CourseInfo(COURSE_ID, true));
        given(loadEnrollmentPort.existsActiveEnrollment(MEMBER_ID, COURSE_ID)).willReturn(false);
        EnrollmentResult expected = new EnrollmentResult(
                10L, MEMBER_ID, COURSE_ID, EnrollmentStatus.ACTIVE, LocalDateTime.now());
        given(saveEnrollmentPort.save(any(Enrollment.class))).willReturn(expected);

        EnrollmentResult result = service.enroll(COMMAND);

        assertThat(result).isEqualTo(expected);
        verify(saveEnrollmentPort).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("수강생 역할이 아니면 MEMBER_NOT_LEARNER - 저장하지 않는다")
    void notLearner() {
        given(loadMemberInfoPort.load(MEMBER_ID))
                .willReturn(new MemberInfo(MEMBER_ID, Role.INSTRUCTOR, false));

        assertThatThrownBy(() -> service.enroll(COMMAND))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.MEMBER_NOT_LEARNER);
        verify(saveEnrollmentPort, never()).save(any());
    }

    @Test
    @DisplayName("정지 회원이면 MEMBER_SUSPENDED - 저장하지 않는다")
    void suspended() {
        given(loadMemberInfoPort.load(MEMBER_ID)).willReturn(learner(true));

        assertThatThrownBy(() -> service.enroll(COMMAND))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.MEMBER_SUSPENDED);
        verify(saveEnrollmentPort, never()).save(any());
    }

    @Test
    @DisplayName("비공개 강좌면 COURSE_NOT_PUBLIC - 저장하지 않는다")
    void courseNotPublic() {
        given(loadMemberInfoPort.load(MEMBER_ID)).willReturn(learner(false));
        given(loadCourseInfoPort.load(COURSE_ID)).willReturn(new CourseInfo(COURSE_ID, false));

        assertThatThrownBy(() -> service.enroll(COMMAND))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.COURSE_NOT_PUBLIC);
        verify(saveEnrollmentPort, never()).save(any());
    }

    @Test
    @DisplayName("ACTIVE 중복이 있으면 ENROLLMENT_ALREADY_EXISTS - 저장하지 않는다")
    void duplicateActive() {
        given(loadMemberInfoPort.load(MEMBER_ID)).willReturn(learner(false));
        given(loadCourseInfoPort.load(COURSE_ID)).willReturn(new CourseInfo(COURSE_ID, true));
        given(loadEnrollmentPort.existsActiveEnrollment(MEMBER_ID, COURSE_ID)).willReturn(true);

        assertThatThrownBy(() -> service.enroll(COMMAND))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.ENROLLMENT_ALREADY_EXISTS);
        verify(saveEnrollmentPort, never()).save(any());
    }
}
