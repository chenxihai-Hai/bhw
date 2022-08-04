<template>
    <el-dialog title="经纪人"
            :visible.sync="borkerDialogVisible"
            width="60%" class="serviceDialog">
        <div class="manLoginIndex" >
            <el-row>
                <el-col class="shangchuan">
                    <div class="touxiang"><span>头像:</span></div>
                    <el-upload
                            class="avatar-uploader"
                            action="/api/serverManage/fileUpload/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                        <img v-if="manInfo.photo" :src="manInfo.photo" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-col>
            </el-row>
            <el-form label-width="120px" label-position="right" :model="manInfo" class="biaodan">
                <el-form-item label="手机号：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.phone">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="真实姓名：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.name">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="从事行业领域：" prop="name">
                    <div class="xinxi">
                        <el-select v-model="manInfo.borkerId" placeholder="请选择">
                            <el-option
                                    v-for="item in borkerType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </div>
                </el-form-item>
                <el-form-item label="主要成就：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.mainAchieve">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="性别：" prop="name">
                    <div class="xinxi">
                        <el-radio v-model="manInfo.sex" :label="1">男</el-radio>
                        <el-radio v-model="manInfo.sex" :label="2">女</el-radio>
                    </div>
                </el-form-item>
                <el-form-item label="邮箱：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.email">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="从业工龄：" prop="name">
                    <div class="xinxi">
                        <el-input placeholder="必填" v-model="manInfo.seniority">
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item style="margin-top: 60px;">
                    <el-button type="primary" class="btnColorOrg" @click="onSubmit">上传发布</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-dialog>
</template>

<script>
    import {brokerRegister,upBrokerAdvisory} from "../../../api/infoManage/borker";
    import {saveBackOperate} from "../../../api/admin/index";
    export default {
        name: "addBorker",
        data() {
            return {
                borkerDialogVisible:false,
                radio: 1,
                borkerType:[{id:1,name:'知识产权'},{id:2,name:'财税服务'},{id:3,name:'科技咨询'},{id:4,name:'科技金融'},{id:5,name:'法律服务'}
                ,{id:6,name:'技术合同登记'},{id:7,name:'成果认定'},{id:8,name:'商务代写'},{id:9,name:'策划设计'},{id:10,name:'技术开发'},
                    {id:11,name:'教育培训'},{id:12,name:'投资孵化'}],
                manInfo:{
                    photo:'',
                    phone:'',
                    name:'',
                    sex:'',
                    email:'',
                    seniority:'',
                    mainAchieve:'',
                    borkerId:''
                }
            };
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.manInfo.photo = res.data;
                if(this.manInfo.photo.indexOf("-zip")>-1){
                    this.manInfo.photo = this.manInfo.photo.replace("-zip","");
                }
            },
            beforeAvatarUpload(file) {
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
            cleanData(){
                this.manInfo={
                    photo:'',
                        phone:'',
                        name:'',
                        sex:'',
                        email:'',
                        seniority:'',
                        mainAchieve:'',
                        borkerId:''
                }
            },
            onSubmit(){
                if(this.manInfo.id){
                    this.manInfo.createTime = null;
                    this.manInfo.createTime = undefined;
                    upBrokerAdvisory(this.manInfo).then(response=>{
                        this.$message({
                            message: '保存成功',
                            type: 'success'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'修改了'+this.manInfo.name+'经纪人的信息'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.borkerDialogVisible = false;
                        this.$parent.backQryBrokerList();
                    });
                }else{
                    brokerRegister(this.manInfo).then(response=>{
                        this.$message({
                            message: '保存成功',
                            type: 'success'
                        });
                        this.cleanData();
                        this.borkerDialogVisible = false;
                        this.$parent.backQryBrokerList();
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'新增了'+this.manInfo.name+'经纪人'
                        };
                        saveBackOperate(par).then();
                    });
                }
            }
        }
    }
</script>

<style scoped>
    .avatar-uploader /deep/ .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader /deep/ .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 158px;
        line-height: 158px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 158px;
        display: block;
    }
    .shangchuan {
        margin: 0px auto;
        height: 170px;
        border: 1px solid rgba(204, 204, 204, 1);
    }

    .touxiang {
        width: 230px;
        height: 168px;
        background: #F8F8F8;
        float: left;
        text-align: right;
        padding-top: 78px;
    }

    .upload-demo {
        width: 400px;
        margin: 67px auto
    }

   /* .biaodan {
        margin: 50px auto;
        width: 90%;
        background: #F8F8F8;
        line-height: 0px;
    }

    .xinxi {
        float: left;
        width: 750px
    }*/

</style>