package cn.anlucky.system.service.tools.impl;

import cn.anlucky.system.pojo.tools.TablePo;
import cn.anlucky.system.service.tools.TablePoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TablePoServiceImplTest {


    @Autowired
    private TablePoService tablePoService;

    @Test
    void showTableSatus() {
        TablePo tablePo = new TablePo();
        tablePo.setName("users");
        List<TablePo> tablePos = tablePoService.showTableSatus(tablePo);
        System.out.println("tablePos = " + tablePos);
    }

    @Test
    void previewCode() {
        String tableName = "users";
        tablePoService.previewCode(tableName);
    }

}
