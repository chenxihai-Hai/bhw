<template>
    <el-dialog title="技术合同/科技成果登记"
            :visible.sync="supplyDialogVisible"
            width="60%" class="serviceDialog">
    <div class="contractUploading" style="padding-left: 80px;">
        <el-form ref="productInfo" label-width="140px" label-position="right" :model="productInfo">
            <el-form-item label="服务名称：" prop="name">
                <div style="float: left;">
                    <el-input placeholder="必填" v-model="productInfo.serverName">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="供需选择：" prop="name">
                <el-radio v-model="productInfo.checkType" :label="1">供（我要帮办）</el-radio>
                <el-radio v-model="productInfo.checkType" :label="2">需(我要找人代办)</el-radio>
            </el-form-item>
            <el-form-item label="业务选择：" prop="name">
                <el-radio v-model="productInfo.businessType" :label="1">技术合同登记</el-radio>
                <el-radio v-model="productInfo.businessType" :label="2">科技成果认定登记</el-radio>
            </el-form-item>
            <el-form-item label="计价方式：" prop="name">
                <el-radio v-model="productInfo.onPriceType" :label="1">填写价格</el-radio>
                <el-radio v-model="productInfo.onPriceType" :label="2">面议</el-radio>
            </el-form-item>
            <template v-if="productInfo.onPriceType=='1'">
                <el-form-item label="原价：" prop="name" class="price">
                    <div>
                        <el-input placeholder="选填" v-model="productInfo.originalPrice">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="售价：" prop="name" class="price">
                    <div>
                        <el-input placeholder="必填" v-model="productInfo.price">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="预算：" prop="name" class="price">
                    <div>
                        <el-input placeholder="发填布需求时选" v-model="productInfo.budget">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
            </template>

            <el-form-item label="所在地：" prop="name" class="dizhi">
                <el-select v-model="province" placeholder="省份" @change="qryCityList" class="address">
                    <el-option
                            v-for="item in provinceList"
                            :key="item.provinceName"
                            :label="item.provinceName"
                            :value="item.provinceId">
                    </el-option>
                </el-select>
                <el-select v-model="productInfo.cityId" placeholder="地区" class="address">
                    <el-option
                            v-for="item in cityList"
                            :key="item.cityName"
                            :label="item.cityName"
                            :value="item.cityId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="运费：" prop="name">
                <el-radio v-model="productInfo.freightType" :label="1">卖家承担运费（包邮）</el-radio>
                <el-radio v-model="productInfo.freightType" :label="2">买家承担运费</el-radio>
            </el-form-item>
            <el-form-item label="联系人：" prop="name">
                <div style="float: left;">
                    <el-input placeholder="必填" v-model="productInfo.contactName">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="联系人单位：" prop="name">
                <div style="float: left;">
                    <el-input placeholder="必填" v-model="productInfo.contactUnit">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="联系人手机号：" prop="name">
                <div style="float: left;">
                    <el-input placeholder="必填" v-model="productInfo.contactPhone">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="关键字：" prop="name">
                <div style="width:142px;float: left;" class="guanjian">
                    <el-input placeholder="XX.XXX.XX" v-model="productInfo.keyWord">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="数据来源：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productInfo.source">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item  label="相关资料上传：">
                <el-upload
                        class="upload-demo"
                        action="/api/serverManage/fileUpload/upload"
                        :file-list="pFfileList"
                        :limit="1"
                        :on-remove="handlePRemove"
                        :on-success="handlePAvatarSuccess"
                        :before-upload="beforePAvatarUpload">
                    <el-button class="btnColorOrg btnClickUp" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传txt/word文件，且不超过5MB</div>
                </el-upload>
                <span>{{productInfo.dataFilePath}}</span>
            </el-form-item>
            <el-form-item label="详情描述:">
                <el-upload
                        class="quill-avatar-uploader"
                        action="/api/serverManage/fileUpload/upload"
                        :show-file-list="false"
                        :on-success="uploadSuccess"
                        :on-error="uploadError"
                        :before-upload="beforeUpload">
                </el-upload>
                <quill-editor v-model="productInfo.serverRemark" ref="myQuillEditor" :options="editorOption"
                              @change="onEditorChange($event)"
                              style="width:90%;height: 200px;"></quill-editor>
            </el-form-item>
            <el-form-item style="padding: 120px 0 80px;">
                <el-button class="btnColorBlue btnUp" @click="onSubmit(2)">上传发布</el-button>
            </el-form-item>
        </el-form>
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
    import {saveReleaseInfo,upTechSupplyInfo} from "../../../api/infoManage/techSupply";
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
        name:"addSupply",
        components: {
           quillEditor
        },
        data() {
            return {
                supplyDialogVisible:false,
                radio: '1',
                radioType: '1' + '',
                options: [],
                value: '',
                radioTwo: 1,
                state: '',
                province: '',
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                region: '',
                regionList: [],
                street: '',
                pFfileList:[],
                freight: '1',
                productInfo: {
                    checkType: 2,
                    businessType: null,
                    onPriceType: null,
                    originalPrice: null,
                    price: null,
                    budget: null,
                    cityId: null,
                    address: null,
                    freightType: null,
                    contactName: null,
                    contactUnit: null,
                    keyWord: null,
                    serverName: null,
                    serverRemark: null,
                    status: 2,
                    dataFilePath:'',
                    source:'',
                    userId:1
                },
                flag: false,
                quillUpdateImg:false,
                content:'',
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
                                        console.log(document.querySelector('.quill-avatar-uploader input'));
                                        document.querySelector('.quill-avatar-uploader input').click()
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        }
                    }
                }
            };
        },
        created(){
            this.qryProvinceList();
        },
        methods: {
            onEditorChange({editor, html, text}){
                this.content = html;
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
            handlePRemove(file, fileList) {
                this.pFfileList[file.url] = '';
            },
            handlePAvatarSuccess(res, file) {
                this.pFfileList[file.url] =  res.data;
            },
            beforePAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isLt2M) {
                    this.$message.error('上传文件大小不能超过 5MB!');
                }
                return isLt2M;
            },
            onSubmit(status) {
                for(var p in this.pFfileList){
                    if(this.pFfileList[p]!='' && null != this.pFfileList[p] ){
                        var ig = this.pFfileList[p];
                        if(null == ig || ''==ig){
                            continue;
                        }
                        if(ig.indexOf("-zip")>-1){
                            ig = ig.replace('-zip','');
                        }
                        this.productInfo.dataFilePath = ig;
                    }
                }
                this.productInfo.status=status;
                if( this.productInfo.id){
                    this.productInfo.createTime = null;
                    upTechSupplyInfo(this.productInfo).then(response => {
                        this.$refs['productInfo'].resetFields();
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'编辑了技术合同产品('+this.productInfo.serverName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.supplyDialogVisible = false;
                        this.$parent.loadOrderData();
                    })
                }else{
                    saveReleaseInfo(this.productInfo).then(response => {
                        this.$refs['productInfo'].resetFields();
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'发布了技术合同产品('+this.productInfo.serverName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.supplyDialogVisible = false;
                        this.$parent.loadOrderData();
                    })
                }
            },
            cleanData(){
                this.productInfo={
                    checkType: 2,
                        businessType: null,
                        onPriceType: null,
                        originalPrice: null,
                        price: null,
                        budget: null,
                        cityId: null,
                        address: null,
                        freightType: null,
                        contactName: null,
                        contactUnit: null,
                        keyWord: null,
                        serverName: null,
                        serverRemark: null,
                        status: 2,
                        dataFilePath:'',
                        source:'',
                        userId:1
                }
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            },
            handleExceed(files, fileList) {
                this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeRemove(file, fileList) {
                return this.$confirm(`确定移除 ${file.name}？`);
            }
        }
    }
</script>

<style scoped>
    .btnUp,.btnSave{width: 86px;height: 32px;padding: 0;border: none;}
    .btnColorOrg.btnClickUp{height: 29px;font-weight: 400; padding: 0 24px;border: none;}
    /deep/ .contractUploading .el-input--suffix .el-input__inner {
        width: 308px;
        height: 46px;
        background: rgba(255, 255, 255, 1);
    }

    /deep/ .address .el-input--suffix .el-input__inner {
        width: 140px;
        height: 38px;
        background: rgba(255, 255, 255, 1);
    }

    /deep/ .dizhi .el-select > .el-input {
        margin-right: 17px;
    }

    .dizhi i {
        position: relative;
        right: 17px;
    }

    .price .el-form-item__content .el-input-group {
        width: 308px;
        background: rgba(255, 255, 255, 1);
    }

    .guanjian .el-input {
        width: 308px;
        height: 46px;
        background: rgba(255, 255, 255, 1);
    }
</style>