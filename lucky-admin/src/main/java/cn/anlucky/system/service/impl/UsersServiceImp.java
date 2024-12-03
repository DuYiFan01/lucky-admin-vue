package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.pojo.Users;
import cn.anlucky.system.mapper.UsersMapper;
import cn.anlucky.system.service.UsersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * 用户表 服务实现类
 *
 * @author yifan.du
 * @since 2024-12-03 14:16:29
 */
@Service
public class UsersServiceImp extends ServiceImpl<UsersMapper, Users> implements UsersService {


    /**
     * 分页带条件查询Users
     * @param users
     * @return
     */
    @Override
    public List<Users> pageByParams(Users users) {
        if (users == null){
            return this.list();
        }
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(users.getId()), Users::getId, users.getId());
        wrapper.eq(Strings.isNotBlank(users.getUsername()), Users::getUsername, users.getUsername());
        wrapper.eq(Strings.isNotBlank(users.getPassword()), Users::getPassword, users.getPassword());
        wrapper.eq(Strings.isNotBlank(users.getEmail()), Users::getEmail, users.getEmail());
        wrapper.between(Objects.nonNull(users.getCreateTime()), Users::getCreateTime, users.getCreateTime(), users.getCreateTime());
        wrapper.eq(Strings.isNotBlank(users.getCreateBy()), Users::getCreateBy, users.getCreateBy());
        wrapper.between(Objects.nonNull(users.getUpdateTime()), Users::getUpdateTime, users.getUpdateTime(), users.getUpdateTime());
        wrapper.eq(Strings.isNotBlank(users.getUpdateBy()), Users::getUpdateBy, users.getUpdateBy());
        wrapper.eq(Objects.nonNull(users.getDelFlag()), Users::getDelFlag, users.getDelFlag());
    return this.list(wrapper);
    }


    /**
     * 修改所有字段为新值
     * @param  users
     * @return
     */
    @Override
    public boolean updateAllById(Users users){
        if (users == null){
        throw new CustomException("参数不能为空");
        }
        if (users.getId() == null){
        throw new CustomException("id不能为空");
        }
        return this.updateById(users);
    }

    /**
     * 修改指定字段为新值
     * @param users
     * @return
     */
    @Override
    public boolean updateSpecifyById(Users users){
        if (users == null){
            throw new CustomException("参数不能为空");
        }
        if (users.getId() == null){
            throw new CustomException("id不能为空");
        }
        LambdaUpdateWrapper<Users> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(Users::getId, users.getId());
         wrapper.set(Strings.isNotBlank(users.getUsername()), Users::getUsername, users.getUsername());
         wrapper.set(Strings.isNotBlank(users.getPassword()), Users::getPassword, users.getPassword());
         wrapper.set(Strings.isNotBlank(users.getEmail()), Users::getEmail, users.getEmail());
        return this.update(new Users(),wrapper);
    }
}
