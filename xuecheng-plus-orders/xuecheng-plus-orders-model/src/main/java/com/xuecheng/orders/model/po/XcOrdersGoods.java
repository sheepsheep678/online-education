package com.xuecheng.orders.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author itcast
 */
@Data
@ToString
@TableName("xc_orders_goods")
public class XcOrdersGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    // 使用ToStringSerializer将Long类型的id字段转换为String类型
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 订单号
     */
    // 使用ToStringSerializer将Long类型的id字段转换为String类型
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品类型
     */
    private String goodsType;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品交易价，单位分
     */
    private Float goodsPrice;

    /**
     * 商品详情json,可为空
     */
    private String goodsDetail;


}
