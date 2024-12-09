package cn.anlucky.system.service.system;

import cn.anlucky.system.pojo.system.RolesMenus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 角色和菜单关联表 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:44:49
 */
public interface RolesMenusService extends IService<RolesMenus> {

    /**
     * 根据角色ID查询菜单ID
     * @param roleId
     * @return
     */
    public List<RolesMenus> getRolesMenusByRoleId(Long roleId);

}
