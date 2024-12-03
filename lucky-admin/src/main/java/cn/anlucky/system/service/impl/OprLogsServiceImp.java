package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.OprLogs;
import cn.anlucky.system.mapper.OprLogsMapper;
import cn.anlucky.system.service.OprLogsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * 操作日志记录 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:46:47
 */
@Service
public class OprLogsServiceImp extends ServiceImpl<OprLogsMapper, OprLogs> implements OprLogsService {


    /**
     * 分页带条件查询OprLogs
     * @param oprLogs
     * @return
     */
    @Override
    public List<OprLogs> pageByParams(OprLogs oprLogs) {
        if (oprLogs == null){
            return this.list();
        }
        LambdaQueryWrapper<OprLogs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(oprLogs.getId()), OprLogs::getId, oprLogs.getId());
        wrapper.eq(Strings.isNotBlank(oprLogs.getTitle()), OprLogs::getTitle, oprLogs.getTitle());
        wrapper.eq(Objects.nonNull(oprLogs.getBusinessType()), OprLogs::getBusinessType, oprLogs.getBusinessType());
        wrapper.eq(Strings.isNotBlank(oprLogs.getMethodName()), OprLogs::getMethodName, oprLogs.getMethodName());
        wrapper.eq(Strings.isNotBlank(oprLogs.getRequestMethod()), OprLogs::getRequestMethod, oprLogs.getRequestMethod());
        wrapper.eq(Strings.isNotBlank(oprLogs.getUsername()), OprLogs::getUsername, oprLogs.getUsername());
        wrapper.eq(Strings.isNotBlank(oprLogs.getUrl()), OprLogs::getUrl, oprLogs.getUrl());
        wrapper.eq(Strings.isNotBlank(oprLogs.getIp()), OprLogs::getIp, oprLogs.getIp());
        wrapper.eq(Strings.isNotBlank(oprLogs.getParam()), OprLogs::getParam, oprLogs.getParam());
        wrapper.eq(Strings.isNotBlank(oprLogs.getResult()), OprLogs::getResult, oprLogs.getResult());
        wrapper.eq(Objects.nonNull(oprLogs.getStatus()), OprLogs::getStatus, oprLogs.getStatus());
        wrapper.eq(Strings.isNotBlank(oprLogs.getErrorMsg()), OprLogs::getErrorMsg, oprLogs.getErrorMsg());
        wrapper.between(Objects.nonNull(oprLogs.getStartTime()), OprLogs::getStartTime, oprLogs.getStartTime(), oprLogs.getStartTime());
        wrapper.eq(Objects.nonNull(oprLogs.getCostTime()), OprLogs::getCostTime, oprLogs.getCostTime());
    return this.list(wrapper);
    }


    /**
     * 修改所有字段为新值
     * @param  oprLogs
     * @return
     */
    @Override
    public boolean updateAllById(OprLogs oprLogs){
        if (oprLogs == null){
        throw new CustomException("参数不能为空");
        }
        if (oprLogs.getId() == null){
        throw new CustomException("id不能为空");
        }
        return this.updateById(oprLogs);
    }

    /**
     * 修改指定字段为新值
     * @param oprLogs
     * @return
     */
    @Override
    public boolean updateSpecifyById(OprLogs oprLogs){
        if (oprLogs == null){
            throw new CustomException("参数不能为空");
        }
        if (oprLogs.getId() == null){
            throw new CustomException("id不能为空");
        }
        LambdaUpdateWrapper<OprLogs> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(OprLogs::getId, oprLogs.getId());
         wrapper.set(Strings.isNotBlank(oprLogs.getTitle()), OprLogs::getTitle, oprLogs.getTitle());
         wrapper.set(Objects.nonNull(oprLogs.getBusinessType()), OprLogs::getBusinessType, oprLogs.getBusinessType());
         wrapper.set(Strings.isNotBlank(oprLogs.getMethodName()), OprLogs::getMethodName, oprLogs.getMethodName());
         wrapper.set(Strings.isNotBlank(oprLogs.getRequestMethod()), OprLogs::getRequestMethod, oprLogs.getRequestMethod());
         wrapper.set(Strings.isNotBlank(oprLogs.getUsername()), OprLogs::getUsername, oprLogs.getUsername());
         wrapper.set(Strings.isNotBlank(oprLogs.getUrl()), OprLogs::getUrl, oprLogs.getUrl());
         wrapper.set(Strings.isNotBlank(oprLogs.getIp()), OprLogs::getIp, oprLogs.getIp());
         wrapper.set(Strings.isNotBlank(oprLogs.getParam()), OprLogs::getParam, oprLogs.getParam());
         wrapper.set(Strings.isNotBlank(oprLogs.getResult()), OprLogs::getResult, oprLogs.getResult());
         wrapper.set(Objects.nonNull(oprLogs.getStatus()), OprLogs::getStatus, oprLogs.getStatus());
         wrapper.set(Strings.isNotBlank(oprLogs.getErrorMsg()), OprLogs::getErrorMsg, oprLogs.getErrorMsg());
         wrapper.set(Objects.nonNull(oprLogs.getStartTime()), OprLogs::getStartTime, oprLogs.getStartTime());
         wrapper.set(Objects.nonNull(oprLogs.getCostTime()), OprLogs::getCostTime, oprLogs.getCostTime());
        return this.update(new OprLogs(),wrapper);
    }
}
