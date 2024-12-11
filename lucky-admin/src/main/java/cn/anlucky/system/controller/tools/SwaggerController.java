package cn.anlucky.system.controller.tools;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/tools")
public class SwaggerController {

    @SaCheckPermission("tools::swagger::list")
    @GetMapping("/swagger")
    public String index(){
        return "redirect:/swagger-ui.html";
    }

}
