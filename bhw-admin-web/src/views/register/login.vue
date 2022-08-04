<template>
    <div class="loginWrap" ref="loginWH">
        <div class="loginBox">
            <el-row type="flex" align="middle" style="width: 1248px;margin: 80px auto">
                <el-col style="width:350px;margin-top:-20px;"><img src="../../images/img-logo_login.png" style="width: 100%;" alt=""></el-col>
                <el-col style="width: 163px;"></el-col>
                <el-col style="width:735px;">
                    <h1>对接平台后台运营管理系统</h1>
                    <h2>Operation Management System</h2>
                    <div class="loginForm">
                        <el-form :model="form" ref="form" style="width: 100%;margin-top: 50px" class="demo-login-Form">
                            <el-form-item :rules="[{required: true, message: '账号不能为空'}]" prop="account">
                                <el-input v-model="form.account"  placeholder="账号输入" type="name"  ></el-input>
                            </el-form-item>
                            <el-form-item :rules="[{required: true, message: '密码不能为空'}]" prop="password" style="margin: 0 37px 0 15px;">
                                <el-input v-model="form.password" placeholder="密码输入" show-password type="password"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="text" class="btnLogin" @click="login"></el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                 <!--   <div class="link-regist"><router-link :to="{path:'/register'}">管理员注册</router-link></div>-->
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "login",
        data(){
            return{
                clientWidth:'',
                clientHeight:'',
                form:{
                    account:"", password:""
                }
            }
        },
        mounted() {
            this.clientHeight = `${document.documentElement.clientHeight}`;
            this.clientWidth = `${document.documentElement.clientWidth}`;
            this.$refs.loginWH.style.height = this.clientHeight+'px';
            this.$refs.loginWH.style.width = this.clientWidth + 'px';
        },
        methods:{
            login(){
                this.$store.dispatch('Login', this.form).then(response => {
                    if(response.retCode===-1){
                        this.$message.error("用户名或密码错误");
                    }else{
                        this.$router.push({
                            path: '/home'
                        });
                    }
                }).catch(() => {
                    this.$message({
                        message: "222用户名或密码错误",
                        type: 'warning'
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .loginWrap{background: url("../../images/img-login.png") no-repeat;background-size: cover;}
    .loginBox{width: 100%;height: 427px;background:rgba(255,255,255,0.8);position: absolute;top:50%;left: 50%;transform: translate(-50%,-50%);-webkit-transform:translate(-50%,-50%); }
    .loginBox h1{color: #1D1D1D; font-size: 60px;}
    .loginBox h2{color: #1D1D1D;font-size: 52px;}
    .btnLogin{width: 56px;height: 47px;background: url("../../images/imgIcon/icon-btn_login.png") no-repeat;}
    .demo-login-Form{display: flex;display: -webkit-flex;}
    .loginForm /deep/ .demo-login-Form .el-form-item{width: 309px;}
    .loginForm /deep/ .demo-login-Form .el-input__inner{width: 309px;height: 56px;line-height: 56px;color: #1D1D1D;font-size: 18px; text-align: center; border: 1px solid rgba(125,125,125,1);}
    .link-regist{text-align: right;padding-right: 100px;}
    .link-regist a{color: #024ECC;text-decoration: none;}
</style>