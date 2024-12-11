package cn.anlucky.system.service.system.impl;

import cn.anlucky.system.pojo.system.LoginLog;
import cn.anlucky.system.mapper.LoginLogMapper;
import cn.anlucky.system.service.system.LoginLogService;
import cn.anlucky.system.utils.AddressUtils;
import cn.anlucky.system.utils.IpUtils;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.utils.ServletUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * 用户登录日志 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:49:27
 */
@Service
public class LoginLogServiceImp extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {


    /**
     * 分页带条件查询LoginLog
     * @param loginLog
     * @return
     */
    @Override
    public List<LoginLog> pageByParams(LoginLog loginLog) {
        if (loginLog == null){
            return this.list();
        }
        LambdaQueryWrapper<LoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(loginLog.getId()), LoginLog::getId, loginLog.getId());
        wrapper.eq(LoginLog::getUsername, Sa.getLoginUserName());
        wrapper.eq(Strings.isNotBlank(loginLog.getIp()), LoginLog::getIp, loginLog.getIp());
        wrapper.eq(Strings.isNotBlank(loginLog.getIpAddr()), LoginLog::getIpAddr, loginLog.getIpAddr());
        wrapper.eq(Strings.isNotBlank(loginLog.getBrowser()), LoginLog::getBrowser, loginLog.getBrowser());
        wrapper.eq(Strings.isNotBlank(loginLog.getOs()), LoginLog::getOs, loginLog.getOs());
        wrapper.eq(Strings.isNotBlank(loginLog.getStatus()), LoginLog::getStatus, loginLog.getStatus());
        wrapper.eq(Strings.isNotBlank(loginLog.getMsg()), LoginLog::getMsg, loginLog.getMsg());
        wrapper.between(Objects.nonNull(loginLog.getCreateTime()), LoginLog::getCreateTime, loginLog.getCreateTime(), loginLog.getCreateTime());
        wrapper.orderByDesc(LoginLog::getCreateTime);
        return this.list(wrapper);
    }

    /**
     * 添加LoginLog日志
     */

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        this.save(loginLog);
    }
}
