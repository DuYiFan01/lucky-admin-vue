package cn.anlucky.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为角色分配用户对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveGrantVo {
    private Long roleId;
    private Long[] userIds;
}
