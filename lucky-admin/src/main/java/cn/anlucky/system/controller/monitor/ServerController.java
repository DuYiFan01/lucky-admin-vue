package cn.anlucky.system.controller.monitor;

import cn.anlucky.system.pojo.monitor.Server;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    /**
     * 查询服务器监控信息
     * @return
     * @throws Exception
     */
    @SaCheckPermission("monitor::server::list")
    @GetMapping()
    public R getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return R.ok(server);
    }
}
