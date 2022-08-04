<template>
    <div class="VipAdmin" style="padding:22px 16px;">
        <div class="bgColorF" style="padding: 38px 25px;">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="会员姓名" :label-width="formLabelWidth">
                    <el-input v-model="formInline.userName" placeholder="请输入" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="用户昵称" :label-width="formLabelWidth">
                    <el-input v-model="formInline.nickName" placeholder="请输入" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="会员状态" :label-width="formLabelWidth">
                    <el-select v-model="formInline.status" placeholder="不限" class="allSelect">
                        <el-option label="失效" value="0"></el-option>
                        <el-option label="正常" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="会员类型" :label-width="formLabelWidth">
                    <el-select v-model="formInline.type" placeholder="全部" class="allSelect">
                        <el-option label="超级会员" value="2"></el-option>
                        <el-option label="标准会员" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="qryUserMemberList" class="btnColorOrg">查询</el-button>
                </el-form-item>
            </el-form>
            <div style="margin:20px 20px 0;">
                <el-button type="warning" class="btnNoBgdOrg btnDelMore" style="margin-bottom: 20px;" @click="btnDelSure">批量删除</el-button>
                <el-table :data="tableData" border style="width: 100%" class="allTable">
                    <el-table-column
                            type="selection"
                            width="50">
                    </el-table-column>
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
                            label="会员类型">
                        <template slot-scope="scope">
                            {{scope.row.type==1?'标准会员':'高级会员'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="phoneNo"
                            label="会员手机号">
                    </el-table-column>
                    <el-table-column
                            label="会员开通时间至截止时间" width="310">
                        <template slot-scope="scope">
                            {{scope.row.createTime}}-{{scope.row.indate}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <router-link class="linkDetail" :to="{path:'/detail',query:{vipParam:scope.row}}">查看详情</router-link>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pageWrap">
                    <el-pagination
                            @current-change="handleCurrentChange"
                            :current-page.sync="formInline.curPage"
                            :page-size="formInline.pageSize"
                            layout="total,prev, pager, next, jumper"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </div>
            </div>
        </div>
        <!--        删除弹框-->
        <el-dialog title="删除用户" :visible.sync="dialogDelFormVisible" class="allDialog" width="500px">
            <!--<div class="delBox">-->
                <!--<h3>敏感操作，请验证口令</h3>-->
                <!--<div style="text-align: center;"><el-input placeholder="请输入正确口令" class="iptTxt" style="width: 231px;"></el-input></div>-->
            <!--</div>-->
            <div slot="footer" class="dialog-footer" style="text-align: center;padding-bottom: 50px;">
                <el-button type="primary" class="btnColorOrg btnDiaDel" @click="btnDelSure">确定</el-button>
                <el-button class="btnColorBlue btnDiaDel" @click="dialogDelFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {qryUserMemberList} from '../../api/user/userManage';
    export default {
        name: "VipAdmin",
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
                console.log(`每页 ${val} 条`);
                this.formInline.curPage = val;
                this.qryUserMemberList();
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.formInline.curPage = val;
                this.qryUserMemberList();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            btnDelSure(){

            },
            insert() {
                this.dialogFormVisible = true;
            }
        }
    }
</script>

<style scoped>
    /deep/ .allIpt.el-input,.allSelect /deep/ .el-input{width: 150px;}
    /deep/ .allTable.el-table td, /deep/ .allTable.el-table th{text-align: center;}
    .iptTxt /deep/ .el-input__inner{text-align: center;}
    .btnColorOrg.btnDiaDel,.btnColorBlue.btnDiaDel{width: 70px;height: 27px;padding: 0;border: none;}
    /deep/ .btnNoBgdOrg.btnDelMore{width: 83px;height: 33px;padding: 0;}
    .delBox h3{color: #D90000;font-size: 18px;text-align: center;margin-bottom: 30px;}
    .linkDetail{color: #024ECC;text-decoration: none;}
</style>