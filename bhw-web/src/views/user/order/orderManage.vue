<template>
    <div class="orderAll" style="margin: 0 20px;">
        <el-row style="margin-bottom: 4px;">
            <el-menu default-active="-1" class="el-menu-demo" mode="horizontal" @select="menuSelect">
                <el-menu-item index="-1">订单记录</el-menu-item>
                <el-menu-item index="0">我的退货退款</el-menu-item>
                <el-menu-item index="1">开票信息管理</el-menu-item>
                <el-menu-item index="2">我的收货地址</el-menu-item>
            </el-menu>
        </el-row>
        <el-row v-if="activeSubMenu=='-1'">
            <el-tabs v-model="activeName" type="card" class="user-tab" @tab-click="tabsClick">
                <el-tab-pane label="全部订单" name="0"></el-tab-pane>
                <el-tab-pane label="进行中" name="1"></el-tab-pane>
                <el-tab-pane label="已完成" name="2"></el-tab-pane>
            </el-tabs>
            <template v-if="orderList.length>0">
            <el-row style="margin-top: 17px;">
                <el-input  placeholder="请输入订单号/订单流水号" v-model="queryParam.orderVague" style="width: 300px;margin-right: 15px;"></el-input>
                <el-button @click="loadOrderData">订单搜索</el-button>
            </el-row>
            <el-row style="margin-top: 17px;">
                <el-col>
                    <template v-for="ol in orderList">
                        <el-table
                                :data="[ol,ol]"
                                :key="ol.orderId"
                                :span-method="arraySpanMethod" class="user-table">
                            <el-table-column
                                    label="商品"
                                    align="center" width="400">
                                <template slot-scope="scope" >
                                    <template v-if="scope.$index==0">
                                        <el-row style="text-align: left;">
                                            <el-col :span="4"><em>{{scope.row.createTime.replace(".0",'')}}</em></el-col>
                                            <el-col :span="5">订单号: <em>{{scope.row.orderId}}</em></el-col>
                                            <el-col :span="6">订单流水号:<em>{{scope.row.orderNum}}</em></el-col>
                                            <el-col :span="6">卖家名称:{{scope.row.sellerName}}</el-col>
                                        </el-row>
                                    </template>
                                    <template v-else>
                                        <el-row>
                                            <el-col :span="8">
                                                <img style="width: 100%;height:100%;cursor: pointer;" @click="toDetail(scope.row.productId)"
                                                        :src="scope.row.itemLogo"
                                                        fit="fit" v-if="scope.row.itemLogo!=''">
                                                <img style="width: 100%;height:100%;cursor: pointer;" @click="toDetail(scope.row.productId)"
                                                          :src="require('../../../images/img-zhuanl.png')"
                                                          fit="fit" v-else>
                                            </el-col>
                                            <el-col :span="14" :push="2" >
                                                <span style="cursor: pointer;" @click="toDetail(scope.row.productId)"> 商品名称:
                                                    <template v-if="undefined!=scope.row.itemName && scope.row.itemName.length>5">
                                                    {{scope.row.itemName.substring(0,5)+"..."}}
                                                </template>
                                                <template v-else>
                                                    {{scope.row.itemName}}
                                                </template>
                                                </span>
                                            </el-col>
                                            <el-col :span="14" :push="2" style="margin-top: 10px;">
                                                商品规格:
                                                <template v-if="undefined!=scope.row.itemName && scope.row.itemName.length>5">
                                                    {{scope.row.itemName.substring(0,5)+"..."}}
                                                </template>
                                                <template v-else>
                                                    {{scope.row.itemName}}
                                                </template>
                                            </el-col>
                                        </el-row>
                                    </template>
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
                                    label="订单金额"
                                    prop="totalPrice" align="center" width="200">
                                <template slot-scope="scope" >
                                    总金额: <em>￥{{scope.row.totalPrice}}</em>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="订单状态"
                                    align="center">
                                <template slot-scope="scope" >
                                    <template v-if="scope.row.payStatus==1">
                                        待支付
                                    </template>
                                    <template v-else>
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
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <template v-if="scope.row.payStatus==1">
                                        <el-link type="primary"   @click="toOrderPay(scope.row)">立即支付</el-link>
                                    </template>
                                    <template v-else>
                                        <el-link type="primary" v-if="undefined == scope.row.protocolUrl || null == scope.row.protocolUrl || '' == scope.row.protocolUrl" :underline="false" @click="uploadProtocol(scope.row)">上传相关协议</el-link>
                                        <a  v-if="undefined != scope.row.protocolUrl && null != scope.row.protocolUrl"
                                            :href="scope.row.protocolUrl" download="协议附件"
                                            style="text-decoration:none;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:#024ECC;">
                                            查看相关协议
                                        </a>
                                        <br/>
                                        <router-link :to="{path:'/userCenter/orderDetail',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #024ECC;">
                                            订单详情
                                        </router-link>
                                        <br/>
                                        <template v-if="scope.row.status==2">
                                            <router-link :to="{path:'/userCenter/evaluateOrder',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #024ECC;">
                                                评价
                                            </router-link>
                                        </template>
                                        <!--<template v-if="scope.row.status==2">
                                            <router-link :to="{path:'/userCenter/evaluateOrder',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #409EFF;">
                                                修改评价
                                            </router-link>
                                        </template>-->
                                        <template v-if="scope.row.status==1">
                                            <el-link type="primary" @click="comitSt(scope.row.orderId)" :underline="false">确认完成</el-link>
                                        </template>
                                        <template v-if="scope.row.status==3">
                                            <el-link type="info" :underline="false">评价</el-link>
                                        </template>
                                    </template>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                    <el-row style="margin: 70px auto 100px;text-align: center;">
                        <el-col>
                            <el-pagination
                                    background
                                    layout="prev, pager, next"
                                    :page-size="queryParam.pageSize"
                                    :current-page="queryParam.curPage"
                                    @current-change="currentChange"
                                    :total="pageTotal" class="pageBox">
                            </el-pagination>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            </template>
            <template v-else>
                <el-row type="flex" align="middle" style="margin-top: 90px;">
                    <el-col style="text-align: right;"><img src="../../../images/user/img-noData.png" alt=""></el-col>
                    <el-col style="text-align: left;padding-left: 28px;"><span class="nodata">您还没有下单买产品哦~</span></el-col>
                </el-row>
            </template>
        </el-row>
        <el-row style="margin-top: 20px;">
        </el-row>
        <template v-if="activeSubMenu!='-1'">
              <component :is="subMenuComponents[activeSubMenu]" :userInfo="userInfo" ></component>
        </template>
        <el-dialog
                title="上传协议文件"
                :visible.sync="dialogUploadVisible"
                width="30%"
                class="allDialog">
            <el-upload
                    class="upload-demo"
                    action="/api/serverManage/fileUpload/upload"
                    :file-list="pFfileList"
                    :limit="1"
                    :on-remove="handlePRemove"
                    :on-success="handlePAvatarSuccess"
                    :before-upload="beforePAvatarUpload">
                <el-button size="small" type="primary" class="btnColorOrg">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传word文件，且不超过5MB</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                 <el-button @click="dialogUploadVisible = false" class="btnColorOrg">取 消</el-button>
                 <el-button type="primary" @click="saveUploadProtocol" class="btnColorBlue">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {qryUserOrderList,upUserOrderProtocol} from "../../../api/user/userOrder";
    import {saveMsg} from "../../../api/user/userSysMsg";
    import {upOrderSt} from "../../../api/pay/pay";
    import REFUNDRECORD from "../../../views/user/refund/refundRecord";
    import ADDRESSMANAGE from "../../../views/user/addressManage/addressManage";
    import INVOICEMANAGE from "../../../views/user/invoice/invoiceManage";

    export default {
        name: "orderAll",
        components: {
            "refundRecord":REFUNDRECORD,
            "addressManage":ADDRESSMANAGE,
            "addressManage":ADDRESSMANAGE,
            "invoiceManage":INVOICEMANAGE
        },
        props:["userInfo"],
        data() {
            return {
                dialogUploadVisible:false,
                pFfileList:[],
                activeSubMenu:'-1',
                activeName: '0',
                orderList:[],
                queryParam:{
                    pageSize:6,
                    curPage:1,
                    status:null,
                    orderVague:null,
                    userId:this.userInfo.id
                },
                currentOrderObj:null,
                pageTotal:0,
                subMenuComponents:["refundRecord","invoiceManage","addressManage"]
            }
        },
        created(){
           this.loadOrderData();
        },
        methods: {
            comitSt(orderId){
                this.$confirm('确认该交易已正常完成?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    upOrderSt({orderId:orderId}).then(response=>{
                        this.$message({
                            message: '操作成功!',
                            type: 'success'
                        });
                        this.loadOrderData();
                    });
                });
            },
            toOrderPay(obj){
                var orderList = [{}];
                orderList[0].orderId = obj.orderId;
                orderList[0].orderNum = obj.orderNum;
                orderList[0].unitPrice = obj.unitPrice
                orderList[0].totalPrice = obj.totalPrice;;
                orderList[0].itemName = obj.itemName;
                orderList[0].itemNames = obj.itemName;
                orderList[0].sellerName = obj.sellerName;
                this.$router.push({path:"/orderPay",query:{orderInfo:orderList}});
            },
            toDetail(productId){
                this.$router.push({path:"/kjsc/detail",query:{productId:productId}});
            },
            uploadProtocol(obj){
                this.currentOrderObj=obj;
                this.dialogUploadVisible = true;
            },
            saveUploadProtocol(){
                for(var p in this.pFfileList){
                    if(this.pFfileList[p]!='' && null != this.pFfileList[p] ){
                        this.currentOrderObj.protocolUrl = this.pFfileList[p];
                    }
                }
                upUserOrderProtocol(this.currentOrderObj).then(response=>{
                    this.$message({
                        message: '发布成功!',
                        type: 'success'
                    });
                    this.dialogUploadVisible = false;
                    this.loadOrderData();
                    var msg={
                        userId:this.currentOrderObj.sellerUserId,
                        productId:this.currentOrderObj.productId,
                        content:'您产品('+item.itemName+')的购买家已上传协议请尽快查看！',
                        msgType:3
                    }
                    saveMsg(msg).then(response=>{});
                });
            },
            handlePAvatarSuccess(res, file) {
                // this.productInfo.protocolUrl = res.data;
                this.pFfileList[file.url] =  res.data;
            },
            beforePAvatarUpload(file) {
                const isJPG = file.type === 'application/msword' || file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传文件只能是word文档!');
                }
                if (!isLt2M) {
                    this.$message.error('上传文件大小不能超过 5MB!');
                }
                return isLt2M;
            },
            handlePRemove(file, fileList) {
                this.pFfileList[file.url] = '';
            },
            menuSelect(index,indexPath){
                this.activeSubMenu = index;
            },
            arraySpanMethod({row, column, rowIndex, columnIndex}) {
                if (rowIndex  === 0) {
                    if (columnIndex === 0) {
                        return [1,7];
                    }else if (columnIndex >0) {
                        return [0, 0]
                    }
                }
            },
            tabsClick(tab){
                if(tab.name!='0'){
                    this.queryParam.status = tab.name;
                }else{
                    this.queryParam.status = null;
                }
                this.loadOrderData();
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.loadOrderData();
            },
            loadOrderData(){
                qryUserOrderList(this.queryParam).then(response => {
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    /deep/ .user-table.el-table thead{color: #1D1D1D;}
    /deep/.el-tabs--card>.el-tabs__header .el-tabs__item.is-active {
        border-bottom-color: #FFF;
        background-color: white;
    }
    /deep/ .el-link.el-link--primary{color: #024ECC;}
</style>