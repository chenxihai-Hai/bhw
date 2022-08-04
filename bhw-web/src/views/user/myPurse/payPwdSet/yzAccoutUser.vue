<template>
    <div class="yzAccoutUser">
        <template v-if="flag==0">
            <div style="margin: 0 auto;width: 445px;">
                <img style="width:76px;height:88px;margin-top: 77px;margin-left: 184px;" :src="require('@/images/anquan.png')">
                <hr/>
                <h1 style="margin-top: 36px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);text-align: center">为确认是您本人操作，请选择一下任一方式完成身份认证</h1>
                <hr/>
                <el-button class="btnColorOrg bt1" @click="bt('1')">使用 关联银行卡信息</el-button>
                <hr/>
                <el-button class="btnColorBlue bt2" @click="bt('2')">使用 关联新银行卡信息</el-button>
            </div>
        </template>
        <template v-else>
            <div style="margin: 0 auto;width: 663px">
            <el-row style="margin-bottom: 4px;">
                <el-steps :active="active" class="stepBox" style="margin-top: 58px;margin-left: 27px">
                    <el-step title="身份认证" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                    <el-step title="设置操作" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                    <el-step title="完成" :class="active>1?'stepLineBox':''" style="width: 221px"></el-step>
                </el-steps>
            </el-row>
            <template v-if="active==2">
                <div style="margin-top: 98px;margin-left: 4%;">
                    <el-row>
                        <label style="width:91px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">设置新手机号</label>
                    </el-row>
                    <el-row>
                        <el-input v-model="newPhone" placeholder="请输入新手机号" type="newPhone" style="width:425px;height:54px;margin-top: 22px"></el-input>
                    </el-row>
                    <el-row>
                        <el-button class="btnColorOrg bt" @click="submit()">提交</el-button>
                    </el-row>
                </div>
            </template>
            <template v-else-if="active==3">
                <div style="text-align: center;margin-right: 25%;">
                <el-row style="margin-top: 20px;">
                    <img style="width: 71px;height: 71px;margin-top: 20px" :src="require('@/images/tijiaochenggong.png')"/>
                </el-row>
                <el-row style="margin-top: 20px;">
                    <span style="width:111px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">手机号更新成功</span>
                </el-row>
                <el-row>
                    <el-button class="btnColorOrg bt" @click="relogin()">重新登录</el-button>
                </el-row>
                </div>
            </template>
            </div>
        </template>
        <el-dialog
                title="已绑定银行卡"
                :visible.sync="dialogVisible"
                width="503px"
                :before-close="handleClose"
                center class="allDialog">
            <el-form v-model="form" style="margin-top: 20px;text-align: center;">
                <el-form-item>
                    <el-select v-model="form.bankType" placeholder="选择银行卡" style="width: 397px;height: 44px;">
                        <el-option label="储蓄卡" value="1"></el-option>
                        <el-option label="信用卡" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input  type="bankCard" v-model="form.bankCard" style="width: 397px;height: 44px;" placeholder="请输入银行卡"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input  type="idCard" v-model="form.idCard" style="width: 397px;height: 44px;" placeholder="请输入身份证号"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg" style="width: 397px;height: 44px;padding: 0;border: none;" @click="next()">下一步</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog
                title="银行卡添加"
                :visible.sync="dialogVisible01"
                width="700px"
                :before-close="handleClose"
                center class="allDialog">
            <el-form :label-position="labelPosition" label-width="200px" v-model="form1" style="margin-top: 20px;text-align: center;">
                <el-form-item label="真实姓名">
                    <span style="width: 374px;height: 32px;margin-left: 16px">任**</span>
                </el-form-item>
                <el-form-item label="身份证">
                    <span style="width: 374px;height: 32px;margin-left: 16px">4306211******5412</span>
                </el-form-item>
                <el-form-item label="银行卡号">
                    <el-input  type="phone" v-model="form1.bankCard" style="width: 374px;height: 32px;margin-left: 16px"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input  type="phone" v-model="form1.phone" style="width: 374px;height: 32px;margin-left: 16px"></el-input>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-row>
                        <el-col :span="6">
                            <el-input  type="phoneyzm" v-model="form1.phoneCaptcha" style="width: 98px;height: 32px;margin-left: 16px"></el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-button class="btnNoBgdBule"  @click="getPhoneCaptcha">获取验证码</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item  prop="resource">
                    <el-radio-group class="allRadio" v-model="resource">
                        <el-radio label="《北部湾国际技术转移转化中心支付协议》"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg" @click="agrument">同意协议并确定</el-button>
                </el-form-item>

            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {accountAppealverify,accountPhoneAppeal,phoneCaptcha} from "../../../../api/user/payPwdSet";
    export default {
        name: "yzAccoutUser",
        data(){
            return{
                resource:"",
                labelPosition:'right',
                flag:"0",
                active:'2',
                dialogVisible: false,
                dialogVisible01:false,
                form:{
                    type:"2",
                    bankCard:"",
                    idCard:"",
                    userId:this.$store.state.user.userInfo.id
                },
                form1:{
                    type:"3",
                    userId:this.$store.state.user.userInfo.id,
                    bankCard:"",
                    idCard:"430621199606085412",
                    phone:"",
                    phoneCaptcha:""
                },
                newPhone:"",

            }
        },
        created(){

        },
        methods: {
            bt(data){
                if (data ==1){
                    this.dialogVisible=true;
                } else if (data ==2) {
                    this.dialogVisible01=true;
                }
            },
            next(){
                accountAppealverify(this.form).then(response => {
                    if (response.data) {
                        this.flag="1";
                        this.dialogVisible=false;
                    }else{
                        this.$message.error('身份验证失败!');
                    }
                });
            },
            submit(){
                var userid=this.$store.state.user.userInfo.id;
                accountPhoneAppeal({phone:this.newPhone,userId:userid}).then(response => {
                    if (response.data) {
                        this.active=3;
                    }else{
                        this.$message.error('申诉失败!');
                    }
                });
            },
            relogin(){
                this.$router.push({path:'viplogin'})
            },
            getPhoneCaptcha(){
                if (this.form1.phone.trim()=='') {
                    this.$message.error('请输入手机号!');
                }else{
                    phoneCaptcha({phone:this.form1.phone}).then(response => {
                        if (response.data){
                            this.$message.info('验证码已发送!');
                        }
                    });
                }
            },
            agrument(){
                accountAppealverify(this.form1).then(response => {
                    if (response.data) {
                        this.flag="1";
                        this.dialogVisible01=false;
                    }else{
                        this.$message.error('身份验证失败!');
                    }
                });
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            }
        }
    }
</script>

<style scoped>
    button.bt{
        width:425px;
        height:54px;
        margin-top: 41px;
        background:linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);
    }
.btnColorOrg.bt1{
    width:445px;
    height:42px;
    margin-top: 40px;padding: 0;border: none;
}
.btnColorBlue.bt2{
    width:445px;
    height:42px;
    margin-top: 18px;padding: 0;border: none;
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