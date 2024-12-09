package cn.anlucky.system.service.system;

import cn.anlucky.system.pojo.system.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

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

    /**
     * 添加LoginLog日志
     */
    public void saveLoginLog(LoginLog loginLog);

}
