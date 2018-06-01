<template>
  <div class="login-container">
    <el-form class="login-form" autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left">
      <div class="title-container">
        <h3 class="title">H-note</h3>
      </div>
      <el-form-item prop="name">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input name="name" type="text" v-model="loginForm.name" autoComplete="off" placeholder="username" />
      </el-form-item>

      <el-form-item prop="email">
        <span class="svg-container svg-container_email">
          <svg-icon icon-class="email" />
        </span>
        <el-input name="email" type="text" v-model="loginForm.email" autoComplete="off" placeholder="email" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input name="password" :type="passwordType" v-model="loginForm.password" autoComplete="off" placeholder="password" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>

      <el-form-item prop="rePassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input name="rePassword" :type="passwordType" v-model="loginForm.rePassword" autoComplete="off" placeholder="repeat password" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>

      <el-button type="primary" style="width:100%;margin-bottom:10px;" :loading="loading" @click.native.prevent="handleRegister">Register</el-button>
      
      <div class="login-tips-div">
        <router-link class="login-link" to="/login">Already have an account? now login.</router-link>
      </div>
    </el-form>

  </div>
</template>

<script>
import { isvalidUsername, validateEmail } from '@/utils/validate'
import {  checkUser, createUser } from '@/api/user' 
import { Message } from 'element-ui'

export default {
  name: 'register',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        new Promise((resolve, reject) => {
          checkUser(value).then(response => {
            const data = response.data
            if (data.code == 1003) {
              callback(new Error(data.message))
            } else {
              callback()
            }
          })
        })
      }
    }
    const validateCurrEmail = (rule, value, callback) => {
        if (!validateEmail(value)) {
          callback(new Error('Please enter the correct email'))
        } else {
          callback()
        }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    const validateRePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        if (value != this.loginForm.password) {
          callback(new Error('The repeat password is not equal to password!'))
        } else {
          callback()
        }
      }
    }
    return {
      loginForm: {
        name: '',
        password: '',
        rePassword: '',
        email: ''
      },
      loginRules: {
        name: [{ required: true, trigger: 'blur', validator: validateUsername }],
        email: [{ required: true, trigger: 'blur', validator: validateCurrEmail }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        rePassword: [{ required: true, trigger: 'blur', validator: validateRePassword }]
      },
      passwordType: 'password',
      loading: false
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    handleRegister() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          new Promise((resolve, reject) => {
            createUser(this.loginForm).then(response => {
              if (response.status == 201) {
                this.$message({
                    message: '创建用户成功，请前往登录。',
                    type: 'success'
                });
                this.$router.push('/login');
              }
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  created() {
    // window.addEventListener('hashchange', this.afterQRScan)
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan)
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#ffffff;
$light_gray:#666666;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#ffffff;
$dark_gray:#889aa4;
$light_gray:#666666;

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 420px;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    &_login {
      font-size: 20px;
    }
  }
  .title-container {
    position: relative;
    .title {
      font-size: 26px;
      font-weight: 400;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
    .set-language {
      color: #fff;
      position: absolute;
      top: 5px;
      right: 0px;
    }
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .thirdparty-button {
    position: absolute;
    right: 35px;
    bottom: 28px;
  }
}
</style>

<style>
  .login-tips-div {
    position: absolute;
    right: 35px;
    width: 420px;
  }

  .login-tips-div a {
    color: #AA99BB;
    font-size: 12px;
    text-decoration: none;
  }

  .login-tips-div a:hover{
    text-decoration: underline;
  }

  .login-tips-div .login-link {
    float: left;
  }
</style>
