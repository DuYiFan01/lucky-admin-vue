package cn.anlucky.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordVo {

    private String id;

    private String oldPassword;

    private String newPassword;


}
