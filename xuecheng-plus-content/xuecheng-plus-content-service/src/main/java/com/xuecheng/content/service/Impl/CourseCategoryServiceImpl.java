package com.xuecheng.content.service.Impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos=courseCategoryMapper.selectTreeNodes(id);
        //将list转map,除去根节点
        Map<String, CourseCategoryTreeDto> mapTemp  = courseCategoryTreeDtos.stream().
                filter(item -> !item.getId().equals(id)).collect(Collectors.
                toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //最终返回的列表
        List<CourseCategoryTreeDto> categoryTreeDtos=new ArrayList<>();
        //遍历节点，除去根节点
        courseCategoryTreeDtos.stream().filter(item -> !item.getId().equals(id)).forEach(item -> {
            if(item.getParentid().equals(id)) {
                categoryTreeDtos.add(item);
            }
            //找到当前节点父节点
            CourseCategoryTreeDto courseCategoryParent = mapTemp.get(item.getParentid());
            if(courseCategoryParent!=null){
                if(courseCategoryParent.getChildrenTreeNodes() ==null){
                    courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //下边开始往ChildrenTreeNodes属性中放子节点
                courseCategoryParent.getChildrenTreeNodes().add(item);
            }
        });

        return categoryTreeDtos;
    }
}