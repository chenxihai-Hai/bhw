<template>
    <div>
        <TopBar></TopBar>
        <el-divider></el-divider>
        <div style="padding-bottom: 100px;">
            <div class="breadWrap w1240">
                <span class="breakCurr"><i class="icon-bread"></i>当前位置：</span>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/about'}">关于我们</el-breadcrumb-item>
                    <el-breadcrumb-item>廉正举报</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <el-row class="rptBox">
                <el-row class="rptTitle"><h3>举报信息</h3></el-row>
                <el-row>
                    <table class="rptTab">
                        <tr>
                            <td>举报对象</td>
                            <td>
                                <el-radio class="allRadio" v-model="reportForm.reportObject" label="1">员工</el-radio>
                                <el-radio class="allRadio" v-model="reportForm.reportObject" label="2">商家</el-radio>
                            </td>
                        </tr>
                        <tr><td>举报主题</td><td><el-input v-model="reportForm.title"></el-input></td></tr>
                        <tr style="height: 200px;">
                            <td>举报内容</td>
                            <td><el-input type="textarea" v-model="reportForm.content" maxlength="5000" show-word-limit></el-input><p>注意：最大数字为5000字</p></td>
                        </tr>
                        <tr>
                            <td>附件</td>
                            <td style="padding: 5px 23px">
                                <el-upload
                                        class="upload-demo"
                                        action="/api/serverManage/fileUpload/upload"
                                        :show-file-list="false"
                                        :on-success="handleSuccess"
                                        :before-upload="beforeAttachUpload">
                                    <el-button size="small" class="btnColorOrg btnUpload" type="primary">选择上传附件</el-button>
                                    <div slot="tip" class="el-upload__tip">(附件最大为20M，可上传格式为gif、jpg、png、jpeg、bmp、doc、ppt、xls、xlsx、docx、pptx)</div>
                                </el-upload>
                                <span>{{reportForm.url}}</span>
                            </td>
                        </tr>
                    </table>
                </el-row>
            </el-row>
            <el-row class="rptBox">
                <el-row class="rptTitle"><h3>举报人信息</h3></el-row>
                <el-row>
                    <table class="rptTab">
                        <tr>
                            <td>姓名</td>
                            <td><el-input class="rptInput" v-model="reportForm.userName"></el-input></td>
                        </tr>
                        <tr><td>电话</td><td><el-input class="rptInput" v-model="reportForm.phone"></el-input></td></tr>
                        <tr>
                            <td>邮箱</td>
                            <td><el-input class="rptInput" v-model="reportForm.Email"></el-input></td>
                        </tr>
                        <tr>
                            <td>验证码</td>
                            <td>
                                <el-input class="rptInput" style="width: 30%;" v-model="reportForm.verifyCode"></el-input>
                                <el-button class="btnNoBgdBule sendCode" @click="pushCode" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                                <el-button class="btnColorBlue sendCode" v-if="disabledFlag" >{{yzmTitle}}</el-button>
                            </td>
                        </tr>
                    </table>
                </el-row>
            </el-row>
            <el-row style="margin: 32px auto;text-align: center;"><el-button class="btnColorOrg btnJB" style="width: 80px;" @click="rptSubmit">提交</el-button></el-row>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import TopBar from "../layout/TopBar";
    import FootBar from "../layout/FootBar";
    import {saveReport} from "../../api/about/aboutIndex"
    import {sendPhoneCode} from "../../api/admin/index";
    export default {
        name: "addReport",
        components: {FootBar, TopBar},
        data(){
            return{
                reportForm:{
                    reportObject:"",
                    title:"",
                    content:"",
                    url:"",
                    userName:"",
                    phone:"",
                    Email:"",
                    verifyCode:"",
                    userId:this.$store.state.user.userInfo.id
                },
                disabledFlag:false,
                yzmTitle:"发送验证码",
                yzmTime:120,
                yzmTimer:null
            }
        },
        methods:{
            handleSuccess(res, file) {
                this.reportForm.url = res.data;
            },
            beforeAttachUpload(file) {
                const isLt20M = file.size / 1024 / 1024 < 20;
                if (!isLt20M) {
                    this.$message.error('上传附件大小不能超过 20MB!');
                }
                return isLt20M;
            },
            pushCode(){
                if(!(/^1[3456789]\d{9}$/.test(this.reportForm.phone))){
                    this.$message.error('手机号格式错误！');
                    return;
                }
                sendPhoneCode({"phoneNo":this.reportForm.phone}).then(response => {
                    if(response.data==0){
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
                        this.$message({
                            message: '验证码发送失败!',
                            type: 'warning'
                        });
                    }
                });
            },
            rptSubmit(){
                const reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                if(this.reportForm.reportObject === "" || null==this.reportForm.reportObject){
                    this.$message.error('请选择举报对象');
                    return;
                }
                if(this.reportForm.title === "" || null == this.reportForm.title){
                    this.$message.error('请输入标题');
                    return;
                }
                if(this.reportForm.content === "" || null == this.reportForm.content){
                    this.$message.error('请输入内容');
                    return;
                }
                if(this.reportForm.userName === "" || null == this.reportForm.userName){
                    this.$message.error('请输入姓名');
                    return;
                }
                if(this.reportForm.phone === "" || null == this.reportForm.phone){
                    this.$message.error('请输入电话');
                    return;
                }
                if(this.reportForm.Email!=="" && null != this.reportForm.Email) {
                    if (!reg.test(this.reportForm.Email)) {
                        this.$message.error('邮箱格式错误！');
                        return;
                    }
                }
                saveReport(this.reportForm).then(response =>{
                    if(response.retCode === 0){
                        this.$alert('提交成功',{
                            confirmButtonText: '确定',
                            type:'success',
                            callback: action => {
                                this.$router.push({
                                    path: '/about/detail',
                                    query:{typeId:"2"}
                                });
                            }
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/about.css";
    /deep/ .el-divider--horizontal{background: linear-gradient(90deg,rgba(5,152,203,1) 0%,rgba(216,81,133,1) 99%);height: 3px;margin: 0;}
    /deep/ .el-textarea__inner{height: 160px!important;}
    .rptTab p{color: #B0B0B0;margin-top: 8px;}
    .btnUpload{width: 120px;height: 29px;border: none;padding: 0;}
    .sendCode{height: 38px;margin-left: 20px;}
    /*/deep/ .sendCode.el-button.el-button--default span {color: #ca0000;}*/
    /deep/ .btnJB.el-button:focus, .btnJB.el-button:hover,.btnUpload.el-button:focus,.btnUpload.el-button:hover{color: #FFF;border: none;background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);}
</style>