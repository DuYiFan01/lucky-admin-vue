package cn.anlucky.system.service;

import cn.anlucky.system.pojo.Roles;
import cn.anlucky.system.vo.AuthRoleVo;
import cn.anlucky.system.vo.AuthUserVo;
import cn.anlucky.system.vo.SaveGrantUserVo;
import cn.anlucky.system.vo.SaveGrantVo;
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

    /**
     * 添加一个新角色包括他的权限
     *
     * @param roles
     */
    public void saveRoleAndRoleMenus(Roles roles);

    /**
     * 批量删除角色
     * @param ids
     */
    public void deleteRoelsBatch(List<Long> ids);

    /**
     * 根据角色ID去查当前角色已经绑定了哪些用户
     *
     * @param roleId
     * @return
     */
    public AuthUserVo getUsersByRoleId(Long roleId);
    /**
     * 根据用户id获取用户绑定了哪些角色
     * @param userId
     * @return
     */
    public AuthRoleVo getRolesByUserId(Long userId);

    /**
     * 为角色分配用户
     * @param saveGrantVo
     */
    public void saveGrant(SaveGrantVo saveGrantVo);

    /**
     * 为用户分配角色
     * @param saveGrantUserVo
     */
    public void saveGrantByUser(SaveGrantUserVo saveGrantUserVo);
}
