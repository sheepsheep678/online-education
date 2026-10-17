package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddCourseTeacherDto {
    private Long id;
    @NotNull
    @ApiModelProperty(value = "课程id", required = true)
    private Long courseId;
    @NotEmpty
    @ApiModelProperty(value = "老师姓名", required = true)
    private String teacherName;

    @ApiModelProperty(value = "岗位")
    private String position;
    @ApiModelProperty(value = "介绍")
    private String introduction;
}
