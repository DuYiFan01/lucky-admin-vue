package cn.anlucky.system.controller;



import cn.anlucky.system.utils.AvatarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 生成头像
 */
@RequestMapping("/user")
@RestController
public class AvatarController {

    @Autowired
    private AvatarUtils avatarUtils;

    /**
     * 生成头像
     *
     * @param name
     * @param shape        1:圆形，0：方形
     * @param outputStream
     * @throws IOException
     */
    @GetMapping(value = "/{name}/{shape}", produces = MediaType.IMAGE_PNG_VALUE)
    public void getNameAvatar(@PathVariable String name, @PathVariable String shape, OutputStream outputStream) throws IOException {
        boolean isRound = "1".equals(shape);
        byte[] avatarBytes = avatarUtils.generateAvatar(name, isRound);
        outputStream.write(avatarBytes);
        outputStream.flush();
    }
}
