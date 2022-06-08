package com.yyl.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Like)表实体类
 *
 * @author makejava
 * @since 2022-04-28 20:02:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_like")
public class Like  {
    @TableId
    private Long id;

    
    private Long userId;
    
    private Long articleId;
    
    private Integer like;



}
