<template>
    <div>
        <div class="breadWrap" style="padding:20px 20px 0;">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>我的位置</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter',query:{menuActiveFlag:'orderManage'} }">用户中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter' ,query:{menuActiveFlag:'orderManage'}}">我的订单</el-breadcrumb-item>
                <el-breadcrumb-item>订单详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div style="padding: 0 20px 30px;">
            <el-row style="margin-top: 34px;" justify="center" type="flex">
                <el-col :span="22">
                    <el-steps :active="stepsActive" finish-status="success">
                        <el-step title="拍下产品" :description="orderDetailObj.createTime"></el-step>
                        <el-step title="付款成功" :description="undefined==orderDetailObj.updateTime?orderDetailObj.createTime:orderDetailObj.updateTime"></el-step>
                        <el-step title="卖家发货" :description="orderDetailObj.sendTime"></el-step>
                        <!--<el-step title="确认收货"></el-step>-->
                        <el-step title="评价" :description="undefined==evaluateOrderInfo.updateTime?'':evaluateOrderInfo.updateTime"></el-step>
                    </el-steps>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8" style="margin-top: 46px;">
                    <ul style="width: 258px;">
                        <li style="background:rgba(247,255,207,1);height: 30px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);line-height: 30px;">
                            <span style="margin-left: 12px;">订单信息</span>
                        </li>
                        <li style="background:rgba(255,255,255,1);border:0px solid rgba(204,204,204,1);margin-top: -6px;padding-left: 12px; ">
                            <el-row style="margin-top: 6px;width: 258px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                <el-col style="margin-top: 18px;" :span="6">
                                    收货地址:
                                </el-col>
                                <el-col :span="18" style="margin-top: 18px;width: 156px;">
                                    {{addressInfo.nickName}}，{{addressInfo.provinceName}}{{addressInfo.cityName}} {{addressInfo.address}}
                                </el-col>
                            </el-row>
                            <el-row style="margin-top: 19px;width: 258px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                <el-col style="margin-top: 18px;" :span="6">
                                    买家留言:
                                </el-col>
                                <el-col :span="18" style="margin-top: 18px;width: 156px;">
                                    {{orderDetailObj.remark}}
                                </el-col>
                            </el-row>
                            <el-row style="margin-top: 24px;width: 258px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                <el-col style="margin-top: 18px;" :span="6">
                                    订单编号:
                                </el-col>
                                <el-col :span="18" style="margin-top: 18px;width: 156px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(201,0,0,1);">
                                    {{orderDetailObj.orderId}}
                                </el-col>
                            </el-row>
                            <el-row style="padding-bottom:34px;margin-top: 24px;width: 258px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                <el-col style="margin-top: 18px;" :span="6">
                                    商家:
                                </el-col>
                                <el-col :span="18" style="margin-top: 18px;width: 156px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(201,0,0,1);">
                                    {{orderDetailObj.sellerName}}
                                </el-col>
                            </el-row>
                        </li>
                    </ul>
                </el-col>
                <el-col :span="14">
                    <div style="display: flex; margin-top: 100px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);line-height:18px;">
                        <img src="../../../images/orderManage/tishi.png" />
                        <span style="margin-left: 20px;margin-top: 10px;">订单状态：
                            <template v-if="orderDetailObj.status==1">
                                买家已付款，等待商家发货
                            </template>
                            <template v-if="orderDetailObj.status==2">
                                订单已完成
                            </template>
                        </span>
                    </div>
                    <div style="margin-top: 39px;margin-left: 60px;" v-if="orderDetailObj.status==1">
                        <span style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);line-height:18px;">您可以</span>
                        <span @click="sendTix" style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:#039ACD;line-height:18px;cursor: pointer;"> 提醒卖家发货（付款24小时后） </span>
                        <span @click="sendRefund" style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:#039ACD;line-height:18px;cursor: pointer;">申请退款</span>
                    </div>
                </el-col>
            </el-row>
            <el-row style="margin-top: 32px;">
                <el-col>
                    <el-table :data="[orderDetailObj]" class="user-table">
                        <el-table-column
                                label="商品"
                                align="center" width="400">
                            <template slot-scope="scope" >
                                <el-row>
                                    <el-col :span="8">
                                        <el-image style="cursor: pointer;" @click="toDetail(scope.row.productId)"
                                                  :src="scope.row.itemLogo"
                                                  fit="fit" v-if="scope.row.itemLogo!=''"></el-image>
                                        <el-image style="cursor: pointer;" @click="toDetail(scope.row.productId)"
                                                  :src="require('../../../images/img-zhuanl.png')"
                                                  fit="fit" v-else></el-image>
                                    </el-col>
                                    <el-col :span="14" :push="2" >
                                        <span style="cursor: pointer;" @click="toDetail(scope.row.productId)"> 商品名称:{{scope.row.itemName.substring(0,5)+"..."}}</span>
                                    </el-col>
                                    <el-col :span="14" :push="2" style="margin-top: 10px;">
                                        商品规格:{{scope.row.itemName.substring(0,5)+"..."}}
                                    </el-col>
                                </el-row>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="单价"
                                align="center">
                            <template slot-scope="scope" >
                                <em>￥{{scope.row.unitPrice}}</em>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="数量"
                                prop="itemNumber" align="center" width="80">
                        </el-table-column>
                        <el-table-column
                                label="订单状态"
                                align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.status==1">
                                    进行中
                                </template>
                                <template v-if="scope.row.status==2">
                                    已完成
                                </template>
                                <template v-if="scope.row.status==3">
                                    已关闭
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row  type="flex" justify="end" style="margin-top: 30px;background: rgba(2,78,204,0.05);">
                <el-col :span="6" style="padding: 30px 0;">
                    <ul>
                        <li>
                            <span>产品总价：</span>
                            <span style="float: right;margin-right: 34px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(202,0,0,1);">￥{{orderDetailObj.totalPrice}}</span>
                        </li>
                        <li style="margin-top: 26px;">
                            <span>运费(快递)：</span>
                            <span style="float: right;margin-right: 34px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(202,0,0,1);">￥0.00</span>
                        </li>
                        <li style="margin-top: 26px;">
                            <span>订单总价：</span>
                            <span style="float: right;margin-right: 34px;color: rgba(202,0,0,1);;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;">￥{{orderDetailObj.totalPrice}}</span>
                        </li>
                        <li style="margin-top: 26px;">
                            <span>实付款：</span>
                            <span style="float: right;margin-right: 34px;color: #CA0000;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;">￥{{orderDetailObj.totalPrice}}</span>
                        </li>
                    </ul>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {qryAddressManageList} from "../../../api/user/addressManage";
    import {qryEvaluateOrderList} from "../../../api/user/evaluateOrder";
    import {saveRefundRecord} from "../../../api/user/userOrder";
    import {saveMsg} from "../../../api/user/userSysMsg";
    export default {
        name: "orderDetail",
        data() {
            return {
                stepsActive:1,
                orderDetailObj:{
                    addressId:''
                },
                addressInfo:{},
                evaluateOrderInfo:{}
            }
        },
        created(){
            if(this.$route.query.orderDetailObj){
                this.orderDetailObj = this.$route.query.orderDetailObj;
                this.stepsActive = this.orderDetailObj.status+1;
                this.qryEvaluateOrderList();
            }
            if(this.orderDetailObj.addressId){
                this.loadOrderAddress();
            }
        },
        methods: {
            sendTix(){
                var msg={
                    userId:this.orderDetailObj.sellerUserId,
                    content:'尊敬的用户,您的买家提醒您尽快发货，请注意看查!',
                    msgType:1
                }
                saveMsg(msg).then(response=>{
                    this.$message({
                        message: '提醒成功',
                        type: 'success'
                    });
                });
            },
            sendRefund(){
                var par = {
                    refundUserId:this.$store.state.user.userInfo.id,
                    orderId:this.orderDetailObj.orderId,
                    refundPrice:this.orderDetailObj.totalPrice,
                    status:1
                }
                saveRefundRecord(par).then(response=>{
                    this.$message({
                        message: '申请成功',
                        type: 'success'
                    });
                })
            },
            loadOrderAddress(){
                qryAddressManageList({id:this.orderDetailObj.addressId}).then(response=>{
                    if(response.data){
                        this.addressInfo = response.data[0];
                        this.$forceUpdate();
                    }
                });
            },
            qryEvaluateOrderList(){
                qryEvaluateOrderList({orderId:this.orderDetailObj.orderId,productId:this.orderDetailObj.productId}).then(response=>{
                    if(response.data){
                        this.evaluateOrderInfo = response.data[0];
                        this.stepsActive = 5;
                    }
                });
            }
        }
    }
</script>

<style scoped>
   /deep/ .el-step__head.is-success {
        color: #F7490D;
        border-color: #F7490D;
    }
   /deep/.el-step__title.is-success {
       color: #F7490D;
   }
   /deep/.el-step__description.is-success {
       color: #F7490D;
   }
   /deep/ .el-step__head.is-process {
       color: #CBCBCB;
       border-color: #CBCBCB;
   }
</style>