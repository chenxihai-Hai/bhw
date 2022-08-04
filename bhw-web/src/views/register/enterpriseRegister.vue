<template>
    <div class="enterprise-register">
        <el-form  :model="req" ref="req" label-width="230px" :rules="rules" class="demo-ruleForm">
            <el-form-item label="登录账号:" prop="account" >
                <el-input v-model="req.account" placeholder="以字母开头，英文、数字、下划线和减号 6-20位" type="account"></el-input>
            </el-form-item>
            <el-form-item label="公司名称:" prop="companyName"  :rules="[{required: true, message: '公司名称不能为空'}]">
                <el-input v-model="req.companyName" type="companyName"></el-input>
            </el-form-item>
            <el-form-item label="登录密码:" prop="pass" >
                <el-input v-model="req.pwd" type="password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认登录密码:" prop="checkPass">
                <el-input v-model="req.checkPass" type="password" show-password></el-input>
            </el-form-item>
            <el-form-item label="公司电话:" prop="companyPhone" :rules="[{required: true, message: '公司电话不能为空'}]">
                <el-input v-model="req.companyPhone"  type="companyPhone"></el-input>
            </el-form-item>
            <el-form-item label="是否三证合一（注：至少填一项）">
                <el-radio-group v-model="form.flag" class="allRadio">
                    <el-radio label="0" >是</el-radio>
                    <el-radio label="1">否</el-radio>
                </el-radio-group>
            </el-form-item>
            <template v-if="form.flag==0">
                <el-form-item label="统一社会信用代码证:" prop="uniteCode" :rules="[{required: true, message: '统一社会信用代码证不能为空'}]">
                    <el-input v-model="req.uniteCode" type="uniteCode"></el-input>
                </el-form-item>
            </template>
            <template v-else-if="form.flag==1">
                <el-form-item label="营业执照号:"  prop="businessLicense" :rules="[{required: true, message: '营业执照号不能为空'}]">
                    <el-input v-model="req.businessLicense" type="businessLicense"></el-input>
                </el-form-item>
                <el-form-item label="组织机构代码证:" prop="agencyCode" :rules="[{required: true, message: '组织机构代码不能为空'}]">
                    <el-input v-model="req.agencyCode" type="agencyCode"></el-input>
                </el-form-item>
                <el-form-item label="税务登记证:" prop="taxRegisterCode" :rules="[{required: true, message: '税务登记证不能为空'}]">
                    <el-input v-model="req.taxRegisterCode" type="taxRegisterCode"></el-input>
                </el-form-item>
            </template>
            <template v-else>
            </template>
            <el-form-item label="联系人手机号:" prop="contactPhone" :rules="[{required: true, message: '联系人手机号不能为空'}]">
                <el-input v-model="req.contactPhone"  type="contactPhone"></el-input>
            </el-form-item>
            <el-form-item label="手机验证码:" prop="phoneCaptcha" :rules="[{required: true, message: '手机验证不能为空'}]" style="width:100%;display: inline-block;">
                <el-input v-model="req.phoneCaptcha" type="phoneCaptcha"></el-input>
                <el-button class="btnNoBgdBule" style="margin-left: 10px;" @click="Yzmsubmit()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                <el-button class="btnColorBlue" style="margin-left: 10px;cursor: default;" type="info" v-if="disabledFlag">{{yzmTitle}}</el-button>
            </el-form-item>
<!--            <div class="yzmBtn">-->
<!--                <el-collapse>-->
<!--                    <el-collapse-item title="获取验证码">-->
<!--                        <div style="height: 27px;background:rgba(204,204,204,1);">-->
<!--                            <span style="width:74px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-left: 10px;margin-top: 5px">图形验证码</span>-->
<!--                        </div>-->
<!--                        <el-form>-->
<!--                            <el-form-item  >-->
<!--                                <el-input v-model="yzm.vCode" style="width: 117px;height: 32px"></el-input>-->
<!--                                <img :src="yzm.src" style="width: 68px;height: 30px;margin-left: 17px" @click="captcha()"/>-->
<!--                            </el-form-item>-->
<!--                            <el-form-item style="margin-top: 69px;margin-left: 171px">-->
<!--                                <button   @click="Yzmsubmit()">确认</button>-->
<!--                                <button   @click="YzmRest('yzm')" style="margin-left: 5px">取消</button>-->
<!--                            </el-form-item>-->
<!--                        </el-form>-->
<!--                    </el-collapse-item>-->
<!--                </el-collapse>-->
<!--            </div>-->
            <!--<el-form-item label="请选择默认服务类型:" prop="type">
                <el-col :span="11">
                    <el-checkbox-group :max="2" v-model="typeList">
                        <el-checkbox label="1" name="typeList">技术合同/科技成果认定</el-checkbox>
                        <el-checkbox label="2" name="typeList">知识产权</el-checkbox>
                        <el-checkbox label="3" name="typeList">挂牌交易</el-checkbox>
                        <el-checkbox label="4" name="typeList">经纪人服务</el-checkbox>
                        <el-checkbox label="5" name="typeList">财税服务</el-checkbox>
                        <el-checkbox label="6" name="typeList">法律服务</el-checkbox>
                        <el-checkbox label="7" name="typeList">科技咨询</el-checkbox>
                        <el-checkbox label="8" name="typeList">科技金融</el-checkbox>
                    </el-checkbox-group>
                </el-col>
                <el-col :span="8">
                    <div style="width: 200px;margin-left: 50px">
                        <h1 style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(204,204,204,1);">默认可选择两项</h1>
                        <h2 style="font-size:12px;color:rgba(204,204,204,1);">提醒：现在可选 两项服务类型作为 普通用户的默认服务，如需拓展更多业务，可购买增值服务。</h2>
                    </div>
                </el-col>
            </el-form-item>-->
            <el-form-item label="我阅读并接受" prop="resource">
<!--                <el-radio-group v-model="resource">-->
<!--                    <el-radio></el-radio>-->
<!--                </el-radio-group>-->
<!--                <el-link @click="showAgreementDialog=true">《北部湾国际技术转移转化服务平台开户协议》</el-link>-->
                <el-checkbox v-model="agreementChecked" class="allCheck" @change="agreementChecked==!agreementChecked"></el-checkbox>
                <el-link @click="showAgreementDialog=true">《北部湾国际技术转移转化中心公共服务平台开户协议》</el-link>
            </el-form-item>
            <el-form-item>
                <el-button class="btnColorOrg" style="width:383px;height:46px;border:none;padding: 0;" @click="submitForm('req',req)" >立即注册</el-button>
            </el-form-item>
        </el-form>
        <el-dialog :visible.sync="showAgreementDialog" width="50%" class="dialogAgreement">
            <agreement-info></agreement-info>
        </el-dialog>
    </div>
</template>

<script>
    import agreementInfo from "./agreementInfo";
    import {captchaReq,checkCaptchaReq,enterpriseRegisterReq} from "../../api/register/register";
    export default {
        name: "enterprise-register",
        components:{agreementInfo:agreementInfo},
        data(){
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.checkPass !== '') {
                        this.$refs.req.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.req.pwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            var validatePass3 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('登录账号不能为空'));
                } else {
                    var reg = /^[a-zA-Z]([-_a-zA-Z0-9]{6,20})$/;
                    if (!reg.test(value)) {
                        callback(new Error('请以字母开头，英文、数字、下划线和减号 6-20位'));
                    }else{
                        callback();
                    }
                }
            };
            return{
                agreementChecked:false,
                showAgreementDialog:false,
                form:{
                    flag:"-1"
                 },
                yzm:{
                    randomCode:"",
                    vCode:"",
                    src:""
                },
                checkPass:"",
                resource:"",
                typeList:[],
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false,
                req:{
                    account:"",
                    companyName:"",
                    pwd:"",
                    companyPhone:"",
                    type:"",
                    uniteCode:"",
                    businessLicense:"",
                    agencyCode:"",
                    taxRegisterCode:"",
                    contactPhone:"",
                    phoneCaptcha:"",
                    serverType01:"",
                    serverType02:"",
                    checkPass:""
                },
                rules:{
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                    account:[
                        {validator: validatePass3, trigger: 'blur'}
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
             captcha(){
                 var randomCode=Math.random();
                 this.yzm.randomCode =randomCode;
                 this.yzm.src="http://localhost:8081/api/register/captcha?randomCode="+randomCode;
             },
             Yzmsubmit(){
                 // var randomCode=this.yzm.randomCode;
                 // var vCode=this.yzm.vCode;
                 var randomCode="1234";
                 var vCode="1234";
                 var phone=this.req.contactPhone;
                 if(vCode!="" && phone!=""){
                     checkCaptchaReq({randomCode:randomCode,vCode:vCode,phone:phone}).then(response => {
                         var flag=response.data;
                         if(flag){
                             /*this.$message({
                                 message: "手机验证码发送成功，请查收!",
                                 type: 'success'
                             });*/
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
             },
             submitForm(formName,req){
                 var flag=this.form.flag;
                 if(flag=="-1"){
                     alert("选择是否三证合一")
                 }else {
                     this.$refs[formName].validate((valid) => {
                         if (valid) {
                             req.type=this.form.flag;
                             var i=0;
                             for(var item in this.typeList){
                                 if(i==0 && item==null){
                                     break;
                                 }else if((i==0 && item!=null)){
                                     req.serverType01=item;
                                 }else{
                                     req.serverType02=item;
                                 }
                                 i++;
                             }
                             if(this.agreementChecked == false){
                                 this.$message({message:'请先同意协议!',type:'error'});
                                 return;
                             }
                             // alert(JSON.stringify(req));
                             enterpriseRegisterReq(req).then(response => {
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
                         }
                     });
                 }
             }
         }
    }
</script>

<style scoped>
    .demo-ruleForm /deep/ .el-input{width: 45%;}
    /deep/ .yzmBtn{width:248px;height:169px;background:rgba(255,255,255,1);border:0px solid rgba(204,204,204,1);display: inline-block;position: relative;right: -480px;top: -65px;}
    /deep/ .sendCode{height: 38px;background: #FFCD55;margin-left: 20px;border: 1px solid #FFCD55;}
    /deep/ .sendCode.el-button.el-button--default span{color: #CA0000; }
    .dialogAgreement /deep/ .el-dialog__header{padding: 0;}
    .dialogAgreement /deep/ .el-dialog__headerbtn{top: 5px;right: 10px;}
    .dialogAgreement /deep/ .el-dialog__body{padding: 20px;}
</style>