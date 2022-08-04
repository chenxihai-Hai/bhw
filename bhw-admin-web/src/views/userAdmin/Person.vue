<template>
    <div class="person" style="padding:22px 16px;">
        <div class="bgColorF" style="padding: 38px 25px;">
            <el-form :inline="true" :model="formSearch" class="demo-form-inline">
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formSearch.userId" class="allIpt" style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formSearch.userName" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formSearch.phone" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formSearch.email" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="认证" :label-width="formLabelWidth">
                    <el-select v-model="formSearch.statusId" placeholder="请选择" class="allSelect" style="width: 150px;">
                        <el-option v-for="sItem in statusData" :key="sItem.statusId"
                                   :label="sItem.statusName"
                                   :value="sItem.statusId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户类型" :label-width="formLabelWidth">
                    <el-select v-model="formSearch.userType" placeholder="请选择" class="allSelect" style="width: 150px;">
                        <el-option v-for="tItem in userTypeData" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="formSearch.sex" placeholder="请选择" class="allSelect" style="width: 150px;margin-right: 40px;">
                        <el-option v-for="sItem in sexList" :key="sItem.sexId"
                                       :label="sItem.sexName"
                                       :value="sItem.sexId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnAdvert" @click="btnSearch">查询</el-button>
                </el-form-item>
            </el-form>
            <div style="margin:20px 20px 0;">
                <el-button class="btnNoBgdOrg btnDelMore" style="margin-bottom: 20px;" @click="deleteList">批量删除</el-button>
                <el-table :data="tableData" border style="width: 100%" class="allTable"  @selection-change="handleSelectionChange">
                    <el-table-column
                            type="selection"
                            width="50">
                    </el-table-column>
                    <el-table-column type="index" label="ID" width="50">
                    </el-table-column>
                    <el-table-column
                            prop="userName"
                            label="用户名">
                    </el-table-column>
                    <el-table-column label="用户类型">
                        <template slot-scope="scope">{{scope.row.type==1?'商户用户':'普通用户'}}</template>
                    </el-table-column>
                    <el-table-column label="认证">
                        <template slot-scope="scope">{{scope.row.status==1?'已认证':'未认证'}}</template>
                    </el-table-column>
                    <el-table-column label="头像">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.photo" alt="">
                        </template>
                    </el-table-column>
                    <el-table-column label="性别">
                        <template slot-scope="scope">{{scope.row.sex==1?'男':'女'}}</template>
                    </el-table-column>
                    <el-table-column prop="email" label="邮箱"></el-table-column>
                    <el-table-column prop="phone" label="手机号"></el-table-column>
                    <el-table-column prop="name" label="真实姓名"></el-table-column>
                    <el-table-column label="操作" width="155">
                        <template slot-scope="scope">
                            <el-button class="btnColorOrg btnUser"
                                    @click="handleEdit(scope.$index, scope.row)">编辑
                            </el-button>
                            <el-button
                                    type="primary" class="btnColorBlue btnUser"
                                    @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pageWrap">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page.sync="formSearch.curPage"
                            :page-size="formSearch.pageSize"
                            layout="total,prev, pager, next, jumper"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </div>
            </div>
        </div>
<!--        编辑弹框-->
        <el-dialog title="编辑用户" :visible.sync="dialogEditFormVisible" class="allDialog" width="500px">
            <el-form :model="editForm" class="demo-form">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="editForm.userName" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-input v-model="editForm.phoneNo" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="邮箱号" :label-width="formLabelWidth">
                    <el-input v-model="editForm.emall" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="头像" :label-width="formLabelWidth" class="uploadBox">
                    <el-upload
                            class="avatar-uploader"
                            action="/api/serverManage/fileUpload/upload"
                            :show-file-list="false"
                            :on-success="handleSuccess"
                            :before-upload="beforeUpload">
                        <img v-if="editForm.photo" style="width: 129px; height: 78px"  :src="editForm.photo" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-radio v-model="editForm.sex" label="1">男</el-radio>
                    <el-radio v-model="editForm.sex" label="2">女</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="padding-bottom: 20px">
                <el-button type="primary" class="btnColorOrg btnUser" @click="saveInfo">保存</el-button>
                <el-button class="btnColorBlue btnUser" @click="dialogEditFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
<!--        删除弹框-->
        <el-dialog title="删除用户" :visible.sync="dialogDelFormVisible" class="allDialog" width="500px">
            <div class="delBox">
                <h3>敏感操作，请验证口令</h3>
                <div style="text-align: center;"><el-input placeholder="请输入正确口令" class="iptTxt" style="width: 231px;"></el-input></div>
            </div>
            <div slot="footer" class="dialog-footer" style="text-align: center;padding-bottom: 50px;">
                <el-button type="primary" class="btnColorOrg btnDiaDel" @click="btnDelSure">确定</el-button>
                <el-button class="btnColorBlue btnDiaDel" @click="dialogDelFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../api/admin/index";
    import {getUserList,editUser,deleteUserList} from '../../api/user/userManage'
    export default {
        name: "Person",
        data() {
            return {
                userID:0,pageTotal:0,
                dialogEditFormVisible:false,
                dialogDelFormVisible:false,
                formLabelWidth:'80px',
                statusData:[{statusId:1,statusName:'已认证'},{statusId:2,statusName:'未认证'}],
                userTypeData:[{typeId:1,typeName:'普通用户'},{typeId:2,typeName:'商家用户'}],
                sexList:[{sexId:1,sexName:'男'},{sexId:2,sexName:'女'}],
                formSearch:{userId:'',userName:null,phone:null,email:null,statusId:null,userType:null,sex:null,type:1,curPage: 1, pageSize:10},
                tableData: [],
                editForm:{userName:null,phoneNo:null,emall:null,photo:null,sex:null,id:null},
                fileList: [{url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
                deleteForm:{idList:null,type:1}
            }
        },
        mounted(){
            this.btnSearch();
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.formSearch.curPage = val;
                this.btnSearch();
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.formSearch.curPage = val;
                this.btnSearch();
            },
            handleEdit(index, row) {
                this.editForm=row;
                this.editForm.phoneNo = row.phone;
                this.dialogEditFormVisible = true;
            },
            handleDelete(index, row) {
                this.deleteForm.idList=row.id;
                this.$confirm('确认删除该信息', '确认', {
                    confirmButtonText: '确认',
                    cancelButtonText: '关闭',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    deleteUserList(this.deleteForm).then(response => {
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在用户管理下删除了个人用户'+row.userName
                        };
                        saveBackOperate(par).then();
                        if (response.data) {
                            this.$message.info("删除成功")
                        }else{
                            this.$message.error("删除失败")
                        }
                        this.btnSearch();
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            beforeUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handlePreview(file) {
                console.log(file);
            },
            handleSuccess(res){
                this.editForm.photo = res.data;
                if(this.editForm.photo.indexOf("-zip")>-1){
                    this.editForm.photo = this.editForm.photo.replace("-zip","");
                }
            },
            handleSelectionChange(val) {
               if (val.length==1)  {
                   this.deleteForm.idList=val[0].id;
               }else {
                   this.deleteForm.idList=val[0].id;
                   for (var i=1;i<val.length;i++){
                       this.deleteForm.idList+=","+val[i].id;
                   }
               }
            },
            deleteList(rows){
                // deleteUserList(this.deleteForm).then(response => {
                //     if (response.data) {
                //         this.$message.info("删除成功")
                //     }else{
                //         this.$message.error("删除失败")
                //     }
                // })
                this.$confirm('确认删除该信息', '确认', {
                    confirmButtonText: '确认',
                    cancelButtonText: '关闭',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    deleteUserList(this.deleteForm).then(response => {
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在用户管理下批量删除了个人用户'
                        };
                        saveBackOperate(par).then();
                        if (response.data) {
                            this.$message.info("删除成功")
                        }else{
                            this.$message.error("删除失败")
                        }
                        this.btnSearch();
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            btnSearch(){
                getUserList(this.formSearch).then(response => {
                    this.tableData=response.data;
                    this.pageTotal=response.page.counts;
                })
            },
            btnDelSure(){
                //alert(JSON.stringify(this.deleteForm))
                deleteUserList(this.deleteForm).then(response => {
                    if (response.data) {
                        this.$message.info("删除成功")
                    }else{
                        this.$message.error("删除失败")
                    }
                    this.btnSearch();
                })
            },
            saveInfo(){
                if (this.editForm.userName==null) {
                    this.$message.error("请输入用户名")
                }else if (this.editForm.phoneNo==null){
                    this.$message.error("请输入手机号")
                } else if (this.editForm.emall==null){
                    this.$message.error("请输入邮箱")
                }else if (this.editForm.sex==null){
                    this.$message.error("请选择性别")
                }else {
                    editUser(this.editForm).then(response => {
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在用户管理下对个人用户'+this.editForm.userName+'进行了编辑'
                        };
                        saveBackOperate(par).then();
                        if (response.data) {
                            this.$message.info("编辑成功")
                        }else{
                            this.$message.error("编辑失败")
                        }
                        this.btnSearch();
                        this.dialogEditFormVisible = true;
                    })
                }

            }
        }
    }
</script>

<style scoped>
    .person /deep/ .el-input.allIpt{width: 150px;}
    .person /deep/ .el-form-item{margin-bottom: 15px;}
    .btnNoBgdOrg.btnDelMore{width: 100px;height: 34px;padding: 0;}
    .btnColorBlue.btnUser,.btnColorOrg.btnUser{font-size: 12px;width: 60px;height: 24px;padding: 0;}
    .btnColorOrg.btnDiaDel,.btnColorBlue.btnDiaDel{width: 70px;height: 27px;padding: 0;border: none;}
    /deep/ .allTable.el-table td, /deep/ .allTable.el-table th{text-align: center;}
    .iptTxt .el-input__inner{text-align: center;}
    .delBox h3{color: #D90000;font-size: 18px;text-align: center;margin-bottom: 30px;}
    /deep/ .el-upload-list--picture .el-upload-list__item{width: 100px;display: inline-block;margin-right: 50px;}
    .demo-form /deep/ .el-form-item{margin-bottom: 10px;}
</style>