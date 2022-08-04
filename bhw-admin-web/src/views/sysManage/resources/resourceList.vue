<template>
    <div class="resourceWrap" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formSearch" class="demo-form-inline" style="margin: 33px 40px 0;text-align: left;">
                <el-form-item label="名称">
                    <el-input placeholder="请输入" v-model="formSearch.fullName" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <el-select v-model="formSearch.type" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <el-option v-for="tItem in schoolType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="法人">
                    <el-input placeholder="请输入" v-model="formSearch.legalPerson" class="allIpt" style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="成立时间">
                    <el-select v-model="formSearch.date" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <el-option v-for="tItem in schoolType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="注册资本" style="margin-right: 35px;">
                    <el-select v-model="formSearch.registeredCapital" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <el-option v-for="tItem in schoolType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnSchool" @click="btnSearch">查询</el-button>
                    <el-button type="primary" class="btnColorBlue btnSchool" @click="dialogFormVisible=true">+ 添加</el-button>
                </el-form-item>
            </el-form>
            <h3>数据列表</h3>
            <div class="tabBox" style="padding: 20px 0;">
                <el-table
                        :data="dataList"
                        style="width: 100%" class="allTable">
                    <el-table-column
                            label="删除"
                            type="selection"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            type="index" label="编号"
                            :index="indexMethod" width="55">
                    </el-table-column>
                    <el-table-column label="logo" width="150px">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.img">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="fullName"
                            label="名称">
                    </el-table-column>
                    <el-table-column
                            prop="type"
                            label="类型" width="80px">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="成立时间">
                    </el-table-column>
                    <el-table-column
                            prop="industey"
                            label="所属行业领域">
                    </el-table-column>
                    <el-table-column prop="address" label="企业地址" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column
                            prop="profile"
                            label="企业简介" :show-overflow-tooltip="true">
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text"
                                       @click="handleEdit( scope.row)">编辑</el-button>
                            <el-button type="text"
                                       @click="handleDelete(scope.row)" style="color: #CA0000;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pageWrap">
                <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page.sync="formSearch.currentPage"
                        :page-size="formSearch.pageSize"
                        layout="total,prev, pager, next, jumper"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
<!--        添加/编辑 弹框-->
        <el-dialog title="添加数据" :visible.sync="dialogFormVisible" class="allDialog" width="547px">
            <el-form :model="form" class="formBox">
                <el-form-item label="企业名称：" :label-width="formLabelWidth">
                    <el-input v-model="form.fullName" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="简称：" :label-width="formLabelWidth">
                    <el-input v-model="form.shortName" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="法定代表人：" :label-width="formLabelWidth">
                    <el-input v-model="form.legalPerson" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="成立时间：" :label-width="formLabelWidth">
                    <!--<el-input v-model="form.createTime" autocomplete="off" style="width: 250px;"></el-input>-->
                    <el-date-picker
                            value-format="yyyy-MM-dd"
                            v-model="form.createTime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="类型：" :label-width="formLabelWidth">
                    <el-select v-model="form.type" placeholder="请选择" class="allSelect" style="width: 250px;">
                        <el-option v-for="tItem in schoolType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属行业领域：" :label-width="formLabelWidth">
                    <el-input v-model="form.industey" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="注册资本：" :label-width="formLabelWidth">
                    <el-input v-model="form.registeredCapital" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="所属城市：" :label-width="formLabelWidth">
                    <el-select v-model="form.provinceId" @change="qryCityList" placeholder="省份" style="width: 120px;margin-right: 10px;">
                        <el-option
                                v-for="item in provinceList"
                                :key="item.provinceName"
                                :label="item.provinceName"
                                :value="item.provinceId">
                        </el-option>
                    </el-select>
                    <el-select v-model="form.cityId" placeholder="城市" style="width: 120px;">
                        <el-option
                                v-for="item in cityList"
                                :key="item.cityName"
                                :label="item.cityName"
                                :value="item.cityId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="企业详细地址：" :label-width="formLabelWidth">
                    <el-input v-model="form.address" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="logo：" :label-width="formLabelWidth" class="uploadBox">
                    <el-upload
                            class="upload-demo"
                            action="/api/serverManage/fileUpload/upload"
                            :on-preview="handlePreview"
                            :before-upload="beforeUpload"
                            :on-success="handleSuccess"
                            :show-file-list="false"
                            list-type="picture">
                        <el-button size="small" type="primary" class="btnColorOrg">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                    <el-image v-if="form.img"
                              style="width: 100px; height: 100px"
                              :src="form.img"
                              :preview-src-list="[form.img]">
                    </el-image>
                </el-form-item>
                <el-form-item>
                    <h4>企业简介</h4>
                    <quill-editor v-model="form.profile" ref="myQuillEditor" style="height: 200px;" :options="editorOption">
                    </quill-editor>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center;margin-top: 120px;">
                <el-button type="primary" class="btnColorOrg" @click="subInfo">保存</el-button>
                <el-button class="btnColorBlue" @click="dialogFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {getResourceAreaRange,saveResourceArea,upResourceArea,delResourceArea} from "../../../api/school/school";
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    export default {
        name: "resourceList",
        components: {
            quillEditor
        },
        data(){
            return{
                formLabelWidth:'120px',
                dialogFormVisible:false,
                pageTotal:0,
                formSearch: {fullName:null,type:null,legalPerson:null,registeredCapital:null,createTime:null,curPage: 1, pageSize:10},
                schoolType:[{typeId:1,typeName:'企业'},{typeId:2,typeName:'机构'}], //类型
                fieldList:{},//行业领域
                provinceList:{},//省份
                cityList:{},//城市
                dataList:[{id:1,imgUrl:'', name:'深圳市对接平台发展有限公司',setUpDate:'2015年3月16日',field:'通信技术',address:'广东省深圳市南山区粤兴三道', intraduction:'企业的简介'}],
                form:{name:'',shortName:'',legal:'',typeId:1,provinceId:'',cityId:'',fId:'',capital:'',address:'',img:'', details:''},//添加/编辑表单
                fileList: [{url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],//上传logo
                editorOption:{} //简介
            }
        },
        created() {
            this.qryResourceList();
            this.qryProvinceList();
        },
        methods:{
            indexMethod(index) {
                return index + 1;
            },
            handleCurrentChange(val) {
                this.formSearch.curPage = val;
                this.qryResourceList();
            },
            handleEdit( row) {
                this.form = row;
                this.dialogFormVisible = true;
            },
            handleDelete(row) {
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var arr=[];
                    arr.push(row.id);
                    delResourceArea({idList:arr.join(',')}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在资源聚集区下对企业'+ row.fullName+'进行了删除'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qryResourceList();
                    });
                });

            },
            subInfo(){
                if(this.form.id){
                    this.form.createDate = null;
                    upResourceArea(this.form).then(response=>{
                        this.$message.success("修改成功");
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在资源聚集区下对企业'+ this.form.fullName+'进行了编辑'
                        };
                        saveBackOperate(par).then();
                        this.dialogFormVisible = false;
                        this.qryResourceList();
                    });
                }else{
                    saveResourceArea(this.form).then(response=>{
                        this.$message.success("保存成功");
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在资源聚集区下发布了企业'+ this.form.fullName
                        };
                        saveBackOperate(par).then();
                        this.dialogFormVisible = false;
                        this.qryResourceList();
                    });
                }
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
            },
            handleSuccess(res){
                this.form.img = res.data;
                if(this.form.img.indexOf("-zip")>-1){
                    this.form.img = this.form.img.replace("-zip","");
                }
            },
            btnSearch(){
              this.qryResourceList();
            },
            qryResourceList(){
                getResourceAreaRange(this.formSearch).then(response=>{
                    this.dataList = response.data.dataList;
                    this.pageTotal = response.page.counts;
                });
            },
            qryProvinceList(){
                qryProvinceList().then(response => {
                    this.provinceList = response.data;
                });
            },
            qryCityList(val){
                qryCityList({"provinceId":val}).then(response => {
                    this.cityList = response.data;
                });
            }
        }
    }
</script>

<style scoped>
    .allDialog /deep/ .el-dialog{height: 773px;overflow: scroll;}
    /deep/ .formBox.el-form .el-form-item{margin-bottom: 10px;}
    /deep/ .el-form--inline .el-form-item{margin-right: 30px;}
    /deep/ .el-upload-list--picture .el-upload-list__item{width: 100px;display: inline-block;margin-right: 50px;}
    .allDialog h4{color: #1D1D1D;font-size: 16px;font-weight: 400;margin-bottom: 20px;}
    .resourceWrap h3{color: #000;font-size: 18px;font-weight: 400; padding-top: 34px;}
    .allIpt /deep/ .el-input__inner,.allSelect /deep/ .el-input__inner{height: 36px;line-height: 36px;}
    .btnSchool{width: 84px;height: 36px;padding: 0;border: none;}
    @media screen and (max-height: 900px) {
        .allDialog /deep/ .el-dialog{height: 600px;}
    }
</style>