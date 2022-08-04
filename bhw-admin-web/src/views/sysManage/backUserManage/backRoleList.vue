<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>角色权限</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="角色">
                    <el-select v-model="formInline.id" placeholder="角色">
                        <el-option v-for="(k,v) in backRoleParList" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnQueryList" @click="qryBackRoleList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-bottom: 20px;">
                <el-col>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addDialogVisible=true">添加</el-button>
                    <el-button type="primary" class="btnColorOrg btnQueryList" @click="delBackRole(undefined)">删除</el-button>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-table  class="allTable"  :data="backRoleList"  @selection-change="handleSelectionChange">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                label="ID" type="index"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="角色名" prop="roleName"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="具体描述" prop="roleDesc"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="创建时间" prop="createTime"
                                align="center">
                        </el-table-column>
                        <el-table-column label="操作" align="center" width="220px">
                            <template slot-scope="scope">
                                <!--<el-link type="danger" @click="upBackRole(scope.row)">编辑</el-link>
                                <el-link type="danger" @click="upRoleMenu(scope.row)">权限分配</el-link>
                                <el-link type="danger" @click="delBackRole(scope.row.id)">删除</el-link>-->

                                <el-button
                                        size="mini"
                                        style="width: 59px;height: 24px;padding: 0;"
                                        class="btnColorOrg"
                                        @click="upBackRole(scope.row)">编辑
                                </el-button>
                                <el-button
                                        class="btnColorBlue"
                                        style="width: 59px;height: 24px;padding: 0;"
                                        size="mini"
                                        type="danger"
                                        @click="delBackRole(scope.row)">删除
                                </el-button>
                                <el-button
                                        size="mini"
                                        style="width: 59px;height: 24px;padding: 0;"
                                        class="btnColorOrg"
                                        @click="upRoleMenu(scope.row)">权限分配
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
                            :total="pageTotal"  class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
        <el-dialog
                title="编辑角色信息"
                :visible.sync="addDialogVisible"
                width="30%"
                >
            <el-form label-position="right" label-width="80px" :model="backRoleInfo" ref="backRoleInfo">
                <el-form-item label="角色名">
                    <el-input v-model="backRoleInfo.roleName"></el-input>
                </el-form-item>
                <el-form-item label="角色描述">
                    <el-input v-model="backRoleInfo.roleDesc"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm">确定</el-button>
                    <el-button @click="resetForm('backRoleInfo')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog
                title="编辑角色权限"
                :visible.sync="addRoleMenuDialogVisible"
                width="50%"
        >
            <el-form label-position="right" label-width="80px" :model="backRoleMenuInfo" ref="backRoleMenuInfo">
                <el-form-item label="权限范围">
                    <el-tree
                            :data="backMenusList"
                            show-checkbox
                            node-key="id"
                            ref="menuTree"
                            :default-checked-keys="backRoleMenuList"
                           >
                    </el-tree>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitRoleMenuForm">确定</el-button>
                    <el-button @click="resetForm('backRoleMenuInfo')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryBackRoleParams,qryBackRoleList,saveBackRole,delBackRole,upBackRole,saveRoleMenus} from "../../../api/backUserManage/backRoleList";
    import {getBackMenu,getBackUserMenu} from "../../../api/login";
    export default {
        name: "backRoleList",
        data() {
            return {
                formInline:{
                    id:null,
                    pageSize:5,
                    curPage:1
                },
                backRoleInfo:{
                    roleName:null,
                    roleDesc:null
                },
                backRoleMenuInfo:{
                    roleId:''
                },
                addRoleMenuDialogVisible:false,
                addDialogVisible:false,
                pageTotal:0,
                selectList:[],
                backRoleList:[],
                backRoleParList:[],
                backMenusList:[],
                backRoleMenuList:[]
            }
        },
        created(){
            this.qryBackRoleParams();
            this.qryBackRoleList();
        },
        methods:{
            qryBackRoleList(){
                qryBackRoleList(this.formInline).then(response=>{
                    this.backRoleList = response.data;
                });
            },
            qryBackRoleParams(){
                qryBackRoleParams().then(response=>{
                    this.backRoleParList = response.data;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.qryBackRoleList();
            },
            delBackRole(row){
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
                    delBackRole({idList:arr.join(',')}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:undefined==row?'在运营人员管理下进行了批量删除角色':'在运营人员管理下删除了'+row.roleName+'角色'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qryBackRoleList();
                    });
                }).catch(() => {
                });

            },
            submitForm(){
                if(this.backRoleInfo.id){
                    upBackRole(this.backRoleInfo).then(response=>{
                        this.addDialogVisible = false;
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在运营人员管理下对角色为'+ this.backRoleInfo.roleName+'进行了编辑'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("编辑成功");
                        this.qryBackRoleList();
                    });
                }else {
                    saveBackRole(this.backRoleInfo).then(response=>{
                        this.addDialogVisible = false;
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在运营人员管理下新增了角色为'+ this.backRoleInfo.roleName
                        };
                        saveBackOperate(par).then();
                        this.$message.success("添加成功");
                        this.qryBackRoleList();
                    });
                }
            },
            upRoleMenu(obj){
                this.backRoleMenuInfo.roleId=obj.id;
                getBackMenu().then(response=>{
                    this.addRoleMenuDialogVisible = true;
                    this.backMenusList = response.data;
                    getBackUserMenu({roleId:obj.id}).then(resp=>{
                        resp.data.forEach(item=>{
                            if(item.type!='false'){
                                this.backRoleMenuList.push(item.id);
                            }
                            item.children.forEach(it=> {
                                if (it.type != 'false') {
                                    this.backRoleMenuList.push(it.id);
                                }
                                it.children.forEach(i=> {
                                    if (i.type != 'false') {
                                        this.backRoleMenuList.push(i.id);
                                    }
                                });
                            });
                        })
                        this.$refs.menuTree.setCheckedKeys(this.backRoleMenuList);
                    });
                });
            },
            submitRoleMenuForm(){
                saveRoleMenus({idList:this.$refs.menuTree.getCheckedKeys().join(","),
                    roleId:this.backRoleMenuInfo.roleId}).then(response=>{
                    this.addRoleMenuDialogVisible = false;
                    this.$message.success("编辑成功");
                    this.qryBackRoleList();
                });
            },
            upBackRole(obj){
                this.backRoleInfo = obj;
                this.addDialogVisible = true;
            },
            resetForm(formName) {
                this.addDialogVisible = false;
                this.addRoleMenuDialogVisible =false;
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