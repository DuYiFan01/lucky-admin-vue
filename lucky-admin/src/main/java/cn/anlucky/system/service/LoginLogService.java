package cn.anlucky.system.service;

import cn.anlucky.system.pojo.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 *
 * 用户登录日志 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:49:27
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 分页带条件查询LoginLog
     * @param loginLog
     * @return
     */
    public List<LoginLog> pageByParams(LoginLog loginLog);

}
