package com.ohgiraffers.lxp.roadmap.application.port.out;

import java.util.List;

public interface CoursePort {

    boolean existsAllByIds(List<Long> courseIds);
}
