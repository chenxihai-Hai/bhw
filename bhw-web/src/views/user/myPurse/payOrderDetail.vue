<template>
    <div>
        <div class="breadWrap" style="padding: 20px 20px 0;">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>我的位置</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter',query:{menuActiveFlag:'orderManage'} }">用户中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter' ,query:{menuActiveFlag:'myPurse'} }">我的钱包</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter',query:{menuActiveFlag:'myPurse',myPurseFlag:1}  }">资金流水</el-breadcrumb-item>
                <el-breadcrumb-item>支付明细-详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div style="padding: 0 20px;">
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
                                    <template v-if="addressInfo.provinceName">
                                        {{addressInfo.nickName}}，{{addressInfo.provinceName}}{{addressInfo.cityName}} {{addressInfo.address}}
                                    </template>
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
                        </li>
                    </ul>
                </el-col>
                <el-col :span="14">
                    <div style="display: flex; margin-top: 100px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);line-height:18px;">
                        <img src="../../../images/orderManage/tishi.png" />
                        <span style="margin-left: 20px;margin-top: 10px;">支付状态：
                            <template v-if="orderDetailObj.payStatus==1">
                                待付款
                            </template>
                            <template v-if="orderDetailObj.payStatus==2">
                                已完成
                            </template>
                        </span>
                    </div>
                </el-col>
            </el-row>
            <el-row style="margin-top: 32px;">
                <el-col>
                    <el-table :data="[orderDetailObj]" class="user-table">
                        <el-table-column
                                label="创建时间"
                                align="center">
                            <template slot-scope="scope" >
                                <!--<el-row>
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
                                </el-row>-->
                                <span style="color: #CA0000;">{{scope.row.createTime}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="类型"
                                align="center">
                           即时支付
                        </el-table-column>
                        <el-table-column
                                label="支付流水号"
                                align="center">
                            <template slot-scope="scope" >
                            <span style="color: #CA0000;">{{scope.row.orderNum}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="商品名称"
                                align="center" prop="itemName">
                        </el-table-column>
                        <el-table-column
                                label="订单流水号"
                                align="center" >
                            <template slot-scope="scope" >
                            <span style="color: #CA0000;">{{scope.row.orderNum}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="交易方"
                                align="center" prop="sellerName">
                        </el-table-column>
                        <el-table-column
                                label="金额"
                                align="center" >
                            <template slot-scope="scope" >
                            <span style="color: #CA0000;">{{scope.row.totalPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="status" label="完成/退款">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.payStatus==1">
                                    进行中
                                </template>
                                <template v-if="scope.row.payStatus==2">
                                    完成
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column prop="status" label="状态">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.payStatus==1">
                                    进行中
                                </template>
                                <template v-if="scope.row.payStatus==2">
                                    已完成
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row  type="flex" justify="end" style="margin-top: 30px;background: rgba(1,77,203,0.05);">
                <el-col :span="6" style="padding: 30px 0;">
                    <ul>
                        <li>
                            <span>产品总价：</span>
                            <span style="float: right;margin-right: 34px;">￥{{orderDetailObj.totalPrice}}</span>
                        </li>
                        <li style="margin-top: 26px;">
                            <span>运费(快递)：</span>
                            <span style="float: right;margin-right: 34px;">￥0.00</span>
                        </li>
                        <li style="margin-top: 26px;">
                            <span>订单总价：</span>
                            <span style="float: right;margin-right: 34px;color: #1D1D1D;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;">￥{{orderDetailObj.totalPrice}}</span>
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