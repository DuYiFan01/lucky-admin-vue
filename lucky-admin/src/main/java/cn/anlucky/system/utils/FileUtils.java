package cn.anlucky.system.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    /**
     * 指定内容变为文件
     */
    public static byte[] writeFile(List<ZipEntry> list) {
        String content = "hello world";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (int i = 0; i < list.size(); i++) {
            // zip.putNextEntry(list.get(i));
        }


        ZipEntry zipEntry = new ZipEntry("cn/anlucky/file1.txt");
        try {
            zip.putNextEntry(zipEntry);
            IOUtils.write(content, zip);
            zip.flush();
            zip.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        // IOUtils.write(writeFile(), new FileOutputStream("D:/test.zip") );
    }



}
