package com.xuecheng.content.service;

import com.xuecheng.base.exception.ErrCodeAndMessage;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.TeachplanMedia;

import java.util.List;

public interface TeachplanService {

/**
 * @description 查询课程计划树型结构
 * @param courseId  课程id
 * @return List<TeachplanDto>
 * @author Mr.M
 * @date 2022/9/9 11:13
*/
 List<TeachplanDto> findTeachplanTree(long courseId);

 void saveTeachplan(SaveTeachplanDto teachplanDto);

 ErrCodeAndMessage deleteTeachplan(Long id);

 void movedown(Long id);

 void moveup(Long id);

 public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

 void unassociationMedia(Long teachPlanId, String mediaId);
}