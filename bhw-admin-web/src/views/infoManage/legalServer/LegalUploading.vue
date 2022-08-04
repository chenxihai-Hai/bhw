<template>
    <el-dialog title="法律服务"
            :visible.sync="legalDialogVisible"
            width="60%" class="serviceDialog">
    <div class="LegalUploading" style="padding-left: 80px;">
        <el-form ref="form" label-width="140px" label-position="right" :model="productInfo">
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
            <el-form-item label="业务类型：" prop="name">
                <el-select v-model="productInfo.businessType" placeholder="请选择 ">
                    <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="计价方式：" prop="name">
                <el-radio v-model="productInfo.onPriceType" :label="1">填写价格</el-radio>
                <el-radio v-model="productInfo.onPriceType" :label="2">面议</el-radio>
            </el-form-item>
            <template v-if="productInfo.onPriceType==1">
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
                <el-select v-model="province" @change="qryCityList" placeholder="省份" class="address" style="margin-right: 14px;">
                    <el-option
                            v-for="item in provinceList"
                            :key="item.provinceName"
                            :label="item.provinceName"
                            :value="item.provinceId">
                    </el-option>
                </el-select>
                <el-select v-model="productInfo.cityId" placeholder="城市" class="address">
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
                <div style="width:142px;float: left;margin-right: 24px " class="lianxi">
                    <el-input placeholder="必填" v-model="productInfo.contactName">
                    </el-input>
                </div>
                <span style="float: left ">联系人单位：</span>
                <div style="width:142px;float: left;" class="danwei">
                    <el-input placeholder="必填" v-model="productInfo.contactUnit">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="关键字：" prop="name">
                <div style="width:142px;float: left;" class="guanjian">
                    <el-input placeholder="必填" v-model="productInfo.keyWord">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item label="数据来源：" prop="name">
                <div style="width:142px;float: left;" class="name">
                    <el-input placeholder="选填" v-model="productInfo.source">
                    </el-input>
                </div>
            </el-form-item>
            <el-form-item style="margin: 50px 0;">
                <el-button class="btnColorBlue btnUp" @click="onSubmit">上传发布</el-button>
            </el-form-item>
        </el-form>
    </div>
    </el-dialog>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {saveReleaseInfo,upLegalServerInfo,getBussinessType} from "../../../api/infoManage/legalServer";
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    export default {
        name: "LegalUploading",
        data() {
            return {
                legalDialogVisible:false,
                radio: '1',
                options: [
                    {id: "", name: ""},
                ],
                value: '',
                radioTwo: 1,
                state: '',
                statetList: [
                    {value: "1", label: "中国"},
                ],
                province: '',
                provinceList:[{provinceId:1,provinceName:'广东省'}],
                cityList:[{cityId:1,cityName:'深圳市'}],
                region: '',
                regionList: [
                    {value: "1", label: "岳阳"},
                ],
                street: '',
                streetList: [
                    {value: "1", label: "岳阳楼区"},
                ],
                freight: 1,
                productInfo: {
                    checkType: "2",
                    businessType: "",
                    onPriceType: "",
                    originalPrice: "",
                    price: "",
                    budget: "",
                    cityId: "",
                    address: "",
                    freightType: "",
                    contactName: "",
                    contactUnit: "",
                    keyWord: "",
                    source:"",
                    serverName: "",
                    serverRemark: "",
                    status: 2,
                    userId:1
                },
                flag: false,
            };
        },
       /* watch:{
            'productInfo.checkType':function(newVal,oldVal){
                if(newVal==1 && this.$store.state.user.userInfo.merchantFlag!=1){
                    this.$emit("oprVisibleId-event",1);
                    this.productInfo.checkType = oldVal;
                }
            }
        },*/
        methods: {
            onSubmit() {
                if (this.productInfo.checkType==''){
                    this.$message.error('请选择供需发布!');
                } else if (this.productInfo.onPriceType=='') {
                    this.$message.error('请选择计价方式!');
                }else if (this.productInfo.freightType=='') {
                    this.$message.error('请选择运费方式!');
                }else if (this.productInfo.contactName.trim()=='') {
                    this.$message.error('请输入联系人!');
                }else if (this.productInfo.contactUnit.trim()=='') {
                    this.$message.error('请输入联系人单位!');
                }else if (this.productInfo.keyWord.trim()=='') {
                    this.$message.error('请输入关键字!');
                }else{
                    if(this.productInfo.id){
                        this.productInfo.createTime = undefined;
                        upLegalServerInfo(this.productInfo).then(response => {
                            this.flag = response.data;
                            if (this.flag) {
                                this.$message.success('上传成功!');
                            } else {
                                this.$message.error('上传失败!');
                            }
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:'编辑了法律服务产品('+this.productInfo.serverName+')'
                            };
                            saveBackOperate(par).then();
                            this.cleanData();
                            this.legalDialogVisible = false;
                            this.$parent.loadOrderData();
                        })
                    }else{
                        this.productInfo.status=2;
                        saveReleaseInfo(this.productInfo).then(response => {
                            this.flag = response.data;
                            if (this.flag) {
                                this.$message.success('上传成功!');
                            } else {
                                this.$message.error('上传失败!');
                            }
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:'发布了法律服务产品('+this.productInfo.serverName+')'
                            };
                            saveBackOperate(par).then();
                            this.cleanData();
                            this.legalDialogVisible = false;
                            this.$parent.loadOrderData();
                        })
                    }
                }
            },
            cleanData(){
                this.productInfo={
                    checkType: "2",
                        businessType: "",
                        onPriceType: "",
                        originalPrice: "",
                        price: "",
                        budget: "",
                        cityId: "",
                        address: "",
                        freightType: "",
                        contactName: "",
                        contactUnit: "",
                        keyWord: "",
                        source:"",
                        serverName: "",
                        serverRemark: "",
                        status: 2,
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
            this.qryProvinceList();
            getBussinessType({type:11}).then(response => {
                this.options = response.data;
            })
        }
    }
</script>

<style scoped>
    .btnUp,.btnSave{width: 86px;height: 32px;padding: 0;border: none;}
    /deep/ .LegalUploading .el-input--suffix .el-input__inner {
        width: 308px;
        height: 46px;
        background: rgba(255, 255, 255, 1);
    }

    /deep/ .address .el-input--suffix .el-input__inner {
        width: 150px;
        height: 38px;
        background: rgba(255, 255, 255, 1);
    }

    /deep/ .address{margin-right: 17px;}
    /deep/ .price .el-form-item__content .el-input-group {
        width: 308px;
        background: rgba(255, 255, 255, 1);
    }

    /deep/ .guanjian .el-input {
        width: 308px;
        height: 46px;
        background: rgba(255, 255, 255, 1);
    }


</style>