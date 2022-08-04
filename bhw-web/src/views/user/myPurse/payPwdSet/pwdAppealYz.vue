<template>
    <div class="pwdAppealYz">
        <el-row type="flex" align="middle" style="width:574px;height:18px;font-size:18px;font-weight:normal;color:rgba(29,29,29,1);margin-top: 31px;">
            <el-col style="width: 100px;"><span ><i></i>当前位置：</span></el-col>
            <el-col :span="18"><el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter' }">用户中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{path:'/userCenter',query:{menuActiveFlag:'myPurse'}}">我的钱包</el-breadcrumb-item>
                <el-breadcrumb-item>账户申诉</el-breadcrumb-item>
            </el-breadcrumb></el-col>
        </el-row>
        <div :style="'margin: 0 auto;width: 663px;display: '+display">
            <el-row style="margin-bottom: 4px;">
                <el-steps :active="active" class="stepBox" style="margin-top: 58px;">
                    <el-step title="身份认证" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                    <el-step title="设置操作" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                    <el-step title="完成" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                </el-steps>
            </el-row>
            <template v-if="active==2">
                <div style="margin-top: 98px">
                    <el-row>
                        <label style="width:91px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">设置新密码</label>
                    </el-row>
                    <el-row>
                        <el-input v-model="form1.newPassword" placeholder="请输入新密码" type="password" style="width:425px;height:54px;margin-top: 22px" show-password></el-input>
                    </el-row>
                    <el-row>
                        <el-button class="btnColorOrg" style="margin-top: 41px;width:425px;height:54px;padding: 0;" @click="submit01()">提交</el-button>
                    </el-row>
                </div>
            </template>
            <template v-else-if="active==3">
                <div style="margin-top: 40px;margin-right: 30%; text-align: center;">
                <el-row>
                    <img style="width: 71px;height: 71px;margin-top: 20px" :src="require('@/images/tijiaochenggong.png')"/>
                </el-row>
                <el-row style="margin: 20px 0;">
                    <span style="width:111px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">登录密码更新成功</span>
                </el-row>
                <el-row>
                    <el-button class="btnColorOrg" style="width:347px;height:54px;padding: 0;" @click="relogin()">重新登录</el-button>
                </el-row>
                </div>
            </template>
        </div>
        <el-dialog
                title="使用手机短信验证码"
                :visible.sync="dialogVisible"
                width="503px"
                :before-close="handleClose"
                center class="allDialog">
            <el-form  v-model="form" style="width: 80%; margin: 10px auto;">
                <el-form-item style="width:300px;height:12px;font-size:12px;margin-bottom: 30px;">
                    <span>当前手机号</span><span style="color:#CA0000;">{{this.form.phone}}</span>
                </el-form-item>
                <el-form-item style="margin-top: 14px">
                    <el-row>
                        <el-col :span="15">
                            <el-input  type="phoneyzm" v-model="form.phoneCaptcha" style="width:100%;height:40px;"></el-input>
                        </el-col>
                        <el-col :span="9" style="padding-left: 10px;">
                            <el-button class="btnNoBgdBule" @click="getPhoneCaptcha" style="width:126px;height:40px;padding: 0;" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                            <el-button class="btnColorBlue" style="width: 126px;height: 40px;padding: 0;"  v-if="disabledFlag">{{yzmTitle}}</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg" @click="submit" style="width:100%;height:42px;padding: 0;border: none;">提交认证</el-button>
                </el-form-item>
                <el-row>
                    <span style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(202,0,0,1);">收不到短信验证码？</span>
                </el-row>
                <el-row style="margin-top: 12px">
                    <span style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">请检查手机网络并且核实手机是否屏蔽系统短信，如均正常请重新获取或稍后再试</span>
                </el-row>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {phoneCaptcha,verifyPhonecaptcha,accountPwdAppeal} from "../../../../api/user/payPwdSet";
    export default {
        name: "pwdAppealYz",
        data(){
            return{
                active:3,
                newPwd:"",
                display:"none",
                dialogVisible:true,
                form:{
                    userid:this.$store.state.user.userInfo.id,
                    phone:this.$store.state.user.userInfo.phoneNo,
                    phoneCaptcha:""
                },
                form1:{
                    phone:this.$store.state.user.userInfo.phoneNo,
                    userId:this.$store.state.user.userInfo.id,
                    newPassword:""
                },
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"获取短信校验码",
                disabledFlag:false,
            }
        },
        created(){

        },
        methods:{
            getPhoneCaptcha(){
                if(!(/^1[3456789]\d{9}$/.test(this.form.phone))){
                    this.$message.error('手机号格式错误！')
                }else{
                    phoneCaptcha({phone:this.form.phone}).then(response => {
                        if (response.data){
                            var that = this;
                            this.yzmTimer = setInterval(function(){
                                that.disabledFlag = true;
                                if(that.yzmTime==1){
                                    that.yzmTitle="发送验证码";
                                    that.disabledFlag = false;
                                    window.clearInterval(that.yzmTimer);
                                    that.yzmTimer = null;
                                    that.yzmTime = 120;
                                }else{
                                    that.yzmTitle="请在"+that.yzmTime+"秒内输入";
                                    that.yzmTime= that.yzmTime -1;
                                }
                            },1000);
                        }else{
                            this.$message.error('手机验证码发送失败');
                        }
                    });
                }
            },
            submit(){
                if (this.form.phoneCaptcha.trim()=='') {
                    this.$message.error('请输入验证码!');
                }else{
                    verifyPhonecaptcha(this.form).then(response => {
                        if (response.data){
                            this.display="block";
                            this.dialogVisible=false;
                        }
                    });
                }
            },
            submit01(){
                if (this.form1.newPassword.trim()==''){
                    this.$message.error('请输入新密码!');
                } else{
                    accountPwdAppeal(this.form1).then(response => {
                        if (response.data){
                            this.active=3;
                        }
                    });
                }
            },
            relogin(){
                this.$router.push({path:'viplogin'})
            },
            handleClose(done) {
                this.$confirm('确认关闭？',{
                    confirmButtonClass:'btnColorBlue',
                    cancelButtonClass:'btnColorOrg'
                }).then(_ => {
                    this.$emit('fun','7');
                    done()
                }).catch(_ => {});
            }
        }
    }
</script>

<style scoped>
    .stepBox /deep/.is-finish>.is-text {
        border-radius: 50%;
        border: 2px solid;
        border-color:#F7490D;
    }
    .stepBox /deep/.is-finish>.is-text>.el-step__icon-inner {
        color: #F7490D;
    }
    .stepBox /deep/.el-step__head.is-finish.el-step__line{
        background: #F7490D;
    }
    .stepLineBox /deep/.el-step__head.is-finish>.el-step__line>.el-step__line-inner{
        border-color: #F7490D;
    }
    .stepBox /deep/.el-step__title.is-finish{
        color: #F7490D;
    }
    .stepBox /deep/.is-process>.el-step__icon.is-text {
        border-radius: 50%;
        border: 2px solid;
        border-color:#CCCCCC;
    }
    .stepBox /deep/.is-process>.is-text>.el-step__icon-inner {
        color: #CCCCCC;
    }
    .stepBox /deep/.el-step__head.is-process .el-step__line{
        background: #CCCCCC;
    }
    .stepBox /deep/ .el-step__title.is-process{
        color: #CCCCCC;
    }
</style>