package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.Menus;
import cn.anlucky.system.mapper.MenusMapper;
import cn.anlucky.system.service.MenusService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * 菜单表 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:43:01
 */
@Service
public class MenusServiceImp extends ServiceImpl<MenusMapper, Menus> implements MenusService {


    /**
     * 分页带条件查询Menus
     * @param menus
     * @return
     */
    @Override
    public List<Menus> pageByParams(Menus menus) {
        if (menus == null){
            return this.list();
        }
        LambdaQueryWrapper<Menus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(menus.getId()), Menus::getId, menus.getId());
        wrapper.eq(Strings.isNotBlank(menus.getName()), Menus::getName, menus.getName());
        wrapper.eq(Strings.isNotBlank(menus.getTitle()), Menus::getTitle, menus.getTitle());
        wrapper.eq(Objects.nonNull(menus.getParentId()), Menus::getParentId, menus.getParentId());
        wrapper.eq(Objects.nonNull(menus.getOrderNum()), Menus::getOrderNum, menus.getOrderNum());
        wrapper.eq(Strings.isNotBlank(menus.getPath()), Menus::getPath, menus.getPath());
        wrapper.eq(Strings.isNotBlank(menus.getComponent()), Menus::getComponent, menus.getComponent());
        wrapper.eq(Strings.isNotBlank(menus.getQuery()), Menus::getQuery, menus.getQuery());
        wrapper.eq(Objects.nonNull(menus.getIsFrame()), Menus::getIsFrame, menus.getIsFrame());
        wrapper.eq(Objects.nonNull(menus.getIsCache()), Menus::getIsCache, menus.getIsCache());
        wrapper.eq(Strings.isNotBlank(menus.getMenuType()), Menus::getMenuType, menus.getMenuType());
        wrapper.eq(Objects.nonNull(menus.getVisible()), Menus::getVisible, menus.getVisible());
        wrapper.eq(Strings.isNotBlank(menus.getRoles()), Menus::getRoles, menus.getRoles());
        wrapper.eq(Strings.isNotBlank(menus.getIcon()), Menus::getIcon, menus.getIcon());
        wrapper.between(Objects.nonNull(menus.getCreateTime()), Menus::getCreateTime, menus.getCreateTime(), menus.getCreateTime());
        wrapper.eq(Strings.isNotBlank(menus.getCreateBy()), Menus::getCreateBy, menus.getCreateBy());
        wrapper.between(Objects.nonNull(menus.getUpdateTime()), Menus::getUpdateTime, menus.getUpdateTime(), menus.getUpdateTime());
        wrapper.eq(Strings.isNotBlank(menus.getUpdateBy()), Menus::getUpdateBy, menus.getUpdateBy());
        wrapper.eq(Strings.isNotBlank(menus.getRemark()), Menus::getRemark, menus.getRemark());
    return this.list(wrapper);
    }


    /**
     * 修改所有字段为新值
     * @param  menus
     * @return
     */
    @Override
    public boolean updateAllById(Menus menus){
        if (menus == null){
        throw new CustomException("参数不能为空");
        }
        if (menus.getId() == null){
        throw new CustomException("id不能为空");
        }
        return this.updateById(menus);
    }

    /**
     * 修改指定字段为新值
     * @param menus
     * @return
     */
    @Override
    public boolean updateSpecifyById(Menus menus){
        if (menus == null){
            throw new CustomException("参数不能为空");
        }
        if (menus.getId() == null){
            throw new CustomException("id不能为空");
        }
        LambdaUpdateWrapper<Menus> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(Menus::getId, menus.getId());
         wrapper.set(Strings.isNotBlank(menus.getName()), Menus::getName, menus.getName());
         wrapper.set(Strings.isNotBlank(menus.getTitle()), Menus::getTitle, menus.getTitle());
         wrapper.set(Objects.nonNull(menus.getParentId()), Menus::getParentId, menus.getParentId());
         wrapper.set(Objects.nonNull(menus.getOrderNum()), Menus::getOrderNum, menus.getOrderNum());
         wrapper.set(Strings.isNotBlank(menus.getPath()), Menus::getPath, menus.getPath());
         wrapper.set(Strings.isNotBlank(menus.getComponent()), Menus::getComponent, menus.getComponent());
         wrapper.set(Strings.isNotBlank(menus.getQuery()), Menus::getQuery, menus.getQuery());
         wrapper.set(Objects.nonNull(menus.getIsFrame()), Menus::getIsFrame, menus.getIsFrame());
         wrapper.set(Objects.nonNull(menus.getIsCache()), Menus::getIsCache, menus.getIsCache());
         wrapper.set(Strings.isNotBlank(menus.getMenuType()), Menus::getMenuType, menus.getMenuType());
         wrapper.set(Objects.nonNull(menus.getVisible()), Menus::getVisible, menus.getVisible());
         wrapper.set(Strings.isNotBlank(menus.getRoles()), Menus::getRoles, menus.getRoles());
         wrapper.set(Strings.isNotBlank(menus.getIcon()), Menus::getIcon, menus.getIcon());
         wrapper.set(Strings.isNotBlank(menus.getRemark()), Menus::getRemark, menus.getRemark());
        return this.update(new Menus(),wrapper);
    }
}
