package cn.anlucky.system.utils;

import cn.anlucky.system.config.CodeConfig;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VelocityUtils {





    public static List<String> getTemplateList() {
        List<String> list = new ArrayList<>();
        list.add("templates/java/controller.java.vm");
        list.add("templates/java/entity.java.vm");
        list.add("templates/java/mapper.java.vm");
        list.add("templates/java/service.java.vm");
        list.add("templates/java/serviceImpl.java.vm");
        list.add("templates/js/index.js.vm");
        list.add("templates/sql/menus.sql.vm");
        list.add("templates/vue/index.vue.vm");
        list.add("templates/xml/mapper.xml.vm");
        return list;
    }



}
