package cn.anlucky.system.pojo;

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
 * 操作日志记录
 *
 * @author yifan.du
 * @since 2024-12-03 14:46:47
 */
@Data
@TableName("opr_logs")
@ApiModel(value = "OprLogs对象", description = "操作日志记录")
public class OprLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("模块标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
    @TableField("business_type")
    private Integer businessType;

    @ApiModelProperty("方法名称")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty("请求方式")
    @TableField("request_method")
    private String requestMethod;

    @ApiModelProperty("操作人员")
    @TableField("username")
    private String username;

    @ApiModelProperty("请求URL")
    @TableField("url")
    private String url;

    @ApiModelProperty("主机地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("请求参数")
    @TableField("param")
    private String param;

    @ApiModelProperty("返回参数")
    @TableField("result")
    private String result;

    @ApiModelProperty("操作状态（0正常 1异常）")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("错误消息")
    @TableField("error_msg")
    private String errorMsg;

    @ApiModelProperty("操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("消耗时间")
    @TableField("cost_time")
    private Long costTime;
}
