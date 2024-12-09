package cn.anlucky.system.service.system.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.system.Menus;
import cn.anlucky.system.mapper.MenusMapper;
import cn.anlucky.system.service.system.MenusService;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.vo.MetaVo;
import cn.anlucky.system.vo.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * 获取路由树
     */
    @Override
    public List<RouterVo> getRouterTree() {
        Long loginId = Sa.getLoginIdAsLong();
        List<Menus> menus = this.baseMapper.getMenusTreeByUserId(loginId);
        return buildRouter( menus);
    }
    /**
     * 获取路由树
     * @param menus
     * @return
     */
    private List<RouterVo> buildRouter(List<Menus> menus){
        List<RouterVo> routers = new ArrayList<>();
        for (Menus menu : menus) {
            RouterVo routerVo = new RouterVo();
            routerVo.setPath(menu.getPath());
            routerVo.setName(menu.getName());
            routerVo.setHidden(menu.getVisible() == 0);
            routerVo.setAlwaysShow(getAlwaysShow(menu));
            routerVo.setMeta(new MetaVo(menu.getTitle(), menu.getIcon(),menu.getIsCache()==0, getLink(menu)));
            routerVo.setRedirect(getRedirect(menu));
            routerVo.setComponent(getComponent(menu));
            routerVo.setChildren(buildRouter(menu.getChildren()));
            routers.add(routerVo);
        }
        return routers;
    }

    /**
     * 获取组件
     * @param menu
     * @return
     */
    private String getComponent(Menus menu){
        String component = "Layout";
        // 列举所有不是Layout组件的情况，否则直接返回Layout
        if (menu.getMenuType().equals("M") && menu.getIsFrame() != 1 && menu.getParentId() !=0){
            component = "ParentView";
        }
        if (menu.getMenuType().equals("C")){
            component = menu.getComponent();
        }
        return component;
    }
    /**
     * 获取redirect
     * @param menu
     * @return
     */
    private String getRedirect(Menus menu){
        // 如果是外链，并且是目录，那么直接返回null
        if (menu.getIsFrame() == 1 && menu.getMenuType().equals("M")){
            return null;
        }
        // 如果是目录，并且不是外链，那么返回noRedirect
        if (menu.getMenuType().equals("M") && menu.getIsFrame() == 0){
            return "noRedirect";
        }
        // 如果是菜单，那么返回null
        if (menu.getMenuType().equals("C")){
            return null;
        }
        return "noRedirect";
    }

    /**
     * 获取外链地址
     * @param menu
     * @return
     */
    private String getLink(Menus menu){
        if (menu.getIsFrame() == 1){
            // 如果是外链
            return menu.getPath();
        }
        return null;
    }

    /**
     * 获取alwaysShow
     * @param menu
     * @return
     */
    private boolean getAlwaysShow(Menus menu){
        // if (menu.getIsFrame() == 1){
        //     return true;
        // }else {
        //     return false;
        // }
        return false;
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
        // 判断菜单名称是否存在
        LambdaQueryWrapper<Menus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menus::getParentId, menus.getParentId());
        wrapper.eq(Menus::getName, menus.getName());
        wrapper.eq(Menus::getTitle, menus.getTitle());
        wrapper.ne(Menus::getId, menus.getId());
        if (this.count(wrapper) > 0) {
            throw new CustomException("菜单已存在");
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
        // 判断菜单名称是否存在
        LambdaQueryWrapper<Menus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menus::getParentId, menus.getParentId());
        queryWrapper.eq(Menus::getName, menus.getName());
        queryWrapper.eq(Menus::getTitle, menus.getTitle());
        queryWrapper.ne(Menus::getId, menus.getId());

        if (this.count(queryWrapper) > 0) {
            throw new CustomException("菜单已存在");
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

    /**
     * 新增Menus
     *
     * @param menus
     * @return
     */
    @Override
    public boolean saveMenus(Menus menus) {
        if (menus == null) {
            throw new CustomException("参数不能为空");
        }
        // 判断菜单名称是否存在
        LambdaQueryWrapper<Menus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menus::getParentId, menus.getParentId());
        wrapper.eq(Menus::getName, menus.getName());
        wrapper.eq(Menus::getTitle, menus.getTitle());
        if (this.count(wrapper) > 0) {
            throw new CustomException("菜单已存在");
        }
        return this.save(menus);
    }

    /**
     * 获取菜单树
     */
    @Override
    public List<Menus> getMenusTree(Menus menus) {
        LambdaQueryWrapper<Menus> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Menus::getOrderNum);
        if (menus == null){
            return this.list(wrapper);
        }
        wrapper.eq( menus.getTitle() != null && !menus.getTitle().isEmpty(),Menus::getTitle, menus.getTitle());
        List<Menus> list = this.list(wrapper);
        return list;
    }
}
