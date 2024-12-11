package cn.anlucky.system.pojo.system;

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
 * 操作日志记录
 *
 * @author yifan.du
 * @since 2024-12-03 14:46:47
 */
@Data
@TableName("opr_logs")
@Schema(name = "OprLogs", description = "操作日志记录")
public class OprLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "模块标题")
    @TableField("title")
    private String title;

    @Schema(description = "业务类型（0其它 1新增 2修改 3删除）")
    @TableField("business_type")
    private Integer businessType;

    @Schema(description = "方法名称")
    @TableField("method_name")
    private String methodName;

    @Schema(description = "请求方式")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "操作人员")
    @TableField("username")
    private String username;

    @Schema(description = "请求URL")
    @TableField("url")
    private String url;

    @Schema(description = "主机地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "请求参数")
    @TableField("param")
    private String param;

    @Schema(description = "返回参数")
    @TableField("result")
    private String result;

    @Schema(description = "操作状态（0正常 1异常）")
    @TableField("`status`")
    private Integer status;

    @Schema(description = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField("start_time")
    private LocalDateTime startTime;

    @Schema(description = "消耗时间")
    @TableField("cost_time")
    private Long costTime;
}
