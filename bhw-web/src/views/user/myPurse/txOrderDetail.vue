<template>
    <div>
        <div class="breadWrap" style="padding: 20px 20px 0;">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>我的位置</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter',query:{menuActiveFlag:'orderManage'} }">用户中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter' ,query:{menuActiveFlag:'myPurse'} }">我的钱包</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter',query:{menuActiveFlag:'myPurse',myPurseFlag:1}  }">资金流水</el-breadcrumb-item>
                <el-breadcrumb-item>提现明细-详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div style="padding: 0 20px;">
            <el-row>
                <el-col :span="2" style="margin-top: 46px;">
                   <img src="../../../images/user/wancheng.png" width="41px"/>
                </el-col>
                <el-col :span="2" style="margin-top: 46px;">
                    <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);line-height:41px;margin-left: -30px;">交易成功</span>
                </el-col>
            </el-row>
            <el-row style="margin-top: 62px">
                <el-col :span="4" >
                    <img :src="require('@/images/bank/'+orderDetailObj.bankName+'.png')" alt="">
                </el-col>
                <el-col :span="4">
                    <ul>
                        <li style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);line-height:18px;">提现</li>
                        <li style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);line-height:18px;">
                            <span>对方:</span>
                            <span>{{bankNameCode[orderDetailObj.bankName]}}</span>
                        </li>
                        <li style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);line-height:18px;">
                            <span>...{{orderDetailObj.bankCard.substring(orderDetailObj.bankCard.length-4)}}</span>|
                            <span>**{{orderDetailObj.userName.substring(orderDetailObj.userName.length-1)}}</span>
                        </li>
                    </ul>
                </el-col>
            </el-row>
            <el-row style="margin-top: 34px;" justify="center" type="flex">
                <el-col :span="16">
                    <el-steps :active="orderDetailObj.status+1" finish-status="success">
                        <el-step title="申请成功" :description="orderDetailObj.createTime"></el-step>
                        <el-step title="银行处理中" :description="undefined==orderDetailObj.updateTime?orderDetailObj.createTime:orderDetailObj.updateTime"></el-step>
                        <el-step title="到账成功" :description="orderDetailObj.updateTime"></el-step>
                    </el-steps>
                </el-col>
            </el-row>
            <el-row style="margin-top: 66px;">
                <el-col>
                    <p style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(201,0,0,1);">时间:{{orderDetailObj.createTime}}</p>
                    <el-table :data="[orderDetailObj]" class="user-table" style="margin-top: 16px;">
                        <el-table-column
                                label="转出金额"
                                align="center">
                            <template slot-scope="scope" >
                                <span style="color: #CA0000;">{{scope.row.totalPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="手续费"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="转入金额"
                                align="center">
                            <template slot-scope="scope" >
                            <span style="color: #CA0000;">{{scope.row.totalPrice}}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {qryAddressManageList} from "../../../api/user/addressManage";
    import {qryEvaluateOrderList} from "../../../api/user/evaluateOrder";
    import {saveRefundRecord} from "../../../api/user/userOrder";
    export default {
        name: "orderDetail",
        data() {
            return {
                stepsActive:1,
                orderDetailObj:{
                    addressId:''
                },
                addressInfo:{},
                evaluateOrderInfo:{},
                bankNameCode:{'CCB':'中国建设银行','PBC':'中国人民银行','BC':'中国银行','ABC':'中国农业银行','CMB':'中国招商银行'}
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
            console.log('orderDetailObj',this.orderDetailObj);
        },
        methods: {
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