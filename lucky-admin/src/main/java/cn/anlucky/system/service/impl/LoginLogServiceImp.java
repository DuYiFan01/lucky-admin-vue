package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.LoginLog;
import cn.anlucky.system.mapper.LoginLogMapper;
import cn.anlucky.system.service.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

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
        wrapper.eq(Strings.isNotBlank(loginLog.getUsername()), LoginLog::getUsername, loginLog.getUsername());
        wrapper.eq(Strings.isNotBlank(loginLog.getIp()), LoginLog::getIp, loginLog.getIp());
        wrapper.eq(Strings.isNotBlank(loginLog.getIpAddr()), LoginLog::getIpAddr, loginLog.getIpAddr());
        wrapper.eq(Strings.isNotBlank(loginLog.getBrowser()), LoginLog::getBrowser, loginLog.getBrowser());
        wrapper.eq(Strings.isNotBlank(loginLog.getOs()), LoginLog::getOs, loginLog.getOs());
        wrapper.eq(Strings.isNotBlank(loginLog.getStatus()), LoginLog::getStatus, loginLog.getStatus());
        wrapper.eq(Strings.isNotBlank(loginLog.getMsg()), LoginLog::getMsg, loginLog.getMsg());
        wrapper.between(Objects.nonNull(loginLog.getCreateTime()), LoginLog::getCreateTime, loginLog.getCreateTime(), loginLog.getCreateTime());
    return this.list(wrapper);
    }
}
