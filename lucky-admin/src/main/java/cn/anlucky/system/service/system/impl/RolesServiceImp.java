package cn.anlucky.system.service.system.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.system.Roles;
import cn.anlucky.system.mapper.RolesMapper;
import cn.anlucky.system.pojo.system.RolesMenus;
import cn.anlucky.system.pojo.system.UserRoles;
import cn.anlucky.system.pojo.system.Users;
import cn.anlucky.system.service.system.RolesMenusService;
import cn.anlucky.system.service.system.RolesService;
import cn.anlucky.system.service.system.UserRolesService;
import cn.anlucky.system.service.system.UsersService;
import cn.anlucky.system.vo.*;
import cn.anlucky.utils.SaTokenDaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * 角色表 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:37:12
 */
@Service
public class RolesServiceImp extends ServiceImpl<RolesMapper, Roles> implements RolesService {

    @Autowired
    private RolesMenusService rolesMenusService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRolesService userRolesService;



    /**
     * 分页带条件查询Roles
     * @param roles
     * @return
     */
    @Override
    public List<Roles> pageByParams(Roles roles) {
        List<Roles> list = null;
        if (roles == null){
            list = this.list();
        }else{
            LambdaQueryWrapper<Roles> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Objects.nonNull(roles.getId()), Roles::getId, roles.getId());
            wrapper.eq(Strings.isNotBlank(roles.getName()), Roles::getName, roles.getName());
            wrapper.eq(Strings.isNotBlank(roles.getDescription()), Roles::getDescription, roles.getDescription());
            wrapper.between(Objects.nonNull(roles.getCreateTime()), Roles::getCreateTime, roles.getCreateTime(), roles.getCreateTime());
            wrapper.eq(Strings.isNotBlank(roles.getCreateBy()), Roles::getCreateBy, roles.getCreateBy());
            wrapper.between(Objects.nonNull(roles.getUpdateTime()), Roles::getUpdateTime, roles.getUpdateTime(), roles.getUpdateTime());
            wrapper.eq(Strings.isNotBlank(roles.getUpdateBy()), Roles::getUpdateBy, roles.getUpdateBy());
            list = this.list(wrapper);
        }
        for (int i = 0; i < list.size(); i++) {
            Long id = list.get(i).getId(); // 角色ID
            List<RolesMenus> menus = rolesMenusService.getRolesMenusByRoleId(id);
            List<Long> menusList = new ArrayList<>();
            for (int j = 0; j < menus.size(); j++) {
                menusList.add(menus.get(j).getMenuId());
            }
            list.get(i).setMenus(menusList);
        }
        return list;
    }


    /**
     * 修改所有字段为新值
     * @param  roles
     * @return
     */
    @Transactional
    @Override
    public boolean updateAllById(Roles roles){
        if (roles == null){
            throw new CustomException("参数不能为空");
        }
        if (roles.getId() == null){
            throw new CustomException("id不能为空");
        }
        // 删除当前角色的所有权限，然后重新添加
        rolesMenusService.remove(new LambdaQueryWrapper<RolesMenus>().eq(RolesMenus::getRoleId, roles.getId()));

        // 添加角色包含的权限到roles_menus表中
        ArrayList<RolesMenus> list = new ArrayList<>();
        for (int i = 0; i < roles.getMenus().size(); i++) {
            RolesMenus rolesMenus = new RolesMenus();
            rolesMenus.setRoleId(Long.valueOf(roles.getId()));
            rolesMenus.setMenuId(roles.getMenus().get(i));
            list.add(rolesMenus);
        }

        rolesMenusService.saveBatch(list);
        this.updateById(roles);
        // 删除当前角色缓存的权限
        SaTokenDaoUtils.deleteObjectKey(SaTokenDaoUtils.PERMISSIONS_CACHE + roles.getId());
        return true;
    }

    /**
     * 修改指定字段为新值
     * @param roles
     * @return
     */
    @Override
    public boolean updateSpecifyById(Roles roles){
        if (roles == null){
            throw new CustomException("参数不能为空");
        }
        if (roles.getId() == null){
            throw new CustomException("id不能为空");
        }
        LambdaUpdateWrapper<Roles> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(Roles::getId, roles.getId());
         wrapper.set(Strings.isNotBlank(roles.getName()), Roles::getName, roles.getName());
         wrapper.set(Strings.isNotBlank(roles.getDescription()), Roles::getDescription, roles.getDescription());
        return this.update(new Roles(),wrapper);
    }

    /**
     * 添加一个新角色包括他的权限
     *
     * @param roles
     */
    @Transactional
    @Override
    public void saveRoleAndRoleMenus(Roles roles) {
        if (roles == null) {
            throw new CustomException("参数不能为空");
        }
        boolean save = this.save(roles);
        if (roles.getId()==null){
            throw new CustomException("添加角色失败");
        }
        if (roles.getMenus()!=null){
            // 添加角色包含的权限到roles_menus表中
            ArrayList<RolesMenus> list = new ArrayList<>();
            for (int i = 0; i < roles.getMenus().size(); i++) {
                RolesMenus rolesMenus = new RolesMenus();
                rolesMenus.setRoleId(roles.getId());
                rolesMenus.setMenuId(roles.getMenus().get(i));
                list.add(rolesMenus);
            }
            boolean b = rolesMenusService.saveBatch(list);
        }
    }

    /**
     * 批量删除角色
     *
     * @param ids
     */
    @Override
    public void deleteRoelsBatch(List<Long> ids) {
        // 查询当前角色是否有用户绑定，若有用户绑定不允许删除，若没有用户绑定则删除角色并删除角色绑定的权限
        for (int i = 0; i < ids.size(); i++) {
            LambdaQueryWrapper<UserRoles> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRoles::getRoleId, ids.get(i));
            // 判断是否有用户绑定
            long count = userRolesService.count(wrapper);
            if (count!=0){
                throw new CustomException("角色下有用户绑定，不允许删除");
            }
            // 判断是否有菜单绑定，不需要可以注释掉这个判断，后续依然会删除角色下的所有菜单
            LambdaQueryWrapper<RolesMenus> roleMenusWrapper = new LambdaQueryWrapper<RolesMenus>().eq(RolesMenus::getRoleId, ids.get(i));
            count = rolesMenusService.count(roleMenusWrapper);
            if (count > 0) {
                throw new CustomException("角色下有菜单绑定，不允许删除");
            }
            // 删除角色绑定的用户
            userRolesService.remove(wrapper);
            // 删除角色绑定的菜单
            rolesMenusService.remove(roleMenusWrapper);
            // 删除角色
            this.removeById(ids.get(i));
            // 删除当前角色的权限缓存
            SaTokenDaoUtils.deleteObjectKey(SaTokenDaoUtils.PERMISSIONS_CACHE + ids.get(i));
        }
    }

    /**
     * 根据角色ID去查当前角色已经绑定了哪些用户
     *
     * @param roleId
     * @return
     */
    @Override
    public AuthUserVo getUsersByRoleId(Long roleId) {
        if (roleId == null) {
            throw new CustomException("参数不能为空");
        }
        AuthUserVo authUserVo = new AuthUserVo();
        List<Users> list = usersService.list();
        List<UserVo> userVos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            userVos.add(new UserVo(list.get(i).getId(), list.get(i).getUsername(), list.get(i).getEmail()));
        }
        authUserVo.setUserList(userVos);
        LambdaQueryWrapper<UserRoles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoles::getRoleId, roleId);
        List<UserRoles> userIds = userRolesService.list(wrapper);
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            ids.add(userIds.get(i).getUserId());
        }
        authUserVo.setAuthUserIds(ids);
        return authUserVo;
    }

    /**
     * 根据用户id获取用户绑定了哪些角色
     *
     * @param userId
     * @return
     */
    @Override
    public AuthRoleVo getRolesByUserId(Long userId) {
        if (userId == null){
            throw new CustomException("用户id不能为空");
        }
        // 查询所有角色 和 查询当前用户拥有的角色 封装为AuthRoleVo
        // 查询所有角色
        List<Roles> roles = this.list();

        LambdaQueryWrapper<UserRoles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoles::getUserId, userId);

        List<UserRoles> userRoles = userRolesService.list(wrapper);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < userRoles.size(); i++) {
            ids.add(userRoles.get(i).getRoleId());
        }
        return new AuthRoleVo(roles, ids);
    }

    /**
     * 为角色分配用户
     * @param saveGrantVo
     */
    @Transactional
    @Override
    public void saveGrant(SaveGrantVo saveGrantVo) {
        if (saveGrantVo == null){
            throw new CustomException("参数不能为空");
        }
        if (saveGrantVo.getRoleId() == null){
            throw new CustomException("角色ID不能为空");
        }
        // 先删除当前角色分配的用户，再为角色进行添加用户
        LambdaQueryWrapper<UserRoles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoles::getRoleId, saveGrantVo.getRoleId());
        userRolesService.remove(wrapper);
        // 为角色添加用户
        ArrayList<UserRoles> list = new ArrayList<>();
        for (int i = 0; i < saveGrantVo.getUserIds().length; i++) {
            UserRoles userRoles = new UserRoles();
            userRoles.setRoleId(saveGrantVo.getRoleId());
            userRoles.setUserId(saveGrantVo.getUserIds()[i]);
            list.add(userRoles);
        }
        boolean b = userRolesService.saveBatch(list);
        // todo: 搜索当前角色缓存的用户，并将其全部失效，避免缓存数据不一致 同一时间内失效大量缓存可能会导致缓存雪崩
        List<String> rolesCache = SaTokenDaoUtils.searchData(SaTokenDaoUtils.ROLES_CACHE, 0L, usersService.count());
        rolesCache.forEach(roleCache -> {
            SaTokenDaoUtils.deleteObjectKey(roleCache);
        });
    }
    /**
     * 为用户分配角色
     *
     * @param saveGrantUserVo
     */
    @Transactional
    @Override
    public void saveGrantByUser(SaveGrantUserVo saveGrantUserVo) {
        if (saveGrantUserVo == null){
            throw new CustomException("参数不能为空");
        }
        if (saveGrantUserVo.getUserId() == null){
            throw new CustomException("用户ID不能为空");
        }
        // 先删除当前用户拥有的角色，再为当前用户添加角色
        LambdaQueryWrapper<UserRoles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoles::getUserId, saveGrantUserVo.getUserId());
        userRolesService.remove(wrapper);
        // 为角色添加用户
        ArrayList<UserRoles> list = new ArrayList<>();

        for (int i = 0; i < saveGrantUserVo.getRoleIds().length; i++) {
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(saveGrantUserVo.getUserId());
            userRoles.setRoleId(saveGrantUserVo.getRoleIds()[i]);
            list.add(userRoles);
        }
        boolean b = userRolesService.saveBatch(list);
        // 将当前用户的缓存失效
        SaTokenDaoUtils.deleteObjectKey(SaTokenDaoUtils.ROLES_CACHE + saveGrantUserVo.getUserId());
    }
}
