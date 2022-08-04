<template>
    <div>
        <label style="font-size:12px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,165,85,1);padding-left: 40px;">新增收货地址＋</label>
        <ul class="addressLi" style="width:55%;padding-left: 40px;">
            <li style="line-height: 28px;margin-top: 13px;height:32px;background:rgba(247,255,207,1);">
                <label>当前配送至</label>
                <label style="padding-left: 0;">中国大陆</label>
            </li>
            <li style="margin-top: 14px;">
                <label><span style="color: red">*</span>地址信息：</label>
                <el-select v-model="addressInfo.provinceId" @change="qryCityList" placeholder="省份" style="width: 160px;margin-right: 20px;">
                    <el-option
                            v-for="item in provinceList"
                            :key="item.provinceId"
                            :label="item.provinceName"
                            :value="item.provinceId">
                    </el-option>
                </el-select>
                <el-select v-model="addressInfo.area" placeholder="城市" style="width: 160px;">
                    <el-option
                            v-for="item in cityList"
                            :key="item.cityId"
                            :label="item.cityName"
                            :value="item.cityId">
                    </el-option>
                </el-select>
            </li>
            <li>
                <label><span style="color: red">*</span>详细地址：</label>
                <el-input style="width: 370px;"
                        type="textarea"
                        :rows="5"
                        placeholder="请输入内容"
                        v-model="addressInfo.address">
                </el-input>
            </li>
            <li>
                <label>邮政编码：</label>
                <el-input style="width: 370px;" v-model="addressInfo.zipCode" placeholder="请输入内容"></el-input>
            </li>
            <li>
                <label><span style="color: red">*</span>收货人姓名：</label>
                <el-input style="width: 370px;" v-model="addressInfo.nickName" placeholder="请输入内容"></el-input>
            </li>
            <li>
                <label><span style="color: red">*</span>手机号码：</label>
                <el-input style="width: 370px;" v-model="addressInfo.phoneNo" placeholder="请输入内容"></el-input>
            </li>
            <li>
                <el-checkbox v-model="addressInfo.defaultFlagCk" class="allCheck" style="margin-left: 80px; line-height: 38px;font-size:12px;">设置为默认收货地址</el-checkbox>
            </li>
            <li style="margin-top: 28px;">
                <el-button @click="saveInfo" class="btnColorOrg" style="width: 90px; margin-left: 90px;">
                    保存
                </el-button>
            </li>
        </ul>
        <div>
            <div style="margin-top: 44px;height: 44px;background: rgba(1,77,203,0.05);width: 974px;line-height: 44px;">
                <i class="icon-tishi" style="margin-left: 16px;"></i>
                <label style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                    已保存{{addressList.length}}条地址，还能保存{{20-addressList.length}}条地址
                </label>
            </div>
            <el-table :data="addressList" style="margin-top: 20px" class="user-table">
                <el-table-column
                        label="收货人" prop="nickName"
                        align="center">
                </el-table-column>
                <el-table-column
                        label="所在地区"
                        align="center">
                    <template slot-scope="scope">
                        {{scope.row.provinceName}}{{scope.row.cityName}}
                    </template>
                </el-table-column>
                <el-table-column
                        label="详情地址" prop="address"
                        align="center">
                </el-table-column>
                <el-table-column
                        label="邮编" prop="zipCode"
                        align="center">
                </el-table-column>
                <el-table-column
                        label="电话/手机" prop="phoneNo"
                        align="center">
                </el-table-column>
                <el-table-column
                        label="操作"
                        align="center">
                    <template slot-scope="scope">
                        <el-link :underline="false" @click="upInfo(scope.row)">修改</el-link>/
                        <el-link :underline="false" @click="delInfo(scope.row.id)">删除</el-link>
                        <el-link :underline="false" @click="upInfoSt(scope.row.id)">设为默认</el-link>
                    </template>
                </el-table-column>
            </el-table>
            <el-row style="margin: 70px auto 100px;text-align: center;">
                <el-col>
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="queryParam.pageSize"
                            :current-page="queryParam.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {qryProvinceList,qryCityList} from "../../../api/admin/index";
    import {qryAddressManageList,saveAddressManage,upAddressManage,delAddressManage} from "../../../api/user/addressManage";
    export default {
        name: "addressManage",
        data() {
            return {
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                addressInfo:{
                    provinceId:null,
                    area:null,
                    address:null,
                    zipCode:null,
                    nickName:null,
                    phoneNo:null,
                    defaultFlagCk:false,
                    defaultFlag:null,
                    userId:this.$store.state.user.userInfo.id
                },
                addressList:[],
                pageTotal:0,
                queryParam:{
                    pageSize:5,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                }
            }
        },
        created(){
            this.qryProvinceList();
            this.qryAddressManageList();
        },
        methods:{
            qryProvinceList(){
                qryProvinceList().then(response => {
                    this.provinceList = response.data;
                });
            },
            qryCityList(val){
                this.addressInfo.area = null;
                qryCityList({"provinceId":val}).then(response => {
                    this.cityList = response.data;
                });
            },
            upInfo(obj){
                this.addressInfo = obj;
                this.addressInfo.area = obj.cityName;
                this.addressInfo.provinceId = obj.provinceName;
            },
            saveInfo(){
                if(this.addressInfo.provinceId == null || this.addressInfo.area == null){
                    this.$message.error("地址信息不能为空");
                    return;
                }
                if(this.addressInfo.address == null){
                    this.$message.error("地址详情不能为空");
                    return;
                }
                if(this.addressInfo.zipCode != null){
                    if(!(/^[1-9][0-9]{5}$/.test(this.addressInfo.zipCode))){
                        this.$message.error("邮政编码格式错误")
                        return;
                    }
                }
                if(this.addressInfo.nickName == null){
                    this.$message.error("昵称不能为空");
                    return;
                }
                if(!(/^1[3456789]\d{9}$/.test(this.addressInfo.phoneNo))){
                    this.$message.error('手机号格式错误！')
                    return;
                }

                if(undefined == this.addressInfo.id){
                    saveAddressManage(this.addressInfo).then(response=>{
                        this.$message({
                            message: '保存成功',
                            type: 'success'
                        });
                        this.resetInfo();
                        this.qryAddressManageList();
                    });
                }else{
                    upAddressManage(this.addressInfo).then(response=>{
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        this.resetInfo();
                        this.qryAddressManageList();
                    });
                }
            },
            resetInfo(){
                this.addressInfo={
                    provinceId:null,
                    area:null,
                    address:null,
                    zipCode:null,
                    nickName:null,
                    phoneNo:null,
                    defaultFlagCk:false,
                    defaultFlag:null,
                    userId:this.$store.state.user.userInfo.id
                };
            },
            upInfoSt(id){
                for(var i=0;i<this.addressList.length;i++){
                    if(this.addressList[i].defaultFlag==1){
                        upAddressManage({id:this.addressList[i].id,defaultFlag:2}).then(response=>{
                        })
                        break;
                    }
                }
                upAddressManage({id:id,defaultFlag:1}).then(response=>{
                    this.$message({
                        message: '设置成功',
                        type: 'success'
                    });
                    this.qryAddressManageList();
                })
            },
            delInfo(id){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    delAddressManage({id:id}).then(response=>{
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.qryAddressManageList();
                    });
                }).catch(() => {
                });
            },
            qryAddressManageList(){
                qryAddressManageList(this.queryParam).then(response=>{
                    this.addressList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.queryParam.curPage = val;
            }
        }
    }
</script>

<style scoped>
    .addressLi li{display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;margin-top: 20px;}
    .addressLi li label{
        font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);width: 90px;padding-left: 10px;
    }
    /deep/.el-button.el-button--default span {
        font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);
    }
    .icon-tishi{width: 18px;height: 18px;background: url("../../../images/user/tishi.png") no-repeat;display: inline-block;vertical-align: -5px;margin-right: 8px;}
</style>