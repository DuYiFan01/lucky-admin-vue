



SET FOREIGN_KEY_CHECKS = 0;

-- 创建用户表
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                         `username` VARCHAR ( 50 ) NOT NULL COMMENT '用户名',
                         `password` VARCHAR ( 255 ) NOT NULL COMMENT '密码',
                         `email` VARCHAR ( 100 ) NULL DEFAULT NULL COMMENT '邮箱',
                         `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `create_by` VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '创建人',
                         `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                         `update_by` VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '更新人',
                         `del_flag` INT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                         PRIMARY KEY ( `id` ) USING BTREE,
                         UNIQUE INDEX `username` ( `username` ASC ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表';

-- 添加用户信息
INSERT INTO `users` ( `username`, `password`, `email`, `create_by`, `update_by` )
VALUES
    ( 'admin', 'admin123', 'admin@example.com', 'system', 'system' ),
    ( 'user', 'admin123', 'user@example.com', 'system', 'system' ),
    ( 'user1', 'admin123', 'user1@example.com', 'system', 'system' );


-- 创建角色表
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                         `name` VARCHAR ( 50 )  NOT NULL COMMENT '角色名称',
                         `description` VARCHAR ( 255 )  NULL COMMENT '角色描述',
                         `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `create_by` VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '创建人',
                         `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                         `update_by` VARCHAR ( 50 )  NULL DEFAULT NULL COMMENT '更新人',
                         PRIMARY KEY ( `id` ) USING BTREE,
                         UNIQUE INDEX `name` ( `name` ASC ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表';

-- 添加角色信息
INSERT INTO `roles` ( `name`, `description`, `create_by`, `update_by` )
VALUES
    ( 'admin', '系统管理员', 'system', 'system' ),
    ( 'user', '普通用户', 'system', 'system' ),
    ( 'testUser', '测试用户', 'system', 'system' );




-- 创建用户角色关联表
DROP TABLE IF	EXISTS `user_roles`;
CREATE TABLE `user_roles` (
                              `user_id` BIGINT NOT NULL COMMENT '用户ID',
                              `role_id` BIGINT NOT NULL COMMENT '角色ID',
                              PRIMARY KEY ( `user_id`, `role_id` ) USING BTREE,
                              INDEX `role_id` ( `role_id` ASC ) USING BTREE,
                              CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `users` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
                              CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY ( `role_id` ) REFERENCES `roles` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表';

-- 添加用户角色关联信息
INSERT INTO `user_roles` ( `user_id`, `role_id` )
VALUES
    ( 1, 1 ),
    ( 2, 2 ),
    ( 3, 3 );

-- 创建菜单表
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                         `name` VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '路由名称',
                         `title` VARCHAR ( 50 ) NOT NULL DEFAULT '' COMMENT '菜单名称',
                         `parent_id` BIGINT NULL DEFAULT 0 COMMENT '父菜单ID',
                         `order_num` INT NULL DEFAULT 0 COMMENT '显示顺序',
                         `path` VARCHAR ( 200 ) NULL DEFAULT '' COMMENT '路由地址',
                         `component` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '组件路径',
                         `query` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '路由参数',
                         `is_frame` INT NULL DEFAULT 0 COMMENT '是否为外链（1是 0否）',
                         `is_cache` INT NULL DEFAULT 1 COMMENT '是否缓存（1缓存 0不缓存）',
                         `menu_type` CHAR ( 1 )  NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                         `visible` INT NULL DEFAULT 1 COMMENT '菜单状态（1显示 0隐藏）',
                         `roles` VARCHAR ( 100 ) NULL DEFAULT NULL COMMENT '权限标识',
                         `icon` VARCHAR ( 100 ) NULL DEFAULT '#' COMMENT '菜单图标',
                         `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `create_by` VARCHAR ( 64 ) NULL DEFAULT '' COMMENT '创建人',
                         `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                         `update_by` VARCHAR ( 64 ) NULL DEFAULT '' COMMENT '更新人',
                         `remark` VARCHAR ( 500 ) NULL DEFAULT '' COMMENT '备注',
                         PRIMARY KEY ( `id` ) USING BTREE,
                         INDEX `index_parent_id` ( `parent_id` ASC ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表';


-- 创建角色菜单表
DROP TABLE IF EXISTS `roles_menus`;
CREATE TABLE `roles_menus` (
                               `role_id` BIGINT NOT NULL COMMENT '角色ID',
                               `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
                               PRIMARY KEY ( `role_id`, `menu_id` ) USING BTREE
)	ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表';

-- 创建操作日志表
DROP TABLE IF EXISTS `opr_logs`;
CREATE TABLE `opr_logs`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                             `title` varchar(50)  NULL DEFAULT '' COMMENT '模块标题',
                             `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
                             `method_name` varchar(200)  NULL DEFAULT '' COMMENT '方法名称',
                             `request_method` varchar(10)  NULL DEFAULT '' COMMENT '请求方式',
                             `username` varchar(50)  NULL DEFAULT '' COMMENT '操作人员',
                             `url` varchar(255)  NULL DEFAULT '' COMMENT '请求URL',
                             `ip` varchar(128)  NULL DEFAULT '' COMMENT '主机地址',
                             `param` varchar(2000)  NULL DEFAULT '' COMMENT '请求参数',
                             `result` varchar(2000)  NULL DEFAULT '' COMMENT '返回参数',
                             `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
                             `error_msg` varchar(2000) NULL DEFAULT '' COMMENT '错误消息',
                             `start_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
                             `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
                             INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
                             INDEX `idx_sys_oper_log_ot`(`start_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录';



DROP TABLE IF	EXISTS login_log;
CREATE TABLE login_log (
                           id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
                           username VARCHAR ( 50 ) DEFAULT '' COMMENT '登录账号',
                           ip VARCHAR ( 128 ) DEFAULT '' COMMENT '登录IP地址',
                           ip_addr VARCHAR ( 255 ) DEFAULT '' COMMENT '登录地点',
                           browser VARCHAR ( 50 ) DEFAULT '' COMMENT '浏览器类型',
                           os VARCHAR ( 50 ) DEFAULT '' COMMENT '操作系统',
                           STATUS CHAR ( 1 ) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
                           msg VARCHAR ( 255 ) DEFAULT '' COMMENT '提示消息',
                           create_time datetime COMMENT '访问时间',
                           PRIMARY KEY ( id ),
                           KEY idx_login_log_s ( STATUS ),
                           KEY idx_login_log_lt ( create_time )
) ENGINE = INNODB COMMENT = '用户登录日志';

SET FOREIGN_KEY_CHECKS = 1;
