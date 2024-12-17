package cn.anlucky.system.utils;

import cn.anlucky.system.gen.CodeGenerationConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VelocityUtils {

    public static List<String> getTemplateList() {
        List<String> list = new ArrayList<>();
        list.add("templates/java/entity.java.vm");
        list.add("templates/java/mapper.java.vm");
        list.add("templates/java/service.java.vm");
        list.add("templates/java/serviceImpl.java.vm");
        list.add("templates/java/controller.java.vm");
        list.add("templates/xml/mapper.xml.vm");
        list.add("templates/vue/index.vue.vm");
        list.add("templates/js/index.js.vm");
        list.add("templates/sql/menus.sql.vm");
        return list;
    }



}
