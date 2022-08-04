<template>
    <div class="detailWrap orderPay">
        <div class="breadWrap w1240">
            <span class="breakCurr"><i></i>当前位置：</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">科技市场</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">资产交易</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <template v-if="orderPayFlag">
            <div style="width: 1240px;margin: 0 auto; padding: 73px 20px 100px;">
                <el-row>
                    <el-col :span="2" style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);border-bottom: 0.1px solid #0066B5;text-align: center;line-height: 28px;">
                        订单信息
                    </el-col>
                    <el-col :span="22" style="border-bottom: 0.1px solid #CCCCCC;height: 28px;">
                    </el-col>
                </el-row>
                <el-row type="flex" style="padding: 32px 31px 0;">
                    <el-col :span="22" :push="2" class="orderInfo">
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">卖家名称：</span>
                        <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">{{orderInfo[0].sellerName}}</span>
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">支付订单流水号：</span>
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">{{orderInfo[0].orderNum}}</span>
                    </el-col>
                </el-row>
                <el-row type="flex" style="padding: 18px 31px" v-for="order in orderInfo" :key="order.orderNum+'_'+order.projectId">
                    <el-col :span="22" :push="2" class="orderInfo">
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">产品名称：</span>
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">{{order.itemNames}}</span>
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">产品金额：</span>
                        <span style="font-family:'Microsoft YaHei';font-weight:400;"><em style="color:#CA0000;font-size:20px;font-style: normal;vertical-align: -5px;">{{moneyFormat(order.unitPrice)}}</em> 元</span>
                    </el-col>
                </el-row>
                <el-row style="padding: 62px 0 10px;">
                    <el-col :span="2" style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);border-bottom: 0.1px solid #0066B5;text-align: center;line-height: 28px;">
                        订单支付
                    </el-col>
                    <el-col :span="22" style="border-bottom: 0.1px solid #CCCCCC;height: 28px;">
                    </el-col>
                </el-row>
                <el-row type="flex" style="padding: 32px 30px 10px 58px;">
                    <el-col :span="18" :push="2">
                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">支付方式：</span>
                        <el-radio v-model="payType" class="allRadio" label="1">
                            <i class="el-icon-wx-statistic" style="width: 89px;height: 32px;position: absolute;margin-top: -12px;"></i>
                        </el-radio>
                        <el-radio v-model="payType" class="allRadio" label="2" style="margin-left: 100px;">
                            <i class="el-icon-ali-statistic" style="width: 89px;height: 32px;position: absolute;margin-top: -12px;"></i>
                        </el-radio>
                        <el-row style="margin-top: 20px;margin-left: 70px;">
                            <el-col>
                                <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:#1D1D1D;">
                                    您本次操作需支付的总金额为
                                </span>
                                <span style="font-size:20px;font-family:'Microsoft YaHei';font-weight:400;color:#CA0000;">
                                    {{moneyFormat(orderInfo[0].totalPrice)}}
                                </span>
                                元
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 33px;margin-left: 70px;">
                            <el-col>
                                <el-button class="btnColorOrg"  type="warning" @click="starPay" style="width: 122px;">立即支付</el-button>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </div>
        </template>
        <template v-else>
            <div style="width:1247px;height:241px;background:rgba(3,154,205,0.1);;margin: 50px auto;">
                <el-row justify="center" type="flex" style="padding-top: 68px;">
                    <el-col :span="4">
                        <img :src="require('@/images/pay/jurassic_warn.png')"/>
                    </el-col>
                    <el-col :span="6" style="margin-top: 45px;">
                        <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#1D1D1D;">您已付款成功！</span>
                        <router-link :to="{path:'/userCenter'}" tag="a" class="routerLink-a" style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#CA0000;">点击查看我的订单</router-link>
                    </el-col>
                </el-row>
            </div>
        </template>
        <el-dialog
                title="扫码支付"
                :visible.sync="dialogCodeVisible"
                :modal="true"
                :close-on-press-escape="false"
                :show-close="true"
                :close-on-click-modal="false"
                width="30%">
               <div style="text-align: center;">
                <qrcode :value="codeUrl" v-if="codeUrl!=''"/>
                <br/>
                <span>请尽快扫描完成支付!</span>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {wxPay,qryOrderInfo,aliPay} from "../../api/pay/pay";
    import VueQrcode from 'vue-qrcode';
    export default {
        name: "orderPay",
        components: {
            "qrcode":VueQrcode
        },
        data() {
            return {
                orderPayFlag:true,
                payType:"1",
                dialogCodeVisible:false,
                qrcodePopover:false,
                codeUrl:'weixin://wxpay/bizpayurl/up?pr=NwY5Mz9&groupid=00',
                orderInfo:{userId:null},
                orderId:null,
                timer:null
            }
        },
        created(){
            if(this.$route.query.orderInfo){
                this.orderInfo = this.$route.query.orderInfo;
            }else{
                this.orderPayFlag = false;
            }
            this.stopF5Refresh();
        },
        methods:{
            moneyFormat (value) { // 金额 格式化
                if (!value && value !== 0) return '-';
                var intPart = Number(value) | 0; //获取整数部分
                var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'); //将整数部分逢三一断

                var floatPart = ""; //预定义小数部分
                var value2Array = value.toString().split(".");

                //=2表示数据有小数位
                if (value2Array.length == 2) {
                    floatPart = value2Array[1].toString(); //拿到小数部分

                    if (floatPart.length == 1) { //补0,实际上用不着
                        return intPartFormat + "." + floatPart + '0';
                    } else {
                        return intPartFormat + "." + floatPart;
                    }
                } else {
                    return intPartFormat + floatPart;
                }
            },
            stopF5Refresh() {
                //禁止按键F5
                document.onkeydown = function(e){
                    e = window.event || e;
                    var keycode = e.keyCode || e.which;
                    if( keycode = 116){
                        if(window.event){// ie
                            try{e.keyCode = 0;}catch(e){}
                            e.returnValue = false;
                        }else{// ff
                            e.preventDefault();
                        }
                    }
                }
                //禁止鼠标右键菜单
                document.oncontextmenu = function(e){
                    return false;
                }
            },
            starPay(){
                if(null == this.$store.state.user.userInfo){
                    this.$message.error('登录已失效请重新登录!');
                    return;
                }
                this.orderInfo.userId = this.$store.state.user.userInfo.id;
                var pay={
                    orderId:this.orderInfo[0].orderId,
                    orderNum:this.orderInfo[0].orderNum,
                    totalPrice:this.orderInfo[0].totalPrice,
                    itemName:this.orderInfo[0].itemNames
                }
                if(this.payType=="1"){
                    wxPay(pay).then(response => {
                        if(response.data){
                            this.codeUrl = response.data.codeUrl;
                            this.orderId = response.data.noncestr;
                            this.dialogCodeVisible = true;
                            this.timer = setInterval(() => {
                                this.qryPayStatus();
                            },3000);
                        }else{
                            this.$message.error('系统异常,请稍候再试!');
                        }
                    });
                }else{
                    aliPay(pay).then(response => {
                        document.querySelector('body').innerHTML = response;
                        document.forms[0].submit();
                    });
                }
            },
            qryPayStatus(){
                qryOrderInfo({orderId:this.orderId}).then(response => {
                    const paySt = response.data.payStatus;
                    if(paySt==2){
                        this.dialogCodeVisible = false;
                        clearInterval(this.timer);
                        this.orderPayFlag = false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
    /*.el-breadcrumb__inner{*/
    /*    font-size:18px;*/
    /*    font-family:'Microsoft YaHei';*/
    /*    font-weight:400;*/
    /*    color:rgba(29,29,29,1);*/
    /*}*/

    /deep/ .el-radio.allRadio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
    /deep/ .el-icon-wx-statistic{
        background: url("../../images/pay/wxpay.png") center no-repeat;
        background-size: contain;
    }
    /deep/ .el-icon-ali-statistic{
        background: url("../../images/pay/alipay.png") center no-repeat;
        background-size: contain;
    }
    .orderInfo{display: flex;display: -webkit-flex;}
    .orderInfo span:nth-child(2),.orderInfo span:last-child{text-align: left; flex: 1;-webkit-flex: 1;}
    .orderInfo span:first-child{width: 80px;text-align: right;}
    .orderInfo span:nth-child(3){width: 180px;text-align: right;}
</style>