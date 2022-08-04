<template>
    <div class="person" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 20px;">
            <h3>商家审核-个人</h3>
            <div style="padding: 40px 10px 0px;">
                <div style="margin-bottom: 20px;">
                    <el-button type="warning" class="btnColorOrg btn" @click="argeeList">一键通过</el-button>
                    <el-button type="primary" class="btnColorBlue btn" @click="disArgeeList">一键驳回</el-button>
                </div>
                <el-table :data="tableData" style="width: 100%;" class="allTable" border @selection-change="handleSelectionChange">
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
                            label="用户名">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="真实姓名">
                    </el-table-column>
                    <el-table-column label="证件照正面">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.zheng" alt="">
                        </template>
                    </el-table-column>
                    <el-table-column label="证件照反面">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.back" alt="">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="card"
                            label="身份证号">
                    </el-table-column>
                    <el-table-column label="操作" width="155">
                        <template slot-scope="scope">
                            <template v-if="scope.row.merchantFlag==3">
                                <el-button
                                        type="warning" class="btnColorOrg btnUser"
                                        @click="handleEdit(scope.$index, scope.row)">通过
                                </el-button>
                                <el-button
                                        type="primary" class="btnColorBlue btnUser"
                                        @click="handleDelete(scope.$index, scope.row)">驳回
                                </el-button>
                            </template>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pageWrap">
                    <el-pagination
                            @current-change="handleCurrentChange"
                            :current-page.sync="formSearch.curPage"
                            :page-size="formSearch.pageSize"
                            layout="total,prev, pager, next, jumper"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {getAuditUserList,infoAudit,saveMsg} from '../../api/user/userManage'
    export default {
        name: "personAudit",
        data() {
            return {
                pageTotal:0,
                formSearch:{type:1,pageSize:10,curPage:1},
                tableData: [
                    {
                        id: "",
                        userName: "",
                        name: "",
                        pictureName: '',
                        zheng: "",
                        back: "",
                        card: "",
                    }
                ],
                idList:""
            }
        },
        created(){
            this.loadData();
        },
        methods: {
            loadData(){
                getAuditUserList(this.formSearch).then(response => {
                    this.tableData=response.data;
                })
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            handleEdit(index, row) {
                var params={type:1,idList:row.id}
                infoAudit(params).then(response => {
                    if (response.data) {
                        this.$message.info("通过成功")
                    }else{
                        this.$message.error("通过失败")
                    }
                    this.loadData();
                    var msg={
                        userId:row.id,
                        productId:null,
                        content:'尊敬的用户,您的商家申请已通过审核！',
                        msgType:3
                    }
                    saveMsg(msg).then(response=>{});
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'在用户管理下对企业用户'+row.userName+'进行了审核通过'
                    };
                    saveBackOperate(par).then();
                })
            },
            handleDelete(index, row) {
                this.$prompt('请输入驳回原因', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue'
                }).then(({ value }) => {
                    var params={type:2,idList:row.id}
                    infoAudit(params).then(response => {
                        if (response.data) {
                            this.$message.info("驳回成功")
                        }else{
                            this.$message.error("驳回失败")
                        }
                        this.loadData();
                        var msg={
                            userId:row.id,
                            productId:null,
                            content:'尊敬的用户,您的商家申请被驳回。原因如下:'+value+'！',
                            msgType:3
                        }
                        saveMsg(msg).then(response=>{});
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在用户管理下对企业用户'+row.userName+'进行了审核驳回'
                        };
                        saveBackOperate(par).then();
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });

            },
            argeeList(){
                var params={type:1,idList:this.idList}
                infoAudit(params).then(response => {
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'在用户管理下对个人用户进行了批量审核通过'
                    };
                    saveBackOperate(par).then();
                    if (response.data) {
                        this.$message.info("通过成功")
                    }else{
                        this.$message.error("通过失败")
                    }
                    this.loadData();
                })
            },
            disArgeeList(){
                var params={type:2,idList:this.idList}
                infoAudit(params).then(response => {
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'在用户管理下对企业用户进行了批量审核驳回'
                    };
                    saveBackOperate(par).then();
                    if (response.data) {
                        this.$message.info("驳回成功")
                    }else{
                        this.$message.error("驳回失败")
                    }
                    this.loadData();
                })
            },
            handleSelectionChange(val) {
                if (val.length==1)  {
                    this.idList=val[0].id;
                }else {
                    this.idList=val[0].id;
                    for (var i=1;i<val.length;i++){
                        this.idList+=","+val[i].id;
                    }
                }
            }
        }
    }
</script>

<style scoped>
    .person h3{color: #000;font-size: 18px;font-weight: 400; padding-top: 34px;}
    .btnColorOrg.btn,.btnColorBlue.btn{width: 83px;height: 34px;padding: 0;border: none;}
    .btnColorBlue.btnUser,.btnColorOrg.btnUser{font-size: 12px;width: 60px;height: 24px;padding: 0;}
    /deep/ .allTable.el-table td, /deep/ .allTable.el-table th{text-align: center;}
</style>