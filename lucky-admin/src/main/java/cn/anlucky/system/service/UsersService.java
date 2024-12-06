package cn.anlucky.system.service;

import cn.anlucky.system.pojo.Users;
import cn.anlucky.system.vo.AuthRoleVo;
import cn.anlucky.system.vo.SaveGrantUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 用户表 服务类
 *
 * @author yifan.du
 * @since 2024-12-03 14:16:29
 */
public interface UsersService extends IService<Users> {

    /**
     * 分页带条件查询Users
     * @param users
     * @return
     */
    public List<Users> pageByParams(Users users);

    /**
     * 修改所有字段为新值
     * @param  users
     * @return
     */
    public boolean updateAllById(Users users);

    /**
     * 修改指定字段为新值
     * @param users
     * @return
     */
    public boolean updateSpecifyById(Users users);

}
