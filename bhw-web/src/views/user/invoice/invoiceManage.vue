<template>
    <div>
        <el-tabs v-model="invoiceActiveName" type="card" @tab-click="handleClick" >
            <el-tab-pane label="抬头" name="first"  >
                <template v-if="!addInvoiceFlag">
                    <ul class="invoiceUl" v-for="invoice in invoiceList" :key="invoice.id+'_taitou'">
                        <li>
                            <span class="invoiceSpan">{{invoice.invoiceName}}</span>
                        </li>
                        <li>
                            <span class="tfnSpan">税号:{{invoice.tfn}}</span>
                            <el-button class="btnNoBgdBule"  size="small" plain v-if="invoice.defaultFlag==1">默认抬头</el-button>
                            <el-button class="btnNoBgdBule" @click="upInvoice(invoice.id)" size="small" plain v-if="invoice.defaultFlag==2">设为默认</el-button>
                            <el-button class="btnColorOrg"  size="small" @click="delInvoice(invoice.id)" plain >删除</el-button>
                        </li>
                    </ul>
                    <el-button type="primary" class="btnColorOrg" style="width: 526px;margin-top: 15px;" @click="addInvoiceFlag=true">添加抬头</el-button>
                </template>
                <template v-else>
                    <div style="padding-left: 30px;padding-bottom: 40px;">
                    <ul style="margin-top: 18px;width: 453px;">
                        <li style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">添加抬头</li>
                        <li style="margin-top:10px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">如需开具增值税专用发票，需全部填写</li>
                    </ul>
                    <ul class="ulFrom">
                        <li>
                            <el-checkbox class="allCheck" v-model="checked" @change="checkFn(1)">企业</el-checkbox>
                            <el-checkbox class="allCheck" v-model="checked1" @change="checkFn(2)">个人及事业单位</el-checkbox>
                        </li>
                        <li>
                            <label>名称<i style="color: red">*</i></label>
                            <el-input v-model="invoiceInfo.invoiceName" placeholder="请输入内容"></el-input>
                        </li>
                        <template v-if="checked">
                        <li>
                            <label>税号<i style="color: red">*</i></label>
                            <el-input v-model="invoiceInfo.tfn" placeholder="请输入内容"></el-input>
                        </li>
                        <li>
                            <label>默认抬头</label>
                            <el-switch style="margin-top: 10px;"
                                    v-model="invoiceInfo.defaultCkFlag"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </li>
                        <li>
                            <label>公司地址</label>
                            <el-input v-model="invoiceInfo.companyAddr" placeholder="请输入内容"></el-input>
                        </li>
                        <li>
                            <label>电话号码</label>
                            <el-input v-model="invoiceInfo.companyAddr" placeholder="请输入内容"></el-input>
                        </li>
                        <li>
                            <label>开户银行</label>
                            <el-input v-model="invoiceInfo.companyAddr" placeholder="请输入内容"></el-input>
                        </li>
                        <li>
                            <label>银行账号</label>
                            <el-input v-model="invoiceInfo.companyAddr" placeholder="请输入内容"></el-input>
                        </li>
                        </template>
                    </ul>
                    <div style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);margin-top: 26px;"> 填写联系方式，向你同步电子发票信息 </div>
                    <ul style="margin-top: 20px;" class="ulFrom">
                        <li>
                            <label style="width: 100px;">手机号<i style="color: red">*</i></label>
                            <el-input v-model="invoiceInfo.phone" placeholder="请输入内容"></el-input>
                        </li>
                        <li>
                            <label style="width: 100px;">邮箱<i style="color: red">*</i></label>
                            <el-input v-model="invoiceInfo.email" placeholder="请输入内容"></el-input>
                        </li>
                    </ul>
                    <el-button type="primary" class="btnColorOrg" style="width: 216px;margin-top: 25px;margin-left: 84px;" @click="saveInvoice">保存</el-button>
                    </div>
                </template>
            </el-tab-pane>
            <el-tab-pane label="我的发票" name="second">
                <ul v-for="invoice in invoiceRecordList" :key="invoice.id+'_invoice'" class="ul-invoicerecord">
                    <li style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                        {{invoice.sellerName}}
                    </li>
                    <li style="margin-top: 8px;"><el-divider style="width: 96%;margin: auto;"></el-divider></li>
                    <li>
                        <label>购买名称</label>
                        <span>{{invoice.bayName}}</span>
                    </li>
                    <li>
                        <label>发票金额</label>
                        <span>{{invoice.totalPrice}}</span>
                    </li>
                    <li>
                        <label>开票时间</label>
                        <span>{{invoice.createTime}}</span>
                    </li>
                    <li style="margin-top: 8px;"><el-divider style="width: 96%;margin: auto;"></el-divider></li>
                    <li style="padding-bottom: 10px;">
                        <el-button plain @click="dialogInvoiceVisible=true" class="btnNoBgdOrg">查看发票</el-button>
                        <el-button plain class="btnNoBgdBule">发送到邮箱</el-button>
                        <el-dialog :visible.sync="dialogInvoiceVisible">
                            <img width="100%" :src="invoice.invoiceUrl" alt="">
                        </el-dialog>
                    </li>
                </ul>
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
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import {qryInvoiceUserList,saveInvoiceUser,upInvoiceUser,qryInvoicRecordList,delInvoiceUser} from "../../../api/user/invoiceManage";
    export default {
        name: "invoiceManage",
        data() {
            return {
                dialogInvoiceVisible:false,
                checked:true,
                checked1:false,
                addInvoiceFlag:false,
                invoiceActiveName:'first',
                invoiceList:[],
                invoiceRecordList:[],
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
                pageTotal:0,
                queryParam:{
                    pageSize:5,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                }
            }
        },
        created(){
            this.qryInvoiceUserList();
        },
        methods:{
            handleClick(tab, event) {
                if(tab.name=='first'){
                    this.qryInvoiceUserList();
                }else{
                    this.qryInvoicRecordList();
                }
            },
            qryInvoicRecordList(){
                qryInvoicRecordList(this.queryParam).then(response=>{
                    this.invoiceRecordList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.queryParam.curPage=val;
                this.qryInvoicRecordList();
            },
            qryInvoiceUserList(){
                qryInvoiceUserList({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    this.invoiceList = response.data;
                });
            },
            delInvoice(id){
                this.$confirm('请确认是否删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    delInvoiceUser({id:id}).then(response=>{
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.qryInvoiceUserList();
                    });
                });
            },
            upInvoice(id){
                upInvoiceUser({id:id,defaultFlag:1}).then(response=>{
                    this.qryInvoiceUserList();
                });
            },
            saveInvoice(){
                this.addInvoiceFlag = true;
                if(null == this.invoiceInfo.invoiceName || ''==this.invoiceInfo.invoiceName.trim()){
                    this.$message({
                        message: '请填写名称',
                        type: 'warning'
                    });
                    return;
                }
                if(this.checked){
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

                this.$confirm('请仔细检查是否有误,确认保存?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    qryInvoiceUserList({userId:this.$store.state.user.userInfo.id,invoiceName:this.invoiceInfo.invoiceName}).then(response=> {
                        if (response.data.length > 0) {
                            this.$message.error('该名称已存在！');
                            return;
                        }
                        if(this.invoiceInfo.defaultCkFlag){
                            for(var i=0;i<this.invoiceList;i++){
                                if(this.invoiceList[i].defaultFlag==1){
                                    upInvoiceUser({id:this.invoiceList[i].id,defaultFlag:2}).then(response=>{});
                                    break;
                                }
                            }
                        }
                        this.invoiceInfo.defaultFlag = this.invoiceInfo.defaultCkFlag?1:2;
                        saveInvoiceUser(this.invoiceInfo).then(response=>{
                            this.$message({
                                message: '保存成功',
                                type: 'success'
                            });
                            this.invoiceInfo={
                                invoiceName:null,
                                userId:this.$store.state.user.userInfo.id,
                                tfn:null,
                                defaultFlag:null,
                                companyAddr:null,
                                telePhone:null,
                                bankName:null,
                                bankCode:null,
                                phone:null,
                                email:null
                            };
                            this.addInvoiceFlag = false;
                            this.qryInvoiceUserList();
                        });
                    });

                });
            },
            checkFn(val){
                if(val==2){
                    this.checked = false;
                    this.invoiceInfo.aultFlag=null;
                    this.invoiceInfo.telePhone=null;
                    this.invoiceInfo.bankName=null;
                    this.invoiceInfo.bankCode=null;
                    this.invoiceInfo.companyAddr=null;
                }else{
                    this.checked1 = false;
                }
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-button.el-button--default.btnNoBgdOrg span{color: #F74A0D;}
    /deep/ .el-button.el-button--default.btnNoBgdBule span{color: #024ecc;}
    .ul-invoicerecord{
        width: 975px;
        border:1px solid rgba(204,204,204,1);
    }
    .ul-invoicerecord li{
        margin-top: 15px;
        padding-left: 14px;
    }
    .ul-invoicerecord li label{
        margin-top: 15px;
        font-size:12px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(125,125,125,1);
    }
    .ul-invoicerecord /deep/.el-divider--horizontal {
        display: block;
        height: 1px;
        width: 96%;
        margin: auto;
    }
    .ul-invoicerecord li span{
        margin-left: 34px;
        font-size:12px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(29,29,29,1);
    }
    .ulFrom{
        width: 526px;
        margin-top: 15px;
    }
    .ulFrom li{
        line-height: 40px;
        height: 42px;
        display: flex;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(29,29,29,1);
    }
    .ulFrom li label{
        width: 100px;
    }
    .ulFrom li /deep/.el-input__inner{
        height: 35px;
    }
    .invoiceUl{
        width: 526px;
        margin-top: 24px;
    }
    .invoiceUl li{
        line-height: 40px;
        height: 42px;
        border: 1px solid rgba(204,204,204,1);
        padding-left: 16px;
    }
    .invoiceUl li button{
        float: right;
        margin-top: 5px;
        margin-right: 16px;
    }
    .invoiceSpan{
        font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);
    }
    .tfnSpan{
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(125,125,125,1);
    }

    /deep/.el-tabs--card>.el-tabs__header {
        border-bottom: 1px solid #E4E7ED;
        background: rgba(247,255,207,1);
    }
</style>