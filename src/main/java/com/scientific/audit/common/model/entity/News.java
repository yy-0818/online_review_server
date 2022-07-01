package com.scientific.audit.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tb_news
 */
@TableName(value = "tb_news")
@Data
public class News implements Serializable{
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 发布时间
     */
    @TableField(value = "time")
    private Date time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}