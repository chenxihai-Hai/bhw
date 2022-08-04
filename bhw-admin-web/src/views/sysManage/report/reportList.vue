<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>举报管理</h3>
            <el-row style="margin-top: 68px;">
                <el-col>
                    <el-table  class="allTable"  :data="reportList">
                        <el-table-column label="被举报人信息" align="center">
                            <el-table-column label="序号" type="index">
                            </el-table-column>
                            <el-table-column label="举报对象">
                                <template slot-scope="scope" >
                                    <template v-if="scope.row.reportObject==1">
                                        员工
                                    </template>
                                    <template v-else>
                                        商家
                                    </template>
                                </template>
                            </el-table-column>
                            <el-table-column label="举报主题" prop="title">
                            </el-table-column>
                            <el-table-column label="举报内容" prop="content">
                            </el-table-column>
                            <el-table-column label="附件">
                                <template slot-scope="scope" >
                                    <template v-if="undefined == scope.row.url|| ''==scope.row.url">
                                        暂无附件
                                    </template>
                                    <a  v-if="undefined != scope.row.url && ''!=scope.row.url" target="_blank" :href="scope.row.url" download="举报附件" style="text-decoration:none;margin-left: 51px;color:rgba(3,154,205,1);font-size:16px;font-family:'Microsoft YaHei';font-weight:400;">查看</a>
                                </template>
                            </el-table-column>
                        </el-table-column>
                        <el-table-column label="举报人信息" align="center">
                            <el-table-column label="姓名" prop="userName">
                            </el-table-column>
                            <el-table-column label="电话" prop="phone">
                            </el-table-column>
                            <el-table-column label="邮箱" prop="email">
                            </el-table-column>
                        </el-table-column>
                        <el-table-column  label="操作"
                                          align="center">
                            <template slot-scope="scope" >
                                <span style="color: #024ECC;cursor: pointer" @click="disReport(scope.row)" v-if="scope.row.status==1">处理</span>
                                <span style="color: #000000;" v-if="scope.row.status==2">已处理</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col  class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {disposeReport,qryReportList} from "../../../api/report/reportList";
    import {saveMsg} from '../../../api/user/userManage';
    export default {
        name: "reportList",
        data(){
            return{
                formInline:{
                    pageSize:5,
                    curPage:1,
                },
                reportList:[],
                pageTotal:0
            }
        },
        methods:{
            disReport(row){
                var par={
                    id:row.id,
                    status:2,
                    disposeUserId:this.$store.state.user.id,
                    disposeUser:this.$store.state.user.userName,
                };
                disposeReport(par).then(response=>{
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'在信息举报管理下处理了'+ row.title
                    };
                    saveBackOperate(par).then();
                    this.$message.success("处理成功");

                    var msg={
                        userId:row.userId,
                        productId:null,
                        content:'尊敬的用户,您的信息举报已举报成功！',
                        msgType:3
                    }
                    saveMsg(msg).then(response=>{});
                    this.qryReportList();
                });
            },
            qryReportList(){
                qryReportList(this.formInline).then(response=>{
                    this.reportList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.qryReportList();
            }
        },
        mounted(){
            this.qryReportList();
        }
    }
</script>

<style scoped>

</style>