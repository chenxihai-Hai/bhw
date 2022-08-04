<template>
    <div class="intellectual" style="padding-bottom: 50px;">
        <el-row><h2 class="useTitle">知识产权</h2></el-row>
        <el-row>
            <el-menu :default-active="intellectuaManageMenuActive" class="useMenu" mode="horizontal" @select="intellectualManageMenuSelect">
                <el-menu-item index="-1">上传发布</el-menu-item>
                <el-menu-item index="0">交易管理</el-menu-item>
                <el-menu-item index="1">产品管理</el-menu-item>
                <el-menu-item index="2">退款管理</el-menu-item>
                <el-menu-item index="3">操作历史</el-menu-item>
            </el-menu>
        </el-row>
        <el-row style="margin-top: 50px;"></el-row>
        <el-row v-if="intellectuaManageMenuActive=='-1'">
            <el-col :span="20">
                <el-form label-width="120px" label-position="right" :rules="rules" ref="productInfo" :model="productInfo">
                    <el-form-item label="供需选择:">
                        <el-radio v-model="productInfo.needType" class="radioInfo" :label="1">供(我要卖)</el-radio>
                        <el-radio v-model="productInfo.needType" class="radioInfo" :label="2">需(我要买)</el-radio>
                    </el-form-item>
                    <el-form-item label="产品名称:"  prop="projectName" :rules="[{required: true, message: '产品名称不能为空'}]" >
                        <el-input  placeholder="请输入产品名称" v-model="productInfo.projectName" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="产品价格:">
                        <el-radio v-model="priceType" class="radioInfo" :label="1">填写价格</el-radio>
                        <el-radio v-model="priceType" class="radioInfo" :label="2">面议</el-radio>
                    </el-form-item>
                    <template v-if="priceType=='1'">
                        <!--<el-form-item label="原价:">
                            <el-input  placeholder="请输入原价" v-model="productInfo.unitPrice"></el-input>
                        </el-form-item>
                        <el-form-item label="预算:">
                            <el-input  placeholder="请输入预算" v-model="productInfo.unitPrice"></el-input>
                        </el-form-item>-->
                        <el-form-item label="售价:" prop="unitPrice">
                            <el-input  placeholder="请输入售价" v-model="productInfo.unitPrice" class="ipt"></el-input>
                        </el-form-item>
                    </template>
                   <!-- <el-form-item label="售价:" prop="unitPrice">
                        <el-input  placeholder="请输入售价" v-model="productInfo.unitPrice" class="ipt"></el-input>
                    </el-form-item>-->
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
                    <el-form-item label="产品数量:" prop="projectNumber" :rules="[{ type: 'number', message: '产品数量必须为数字值'} ]">
                        <el-input  placeholder="请输入产品数量" v-model.number="productInfo.projectNumber" class="ipt"></el-input>
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
                        <el-radio v-model="productInfo.freight" class="radioInfo" :label="1">卖家承担运费</el-radio>
                        <el-radio v-model="productInfo.freight" class="radioInfo" :label="2">买家承担运费</el-radio>
                    </el-form-item>
                    <el-form-item label="产品简介:">
                        <el-input  placeholder="请输入产品简介" v-model="productInfo.introduce" class="ipt"></el-input>
                    </el-form-item>
                    <el-form-item label="发布人:">
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
                            <el-button size="small" class="btnColorOrg btnClickUp" type="primary">点击上传</el-button>
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
                            <el-button size="small" class="btnColorOrg btnClickUp" type="primary">点击上传</el-button>
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
                    <el-form-item style="margin-top: 120px;">
                        <el-button class="btnColorOrg btnSave"  type="info" @click="submitInfo(1)">保存草稿</el-button>
                        <el-button class="btnColorBlue btnUp" type="primary" @click="submitInfo(2)">上传发布</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <template v-if="intellectuaManageMenuActive!='-1'">
            <component :is="subMenuComponents[intellectuaManageMenuActive]" v-on:upInfoAc-event="upInfoAc" ></component>
        </template>
    </div>
</template>

<script>
    import {
        quillEditor
    } from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {qryIntellectualType} from "../../../api/projectDw/projectDw";
    import {addProject,upProject} from "../../../api/intellectual/intellectual";
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    import {saveOperateRecord} from "../../../api/intellectual/operateRecord";
    import PRODUCTORDER from "./productOrder";
    import PRODUCTLIST from "./productList";
    import REFUNDRECORD from "./refundRecord";
    import OPERATERECORD from "./operateRecord";
    import axios from 'axios';
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
            quillEditor,
            "productOrder":PRODUCTORDER,
            "productList":PRODUCTLIST,
            "refundRecord":REFUNDRECORD,
            "operateRecord":OPERATERECORD
        },
        props:["userInfo"],
        data(){
            var validatePrice = (rule, value, callback) => {
                if(value==''){
                    callback(new Error('请输入价格'));
                }else{
                    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
                    if(!regPos.test(value)){
                        callback(new Error('请正确输入价格'));
                    }else{
                        callback();
                    }
                }
            };
            return {
                rules: {
                    unitPrice: [  { validator: validatePrice, trigger: 'blur' }]
                },
                subMenuComponents:["productOrder","productList","refundRecord","operateRecord"],
                intellectuaManageMenuActive:"-1",
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
                projectType:[{value:1,label:"商标"},{value:2,label:"专利"},{value:3,label:"域名"}],
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
        watch:{
            'productInfo.needType':function(newVal,oldVal){
                if(newVal==1 && this.userInfo.merchantFlag!=1){
                    this.$emit("oprVisibleId-event",1);
                    this.productInfo.needType = oldVal;
                }
            }
        },
        created(){
            this.qryIntellectualType(1);
            this.qryIntellectualType(2);
            this.qryProvinceList();
        },
        methods:{
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
            upInfoAc(obj){
                if(undefined==obj.unitPrice||''==obj.unitPrice){
                    this.priceType = 2;
                }else{
                    this.priceType = 1;
                }
                this.productInfo = obj
                this.productInfo.provinceId = obj.provinceName;
                this.productInfo.cityId = obj.cityName;
                this.intellectuaManageMenuActive = '-1';
            },
            submitInfo(val){
                this.$refs['productInfo'].validate((valid) => {
                    if (valid) {
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
                        this.productInfo.userId = this.userInfo.id;
                        this.productInfo.status =val;
                        if(this.productInfo.needType=='2'){
                            this.productInfo.projectType=1;
                        }
                        if(this.productInfo.id){
                            if(!Number.isInteger(this.productInfo.cityId)){
                                this.productInfo.cityId = undefined;
                            }
                            this.productInfo.createTime = undefined;
                            this.productInfo.updateTime = undefined;
                            upProject(this.productInfo).then(response=>{
                                this.$refs['productInfo'].resetFields();
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
                                saveOperateRecord({userId:this.productInfo.userId,title:this.productInfo.projectName,
                                    content:this.productInfo.projectType==1?"商标":"专利",type:1}).then(resp=>{});
                                this.$confirm('信息发布成功, 是否继续发布?', '提示', {
                                    confirmButtonText: '确定',
                                    cancelButtonText: '取消',
                                    type: 'success'
                                }).then(() => {
                                }).catch(() => {
                                    this.intellectuaManageMenuActive = '1';
                                });
                            });
                        }else{
                            addProject(this.productInfo).then(response => {
                                this.$refs['productInfo'].resetFields();
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
                                saveOperateRecord({userId:this.productInfo.userId,title:this.productInfo.projectName,
                                    content:this.productInfo.projectType==1?"商标":"专利",type:1}).then(resp=>{});
                                this.$confirm('信息发布成功, 是否继续发布?', '提示', {
                                    confirmButtonText: '确定',
                                    cancelButtonText: '取消',
                                    type: 'success'
                                }).then(() => {
                                }).catch(() => {
                                    this.intellectuaManageMenuActive = '1';
                                });
                            });
                        }

                    }
                });
            },
            intellectualManageMenuSelect(index,indexPath){
                this.intellectuaManageMenuActive = index;
            },
            handleRemove(file, fileList) {
                console.log('file.url',file.url);
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
            },
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