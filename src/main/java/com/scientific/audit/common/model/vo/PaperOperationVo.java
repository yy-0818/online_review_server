package com.scientific.audit.common.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("论文操作Vo")
public class PaperOperationVo{

    @ApiModelProperty("论文id")
    private Long id;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("意见")
    private String opinion;

    @ApiModelProperty("原因")
    private String reason;

    @ApiModelProperty("批注文件地址")
    private String commentFileUrl;


}
