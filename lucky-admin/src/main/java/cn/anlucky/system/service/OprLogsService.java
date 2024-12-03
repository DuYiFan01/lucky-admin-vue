package cn.anlucky.system.service;

import cn.anlucky.system.pojo.OprLogs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 操作日志记录 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:46:47
 */
public interface OprLogsService extends IService<OprLogs> {

    /**
     * 分页带条件查询OprLogs
     * @param oprLogs
     * @return
     */
    public List<OprLogs> pageByParams(OprLogs oprLogs);

    /**
     * 修改所有字段为新值
     * @param  oprLogs
     * @return
     */
    public boolean updateAllById(OprLogs oprLogs);

    /**
     * 修改指定字段为新值
     * @param oprLogs
     * @return
     */
    public boolean updateSpecifyById(OprLogs oprLogs);

}
