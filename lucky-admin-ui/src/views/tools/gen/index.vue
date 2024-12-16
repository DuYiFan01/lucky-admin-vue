<template>
    <div class="user-management">
        <div v-permission="['tools::gen::list']" class="search-bar">
            <div class="grid-item">
                <span>
                    表名称:
                </span>
                <el-input v-model="searchForm.name" placeholder="请输入表名称" style="width: 200px;" />
            </div>
            <div class="grid-item">
                <span>
                    表描述:
                </span>
                <el-input v-model="searchForm.comment" placeholder="请输入表描述" style="width: 200px;" />
            </div>
            <div class="grid-item">
                <span>
                    实体名称:
                </span>
                <el-input v-model="searchForm.entity" placeholder="请输入表描述" style="width: 200px;" />
            </div>
            <div class="grid-item">
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
        </div>
        <div v-permission="['tools::gen::export', 'tools::gen::list']" class="button-bar">
            <el-button v-permission="['tools::gen::export']" :type="buttonBar.exportType" :size="buttonBar.size"
                :plain="buttonBar.plain" @click="handleExport">导出</el-button>
            <el-button v-permission="['tools::gen::list']" :type="buttonBar.reFreshType" :size="buttonBar.size"
                :plain="buttonBar.plain" @click="handleRefresh">刷新</el-button>
        </div>
        <el-table v-loading.fullscreen.lock="tableLoading" :data="tableData" style="width: 100%" border max-height="560"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" v-bind="columnProps" />
            <el-table-column type="index" width="50" label="序号" v-bind="columnProps" />
            <el-table-column prop="name" label="表名称" v-bind="columnProps" />
            <el-table-column prop="comment" label="表描述" v-bind="columnProps" />
            <el-table-column prop="entity" label="实体名称" v-bind="columnProps" />
            <el-table-column prop="createTime" label="创建时间" v-bind="columnProps" />
            <el-table-column prop="updateTime" label="更新时间" v-bind="columnProps" />
            <el-table-column v-if="checkPermission(['tools::gen::list'])" label="操作" width="200" fixed="right"
                :header-align="tableConfig.headerAlign" align="center">
                <template slot-scope="scope">
                    <el-button v-permission="['tools::gen::list']" :size="toolBar.size" :type="toolBar.updateType"
                        icon="el-icon-view" @click="handlePreview(scope.row)">预览代码</el-button>
                    <el-button v-permission="['tools::gen::list']" :size="toolBar.size" :type="toolBar.updateType"
                        icon="el-icon-download" @click="handleAssRole(scope.row)">下载代码</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination">
            <el-pagination :current-page="currentPage" :page-sizes="[1, 10, 20, 50, 100]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
        </div>
        <!-- 预览界面 -->
        <el-dialog :title="preview.title" :visible.sync="preview.open" width="80%" top="5vh" append-to-body
            >
            <el-tabs v-model="preview.activeName" class="scrollbar">
                <el-tab-pane v-for="(value, key) in preview.data"
                    :label="key.substring(key.lastIndexOf('/') + 1, key.indexOf('.vm'))"
                    :name="key.substring(key.lastIndexOf('/') + 1, key.indexOf('.vm'))" :key="key">
                    <el-link :underline="false" icon="el-icon-document-copy" v-clipboard:copy="value"
                        v-clipboard:success="clipboardSuccess" style="float:right">复制</el-link>
                    <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
                </el-tab-pane>
            </el-tabs>
        </el-dialog>
    </div>
</template>


<script>
import { tableList, previewCode } from '@/api/tools/gen'
import hljs from 'highlight.js/lib/core';
import 'highlight.js/styles/github.css';
hljs.registerLanguage("java", require("highlight.js/lib/languages/java"));
hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("html", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("vue", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("javascript", require("highlight.js/lib/languages/javascript"));
hljs.registerLanguage("sql", require("highlight.js/lib/languages/sql"));

export default {
    data() {
        return {
            tableLoading: false,
            preview: {
                title: '',
                open: false,
                activeName: '',
                data: [],
            },
            // 表格样式设置
            tableConfig: {
                tooltip: true, // 表格内容长度过长时鼠标悬浮显示
                columnWidth: '180px', // 表格宽度
                align: 'center', // 内容对齐方式
                headerAlign: 'center' // 标题对齐方式
            },
            // 当前页码
            currentPage: 1,
            // 页码显示条数
            pageSize: 10,
            // 数据总条数
            total: 0,
            // 表格数据
            tableData: [],
            // 搜索条件
            searchForm: {
            },
            // 复选框选择的主键
            selectedIds: [],
        }
    },
    computed: {
        // 计算属性
        columnProps() {
            return {
                showOverflowTooltip: this.tableConfig.tooltip,
                headerAlign: this.tableConfig.headerAlign,
                align: this.tableConfig.align,
                width: this.tableConfig.width
            }
        }
    },
    created() {
        this.getTableData({}, this.currentPage, this.pageSize)
    },
    methods: {
        /** 复制代码成功 */
        clipboardSuccess() {
            this.$message.success("复制成功");
        },
        /** 高亮显示 */
        highlightedCode(code, key) {
            const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
            var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
            const result = hljs.highlight(language, code || "", true);
            return result.value || '&nbsp;';
        },
        // 预览按钮被点击
        handlePreview(row) {
            console.log("row", row);
            this.preview.title = row.name + "表生成代码预览"
            this.preview.activeName = 'controller.java'
            this.preview.open = true
            this.getPreviewData(row.name)
        },
        // 获取生成代码预览
        getPreviewData(tableName) {
            previewCode(tableName).then(res => {
                const { data } = res
                this.preview.data = data
            })
        },
        // 表格初始化获取数据
        getTableData(data, currentPage, pageSize) {
            this.tableLoading = true
            tableList(data, currentPage, pageSize).then(res => {
                const { data } = res
                // console.log(data);
                this.currentPage = data.currentPage
                this.pageSize = data.pageSize
                this.total = data.total
                this.tableData = data.data
                this.tableLoading = false
            }).catch(() => {
                this.tableLoading = false
            })
        },
        // 搜索按钮被点击
        handleSearch() {
            this.handlePagination()
        },
        // 行数据复选框被选中
        handleSelectionChange(selection) {
            this.selectedUsers = selection
            this.selectedIds = selection.map(obj => obj.id)
        },
        // 导出按钮被点击
        handleExport() {
            // 假设这里有一个API调用导出用户
            // console.log('数据导出');
        },
        // 刷新按钮被点击
        handleRefresh() {
            this.handlePagination()
        },
        // 页码改变/页面显示条数改变
        handleSizeChange(newSize) {
            this.pageSize = newSize
            // console.log("页面显示条数被改变：" + newSize + "条");
            this.handlePagination()
        },
        // 页码被改变
        handleCurrentChange(newPage) {
            this.currentPage = newPage
            // console.log("页码被改变：" + newPage);
            this.handlePagination()
        },
        handlePagination() {
            this.getTableData(this.searchForm, this.currentPage, this.pageSize)
        }
    }
}
</script>


<style scoped>
.scrollbar {
    overflow: auto;
    overflow-x: hidden;
    max-height: 70vh;
    padding: 10px 20px 0;
}
</style>