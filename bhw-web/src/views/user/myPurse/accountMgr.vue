<template>
    <div class="accountMgr">
        <el-row type="flex" align="middle" class="accountBox">
            <el-col :span="6"><span>登录账号：<em>{{$store.state.user.userInfo.account==null?$store.state.user.userInfo.phoneNo:$store.state.user.userInfo.account}}</em></span></el-col>
            <el-col :span="5"><span>注册时间：<em>{{$store.state.user.userInfo.createTime}}</em></span></el-col>
        </el-row>
        <el-row type="flex" align="middle" class="accountBox">
            <el-col :span="6"><span>可提现金额<em></em></span><p><em>{{userWalletInfo.moneyRemaining}}</em>元</p></el-col>
            <el-col :span="5"><span>资金冻结<em></em></span><p><em>{{userWalletInfo.moneyFreeze}}</em>元</p></el-col>
            <el-col :span="6">
                <el-button class="btnColorOrg btnAccount" @click="tixian">提现</el-button>
                <!--<el-button class="btnColorBlue" style="margin-left: 25px;">提现</el-button>-->
            </el-col>
        </el-row>
        <el-row type="flex" align="middle" class="accountBox">
            <el-col :span="6"><span>账户可用T币</span><p><em>{{userWalletInfo.tremaining}}</em>元</p></el-col>
            <el-col :span="5"><span>冻结T币</span><p><em>{{userWalletInfo.tfreeze}}</em>元</p></el-col>
            <el-col :span="6"><el-button class="btnColorOrg btnAccount" @click="dialogTbVisible = true">购买T币</el-button></el-col>
        </el-row>
        <div class="cardWrap">
            <h3><span>我的银行卡</span></h3>
            <p style="padding: 24px 0 22px 20px;">已添加<em>{{cardNum.xyCarNum}}</em>张信用卡，<em>{{cardNum.cxCardNum}}</em>张储蓄卡</p>
            <div>
                <el-row>
                    <el-col v-for="bank in bankCardList" :key="'bank_card_'+bank.id" class="cardBox">
                        <el-row type="flex" justify="space-between" align="middle">
                            <el-col :span="12"><img :src="require('@/images/bank/'+bank.bankName+'.png')" alt=""></el-col>
                            <el-col :span="12" style="text-align: right;"><span>尾号{{bank.bankCard.substring(bank.bankCard.length-4)}}</span><span :class="'cardMark '+ [bank.bankCardType=='2'?'cardCredit':'']">{{bank.bankCardType==2?"信用卡":"储蓄卡"}}</span></el-col>
                        </el-row>
                        <el-row style="margin-top: 60px;">
                            <el-col><span>持卡人姓名：<em>**{{bank.realName.substring(bank.realName.length-1)}}</em></span></el-col>
                            <el-col style="margin-top: 16px;"><span>手机号：<em>{{bank.mobile}}</em></span><el-link @click="delBankCard(bank.id)" class="btnDel">删除</el-link></el-col>
                        </el-row>
                    </el-col>
                    <el-col class="cardBox cardBoxAdd">
                        <el-link @click="btnAddBank">
                            <img src="../../../images/user/icon-add_card.png" alt="">
                            <p>添加银行卡</p>
                        </el-link>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div class="cardWrap">
            <h3><span>最近交易纪录</span></h3>
            <el-table :data="tableData" border style="width: 100%;margin-top: 29px">
                <el-table-column prop="createTime" label="创建时间" width="200"></el-table-column>
                <el-table-column prop="orderNum" label="订单流水号" width="200"></el-table-column>
                <el-table-column prop="itemName" label="商品名称" width="200">
                    <template slot-scope="scope" >
                        <template v-if="scope.row.dealType==1">
                            购买T币
                        </template>
                        <template v-if="scope.row.dealType==2">
                            现金充值
                        </template>
                        <template v-if="scope.row.dealType==2">
                            开通增值服务
                        </template>
                        <template v-if="scope.row.dealType==0">
                            {{scope.row.itemName}}
                        </template>
                    </template>
                </el-table-column>
                <el-table-column prop="totalPrice" label="交易金额（元）"></el-table-column>
                <el-table-column label="交易状态">
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
        </div>
        <div style="text-align: right;margin-top: 43px;">
            <el-pagination
                    background
                    layout="prev, pager, next"
                    :page-size="queryParam.pageSize"
                    :current-page="queryParam.curPage"
                    @current-change="currentChange"
                    :total="pageTotal" class="pageBox">
            </el-pagination>
        </div>
<!--        添加银行卡-->
        <el-dialog title="添加银行卡" class="bankDialog" :visible.sync="outerVisible" width="695px">
            <el-dialog
                    title="添加银行卡" class="bankDialog"
                    :visible.sync="innerVisible"
                    append-to-body  width="695px">
                <div>
                    <p style="margin: 15px 0 10px;font-weight:normal;">验证银行预留手机号</p>
                    <el-form :model="bankForm">
                        <el-form-item label="手机号：">
                            <span>{{bankForm.mobile.replace(/^(.{3}).*(.{4})$/,"$1****$2")}}</span>
                        </el-form-item>
                        <el-form-item label="验证码：" style="padding: 1px 0 5px;">
                            <el-input v-model="bankForm.code" autocomplete="off" style="width: 70%;"></el-input>
                         <!--   <el-button class="btnYzm" @click="sendMobileCode">获取验证码</el-button>-->
                            <el-button class="btnNoBgdBule" style="margin-left: 10px;" @click="Yzmsubmit()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                            <el-button class="btnColorBlue" style="margin-left: 10px;cursor: default;width: 150px;height: 40px;" v-if="disabledFlag">{{yzmTitle}}</el-button>

                        </el-form-item>
                    </el-form>
                    <div style="padding: 32px 0 27px;text-align: center;"><el-button class="btnColorOrg btnNext" @click="nextInnerPwd">下一步</el-button></div>
                </div>
            </el-dialog>
            <el-dialog
                    title="添加银行卡" class="bankDialog"
                    :visible.sync="innerPwdVisible"
                    append-to-body  width="695px">
                <div>
                    <p style="margin: 15px 0 10px;font-weight:normal;">请输入支付密码,以验证身份</p>
                    <el-form :model="bankForm">
                        <el-form-item label="支付密码：" style="padding: 1px 0 5px;">
                            <el-input v-model="bankForm.password"  type="password" autocomplete="off" style="width: 70%;"></el-input>
                        </el-form-item>
                    </el-form>
                    <div style="padding: 32px 0 27px;text-align: center;"><el-button class="btnColorOrg btnNext" @click="saveBankCardInfo">确认</el-button></div>
                </div>
            </el-dialog>
            <div class="bankBox">
                <el-form :model="bankForm" ref="bankForm">
                    <el-form-item label="持卡人：" :rules="[{required: true, message: '持卡人不能为空'}]" prop="realName">
                        <el-input v-model="bankForm.realName" placeholder="请输入持卡人姓名 " type="realName"></el-input>
                    </el-form-item>
                    <el-form-item label="身份证：" :rules="[{required: true, message: '身份证不能为空'}]" prop="idCard">
                        <el-input v-model="bankForm.idCard" placeholder="请输入身份证姓名 " type="idCard"></el-input>
                    </el-form-item>
                    <el-form-item label="银行卡号：" :rules="[{required: true, message: '银行卡号不能为空'},{pattern:/^[1-9][0-9]*$/,message: '银行卡号格式不正确'}]" prop="bankCard">
                        <el-input v-model="bankForm.bankCard" placeholder="请输入银行卡卡号" type="bankCard" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="银行名称：" :rules="[{required: true, message: '银行名称不能为空'}]" prop="bankName">
                        <el-input v-model="bankForm.bankName" placeholder="请输入银行名称" autocomplete="off" type="bankName"></el-input>
                    </el-form-item>
                    <el-form-item label="卡种类型：">
                        <el-radio class="allRadio" v-model="bankForm.bankCardType" label="1">储蓄卡</el-radio>
                        <el-radio class="allRadio" v-model="bankForm.bankCardType" label="2">信用卡</el-radio>
                        <el-radio class="allRadio" v-model="bankForm.bankCardType" label="3">国际银行卡<em style="font-size: 11px;font-style: normal;">海外（含港澳台）发行的信用卡和银行储蓄账户</em></el-radio>
                    </el-form-item>
                    <el-form-item label="手机号："
                                  :rules="[{required: true, message: '手机号不能为空'},
                                           {pattern:/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,message: '手机号格式不正确'}]" prop="mobile">
                        <el-input v-model="bankForm.mobile" placeholder="请输入银行预留手机号" type="phone" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <div style="padding: 38px 0 30px;"><el-button class="btnColorOrg btnNext" @click="innerClick('bankForm',bankForm)">下一步</el-button></div>
            </div>
        </el-dialog>
<!--        T币购买弹框-->
        <el-dialog :show-close="false" :visible.sync="dialogTbVisible" class="showTbBox" width="854px">
            <el-row>
                <el-col style="padding-right: 12px;width: 172px;border-right: 1px solid #CCC;">
                    <div style="margin: 31px 0 40px;text-align: center;"><img src="../../../images/user/img-Tbi.png" alt=""><h3>T币</h3></div>
                    <div class="tmark">
                        <h4 style="text-align: center;">1元=1 T币</h4>
                        <p>1、T币可以用来购买北部湾国际技术转移转化中心服务平台增值服务及其他相关服务；</p>
                        <p style="margin: 16px 0 27px;">2、T币仅能用于兑换北部湾国际技术转移转化中心服务平台增值服务及其他相关服务，不能兑换现金，不能进行转账交易，不能兑换北部湾国际技术转移转化中心服务平台体系外的产品和服务。</p>
                    </div>
                    <div><el-checkbox class="allCheck" v-model="ckTreaty" style="font-size: 12px;">同意 <em style="color: #024ECC;font-style: normal;">T币充值用户条例</em></el-checkbox></div>
                    <p style="color: #024ECC;margin: 25px 0 19px;">客服电话：400-678786</p>
                </el-col>
                <el-col style="width: calc(100% - 172px);padding:0 15px 0 34px;">
                    <div>
                        <p>充值账号：1362108241</p>
                        <el-row class="choiceTb">
                            <el-col v-for="temp in priceList" :key="temp.priceId">
                                <el-link :class="priceType==temp.priceId?'pcheck':''" @click="choicePrice(temp.priceId)">
                                    <div class="priceBox"><i class="icon_Tb"></i><span>{{temp.num}}</span></div>
                                    <p>{{temp.price}}元</p>
                                </el-link>
                            </el-col>
                            <el-col>
                                <el-link :class="priceType==5?'pcheck':''" style="padding: 9px;" @click="choicePrice(5)">
                                    <div class="priceBox" v-if="priceType != 5"><i class="icon_Tb" style="margin-right:6px;"></i><span style="font-size:14px;">其它金额</span></div>
                                    <div class="priceBox" style="padding: 13px 0 20px;" v-else><el-input v-model="price" placeholder="请输入内容" class="iptPrice"></el-input></div>
                                    <p>{{price}}元</p>
                                </el-link>
                            </el-col>
                        </el-row>
                    </div>
                    <el-divider></el-divider>
                    <div class="payType">
                        <h3 style="margin: 35px 0 20px;">支付方式</h3>
                        <div>
                            <el-link :class="payId==1?'linkCheck':''" @click="choicePay(1)">微信支付</el-link>
                            <el-link :class="payId==2?'linkCheck':''" @click="choicePay(2)" style="margin-left: 20px">支付宝支付</el-link>
                        </div>
                    </div>
                    <div style="text-align: center;margin-top: 37px;">
                        <el-button class="btnColorOrg btnAccount" @click="submitPay">确认支付</el-button>
                        <template v-if="codeUrl!=''">
                            <p>请扫码完成支付，支付<em style="color: #F74A0D;font-size: 18px;font-weight:normal;font-style: normal;">{{priceType == 5?price:priceList[priceType-1].price}}</em>元</p>
                            <div style="width:108px;height:108px;margin: 5px auto 0" v-show="codeUrl!=''">
                                <qrcode :value="codeUrl" v-if="codeUrl!=''"  style="width:108px;height:108px;"/>
                            </div>
                        </template>
                    </div>
                </el-col>
            </el-row>
        </el-dialog>
        <!--提现-->
        <el-dialog title="选择银行卡" class="selectBankCard" :visible.sync="tixianVisible" >
                <el-form>
                    <el-form-item label="银行卡号：">
                        <el-select v-model="userWithdraw.bankCardId" placeholder="请选择">
                            <el-option
                                    v-for="item in bankCardList"
                                    :key="item.bankCard"
                                    :label="item.bankCard"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div style="padding: 38px 0 30px;"><el-button class="btnColorOrg btnNext" @click="submitWithdraw">确认</el-button></div>
        </el-dialog>
    </div>
</template>

<script>
    import {wxPay,qryWalletOrderInfo,aliPay,saveWalletOrder} from "../../../api/pay/pay";
    import {qryUserWallet,upUserWalletRemaining,qryPayDealList,saveBankCardInfo,
        qryBankCardList,delBankCard,saveUserWithdraw,bankCardVerify} from "../../../api/user/userWallet";
    import VueQrcode from 'vue-qrcode';
    import {setWtPayToken, getWtPayToken,removeWtPayToken} from '../../../utils/auth';
    import {getAccountStatus,phoneCaptcha,verifyPhonecaptcha} from "../../../api/user/payPwdSet";
    export default {
        name: "accountMgr",
        components: {
            "qrcode":VueQrcode
        },
        data(){
            return{
                tixianVisible:false,
                tableData:[],
                queryParam:{
                    pageSize:1,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                },
                priceList:[{priceId:1,num:'500',price:'500'},//T币值
                    {priceId:2,num:'1000',price:'1000'},
                    {priceId:3,num:'5000',price:'5000'},
                    {priceId:4,num:'10000',price:'10000'}],
                bankForm:{payAccountId:'',realName:'',bankCard:'',bankName:'',bankCardType:'1',mobile:'',code:'',idCard:'',password:''},
                pageTotal:0,
                dialogTbVisible:false,
                ckTreaty:true,//复选框选择T币条约
                priceType:1,//T币选择
                payId:1, //支付方式
                price:0,
                outerVisible:false,
                innerVisible:false,
                innerPwdVisible:false,
                codeUrl:'',//weixin://wxpay/bizpayurl/up?pr=NwY5Mz9&groupid=00',
                timer:null,
                orderId:null,
                userWalletInfo:{tRemaining:'',tFreeze:''},
                payAccountId:'',
                bankCardList:[],
                userWithdraw:{
                    bankCardId:null,
                    orderIdTemp:null,
                    userId:this.$store.state.user.userInfo.id,
                    totalPrice:null
                },
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false,
                bankSubmitFlag:true,
                cardNum:{cxCardNum:0,xyCarNum:0}
            }
        },
        created(){
            if(getWtPayToken() ){
                this.upUserWalletRemaining(getWtPayToken());
            }
            this.qryUserWallet();
            this.qryPayDealList();
            this.qryPayAccountSt();
        },
        methods:{
            Yzmsubmit(){
                var randomCode = "1234";
                var vCode="1234";
                var phone=this.bankForm.mobile;
                if(vCode!="" && phone!=""){
                    phoneCaptcha({phone:this.bankForm.mobile}).then(response => {
                        if (response.data){
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
                        }
                    });
                }else {
                    this.$message({
                        message: "请正确输入",
                        type: 'warning'
                    });
                }
            },
            submitWithdraw(){
                if(null == this.userWithdraw.bankCardId){
                    this.$message.warning("请先选择银行卡!");
                    return;
                }
                this.userWithdraw.totalPrice = this.userWalletInfo.moneyRemaining;
                this.userWithdraw.orderIdTemp = this.userWalletInfo.orderIdTemp;
                saveUserWithdraw(this.userWithdraw).then(response=>{
                    this.$message.success('提交成功!');
                    this.tixianVisible = false;
                });
            },
            delBankCard(id){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    confirmButtonClass:'btnColorBlue',
                    cancelButtonClass:'btnColorOrg',
                    type: 'warning'
                }).then(() => {
                    delBankCard({id:id}).then(response=>{
                        this.$message.success('删除成功!');
                        this.qryBankCardList();
                    });
                });
            },
            tixian(){
                if(undefined == this.userWalletInfo.moneyRemaining || ''==this.userWalletInfo.moneyRemaining || '0'==this.userWalletInfo.moneyRemaining){
                    this.$message.warning("暂无可提现金额!");
                    return;
                }
                if(this.bankCardList.length<1){
                    this.$message.warning("请先添加银行卡!");
                    return;
                }
                this.tixianVisible = true;
            },
            qryPayAccountSt(){
                getAccountStatus({userid:this.queryParam.userId}).then(response => {
                    if(response.data>=1){
                        this.payAccountId = response.message;
                        this.bankForm.payAccountId = this.payAccountId;
                        this.qryBankCardList();
                    }
                });
            },
            saveBankCardInfo(){
                if(!this.bankSubmitFlag){
                    return;
                }
                this.bankSubmitFlag = false;
                saveBankCardInfo(this.bankForm).then(response=>{
                    if(response.retCode==-1){
                        this.$message.error(response.message);
                        return;
                    }
                    this.$message.success('添加成功!');
                    this.innerPwdVisible=false;
                    this.outerVisible = false;
                    this.bankSubmitFlag = true;
                    this.bankForm={payAccountId:'',realName:'',bankCard:'',bankName:'',bankCardType:'1',mobile:'',code:'',idCard:'',password:''};
                    this.qryBankCardList();
                });
            },
            sendMobileCode(){
                phoneCaptcha({phone:this.bankForm.mobile}).then(response => {
                    if (response.data){
                        this.$message.success('验证码已发送!');
                    }
                });
            },
            nextInnerPwd(){
                verifyPhonecaptcha({phone:this.bankForm.mobile,phoneCaptcha:this.bankForm.code,userid:this.queryParam.userId}).then(response=>{
                    if(response.data){
                        this.innerVisible=false;
                        this.innerPwdVisible=true;
                    }else{
                        this.$message.error('验证码输入有误!');
                    }
                });
            },
            qryBankCardList(){
                qryBankCardList({payAccountId:this.payAccountId}).then(response=>{
                    this.bankCardList = response.data;
                });
                qryBankCardList({payAccountId:this.payAccountId,bankCardType:1}).then(response=>{
                    this.cardNum.cxCardNum = response.page.counts;
                });
                qryBankCardList({payAccountId:this.payAccountId,bankCardType:2}).then(response=>{
                    this.cardNum.xyCarNum = response.page.counts;
                });
            },
            qryUserWallet(){
                qryUserWallet({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    this.userWalletInfo = response.data;
                });
            },
            upUserWalletRemaining(orderId){
                upUserWalletRemaining({userId:this.$store.state.user.userInfo.id,orderId:orderId}).then(response=>{
                    removeWtPayToken();
                    this.qryUserWallet();
                })
            },
            qryPayDealList(){
                qryPayDealList(this.queryParam).then(response=>{
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            submitPay(){
                var wOrder={
                    totalPrice:this.priceType == 5?this.price:this.priceList[this.priceType-1].price,
                    status:1,
                    userId:this.$store.state.user.userInfo.id,
                    payStatus:1,
                    productId:this.$store.state.user.userInfo.id+''+'10001',
                    dealType:1
                };
                saveWalletOrder(wOrder).then(response=>{
                    var pay={
                        orderId:response.data.orderId,
                        orderNum:response.data.orderNum,
                        totalPrice:response.data.totalPrice,
                        itemName:'T币充值'
                    }
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
                qryWalletOrderInfo({orderId:this.orderId}).then(response => {
                    const paySt = response.data.payStatus;
                    if(paySt==2){
                        clearInterval(this.timer);
                        this.upUserWalletRemaining(this.orderId);
                        this.dialogTbVisible = false;
                    }
                });
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.qryPayDealList();
            },
            choicePay(val){
                this.payId = val;
            },
            choicePrice(val){
                this.priceType = val;
            },
            btnAddBank(){
                if(this.payAccountId==''){
                    this.$message.warning("请先激活支付密码!");
                    return;
                }
                this.outerVisible = true;
            },
            innerClick(formName,bankForm){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        bankCardVerify(this.bankForm).then(response=>{
                            if(response.retCode==0){
                                this.innerVisible = true;
                            }else{
                                this.$message.error(response.message);
                            }
                        });
                    }else{
                        return false;
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .accountBox{padding: 23px 0;border-bottom: 0.5px solid #CCC;}
    .accountBox em,.cardWrap em{color: #CA0000;font-style: normal;}
    .accountBox p{margin-top: 10px;}
    .btnAccount,.btnColorBlue{width: 82px;height: 29px;padding: 0;border: none;font-weight: 400;}
    .cardWrap{margin-top: 70px;}
    .cardWrap h3{padding-bottom: 7px;border-bottom: 0.5px solid #CCC;}
    .cardWrap h3 span{padding: 7px 5px;border-bottom: 3px solid #CA0000;}
    .cardBox{width: 320px;height: 186px;padding: 15px; border: 1px solid #EAEAEA;margin-right: 8px;margin-bottom: 15px;}
    .cardBox:nth-child(3n){margin-right: 0;}
    .cardBoxAdd{text-align: center;display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;justify-content: center;-webkit-justify-content: center; flex-flow: column;-webkit-flex-flow: column;}
    .cardBoxAdd p{color: #CA0000;margin-top: 16px;}
    .btnDel{color: #024ECC; float: right;clear: both;}
    .cardMark{background: #024ECC;color: #FFF;font-size: 12px;font-weight:normal; padding: 4px 5px;margin-left: 5px;}
    .cardMark.cardCredit{background: rgb(245,92,94);}
    /deep/ .el-table th{background: #F8FFD0;color: #1D1D1D;}
/*    购买T币弹框*/
    .showTbBox /deep/ .el-dialog__header{display: none;}
    .showTbBox /deep/ .el-dialog__body{padding: 18px;}
    .showTbBox h3{color: #1D1D1D;font-size: 18px;font-weight:normal;margin-top: 5px;}
    .tmark{color: #7D7D7D;}
    .tmark h4{text-align: center;margin-bottom: 18px;}
    .tmark p{font-size: 12px;line-height: 16px;}
    /deep/ .el-checkbox__label{font-size: 12px;color: #1D1D1D;}
    .choiceTb{margin: 24px 0 41px;}
    .choiceTb .el-col{width: 105px;height: 113px;border:0.3px solid #CCC;margin-right: 18px;position: relative;}
    .choiceTb .el-col:last-child{margin-right: 0;}
    .choiceTb .el-col .el-link{width: 100%;height: 100%; color:#1D1D1D;font-weight:normal;text-align: center;padding: 12px;}
    .choiceTb .el-col .el-link.pcheck,.payType .el-link.linkCheck{border: 0.3px solid #024ECC;}
    .choiceTb .el-col .el-link.pcheck::before, .payType .el-link.linkCheck::before{content: "";width: 21px;height: 19px;background: url("../../../images/pay/icon-pay_check.png") no-repeat;background-size: contain;position: absolute;right: 0;top: 0;display: block;}
    .choiceTb .el-col .el-link .priceBox{width: 100%; padding: 13px 0 25px;border-bottom: 0.3px dashed #CCC;}
    .choiceTb .el-col .el-link span{width: 100%;height: 100%;font-size: 18px;}
    .choiceTb .el-col .el-link p{margin-top: 10px;}
    .icon_Tb{width: 22px;height: 22px;background: url("../../../images/user/img-Tbi.png") no-repeat;background-size: contain;display: inline-block;margin-right: 8px;vertical-align: -4px;}
    .payType .el-link{width: 105px;height: 33px;color: #1D1D1D;border:0.3px solid #CCC;position: relative;}
    .iptPrice /deep/ .el-input__inner{height: 30px;line-height: 30px;padding: 0 8px;}
    /deep/ .allCheck .el-checkbox__input.is-checked + .el-checkbox__label{color: #024ECC;}
/*    添加银行卡弹框*/
    .bankDialog /deep/ .el-dialog__header{height: 59px;line-height: 59px; background:rgba(2,78,204,1);padding: 0 23px; box-shadow:0px 5px 5px 0px rgba(0, 0, 0, 0.25);}
    .bankDialog /deep/ .el-dialog__title{color: #FFF;font-weight:normal;}
    .bankDialog /deep/ .el-dialog__headerbtn .el-dialog__close{color: #FFF;font-size:18px;font-weight:normal;}
    .bankDialog /deep/ .el-dialog__body{padding: 0 23px;color: #1D1D1D;}
    .bankDialog /deep/ .el-form-item{padding: 14px 0 18px;margin-bottom: 0;border-bottom: 1px solid #ccc;display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .bankDialog /deep/ .el-form-item__label{text-align: left;color: #1D1D1D;width: 90px;line-height: normal;}
    .bankDialog /deep/ .el-form-item__content{flex: 1;-webkit-flex: 1;line-height: normal;}
    .bankDialog /deep/ .el-input__inner{border:none;background: none;padding: 0;height: auto;line-height: normal;}
    .btnNext{width: 101px;height: 34px;padding: 0;border: none;}
    .btnYzm{width: 101px;height: 34px;color: #024ECC;font-weight: normal;border:1px solid #024ECC;border-radius: 3px;padding: 0;margin-top: 4px;float: right;clear: both;}
    /deep/ .el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label::before{content: none;}
    .selectBankCard /deep/ .el-dialog__header{height: 59px;line-height: 59px; background:rgba(2,78,204,1);padding: 0 23px; box-shadow:0px 5px 5px 0px rgba(0, 0, 0, 0.25);}
    .selectBankCard /deep/ .el-dialog__title{color: #FFF;font-weight:normal;}
    /deep/ .allRadio.el-radio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
</style>