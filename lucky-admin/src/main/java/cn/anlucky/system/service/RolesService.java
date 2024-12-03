package cn.anlucky.system.service;

import cn.anlucky.system.pojo.Roles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 角色表 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:37:12
 */
public interface RolesService extends IService<Roles> {

    /**
     * 分页带条件查询Roles
     * @param roles
     * @return
     */
    public List<Roles> pageByParams(Roles roles);

    /**
     * 修改所有字段为新值
     * @param  roles
     * @return
     */
    public boolean updateAllById(Roles roles);

    /**
     * 修改指定字段为新值
     * @param roles
     * @return
     */
    public boolean updateSpecifyById(Roles roles);

}
