package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageParams {

    // 加上 example = "1"
    @ApiModelProperty(value = "当前页码", example = "1")
    private Long pageNo = 1L;

    // 加上 example = "30"
    @ApiModelProperty(value = "每页记录数默认值", example = "30")
    private Long pageSize = 30L;
}
