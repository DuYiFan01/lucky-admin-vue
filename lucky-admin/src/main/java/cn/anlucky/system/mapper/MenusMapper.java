package cn.anlucky.system.mapper;

import cn.anlucky.system.pojo.system.Menus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *
 * 菜单表 Mapper 接口
 *
 * @author yifan.du
 * @since 2024-12-03 14:43:01
 */
public interface MenusMapper extends BaseMapper<Menus> {

    /**
     * 根据父级菜单ID获取当前用户的其子菜单和目录
     * @param parentId
     * @param userId
     * @return
     */
    List<Menus> getMenusByParentId(Long parentId, Long userId);

    /**
     * 获取当前用户的菜单树
     * @param userId
     * @return
     */
    List<Menus> getMenusTreeByUserId(Long userId);
}
