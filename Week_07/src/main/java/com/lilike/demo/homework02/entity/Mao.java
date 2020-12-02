package com.lilike.demo.homework02.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2020-12-02
 */
@Data
@Accessors
@ApiModel(value="Mao", description="")
public class Mao extends Model<Mao> {

    private static final long serialVersionUID = 1L;

    private Integer id;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
