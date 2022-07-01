package com.scientific.audit.common.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.scientific.audit.common.model.entity.Direction;
import com.scientific.audit.common.model.entity.PaperFile;
import com.scientific.audit.common.model.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FindByTypesVo{

    /**
     *
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型，0：论文，1：专利，2：报告
     */
    @ApiModelProperty("类型，0：论文，1：专利，2：报告")
    @TableField(value = "types")
    private Byte types;

    /**
     * 论文标题
     */
    @ApiModelProperty("论文标题")
    @TableField(value = "title")
    private String title;

    /**
     * 英文标题
     */
    @ApiModelProperty("英文标题")
    @TableField(value = "title_en")
    private String titleEn;

    /**
     * 论文摘要
     */
    @ApiModelProperty("论文摘要")
    @TableField(value = "summary")
    private String summary;

    /**
     * 英文摘要
     */
    @ApiModelProperty("英文摘要")
    @TableField(value = "summary_en")
    private String summaryEn;

    /**
     * 论文关键词
     */
    @ApiModelProperty("论文关键词")
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 英文关键词
     */
    @ApiModelProperty("英文关键词")
    @TableField(value = "keyword_en")
    private String keywordEn;

    /**
     * 论文状态，0：未审核，1：初审通过，2：初审未通过，3：二审通过，4：二审未通过，5：归档，6：终审未通过；默认未通过
     */
    @ApiModelProperty("论文状态，0：未审核，1：初审通过，2：初审未通过，3：二审通过，4：二审未通过，5：归档，6：终审未通过；默认未通过")
    @TableField(value = "state")
    private Byte state;

    /**
     * 上传者id
     */
    @ApiModelProperty("上传者ID")
    @TableField(value = "uploader_id")
    private Long uploaderId;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    @TableField(value = "name")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "createTime")
    private String createTime;

    private List<Direction> directions = new ArrayList<>();

    private List<PaperFile> paperFiles = new ArrayList<>();

    private User user;

    private List<FindPaperTeacherVo> paperReviewers = new ArrayList<>();
}
