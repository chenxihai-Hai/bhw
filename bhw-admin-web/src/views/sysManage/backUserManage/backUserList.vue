<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>后台管理员</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="登录名">
                    <el-input v-model="formInline.accountName" placeholder="登录名"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="formInline.phoneNo" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="formInline.roleId" placeholder="角色">
                        <el-option v-for="(k,v) in backRoleList" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  class="btnColorOrg btnQueryList" @click="qryBackUserList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addDialogVisible=true">添加</el-button>
                    <el-button type="primary" class="btnColorOrg btnQueryList" @click="delBackUser(undefined)">删除</el-button>
                </el-col>
            </el-row>
            <el-row style="margin-top: 18px;">
                <el-col>
                    <el-table :data="backUserList" class="allTable"  @selection-change="handleSelectionChange">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                label="ID" type="index"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="登录名" prop="accountName"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="用户名称" prop="userName"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="手机" prop="phoneNo"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="角色"
                                align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.accountType==1">
                                    系统默认管理员
                                </template>
                                <template v-else>
                                    {{backRoleList[scope.row.roleId]}}
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="创建时间" prop="createTime"
                                align="center">
                        </el-table-column>
                        <el-table-column label="操作" align="center" width="150px;">
                            <template slot-scope="scope" v-if="scope.row.accountType!=1">
                               <!-- <el-link type="danger" @click="upBackUser(scope.row)">编辑</el-link>
                                <el-link type="danger" @click="delBackUser(scope.row.id)">删除</el-link>
-->
                                <el-button
                                        size="mini"
                                        style="width: 59px;height: 24px;padding: 0;"
                                        class="btnColorOrg"
                                        @click="upBackUser(scope.row)">编辑
                                </el-button>
                                <el-button
                                        class="btnColorBlue"
                                        style="width: 59px;height: 24px;padding: 0;"
                                        size="mini"
                                        type="danger"
                                        @click="delBackUser(scope.row)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col class="pageWrap">
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
        <el-dialog
                title="编辑管理员"
                :visible.sync="addDialogVisible"
                width="30%"
                >
            <el-form label-position="right" label-width="80px" :model="backUserInfo" ref="backUserInfo">
                <el-form-item label="登录名">
                    <el-input v-model="backUserInfo.accountName"></el-input>
                </el-form-item>
                <el-form-item label="用户名称">
                    <el-input v-model="backUserInfo.userName"></el-input>
                </el-form-item>
                <el-form-item label="登录密码">
                    <el-input v-model="backUserInfo.passWord" type="password"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="backUserInfo.phoneNo"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="backUserInfo.roleId" placeholder="角色">
                        <el-option v-for="(k,v) in backRoleList" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm">确定</el-button>
                    <el-button @click="resetForm('backUserInfo')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryBackUserList,saveBackUser,delBackUser,upBackUser} from "../../../api/backUserManage/backUserList";
    import {qryBackRoleParams} from "../../../api/backUserManage/backRoleList";
    export default {
        name: "backUserList",
        data() {
            return {
                formInline:{
                    accountName:null,
                    phoneNo:null,
                    roleId:null,
                    pageSize:5,
                    curPage:1
                },
                backUserInfo:{
                },
                addDialogVisible:false,
                pageTotal:0,
                backUserList:[],
                selectList:[],
                backRoleList:[]
            }
        },
        created(){
            this.qryBackRoleParams();
        },
        methods:{
            qryBackUserList(){
                qryBackUserList(this.formInline).then(response=>{
                    this.backUserList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryBackRoleParams(){
                qryBackRoleParams().then(response=>{
                    this.backRoleList = response.data;
                    this.qryBackUserList();
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.qryBackUserList();
            },
            delBackUser(row){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var arr = [];
                    if(row){
                        arr.push(row.id);
                    }else{
                        this.selectList.forEach(function(item,index){
                            arr.push(item.id);
                        })
                    }
                    if(arr.length<1){
                        return;
                    }
                    delBackUser({idList:arr.join(',')}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:undefined==row?'在运营人员管理下进行了管理员批量删除':'在运营人员管理下删除了'+row.accountName+'管理员'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qryBackUserList();
                    });
                }).catch(() => {
                });

            },
            submitForm(){
                if(this.backUserInfo.id){
                    this.backUserInfo.createTime = null;
                    upBackUser(this.backUserInfo).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在运营人员管理下对管理员'+ this.backUserInfo.accountName+'进行了编辑'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.addDialogVisible = false;
                        this.$message.success("编辑成功");
                        this.qryBackUserList();
                    });
                }else {
                    saveBackUser(this.backUserInfo).then(response=>{
                        this.addDialogVisible = false;
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在运营人员管理下新增了管理员'+ this.backUserInfo.accountName
                        };
                        saveBackOperate(par).then();
                        this.$message.success("添加成功");
                        this.qryBackUserList();
                    });
                }
            },
            upBackUser(obj){
                this.backUserInfo = obj;
                this.addDialogVisible = true;
            },
            resetForm(formName) {
                this.addDialogVisible = false;
                this.$refs[formName].resetFields();
            },
            handleSelectionChange(val){
                this.selectList = val;
                console.log(this.selectList);
            }
        }
    }
</script>

<style scoped>

</style>