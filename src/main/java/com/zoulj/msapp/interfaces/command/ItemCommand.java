package com.zoulj.msapp.interfaces.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "创建商品参数", description = "创建商品参数")
public class ItemCommand {
    private Long id;

    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty(value = "商品名称", name = "title", dataType = "String")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    @ApiModelProperty(value = "商品价格", name = "price", dataType = "String")
    private BigDecimal price;

    @NotNull(message = "库存不能不填")
    @ApiModelProperty(value = "商品的库存", name = "stock", dataType = "String")
    private Integer stock;

    @NotBlank(message = "商品描述信息不能为空")
    @ApiModelProperty(value = "商品的描述", name = "description", dataType = "String")
    private String description;

    @ApiModelProperty(value = "商品的销量", name = "sales", dataType = "Integer")
    private Integer sales;

    @NotBlank(message = "商品图片信息不能为空")
    @ApiModelProperty(value = "商品描述图片的url", name = "imgUrl", dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "秒杀活动内容", name = "promo", dataType = "Object")
    private Promo promo = new Promo();

    @Data
    @NoArgsConstructor
    public static class Promo {
        private Long id;

        @ApiModelProperty(value = "秒杀活动状态：1表示还未开始，2表示正在进行，3表示已结束", name = "status", dataType = "Integer")
        private Integer status = 0;

        @ApiModelProperty(value = "秒杀活动名称", name = "promoName", dataType = "String")
        private String promoName;

        //format:2022-09-07T08:51:25.878Z
        @ApiModelProperty(value = "秒杀活动的开始时间", name = "startDate", dataType = "String")
        private Date startDate;

        @ApiModelProperty(value = "秒杀活动的结束时间", name = "endDate", dataType = "String")
        private Date endDate;

        @ApiModelProperty(value = "秒杀活动的适用商品", name = "itemId", dataType = "Integer")
        private Long itemId;

        @ApiModelProperty(value = "秒杀活动的商品价格", name = "promoItemPrice", dataType = "String")
        private BigDecimal promoItemPrice;
    }
}
