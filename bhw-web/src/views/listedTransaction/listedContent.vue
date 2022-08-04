<template>
    <div class="listedTransaction">
        <el-row justify="center" type="flex" style="height:707px;" class="el-row-jiaoyi-statistic">
            <el-col :span="4" :push="4" style="top:24%;">
                <el-row class="showBar">
                    <el-col class="showTitle">
                        正在挂牌交易
                    </el-col>
                    <el-col class="showNum">
                        <template v-for="(numItem,numIndex) in countList">
                            <span :key="numIndex">{{numItem}}</span>
                        </template>
                        <span>宗</span>
                    </el-col>
                    <el-col>
                        <el-button class="el-icon-guapai-statistic">参与挂牌交易</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row type="flex" class="warmbar" >
            <el-col class="w1240">
                <!--<img src="../../images/notice-fill.png" style="vertical-align:-2px;"/>
                <span style="">挂牌交易公告:'FAMINGZHAUNLI名称'挂牌交易</span>-->
                <template v-if="listedMessage.length>0">
                    <img src="../../images/notice-fill.png" style="vertical-align:-2px;"/>
                    <!--router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:listedMessage[0].infoId}}">
                        <span style="">挂牌交易公告:'{{listedMessage[0].name}}'</span>
                    </router-link>-->
                    <span style="">挂牌交易公告:</span>
                    <div class="text-container">
                        <div class="inner-container">
                            <p class="text" v-for="item in listedMessage" :key="'gpjygd_'+item.infoId" >{{item.name}}</p>
                        </div>
                    </div>
                </template>
            </el-col>
        </el-row>
        <div class="listedProcess">
            <div>
                <img class="imgProcess" src="../../images/img-listed_step.png">
            </div>
            <el-button class="btnColorOrg btnAsk" @click="dialogFormVisible = true">点击咨询</el-button>
            <el-dialog title="挂牌交易咨询" :visible.sync="dialogFormVisible" class="allDialog">
                <el-form :model="servcceForm">
                    <el-form-item>
                        <el-input type="textarea" v-model="servcceForm.remark" placeholder="请输入您的需求：" :rows="5"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <table class="tab">
                            <tr><th>姓名：</th><td><el-input v-model="servcceForm.name" placeholder="您的姓名："></el-input></td></tr>
                            <tr><th>手机号码：</th>
                                <td>
                                    <el-input v-model="servcceForm.phone" placeholder="您的电话："></el-input>
                                    <el-button class="btnNoBgdBule btnYzm" @click="pushCode" v-if="disabledFlag==false">发送验证码</el-button>
                                    <el-button class="btnColorBlue" type="info" v-if="disabledFlag" style="cursor: none;">{{yzmTitle}}</el-button>
                                </td>
                            </tr>
                            <tr><th>验证码：</th><td><el-input v-model="servcceForm.captchaCode" placeholder="请输入验证码："></el-input></td></tr>
                        </table>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button class="btnColorOrg" style="width: 235px;height: 36px;padding: 0;border: none;" @click="submitServerAdv">确 定</el-button>
                    <p><i class="i-anquan"></i>信息保护中，请放心填写</p>
                </div>
            </el-dialog>
            <el-dialog
                    title="挂牌交易咨询"
                    :visible.sync="dialogScuVisible"
                    width="660px"
                    class="allDialog">
                <div class="listSucessBox">
                    <img src="../../images/listed/tijiao.png"/>
                    <span style="font-size:30px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,153,102,1);margin-left: 43px;">咨询成功</span>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import {listedOnNumberReq,getcaptchaCode,listedAdvisory,listedMessageReq} from "../../api/listedTranaction/listedTranaction";
    import {sendPhoneCode} from "../../api/admin";
    export default {
        name: "listedContent",
        data() {
            return {
                countList:[],
                countNum:"",
                dialogFormVisible: false,
                servcceForm: {
                    remark: '',
                    name:'',
                    phone:'',
                    captchaCode:''
                },
                listedMessage:[
                    {name:"",
                        number:"",
                        client:"",
                        status:"",
                        timeLeft:""
                    }
                ],
                dialogScuVisible:false,
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false,
            }
        },
        created() {
            listedMessageReq({pageSize:6,curPage:1}).then(response => {
                this.listedMessage=response.data;
            });
            listedOnNumberReq({status:0}).then(response => {
                var numList = [];
                if(response.data > 0){
                    this.countNum = response.data;
                    if(this.countNum<10){
                        this.countList.push(0);
                        this.countList.push(response.data);
                    }else if(this.countNum < 100){
                        this.countList.push("0");
                        numList = this.countNum.split("");
                        for(var i=0;i<numList.length;i++) {
                            this.countList.push(this.countNum[i]);
                        }
                    }else if(this.countNum < 1000){
                        numList = this.countNum.split("");
                        for(var i=0;i<numList.length;i++) {
                            this.countList.push(this.countNum[i]);
                        }
                    }
                }else{
                    this.countList.push("0");
                }
            });
        },
        methods:{
            pushCode(){
                if(!(/^1[3456789]\d{9}$/.test(this.servcceForm.phone))){
                    this.$message.error('手机号格式错误！')
                    return;
                }
                getcaptchaCode(this.servcceForm).then(response => {
                    if(response.data){
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
            submitServerAdv(){
                if(this.servcceForm.phone=='' || null == this.servcceForm.phone){
                    this.$message({
                        message: '手机号码不能为空!',
                        type: 'warning'
                    });
                    return;
                }
                if(this.servcceForm.name=='' || null == this.servcceForm.name){
                    this.$message({
                        message: '姓名不能为空!',
                        type: 'warning'
                    });
                    return;
                }
                listedAdvisory(this.servcceForm).then(response=>{
                    this.dialogFormVisible = false;
                    this.servcceForm={
                        remark: '',
                            name:'',
                            phone:'',
                            ysm:''
                    };
                    this.dialogScuVisible = true;
                    setTimeout(function () {
                        this.dialogScuVisible = false;
                        this.$router.push({path: '/listedTransaction'});
                    },2000);

                });
            },
        }
    }
</script>

<style scoped>
    @import "../../styles/listed.css";
    /deep/ .listSucessBox{height: 120px;padding-bottom: 30px;display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items: center;}
    .el-icon-guapai-statistic{
        background: url("../../images/guapai1.png") center no-repeat;
        background-size: 255px 40px;
    }
    .el-icon-guapai-statistic.el-button:focus, .el-icon-guapai-statistic.el-button:hover{color: #FFF;border:none;}
    .el-row-jiaoyi-statistic{
        background: url("../../images/jiaoyi.png") center no-repeat;
    }
    .imgProcess{width: 1243px;height: 834px;margin: 70px auto 100px;text-align: center;}
    /deep/ .btnYzm{width: 129px;height: 36px;padding:0;}
    .inner-container {
        animation: myMove 5s linear infinite;
        animation-fill-mode: forwards;
    }
    /*文字无缝滚动*/
    @keyframes myMove {
        0% {
            transform: translateY(0);
        }
        100% {
            transform: translateY(-150px);
        }
    }

    .text-container {
        width: 500px;
        height: 30px;
        line-height: 30px;
        margin: -30px auto;
        overflow: hidden;
        margin-left: 130px;
    }
</style>