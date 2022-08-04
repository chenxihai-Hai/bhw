<template>
    <div class="messageList" style="padding: 30px 20px 20px;">
        <h3 style="padding: 0 20px;">检索参数</h3>
        <el-form :inline="true" :model="formSeach" class="demo-form-inline" style="margin: 23px 20px;">
            <el-form-item label="操作状态：">
                <el-radio v-model="formSeach.status" label="1" style="margin-right: 20px;">已处理</el-radio>
                <el-radio v-model="formSeach.status" label="2" style="margin-right: 30px;">未处理</el-radio>
            </el-form-item>
            <el-form-item label="客户姓名：">
                <el-input placeholder="请输入" v-model="formSeach.userName" class="allIpt" style="width: 150px;margin-right: 20px;"></el-input>
            </el-form-item>
            <el-form-item label="指定经纪人：">
                <el-input placeholder="请输入" v-model="formSeach.borkerUserName" class="allIpt" style="width: 150px;margin-right: 20px;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button class="btnColorOrg" style="border: none;" @click="btnSearch">查询</el-button>
            </el-form-item>
        </el-form>
        <div>
            <div style="margin-bottom: 20px;"><el-button class="btnColorBlue btnDelMore" @click="delAll">一键删除</el-button></div>
            <div>
                <el-table
                        @selection-change="handleSelectionChange"
                        :data="dataList" @cell-click="updis"
                        style="width: 100%" class="user-table" border>
                    <el-table-column
                            type="index" label="序号"
                            :index="indexMethod" width="50">
                    </el-table-column>
                    <el-table-column
                            prop="userName"
                            label="客户姓名">
                    </el-table-column>
                    <el-table-column prop="phoneNo" label="手机号">
                    </el-table-column>
                    <el-table-column prop="serverName" label="服务名称">
                    </el-table-column>
                    <el-table-column prop="serviceType" label="服务类型">
                    </el-table-column>
                    <el-table-column
                            prop="serverRemark"
                            label="服务需求">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="留言时间">
                    </el-table-column>
                    <el-table-column
                            prop="borkerUserName"
                            label="指定经纪人">
                    </el-table-column>
                    <el-table-column label="操作" width="120">
                        <template slot-scope="scope">
                        <el-switch
                                @change="switchCk(scope.row.status,scope.row)"
                                :value="scope.row.status+''"
                                inactive-value="1"
                                active-value="2"
                                active-color="#024ECC"
                                inactive-color="#DCDCDC">
                        </el-switch>
                            {{scope.row.status==1?'未处理':'已处理'}}
                        </template>
                    </el-table-column>
                    <el-table-column label="备注">
                        <template slot-scope="scope">
                            <div v-html="scope.row.disposeRemark"></div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
</template>

<script>
    import {qryServerAdvisoryList,upServerAdvisory,delServerAdvisory} from "../../../api/middleman/middelman";
    export default {
        name: "manMessageList",
        data(){
            return{
                pageTotal:0,
                formSeach:{status:null,userName:null,borkerUserName:null,pageSize:10,curPage:1,borkerUserId:this.$store.state.user.userInfo.id},
                dataList:[],
                selectTd:[]
            }
        },
        created() {
            this.queryDataList();
        },
        methods:{
            updis(row, column, cell){
                if(column.label=='备注'){
                    this.switchCk(1,row);
                }
            },
            indexMethod(index) {
                return index + 1;
            },
            handleSelectionChange(val){
                this.selectTd = val;
            },
            delAll(){
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
                    this.selectTd.forEach(p => {
                        idList.push(p.id);
                    })
                    delServerAdvisory({ids:idList.join(',')}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        this.queryDataList();
                    });
                });
            },
            switchCk(st,row){
                if(st==1){
                    this.$prompt('请输入处理备注', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        cancelButtonClass:'btnColorOrg',
                        confirmButtonClass:'btnColorBlue'
                    }).then(({ value }) => {
                        if(value){
                            row.disposeRemark = undefined==row.disposeRemark?'':row.disposeRemark;
                            var dis = '<span style="color: red;">'+new Date().format('yyyy-MM-dd hh:mm:ss')+'</span>' +
                            '<p>'+value+'</p>';
                            upServerAdvisory({id:row.id,status:2,disposeRemark:row.disposeRemark+''+dis}).then(response=>{
                                this.$message({
                                    type: 'success',
                                    message: '操作成功!'
                                });
                                this.queryDataList();
                            });
                        }
                    });
                }
            },
            btnSearch(){
                this.queryDataList();
            },
            currentChange(val){
                this.formSearch.curPage = val;
                this.queryDataList();
            },
            queryDataList(){
                qryServerAdvisoryList(this.formSeach).then(response=>{
                   this.dataList =  response.data;
                   this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .btnNoBgdBule.btnDelMore{width: 116px;height: 32px;padding: 0;}
    /deep/ .user-table.el-table td,/deep/ .user-table.el-table th{text-align: center;}
</style>