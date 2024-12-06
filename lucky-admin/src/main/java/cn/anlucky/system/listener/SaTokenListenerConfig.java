package cn.anlucky.system.listener;

import cn.anlucky.system.pojo.LoginLog;
import cn.anlucky.system.service.LoginLogService;
import cn.anlucky.system.utils.AddressUtils;
import cn.anlucky.system.utils.IpUtils;
import cn.anlucky.system.utils.ServletUtils;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义侦听器的实现
 */
@Component
public class SaTokenListenerConfig implements SaTokenListener {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {

        String username = "admin";
        String ip = IpUtils.getIpAddr();
        String ipAddr = AddressUtils.getRealAddressByIP(ip);
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(ip);
        loginLog.setIpAddr(ipAddr);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setStatus("0");
        loginLog.setMsg("登录成功");
        loginLog.setCreateTime(LocalDateTime.now());
        System.out.println("tokenValue = " + tokenValue);
        // loginLogService.save(loginLog);
        System.out.println("---------- 自定义侦听器实现 doLogin");
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        System.out.println("---------- 自定义侦听器实现 doLogout");
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        System.out.println("---------- 自定义侦听器实现 doKickout");
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        System.out.println("---------- 自定义侦听器实现 doReplaced");
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
        System.out.println("---------- 自定义侦听器实现 doDisable");
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
        System.out.println("---------- 自定义侦听器实现 doUntieDisable");
    }

    /**
     * 每次二级认证时触发
     */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
        System.out.println("---------- 自定义侦听器实现 doOpenSafe");
    }

    /**
     * 每次退出二级认证时触发
     */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
        System.out.println("---------- 自定义侦听器实现 doCloseSafe");
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
        System.out.println("---------- 自定义侦听器实现 doCreateSession");
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
        System.out.println("---------- 自定义侦听器实现 doLogoutSession");
    }

    /**
     * 每次Token续期时触发
     */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
        System.out.println("---------- 自定义侦听器实现 doRenewTimeout");
    }
}
