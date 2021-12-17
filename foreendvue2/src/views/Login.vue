<template>
    <div>
        <el-form :rules="rules"
                 v-loading="loading"
                 element-loading-text="正在登录..."
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.8)"
                 ref="loginForm"
                 :model="loginForm"
                 class="loginContainer">
            <el-image :src="lenovologo" style="width: 120px;" />
            <h3 class="loginTitle">Lenovo Beijing APP Automation</h3>
            <el-form-item prop="username">
                <el-input type="text" auto-complete="false" v-model="loginForm.username"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" auto-complete="false" v-model="loginForm.password"
                placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-checkbox v-model="checked" class="loginRemember">记住我</el-checkbox>
            <div style="text-align:left">
        <el-button-group>
          <el-button :loading="loading" type="primary" plain size="small" style="margin-bottom:30px;float:left" @click="submitLogin">用户登录</el-button>
          <el-tooltip class="item" effect="dark" content="请选择角色，以便后面接收邮件，另外，请填写联想邮箱。" placement="top-start">
            <el-button type="primary" plain size="small" style="margin-bottom:30px;float:center" @click="registerUser()" >注册账户</el-button>
          </el-tooltip>
        </el-button-group>
        <el-button type="primary" plain size="small" style="margin-bottom:30px;float:right" @click.native.prevent="handleForget">忘记/修改密码</el-button>
      </div>
        </el-form>
      <el-dialog :visible.sync="showDialog">
      <div>
        <div class="title-container">
          <h5 class="title">账户注册</h5>
        </div>
        <div class="title-container">
          <h6 class="title-notice">（本账户注册用以登录自动化测试平台；注册成功后会发送邮件，请注意查收！）</h6>
        </div>
        <div>
          <el-form ref="dynamicValidateForm" :model="dynamicValidateForm" autocomplete="on" 
          label-position="left" class="authsystem-el-form">
            <el-form-item prop="loginid">
            <el-input v-model="dynamicValidateForm.loginid" placeholder="登录用户唯一ID" />
            </el-form-item>
            <el-form-item icon-class="people">
              <el-select
                v-model="dynamicValidateForm.componentId"
                size="mini"
                placeholder="请选择所属项目"
              >
                <el-option
                  v-for="(item, index) in componentOptions"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="dynamicValidateForm.email" placeholder="联想邮箱地址,用于发送测试结果" />
            </el-form-item>
            <el-form-item prop="password">
            <el-input v-model="dynamicValidateForm.password" placeholder="密码" show-password />
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input v-model="dynamicValidateForm.confirmPassword" 
              placeholder="确认密码" show-password />
            </el-form-item>
          </el-form>
        </div>
        <div style="color: #ff0000;">
          <h6> ( * 密码必须8位以上，并且包含字母、数字、特殊符号 )</h6>
        </div>
        <div style="text-align:right">
          <span slot="footer" class="dialog-footer">
            <el-button size="small" type="primary" @click="handleRegister()">确 定</el-button>
            <el-button size="small" @click="showDialog = false">取 消</el-button>
          </span>
        </div>
      </div>
    </el-dialog>
    </div>
</template>

<script>
import lenovologo from '@/assets/group_images/LenovoLogo.png'
  export default {
        name: "Login",
        data() {
            return {
              lenovologo: lenovologo,
              // register
              forgetForm: {
                loginid: '',
                email: '',
                role: '',
                password: '',
                confirmPassword: ''
              },
              // add
              dynamicValidateForm: {
                loginid: '',
                email: '',
                componentId: 0,
                password: '',
                confirmPassword: ''
              },
              componentOptions: [
                {id:'', name:''}
               ],
              showDialog: false,
              getComponentUrl: '',
                captchaUrl: '/captcha?time=' + new Date(),
                loginForm: {
                    username: 'admin',
                    password: '123',
                    code: ''
                },
                loading: false,
                checked: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                }
            }
        },
        methods: {
            updateCaptcha() {
                // this.captchaUrl = '/captcha?time=' + new Date();
            },
            registerUser(){
              this.showDialog = true
              this.getComponents()
            },
            getComponents() {
              this.getRequest('/getParentComponent').then(resp => {
                this.componentOptions = resp
              })
            },
            handleRegister(){
                this.$refs.dynamicValidateForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        console.log(this.dynamicValidateForm.componentId)
                        this.postRequest('/register', this.dynamicValidateForm).then(resp => {
                            this.loading = false
                            if (resp) {
                                this.$message.console.warn();('请输入！')
                                this.$router.replace('/')
                            }
                        })
                    } else {
                        this.$message.error('请输入所有字段！')
                        return false;
                    }
                });
            },
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.postRequest('/login', this.loginForm).then(resp => {
                            this.loading = false
                            if (resp) {
                                const tokenStr = resp.obj.tokenHead+resp.obj.token
                                window.sessionStorage.setItem('tokenStr', tokenStr)
                                this.$router.replace('/home')
                            }
                        })
                    } else {
                        this.$message.error('请输入所有字段！')
                        return false;
                    }
                });
            }
        }
    }
    
</script>

<style>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 0px auto 40px auto;
        text-align: center;
    }

    .loginRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }

    .el-form-item__content {
        display: flex;
        align-items: center;
    }
</style>