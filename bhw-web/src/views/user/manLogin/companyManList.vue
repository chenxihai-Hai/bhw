<template>
    <div class="companyManList" style="padding: 30px 20px 20px;">
        <h3 style="padding: 0 20px;">检索参数</h3>
        <el-form :inline="true" :model="formSeach" class="demo-form-inline" style="margin: 23px 20px;">
            <el-form-item label="姓名">
                <el-input placeholder="请输入" v-model="formSeach.title" class="allIpt" style="width: 150px;margin-right: 20px;"></el-input>
            </el-form-item>
            <el-form-item label="专业领域">
                <el-select v-model="formSeach.borkerId" placeholder="请选择" style="width: 150px;margin-right: 20px;">
                    <el-option
                            v-for="item in borkerType"
                            :key="item.name"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="申请状态">
                <el-select v-model="formSeach.status" placeholder="请选择" style="width: 150px;margin-right: 20px;">
                    <template>
                        <el-option v-for="litem in statusType" :key="litem.statusId"
                                   :label="litem.statusName"
                                   :value="litem.statusId">
                        </el-option>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button class="btnColorOrg" style="border: none;" @click="btnSearch">查询</el-button>
            </el-form-item>
        </el-form>
        <div>
            <div style="margin-bottom: 20px;">
                <el-button class="btnColorOrg btnAddMore" @click="upAll">一键申请</el-button>
                <el-button class="btnColorBlue btnDelMore" @click="delAll">一键删除</el-button>
            </div>
            <div>
                <el-table
                        @selection-change="handleSelectionChange"
                        :data="dataList"
                        style="width: 100%" class="user-table" border>
                    <el-table-column
                            type="selection"
                            width="55">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="用户名">
                    </el-table-column>
                    <el-table-column label="专业领域" prop="brokerName">
                    </el-table-column>
                    <el-table-column label="图片" width="120px">
                        <template slot-scope="scope">
                            <img style="width: 80px; height: 80px" :src="scope.row.photo">
                        </template>
                    </el-table-column>
                    <el-table-column label="姓别">
                        <template slot-scope="scope">{{scope.row.sex==1?'男':'女'}}</template>
                    </el-table-column>
                    <el-table-column
                            prop="email"
                            label="邮箱">
                    </el-table-column>
                    <el-table-column
                            prop="phone"
                            label="手机号">
                    </el-table-column>
                    <el-table-column
                            prop="seniority"
                            label="工龄/年">
                    </el-table-column>
                    <el-table-column
                            prop="mainAchieve"
                            label="成就">
                    </el-table-column>
                    <el-table-column label="状态">
                        <template slot-scope="scope">
                            <!--<span :class="scope.row.status==1?'':'txtRed'">-->
                            <span :class="scope.row.status==1?'':'txtRed'">{{scope.row.status==3?'已通过':'未通过'}}</span>
                            <template v-if="scope.row.status==1">
                                <p>驳回原因为:{{scope.row.result}}</p>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150px">
                        <template slot-scope="scope">
                            <el-button class="btnColorOrg btnMan"
                                       @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button class="btnColorBlue btnMan"
                                       @click="handleDelete(scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pageWrap">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :page-size="formSeach.pageSize"
                        :current-page="formSeach.curPage"
                        @current-change="currentChange"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import {backQryBrokerList,delBrokerRegister,upBrokerAdvisory} from "../../../api/middleman/middelman";
    export default {
        name: "companyManList",
        data(){
            return{
                manType:1,
                pageTotal:0,
                borkerType:[{id:1,name:'知识产权'},{id:2,name:'财税服务'},{id:3,name:'科技咨询'},{id:4,name:'科技金融'},{id:5,name:'法律服务'}
                    ,{id:6,name:'技术合同登记'},{id:7,name:'成果认定'},{id:8,name:'商务代写'},{id:9,name:'策划设计'},{id:10,name:'技术开发'},
                    {id:11,name:'教育培训'},{id:12,name:'投资孵化'}],
                statusType:[{statusId:null,statusName:'请选择'},{statusId:1,statusName:'未通过'},{statusId:2,statusName:'待审核'},{statusId:3,statusName:'已通过'}],
                formSeach:{name:null,borkerId:null,status:null,pageSize:10,curPage:1,userId:this.$store.state.user.userInfo.id},
                dataList:[],
                selectTd:[]
            }
        },
        created() {
            this.queryDataList();
        },
        methods:{
            handleSelectionChange(val){
                this.selectTd = val;
            },
            upAll(){
                if(this.selectTd.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
                }
                var arr = [];
                this.selectTd.forEach(p => {
                    idList.push(p.id);
                })
                var promList=[];
                arr.forEach(id=>{
                    var par = new Promise((resolve, reject) => {
                        upBrokerAdvisory({id:id,status:2}).then(response=>{
                            resolve(response.data);
                        });
                    });
                    promList.push(par);
                })
                Promise.all(promList).then(response=>{
                    this.$message.success("操作成功");
                    this.queryDataList();
                });
            },
            delAll(row){
                if(undefined == row && this.selectTd.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
                }
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var idList = [];
                    if(row){
                        idList.push(row.id);
                    }else{
                        this.selectTd.forEach(p => {
                            idList.push(p.id);
                        })
                    }
                    delBrokerRegister({idList:idList.join(',')}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        this.queryDataList();
                    });
                });
            },
            btnSearch(){
                this.queryDataList();
            },
            handleEdit(row) {
                this.$emit("upInfo-event",row);
            },
            handleDelete(row) {
                this.delAll(row);
            },
            currentChange(val){
                this.formSearch.curPage = val;
                this.queryDataList();
            },
            queryDataList(){
                backQryBrokerList(this.formSeach).then(response=>{
                    this.dataList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .txtRed{color: #ca0000;}
    .btnColorBlue.btnAddMore{width: 116px;height: 32px;padding: 0;border: none;}
    .btnNoBgdBule.btnDelMore{width: 116px;height: 32px;padding: 0;}
    .btnColorBlue.btnMan,.btnColorOrg.btnMan{width: 59px;height: 24px;font-size: 12px;padding: 0;border: none;}
    /deep/ .user-table.el-table td,/deep/ .user-table.el-table th{text-align: center;}
</style>