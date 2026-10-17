package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

public interface CourseTeacherService {

    List<CourseTeacher> queryCourseTeacherByCourseId(Long courseId);

    CourseTeacher addCourseTeacher(Long companyId,AddCourseTeacherDto addCourseTeacherDto);

    void deleteCourseTeacher(Long companyId, Long courseId, Long teacherId);
}
