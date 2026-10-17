package com.xuecheng.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Override
    public List<CourseTeacher> queryCourseTeacherByCourseId(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(queryWrapper);
        return courseTeachers;

    }

    @Transactional
    @Override
    public CourseTeacher addCourseTeacher(Long companyId,AddCourseTeacherDto dto) {
        Long id=dto.getId();
        if(id==null){
            //添加
            Long courseId = dto.getCourseId();
            CourseBase courseBase = courseBaseMapper.selectById(courseId);
            if (courseBase == null) {
                XueChengPlusException.cast("课程不存在");
            }

            //校验本机构只能修改本机构的课程教师
            if (!courseBase.getCompanyId().equals(companyId)) {
                XueChengPlusException.cast("本机构只能修改本机构课程教师");
            }
            CourseTeacher courseTeacherNew = new CourseTeacher();
            BeanUtils.copyProperties(dto,courseTeacherNew);
            courseTeacherNew.setCreateDate(LocalDateTime.now());
            int insert=courseTeacherMapper.insert(courseTeacherNew);
            if(insert<=0){
                XueChengPlusException.cast("添加课程教师信息失败");
            }
            return courseTeacherMapper.selectById(courseTeacherNew.getId());
        }else{
            //修改
            Long courseTeacherId = dto.getId();
            Long courseId = dto.getCourseId();
            CourseBase courseBase = courseBaseMapper.selectById(courseId);
            if (courseBase == null) {
                XueChengPlusException.cast("课程不存在");
            }

            //校验本机构只能修改本机构的课程教师
            if (!courseBase.getCompanyId().equals(companyId)) {
                XueChengPlusException.cast("本机构只能修改本机构课程教师");
            }
            CourseTeacher courseTeacher = courseTeacherMapper.selectById(courseTeacherId);
            if (courseTeacher == null) {
                throw new XueChengPlusException("教师不存在");
            }

            BeanUtils.copyProperties(dto,courseTeacher);
            int update = courseTeacherMapper.updateById(courseTeacher);
            log.info("更新课程教师信息成功");
            if(update<=0){
                XueChengPlusException.cast("更新课程教师信息失败");
            }
            return courseTeacherMapper.selectById(courseTeacherId);
        }

    }

    @Transactional
    @Override
    public void deleteCourseTeacher(Long companyId, Long courseId, Long teacherId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }
        Long companyIdNew = courseBase.getCompanyId();
        if (!companyIdNew.equals(companyId)) {
            XueChengPlusException.cast("本机构只能删除本机构课程教师");
        }
        int delete = courseTeacherMapper.deleteById(teacherId);
        if(delete<=0) {
            XueChengPlusException.cast("删除课程教师信息失败");
        }
        log.info("删除课程教师信息成功");
    }
}
