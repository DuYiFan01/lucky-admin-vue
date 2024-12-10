# Lukcy-Admin-Vue-Ui

## 启动命令
~~~ node.js
  npm run dev  //开发环境
  npm run build:prod  //打包生产环境
  npm run build:dev  //打包开发环境
  npm run preview // 预览生产环境
~~~
## 预览地址
~~~
  http://localhost:9528
~~~
## 登录账号
账号：admin 密码：admin123

## 快速启动
### 下载依赖
~~~ node.js
  npm install
~~~
### 启动项目
~~~ node.js
  npm run dev
~~~

## 打包项目

1. 确认是否有node环境以及版本在18.0以上
2. 进入根目录执行 npm install 下载依赖包
3. 设置修改 .env.production 文件中的后端请求地址
4. 执行 npm run dev 启动项目(确认是否可以正常运行)
5. 执行 npm run build:prod 打包线上环境项目(dir: dist)
6. 将 dist 目录中的文件上传至服务器

### 常见问题：
待添加......