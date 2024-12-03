package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.Roles;
import cn.anlucky.system.mapper.RolesMapper;
import cn.anlucky.system.service.RolesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

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


    /**
     * 分页带条件查询Roles
     * @param roles
     * @return
     */
    @Override
    public List<Roles> pageByParams(Roles roles) {
        if (roles == null){
            return this.list();
        }
        LambdaQueryWrapper<Roles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(roles.getId()), Roles::getId, roles.getId());
        wrapper.eq(Strings.isNotBlank(roles.getName()), Roles::getName, roles.getName());
        wrapper.eq(Strings.isNotBlank(roles.getDescription()), Roles::getDescription, roles.getDescription());
        wrapper.between(Objects.nonNull(roles.getCreateTime()), Roles::getCreateTime, roles.getCreateTime(), roles.getCreateTime());
        wrapper.eq(Strings.isNotBlank(roles.getCreateBy()), Roles::getCreateBy, roles.getCreateBy());
        wrapper.between(Objects.nonNull(roles.getUpdateTime()), Roles::getUpdateTime, roles.getUpdateTime(), roles.getUpdateTime());
        wrapper.eq(Strings.isNotBlank(roles.getUpdateBy()), Roles::getUpdateBy, roles.getUpdateBy());
    return this.list(wrapper);
    }


    /**
     * 修改所有字段为新值
     * @param  roles
     * @return
     */
    @Override
    public boolean updateAllById(Roles roles){
        if (roles == null){
        throw new CustomException("参数不能为空");
        }
        if (roles.getId() == null){
        throw new CustomException("id不能为空");
        }
        return this.updateById(roles);
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
}
