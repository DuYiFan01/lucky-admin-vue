package cn.anlucky.system.vo;


import lombok.Data;

import java.util.List;

/**
 * 分配用户显示队形
 */
@Data
public class AuthUserVo {
    /**
     * 用户列表
     */
    private List<UserVo> userList;
    /**
     * 已分配用户ID
     */
    private List<Long> authUserIds;
}
