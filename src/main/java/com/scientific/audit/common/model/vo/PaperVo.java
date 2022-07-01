package com.scientific.audit.common.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel("论文表vo")
public class PaperVo implements Serializable{
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
     * 论文上传时间
     */
    @ApiModelProperty("论文上传时间")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 上传者id
     */
    @ApiModelProperty("上传者ID")
    @TableField(value = "uploader_id")
    private Long uploaderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 审稿人Id
     */
    @ApiModelProperty("审稿人ID----列表")
    @TableField(value = "user_id")
    private String userId;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    @TableField(value = "url")
    private String url;

    /**
     * 文件类型， 0为需要审核的文件地址，1为老师意见
     */
    @ApiModelProperty("文件类型， 0为需要审核的文件地址，1为老师意见")
    @TableField(value = "type_or")
    private Byte typeOr;

    /**
     * 方向Id
     */
    @ApiModelProperty("方向ID----字符串")
    @TableField(value = "direction_id")
    private String directionId;

    @ApiModelProperty("审稿人ID----字符串")
    @TableField(value = "reviewer_id")
    private String reviewerId;


    /**
     * 邮箱， 登录凭据
     */
    @TableField(value = "email")
    private String email;

    /**
     * 密码， 登录凭据
     */
    @TableField(value = "password")
    private String password;

    /**
     * 性别； 0：女， 1：男
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 用户类型，1：普通用户，2：老师，3：管理员,4：超级管理员
     */
    @TableField(value = "role")
    private Byte role;

    /**
     * 用户真实姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户头像地址
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;


}
