<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formInline"  class="demo-form-inline"  style="margin: 33px 40px;">
                <el-form-item label="供需选择" prop="resource">
                    <el-radio-group v-model="formInline.needType">
                        <el-radio label="1" >供（我要卖）</el-radio>
                        <el-radio label="2">需（我要买）</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="所属类别：" prop="name">
                    <el-select v-model="formInline.projectType" @change="qryIntellectualType" placeholder="请选择 ">
                        <el-option
                                v-for="item in projectType"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="行业类别：" prop="name">
                    <el-select v-model="formInline.industryId" placeholder="请选择 ">
                        <el-option
                                v-for="item in industryType"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专利号：" prop="name">
                        <el-input placeholder="选填" v-model="formInline.codeNumber">
                        </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList" @click="loadListData()">查询</el-button>
                </el-form-item>
                <hr/>
                <el-form-item label="是否置顶" prop="resource">
                    <el-radio-group v-model="formInline.boutique">
                        <el-radio label="1" >已置顶</el-radio>
                        <el-radio label="0">未置顶</el-radio>
                    </el-radio-group>
                </el-form-item>
                <hr/>
                <el-form-item>
                    <el-button  class="btnColorOrg btnQueryList" @click="batchUp(3,undefined)">一键下架</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  class="btnColorBlue btnQueryList"  @click="batchUpBoutique(1)">一键置顶</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnNoBgdBlue btnQueryList" style="width: 107px;" @click="batchUpBoutique(0)">一键取消置顶</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button  class="btnNoBgdOrg btnQueryList"  @click="addIntelle">添加</el-button>
                </el-form-item>
            </el-form>
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
                        prop="projectName"
                        label="产品名称"
                        :show-overflow-tooltip="true">
                </el-table-column>
                <el-table-column
                        label="供/需"
                       >
                    <template slot-scope="scope">
                        {{scope.row.needType==1?'供':'需'}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="unitPrice"
                        label="售价"
                       >
                </el-table-column>
                <el-table-column
                        label="所属类别"
                      >
                    <template slot-scope="scope">
                        <template v-if="scope.row.projectType==1">
                            商标
                        </template>
                        <template v-if="scope.row.projectType==2">
                            专利
                        </template>
                        <template v-if="scope.row.projectType==3">
                            域名
                        </template>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="industryName"
                        label="行业类别"
                       >
                </el-table-column>
                <el-table-column
                        prop="codeNumber"
                        label="注册号/专利号"
                >
                </el-table-column>
                <el-table-column
                    prop="intellectualName"
                    label="交易类型"
                    >
                    <template slot-scope="scope">
                        <template v-if="scope.row.dealType==1">
                            转让
                        </template>
                        <template v-if="scope.row.dealType==2">
                            许可
                        </template>
                    </template>
                </el-table-column>
                <el-table-column
                        label="数量/单位"
                        >
                    <template slot-scope="scope">
                        {{scope.row.projectNumber}}{{scope.row.projectUnit}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="userName"
                        label="联系人"
                       >
                </el-table-column>
                <el-table-column
                        prop="contactPhone"
                        label="联系方式"
                      >
                </el-table-column>
                <el-table-column
                        label="图片/视频"
                     >
                    <template slot-scope="scope">
                        <el-image
                                style="width: 50px; height: 50px"
                                :src="scope.row.homeImg"
                                :preview-src-list="[scope.row.homeImg]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="projectTag"
                        label="关键字"
                >
                </el-table-column>
                <el-table-column
                        label="所在地"
                       >
                    <template slot-scope="scope">
                        {{scope.row.provinceName}}{{scope.row.cityName}}
                    </template>
                </el-table-column>
                <el-table-column label="置顶-精品" width="120">
                    <template slot-scope="scope">
                        <el-switch
                                @change="switchBoutiqueCk(scope.row.boutique,scope.row)"
                                :value="scope.row.boutique+''"
                                inactive-value="0"
                                active-value="1"
                                active-color="#024ECC"
                                inactive-color="#DCDCDC">
                        </el-switch>
                        {{scope.row.boutique==0?'未置顶':'置顶'}}
                    </template>
                </el-table-column>
                <el-table-column
                        label="详情描述"
                       >
                    <template slot-scope="scope">
                        <span style="color: #024ECC;cursor: pointer;" @click="showCt(scope.row.details)">预览</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="发布时间"
                      >
                </el-table-column>
                <el-table-column fixed="right"
                        label="显示状态" width="120" >
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
                <el-table-column label="操作" fixed="right" width="150">
                    <template slot-scope="scope">
                        <el-button
                                style="width: 58px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                @click="handleUp(scope.row)">编辑</el-button>
                        <el-button  v-if="scope.row.status==2"
                                class="btnColorBlue btnUser"
                                size="mini"
                                type="primary"
                                @click="batchUp(3,scope.row)">
                                下架
                        </el-button>
                        <el-button  v-if="scope.row.status==3"
                                    class="btnColorBlue btnUser"
                                    size="mini"
                                    type="primary"
                                    @click="batchUp(2,scope.row)">
                            上架
                        </el-button>
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
        <el-dialog
                title="内容预览"
                :visible.sync="showHtmlCt"
                width="60%"
        >
            <div v-html="shtml"></div>
        </el-dialog>
        <intellectual ref="intellectualRef"></intellectual>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryProjectList,qryIntellectualType,batchUpStatus,batchUpBoutique,delServerProject} from "../../../api/infoManage/intellectual";
    import intellectual from "../Intellectual/intellectual";
    export default {
        name: "IntellectualList",
        comments: { intellectual },
        data(){
            return{
                projectType:[{id:'',name:"所有"},{id:'1',name:"商标"},{id:'2',name:"专利"},{id:'3',name:"域名"}],
                formInline:{
                    projectType:"",
                    industryId:"",
                    needType:null,
                    codeNumber:null,
                    pageSize:6,
                    curPage:1,
                    contactPhone:'back',
                    boutique:null
                },
                pageTotal:0,
                options:[
                    {id:"1",name:"测试类别"}
                ],
                tableData:[],
                productSelect:[],
                shtml:'',
                showHtmlCt:false,
                industryType:[]
            }
        },
        created(){
            this.loadListData();
        },
        methods:{
            addIntelle(){
                this.$nextTick(function() {
                    this.$refs.intellectualRef.intelleDialogVisible = true;
                });
            },
            delInfo(obj){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delServerProject({id:obj.id}).then(response=>{
                        this.loadListData();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    });
                }).catch(() => {
                });
            },
            handleUp(row){
                this.$nextTick(function() {
                    this.$refs.intellectualRef.productInfo = row;
                    this.$refs.intellectualRef.productInfo.updateTime = null;
                    this.$refs.intellectualRef.productInfo.createTime = null;
                    this.$refs.intellectualRef.productInfo.cityId = null;
                    this.$refs.intellectualRef.intelleDialogVisible = true;
                });
            },
            loadListData(){
                qryProjectList(this.formInline).then(response=>{
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryIntellectualType(val){
                qryIntellectualType({"intellectualType":val}).then(response => {
                    this.industryType = response.data;
                });
            },
            switchCk(st,row){
                this.batchUp(st==2?3:2,row);
            },
            batchUp(st,row){
                if(this.productSelect.length<1 && undefined == row){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
                }
                var pIds = [];
                if(row){
                    pIds.push(row.id);
                }else{
                    this.productSelect.forEach(p => {
                        pIds.push(p.id);
                    })
                }

                this.$confirm('此操作将下架选择信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    batchUpStatus({"pIds":pIds.join(','),status:st}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:undefined==row?'对知识产权产品进行了批量下架':'对知识产权产品('+row.projectName+')进行了下架'
                        };
                        saveBackOperate(par).then();
                        this.loadListData();
                    });
                }).catch(() => {
                });
            },
            switchBoutiqueCk(st,row){
                if(st==1){
                    this.$confirm('此操作将选择信息取消置顶显示, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        cancelButtonClass:'btnColorOrg',
                        confirmButtonClass:'btnColorBlue',
                        type: 'warning'
                    }).then(() => {
                        batchUpBoutique({"pIds":row.id,status:0}).then(response=>{
                            this.$message({
                                type: 'success',
                                message: '操作成功!'
                            });
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:'对知识产权产品进行了取消置顶'
                            };
                            saveBackOperate(par).then();
                            this.loadListData();
                        });
                    })
                }else{
                    batchUpBoutique({"pIds":row.id,status:1}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'对知识产权产品进行了一键置顶'
                        };
                        saveBackOperate(par).then();
                        this.loadListData();
                    });
                }
            },
            batchUpBoutique(st){
                if(this.productSelect.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
                }
                var pIds = '';
                this.productSelect.forEach(p => {
                    pIds=pIds+p.id+","
                })
                this.$confirm('此操作将选择信息'+(st==0?'取消':'')+'置顶显示, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    batchUpBoutique({"pIds":pIds,status:st}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:st==1?'对知识产权产品进行了一键置顶':'对知识产权产品进行了一键取消置顶'
                        };
                        saveBackOperate(par).then();
                        this.loadListData();
                    });
                }).catch(() => {
                });
            },
            handleSelectionChange(val){
                this.productSelect = val;
            },
            showCt(html){
                this.shtml = html;
                this.showHtmlCt = true;
            },
            currentChange(val) {
                this.formInline.curPage = val;
                this.loadListData();
            }
        }
    }
</script>

<style scoped>

</style>