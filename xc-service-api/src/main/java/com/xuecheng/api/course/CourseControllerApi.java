package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;

public interface CourseControllerApi {
    TeachplanNode getTeachplanTree(String courseId);
}
