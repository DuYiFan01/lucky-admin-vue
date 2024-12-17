SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `username` varchar(50) NULL DEFAULT '' COMMENT '登录账号',
                              `ip` varchar(128)  NULL DEFAULT '' COMMENT '登录IP地址',
                              `ip_addr` varchar(255) NULL DEFAULT '' COMMENT '登录地点',
                              `browser` varchar(50) NULL DEFAULT '' COMMENT '浏览器类型',
                              `os` varchar(50) NULL DEFAULT '' COMMENT '操作系统',
                              `STATUS` char(1) NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
                              `msg` varchar(255) NULL DEFAULT '' COMMENT '提示消息',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `idx_login_log_s`(`STATUS` ASC) USING BTREE,
                              INDEX `idx_login_log_lt`(`create_time` ASC) USING BTREE,
                              INDEX `idx_login_log_username`(`username` ASC) USING BTREE
) COMMENT = '用户登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                          `name` varchar(50)  NULL DEFAULT NULL COMMENT '路由名称',
                          `title` varchar(50)  NOT NULL DEFAULT '' COMMENT '菜单名称',
                          `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
                          `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
                          `path` varchar(200)  NULL DEFAULT '' COMMENT '路由地址',
                          `component` varchar(255)  NULL DEFAULT NULL COMMENT '组件路径',
                          `query` varchar(255) NULL DEFAULT NULL COMMENT '路由参数',
                          `is_frame` int NULL DEFAULT 0 COMMENT '是否为外链（1是 0否）',
                          `is_cache` int NULL DEFAULT 1 COMMENT '是否缓存（1缓存 0不缓存）',
                          `menu_type` char(1)  NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                          `visible` int NULL DEFAULT 1 COMMENT '菜单状态（1显示 0隐藏）',
                          `roles` varchar(100) NULL DEFAULT NULL COMMENT '权限标识',
                          `icon` varchar(100) NULL DEFAULT '#' COMMENT '菜单图标',
                          `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `create_by` varchar(64)  NULL DEFAULT '' COMMENT '创建人',
                          `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                          `update_by` varchar(64)  NULL DEFAULT '' COMMENT '更新人',
                          `remark` varchar(500) NULL DEFAULT '' COMMENT '备注',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `name_title`(`name` ASC, `title` ASC) USING BTREE,
                          INDEX `index_parent_id`(`parent_id` ASC) USING BTREE
)COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('system', '系统管理', 0, 1, '/system', NULL, NULL, 0, 1, 'M', 1, NULL, 'system');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('users', '用户管理', 1, 1, '/system/users', '/system/users/index', NULL, 0, 1, 'C', 1, '', 'user');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('menus', '菜单管理', 1, 2, '/system/menus', '/system/menus/index', NULL, 0, 1, 'C', 1, '', 'tree-table');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('roles', '角色管理', 1, 3, '/system/roles', '/system/roles/index', NULL, 0, 1, 'C', 1, '', 'peoples');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('logs', '日志管理', 1, 4, '/system/logs', NULL, NULL, 0, 1, 'M', 1, NULL, 'log');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('operlog', '操作日志', 5, 1, '/system/logs/operlog', '/system/logs/operlog/index', NULL, 0, 1, 'C', 1, '', 'form');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('loginlog', '登录日志', 5, 2, '/system/logs/loginlog', '/system/logs/loginlog/index', NULL, 0, 1, 'C', 1, '', 'logininfor');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('', '用户新增', 2, 2, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::insert', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('tools', '系统工具', 0, 2, '/tools', NULL, NULL, 0, 1, 'M', 1, NULL, 'tool');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('swagger', '系统接口', 9, 1, '/tools/swagger', '/tools/swagger/index', NULL, 0, 1, 'C', 1, '', 'swagger');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('MyGitee', 'Gitee地址', 0, 4, 'https://gitee.com/xiaodu6/lucky-admin-vue', NULL, NULL, 1, 1, 'M', 1, NULL, 'guide');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '菜单查询', 3, 1, '', NULL, NULL, 0, 1, 'F', 1, 'system::menus::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户查询', 2, 1, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '菜单新增', 3, 2, '', NULL, NULL, 0, 1, 'F', 1, 'system::menus::insert', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '菜单修改', 3, 3, '', NULL, NULL, 0, 1, 'F', 1, 'system::menus::update', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '菜单删除', 3, 4, '', NULL, NULL, 0, 1, 'F', 1, 'system::menus::delete', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户修改', 2, 3, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::update', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户删除', 2, 4, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::delete', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户导入', 2, 5, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::import', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户导出', 2, 6, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::export', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '用户添加角色', 2, 7, '', NULL, NULL, 0, 1, 'F', 1, 'system::users::grant', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '角色查询', 4, 1, '', NULL, NULL, 0, 1, 'F', 1, 'system::role::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '角色新增', 4, 2, '', NULL, NULL, 0, 1, 'F', 1, 'system::role::insert', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '角色修改', 4, 3, '', NULL, NULL, 0, 1, 'F', 1, 'system::role::update', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '角色删除', 4, 4, '', NULL, NULL, 0, 1, 'F', 1, 'system::role::delete', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '角色添加用户', 4, 5, '', NULL, NULL, 0, 1, 'F', 1, 'system::role::grant', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '操作查询', 6, 1, '', NULL, NULL, 0, 1, 'F', 1, 'system::logs::oprLogs::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '登录查询', 7, 1, '', NULL, NULL, 0, 1, 'F', 1, 'system::logs::loginLog::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '操作删除', 6, 2, '', NULL, NULL, 0, 1, 'F', 1, 'system::logs::oprLogs::delete', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '登录删除', 7, 2, '', NULL, NULL, 0, 1, 'F', 1, 'system::logs::loginLog::delete', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('monitor', '系统监控', 0, 3, '/monitor', NULL, NULL, 0, 1, 'M', 1, NULL, 'monitor');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('server', '服务监控', 31, 1, '/monitor/server', '/monitor/server/index', NULL, 0, 1, 'C', 1, NULL, 'server');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '服务查询', 32, 1, '', NULL, NULL, 0, 1, 'F', 1, 'monitor::server::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '接口查询', 10, 1, '', NULL, NULL, 0, 1, 'F', 1, 'tools::swagger::list', '#');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES ('gen', '代码生成', 9, 2, '/tools/gen', '/tools/gen/index', NULL, 0, 1, 'C', 1, NULL, 'code');
INSERT INTO `menus` (`name`, `title`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `roles`, `icon`) VALUES (NULL, '查询代码', 35, 1, '', NULL, NULL, 0, 1, 'F', 1, 'tools::gen::list', '#');

-- ----------------------------
-- Table structure for opr_logs
-- ----------------------------
DROP TABLE IF EXISTS `opr_logs`;
CREATE TABLE `opr_logs`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                             `title` varchar(50) NULL DEFAULT '' COMMENT '模块标题',
                             `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
                             `method_name` varchar(200) NULL DEFAULT '' COMMENT '方法名称',
                             `request_method` varchar(10) NULL DEFAULT '' COMMENT '请求方式',
                             `username` varchar(50) NULL DEFAULT '' COMMENT '操作人员',
                             `url` varchar(255) NULL DEFAULT '' COMMENT '请求URL',
                             `ip` varchar(128) NULL DEFAULT '' COMMENT '主机地址',
                             `param` varchar(2000) NULL DEFAULT '' COMMENT '请求参数',
                             `result` varchar(2000) NULL DEFAULT '' COMMENT '返回参数',
                             `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
                             `error_msg` varchar(2000) NULL DEFAULT '' COMMENT '错误消息',
                             `start_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
                             `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
                             INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
                             INDEX `idx_sys_oper_log_ot`(`start_time` ASC) USING BTREE
) COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opr_logs
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                          `name` varchar(50) NOT NULL COMMENT '角色名称',
                          `description` varchar(255)  NULL DEFAULT NULL COMMENT '角色描述',
                          `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
                          `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                          `update_by` varchar(50) NULL DEFAULT NULL COMMENT '更新人',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `name`(`name` ASC) USING BTREE
) COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'admin', '系统管理员', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');
INSERT INTO `roles` VALUES (2, 'user', '普通用户', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

-- ----------------------------
-- Table structure for roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `roles_menus`;
CREATE TABLE `roles_menus`  (
                                `role_id` bigint NOT NULL COMMENT '角色ID',
                                `menu_id` bigint NOT NULL COMMENT '菜单ID',
                                PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) COMMENT = '角色和菜单关联表';

-- ----------------------------
-- Records of roles_menus
-- ----------------------------
INSERT INTO `roles_menus` VALUES (1, 1);
INSERT INTO `roles_menus` VALUES (1, 2);
INSERT INTO `roles_menus` VALUES (1, 3);
INSERT INTO `roles_menus` VALUES (1, 4);
INSERT INTO `roles_menus` VALUES (1, 5);
INSERT INTO `roles_menus` VALUES (1, 6);
INSERT INTO `roles_menus` VALUES (1, 7);
INSERT INTO `roles_menus` VALUES (1, 8);
INSERT INTO `roles_menus` VALUES (1, 9);
INSERT INTO `roles_menus` VALUES (1, 10);
INSERT INTO `roles_menus` VALUES (1, 11);
INSERT INTO `roles_menus` VALUES (1, 12);
INSERT INTO `roles_menus` VALUES (1, 13);
INSERT INTO `roles_menus` VALUES (1, 14);
INSERT INTO `roles_menus` VALUES (1, 15);
INSERT INTO `roles_menus` VALUES (1, 16);
INSERT INTO `roles_menus` VALUES (1, 17);
INSERT INTO `roles_menus` VALUES (1, 18);
INSERT INTO `roles_menus` VALUES (1, 19);
INSERT INTO `roles_menus` VALUES (1, 20);
INSERT INTO `roles_menus` VALUES (1, 21);
INSERT INTO `roles_menus` VALUES (1, 22);
INSERT INTO `roles_menus` VALUES (1, 23);
INSERT INTO `roles_menus` VALUES (1, 24);
INSERT INTO `roles_menus` VALUES (1, 25);
INSERT INTO `roles_menus` VALUES (1, 26);
INSERT INTO `roles_menus` VALUES (1, 27);
INSERT INTO `roles_menus` VALUES (1, 28);
INSERT INTO `roles_menus` VALUES (1, 29);
INSERT INTO `roles_menus` VALUES (1, 30);
INSERT INTO `roles_menus` VALUES (1, 31);
INSERT INTO `roles_menus` VALUES (1, 32);
INSERT INTO `roles_menus` VALUES (1, 33);
INSERT INTO `roles_menus` VALUES (2, 1);
INSERT INTO `roles_menus` VALUES (2, 5);
INSERT INTO `roles_menus` VALUES (2, 7);
INSERT INTO `roles_menus` VALUES (2, 11);
INSERT INTO `roles_menus` VALUES (2, 28);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
                               `user_id` bigint NOT NULL COMMENT '用户ID',
                               `role_id` bigint NOT NULL COMMENT '角色ID',
                               PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
                               INDEX `role_id`(`role_id` ASC) USING BTREE,
                               CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) COMMENT = '用户角色关联表' ;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (1, 1);
INSERT INTO `user_roles` VALUES (2, 2);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                          `username` varchar(50)  NOT NULL COMMENT '用户名',
                          `password` varchar(255)  NOT NULL COMMENT '密码',
                          `email` varchar(100)  NULL DEFAULT NULL COMMENT '邮箱',
                          `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `create_by` varchar(50) NULL DEFAULT NULL COMMENT '创建人',
                          `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                          `update_by` varchar(50) NULL DEFAULT NULL COMMENT '更新人',
                          `del_flag` int NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `username`(`username` ASC) USING BTREE
) COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin123', 'admin@example.com', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 0);
INSERT INTO `users` VALUES (2, 'user', 'admin123', 'user@example.com', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 0);

SET FOREIGN_KEY_CHECKS = 1;
