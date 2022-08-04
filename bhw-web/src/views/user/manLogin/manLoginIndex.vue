<template>
    <div class="manLoginIndex">
        <h2 class="useTitle">经纪人上传</h2>
        <el-row style="padding-left: 20px;">
            <el-menu :default-active="tabPosition" class="useMenu smallMenu" mode="horizontal" @select="iconToggle">
                <el-menu-item index="-1" v-if="undefined == manInfo.id">技术经纪人注册</el-menu-item>
                <el-menu-item index="-1" v-if="undefined != manInfo.id">技术经纪人编辑</el-menu-item>
                <el-menu-item index="0" >经纪人资料管理</el-menu-item>
                <el-menu-item index="1">服务交易留言</el-menu-item>
            </el-menu>
        </el-row>
        <template v-if="tabPosition=='-1'">
            <el-form label-width="183px" label-position="right" ref="form" :model="manInfo" class="manForm">
                <el-form-item label="头像：" class="labelHight">
                    <el-upload
                            class="avatar-uploader"
                            action="/api/serverManage/fileUpload/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload" style="margin: 6px 10px;">
                        <img v-if="manInfo.photo" :src="manInfo.photo" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <el-form label-width="183px" label-position="right" ref="form" :model="manInfo" class="manForm">
                <el-form-item label="手机号：" prop="phone">
                    <el-input placeholder="必填" v-model="manInfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="真实姓名：" prop="name">
                    <el-input placeholder="必填" v-model="manInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="从事行业领域：" prop="name">
                        <el-select v-model="manInfo.borkerId" placeholder="请选择">
                            <el-option
                                    v-for="item in borkerType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                </el-form-item>
                <el-form-item label="主要成就：" prop="name">
                    <el-input placeholder="必填" v-model="manInfo.mainAchieve"></el-input>
                </el-form-item>
                <el-form-item label="性别：" prop="name">
                    <div style="line-height: 46px;padding-left: 20px;">

                        <el-radio v-model="manInfo.sex" :label="1" class="radioInfo">男</el-radio>
                        <el-radio v-model="manInfo.sex" :label="2" class="radioGril">女</el-radio>
                    </div>
                </el-form-item>
                <el-form-item label="邮箱：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.email">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="从业工龄：" prop="name">
                    <el-input placeholder="必填" v-model="manInfo.seniority"></el-input>
                </el-form-item>
            </el-form>
            <div style="text-align: center;margin-top: 70px;">
                <el-button class="btnColorOrg manBtn" v-if="submitFlag"  @click="onSubmit">提交申请</el-button>
                <el-button class="btnColorOrg manBtn" v-if="!submitFlag"  @click="delInfo">删除我的信息</el-button>
                <el-button class="btnColorBlue manBtn" v-if="!submitFlag"  @click="onSubmit">编辑保存</el-button>
            </div>
        </template>
        <template v-else>
            <component :is="subMenuComponents[tabPosition]" v-on:upInfo-event="upInfoPg"></component>
        </template>
    </div>
</template>

<script>
    import {brokerRegister,backQryBrokerList,delBrokerRegister,upBrokerAdvisory} from "../../../api/middleman/middelman";
    import companyManList from "./companyManList";
    import manMessageList from "./manMessageList";
    export default {
        name: "manLoginIndex",
        components: {'companyManList':companyManList,'manMessageList':manMessageList},
        data() {
            return {
                subMenuComponents:['companyManList','manMessageList'],
                tabPosition:'-1',
                iconToggleFlag: 0,
                radio: 1,
                borkerType:[{id:1,name:'知识产权'},{id:2,name:'财税服务'},{id:3,name:'科技咨询'},{id:4,name:'科技金融'},{id:5,name:'法律服务'}
                ,{id:6,name:'技术合同登记'},{id:7,name:'成果认定'},{id:8,name:'商务代写'},{id:9,name:'策划设计'},{id:10,name:'技术开发'},
                    {id:11,name:'教育培训'},{id:12,name:'投资孵化'}],
                manInfo:{
                    photo:'',
                    phone:'',
                    name:'',
                    sex:'',
                    email:'',
                    seniority:'',
                    mainAchieve:'',
                    borkerId:'',
                    userId:''
                },
                submitFlag:true,
                components: ["companyManList"]
            };
        },
        created(){
            if(this.$store.state.user.userInfo.type==1){
                backQryBrokerList({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    if(response.data.length>0){
                        this.submitFlag = false;
                        this.manInfo = response.data[0];
                    }
                });
            }
        },
        methods: {
            upInfoPg(info){
                this.manInfo = info;
                this.manInfo.createTime = null;
                this.tabPosition = '-1';
            },
            delInfo(){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delBrokerRegister({idList:this.manInfo.id}).then(response=>{
                        this.$message.success("删除成功");
                        this.submitFlag = true;
                        this.manInfo = {
                            photo:'',
                            phone:'',
                            name:'',
                            sex:'',
                            email:'',
                            seniority:'',
                            mainAchieve:'',
                            borkerId:'',
                            userId:this.$store.state.user.userInfo.id
                        };
                    });
                });
            },
            iconToggle(val) {
                if(this.$store.state.user.userInfo.type!=1){
                    this.manInfo = {
                        photo:'',
                        phone:'',
                        name:'',
                        sex:'',
                        email:'',
                        seniority:'',
                        mainAchieve:'',
                        borkerId:'',
                        userId:this.$store.state.user.userInfo.id
                    };
                }
                this.iconToggleFlag = val;
                this.tabPosition = val;
            },
            handleAvatarSuccess(res, file) {
                this.manInfo.photo = res.data;
                if(this.manInfo.photo.indexOf("-zip")>-1){
                    this.manInfo.photo = this.manInfo.photo.replace("-zip","");
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
            onSubmit(){
                this.manInfo.userId = this.$store.state.user.userInfo.id;
                if(this.$store.state.user.userInfo.type==2 || this.submitFlag){
                    this.manInfo.status=2;
                    if(this.manInfo.id){
                        this.manInfo.status=this.manInfo.status==1?2:this.manInfo.status;
                        this.manInfo.createTime = null;
                        upBrokerAdvisory(this.manInfo).then(response=>{
                            this.$message({
                                message: '编辑成功',
                                type: 'success'
                            });
                        });
                    }else{
                        backQryBrokerList({phone:this.manInfo.phone}).then(response=>{
                            if(response.data.length>0){
                                this.$message.error('尊敬的用户,该手机号已注册过经纪人!');
                                return;
                            }
                            brokerRegister(this.manInfo).then(response=>{
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                            });
                        })
                    }
                }else{
                    if(this.manInfo.id){
                        this.manInfo.status=this.manInfo.status==1?2:this.manInfo.status;
                        this.manInfo.createTime = null;
                        upBrokerAdvisory(this.manInfo).then(response=>{
                            this.$message({
                                message: '编辑成功',
                                type: 'success'
                            });
                        });
                    }else{
                        this.$message.error('尊敬的用户,您已注册过经纪人!');
                    }
                }
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
        height: 158px;
        line-height: 158px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 158px;
        display: block;
    }
    .shangchuan {
        margin: 0px auto;
        height: 170px;
        border: 1px solid rgba(204, 204, 204, 1);
    }

    .touxiang {
        width: 230px;
        height: 168px;
        background: #F8F8F8;
        float: left;
        text-align: right;
        padding-top: 78px;
    }

    .upload-demo {
        width: 400px;
        margin: 67px auto
    }

    .manForm {margin: 50px auto 0;width: 70%;border: 1px solid rgba(181,181,181,1);line-height: 0px;}
    .manForm /deep/ .el-form-item{margin-bottom: 0px;}
    .manForm /deep/ .el-form-item__label{color: #000; background: #F8FFD0;padding-right: 37px;line-height: 46px;border-right: 1px solid rgba(181,181,181,1);}
    .manForm /deep/ .el-form-item.labelHight .el-form-item__label{height: 172px;line-height: 172px;}
    .manForm /deep/ .el-form-item:last-child .el-form-item__content{border-bottom: none;}
    .manForm /deep/ .el-form-item__content{line-height: inherit;border-bottom: 1px solid rgba(181,181,181,1); }
    .manForm /deep/ .el-input__inner{height: 46px;line-height: 46px;border: none;}
    .btnColorBlue.manBtn,.btnColorOrg.manBtn{width: 100px;height: 32px;padding: 0;border: none;border-radius: 2px;}
    /deep/ .allRadio.el-radio{color: #1D1D1D;}
    /deep/ .radioGril .el-radio__input.is-checked .el-radio__inner{background: #CA0000;border:1px solid #CA0000;}
    /deep/ .radioGril .el-radio__input.is-checked+.el-radio__label{color: #CA0000}
    /deep/ .el-radio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
    .radioGril /deep/ .el-radio__input.is-checked + .el-radio__label,/deep/ .el-radio__input.is-checked + .el-radio__label{color: #1D1D1D;}


</style>