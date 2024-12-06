package cn.anlucky.system.service;

import cn.anlucky.system.pojo.Menus;
import cn.anlucky.system.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 菜单表 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:43:01
 */
public interface MenusService extends IService<Menus> {

    /**
     * 获取路由树
     */
    public List<RouterVo> getRouterTree();


    /**
     * 修改所有字段为新值
     * @param  menus
     * @return
     */
    public boolean updateAllById(Menus menus);

    /**
     * 修改指定字段为新值
     * @param menus
     * @return
     */
    public boolean updateSpecifyById(Menus menus);

    /**
     * 新增Menus
     * @param menus
     * @return
     */
    public boolean saveMenus(Menus menus);

    /**
     * 获取菜单树
     */
    public List<Menus> getMenusTree(Menus menus);


}
