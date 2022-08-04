<template>
    <div class="detailWrap orderDetail" style="padding-bottom: 80px;">
        <div class="breadWrap w1240">
            <span class="breakCurr"><i></i>当前位置：</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">科技市场</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">资产交易</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div style="width: 1246px;background: #fff;padding: 22px;margin: 42px auto 130px;">
            <el-row>
                <el-col style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#1D1D1D;">确认收货地址/确认文件收地址</el-col>
            </el-row>
            <div class="detailBox" style="min-height:103px;margin: 16px auto;">
                <el-row style="margin-left: 26px;margin-top: 45px;" v-if="addressList.length>0">
                    <el-col :span="1" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,165,85,1);">
                        寄送至
                    </el-col>
                    <el-col :span="16">
                        <el-radio class="allRadio"  :key="'addr_'+addr.id" v-for="(addr,index) in addressList" style="margin-left:4px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,165,85,1);width: 500px;" v-model="address" :label="index">
                            {{addr.provinceName}}{{addr.cityName}} {{addr.address}}（收货人：{{addr.nickName}}） {{addr.phoneNo}}</el-radio>
                    </el-col>
                    <el-col :span="4">
                        <a @click="upAddr" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:#024ECC;cursor: pointer;">修改本地址</a>
                    </el-col>
                </el-row>
                <el-row style="margin-left: 26px;margin-top: 45px;" v-else>
                    <el-col :span="4" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,165,85,1);">
                        暂无地址
                    </el-col>
                </el-row>
                <el-button icon="el-icon-plus" @click="addNewAddr" class="btnNoBgdOrg" style="margin-top: 22px;margin-left: 19px;margin-bottom: 43px;">添加新地址</el-button>
            </div>
            <el-row style="padding-top: 20px;">
                <el-col style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#1D1D1D;">确认订单信息</el-col>
            </el-row>
            <el-row style="border: 0.1px solid #7D7D7D;margin: 20px auto;">
                <el-col>
                    <el-table
                            :data="orderList"
                            style="width: 100%"
                            :header-cell-style="headerRowStyle"
                    class="user-table">
                        <el-table-column
                                label="订单"
                                width="460">
                            <template slot-scope="scope">
                                <router-link :to="{path:'/kjsc/detail'}" tag="a" class="itemName-a" >{{scope.row.itemName}}</router-link>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="价格"
                                width="160">
                            <template slot-scope="scope">
                                <span class="unitPrice-span">￥{{moneyFormat(scope.row.unitPrice)}}元</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="itemNumber"
                                label="数量" width="160">
                        </el-table-column>
                        <el-table-column
                                prop="itemType"
                                label="商品类型" width="260">
                        </el-table-column>
                        <el-table-column
                                prop="totalPrice"
                                label="小计">
                            <template slot-scope="scope">
                                <span class="unitPrice-span">￥{{moneyFormat(scope.row.totalPrice)}}元</span>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-row style="margin-top: 33px;margin-bottom: 39px;" type="flex" justify="end">
                        <el-col :span="2" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">订单合计:</el-col>
                        <el-col :span="4" style="font-size:20px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,131,21,1);">￥{{moneyFormat(totalPrice)}}元</el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="padding: 38px 0 29px">
                <el-col style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">发票信息</el-col>
            </el-row>
            <div class="detailBox invoiceWrap" style="margin: 0 auto;padding: 22px 25px 30px;position: relative;">
                <div class="choiceInvoice">
                    <el-radio-group v-model="invoiceVal" @change="changeHandler">
                        <el-radio-button label="0">不开票</el-radio-button>
                        <el-radio-button label="1">个人发票</el-radio-button>
                        <el-radio-button label="2">企业发票</el-radio-button>
                    </el-radio-group>
                </div>
                <div v-if="invoiceVal=='1'" style="margin-top: 15px;">
                    <el-form :model="invoiceInfo">
                        <el-form-item label="个人名称：">
                            <el-input placeholder="如需开票，请输入发票抬头，字数不能大于60" v-model="invoiceInfo.invoiceName" style="width: 433px;margin-right: 15px;"></el-input>
                            <el-button class="btnColorOrg" @click="btnAddInvoice(1)">+添加抬头</el-button>
                            <el-button class="btnColorBlue" @click="btnShowInvoiceBox(1)">选择抬头</el-button>
                        </el-form-item>
                    </el-form>
                </div>
                <div v-if="invoiceVal=='2'" style="margin-top: 15px;">
                    <el-form :model="invoiceInfo">
                        <el-form-item label="公司名称：">
                            <el-input placeholder="如需开票，请输入发票抬头，字数不能大于60" v-model="invoiceInfo.invoiceName" style="width: 433px;margin-right: 15px;"></el-input>
                            <el-button class="btnColorOrg" @click="btnAddInvoice(2)">+添加抬头</el-button>
                            <el-button class="btnColorBlue" @click="btnShowInvoiceBox(2)">选择抬头</el-button>
                        </el-form-item>
                        <el-form-item label="税号：" label-width="82px">
                            <el-input v-model="invoiceInfo.tfn" style="width: 433px;"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号：" label-width="82px">
                            <el-input v-model="invoiceInfo.phone" style="width: 433px;"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱：" label-width="82px">
                            <el-input v-model="invoiceInfo.email" style="width: 433px;"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="invoiceList" v-show="showInvoiceBox">
                    <el-row>
                        <el-col>
                            <el-radio-group v-model="invoiceRadio" @change="invoiceRadioCg">
                                <el-radio :label="item.id" v-for="item in this.invoiceList" :key="'invoice_'+item.id">
                                    <span>{{item.invoiceName}}</span><p>{{item.tfn}}</p>
                                </el-radio>
                            </el-radio-group>
                        </el-col>
                        <el-col style="text-align: right;margin-bottom: 0;">
                            <el-button class="btnColorOrg" @click="btnConfirm">确认</el-button>
                            <el-button class="btnColorBlue" @click="btnCancel">取消</el-button>
                        </el-col>
                    </el-row>
                </div>
            </div>
            <el-row style="padding: 38px 0 29px">
                <el-col style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">附加信息</el-col>
            </el-row>
            <div class="detailBox" style="min-height:203px;margin: 0 auto;">
                <el-row style="padding: 35px;">
                    <el-col :span="2" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                        买家留言：
                    </el-col>
                    <el-col :span="12">
                        <el-input
                                type="textarea"
                                :rows="5"
                                placeholder="请输入内容"
                                v-model="textarea">
                        </el-input>
                    </el-col>
                </el-row>
            </div>
            <el-row style="padding-top: 26px">
                <el-col style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;">
                    <span style="color:rgba(202,0,0,1);">温馨提示：</span>
                    <span style="color:#7D7D7D;font-weight:400">此非一口价交易品，不确定交易品有货或价格不变，此方式卖家不承担违约责任。建议您下单前先与卖家确认是否可交易或下单后联
                    </span>
                </el-col>
            </el-row>
            <el-row style="margin-top: 73px;margin-bottom: 100px;" type="flex" justify="end" align="middle">
                <el-col  style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);text-align: right;">订单合计:</el-col>
                <el-col :span="5" style="font-size:20px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,131,21,1);">￥{{moneyFormat(totalPrice)}}元</el-col>
                <el-col :span="4" style="text-align: right;">
                    <el-button type="warning" @click="submitOrder" class="btnColorOrg" style="width: 122px;">提交订单</el-button>
                </el-col>
            </el-row>
        </div>
        <el-dialog title="地址管理" :visible.sync="addressFormVisible" class="allDialog">
            <ul class="addressLi">
                <li style="line-height: 28px;height:32px;background:rgba(238,238,238,1);margin-top: 0;">
                    <label>当前配送至</label>
                    <label style="padding-left: 0;">中国大陆</label>
                </li>
                <li>
                    <label><span style="color: red">*</span>地址信息：</label>
                    <el-select v-model="addressInfo.provinceId" @change="qryCityList" placeholder="省份" style="width: 160px;margin-right: 15px;">
                        <el-option
                                v-for="item in provinceList"
                                :key="item.provinceId"
                                :label="item.provinceName"
                                :value="item.provinceId">
                        </el-option>
                    </el-select>
                    <el-select v-model="addressInfo.areaStr" placeholder="城市" style="width: 160px;">
                        <el-option
                                v-for="item in cityList"
                                :key="item.cityId"
                                :label="item.cityName"
                                :value="item.cityId">
                        </el-option>
                    </el-select>
                </li>
                <li>
                    <label><span style="color: red">*</span>详细地址：</label>
                    <el-input style="margin-right: 11px;width: 470px;"
                              type="textarea"
                              :rows="5"
                              placeholder="请输入内容"
                              v-model="addressInfo.address">
                    </el-input>
                </li>
                <li>
                    <label>邮政编码：</label>
                    <el-input style="margin-right: 11px;width: 470px;" v-model="addressInfo.zipCode" placeholder="请输入内容"></el-input>
                </li>
                <li>
                    <label><span style="color: red">*</span>收货人姓名：</label>
                    <el-input style="margin-right: 11px;width: 470px;" v-model="addressInfo.nickName" placeholder="请输入内容"></el-input>
                </li>
                <li>
                    <label><span style="color: red">*</span>手机号码：</label>
                    <el-input style="margin-right: 11px;width: 470px;" v-model="addressInfo.phoneNo" placeholder="请输入内容"></el-input>
                </li>
                <li>
                    <el-checkbox v-model="addressInfo.defaultFlagCk" class="allCheck" style="margin-left: 80px;">设置为默认收货地址</el-checkbox>
                </li>
                <li style="justify-content: center;-webkit-justify-content: center;">
                    <el-button @click="saveInfo" class="btnColorOrg" style="width: 100px;">
                        保存
                    </el-button>
                </li>
            </ul>
        </el-dialog>
    </div>
</template>

<script>
    import {qryAddressManageList,saveAddressManage,upAddressManage} from "../../api/user/addressManage";
    import {qryProvinceList,qryCityList} from "../../api/admin/index";
    import {saveOrder} from "../../api/pay/pay";
    import {delUserShopping} from "../../api/user/shoppingCart";
    import {saveMsg} from "../../api/user/userSysMsg";
    import {qryInvoiceUserList,saveInvoiceUser,saveInvoiceRecord} from "../../api/user/invoiceManage";
    export default {
        name: "orderDetail",
        data() {
            return{
                addressFormVisible:false,
                circleUrl:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
                outerVisible: false,
                form: {
                    msg: ''
                },
                size:"large",
                address:null,
                textarea:null,
                productIds:null,
                itemNames:null,
                shoppingIds:null,
                totalPrice:0,
                totalNumber:0,
                orderList:[],
                addressList:[],
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                addressInfo:{
                    provinceId:null,
                    area:null,
                    address:null,
                    zipCode:null,
                    nickName:null,
                    phoneNo:null,
                    defaultFlagCk:false,
                    defaultFlag:null,
                    areaStr:null,
                    userId:this.$store.state.user.userInfo.id
                },
                invoiceVal:0,
                invoiceId:"1",
                showInvoiceBox:false,
                invoiceList:[],
                invoiceRadio:0,
                invoiceInfo:{
                    invoiceName:null,
                    userId:this.$store.state.user.userInfo.id,
                    tfn:null,
                    defaultCkFlag:null,
                    defaultFlag:null,
                    companyAddr:null,
                    telePhone:null,
                    bankName:null,
                    bankCode:null,
                    phone:null,
                    email:null
                },
                submitOrderFlag:false
            }
        },
        created(){
            if(this.$route.query.orderProject){
                this.orderList.push(this.$route.query.orderProject);
            }
            if(this.$route.query.orderProjectList){
                this.orderList = this.$route.query.orderProjectList;
            }
            var p = 0,m=0,pid='',name='',sid='';
            this.orderList.forEach(function(item,index){
                p = p+parseFloat(item.totalPrice);
                m = m+parseFloat(item.itemNumber);
                pid=pid+item.projectId+",";
                name=name+item.itemName+";";
                if(item.id){
                    sid=sid+item.id+",";
                }
            })
            this.shoppingIds = sid;
            this.itemNames = name.substring(0,name.length-1);
            this.productIds = pid;
            this.totalPrice = p;
            this.totalNumber = m;
            //this.orderList[0].totalPrice = p;
            this.qryAddressManageList();
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
                    if( keycode == 116){
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
            qryProvinceList(){
                qryProvinceList().then(response => {
                    this.provinceList = response.data;
                });
            },
            qryCityList(val){
                this.addressInfo.area = null;
                qryCityList({"provinceId":val}).then(response => {
                    this.cityList = response.data;
                });
            },
            upAddr(){
                this.addressInfo = this.addressList[this.address];
                this.addressInfo.provinceId = this.addressInfo.provinceName;
                this.addressInfo.areaStr = this.addressInfo.cityName;
                if(this.addressInfo.defaultFlag==1){
                    this.addressInfo.defaultFlagCk=true;
                }
                this.qryProvinceList();
                this.addressFormVisible = true;
            },
            addNewAddr(){
                this.qryProvinceList();
                this.addressFormVisible = true;
            },
            saveInfo(){
                var numReg = /^[0-9]*$/
                var numRe = new RegExp(numReg);
                if (numRe.test(this.addressInfo.areaStr)) {
                    this.addressInfo.area = this.addressInfo.areaStr;
                }
                if(undefined == this.addressInfo.id){
                    saveAddressManage(this.addressInfo).then(response=>{
                        this.$message({
                            message: '保存成功',
                            type: 'success'
                        });
                        this.addressFormVisible=false;
                        this.resetInfo();
                        this.qryAddressManageList();
                    });
                }else{
                    if(this.addressInfo.defaultFlagCk){
                        this.addressInfo.defaultFlag = 1;
                    }
                    this.addressInfo.createTime = null;
                    upAddressManage(this.addressInfo).then(response=>{
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        this.addressFormVisible=false;
                        this.resetInfo();
                        this.qryAddressManageList();
                    });
                }
            },
            resetInfo(){
                this.addressInfo={
                    provinceId:null,
                    area:null,
                    address:null,
                    zipCode:null,
                    nickName:null,
                    phoneNo:null,
                    defaultFlagCk:false,
                    defaultFlag:null,
                    userId:this.$store.state.user.userInfo.id
                };
            },
            qryAddressManageList(){
                qryAddressManageList({
                    pageSize:10,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                }).then(response=>{
                    this.addressList = response.data;
                    if(this.addressList.length==1){
                        this.address = 0;
                    }
                    if(this.addressList.length>1){
                        for(var i=0;i<this.addressList.length;i++){
                            if(this.addressList[i].defaultFlag==1){
                                this.address = i;
                                break;
                            }
                        }
                    }

                });
            },
            headerRowStyle(){
                return "font-size:14px;\n" +
                    "font-family:'Microsoft YaHei';\n" +
                    "font-weight:400;\n" +
                    "color:#1D1D1D;background:#F8FFD0;";
            },
            submitOrder(){
                if(null == this.address){
                    this.$message.error('请选择地址!');
                    return;
                }
                if(this.invoiceVal!=0&&this.invoiceVal!='0' && this.invoiceRadio==0 ){
                    this.btnAddInvoice(this.invoiceVal);
                    this.submitOrderFlag = true;
                    return;
                    /*this.$message.error('请选择发票!');
                    return;*/
                }
                var orderInfo={
                    totalPrice:this.totalPrice,
                    sellerName:this.orderList[0].userName,
                    itemNumber:this.totalNumber,
                    unitPrice:this.orderList[0].unitPrice,
                    status:1,
                    userId:this.$store.state.user.userInfo.id,
                    payStatus:1,
                    addressId:this.addressList[this.address].id,
                    remark:this.textarea,
                    productId:this.productIds
                };
                saveOrder(orderInfo).then(response=>{
                    delUserShopping({ids:this.shoppingIds}).then(response=>{
                    });
                    if(this.invoiceRadio!=0){
                        var irpar = {
                            userId:this.$store.state.user.userInfo.id,
                            orderId:response.data.orderId,
                            sellerName:orderInfo.sellerName,
                            bayName:this.itemNames,
                            totalPrice:this.totalPrice
                        }
                        saveInvoiceRecord(irpar).then(response=>{});
                    }
                    this.orderList[0].orderId = response.data.orderId;
                    this.orderList[0].orderNum = response.data.orderNum;
                    this.orderList[0].totalPrice = this.totalPrice;
                    // this.orderList[0].userId = orderInfo.userId;
                    this.orderList[0].itemNames = this.itemNames;
                    this.$router.push({path:"/orderPay",query:{orderInfo:this.orderList}});
                });
                this.orderList.forEach(function(item,index){
                    if(item.userId){
                        var msg={
                            userId:item.userId,
                            productId:item.projectId,
                            content:'恭喜您,'+item.itemName+'产品以'+item.totalPrice+'元成功卖出，请您留意手机信息，及时为卖家安排发货。感谢您对本平台的支持！',
                            msgType:1
                        }
                        saveMsg(msg).then(response=>{});
                    }
                });
            },
        //发票操作
            queryInvoice(val){
                qryInvoiceUserList({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    var list = response.data;
                    this.invoiceList = [];
                    // this.invoiceList = response.data;
                    for(var i=0;i<list.length;i++){
                        if(this.invoiceVal=='1'  && undefined==list[i].tfn || list[i].tfn==''){
                            this.invoiceList.push(list[i]);
                            if(list[i].defaultFlag==1 ){
                                this.invoiceRadio = list[i].id;
                                this.invoiceInfo = list[i];
                            }
                        }else if(this.invoiceVal=='2'  && undefined!=list[i].tfn && list[i].tfn!=''){
                            this.invoiceList.push(list[i]);
                            if(list[i].defaultFlag==1 ){
                                this.invoiceRadio = list[i].id;
                                this.invoiceInfo = list[i];
                            }
                        }
                    }
                    if(this.invoiceRadio=='' && this.invoiceList.length>0){
                        this.invoiceRadio = this.invoiceList[0].id;
                        this.invoiceInfo =  this.invoiceList[0];
                    }
                    this.$forceUpdate();
                    if(this.submitOrderFlag){
                        this.submitOrderFlag = false;
                        this.submitOrder();
                    }
                });
            },
            invoiceRadioCg(val){
                for(var i=0;i<this.invoiceList.length;i++){
                    if(this.invoiceList[i].id==val){
                        this.invoiceInfo =  this.invoiceList[i];
                        break;
                    }
                }
            },
            changeHandler(value) {
                this.showInvoiceBox = false;
            },
            btnShowInvoiceBox(val){
                this.queryInvoice(val);
                this.showInvoiceBox=true;
            },
            btnAddInvoice(type){
                if(null == this.invoiceInfo.invoiceName || ''==this.invoiceInfo.invoiceName.trim()){
                    this.$message({
                        message: '请填写发票名称',
                        type: 'warning'
                    });
                    return;
                }
                if(type==2){
                    if(null == this.invoiceInfo.tfn || ''==this.invoiceInfo.tfn.trim()){
                        this.$message({
                            message: '请填写税号',
                            type: 'warning'
                        });
                        return;
                    }
                    if(!(/^1[3456789]\d{9}$/.test(this.invoiceInfo.phone))){
                        this.$message.error('手机号格式错误！')
                        return;
                    }
                    if(!(/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(this.invoiceInfo.email))){
                        this.$message.error("邮箱格式错误");
                        return;
                    }
                }
                qryInvoiceUserList({userId:this.$store.state.user.userInfo.id,invoiceName:this.invoiceInfo.invoiceName}).then(response=>{
                    if(response.data.length>0){
                        this.$message.error('该名称已存在！');
                        return;
                    }
                    this.invoiceInfo.userId = this.$store.state.user.userInfo.id;
                    saveInvoiceUser(this.invoiceInfo).then(response=>{
                        this.queryInvoice();
                        if(!this.submitOrderFlag){
                            this.$message({
                                message: '保存成功',
                                type: 'success'
                            });
                        }
                    });
                })

            },
            btnConfirm(){
                this.showInvoiceBox = false;
            },
            btnCancel(){
                this.showInvoiceBox = false;
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
    .orderDetail{margin-bottom: 100px;}
    /deep/ .el-radio__input.is-checked+.el-radio__label {
        color: rgba(255,165,85,1);
    }
    /deep/ .el-radio.allRadio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
    /deep/ .allCheck .el-checkbox__input.is-checked + .el-checkbox__label{color: #024ECC;}
    /deep/ .el-button.btnNoBgdOrg:focus, .el-button.btnNoBgdOrg:hover{color: #F74A0D;border-color: #F74A0D;background: none;}
    .itemName-a{
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:#024ECC;
        text-decoration:none;
    }
    .unitPrice-span{
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:rgba(202,0,0,1);
    }
    .addressLi li{display: flex;display: -webkit-flex;align-items:center;-webkit-align-items:center;margin-top: 20px;}
    .addressLi li label{
        font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);width: 90px;padding-left: 10px;
    }
/*    发票信息*/
    .choiceInvoice /deep/ .el-radio-button{margin-right: 15px;}
    .choiceInvoice /deep/ .el-radio-button__inner{width: 66px;height: 25px;background: #eee;color:#7D7D7D;font-size: 14px; line-height: 25px;padding: 0;border-radius: 3px;border: 0.1px solid #CCC;}
    .choiceInvoice /deep/ .el-radio-button__orig-radio:checked + .el-radio-button__inner{width: 66px;height: 25px;background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);color: #FFF;font-size: 14px;border: 0.1px solid #FF8315;box-shadow:none;}
    .choiceInvoice /deep/ .el-radio-button:focus:not(.is-focus):not(:active):not(.is-disabled){box-shadow: none;}
    .invoiceWrap /deep/ .el-form-item{margin-bottom: 10px;}
    .invoiceList{width: 362px;background: #FFF; padding: 6px 28px 10px;border: 0.3px solid #CCC; position: absolute;top:105px;left:670px;z-index: 9;}
    .invoiceList /deep/ .el-col{margin-bottom: 24px;}
    .invoiceList /deep/ .el-button{width: 45px;height: 20px;padding: 0;border: none;}
    .invoiceList /deep/ .el-radio{display: block;margin-top: 15px;}
    .invoiceList /deep/ .el-radio__inner{width: 10px;height: 10px;}
    .invoiceList /deep/ .el-radio__input.is-checked .el-radio__inner{border-color: #CA0000;background: #CA0000;}
    .invoiceList /deep/ .el-radio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner{box-shadow: none;}
    .invoiceList p{margin-left: 20px;}
</style>