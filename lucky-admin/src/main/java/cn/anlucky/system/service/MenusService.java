package cn.anlucky.system.service;

import cn.anlucky.system.pojo.Menus;
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
     * 分页带条件查询Menus
     * @param menus
     * @return
     */
    public List<Menus> pageByParams(Menus menus);

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

}
