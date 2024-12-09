# Lucky-Admin-Vue

#### 介绍
Lucky-Admin-Vue 一个基于vue-admin-template的后台管理框架，集成了动态角色权限，动态路由，Sa-Token权限校验，快速构建一个管理后台的框架
#### 软件架构
### 后端目录架构
```
├─system                # 系统相关
│  ├─annotation         # 注解相关
│  ├─aspectj            # 切面相关
│  ├─base               # 基础类
│  │  └─controller      # 继承前端控制器
│  ├─config             # 配置相关
│  ├─controller         # 前端控制器
│  │  ├─monitor         # 监控相关
│  │  └─system          # 系统相关
│  ├─enums              # 枚举相关
│  ├─exception          # 异常相关
│  ├─gen                # 代码生成相关
│  ├─listener           # 监听相关
│  ├─mapper             # mapper
│  ├─page               # 分页相关
│  │  └─vo              # 分页视图对象
│  ├─pojo               # 实体类
│  │  ├─monitor         # 监控相关实体类
│  │  │  └─server       # 服务器信息实体类
│  │  └─system          # 系统相关实体类
│  ├─service            # 服务相关
│  │  ├─monitor         # 监控相关服务
│  │  └─system          # 系统相关服务
│  │      └─impl        # 系统相关服务实现类
│  ├─utils              # 工具类
│  └─vo                 # 系统相关视图对象
└─vo                    # 全局相关视图对象
``` 
### 前端目录架构
```
├─api                   # api接口
│  ├─monitor            # 监控相关api
│  └─system             # 系统相关api
├─assets                # 静态资源
│  ├─403_images         # 403图片
│  ├─404_images         # 404图片
│  ├─css                # 样式文件
│  └─icons              # 图标
│      └─svg            # svg图标
├─components            # 组件
│  ├─Breadcrumb         # 面包屑
│  ├─Hamburger          # 汉堡图标
│  ├─IconSelect         # 图标选择器
│  ├─ParentView         # 父级路由
│  └─SvgIcon            # svg图标
├─directive             # 自定义指令
│  └─permission         # 权限指令
├─icons                 # 图标
│  └─svg                # svg图标
├─layout                # 布局
│  ├─components         # 布局组件
│  │  └─Sidebar         # 侧边栏
│  └─mixin              # 布局混入
├─router                # 路由
├─store                 # 状态管理
│  └─modules            # 状态管理模块
├─styles                # 样式文件
├─utils                 # 工具类
└─views                 # 页面
    ├─dashboard         # 首页
    ├─login             # 登录
    ├─monitor           # 监控相关
    │  └─server         # 服务器信息
    └─system            # 系统相关
        ├─logs          # 日志相关
        │  ├─loginlog   # 登录日志
        │  └─operlog    # 操作日志
        ├─menus         # 菜单相关
        ├─roles         # 角色相关
        └─users         # 用户相关
```
#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 项目截图
![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
