package cn.anlucky.system.utils;

import org.apache.logging.log4j.util.Strings;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperties(String name) {
        try {
            Properties properties = new Properties();
            // 使用InPutStream流读取properties文件
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(name);
            properties.load(in);
            return properties;
        } catch (Exception ex) {
            return null;
        }
    }
    public static Object getPropertyValue(String name, String key) {

        return getProperties(name).get(key);
    }

    public static String getStopServicePath(String name) {
        if (Strings.isNotEmpty(name)) {
            Properties properties = PropertiesUtil.getProperties(name);
            String path = null;
            if (properties != null) {
                path = properties.get("deployServicePath").toString() + "/stopService.bat";
            }
            return path;
        } else
            return null;
        //Properties properties = PropertiesUtil.getProperties("Htl2Application.properties");

    }
}
