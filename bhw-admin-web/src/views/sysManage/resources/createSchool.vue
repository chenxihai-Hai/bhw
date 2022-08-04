<template>
    <div class="schoolWrap" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 10px 25px;">
            <h2>高校信息</h2>
            <h3>一、基本信息（展示页）</h3>
            <div style="margin-bottom: 30px;">
                <el-form :model="formSchool" class="demo-form">
                    <el-form-item label="高校名称" :label-width="formLabelWidth">
                        <el-input placeholder="请输入" v-model="formSchool.name" class="allIpt" style="width: 300px"></el-input>
                    </el-form-item>
                    <el-form-item label="定位" :label-width="formLabelWidth">
                        <el-select v-model="formSchool.provinceId"  @change="qryCityList" placeholder="省份" style="width: 145px;margin-right: 10px;">
                            <el-option
                                    v-for="item in provinceList"
                                    :key="item.provinceName"
                                    :label="item.provinceName"
                                    :value="item.provinceId">
                            </el-option>
                        </el-select>
                        <el-select v-model="formSchool.cityId" placeholder="城市" style="width: 145px;">
                            <el-option
                                    v-for="item in cityList"
                                    :key="item.cityName"
                                    :label="item.cityName"
                                    :value="item.cityId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="国内外选择" :label-width="formLabelWidth">
                        <el-radio v-model="formSchool.type" :label="1">国内高校</el-radio>
                        <el-radio v-model="formSchool.type" :label="0">国外高校</el-radio>
                    </el-form-item>
                    <el-form-item label="logo" :label-width="formLabelWidth">
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
                        <el-image v-if="formSchool.photo"
                                  style="width: 100px; height: 100px"
                                  :src="formSchool.photo"
                                  :preview-src-list="[formSchool.photo]">
                        </el-image>
                    </el-form-item>
                </el-form>
            </div>
            <h3>二、高校详情页信息</h3>
            <div>
                <h4>1、学校概况编辑:</h4>
                <el-form :model="formSchool" class="demo-form">
                    <el-form-item label="高校简介" :label-width="formLabelWidth">
                        <quill-editor v-model="formSchool.introduce" ref="myQuillEditor" style="margin-bottom: 100px;height: 200px;width: 80%;" :options="editorOption">
                        </quill-editor>
                    </el-form-item>
                    <el-form-item label="高校背景图" :label-width="formLabelWidth">
                        <el-upload
                                class="upload-demo"
                                action="/api/serverManage/fileUpload/upload"
                                :on-preview="handlePreview"
                                :before-upload="beforeUpload"
                                :on-success="handleBgSuccess"
                                :show-file-list="false"
                                list-type="picture">
                            <el-button size="small" type="primary" class="btnColorOrg">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过25M</div>
                        </el-upload>
                        <el-image v-if="formSchool.introduceImg"
                                  style="width: 100px; height: 100px"
                                  :src="formSchool.introduceImg"
                                  :preview-src-list="[formSchool.introduceImg]">
                        </el-image>
                    </el-form-item>
                </el-form>
                <h4>2、研究成果编辑：</h4>
                <template v-for="(formResult,index) in formList">
                    <div class="resultData" :key="index">
                        <el-form :model="formResult" class="demo-form">
                            <el-form-item label="成果名称" :label-width="formLabelWidth">
                                <el-input placeholder="请输入" v-model="formResult.patentname" class="allIpt" style="width: 300px"></el-input>
                            </el-form-item>
                            <el-form-item label="专利号" :label-width="formLabelWidth">
                                <el-input placeholder="请输入" v-model="formResult.patentcode" class="allIpt" style="width: 300px"></el-input>
                            </el-form-item>
                            <el-form-item label="申请日" :label-width="formLabelWidth">
                                <el-input placeholder="请输入" v-model="formResult.shenqingdate" class="allIpt" style="width: 300px"></el-input>
                            </el-form-item>
                            <el-form-item label="联系人" :label-width="formLabelWidth">
                                <el-input placeholder="请输入" v-model="formResult.dailiren" class="allIpt" style="width: 300px"></el-input>
                            </el-form-item>
                            <el-form-item label="联系人电话" :label-width="formLabelWidth">
                                <el-input placeholder="请输入" v-model="formResult.dailiJigou" class="allIpt" style="width: 300px"></el-input>
                            </el-form-item>
                            <el-form-item label="成果图片" :label-width="formLabelWidth">
                                <el-upload
                                        class="upload-demo"
                                        action="/api/serverManage/fileUpload/upload"
                                        :on-preview="handlePreview"
                                        :on-remove="handleRemove"
                                        :on-success="handleMoreSuccess"
                                        :show-file-list="false"
                                        list-type="picture">
                                    <el-button size="small" class="btnColorOrg btnClickUp" @click="recordUp(index)" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                                </el-upload>
                                <el-image v-if="formResult.patentimg"
                                          style="width: 100px; height: 100px"
                                          :src="formResult.patentimg"
                                          :preview-src-list="[formResult.patentimg]">
                                </el-image>
                            </el-form-item>
                            <el-form-item label="成果详情" :label-width="formLabelWidth">
                                <quill-editor v-model="formResult.content" ref="myQuillEditor" style="height: 200px;width:80%;margin-bottom: 100px;" :options="editorOption">
                                </quill-editor>
                            </el-form-item>
                        </el-form>
                    </div>
                </template>
                <div style="text-align: center;margin-bottom: 30px" ><el-button type="text" class="btnMore" @click="btnAddMore">＋添加更多成果</el-button></div>
                <h4>3、相关院士编辑：</h4>
                <el-form :model="formSchool" class="demo-form">
                    <el-form-item label="院士名称" :label-width="formLabelWidth">
                        <el-input placeholder="请输入" v-model="formSchool.academician" class="allIpt" style="width: 300px"></el-input>
                    </el-form-item>
                    <el-form-item label="英文名称" :label-width="formLabelWidth">
                        <el-input placeholder="请输入" v-model="formSchool.enName" class="allIpt" style="width: 300px"></el-input>
                    </el-form-item>
                    <el-form-item label="在校职称" :label-width="formLabelWidth">
                        <el-input placeholder="请输入" v-model="formSchool.academicianDuty" class="allIpt" style="width: 300px"></el-input>
                    </el-form-item>
                    <el-form-item label="院士头像" :label-width="formLabelWidth">
                        <el-upload
                                class="upload-demo"
                                action="/api/serverManage/fileUpload/upload"
                                :on-preview="handlePreview"
                                :before-upload="beforeUpload"
                                :on-success="handleYsSuccess"
                                :show-file-list="false"
                                list-type="picture">
                            <el-button size="small" type="primary" class="btnColorOrg">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                        </el-upload>
                        <el-image v-if="formSchool.academicianImg"
                                  style="width: 100px; height: 100px"
                                  :src="formSchool.academicianImg"
                                  :preview-src-list="[formSchool.academicianImg]">
                        </el-image>
                    </el-form-item>
                    <el-form-item label="简介" :label-width="formLabelWidth">
                        <quill-editor v-model="formSchool.academicianRemark" ref="myQuillEditor" style="margin-bottom:120px;height: 200px;width: 80%;" :options="editorOption">
                        </quill-editor>
                    </el-form-item>
                </el-form>
            </div>
            <div style="text-align: center">
                <el-button type="primary" class="btnColorOrg" @click="subInfo">保存</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    import {saveSchoolInfo,savePatentBean,getPatentList,upSchoolInfo,upPatentBean} from "../../../api/school/school";
    export default {
        name: "createSchool",
        components: {
            quillEditor
        },
        data(){
            return{
                formLabelWidth:'150px',
                formList:[{schoolid:null,patentimg:null,patentname:null,patentcode:null,shenqingdate:null,dailiren:null,dailiJigou:null,content:null,moreFile:[]}],
                provinceList:{},
                cityList:{},
                formSchool:{photo:'',introduceImg:'',academicianImg:'',academicianRemark:'',cityId:null,
                    name:null,type:null,introduce:null,academician:null,enName:null,academicianDuty:null},
                // formResult:{},
                fileList: [],
                editorOption:{},//高校简介
                moreFile:[],
                lastIndex:0
            }
        },
        mounted(){
            this.qryProvinceList();
            if(this.$route.query.schoolDet){
                this.formSchool = this.$route.query.schoolDet;
                this.formSchool.cityId= this.$route.query.schoolDet.collegeCity;
                this.formSchool.provinceId= this.$route.query.schoolDet.collegeProvince;
                this.formSchool.createTime = null;
                getPatentList({schoolid:this.formSchool.id,pageSize:10000}).then(response=>{
                    this.formList = response.data;
                    /*for(var i=0;i<this.formList.length;i++){
                        var patentimg = this.formList[i].patentimg;
                        this.formList[i].fileList = [];
                        this.formList[i].moreFile = [];
                        if(null != patentimg && '' != patentimg){
                            var pimgs = patentimg.split(";");
                            for(var j=0;j<pimgs.length;j++){
                                if(pimgs[j]){
                                    this.formList[i].fileList.push({url:pimgs[j]});
                                }
                            }
                        }
                    }*/
                });
            }
        },
        methods:{
            recordUp(index){
                this.lastIndex = index;
            },
            subInfo(){
                if(this.formSchool.id){
                    this.formSchool.createTime = undefined;
                    if(!Number.isInteger(this.formSchool.cityId)){
                        this.formSchool.cityId = undefined;
                        this.formSchool.provinceId  = undefined;
                    }
                    upSchoolInfo(this.formSchool).then(response=>{
                        var item = null;
                        var promList = [];
                        console.log('this.formList',this.formList);
                        for (var i = 0;i<this.formList.length;i++) {
                            item = this.formList[i];
                            item.patentimg = null == item.patentimg?'':item.patentimg;
                            item.createTime = undefined;
                           /* for (var p in item.moreFile) {
                                if (item.moreFile[p] != '' && null != item.moreFile[p]) {
                                    var ig = item.moreFile[p];
                                    if (null == ig || '' == ig) {
                                        continue;
                                    }
                                    if (ig.indexOf("-zip") > -1) {
                                        ig = ig.replace('-zip', '');
                                    }
                                    item.patentimg = item.patentimg + ig + ";";
                                }
                            }*/
                            item.schoolid = this.formSchool.id;
                            /*if(item.patentimg.length>0&&item.patentimg.indexOf(";")>0){
                                item.patentimg = item.patentimg.substring(0,item.patentimg.length-1);
                            }*/
                            if (null == item.patentname || item.patentname.trim() == '') {
                                continue;
                            }
                            if (null == item.patentcode || item.patentcode.trim() == '') {
                                continue;
                            }
                            if(item.id){
                                var par = new Promise((resolve, reject) => {
                                    item.createTime = undefined;
                                    upPatentBean(item).then(response => {
                                    });
                                })
                                promList.push(par);
                            }else{
                                var par = new Promise((resolve, reject) => {
                                    savePatentBean(item).then(response => {
                                    });
                                });
                                promList.push(par);
                            }
                        }
                        Promise.all(promList).then(response => {
                        })
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在资源聚集区下编辑了'+ this.formSchool.name
                        };
                        saveBackOperate(par).then();
                        this.$message.success("保存成功");
                        this.$router.push({path:'/schoolList'})
                    });
                }else{
                    saveSchoolInfo(this.formSchool).then(response=>{
                        if(response.data){
                            var item = null;
                            var promList = [];
                            for (var i = 0;i<this.formList.length;i++) {
                                item = this.formList[i];
                                item.patentimg = null == item.patentimg?'':item.patentimg;
                                /*for (var p in item.moreFile) {
                                    if (item.moreFile[p] != '' && null != item.moreFile[p]) {
                                        var ig = item.moreFile[p];
                                        if (null == ig || '' == ig) {
                                            continue;
                                        }
                                        if (ig.indexOf("-zip") > -1) {
                                            ig = ig.replace('-zip', '');
                                        }
                                        item.patentimg = item.patentimg + ig + ";";
                                    }
                                }*/
                                item.schoolid = response.data;
                                /*if(item.patentimg.length>0){
                                    item.patentimg = item.patentimg.substring(0,item.patentimg.length-1);
                                }*/
                                if (null == item.patentname || item.patentname.trim() == '') {
                                    continue;
                                }
                                if (null == item.patentcode || item.patentcode.trim() == '') {
                                    continue;
                                }
                                var par = new Promise((resolve, reject) => {
                                    savePatentBean(item).then(response => {
                                    });
                                });
                                promList.push(par);
                            }
                            Promise.all(promList).then(response => {
                            })
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:'在资源聚集区下发布了'+ this.formSchool.name
                            };
                            saveBackOperate(par).then();
                            this.$message.success("保存成功");
                            this.$router.push({path:'/schoolList'})
                        }
                    });
                }

            },
            beforeUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 25;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 25MB!');
                }
                return isJPG && isLt2M;
            },
            handlePreview(file) {
            },
            handleSuccess(res){
                this.formSchool.photo = res.data;
                console.log('this.formSchool.photo',this.formSchool.photo);
                if(this.formSchool.photo.indexOf("-zip")>-1){
                    this.formSchool.photo = this.formSchool.photo.replace("-zip","");
                }
            },
            handleBgSuccess(res){
                this.formSchool.introduceImg = res.data;
                if(this.formSchool.introduceImg.indexOf("-zip")>-1){
                    this.formSchool.introduceImg = this.formSchool.introduceImg.replace("-zip","");
                }
            },
            handleYsSuccess(res){
                this.formSchool.academicianImg = res.data;
                if(this.formSchool.academicianImg.indexOf("-zip")>-1){
                    this.formSchool.academicianImg = this.formSchool.academicianImg.replace("-zip","");
                }
            },
            handleRemove(file, fileList) {
                this.formList[this.lastIndex].moreFile[file.url] = '';
            },
            handleMoreSuccess(res, file){
                // this.formList[this.lastIndex].moreFile[file.url] = res.data;
                var url = res.data;
                this.formList[this.lastIndex].patentimg = res.data;
                if(url.indexOf("-zip")>-1){
                    this.formList[this.lastIndex].patentimg = url.replace("-zip","");
                }
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
            },
            btnAddMore(){
                this.formList.push({schoolid:null,patentimg:null,patentname:null,patentcode:null,shenqingdate:null,dailiren:null,dailiJigou:null,content:null,moreFile:[]});
            }
        }
    }
</script>

<style scoped>
    .schoolWrap h2{color: #1D1D1D;font-size: 20px;text-align: center;margin-bottom: 30px;}
    .schoolWrap h3{color: #1D1D1D;font-size: 16px;font-weight: 400;margin-bottom: 26px;}
    .schoolWrap h4{color: #1D1D1D;font-size: 14px;font-weight: 400;margin-bottom: 26px;}
    .resultData{padding: 10px 0;margin-bottom: 10px; border: 1px solid #eee;}
    .btnMore{color: #ca0000;text-align: center;font-weight: 400;}
    /deep/ .el-upload-list--picture .el-upload-list__item{width: 100px;display: inline-block;margin-right: 50px;}
    .demo-form /deep/ .el-form-item{margin-bottom: 10px;}
</style>