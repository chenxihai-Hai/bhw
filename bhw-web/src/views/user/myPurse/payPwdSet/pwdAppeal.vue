<template>
    <div class="pwdAppeal">
        <el-row type="flex" align="middle" style="height:18px;font-size:18px;font-weight:normal;color:rgba(29,29,29,1);margin-top: 31px;">
            <el-col style="width: 100px;"><span>当前位置：</span></el-col>
            <el-col :span="18">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/userCenter' }">用户中心</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{path:'/userCenter',query:{menuActiveFlag:'myPurse'}}">我的钱包</el-breadcrumb-item>
                    <el-breadcrumb-item>账户申诉</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>
        <div style="width:955px;height:469px;background:rgba(2,78,204,0.05);margin: 79px auto;">
            <el-row style="position: relative;top: 72px">
                <span style="margin-left: 400px;width:100px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">登录密码修改申诉</span>
            </el-row>
            <el-form :label-position="labelPosition" label-width="200px" v-model="form" style="margin-top: 136px;margin-left: 97px">
                <i class="el-icon-s-help" style="color: #F74A0D; margin-left: 170px"/>
                <span  style="margin-left: 15px">请填写可联系到您的手机号，以便于我们通知您申诉结果。</span>
                <el-form-item label="联系手机号:" style="margin-top: 22px">
                    <el-input  type="phone" v-model="form.phone" style="width: 285px;height: 37px;"></el-input>
                </el-form-item>
                <el-form-item label="短信验证码：" style="margin-top: 48px">
                    <el-row>
                        <el-col :span="6">
                            <el-input  type="yzm" v-model="form.phoneCaptcha" style="width: 138px;height: 37px"></el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-button class="btnNoBgdBule" style="width: 140px;height: 37px;padding: 0;" @click="phoneCapth()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                            <el-button class="btnColorBlue"  v-if="disabledFlag">{{yzmTitle}}</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item style="margin-top:65px ;">
                    <el-button class="btnColorOrg bt" @click="bt()">提交</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-dialog
                title="确认"
                :visible.sync="dialogVisible"
                width="342px"
                class="allDialog">
            <div style="text-align: center;">
                <img src="../../../../images/jurassic_warn.png"/>
            </div>
            <div style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-top: 26px;text-align: center">
                请通过线上方式找回密码
            </div>
            <div style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);text-align: center;margin-top: 13px;">
                通过您的联系方式可快速找回密码，快去试试吧
            </div>
            <div style="text-align: center;margin-top: 31px;">
                <el-button class="btnColorOrg btnSave"  type="info" @click="toFd" >找回密码</el-button>
                <el-button class="btnColorBlue btnUp" type="primary" @click="dialogVisible=false">关闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {phoneCaptcha,verifyPhonecaptcha} from "../../../../api/user/payPwdSet";
    export default {
        name: "pwdAppeal",
        data(){
            return{
                labelPosition:'right',
                form:{
                    phone:"",
                    phoneCaptcha:"",
                    userid:this.$store.state.user.userInfo.id
                },
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"获取短信校验码",
                disabledFlag:false,
                dialogVisible:false
            }
        },
        created(){
        },
        methods: {
            phoneCapth(){
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
            toFd(){
                this.$emit('fun',12);
            },
            bt(){
                this.dialogVisible = true;
               /* this.$confirm('是否确认更改该手机号', '确认', {
                    confirmButtonText: '修改密码',
                    cancelButtonText: '关闭',
                    confirmButtonClass:'btnColorBlue',
                    cancelButtonClass:'btnColorOrg',
                    type: 'warning'
                }).then(() => {

                    this.$emit('fun',12);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });*/
            }
        }
    }
</script>

<style scoped>
    button.bt{
        width:132px;
        height:37px;padding: 0;border: none;
        border-radius:5px;

    }
</style>