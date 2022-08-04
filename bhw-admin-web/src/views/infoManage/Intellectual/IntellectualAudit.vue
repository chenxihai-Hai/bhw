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
                    <el-button class="btnColorOrg btnQueryList" @click="loadOrderData()">查询</el-button>
                </el-form-item>
                <hr/>
                <el-form-item>
                    <el-button  class="btnColorOrg btnQueryList" @click="batchUpAuditStatus(undefined,1)">一键通过</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  class="btnColorBlue btnQueryList"  @click="batchUpAuditStatus(undefined,2)">一键驳回</el-button>
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
                <el-table-column label="置顶-精品" >
                    <template slot-scope="scope">
                        {{scope.row.boutique==0?'否':'是'}}
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
                        label="审核状态"
                >
                    <template slot-scope="scope">
                        <template v-if="scope.row.auditStatus==0">
                            待审核
                        </template>
                        <template v-if="scope.row.auditStatus==1">
                            通过
                        </template>
                        <template v-if="scope.row.auditStatus==2">
                            未通过
                        </template>
                    </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right" width="150">
                    <template slot-scope="scope">
                        <el-button
                                style="width: 58px;"
                                class="btnColorOrg btnUser"
                                size="mini"
                                @click="batchUpAuditStatus(scope.row,1)">通过</el-button>
                        <el-button
                                class="btnColorBlue btnUser"
                                size="mini"
                                type="primary"
                                @click="batchUpAuditStatus(scope.row,2)">驳回</el-button>
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
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryProjectList,qryIntellectualType,batchUpAuditStatus} from "../../../api/infoManage/intellectual";
    import {saveMsg} from '../../../api/user/userManage'
    export default {
        name: "IntellectualAudit",
        data(){
            return{
                projectType:[{id:'1',name:"商标"},{id:'2',name:"专利"},{id:'3',name:"域名"}],
                formInline:{
                    projectType:"",
                    industryId:"",
                    needType:null,
                    codeNumber:"",
                    pageSize:3,
                    curPage:1,
                    auditStatus:0
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
            batchUpAuditStatus(row,st){
                if(undefined == row && this.productSelect.length<1){
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
                if (st==1) {
                    batchUpAuditStatus({"pIds":pIds.join(','),status:st}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        this.loadListData();
                        if(row){
                            var msg={
                                userId:row.userId,
                                productId:row.id,
                                content:'尊敬的用户,您您发布的产品:'+row.projectName+'已通过！',
                                msgType:3
                            }
                            saveMsg(msg).then(response=>{});
                        }
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:undefined==row?'对知识产权下的产品进行了批量审核为'+(st==1?'通过':'驳回'):'对知识产权下的产品('+row.projectName+')进行了审核为'+(st==1?'通过':'驳回')
                        };
                        saveBackOperate(par).then();
                    });
                }else {
                    this.$prompt('请输入驳回原因', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        cancelButtonClass:'btnColorOrg',
                        confirmButtonClass:'btnColorBlue'
                    }).then(({ value }) => {
                        batchUpAuditStatus({"pIds":pIds.join(','),status:st}).then(response=>{
                            this.$message({
                                type: 'success',
                                message: '操作成功!'
                            });
                            this.loadListData();
                            if(row){
                                var msg={
                                    userId:row.userId,
                                    productId:row.id,
                                    content:'尊敬的用户,您发布的:'+row.projectName+'被驳回。原因如下:'+value+'!',
                                    msgType:3
                                }
                                saveMsg(msg).then(response=>{});
                            }
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:undefined==row?'对知识产权下的产品进行了批量审核为'+(st==1?'通过':'驳回'):'对知识产权下的产品('+row.projectName+')进行了审核为'+(st==1?'通过':'驳回')
                            };
                            saveBackOperate(par).then();
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '取消输入'
                        });
                    });
                }

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