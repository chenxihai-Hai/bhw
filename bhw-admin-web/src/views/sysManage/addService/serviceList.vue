<template>
    <div class="VipAdmin" style="padding:22px 16px;">
        <div class="bgColorF" style="padding: 38px 25px;">
            <h3>增值服务（会员）开通信息列表</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline"  style="margin: 33px 40px;">
                <el-form-item label="会员姓名" :label-width="formLabelWidth">
                    <el-input v-model="formInline.userName" placeholder="请输入" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="会员类型" :label-width="formLabelWidth">
                    <el-select v-model="formInline.type" placeholder="全部" class="allSelect">
                        <el-option label="超级会员" value="2"></el-option>
                        <el-option label="标准会员" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button @click="qryUserMemberList" class="btnColorOrg">查询</el-button>
                </el-form-item>
            </el-form>
            <div style="margin:20px 20px 0;">
                <el-table :data="tableData" border style="width: 100%" class="allTable">
                    <el-table-column
                            type="index"
                            label="ID"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="userName"
                            label="会员姓名">
                    </el-table-column>
                    <el-table-column
                            prop="nickName"
                            label="用户昵称">
                    </el-table-column>
                    <el-table-column
                            label="增值服务类型">
                        <template slot-scope="scope">
                            {{scope.row.type==1?'标准会员':'高级会员'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="totalPrice"
                            label="开通价格/元">
                    </el-table-column>
                    <el-table-column
                            prop="totalPrice"
                            label="实收金额/元">
                    </el-table-column>
                    <el-table-column
                            label="到期时间">
                        <template slot-scope="scope">
                            {{scope.row.indate}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="会员状态">
                        <template slot-scope="scope">
                            {{scope.row.status==0?'失效':'开通中'}}
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pageWrap">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page.sync="formInline.curPage"
                            :page-size="formInline.pageSize"
                            layout="total,prev, pager, next, jumper"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
    import {qryUserMemberList} from '../../../api/user/userManage';
    export default {
        name: "serviceList",
        data() {
            return {
                formLabelWidth:'100px',
                pageTotal:0,
                dialogDelFormVisible:false,
                formSearch:{userId:'',userName:'',nickNae:'',statusId:0,userType:0,curPage: 1, pageSize:10},
                tableData: [
                    {
                        id: "001",
                        name: "黄惠丽",
                        zname: "黄惠丽",
                        userType: '超级会员',
                        phone: '13748546921',
                        time: '2020·03·24·14：30-2030·03·24·14：30',
                    }
                ],
                formInline: {
                    userName: null,
                    nickName: null,
                    status: null,
                    type: null,
                    curPage:1,
                    pageSize:5
                },
                dialogFormVisible: false,
            }
        },
        mounted(){
            this.qryUserMemberList();
        },
        methods: {
            qryUserMemberList(){
                qryUserMemberList(this.formInline).then(response=>{
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            handleSizeChange(val) {
                this.formInline.curPage = val;
                this.qryUserMemberList();
            },
            handleCurrentChange(val) {
                this.formInline.curPage = val;
                this.qryUserMemberList();
            }
        }
    }
</script>

<style scoped>

</style>