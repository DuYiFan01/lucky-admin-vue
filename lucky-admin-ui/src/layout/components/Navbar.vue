<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar + '?imageView2/1/w/80/h/80'" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item divided style="text-align: center;">首页</el-dropdown-item>
          </router-link>
          <a v-for="item in links" :key="item.title" :href="item.url" :target="item.target">
            <el-dropdown-item>{{ item.title }}</el-dropdown-item>
          </a>
          <el-dropdown-item divided @click.native="updatePassword.updatePasswordDialog = true">
            <span style="display:block;">修改密码</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 修改密码弹出框 -->
    <el-dialog
      title="修改密码"
      :visible.sync="updatePassword.updatePasswordDialog"
      :close-on-click-modal="false"
      width="400px"
      center
    >
      <el-form ref="updatePassword.form" :model="updatePassword.form" status-icon :rules="updatePassword.rules">
        <el-form-item label="密码" prop="oldPassword">
          <el-input v-model="updatePassword.form.oldPassword" type="text" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="updatePassword.form.newPassword" type="text" />
        </el-form-item>
        <el-form-item label="确认新密码" prop="checkNewPassword">
          <el-input v-model="updatePassword.form.checkNewPassword" type="text" />
        </el-form-item>
        <el-form-item style="text-align: center;">
          <el-button type="primary" @click="submitForm('updatePassword.form')">提交</el-button>
          <el-button @click="updatePassword.updatePasswordDialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },

  data() {
    return {
      // 修改密码
      updatePassword: {
        updatePasswordDialog: false,
        form: {
          oldPassword: '',
          newPassword: '',
          checkNewPassword: ''
        },
        rules: {
          oldPassword: [
            { required: true, message: '请输入旧密码', trigger: 'blur' },
            { min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur' }
          ],
          newPassword: [
            { required: true, message: '请输入新密码', trigger: 'blur' },
            { min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur' }
          ],
          checkNewPassword: [
            { required: true, message: '请再次输入新密码', trigger: 'blur' },
            { validator: this.validateCheckNewPassword, trigger: 'blur' }
          ]

        }
      },
      // 头像地址
      avatar: process.env.VUE_APP_BASE_URL + process.env.VUE_APP_BASE_API + '/user/' + this.$store.getters.username + '/square',
      // 链接
      links: [
        { title: '在线编辑', url: 'https://panjiachen.github.io/vue-element-admin-site/#/', target: '_blank' },
        { title: '下载模板', url: 'https://github.com/PanJiaChen/vue-element-admin/archive/master.zip', target: '_blank' }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ])
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('user/updatePassword', this.updatePassword.form).then(() => {
            this.$message({
              message: '修改成功',
              type: 'success'
            })
            this.updatePassword.updatePasswordDialog = false
          })
        }
      })
    },

    // 校验两次密码是否一致
    validateCheckNewPassword(rule, value, callback) {
      if (value !== this.updatePassword.form.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }

}

</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
