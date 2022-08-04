<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formInline"  class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="供需选择" prop="resource">
                    <el-radio-group v-model="formInline.checkType">
                        <el-radio label="1" >供（我要卖）</el-radio>
                        <el-radio label="2">需（我要买）</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="供需类别：" prop="name">
                    <el-select v-model="formInline.businessType" placeholder="请选择 " style="width: 140px;">
                        <el-option
                                v-for="item in options"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="技术名称：" prop="name">
                        <el-input placeholder="选填" v-model="formInline.techName">
                        </el-input>
                </el-form-item>
                <el-form-item label="行业类型：" prop="name">
                    <el-select v-model="formInline.intellectualType" placeholder="请选择 " style="width: 140px;">
                        <el-option
                                v-for="item in leixinselect"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList" @click="loadOrderData()">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-bottom: 30px;">
                <el-col>
                    <el-button class="btnColorOrg btnQueryList" @click="handleDel(undefined)">一键下架</el-button>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addOlogyBtn">添加</el-button>
                </el-col>
            </el-row>
            <el-table
                    :data="tableData"
                    class="allTable"
                    @selection-change="handleSelectionChange"
                    border
                    style="width: 100%">
                <el-table-column
                        fixed
                        type="selection"
                        width="54">
                </el-table-column>
                <el-table-column
                        fixed
                        label="编码"
                        type="index"
                        width="50">
                </el-table-column>
                <el-table-column
                        fixed
                        prop="techName"
                        label="技术名称"
                        width="120" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="checkTypeName"
                        label="供/需"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="originalPrice"
                        label="原价"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="售价"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="budget"
                        label="预算"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="businessName"
                        label="供需类别"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="intellectualName"
                        label="行业类别"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="publisher"
                        label="发布人/企业"
                        width="120" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="contactName"
                        label="联系人"
                        width="80" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="contactPhone"
                        label="联系方式"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="contactUnit"
                        label="联系单位"
                        width="120" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="cutoffDate"
                        label="截止日期"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="keyWord"
                        label="关键字"
                        width="100" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="所在地"
                        width="100">
                </el-table-column>
                <el-table-column label="状态" width="120" fixed="right">
                    <template slot-scope="scope">
                        <el-switch
                                @change="switchCk(scope.row.status,scope.row)"
                                :value="scope.row.status+''"
                                inactive-value="3"
                                active-value="2"
                                active-color="#024ECC"
                                inactive-color="#DCDCDC">
                        </el-switch>
                        <template v-if="scope.row.status==2">
                            正常
                        </template>
                        <template v-if="scope.row.status==3">
                            已下架
                        </template>
                    </template>
                </el-table-column>
                <el-table-column label="操作"  fixed="right" width="150">
                    <template slot-scope="scope">
                        <el-button
                                style="width: 58px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                type="primary"
                                @click="handleUp(scope.row)">编辑</el-button>
                        <el-button v-if="scope.row.status==2"
                                class="btnColorBlue btnUser"
                                size="mini"
                                type="primary"
                                @click="handleDel(scope.row)">下架</el-button>
                        <el-button v-if="scope.row.status==3"
                                   class="btnColorBlue btnUser"
                                   size="mini"
                                   type="primary"
                                   @click="upHandleDel(scope.row)">上架</el-button>
                        <br/>
                        <el-button
                                style="width: 58px;margin-top: 5px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                @click="delInfo(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="text-align: center;" class="pageWrap">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :page-size="formInline.pageSize"
                        :current-page="formInline.curPage"
                        @current-change="currentChange"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
        <technicalUploading ref="ologyUploadingRef"></technicalUploading>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {getBussinessType,getInfoList,getTechologyInfo,serverTakeOff,upTechologyInfo,delTechologyInfo} from "../../../api/infoManage/techology";
    import {qryScienceRequireTypeList} from "../../../api/projectDw/projectDw";
    import technicalUploading from "../techology/technicalUploading";
    export default {
        name: "techologyList",
        comments: { technicalUploading },
        data(){
            return {
                formInline:{
                    checkType:null,
                    businessType:null,
                    techName:null,
                    intellectualType:null,
                    pageSize:3,
                    curPage:1
                },
                pageTotal:0,
                options:[
                    {id:"1",name:"测试类别"}
                ],
                leixinselect: [
                    {value: "1", label: "类型"},
                ],
                tableData:[
                    {
                        id:"",
                        techName:"黄辉里",
                        checkTypeName:"供",
                        originalPrice:"面谈",
                        price:"售价",
                        budget:"预算",
                        businessName:"技术难题",
                        intellectualName:"电子信息",
                        publisher:"黄辉里",
                        contactName:"黄辉里",
                        contactPhone:"13777722222",
                        contactUnit:"北海技术",
                        cutoffDate:"2019-02-03",
                        keyWord:"关键字",
                        address:"广西北海",
                        content:''
                    }
                ],
                productSelect:[]

            }
        },
        created(){
            getBussinessType({type: 16}).then(response => {
                this.options = response.data;
             })
            qryScienceRequireTypeList().then(response => {
                this.leixinselect = response.data;
            })
            this.loadOrderData();
        },
        methods:{
            delInfo(obj){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delTechologyInfo({ids:obj.id}).then(response=>{
                        this.loadOrderData();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    });
                }).catch(() => {
                });
            },
            handleSelectionChange(val){
                this.productSelect = val;
            },
            addOlogyBtn(){
                this.$nextTick(function() {
                    this.$refs.ologyUploadingRef.ologyDialogVisible = true;
                });
            },
            handleUp(row){
                getTechologyInfo({id:row.id}).then(response=>{
                    this.$nextTick(function() {
                        this.$refs.ologyUploadingRef.productinfo = response.data;
                        this.$refs.ologyUploadingRef.productinfo.cityId=null;
                        this.$refs.ologyUploadingRef.productinfo.cutoffDate = null;
                        this.$refs.ologyUploadingRef.productinfo.createTime = null;
                        this.$refs.ologyUploadingRef.ologyDialogVisible = true;
                    });
                });
            },
            upHandleDel(row){
                upTechologyInfo({id:row.id,status:2}).then(response=>{
                    this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                    this.loadOrderData();
                });
            },
            switchCk(st,row){
                if(st==2){
                    this.handleDel(row);
                }else{
                    this.upHandleDel(row);
                }
            },
            handleDel(row){
                if(undefined==row && this.productSelect.length<1){
                    this.$message.error('请勾选数据!');
                    return;
                }
                this.$confirm('此操作将下架选择信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var idList =[];
                    if (row) {
                        idList.push(row.id);
                    } else {
                        this.productSelect.forEach(p => {
                            idList.push(p.id);
                        })
                    }
                    serverTakeOff({idList:idList.join(',')}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:undefined!=row?'对技术供需下的产品('+row.serverName+')进行了下架':'对技术供需下的产品进行了批量下架'
                        };
                        saveBackOperate(par).then();
                        this.loadOrderData();
                    })
                }).catch(()=>{

                })
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.loadOrderData();
            },
            loadOrderData(){
                getInfoList(this.formInline).then(response => {
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                })
            },
            handleArgee(index,row){
                var req={type:1,idList:row.id}
                infoAudit(req).then(response => {
                    if (response.data) {
                        this.$message.success('成功通过!');
                    }else{
                        this.$message.error('通过失败!');
                    }
                })
            },
            handleDisAgree(index,row){
                var req={type:2,idList:row.id}
                infoAudit(req).then(response => {
                    if (response.data) {
                        this.$message.success('成功驳回!');
                    }else{
                        this.$message.error('驳回失败!');
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>