<template>
    <div class="personal-register">
        <div  style="width: 617px;margin-left: 20px;">
            <el-form :model="personalRegister" ref="personalRegister" :rules="rules" label-width="150px"   class="demo-ruleForm">
                <el-form-item label="手机号:" prop="phone" :rules="[{required: true, message: '手机号不能为空'}]">
                    <el-input v-model="personalRegister.phone" type="phone"></el-input>
                </el-form-item>
                <el-form-item label="登录密码:" prop="pass" >
                    <el-input v-model="personalRegister.pwd" type="password" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认登录密码:" prop="checkPass">
                    <el-input v-model="personalRegister.checkPass" type="password" show-password></el-input>
                </el-form-item>
                <el-form-item label="手机验证码:" prop="phoneCaptcha" :rules="[{required:true,message:'手机验证码不能为空'}]">
                    <el-input v-model="personalRegister.phoneCaptcha" type="phoneCaptcha"></el-input>
                    <!--<el-button class="sendCode" @click="Yzmsubmit()">发送验证码</el-button>-->
                    <el-button class="btnNoBgdBule" style="margin-left: 10px;" @click="Yzmsubmit()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                    <el-button class="btnColorBlue" style="margin-left: 10px;cursor: default;" v-if="disabledFlag">{{yzmTitle}}</el-button>
                </el-form-item>
                <el-form-item label="我阅读并接受" prop="resource">
                    <el-checkbox v-model="agreementChecked" class="allCheck" @change="agreementChecked==!agreementChecked"></el-checkbox>
                    <el-link @click="showAgreementDialog=true">《北部湾国际技术转移转化中心公共服务平台开户协议》</el-link>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg" style="width:383px;height:46px;border:none;padding: 0;" @click="submitForm('personalRegister',personalRegister)">立即注册</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-dialog :visible.sync="showAgreementDialog" width="50%" class="dialogAgreement">
            <agreement-info></agreement-info>
        </el-dialog>

<!--        <div style="width:248px;height:169px;background:rgba(255,255,255,1);border:0px solid rgba(204,204,204,1);position: relative;right: -680px;top: -200px">-->
<!--            <el-collapse>-->
<!--                <el-collapse-item title="获取验证码" style="background-color: rgba(202,0,0,1);">-->
<!--                        <div style="height: 27px;background:rgba(204,204,204,1);"><span style="width:74px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-left: 10px;margin-top: 5px">图形验证码</span></div>-->
<!--                        <el-form>-->
<!--                            <el-form-item  >-->
<!--                                    <el-input v-model="yzm.vCode" style="width: 117px;height: 32px"></el-input>-->
<!--                                    <img :src="yzm.src" style="width: 68px;height: 30px;margin-left: 17px" @click="captcha()"/>-->
<!--                            </el-form-item>-->
<!--                            <el-form-item style="margin-top: 69px;margin-left: 171px">-->
<!--                                <button   @click="Yzmsubmit()">确认</button>-->
<!--                                <button   @click="YzmRest('yzm')" style="margin-left: 5px">取消</button>-->
<!--                            </el-form-item>-->
<!--                        </el-form>-->
<!--                </el-collapse-item>-->
<!--            </el-collapse>-->
<!--        </div>-->
    </div>
</template>

<script>
    import agreementInfo from "./agreementInfo";
    import {captchaReq,checkCaptchaReq,personalRegisterReq} from "../../api/register/register";
    export default {
        name: "personal-register",
        components:{agreementInfo:agreementInfo},
        data(){
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.personalRegister.checkPass !== '') {
                        this.$refs.personalRegister.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.personalRegister.pwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return{
                agreementChecked:false,
                showAgreementDialog:false,
                personalRegister:{
                    phoneCaptcha:"",
                    phone:"",
                    pwd:"",
                    checkPass:"",
                    resource:""
                },
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false,
                yzm:{
                    randomCode:"",
                    vCode:"",
                    src:""
                },
                rules:{
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ]

                }
            }
        },
        created(){
            var randomCode=Math.random();
            this.yzm.randomCode =randomCode;
            this.yzm.src="http://localhost:8081/api/register/captcha?randomCode="+randomCode;
        },
        methods:{
            personalClick(){
                this.flag=true;
            },
            enterpriseClick(){
                this.flag=false;
            },
            submitForm(formName,personalRegister){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var parms={phoneCaptcha:personalRegister.phoneCaptcha,
                            phone:personalRegister.phone,
                            pwd:personalRegister.pwd
                        }
                        if(this.agreementChecked == false){
                            this.$message({message:'请先同意协议!',type:'error'});
                            return;
                        }
                        personalRegisterReq(parms).then(response => {
                            if(response.retCode==0){
                                this.$message({
                                    message: '注册成功!',
                                    type: 'success'
                                });
                                this.$router.push({path:"/viplogin"});
                            }else{
                                this.$message({
                                    message: response.message,
                                    type: 'warning'
                                });
                            }
                         });
                    } else {
                        return false;
                     }
                 });

            },
            getphoneCaptcha(){

            },
            captcha(){
                var randomCode=Math.random();
                this.yzm.randomCode =randomCode;
                this.yzm.src="http://localhost:8081/api/register/captcha?randomCode="+randomCode;
            },
            Yzmsubmit(){
               /* var randomCode=this.yzm.randomCode;
                var vCode=this.yzm.vCode;*/
                var randomCode = "1234";
                var vCode="1234";
                var phone=this.personalRegister.phone;
                if(vCode!="" && phone!=""){
                    checkCaptchaReq({randomCode:randomCode,vCode:vCode,phone:phone}).then(response => {
                        var flag=response.data;
                        if(flag){
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
                        }
                    });
                }else {
                    this.$message({
                        message: "请正确输入",
                        type: 'warning'
                    });
                }
            },
            YzmRest(formName){
                this.$refs[formName].resetFields();
            }
        }
    }
</script>
<style scoped>
    .demo-ruleForm /deep/ .el-input{width: 45%;}
    .sendCode{height: 38px;background: #FFCD55;margin-left: 20px;border: 1px solid #FFCD55;}
    /deep/ .sendCode.el-button.el-button--default span{color: #CA0000; }
    .dialogAgreement /deep/ .el-dialog__header{padding: 0;}
    .dialogAgreement /deep/ .el-dialog__headerbtn{top: 5px;right: 10px;}
    .dialogAgreement /deep/ .el-dialog__body{padding: 20px;}
</style>

