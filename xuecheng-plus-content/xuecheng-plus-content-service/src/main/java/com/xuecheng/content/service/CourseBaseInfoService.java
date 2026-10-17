package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {
    //课程分页查询接口
    public PageResult<CourseBase> queryCourseBaseList(String companyId, PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    //新增课程接口
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    //根据id查询课程接口
    CourseBaseInfoDto queryCourseBaseById(Long courseId);

    //修改课程接口
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);

    void deleteCourseBase(Long courseId);
}
