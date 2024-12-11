package cn.anlucky.system.pojo.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "LoginLog", description = "用户登录日志")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "登录账号")
    @TableField("username")
    private String username;

    @Schema(description = "登录IP地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "登录地点")
    @TableField("ip_addr")
    private String ipAddr;

    @Schema(description = "浏览器类型")
    @TableField("browser")
    private String browser;

    @Schema(description = "操作系统")
    @TableField("os")
    private String os;

    @Schema(description = "登录状态（0成功 1失败）")
    @TableField("`STATUS`")
    private String status;

    @Schema(description = "提示消息")
    @TableField("msg")
    private String msg;

    @Schema(description = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
