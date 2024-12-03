package cn.anlucky.system.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 用户登录日志
 *
 * @author yifan.du
 * @since 2024-12-03 14:49:27
 */
@Data
@TableName("login_log")
@ApiModel(value = "LoginLog对象", description = "用户登录日志")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("登录账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("登录IP地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("登录地点")
    @TableField("ip_addr")
    private String ipAddr;

    @ApiModelProperty("浏览器类型")
    @TableField("browser")
    private String browser;

    @ApiModelProperty("操作系统")
    @TableField("os")
    private String os;

    @ApiModelProperty("登录状态（0成功 1失败）")
    @TableField("`STATUS`")
    private String status;

    @ApiModelProperty("提示消息")
    @TableField("msg")
    private String msg;

    @ApiModelProperty("访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
