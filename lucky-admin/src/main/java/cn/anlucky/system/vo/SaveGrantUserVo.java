package cn.anlucky.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveGrantUserVo {

    private Long userId;

    private Long[] roleIds;
}



