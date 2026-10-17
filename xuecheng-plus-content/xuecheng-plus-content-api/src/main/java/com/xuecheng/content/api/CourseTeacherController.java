package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "老师信息编辑接口",tags = "老师信息编辑接口")
@RestController
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @ApiOperation("老师查询接口")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getCourseTeacherByCourseId(@PathVariable Long courseId){
        return courseTeacherService.queryCourseTeacherByCourseId(courseId);
    }

    @ApiOperation("添加或修改老师接口")
    @PostMapping("/courseTeacher")
    public CourseTeacher addCourseTeacher(@RequestBody AddCourseTeacherDto addCourseTeacherDto){
        Long companyId=1232141425L;
        return courseTeacherService.addCourseTeacher(companyId,addCourseTeacherDto);
    }

    @ApiOperation("删除老师接口")
    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    public void deleteCourseTeacher(@PathVariable Long courseId,@PathVariable Long teacherId){
        Long companyId=1232141425L;
        courseTeacherService.deleteCourseTeacher(companyId,courseId,teacherId);
    }

}
