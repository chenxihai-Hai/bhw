<template>
    <div class="userManage" style="margin: 0 20px;">
        <el-row style="margin-bottom: 4px;">
            <el-menu default-active="-1" class="el-menu-demo" mode="horizontal" @select="userManageMenuSelect">
                <el-menu-item index="-1">用户设置</el-menu-item>
                <el-menu-item index="0">安全中心</el-menu-item>
            </el-menu>
        </el-row>
        <el-row v-if="userManageMenuActive=='-1'">
            <el-tabs  type="card" v-model="userDetailTabs" :before-leave="beforeLeaveFn" @tab-click="tabsClick" class="user-tab">
                <el-tab-pane label="用户资料" name="0">
                    <el-row v-if="editSwitch && activeTabs=='0'">
                        <el-row style="margin: 25px 0 10px;">
                            <el-col :span="10" :offset="1" style="color: #7D7D7D;font-size: 18px;font-weight:normal;">您好! {{userInfo.userName}} 欢迎登录!</el-col>
                            <el-col :span="1" :offset="22"><el-button type="primary" class="btnColorBlue" size="small" @click="editSwitchCk">编辑</el-button></el-col>
                        </el-row>
                        <template v-if="userInfo.type==1">
                            <el-form ref="userInfo" label-width="120px" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                                <el-form-item label="手机号码:">
                                    <el-input :disabled="true" v-model="userInfo.phoneNo"></el-input>
                                </el-form-item>
                                <el-form-item label="真实姓名:">
                                    <el-input :disabled="true" v-model="userInfo.userName"></el-input>
                                </el-form-item>
                                <el-form-item label="身份证号:">
                                    <el-input :disabled="true" v-model="userInfo.idCard"></el-input>
                                </el-form-item>
                                <el-form-item label="QQ邮箱:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.email"></el-input>
                                </el-form-item>
                                <el-form-item label="微信号:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.wx"></el-input>
                                </el-form-item>
                                <el-form-item label="QQ号:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.qq"></el-input>
                                </el-form-item>
                            </el-form>
                            <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                                <el-form-item label="头像:">
                                    <el-avatar :size="50" :src="userInfo.photo"></el-avatar>
                                </el-form-item>
                                <el-form-item label="昵称:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.nickName"></el-input>
                                </el-form-item>
                                <el-form-item label="性别:">
                                    <el-radio  v-model="userInfo.sex" :label="1" disabled >男</el-radio>
                                    <el-radio  v-model="userInfo.sex" :label="2" disabled >女</el-radio>
                                    <!--<el-input :disabled="editSwitch" v-model="userInfo.sex"></el-input>-->
                                </el-form-item>
                                <el-form-item label="生日:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.birthday"></el-input>
                                </el-form-item>
                            </el-form>
                            <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                            <el-form-item label="单位名称:">
                                <el-input :disabled="editSwitch" v-model="userInfo.unitName"></el-input>
                            </el-form-item>
                            <el-form-item label="职务:">
                                <el-input :disabled="editSwitch" v-model="userInfo.duty"></el-input>
                            </el-form-item>
                            <el-form-item label="职业资格:">
                                <el-input :disabled="editSwitch" v-model="userInfo.qualification"></el-input>
                            </el-form-item>
                            <el-form-item label="学历:">
                                <el-input :disabled="editSwitch" v-if="userInfo.education" :value="education[userInfo.education-1].label"></el-input>
                                <el-input :disabled="editSwitch" v-else></el-input>
                            </el-form-item>
                            <el-form-item label="学校:">
                                <el-input :disabled="editSwitch" v-model="userInfo.school"></el-input>
                            </el-form-item>
                        </el-form>
                        </template>
                        <template v-else>
                            <el-form ref="userInfo" label-width="120px" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                                <el-form-item label="登录账号:">
                                    <el-input :disabled="true" v-model="userInfo.account"></el-input>
                                </el-form-item>
                                <el-form-item label="法人手机号:">
                                    <el-input :disabled="true" v-model="compayCard.contactPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="法人姓名:">
                                    <el-input :disabled="true" v-model="compayCard.opername"></el-input>
                                </el-form-item>
                                <el-form-item label="法人身份证:">
                                    <el-input :disabled="editSwitch" v-model="compayCard.operidcard"></el-input>
                                </el-form-item>
                                <el-form-item label="法人邮箱:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.email"></el-input>
                                </el-form-item>
                                <el-form-item label="企业对公账户:">
                                    <el-input :disabled="editSwitch" v-model="compayCard.corporateAccount"></el-input>
                                </el-form-item>
                                <el-form-item label="公司名称:">
                                    <el-input :disabled="editSwitch" v-model="compayCard.companyName"></el-input>
                                </el-form-item>
                                <el-form-item label="公司电话:">
                                    <el-input :disabled="editSwitch" v-model="userInfo.phoneNo"></el-input>
                                </el-form-item>
                            </el-form>
                            <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                                <el-form-item label="是否三证合一:">
                                    <el-radio  v-model="compayCard.type" label="0" disabled >是</el-radio>
                                    <el-radio  v-model="compayCard.type" label="1" disabled >否</el-radio>
                                </el-form-item>
                                <template v-if="compayCard.type==0">
                                    <el-form-item label="营业执照号:">
                                        <el-input :disabled="editSwitch" v-model="compayCard.businessLicense"></el-input>
                                    </el-form-item>
                                    <el-form-item label="社会信用代码号:">
                                        <el-input :disabled="editSwitch" v-model="compayCard.uniteCode"></el-input>
                                    </el-form-item>
                                </template>
                                <template v-else>
                                    <el-form-item label="营业执照号:">
                                        <el-input :disabled="editSwitch" v-model="compayCard.businessLicense"></el-input>
                                    </el-form-item>
                                    <el-form-item label="组织机构代码证:">
                                        <el-input :disabled="editSwitch" v-model="compayCard.agentCode"></el-input>
                                    </el-form-item>
                                    <el-form-item label="税务登记号:">
                                        <el-input :disabled="editSwitch" v-model="compayCard.taxRegisterCode"></el-input>
                                    </el-form-item>
                                </template>

                            </el-form>
                            <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                                <el-form-item label="企业头像:">
                                    <el-avatar :size="50" :src="userInfo.photo"></el-avatar>
                                </el-form-item>
                            </el-form>
                        </template>
                    </el-row>
                    <el-row v-else>
                        <component :is="subMenuComponents[activeTabs]" v-on:edit-event='editEnd'></component>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane label="实名认证" name="1">
                    <el-tabs style="margin-top: 42px;" value="first" >
                        <el-tab-pane label="个人认证" name="first"  v-if="userInfo.type==1">
                            <el-row v-if="userInfo.status==2" style="margin-top: 30px;" >
                                <el-form ref="userInfo" label-width="180px" :hide-required-asterisk="true" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                                    <el-form-item label="*真实姓名:">
                                        <el-input style="width: 300px;" v-model="userIdCard.userName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="*身份证号:">
                                        <el-input style="width: 300px;" v-model="userIdCard.idCard"></el-input>
                                    </el-form-item>
                                    <el-form-item label="*请上传您的二代身份证:">
                                        <el-upload
                                                class="avatar-uploader"
                                                :action="'/api/userApi/fileUpload/idCardUpload?idCard='+userIdCard.idCard"
                                                :show-file-list="false"
                                                :on-success="handleAvatarSuccess"
                                                :before-upload="beforeAvatarUpload">
                                            <img v-if="userIdCard.idCardFront" :src="userIdCard.idCardFront" class="avatar">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                        <el-upload
                                                class="avatar-back-uploader"
                                                :action="'/api/userApi/fileUpload/idCardUpload?side=back&idCard='+userIdCard.idCard"
                                                :show-file-list="false"
                                                :on-success="handleBackAvatarSuccess"
                                                :before-upload="beforeBackAvatarUpload">
                                            <img v-if="userIdCard.idCardBack" :src="userIdCard.idCardBack" class="avatar">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                        <el-upload
                                                class="avatar-user-uploader"
                                                :action="'/api/userApi/fileUpload/idCardUpload?side=front&idCard='+userIdCard.idCard"
                                                :show-file-list="false"
                                                :on-success="handleUserAvatarSuccess"
                                                :before-upload="beforeUserAvatarUpload">
                                            <img v-if="userIdCard.idCardUser" :src="userIdCard.idCardUser" class="avatar">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button class="btnColorOrg" @click="userVerify" type="primary" >认证</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-row>
                            <el-row justify="center" type="flex" style="margin-top: 110px;"  v-else>
                                <el-col :span="4">
                                    <img src="../../../images/user/sm1.png"/>
                                    <p>您的实名认证已通过！</p>
                                </el-col>
                            </el-row>
                        </el-tab-pane>
                        <el-tab-pane label="企业认证" name="first" v-else>
                            <el-row v-if="userInfo.status==2" style="margin-top: 30px;" >
                                <el-form ref="userInfo" label-width="180px" :hide-required-asterisk="true" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                                    <el-form-item label="*企业姓名:">
                                        <el-input style="width: 300px;" v-model="compayCard.userName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="*统一社会信用代码:">
                                        <el-input style="width: 300px;" v-model="compayCard.uniteCode"></el-input>
                                    </el-form-item>
                                    <el-form-item label="*营业执照:">
                                        <el-upload
                                                class="avatar-uploader"
                                                action="/api/userApi/fileUpload/upload"
                                                :show-file-list="false"
                                                :on-success="handleCpAvatarSuccess"
                                                :before-upload="beforeCpAvatarUpload">
                                            <img v-if="compayCard.businesslicenseurl" :src="compayCard.businesslicenseurl" class="avatar">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                    </el-form-item>
                                    <el-form-item label="*企业法人姓名:">
                                        <el-input style="width: 300px;" v-model="compayCard.opername"></el-input>
                                    </el-form-item>
                                    <el-form-item label="*法人身份证号:">
                                        <el-input style="width: 300px;" v-model="compayCard.operidcard"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button class="btnColorOrg" @click="companyVerify" type="primary" >认证</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-row>
                            <el-row justify="center" type="flex" style="margin-top: 110px;"  v-else>
                                <el-col :span="4">
                                    <img src="../../../images/user/sm1.png"/>
                                    <p>您的实名认证已通过！</p>
                                </el-col>
                            </el-row>
                        </el-tab-pane>
                    </el-tabs>
                </el-tab-pane>
                <el-tab-pane label="用户绑定" name="2">
                    <el-row style="margin-top: 20px;">
                        <el-col :offset="1">您好!<em style="color: #ca0000;font-style: normal;padding: 0 5px;">{{userInfo.userName}}</em>欢迎登录!</el-col>
                    </el-row>
                    <el-row style="margin-top: 30px;" >
                        <el-form ref="userInfo" label-width="195px" label-position="right" class="unBindBox" style="min-height: 500px;">
                            <el-form-item >
                                <span slot="label">
                                        <i class="el-icon-wx-statistic" style="width: 38px;height: 38px;"></i>
                                        <span>微信绑定:</span>
                                </span>
                                <el-input :disabled="true" style="width: 300px;" :value="userInfo.wx?'已绑定：'+userInfo.wx:'未绑定'" ></el-input>
                                <el-button class="btnColorOrg" size="small" type="warning" @click="unbind(1)" v-if="userInfo.wx!=null" style="margin-left: 20px;">解绑</el-button>
                                <el-button class="btnColorBlue" size="small" type="primary" @click="bind(1)" v-if="userInfo.wx==null" style="margin-left: 20px;">绑定</el-button>
                                <el-button class="btnColorOrg" size="small" type="warning" v-if="wxbindFlag" @click="cleanWx" style="margin-left: 20px;">取消绑定</el-button>
                                <div id="login_container" style="z-index: 99999;position: inherit;">
                                </div>
                            </el-form-item>
                            <el-form-item >
                                <span slot="label">
                                        <i class="el-icon-qq-statistic ico-pre" style="width: 38px;height: 38px;"></i>
                                        <span>QQ号绑定:</span>
                                </span>
                                <el-input :disabled="true" style="width: 300px;" :value="userInfo.qq?'已绑定：'+userInfo.qq:'未绑定'" ></el-input>
                                <el-button class="btnColorOrg" size="small" type="warning" @click="unbind(2)" v-if="userInfo.qq!=null" style="margin-left: 20px;">解绑</el-button>
                                <el-button class="btnColorBlue" size="small" type="primary"  @click="bind(2)" v-if="userInfo.qq==null" style="margin-left: 20px;">绑定</el-button>
                            </el-form-item>
                            <el-form-item >
                                <span slot="label">
                                        <i class="el-icon-qqem-statistic ico-pre" style="width: 38px;height: 38px;"></i>
                                        <span>QQ邮箱绑定:</span>
                                </span>
                                <el-input style="width: 300px;" v-model="userInfo.email" v-if="userInfo.email"  ></el-input>
                                <el-input style="width: 300px;" v-model="newInfo.email" v-if="userInfo.email==null"  ></el-input>
                                <el-button class="btnColorOrg" size="small" type="warning" @click="unbind(3)" v-if="userInfo.email!=null" style="margin-left: 20px;">解绑</el-button>
                                <el-button class="btnColorBlue" size="small" type="primary" @click="bind(3)" v-if="userInfo.email==null" style="margin-left: 20px;">绑定</el-button>
                            </el-form-item>
                            <el-form-item >
                                <span slot="label">
                                        <i class="el-icon-sj-statistic ico-pre" style="width: 38px;height: 38px;"></i>
                                        <span>手机号绑定:</span>
                                </span>
                                <el-input style="width: 300px;" v-model="userInfo.phoneNo"></el-input>
                                <el-button class="btnColorOrg" size="small" type="warning" @click="bind(4)" v-if="userInfo.phoneNo!=null" style="margin-left: 20px;">更换</el-button>
                                <el-button class="btnColorBlue" size="small" type="primary" v-if="userInfo.phoneNo==null" style="margin-left: 20px;">绑定</el-button>
                            </el-form-item>
                        </el-form>
                    </el-row>
                </el-tab-pane>
            </el-tabs>
        </el-row>
        <el-row style="margin-top: 20px;">
        </el-row>
        <template v-if="userManageMenuActive!='-1'">
            <component :is="subMenuComponents[1]" ></component>
        </template>
        <el-dialog title="更换手机号" :visible.sync="dialogFormVisible" class="allDialog">
            <el-form   label-width="150px" v-model="form" ref="form" >
                <el-form-item label="手机号码:" >
                    <el-input  type="text" v-model="form.phone" style="width: 200px;"></el-input>
                </el-form-item>
                <el-form-item label="新手机号:" >
                    <el-input  type="text" v-model="form.newPhone" style="width: 200px;"></el-input>
                </el-form-item>
                <el-form-item label="图形验证码:" prop="vCode" >
                    <el-row>
                        <el-col :span="10">
                            <el-input  type="text" v-model="form.vCode" @blur="viCode"></el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-image style="width: 70px;height: 32px;margin-left: 30px" :src="src"></el-image>
                        </el-col>
                        <el-col :span="9">
                            <span>看不清？<el-link  @click="captcha()">换一张</el-link></span>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label="手机验证码:" >
                    <el-input v-model="form.phoneCaptcha" style="width: 200px;" type="phoneCaptcha"></el-input>
                    <el-button class="btnNoBgdBule" style="margin-left: 10px;" @click="Yzmsubmit()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                    <el-button class="btnColorBlue" style="margin-left: 10px;cursor: default;" v-if="disabledFlag">{{yzmTitle}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg button" @click="submit()">提交</el-button>
                    <el-button class="btnColorBlue button" @click="dialogBindVisible==false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import EDITUSERINFO from "../../../views/user/userManage/editUserInfo";
    import SECURITYCENTER from "../../../views/user/securityCenter/securityCenter";
    import {userIdCardverify,compayVerify,getBhwCompanyCardInfo,upBhwUser,upUserPhone} from "../../../api/user/user";
    import {unbind,qryWxAccessTokenApi} from "../../../api/thirdparty/thirdparty";
    import {checkCaptchaReq,viCaptchaCheckByCode} from "../../../api/register/register";
    import {captchaVi} from "../../../api/user/payPwdSet";
    import {
        setTempInfo,
        getTempInfo,
        getQqToken,
        getWxToken,
        removeWxToken,
        removeQqToken,
        removeTempInfo,
        removeToken
    } from '../../../utils/auth';
    export default {
        name: "userManage",
        components: {
            "editUserInfo":EDITUSERINFO,
            "securityCenter":SECURITYCENTER
        },
        props:["userInfo"],
        data() {
            return {
                newInfo:{id:this.userInfo.id,email:null},
                dialogBindVisible:false,
                bindTitle:'微信绑定',
                userDetailTabs:"0",
                education:[
                    {
                    value: '1',
                    label: '小学'
                }, {
                    value: '2',
                    label: '初中'
                }, {
                    value: '3',
                    label: '高中'
                }, {
                    value: '4',
                    label: '中专'
                }, {
                    value: '5',
                    label: '大专'
                }, {
                    value: '6',
                    label: '本科'
                }, {
                    value: '7',
                    label: '硕士研究生'
                }, {
                    value: '8',
                    label: '博士研究生'
                }],
                editSwitch:true,
                activeTabs:'0',
                subMenuComponents:["editUserInfo","securityCenter"],
                userManageMenuActive:'-1',
                userIdCard:{
                    userName:"",
                    id:this.userInfo.id,
                    idCard:"",
                    idCardFront:'',
                    idCardBack:'',
                    idCardUser:''
                },
                compayCard:{
                    userId:this.userInfo.id,
                    userName:this.userInfo.userName,
                    uniteCode:'',
                    businesslicenseurl:'',
                    opername:'',
                    operidcard:'',
                    type:0
                },
                form:{randomCode:'',phone:'',newPhone:'',phoneCaptcha:'',vCode:''},
                src:'',
                dialogFormVisible:false,
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false,
                vCodeFlag:false,
                wxbindFlag:false
            }
        },
        created(){
            if(this.userInfo.toVisible){
                this.userDetailTabs = "1";
            }
            if(getTempInfo("QQ_BIND") && getQqToken()){
                this.userDetailTabs = "2";
                removeTempInfo("QQ_BIND");
                var data = JSON.parse(getQqToken());
                this.userInfo.qq = data.qq;
                upBhwUser({id:this.userInfo.id,qq:data.qq});
                removeQqToken();
                this.$forceUpdate();
            }
            if(getTempInfo("WX_BIND") && getWxToken()){
                this.userDetailTabs = "2";
                removeTempInfo("WX_BIND");
                var par = {code:getWxToken()};
                qryWxAccessTokenApi(par).then(response=>{
                    if(undefined!=response.data.wx){
                        this.userInfo.wx = response.data.wx;
                        upBhwUser({id:this.userInfo.id,wx:response.data.wx});
                        removeWxToken();
                        this.$forceUpdate();
                    }
                });
            }
            if(this.userInfo.type==2){
                getBhwCompanyCardInfo({userId:this.userInfo.id}).then(response=>{
                    this.compayCard = response.data;
                    this.compayCard.userId=this.userInfo.id;
                    this.compayCard.userName=this.userInfo.userName;
                });
            }
        },
        methods:{
            captcha(){
                this.vCodeFlag = false;
                var randomCode=Math.random();
                this.form.randomCode =randomCode;
                this.src=this.$store.state.user.randomCodeUrl+randomCode;
            },
            viCode(){
                if(this.form.vCode){
                    captchaVi(this.form).then(response=>{
                        if(this.form.vCode!=response.data){
                            this.$message.error('图形验证码输入有误!');
                            return;
                        }
                        this.vCodeFlag = true;
                    });
                }
            },
            submit(){
                if(!this.vCodeFlag){
                    this.$message.error('图形验证码输入有误!');
                    return;
                }
                if(this.form.phone.trim()==''){
                    this.$message.error('请输入手机号码!');
                    return;
                }
                if(this.form.newPhone.trim()==''){
                    this.$message.error('请输入新手机号码!');
                    return;
                }
                if(this.form.vCode.trim()==''){
                    this.$message.error('请输入图形验证码!');
                    return;
                }
                if(this.form.phoneCaptcha.trim()==''){
                    this.$message.error('请输入手机验证码!');
                    return;
                }
                viCaptchaCheckByCode({vCode:this.form.phoneCaptcha,phone:this.form.phone}).then(response=>{
                    if(!response.data){
                        this.$message.error('手机验证码输入有误!');
                        return;
                    }
                    upUserPhone({id:this.userInfo.id,phoneNo:this.form.phone,newPhone:this.form.newPhone}).then(response=>{
                        if(this.userInfo.type==1){
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
                        }else{
                            this.userInfo.phoneNo = this.form.phoneNo;
                            this.$emit("upUserInfo-event",this.userInfo);
                            this.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                        }
                    });
                })
            },
            Yzmsubmit(){
                var randomCode = "1234";
                var vCode="1234";
                var phone=this.form.phone;
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
            cleanWx(){
                removeTempInfo("WX_BIND");
                this.wxbindFlag = false;
                document.getElementById("login_container").innerHTML='';
            },
            bind(type){
                if(type==1){
                    this.wxbindFlag = true;
                    var obj = new WxLogin({
                        self_redirect:true,
                        id:"login_container",
                        appid: this.$store.state.user.wxAppid,
                        scope: "snsapi_login",
                        redirect_uri: encodeURI(this.$store.state.user.wx_redirect_uri),
                        state: "",
                        style: "",
                        href: ""
                    });
                    setTempInfo('WX_BIND',true,60);
                    this.$nextTick(function(){
                        this.dialogBindVisible = true;
                    })
                }else if(type==2){
                    setTempInfo('QQ_BIND',true,60);
                    QC.Login.showPopup({
                        appId:this.$store.state.user.qqAppid,
                        redirectURI:this.$store.state.user.qqredirectURI
                    });
                }else if(type==3){
                    var t  = /^[A-Za-zd0-9]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
                    if(!(t.test(this.newInfo.email))){
                        this.$message.error('邮箱输入有误!');
                        return;
                    }
                    this.$confirm('确认绑定该邮箱吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        upBhwUser({id:this.userInfo.id,email:this.newInfo.email}).then(response=>{
                            this.userInfo.email = this.newInfo.email;
                            this.$emit("upUserInfo-event",this.userInfo);
                            this.$message({
                                message: '绑定成功',
                                type: 'success'
                            });
                        });
                    })
                }else if(type==4){
                    var randomCode=Math.random();
                    this.form.randomCode =randomCode;
                    this.src=this.$store.state.user.randomCodeUrl+randomCode;
                    this.dialogFormVisible = true;
                }
            },
            unbind(type){
                if(type==4){

                }else{
                    this.$confirm('此操作将解除与该账号的绑定, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        unbind({type:type,id:this.userInfo.id}).then(response=>{
                            if(type==1){
                                this.userInfo.wx=null;
                            }else if(type==2){
                                this.userInfo.qq=null;
                            }else if(type==3){
                                this.userInfo.email=null;
                            }
                            this.userInfo.wx = null;
                            this.$emit("upUserInfo-event",this.userInfo);
                            this.$forceUpdate();
                            this.$message({
                                message: '解绑成功',
                                type: 'success'
                            });
                        });
                    });
                }
            },
            handleAvatarSuccess(res, file) {
                if(res.retCode==-1){
                    this.$message.error('与输入的身份证号码不一致,请重新上传!');
                    return;
                }
                this.userIdCard.idCardFront = res.data;
                if(this.userIdCard.idCardFront.indexOf("-zip")>-1){
                    this.userIdCard.idCardFront = this.userIdCard.idCardFront.replace("-zip","");
                }
            },
            beforeAvatarUpload(file) {
                if(this.userIdCard.idCard.trim()==''){
                    this.$message.error('请先输入身份证号码!');
                    return;
                }
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleBackAvatarSuccess(res, file) {
                if(res.retCode==-1){
                    this.$message.error('与正面的身份证一致,请重新上传!');
                    return;
                }
                this.userIdCard.idCardBack = res.data;
                if(this.userIdCard.idCardBack.indexOf("-zip")>-1){
                    this.userIdCard.idCardBack = this.userIdCard.idCardBack.replace("-zip","");
                }
            },
            beforeBackAvatarUpload(file) {
                if(this.userIdCard.idCard.trim()==''){
                    this.$message.error('请先输入身份证号码!');
                    return;
                }
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleUserAvatarSuccess(res, file) {
                if(res.retCode==-1){
                    this.$message.error('与输入的身份证号码不一致,请重新上传!');
                    return;
                }
                this.userIdCard.idCardUser = res.data;
                if(this.userIdCard.idCardUser.indexOf("-zip")>-1){
                    this.userIdCard.idCardUser = this.userIdCard.idCardUser.replace("-zip","");
                }
            },
            beforeUserAvatarUpload(file) {
                if(this.userIdCard.idCard.trim()==''){
                    this.$message.error('请先输入身份证号码!');
                    return;
                }
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleCpAvatarSuccess(res, file) {
                this.compayCard.businesslicenseurl = res.data;
                if(this.compayCard.businesslicenseurl.indexOf("-zip")>-1){
                    this.compayCard.businesslicenseurl = this.compayCard.businesslicenseurl.replace("-zip","");
                }
                this.$forceUpdate();
            },
            beforeCpAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            userManageMenuSelect(index,indexPath){
                this.userManageMenuActive = index;
            },
            beforeLeaveFn(activeName, oldActiveName){
                if(oldActiveName=='0' && this.editSwitch==false){
                    this.$confirm('您正在编辑用户资料,请先保存再继续切换', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {

                    }).catch(() => {
                        this.activeTabs = activeName;
                    });
                    return false;
                }
                this.activeTabs = activeName;
            },
            tabsClick(tab){
                // this.activeTabs = tab.name;
            },
            editSwitchCk(){
                this.editSwitch = !this.editSwitch;
            },
            editEnd(userInfo){
                // this.userInfo = userInfo;
                if(userInfo.type==2){
                    getBhwCompanyCardInfo({userId:this.userInfo.id}).then(response=>{
                        this.compayCard = response.data;
                        this.compayCard.userId=this.userInfo.id;
                        this.compayCard.userName=this.userInfo.userName;
                    });
                }
                this.$emit("upUserInfo-event",userInfo);
                this.editSwitchCk();
            },
            userVerify(){
                if(this.userIdCard.userName.trim()==''){
                    this.$message.error('姓名不能为空!');
                    return;
                }
                if(this.userIdCard.idCard.trim()==''){
                    this.$message.error('身份证号码不能为空!');
                    return;
                }
                if(this.userIdCard.idCardFront.trim()==''){
                    this.$message.error('请上传身份证正面照!');
                    return;
                }
                if(this.userIdCard.idCardBack.trim()==''){
                    this.$message.error('请上传身份证背面照!');
                    return;
                }
                if(this.userIdCard.idCardUser.trim()==''){
                    this.$message.error('请上传手持身份证背面照!');
                    return;
                }
                userIdCardverify(this.userIdCard).then(response=>{
                    if(response.retCode==-1){
                        this.$message.error('认证失败!');
                    }else{
                        this.userInfo.status=1;
                        this.$emit("upUserInfo-event",this.userInfo);
                        this.$message({
                            message: '认证成功',
                            type: 'success'
                        });
                    }
                });
            },
            companyVerify(){
                if(this.compayCard.userName.trim()==''){
                    this.$message.error('请输入企业名称!');
                    return;
                }
                if(this.compayCard.uniteCode.trim()==''){
                    this.$message.error('请输入企业信用代码!');
                    return;
                }
                if(this.compayCard.businesslicenseurl.trim()==''){
                    this.$message.error('请上传企业营业执照!');
                    return;
                }
                if(this.compayCard.opername.trim()==''){
                    this.$message.error('请输入法人姓名!');
                    return;
                }
                if(this.compayCard.operidcard.trim()==''){
                    this.$message.error('请输入法人身份证!');
                    return;
                }
                compayVerify(this.compayCard).then(response=>{
                    console.log(this.compayCard);
                    console.log(response.retCode);
                    if(response.retCode==-1){
                        this.$message.error('认证失败!');
                    }else{
                        this.userInfo.status=1;
                        this.$message({
                            message: '认证成功',
                            type: 'success'
                        });
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .userVerify-class  {
        color: #FFF;
        background:linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);
        border-color: none;
    }
    .el-icon-wx-statistic{
        background: url("../../../images/weixin@2x.png") center no-repeat;
        background-size: contain;
    }

    .el-icon-wx-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    .el-icon-qq-statistic{
        background: url("../../../images/QQ@2x.png") center no-repeat;
        background-size: contain;
    }

    .el-icon-qq-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    .el-icon-qqem-statistic{
        background: url("../../../images/youxiang@2x.png") center no-repeat;
        background-size: contain;
    }

    .el-icon-qqem-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    .el-icon-sj-statistic{
        background: url("../../../images/shouji@2x.png") center no-repeat;
        background-size: contain;
    }

    .el-icon-sj-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }


    .span-box{
        display:inline-block;
        position:relative;
    }

    .avatar-uploader /deep/.el-upload {
        border: 0px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        background: url("../../../images/user/shenfenzhengzhengmian.png") center no-repeat;
        background-size: contain;
    }

    .avatar-back-uploader /deep/.el-upload {
        border: 0px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        background: url("../../../images/user/shenfenzhengbeimian.png") center no-repeat;
        background-size: contain;
    }

    .avatar-user-uploader /deep/.el-upload {
        border: 0px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        background: url("../../../images/user/shouchishenfenzheng.png") center no-repeat;
        background-size: contain;
    }
    .avatar-uploader /deep/.el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
    .unBindBox /deep/ .el-form-item{height: 80px; margin-bottom: 0; display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .unBindBox /deep/ .el-form-item__label{height: 100%;background: rgba(2,78,204,0.05);padding: 0 0 0 38px;text-align: left;float: initial;line-height: initial;display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .unBindBox /deep/ .el-form-item__label span span{width: 94px;display: inline-block;text-align: right; vertical-align: -11px;}
    .unBindBox /deep/ .el-form-item__content{width: calc(100% - 195px); height: 100%; margin-left: 0!important;padding:20px 14px;border: 0.1px solid #7D7D7D; }
    .unBindBox /deep/ .el-form-item+.el-form-item .el-form-item__content{border-top: none;}
</style>