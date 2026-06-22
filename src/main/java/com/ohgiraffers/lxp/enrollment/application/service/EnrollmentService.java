package com.ohgiraffers.lxp.enrollment.application.service;

import com.ohgiraffers.lxp.enrollment.application.dto.CourseInfo;
import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.dto.MemberInfo;
import com.ohgiraffers.lxp.enrollment.application.exception.CourseNotPublicException;
import com.ohgiraffers.lxp.enrollment.application.exception.DuplicateEnrollmentException;
import com.ohgiraffers.lxp.enrollment.application.exception.MemberNotLearnerException;
import com.ohgiraffers.lxp.enrollment.application.exception.MemberSuspendedException;
import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;
import com.ohgiraffers.lxp.enrollment.application.port.in.EnrollUseCase;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadCourseInfoPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadMemberInfoPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.SaveEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnrollmentService implements EnrollUseCase {

    private final LoadMemberInfoPort loadMemberInfoPort;
    private final LoadCourseInfoPort loadCourseInfoPort;
    private final LoadEnrollmentPort loadEnrollmentPort;
    private final SaveEnrollmentPort saveEnrollmentPort;

    public EnrollmentService(
            LoadMemberInfoPort loadMemberInfoPort,
            LoadCourseInfoPort loadCourseInfoPort,
            LoadEnrollmentPort loadEnrollmentPort,
            SaveEnrollmentPort saveEnrollmentPort
    ) {
        this.loadMemberInfoPort = loadMemberInfoPort;
        this.loadCourseInfoPort = loadCourseInfoPort;
        this.loadEnrollmentPort = loadEnrollmentPort;
        this.saveEnrollmentPort = saveEnrollmentPort;
    }

    @Override
    public EnrollmentResult enroll(EnrollCommand command) {
        MemberInfo member = loadMemberInfoPort.load(command.memberId());

        if (!member.isLearner()) {
            throw new MemberNotLearnerException();
        }
        if (member.isSuspended()) {
            throw new MemberSuspendedException();
        }

        CourseInfo course = loadCourseInfoPort.load(command.courseId());

        if (!course.isPublished()) {
            throw new CourseNotPublicException();
        }
        if (loadEnrollmentPort.existsActiveEnrollment(command.memberId(), command.courseId())) {
            throw new DuplicateEnrollmentException();
        }

        Enrollment enrollment = Enrollment.enroll(command.memberId(), command.courseId());

        return saveEnrollmentPort.save(enrollment);
    }
}
