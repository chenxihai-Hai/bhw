<template>
    <div class="updateByPhoneBankCard">
        <div style="width:980px;height:52px;background:rgba(248,255,208,1);margin:0 auto;padding-top: 17px;padding-left: 27px">
            <span style="width:109px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">修改支付密码</span>
        </div>
        <el-row style="margin-bottom: 4px;">
            <el-steps :active="active" class="stepBox" style="margin-top: 58px;margin-left: 27px">
                <el-step title="验证身份" :class="active>1?'stepLineBox':''"  ></el-step>
                <el-step title="重置支付密码" :class="active>1?'stepLineBox':''" ></el-step>
                <el-step title="完成" ></el-step>
            </el-steps>
        </el-row>
        <template v-if="active==1">
            <el-form :label-position="labelPosition" label-width="200px" v-model="form1" style="margin-top: 55px;margin-left: 97px;">
                <el-form-item label="选择验证卡：">
                    <el-select v-model="form1.bankType" style="width: 374px;height: 32px;">
                        <el-option label="信用卡" value="1"></el-option>
                        <el-option label="储蓄卡" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="银行卡号：">
                    <el-input  type="phone" v-model="form1.bankCard" style="width: 374px;height: 32px;"></el-input>
                </el-form-item>
                <el-form-item label="持卡人身份证号：">
                    <el-input  type="phone" v-model="form1.idCard" style="width: 374px;height: 32px;"></el-input>
                </el-form-item>
                <el-form-item label="已绑定手机号：">
                    <span style="width: 374px;height: 32px;">{{bindPhone}}</span>
                </el-form-item>
                <el-form-item label="短信验证码：">
                    <el-row>
                        <el-col :span="4">
                            <el-input  type="phoneyzm" v-model="form1.phoneCaptcha" style="width: 100%;height: 32px;"></el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-button class="btnNoBgdBule bt" @click="phoneCaptcha" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                            <el-button class="btnColorBlue" style="margin-left: 5px;width: 120px;height: 36px; padding: 0;border: none;"  v-if="disabledFlag">{{yzmTitle}}</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label="验证码：">
                    <el-row type="flex" align="middle">
                        <el-col :span="4">
                            <el-input  type="yzm" v-model="form1.captcha" style="width: 100%;height: 32px;"></el-input>
                        </el-col>
                        <el-col :span="3" style="margin-left: 5px;">
                            <el-image style="width: 100%;height: 32px;vertical-align: -10px;" :src="src"></el-image>
                        </el-col>
                        <el-col :span="4" style="margin-left: 5px;">
                            <p>看不清？<el-link  @click="captcha()" style="vertical-align: 1px;">换一张</el-link></p>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg submitBt" @click="submit01()">提交</el-button>
<!--                    <el-link style="margin-left: 14px;" href="https://element.eleme.io" target="_blank">返回</el-link>-->
                </el-form-item>
            </el-form>
        </template>
        <template v-if="active==2">
            <el-form :label-position="labelPosition" label-width="200px" v-model="form" style="margin-top: 55px;margin-left: 97px">
                <el-form-item label="请设置6位数字支付密码:">
                    <el-input  type="password" v-model="form.pwd" style="width: 211px;height: 32px" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认6位数字支付密码:">
                    <el-input  type="password" v-model="ckeckPassword" style="width: 211px;height: 32px" show-password></el-input>
                </el-form-item>
                <el-form-item label="验证码:">
                    <el-row>
                        <el-col :span="3">
                            <el-input  type="yzm" v-model="form.vCode" style="width: 98px;height: 32px"></el-input>
                        </el-col>
                        <el-col :span="4">
                            <el-image style="width: 70px;height: 32px;margin-left: 43px" :src="src"></el-image>
                        </el-col>
                        <el-col :span="4">
                            <span>看不清？<el-link  @click="captcha()">换一张</el-link></span>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg submitBt" @click="submit02()">提交</el-button>
                </el-form-item>
            </el-form>
        </template>
        <template v-if="active==3">
            <div class="updateScuBox">
                <i class="el-icon-circle-check"></i>
                <p>恭喜，支付密码设置成功！</p>
            </div>
        </template>
    </div>
</template>

<script>
    import {verifyIdentity,setPayPwd,phoneCaptcha} from "../../../../api/user/payPwdSet";
    export default {
        name: "updateByPhoneBankCard",
        data(){
            return{
                active:1,
                labelPosition:'right',
                randomCode:"",
                src:"",
                ckeckPassword:"",
                bindPhone:this.$store.state.user.userInfo.phoneNo,
                form1:{
                    phone:"",
                    type:"2",
                    randomCode:"",
                    captcha:"",
                    phoneCaptcha:"",
                    bankType:"",
                    bankCard:"",
                    idCard:"",
                    userId:this.$store.state.user.userInfo.id
                },
                form:{
                    pwd:"",
                    randomCode:"",
                    vCode:"",
                    userid:this.$store.state.user.userInfo.id
                },
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false
            }
        },
        created(){
            this.captcha();
        },
        methods:{
            submit01(){
                this.form1.randomCode=this.randomCode;
                this.form1.phone=this.bindPhone;
                verifyIdentity(this.form1).then(response => {
                    if (response.data) {
                        this.active=2
                        var randomCode=Math.random();
                        this.randomCode =randomCode;
                        this.src=this.$store.state.user.randomCodeUrl+randomCode;
                    }else {
                        this.$message.error('身份验证失败');
                    }
                });
            },
            submit02(){
                this.form.randomCode=this.randomCode;
                setPayPwd(this.form).then(response => {
                    if (response.data) {
                        this.active=3
                    }else{
                        this.$message.error('验证失败');
                    }
                });

            },
            phoneCaptcha(){
                if(!(/^1[3456789]\d{9}$/.test(this.bindPhone))){
                    this.$message.error('手机号格式错误！')
                    return;
                }
                phoneCaptcha({phone:this.bindPhone}).then(response => {
                    if (response.data) {
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
                })
            },
            captcha(){
                var randomCode=Math.random();
                this.randomCode =randomCode;
                this.src=this.$store.state.user.randomCodeUrl+randomCode;
            }
        }
    }
</script>

<style scoped>
    p.pclass{
        width:400px;
        height:12px;
        font-size:12px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(125,125,125,1);
        margin-left: 145px;
    }
    .btnNoBgdBule.bt{
        width:83px;
        height:36px;
        margin-left: 5px;
        padding: 0;
        font-size:12px;
        font-family:'Microsoft YaHei';
        font-weight:400;
    }
    .btnColorOrg.submitBt{
        width:132px;height:37px;border-radius:5px;margin-top: 51px;padding: 0;border: none;
    }
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