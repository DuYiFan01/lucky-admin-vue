package cn.anlucky.system.service.system.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.system.RolesMenus;
import cn.anlucky.system.mapper.RolesMenusMapper;
import cn.anlucky.system.service.system.RolesMenusService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * 角色和菜单关联表 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:44:49
 */
@Service
public class RolesMenusServiceImp extends ServiceImpl<RolesMenusMapper, RolesMenus> implements RolesMenusService {

    /**
     * 根据角色ID查询菜单ID
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolesMenus> getRolesMenusByRoleId(Long roleId) {
        if (roleId == null){
            throw new CustomException("参数不能为空");
        }
        LambdaQueryWrapper<RolesMenus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolesMenus::getRoleId, roleId);
        List<RolesMenus> list = this.list(wrapper);
        return list;
    }
}
