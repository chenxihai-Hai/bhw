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
                <el-form-item label="发布人：" prop="name">
                        <el-input placeholder="选填" v-model="formInline.publisher">
                        </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList"  @click="loadOrderData()">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-bottom: 32px;">
                <el-col>
                    <el-button class="btnColorOrg btnQueryList" @click="handleDel(undefined)">一键下架</el-button>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addLegalBtn">添加</el-button>
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
                        width="55">
                </el-table-column>
                <el-table-column
                        fixed
                        label="编码"
                        type="index"
                        width="50">
                </el-table-column>
                <el-table-column
                        fixed
                        prop="serverName"
                        label="服务标题"
                        :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="businessName"
                        label="业务类型"
                        width="100">
                </el-table-column>
                <el-table-column
                        label="供/需"
                        width="80">
                    <template slot-scope="scope">
                        {{scope.row.checkType==1?'供':'需'}}
                    </template>
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
                        prop="publisher"
                        label="发布人/企业"
                        width="120" :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        prop="contactName"
                        label="联系人"
                        width="100">
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
                        prop="keyWord"
                        label="关键字"
                        width="100">
                </el-table-column>
                <el-table-column
                        label="所在地"
                        width="100">
                    <template slot-scope="scope">
                        {{scope.row.provinceName}}{{scope.row.cityName}}
                    </template>
                </el-table-column>
                <el-table-column label="状态" fixed="right" width="120" >
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
                                @click="handleUp(scope.row)">编辑</el-button>
                        <!--<el-button
                                size="mini"
                                type="danger"
                                @click="handleDel(scope.row)">下架</el-button>-->
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
                        <el-button
                                style="width: 58px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                @click="handleUp(scope.row)">编辑</el-button>
                        <br/>
                        <el-button
                                style="width: 58px;margin-top: 5px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                @click="delInfo(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="text-align: center;"  class="pageWrap">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :page-size="formInline.pageSize"
                        :current-page="formInline.curPage"
                        @current-change="currentChange"
                        :total="pageTotal"  class="pageBox">
                </el-pagination>
            </div>
        </div>
        <fiscalUploading ref="fiscalUploadingRef"></fiscalUploading>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {getInfoList,infoAudit,serverTakeOff,upFiscalTaxInfo,delFiscalTaxInfo} from "../../../api/infoManage/fiscalTaxInfo";
    import fiscalUploading from "../fiscalTaxInfo/fiscalUploading";
    export default {
        name: "fiscalTaxInfoList",
        comments: { fiscalUploading },
        data(){
            return {
                formInline:{
                    checkType:"",
                    publisher:null,
                    pageSize:3,
                    curPage:1
                },
                pageTotal:0,
                leixinselect: [
                    {value: "1", label: "类型"},
                ],
                tableData:[
                    {
                        id:"",
                        serverName:"",
                        checkTypeName:"",
                        originalPrice:"",
                        price:"",
                        budget:"",
                        businessName:"",
                        publisher:"",
                        contactName:"",
                        contactPhone:"",
                        contactUnit:"",
                        keyWord:"",
                        address:""
                    }
                ],
                productSelect:[]
            }
        },
        created(){
            this.loadOrderData();
        },
        methods: {
            delInfo(obj){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delFiscalTaxInfo({ids:obj.id}).then(response=>{
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
            addLegalBtn(){
                this.$nextTick(function() {
                    this.$refs.fiscalUploadingRef.fiscalDialogVisible = true;
                });
            },
            handleUp(row){
                this.$nextTick(function() {
                    this.$refs.fiscalUploadingRef.productInfo = row;
                    this.$refs.fiscalUploadingRef.productInfo.cityId=null;
                    this.$refs.fiscalUploadingRef.fiscalDialogVisible = true;
                });
            },
            upHandleDel(row){
                upFiscalTaxInfo({id:row.id,status:2}).then(response=>{
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
                if(undefined == row && this.productSelect.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
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
                            content:undefined!=row?'对财税服务下的产品('+row.serverName+')进行了下架':'对财税服务下的产品进行了批量下架'
                        };
                        saveBackOperate(par).then();

                        this.loadOrderData();
                    })
                })
            },
            currentChange(val) {
                this.formInline.curPage = val;
                this.loadOrderData();
            },
            loadOrderData() {
                getInfoList(this.formInline).then(response => {
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                })
            },
            handleArgee(index, row) {
                var req = {type: 1, idList: row.id}
                infoAudit(req).then(response => {
                    if (response.data) {
                        this.$message.success('成功通过!');
                    } else {
                        this.$message.error('通过失败!');
                    }
                })
            },
            handleDisAgree(index, row) {
                var req = {type: 2, idList: row.id}
                infoAudit(req).then(response => {
                    if (response.data) {
                        this.$message.success('成功驳回!');
                    } else {
                        this.$message.error('驳回失败!');
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>