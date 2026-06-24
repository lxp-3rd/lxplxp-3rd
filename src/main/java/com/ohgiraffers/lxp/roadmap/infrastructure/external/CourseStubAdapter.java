package com.ohgiraffers.lxp.roadmap.infrastructure.external;

import com.ohgiraffers.lxp.roadmap.application.port.out.CoursePort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CourseStubAdapter implements CoursePort {

    private static final Set<Long> COURSE_IDS = Set.of(1L, 2L, 3L);

    @Override
    public boolean existsAllByIds(List<Long> courseIds) {
        if (courseIds == null) {
            return false;
        }
        return COURSE_IDS.containsAll(new HashSet<>(courseIds));
    }
}
