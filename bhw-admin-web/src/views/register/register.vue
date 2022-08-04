<template>
    <div class="registerWrap" ref="registerWH">
        <div class="registerBox">
            <h2>Traup-OMSAdmin</h2>
            <p>Traup-北部湾中心网站后台运营管理系统</p>
            <div class="registerForm">
                        <el-form :model="form" ref="form" style="width: 100%;margin-top: 50px" class="demo-login-Form">
                            <el-form-item :rules="[{required: true, message: '账号不能为空'}]" prop="name">
                                <el-input v-model="form.name"  placeholder="账号输入" type="name"  ></el-input>
                            </el-form-item>
                            <el-form-item :rules="[{required: true, message: '密码不能为空'}]" prop="password" style="margin: 0 37px 0 15px;">
                                <el-input v-model="form.password" placeholder="密码输入" show-password type="password"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="text" class="btnLogin"></el-button>
                            </el-form-item>
                        </el-form>
                    </div>
            <div class="link-regist"><router-link :to="{path:'/register'}">管理员注册</router-link></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "register",
        data(){
            return{
                clientWidth:'',
                clientHeight:'',
                form:{
                    name:"", password:""
                }
            }
        },
        mounted() {
            this.clientHeight = `${document.documentElement.clientHeight}`;
            this.clientWidth = `${document.documentElement.clientWidth}`;
            this.$refs.registerWH.style.height = this.clientHeight+'px';
            this.$refs.registerWH.style.width = this.clientWidth + 'px';
        }
    }
</script>

<style scoped>
    .registerWrap{background: url("../../images/img-login.png") no-repeat;background-size: cover;}
    .registerBox{width: 100%;height: 535px;background:rgba(255,255,255,0.8);position: absolute;top:50%;left: 50%;transform: translate(-50%,-50%);-webkit-transform:translate(-50%,-50%); }
    .registerBox h2{color: #1D1D1D;font-size: 30px;}
    .registerForm /deep/ .demo-login-Form .el-input__inner{width: 394px;height: 48px;line-height: 48px;color: #1D1D1D; border: 1px solid rgba(125,125,125,1);}
</style>