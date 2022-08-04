<template>
    <el-dialog title="知识产权"
            :visible.sync="intelleDialogVisible"
            width="60%" class="serviceDialog">
    <div class="intellectual" style="padding-bottom: 50px;">
        <el-row>
            <el-col :span="20">
                <el-form label-width="120px" label-position="right" ref="productInfo" :model="productInfo">
                    <el-form-item label="供需选择:">
                        <el-radio v-model="productInfo.needType" :label="1">供(我要卖)</el-radio>
                        <el-radio v-model="productInfo.needType" :label="2">需(我要买)</el-radio>
                    </el-form-item>
                    <el-form-item label="产品名称:">
                        <el-input  placeholder="请输入产品名称" v-model="productInfo.projectName" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品编码:">
                        <el-input  placeholder="请输入产品编码" v-model="productInfo.projectCode" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品价格:">
                        <el-radio v-model="priceType" :label="1">填写价格</el-radio>
                        <el-radio v-model="priceType" :label="2">面议</el-radio>
                    </el-form-item>
                    <template v-if="priceType=='1'">
                        <el-form-item label="售价:">
                            <el-input  placeholder="请输入售价" v-model="productInfo.unitPrice" class="ipt"></el-input>
                        </el-form-item>
                    </template>
                    <el-form-item label="所属类别:" v-if="productInfo.needType==1">
                        <el-select v-model="productInfo.projectType" placeholder="请选择">
                            <el-option
                                    v-for="item in projectType"
                                    :key="item.label"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="行业分类:" v-if="productInfo.projectType=='2' ">
                        <el-select v-model="productInfo.industryId" placeholder="请选择">
                            <el-option
                                    v-for="item in industryType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="专利分类:" v-if="productInfo.projectType=='2'">
                        <el-select v-model="productInfo.patentType" placeholder="请选择">
                            <el-option
                                    v-for="item in patentType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="商标分类:" v-if="productInfo.projectType=='1' && productInfo.needType=='1'">
                        <el-select v-model="productInfo.industryId" placeholder="请选择">
                            <el-option
                                    v-for="item in spType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="分类:" v-if=" productInfo.needType=='2'">
                        <el-select v-model="productInfo.industryId" placeholder="请选择">
                            <el-option
                                    v-for="item in spType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="交易类型:" v-if=" productInfo.needType=='1'">
                        <el-select v-model="productInfo.dealType" placeholder="请选择">
                            <el-option
                                    v-for="item in dealType"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="注册号:" v-if=" productInfo.needType=='1' && productInfo.projectType=='1'">
                        <el-input  placeholder="请输入注册号" v-model="productInfo.codeNumber" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="有效期:" v-if=" productInfo.needType=='1' && productInfo.projectType=='1'">
                        <el-input  placeholder="请输入有效期" v-model="productInfo.indate" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="专利号:" v-if=" productInfo.needType=='1' && productInfo.projectType=='2'">
                        <el-input  placeholder="请输入注册号" v-model="productInfo.codeNumber" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品数量:">
                        <el-input  placeholder="请输入产品数量" v-model="productInfo.projectNumber" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品单位:">
                        <el-input  placeholder="请输入产品单位、例如：件、个、套等" v-model="productInfo.projectUnit" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品所在地:">
                        <el-select v-model="productInfo.provinceId" @change="qryCityList" placeholder="省份" style="margin-right: 14px;">
                            <el-option
                                    v-for="item in provinceList"
                                    :key="item.provinceName"
                                    :label="item.provinceName"
                                    :value="item.provinceId">
                            </el-option>
                        </el-select>
                        <el-select v-model="productInfo.cityId" placeholder="城市">
                            <el-option
                                    v-for="item in cityList"
                                    :key="item.cityName"
                                    :label="item.cityName"
                                    :value="item.cityId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="运费:">
                        <el-radio v-model="productInfo.freight" label="1">卖家承担运费</el-radio>
                        <el-radio v-model="productInfo.freight" label="2">买家承担运费</el-radio>
                    </el-form-item>
                    <el-form-item label="产品简介:">
                        <el-input  placeholder="请输入产品简介" v-model="productInfo.introduce" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="数据来源:">
                        <el-input  placeholder="请输入数据来源" v-model="productInfo.source" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品主图:">
                        <el-upload
                                class="avatar-uploader"
                                action="/api/serverManage/fileUpload/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="productInfo.homeImg" :src="productInfo.homeImg" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon" style="border: 1px dashed #CCC;"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="更多图片:">
                        <el-upload
                                class="upload-demo"
                                action="/api/serverManage/fileUpload/upload"
                                :on-preview="handlePreview"
                                :on-remove="handleRemove"
                                :on-success="handleMoreSuccess"
                                :file-list="fileList"
                                list-type="picture">
                            <el-button size="small" class="btnColorOrg btnClickUp">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="关键字:">
                        <el-input  placeholder="便于快速检索" v-model="productInfo.projectTag" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="协议文件:">
                        <el-upload
                                class="upload-demo"
                                action="/api/serverManage/fileUpload/upload"
                                :file-list="pFfileList"
                                :limit="1"
                                :on-remove="handlePRemove"
                                :on-success="handlePAvatarSuccess"
                                :before-upload="beforePAvatarUpload">
                            <el-button size="small" class="btnColorOrg btnClickUp" >点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传txt/word文件，且不超过5MB</div>
                        </el-upload>
                        <span>{{productInfo.protocolUrl}}</span>
                    </el-form-item>

                    <el-form-item label="产品详情:">
                        <el-upload
                                class="quill-avatar-uploader"
                                action="/api/serverManage/fileUpload/upload"
                                :show-file-list="false"
                                :on-success="uploadSuccess"
                                :on-error="uploadError"
                                :before-upload="beforeUpload">
                        </el-upload>
                        <quill-editor v-model="productInfo.details" ref="myQuillEditor" style="height: 200px;" :options="editorOption">
                        </quill-editor>
                    </el-form-item>
                    <el-form-item style="margin-top: 160px;">
                        <el-button class="btnColorBlue btnUp" type="primary" @click="submitInfo(2)">上传发布</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
    </el-dialog>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {
        quillEditor
    } from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {addProject,qryIntellectualType,upProject} from "../../../api/infoManage/intellectual";
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],  // toggled buttons
        [{'header': 1}, {'header': 2}],    // custom button values
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'indent': '-1'}, {'indent': '+1'}],   // outdent/indent
        [{'direction': 'rtl'}],       // text direction
        [{'size': ['small', false, 'large', 'huge']}], // custom dropdown
        [{'header': [1, 2, 3, 4, 5, 6, false]}],
        [{'color': []}, {'background': []}],   // dropdown with defaults from theme
        [{'font': []}],
        [{'align': []}],
        ['link', 'image'],
        ['clean']

    ];
    export default {
        name: "intellectual",
        components: {
            quillEditor
        },
        data(){
            return {
                intelleDialogVisible:false,
                priceType:'1',
                fileList:[],
                pFfileList:[],
                moreFile:[],
                productInfo:{
                    projectName:null,
                    projectCode:null,
                    unitPrice:null,
                    projectType:null,
                    industryId:null,
                    projectNumber:null,
                    projectUnit:null,
                    cityId:null,
                    freight:null,
                    introduce:null,
                    homeImg:null,
                    imgs:null,
                    projectTag:null,
                    details:null,
                    patentType:null,
                    needType:'2',
                    provinceId:null,
                    protocolUrl:null,
                    dealType:null,
                    codeNumber:null,
                    indate:null,
                    source:null
                },
                countryList:[{id:1,name:'中国'}],
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                dealType:[{id:1,name:'转让'},{id:2,name:'许可'}],
                spType:[{id:1,name:" 01类-化学原料 "},{id:2,name:"02类-颜料油漆"},{id:3,name:"03类-日化用品"},{id:4,name:"04类-燃料油脂"}],
                industryType:[{id:1,name:"农林渔牧/食品饮料"},{id:2,name:"交通/运输/包装"},{id:3,name:"化工/材料/冶金/生物"},{id:4,name:"纺织/造纸"}],
                projectType:[{value:'1',label:"商标"},{value:'2',label:"专利"},{value:'3',label:"域名"}],
                patentType:[{id:1,name:"发明专利"},{id:2,name:"实用新型专利"},{id:3,name:"外观专利"}],
                content: null,
                editorOption: {
                    placeholder: '',
                    theme: 'snow', // or 'bubble'
                    modules: {
                        toolbar: {
                            container: toolbarOptions,
                            handlers: {
                                'image': function (value) {
                                    if (value) {
                                        // 触发input框选择图片文件
                                        document.querySelector('.quill-avatar-uploader input').click()
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        }
                    }
                },
                quillUpdateImg:false
            }
        },
        created(){
            this.qryIntellectualType(1);
            this.qryIntellectualType(2);
            this.qryProvinceList();
        },
        methods:{
            // 富文本图片上传前
            beforeUpload(file) {
                // 显示loading动画
                this.quillUpdateImg = true;
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
            uploadSuccess(res, file) {
                // res为图片服务器返回的数据
                // 获取富文本组件实例
                console.log(res);
                let quill = this.$refs.myQuillEditor.quill;
                // 如果上传成功
                if (res.data ) {
                    // 获取光标所在位置
                    let length = quill.getSelection().index;
                    // 插入图片 res.url为服务器返回的图片地址
                    quill.insertEmbed(length, 'image', res.data)
                    // 调整光标到最后
                    quill.setSelection(length + 1)
                } else {
                    this.$message.error('图片插入失败')
                }
                // loading动画消失
                this.quillUpdateImg = false
            },
            // 富文本图片上传失败
            uploadError() {
                // loading动画消失
                this.quillUpdateImg = false
                this.$message.error('图片插入失败')
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
            qryIntellectualType(val){
                qryIntellectualType({"intellectualType":val}).then(response => {
                    if(val==2){
                        this.industryType = response.data;
                    }else{
                        this.spType = response.data;
                    }
                });
            },
            submitInfo(val){
                for(var p in this.moreFile){
                    if(this.moreFile[p]!='' && null != this.moreFile[p] ){
                        var ig = this.moreFile[p];
                        if(null == ig || ''==ig){
                            continue;
                        }
                        if(ig.indexOf("-zip")>-1){
                            ig = ig.replace('-zip','');
                        }
                        this.productInfo.imgs = this.productInfo.imgs+ig+";";
                    }
                }
                for(var p in this.pFfileList){
                    if(this.pFfileList[p]!='' && null != this.pFfileList[p] ){
                        var ig = this.pFfileList[p];
                        if(null == ig || ''==ig){
                            continue;
                        }
                        if(ig.indexOf("-zip")>-1){
                            ig = ig.replace('-zip','');
                        }
                        this.productInfo.protocolUrl = ig;
                    }
                }

                this.productInfo.status =val;
                if(this.productInfo.needType=='2'){
                    this.productInfo.projectType=1;
                }
                if(this.productInfo.id){
                    this.productInfo.createTime = undefined;
                    this.productInfo.updateTime = undefined;
                        upProject(this.productInfo).then(response => {
                        this.$refs['productInfo'].resetFields();
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'编辑了知识产权产品('+this.productInfo.projectName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.intelleDialogVisible = false;
                        this.$parent.loadListData();
                    });
                }else{
                    this.productInfo.userId = 1;
                    addProject(this.productInfo).then(response => {
                        this.$refs['productInfo'].resetFields();
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'发布了知识产权产品('+this.productInfo.projectName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.intelleDialogVisible = false;
                        this.$parent.loadListData();
                    });
                }
            },
            cleanData(){
                this.productInfo={
                    projectName:null,
                    projectCode:null,
                    unitPrice:null,
                    projectType:null,
                    industryId:null,
                    projectNumber:null,
                    projectUnit:null,
                    cityId:null,
                    freight:null,
                    introduce:null,
                    homeImg:null,
                    imgs:null,
                    projectTag:null,
                    details:null,
                    patentType:null,
                    needType:'1',
                    provinceId:null,
                    protocolUrl:null
                };
            },
            handleRemove(file, fileList) {
                this.moreFile[file.url] = '';
            },
            handlePRemove(file, fileList) {
                this.pFfileList[file.url] = '';
            },
            handlePreview(file) {
            },
            handleMoreSuccess(res, file){
                this.moreFile[file.url] = res.data;
            },
            handleAvatarSuccess(res, file) {
                this.productInfo.homeImg = res.data;
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
            handlePAvatarSuccess(res, file) {
                // this.productInfo.protocolUrl = res.data;
                this.pFfileList[file.url] =  res.data;
            },
            beforePAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isLt2M) {
                    this.$message.error('上传文件大小不能超过 5MB!');
                }
                return isLt2M;
            }
        }
    }
</script>

<style scoped>
    .btnUp,.btnSave{width: 86px;height: 32px;padding: 0;border: none;}
    .btnColorOrg.btnClickUp{height: 29px;font-weight: 400; padding: 0 24px;border: none;}
    .ql-container {
        box-sizing: border-box;
        font-family: Helvetica, Arial, sans-serif;
        font-size: 13px;
        height: 150px;
        margin: 0px;
        position: relative;
    }
    /deep/.el-button.el-button--default span {
        color: rgba(29,29,29,1);
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>