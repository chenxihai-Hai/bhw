<template>
    <div class="securityCenter">
        <el-row style="margin-top: 30px;">
            <el-form ref="ruleForm" label-width="120px"  status-icon :rules="rules" :hide-required-asterisk="true" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                <el-form-item label="用户:">
                    <el-input :disabled="true" :value="$store.state.user.userInfo.userName" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="密码类型:">
                    <el-radio  v-model="ruleForm.pwType" class="radioInfo"  label="1">登录密码</el-radio>
                    <el-radio  v-model="ruleForm.pwType" class="radioInfo"  label="2" v-if="dealPwFlag==false">支付密码</el-radio>
                    <el-row>
                        <el-col style="color: red;" v-if="ruleForm.pwType==1">
                            提醒:您正在进行的操作是修改账户登录密码,请谨慎操作!
                        </el-col>
                        <el-col style="color: red;" v-else>
                            提醒:您正在进行的操作是修改账户支付密码,请谨慎操作!
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label="原始密码:">
                    <el-input placeholder="请输入密码" v-model="ruleForm.oldPw" style="width: 300px;"  show-password></el-input>
                    <span style="margin-left: 20px;">
                        <template v-if="ruleForm.pwType==1">
                            请输入您的原登录密码
                        </template>
                        <template v-else>
                            请输入您的原支付密码
                        </template>
                        <el-link :underline="false" @click="forgetPage" type="primary" style="color: #024ECC;vertical-align: 0px;">忘记密码?</el-link></span>
                </el-form-item>
                <el-form-item label="新密码:" prop="newPw">
                    <el-input placeholder="请输入密码" v-model="ruleForm.newPw" autocomplete="off" style="width: 300px;" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码:" prop="verifyPw">
                    <el-input placeholder="请输入密码" v-model="ruleForm.verifyPw" autocomplete="off" style="width: 300px;" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg" @click="onSubmit('ruleForm')">修改</el-button>
                    <el-button class="btnColorBlue">取消</el-button>
                </el-form-item>
            </el-form>
        </el-row>
    </div>
</template>

<script>
    import {upUserPayPw,upUserPw} from "../../../api/user/user";
    import {getAccountStatus} from "../../../api/user/payPwdSet";
    import {removeToken} from '../../../utils/auth';
    export default {
        name: "securityCenter",
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.verifyPw !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (this.ruleForm.verifyPw !== this.ruleForm.newPw) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm:{
                    pwType:"1",
                    account:this.$store.state.user.userInfo.account,
                    userId:this.$store.state.user.userInfo.id,
                    phone:this.$store.state.user.userInfo.phoneNo,
                    oldPw:null,
                    newPw:null,
                    verifyPw:null
                },
                dealPwFlag:false,
                rules: {
                    newPw: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    verifyPw: [
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            }
        },
        mounted(){
            getAccountStatus({userid:this.$store.state.user.userInfo.id}).then(response => {
                this.dealPwFlag=response.data;
            });
        },
        methods:{
            forgetPage(){
                if(this.ruleForm.pwType==1){
                    this.$router.push({
                        path: '/forgetPassword'
                    });
                }
            },
            onSubmit(formName) {
                if (this.ruleForm.oldPw.trim()=='') {
                    return;
                }
                if (this.ruleForm.verifyPw !== this.ruleForm.newPw) {
                    return;
                }
                if(this.ruleForm.pwType=='1'){
                    upUserPw(this.ruleForm).then(response=>{
                        if(response.retCode==-1){
                            this.$message.error('原始密码输入有误!');
                        }else {
                            this.$message({
                                message: '修改成功,2秒后即将重新登录',
                                type: 'success'
                            });
                            var that = this;
                            setTimeout(function () {
                                removeToken();
                                that.$router.push({
                                    path: '/viplogin'
                                });
                            }, 2000);
                        }
                    });
                }else{
                    upUserPayPw(this.ruleForm).then(response=>{
                        if(response.retCode==-1){
                            this.$message.error('原始密码输入有误!');
                        }else{
                            this.$message({
                                message: '修改成功!',
                                type: 'success'
                            });
                        }
                    });
                }
            }
        }
    }
</script>

<style scoped>

</style>