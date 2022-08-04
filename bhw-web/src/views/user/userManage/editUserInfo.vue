<template>
    <div class="editUserInfo">
        <el-row>
            <el-row style="margin-top: 20px;">
                <el-col :span="10" :offset="1" style="color: #7D7D7D;font-size: 18px;font-weight:normal;">您好!{{userInfo.userName}}欢迎登录!</el-col>
                <el-col :span="6" :offset="20">
                    <el-button type="primary" class="btnColorOrg" size="small" @click="editExt">取消</el-button>
                    <el-button type="primary" class="btnColorBlue" size="small" @click="editSwitchCk">保存</el-button>
                </el-col>
            </el-row>
            <el-row v-if="userInfo.type==1" style="margin-top: 30px;">
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
                        <el-input placeholder="请输入您的QQ邮箱"  v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="微信号:">
                        <el-input :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="QQ号:">
                        <el-input :disabled="true"></el-input>
                    </el-form-item>
                </el-form>
                <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                    <el-form-item label="头像:">
                        <el-upload
                                class="avatar-uploader"
                                action="/api/serverManage/fileUpload/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="userInfo.photo" :src="userInfo.photo" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="昵称:">
                        <el-input  placeholder="请输入您的昵称" v-model="userInfo.nickName"></el-input>
                    </el-form-item>
                    <el-form-item label="性别:">
                        <el-radio v-model="userInfo.sex" :label="1" class="radioBoy">男</el-radio>
                        <el-radio v-model="userInfo.sex" :label="2" class="radioGril">女</el-radio>
                    </el-form-item>
                    <el-form-item label="生日:">
                        <el-input :disabled="true"></el-input>
                    </el-form-item>
                </el-form>
                <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                    <el-form-item label="单位名称:">
                        <el-input  v-model="subsInfo.unitName"></el-input>
                    </el-form-item>
                    <el-form-item label="职务:">
                        <el-input  v-model="subsInfo.duty"></el-input>
                    </el-form-item>
                    <el-form-item label="职业资格:">
                        <el-input  v-model="subsInfo.qualification"></el-input>
                    </el-form-item>
                    <el-form-item label="学历:">
                        <el-select v-model="subsInfo.education" placeholder="请选择">
                            <el-option
                                    v-for="item in education"
                                    :key="item.label"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="学校:">
                        <el-input v-model="subsInfo.school"></el-input>
                    </el-form-item>
                </el-form>
            </el-row >
            <el-row style="margin-top: 30px;" v-else>
                <el-form ref="userInfo" label-width="120px" style="padding-top: 30px;border: 1px solid #EEEEEE;" label-position="right">
                    <el-form-item label="登录账号:">
                        <el-input :disabled="true" v-model="userInfo.account"></el-input>
                    </el-form-item>
                    <el-form-item label="法人手机号:">
                        <el-input   v-model="compayCard.contactPhone"></el-input>
                    </el-form-item>
                    <el-form-item label="法人姓名:">
                        <el-input  v-model="compayCard.opername"></el-input>
                    </el-form-item>
                    <el-form-item label="法人身份证:">
                        <el-input  v-model="compayCard.operidcard"></el-input>
                    </el-form-item>
                    <el-form-item label="法人邮箱:">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="企业对公账户:">
                        <el-input  v-model="compayCard.corporateAccount"></el-input>
                    </el-form-item>
                    <el-form-item label="公司名称:">
                        <el-input  v-model="compayCard.companyName"></el-input>
                    </el-form-item>
                    <el-form-item label="公司电话:">
                        <el-input  v-model="userInfo.phoneNo"></el-input>
                    </el-form-item>
                </el-form>
                <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                    <el-form-item label="是否三证合一:">
                        <el-radio  v-model="compayCard.type" label="0">是</el-radio>
                        <el-radio  v-model="compayCard.type" label="1">否</el-radio>
                    </el-form-item>
                    <template v-if="compayCard.type==0">
                        <el-form-item label="营业执照号:">
                            <el-input  v-model="compayCard.businessLicense"></el-input>
                        </el-form-item>
                        <el-form-item label="社会信用代码号:">
                            <el-input  v-model="compayCard.uniteCode"></el-input>
                        </el-form-item>
                    </template>
                    <template v-else>
                        <el-form-item label="营业执照号:">
                            <el-input  v-model="compayCard.businessLicense"></el-input>
                        </el-form-item>
                        <el-form-item label="组织机构代码证:">
                            <el-input  v-model="compayCard.agentCode"></el-input>
                        </el-form-item>
                        <el-form-item label="税务登记号:">
                            <el-input  v-model="compayCard.taxRegisterCode"></el-input>
                        </el-form-item>
                    </template>
                </el-form>
                <el-form label-width="120px" style="padding-top: 30px;margin-top:30px;border: 1px solid #EEEEEE;">
                    <el-form-item label="企业头像:">
                        <el-upload
                                class="avatar-uploader"
                                action="/api/serverManage/fileUpload/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="userInfo.photo" :src="userInfo.photo" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
            </el-row>
        </el-row>
    </div>
</template>

<script>
    import {upBhwUser,getUserDetailByToken,getUserSubsInfo,getBhwCompanyCardInfo,upBhwCompanyCardInfo} from "../../../api/user/user";
    import {getToken,removeToken} from '../../../utils/auth';
    export default {
        name: "editUserInfo",
        data() {
            return {
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
                educationVal:'',
                userInfo:{phoneNo:null,subId:null,unitName:null,duty:null,school:null,education:null,qualification:null},
                subsInfo:{subId:null,unitName:null,duty:null,school:null,education:null,qualification:null},
                defPhoto:require('@/images/defaultHeadPhoto.png'),
                compayCard:{
                    uniteCode:null,
                    businesslicenseurl:null,
                    opername:null,
                    operidcard:null,
                    type:null,
                    agentCode:null
                }
            }
        },
        created(){
            getUserDetailByToken({token:getToken()}).then(response =>{
                if(response.data){
                    this.userInfo = response.data;
                    if(undefined == this.userInfo.photo || ""==this.userInfo.photo){
                        this.userInfo.photo = this.defPhoto;
                    }
                    this.userInfo.userId = this.userInfo.id;
                    if(this.userInfo.type==1){
                        getUserSubsInfo({userId: this.userInfo.id}).then(resp => {
                            if(resp.data){
                                this.subsInfo=resp.data;
                                this.userInfo.subId = resp.data.id;
                            }
                        });
                    }else{
                        getBhwCompanyCardInfo({userId:this.userInfo.id}).then(response=>{
                            this.compayCard = response.data;
                        });
                    }
                }else {
                    removeToken();
                    this.$message.error('登录失效,请您重新登录!');
                    this.$router.push({
                        path: '/home'
                    });
                }
            });
        },
        methods:{
            handleAvatarSuccess(res, file) {
                this.userInfo.photo = res.data;
                if(this.userInfo.photo.indexOf("-zip")>-1){
                    this.userInfo.photo = this.userInfo.photo.replace("-zip","");
                }
            },
            beforeAvatarUpload(file) {
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
            editExt(){
                this.$emit("edit-event",this.userInfo);
            },
            editSwitchCk(){
                this.userInfo.createTime = null;
                if(this.userInfo.type==2){
                    upBhwCompanyCardInfo(this.compayCard).then();
                }else{
                    this.userInfo.unitName = this.subsInfo.unitName;
                    this.userInfo.duty = this.subsInfo.duty;
                    this.userInfo.school = this.subsInfo.school;
                    this.userInfo.education = this.subsInfo.education;
                    this.userInfo.qualification = this.subsInfo.qualification;
                }
                upBhwUser(this.userInfo).then(response=>{
                    this.$store.state.user.userInfo = this.userInfo;
                    this.$emit("edit-event",this.userInfo);
                })
            }
        }
    }
</script>

<style scoped>
    .avatar-uploader /deep/ .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader /deep/ .el-upload:hover {
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
    /deep/ .radioGril .el-radio__input.is-checked .el-radio__inner{background: #CA0000;border:1px solid #CA0000;}
    /deep/ .radioGril .el-radio__input.is-checked+.el-radio__label{color: #CA0000}
    /deep/ .radioBoy .el-radio__input.is-checked .el-radio__inner{background: #024ECC;border:1px solid #024ECC;}
    /deep/ .radioBoy .el-radio__input.is-checked+.el-radio__label{color: #024ECC;}
    /deep/ .el-radio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
</style>