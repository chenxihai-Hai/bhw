<template>
    <div style="padding: 0 17px;">
        <el-row style="margin-bottom: 4px;">
            <el-menu default-active="1" class="el-menu-demo" mode="horizontal">
                <el-menu-item index="1">我的增值服务</el-menu-item>
            </el-menu>
        </el-row>
        <div class="addServiceBox" v-show="serviceIndx">
            <table class="service-table">
                <tr>
                    <th style="width: 180px;">服务内容</th>
                    <th><span style="vertical-align: 10px;">标准会员</span><span style="font-size: 41px;">/</span><span style="vertical-align: -5px;">2888元</span></th>
                    <th><span style="vertical-align: 10px;">高级会员</span><span style="font-size: 41px;">/</span><span style="vertical-align: -5px;">8888元</span></th>
                </tr>
                <tr><td>技术合同认定登记</td><td>免费<em>3</em>次+全部业务<em>9</em>折</td><td>免费<em>3</em>次+全部业务<em>8</em>折</td></tr>
                <tr><td>技术成果登记</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>高企认定</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>知识产权</td><td>/</td><td>免费<em>1</em>次</td></tr>
                <tr><td>法律服务</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>科技金融</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>科技咨询</td><td>免费<em>2</em>次+全部业务<em>9</em>折</td><td>免费<em>2</em>次+全部业务<em>8</em>折</td></tr>
                <tr><td>财税服务</td><td>一年免费</td><td>一年免费</td></tr>
                <tr><td>开通年限</td><td>一年</td><td>一年</td></tr>
                <tr><td></td><td>
                    <el-button class="openHy" v-if="undefined==userMember.type || userMember.status==0 || userMember.type==2" @click="btnOpen(0)">开通会员</el-button>
                    <el-button class="openHy" v-if="undefined!=userMember.type && userMember.status==1 && userMember.type==1" >已开通</el-button>
                </td>
                    <td>
                        <el-button class="openHy gjHy"  v-if="undefined==userMember.type || userMember.status==0  || userMember.type==1" @click="btnOpen(1)">开通会员</el-button>
                        <el-button class="openHy" v-if="undefined!=userMember.type && userMember.status==1 && userMember.type==2" >已开通</el-button>
                    </td>
                </tr>
            </table>
            <p class="warnBox"><i class="icon-warn"></i>以上增值服务收费标准，平台具有最终解释权，并保留修改权利。</p>
        </div>
        <el-row style="margin: 58px 0 200px" v-show="servicePay" class="payBox">
            <el-col><h2>{{serviceInfo[typeId].name}}</h2></el-col>
            <el-col style="padding: 39px 0 80px">
                <h3>实付金额：<em>{{serviceInfo[typeId].price}}</em>元</h3>
                <p>描述：{{serviceInfo[typeId].name}}描述</p>
            </el-col>
            <el-col>
                <h3>支付方式：</h3>
                <div class="payLink" style="padding-left: 64px">
                    <el-link :class="payId==1?'check':''" @click="choicePay(1)"><img src="../../../images/pay/icon-wxpay.png" style="width: 100%;" alt=""></el-link>
                    <el-link :class="payId==2?'check':''" @click="choicePay(2)"><img src="../../../images/pay/icon-zfbpay.png" style="width: 100%;" alt=""></el-link>
                </div>
            </el-col>
            <el-col style="padding: 66px 64px;">
                <el-button class="btnColorOrg btnPay" @click="btnPay">付款</el-button>
            </el-col>
        </el-row>
        <el-row v-show="codePay" style="padding-bottom: 200px;">
            <el-col style="font-size: 18px;color: #000;padding-bottom: 26px;border-bottom:0.5px solid #CCC">
                <el-row type="flex" justify="space-between" style="margin: 48px 0 28px;">
                    <el-col><i class="icon-warn"></i><span>正在使用即时到账交易</span></el-col>
                    <el-col style="text-align: right;color: #CA0000;font-size: 24px;font-weight:normal;">{{serviceInfo[typeId].price}}元</el-col>
                </el-row>
                <el-row>
                    <el-col><span style="margin: 0 34px;">{{serviceInfo[typeId].name}}</span><span>收款方：<em style="color: #CA0000;font-style: normal;">北部湾国际技术转移转化中心公共服务平台</em></span></el-col>
                </el-row>
            </el-col>
            <el-col>
                <h3 style="margin: 41px 0 38px;">{{payId == 1?"微信支付":"支付宝支付"}}</h3>
                <el-row type="flex" align="middle" style="padding-left: 68px;">
                    <el-col :span="12">
                        <div class="imgEWM">
                            <div style="margin: 5px auto 0" v-show="codeUrl!=''">
                                <qrcode :value="codeUrl" v-if="codeUrl!=''"  style="width:100%;height:100%;"/>
                            </div>
                        </div>
                    </el-col>
                    <el-col style="width: 320px;">
                        <div class="smMark">
                            <div><img src="../../../images/pay/icon-saomiao.png" alt=""></div>
                            <div style="margin-left: 36px;"><p>请用{{payId==1?"微信":"支付宝"}}扫一扫</p><p>扫描二维码支付</p></div>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {wxPay,qryWalletOrderInfo,aliPay,saveWalletOrder} from "../../../api/pay/pay";
    import {setWtPayToken, getWtPayToken,removeWtPayToken} from '../../../utils/auth';
    import {saveUserMember,getUserMember,upUserMemberByPay} from '../../../api/user/user';
    import VueQrcode from 'vue-qrcode';
    export default {
        name: "addServiceIndex",
        components: {
            "qrcode":VueQrcode
        },
        data(){
            return{
                serviceIndx:true,
                servicePay:false,
                codePay:false,
                serviceInfo:[{name:"普通版会员",price:"2888"},{name:"高级版会员",price:"8888"}],
                typeId:0,
                payId:1,
                timer:null,
                orderId:null,
                codeUrl:'',
                userMember:{}
            }
        },
        created(){
            this.qryUserMember();
        },
        methods:{
            qryUserMember(){
                getUserMember({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    if(response.data){
                        this.userMember = response.data;
                    }
                });
            },
            btnOpen(val){
                this.typeId = val;
                this.serviceIndx = false;
                this.servicePay = true;
            },
            choicePay(val){
                this.payId = val;
            },
            btnPay(){
                var wOrder={
                    totalPrice:this.serviceInfo[this.typeId].price,
                    status:1,
                    userId:this.$store.state.user.userInfo.id,
                    payStatus:1,
                    productId:this.$store.state.user.userInfo.id+''+'30001',
                    dealType:3
                };
                saveWalletOrder(wOrder).then(response=>{
                    var pay={
                        orderId:response.data.orderId,
                        orderNum:response.data.orderNum,
                        totalPrice:response.data.totalPrice,
                        itemName:'开通增值服务'
                    }
                    getUserMember({userId:this.$store.state.user.userInfo.id}).then(response=>{
                        var inDate= new Date().format('yyyy-MM-dd hh:mm:ss');
                        if(response.data){
                           var newY =  parseInt(response.data.indate.split(" ")[0].substring(0,4))+1;
                            inDate = newY+response.data.indate.substring(4);
                        }else{
                            var newY =  parseInt(inDate.split(" ")[0].substring(0,4))+1;
                            inDate = newY+inDate.substring(4);
                        }
                        var umP={
                            userId:this.$store.state.user.userInfo.id,
                            lastOrderId:pay.orderId,
                            // indate:inDate,
                            type:this.typeId+1,
                            totalPrice:pay.totalPrice
                        }
                        saveUserMember(umP).then();
                    });
                    this.servicePay = false;
                    this.codePay = true;
                    if(this.payId=="1"){
                        wxPay(pay).then(resp => {
                            if(resp.data){
                                this.codeUrl = resp.data.codeUrl;
                                this.orderId = resp.data.noncestr;
                                this.timer = setInterval(() => {
                                    this.qryPayStatus();
                                },3000);
                            }else{
                                this.$message.error('系统异常,请稍候再试!');
                            }
                        });
                    }else{
                        setWtPayToken(pay.orderId);
                        aliPay(pay).then(response => {
                            document.querySelector('body').innerHTML = response;
                            document.forms[0].submit();
                        });
                    }
                });
            },
            qryPayStatus(){
                var that = this;
                qryWalletOrderInfo({orderId:this.orderId}).then(response => {
                    const paySt = response.data.payStatus;
                    if(paySt==2){
                        this.$message({
                            message: '支付成功',
                            type: 'success'
                        });
                        upUserMemberByPay({userId: this.$store.state.user.userInfo.id}).then(response=>{
                            that.qryUserMember();
                        });
                        that.servicePay = false;
                        that.codePay = false;
                        that.serviceIndx = true;
                        clearInterval(this.timer);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../../styles/userCenter.css";
    .addServiceBox{width: 667px;margin: 58px auto 150px;}
    .service-table{width: 100%;margin: 0 auto;border-collapse:collapse;}
    .service-table tr{height: 55px;}
    .service-table,.service-table tr th,.service-table tr td{border: 0.5px solid #B5B5B5}
    .service-table tr th{color: #fff;font-size: 20px;font-weight:normal;text-align: center;background:linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);}
    .service-table tr th:nth-child(2){background: linear-gradient(to right, #F44C7D , #F67A4A);}
    .service-table tr th:last-child{background: linear-gradient(to right, #A650F9 , #F778AC);}
    .service-table tr td{color: #1D1D1D; background: #FFFBFB;font-size: 16px;font-weight:normal;text-align: center;}
    .service-table tr td:nth-child(2){background: #FFE8F5;}
    .service-table tr td:last-child{background: #FFEDE6;}
    .service-table tr td em{color: #ED7799;font-size: 30px;font-style: normal;}
    .service-table tr td:last-child em{color: #B067F7;}
    .service-table tr:last-child>td{background: #FFF;}
    .openHy{width: 155px;height: 40px; font-size: 16px;font-weight:normal;border-radius: 15px;padding: 0; background: linear-gradient(to right, #F44C7D , #F67A4A);border: none;}
    /deep/ .el-button.el-button--default.openHy span{color: #FFF!important;}
    .openHy.gjHy{background: linear-gradient(to right, #A650F9 , #F778AC);}
    .warnBox{color: #1D1D1D;font-size: 14px;font-weight:normal; margin-top: 37px;display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .icon-warn{width: 17px;height: 15px;background: url("../../../images/user/icon-warn.png") no-repeat;display: inline-block;margin-right: 18px;}
/*    支付*/
    .payBox h2{color: #000;font-size: 30px;font-weight: inherit;}
    .payBox h3{color: #000;font-size: 24px;margin-bottom: 24px;font-weight: inherit;}
    .payBox h3 em{color: #CA0000;font-size: 40px;font-style: normal;}
    .payBox p{color: #000;font-size: 18px;padding-left: 64px;font-weight: inherit;}
    .payLink /deep/ .el-link{width: 225px;height: 85px;margin-right: 57px;text-align: center;border: 0.5px solid #D5D5D5;position: relative;}
    .payLink /deep/ .el-link.check{border: 1px solid #024ECC;}
    .payLink /deep/ .el-link.check:before{content:"";width: 53px;height: 40px;background: url("../../../images/pay/icon-pay_check.png") no-repeat;display: block;position: absolute;top: 0;right: 0;}
    .btnPay{width: 189px;height: 60px;font-size: 24px;border: none;border-radius: 10px;}
/*    扫码支付*/
    .imgEWM{width: 324px;height: 324px;background: #FFF9FC;}
    .smMark{background: #024ECC;color: #FFF;height:65px;font-size: 18px;display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;justify-content: center;-webkit-justify-content: center;}

</style>