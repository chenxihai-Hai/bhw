<template>
    <el-dialog
            :visible.sync="ologyDialogVisible"
            width="60%" class="serviceDialog" title="技术供需">
    <div class="technicalUploading" style="padding-left: 80px;">
        <el-form ref="productinfo" label-width="140px" label-position="right" :model="productinfo">
            <el-form-item label="供需选择：" prop="name">
                <el-radio v-model="productinfo.checkType" :label="1">供（我要卖）</el-radio>
                <el-radio v-model="productinfo.checkType" :label="2">需(我要买)</el-radio>
            </el-form-item>
            <el-form-item label="技术名称：" prop="name">
                <el-input placeholder="选填" v-model="productinfo.techName"></el-input>
            </el-form-item>
            <el-form-item label="技术编码：" prop="name">
                <el-input placeholder="选填" v-model="productinfo.techCode"></el-input>
            </el-form-item>
            <el-form-item label="计价方式：" prop="name">
                <el-radio v-model="productinfo.onPriceType" :label="1">填写价格</el-radio>
                <el-radio v-model="productinfo.onPriceType" :label="2">面议</el-radio>
            </el-form-item>
            <template v-if="productinfo.onPriceType==1">
                <el-form-item label="原价：" prop="name" class="price">
                    <div>
                        <el-input placeholder="选填" v-model="productinfo.originalPrice">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="预算：" prop="name" class="price">
                    <div>
                        <el-input placeholder="发填布需求时选" v-model="productinfo.budget">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
                <el-form-item label="售价：" prop="name" class="price">
                    <div>
                        <el-input placeholder="必填" v-model="productinfo.price">
                            <template slot="append">元</template>
                        </el-input>
                    </div>
                </el-form-item>
            </template>
            <el-form-item label="所属类别：" prop="name">
                <el-select v-model="productinfo.businessType" placeholder="请选择 ">
                    <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="行业类型：" prop="name">
                <el-select v-model="productinfo.intellectualType" placeholder="请选择 ">
                    <el-option
                            v-for="item in leixinselect"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="截止日期：" prop="name">
                <el-radio v-model="productinfo.cutoffDates" label="30">一个月内</el-radio>
                <el-radio v-model="productinfo.cutoffDates" label="90">三个月内</el-radio>
                <el-radio v-model="productinfo.cutoffDates" label="180">半年内</el-radio>
                <el-radio v-model="productinfo.cutoffDates" label="365">一年内</el-radio>
                <el-radio v-model="productinfo.cutoffDates" label="-1">自定义时间</el-radio>
                <template v-if="productinfo.cutoffDates=='-1'">
                    <el-input placeholder="选填" v-model="productinfo.cutoffDate" style="width: 200px">
                    </el-input>
                </template>
            </el-form-item>
            <el-form-item label="所在地：" prop="name" class="dizhi">
                <el-select v-model="province" placeholder="省份" @change="qryCityList" class="address">
                    <el-option
                            v-for="item in provinceList"
                            :key="item.provinceName"
                            :label="item.provinceName"
                            :value="item.provinceId">
                    </el-option>
                </el-select>
                <el-select v-model="productinfo.cityId" placeholder="地区" class="address">
                    <el-option
                            v-for="item in cityList"
                            :key="item.cityName"
                            :label="item.cityName"
                            :value="item.cityId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="运费：" prop="name">
                <el-radio v-model="productinfo.freightType" label="1">卖家承担运费（包邮）</el-radio>
                <el-radio v-model="productinfo.freightType" label="2">买家承担运费</el-radio>
            </el-form-item>
            <el-form-item label="技术具体联系人：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productinfo.contactName">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="技术联系人单位：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productinfo.contactUnit">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="技术联系人手机号：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productinfo.contactPhone">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="关键字：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productinfo.keyWord">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="数据来源：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productinfo.source">
                    </el-input>
                </div>
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
                <quill-editor v-model="productinfo.serverRemark" ref="myQuillEditor" style="width:90%;height: 200px;"
                              :options="editorOption">
                </quill-editor>
            </el-form-item>
            <el-form-item style="margin: 150px 0 60px;">
                <el-button class="btnColorBlue btnUp" @click="onSubmit(2)">上传发布</el-button>
            </el-form-item>
        </el-form>
    </div>
    </el-dialog>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {getBussinessType,saveReleaseInfo,upTechologyInfo} from "../../../api/infoManage/techology";
    import {qryScienceRequireTypeList} from "../../../api/projectDw/projectDw";
    import {qryProvinceList,qryCityList} from "../../../api/admin/index"
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
        name: "technicalUploading",
        components: {
            quillEditor,
        },
        data() {
            return {
                ologyDialogVisible:false,
                radio: '1',
                priceradio: '1',
                options: [
                    {id: "", name: ""},
                ],
                value: '',
                leixinselect: [
                    {value: "1", label: "类型"},
                ],
                leixin: '',
                time: '1', state: '',
                statetList: [
                    {value: "1", label: "中国"},
                ],
                province: '',
                region: '',
                regionList: [
                    {value: "1", label: "岳阳"},
                ],
                street: '',
                streetList: [
                    {value: "1", label: "岳阳楼区"},
                ],
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                freight: 1,
                details: "",
                productinfo: {
                    checkType: "",
                    techName: "",
                    techCode: "",
                    businessType: "",
                    intellectualType: "",
                    onPriceType: "",
                    originalPrice: "",
                    price: "",
                    budget: "",
                    cityId: "",
                    address: "",
                    freightType: "",
                    contactName: "",
                    contactUnit: "",
                    contactPhone: "",
                    keyWord: "",
                    serverName: "",
                    serverRemark: "",
                    cutoffDates:'30',
                    result:2,
                    status: "1",
                    cutoffDate: "",
                    source:'',
                    userId:1
                },
                flag: false,
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
        methods: {
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
            dateAddDays(dayCount) {
                var tempDate=new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                var resultDateStr=resultDate.getFullYear()+"-"+(resultDate.getMonth()+1)+"-"+(resultDate.getDate());//将日期转化为字符串格式
                return resultDateStr;
            },
            onSubmit(status) {
                if(undefined == this.productinfo.cutoffDates ||  this.productinfo.cutoffDates==null){
                    this.$message.error('请选择截止日期!');
                    return;
                }
                if(this.productinfo.cutoffDates=='-1'){
                    this.productinfo.cutoffDate = this.dateAddDays(this.productinfo.cutoffDate);
                }else {
                    this.productinfo.cutoffDate = this.dateAddDays(this.productinfo.cutoffDates);
                }
                this.productinfo.status=status;
                this.productinfo.uId = 1;
                this.productinfo.serverName = this.productinfo.techName;
                this.productinfo.createTime = undefined;
                if(this.productinfo.id){
                    upTechologyInfo(this.productinfo).then(response => {
                        this.$refs['productinfo'].resetFields();
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'编辑了技术供需产品('+this.productInfo.serverName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.ologyDialogVisible = false;
                        this.$parent.loadOrderData();
                    })
                }else{
                    saveReleaseInfo(this.productinfo).then(response => {
                        this.$refs['productinfo'].resetFields();
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'发布了技术供需产品('+this.productinfo.serverName+')'
                        };
                        saveBackOperate(par).then();
                        this.cleanData();
                        this.ologyDialogVisible = false;
                        this.$parent.loadOrderData();
                    })
                }
            },
            cleanData(){
                this.productinfo = {
                    checkType: "",
                    techName: "",
                    techCode: "",
                    businessType: "",
                    intellectualType: "",
                    onPriceType: "",
                    originalPrice: "",
                    price: "",
                    budget: "",
                    cityId: "",
                    address: "",
                    freightType: "",
                    contactName: "",
                    contactUnit: "",
                    contactPhone: "",
                    keyWord: "",
                    serverName: "",
                    serverRemark: "",
                    cutoffDates:'30',
                    result:2,
                    status: "1",
                    cutoffDate: "",
                    source:'',
                    userId:1
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
            }
        },
        created() {
            getBussinessType({type: 16}).then(response => {
                this.options = response.data;
            })
            qryScienceRequireTypeList().then(response => {
                this.leixinselect = response.data;
            })
            this.qryProvinceList();
        }
    }
</script>

<style scoped>
    .btnUp,.btnSave{width: 86px;height: 32px;padding: 0;border: none;}
    .name /deep/ .el-input {
        width: 308px;
        height: 46px;
        background: rgba(255, 255, 255, 1);
    }

    .price /deep/ .el-form-item__content .el-input-group {
        width: 308px;
        background: rgba(255, 255, 255, 1);
    }

    .address /deep/ .el-input--suffix .el-input__inner {
        width: 140px;
        height: 38px;
        background: rgba(255, 255, 255, 1);
    }

    .address{
         margin-right: 17px;
     }

</style>