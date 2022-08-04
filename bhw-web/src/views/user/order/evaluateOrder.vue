<template>
    <div>
        <el-row style="padding-top: 20px;">
            <el-col>
                <div style="text-align: center;">
                    <label style="font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);">
                        评价订单
                    </label>
                    <p style="margin-top:22px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(147,147,147,1);">订单号：{{orderDetailObj.orderId}} {{orderDetailObj.createTime.replace('.0','')}}</p>
                </div>
            </el-col>
        </el-row>
        <el-row style="margin-top: 90px;">
            <el-col :span="10" style="margin-left: 30px;">
                <img style="cursor: pointer;width: 373px;height: 307px;"
                          :src="orderDetailObj.itemLogo"
                          fit="fit" v-if="orderDetailObj.itemLogo!=''">
                <img style="cursor: pointer;width: 373px;height: 307px;"
                          :src="require('../../../images/img-zhuanl.png')"
                          fit="fit" v-else>
            </el-col>
            <el-col :span="10" style="margin-left: 100px;">
                <ul>
                    <li style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(250,181,39,1);"><i class="el-icon-warning"></i> 请至少填写一件产品的评价</li>
                    <li style="margin-top: 28px;display: flex;">
                        <label style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);margin-right: 27px;">产品评分</label>
                        <el-rate
                                v-model="evalueteInfo.productGrade"
                                :texts="['2分','4分','6分','8分','10分']"
                                show-text>
                        </el-rate>
                    </li>
                    <li style="margin-top: 19px;margin-left: 90px;">
                        <template v-for="(tag,index) in tagsArr">
                            <el-button @click="selectTag(index)" :key="tag" style="margin-top: 5px;">{{tag}}</el-button>
                        </template>
                    </li>
                    <li style="margin-top: 29px;display: flex;">
                        <label style="width: 80px; font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);margin-right: 27px;">评价晒单</label>
                        <el-input
                                type="textarea"
                                :rows="5"
                                placeholder="请输入内容"
                                v-model="evalueteInfo.remark">
                        </el-input>
                    </li>
                    <li style="display: flex;margin-left: 90px;margin-top: 11px;">
                        <el-upload
                                action="/api/serverManage/fileUpload/upload"
                                list-type="picture-card"
                                :on-preview="handlePictureCardPreview"
                                :on-remove="handleRemove"
                                :on-success="handleMoreSuccess"
                                :limit="5"
                                :file-list="fileList"
                        >
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <label style="line-height: 55px;margin-left:20px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(173,173,173,1);">共{{fileNumer}}张,还能上传{{fileNumerTotol}}张</label>

                    </li>
                    <li style="margin-top: 53px;margin-left: 90px;display: flex;">
                        <div @click="sendEvaluate" style="cursor: pointer; text-align: center; line-height:38px;width:216px;height:39px;background:rgba(2,78,204,1);border-radius:5px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,255,255,1);" v-if=" undefined== evalueteInfo.id">发表</div>

                        <el-button class="btnColorOrg" style="border-color:rgba(250,150,59,1);" v-if=" undefined!= evalueteInfo.id" type="primary" @click="sendEvaluate">修改评价</el-button>
                        <el-button class="btnColorBlue" v-if=" undefined!= evalueteInfo.id" type="primary" @click="delEvaluate">删除评价</el-button>

                     <!--   <div @click="sendEvaluate" style="cursor: pointer; text-align: center; line-height:38px;width:216px;height:39px;background:rgba(2,78,204,1);border-radius:5px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,255,255,1);" v-if=" undefined!= evalueteInfo.id">修改评价</div>
                        <div @click="delEvaluate" style="cursor: pointer; text-align: center; line-height:38px;width:216px;height:39px;background:rgba(2,78,204,1);border-radius:5px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,255,255,1);" v-if=" undefined!= evalueteInfo.id">删除评价</div>-->
                        <el-checkbox v-model="evalueteInfo.checked" style="line-height: 38px;margin-left: 28px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(159,159,159,1);">匿名评价</el-checkbox>
                    </li>
                </ul>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {saveEvaluateOrder,qryEvaluateOrderList,delEvaluateOrder,upEvaluateOrder} from "../../../api/user/evaluateOrder";
    export default {
        name: "evaluateOrder",
        data() {
            return {
                orderDetailObj:{},
                evalueteInfo:{
                    productGrade:0,
                    remark:'',
                    checked:false,
                    productId:'',
                    orderId:'',
                    userId:this.$store.state.user.userInfo.id,
                    imgs:'',
                    tags:''
                },
                fileList:[],
                moreFile:[],
                fileNumer:0,
                fileNumerTotol:5,
                tagsArr:["与描述一致","提供的资料详细","效率高","服务态度好"]
            }
        },
        created(){
            if(this.$route.query.orderDetailObj) {
                this.orderDetailObj = this.$route.query.orderDetailObj;
                this.evalueteInfo.orderId = this.orderDetailObj.orderId;
                this.evalueteInfo.productId = this.orderDetailObj.productId;
                this.qryEvaluateOrderList();
            }
        },
        methods: {
            selectTag(val){
                this.evalueteInfo.tags = val;
            },
            qryEvaluateOrderList(){
                this.fileList = [];
                qryEvaluateOrderList({orderId:this.orderDetailObj.orderId,productId:this.orderDetailObj.productId}).then(response=>{
                    if(response.data.length>0){
                        this.evalueteInfo = response.data[0];
                        if( this.evalueteInfo.imgs){
                            var sp = this.evalueteInfo.imgs.split(";");
                            for(var i=0;i<sp.length;i++){
                                if(sp[i]!='' ){
                                    this.fileList.push({url:sp[i]});
                                }
                            }
                            this.fileNumer = this.fileList.length;
                        }
                    }
                });
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handleMoreSuccess(res, file){
                this.fileNumer++;
                this.fileNumerTotol--;
                this.moreFile[file.url] = res.data;
            },
            handlePictureCardPreview(file) {
            },
            delEvaluate(){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    delEvaluateOrder({id:this.evalueteInfo.id}).then(response=>{
                        this.$router.push({path:"/userCenter"});
                    });
                });

            },
            sendEvaluate(){
                for(var p in this.moreFile){
                    if(this.moreFile[p]!='' && null != this.moreFile[p] ){
                        var ig = this.moreFile[p];
                        if(null == ig || ''==ig){
                            continue;
                        }
                        if(ig.indexOf("-zip")>-1){
                            ig = ig.replace('-zip','');
                        }
                        this.evalueteInfo.imgs = this.evalueteInfo.imgs+ig+";";
                    }
                }
                if(this.evalueteInfo.productGrade==null && this.evalueteInfo.tags==null){
                    this.$message({
                        message: '请至少填写一件产品的评价',
                        type: 'warning'
                    });
                    return;
                }
                if(undefined == this.evalueteInfo.id){
                    saveEvaluateOrder(this.evalueteInfo).then(response=>{
                        this.$message({
                            message: '发表评价成功',
                            type: 'success'
                        });
                        this.$router.push({path:"/userCenter"});
                    });
                }else{
                    this.evalueteInfo.createTime = null;
                    this.evalueteInfo.updateTime = null;
                    upEvaluateOrder(this.evalueteInfo).then(response=>{
                        this.$message({
                            message: '修改评价成功',
                            type: 'success'
                        });
                        this.$router.push({path:"/userCenter"});
                    });
                }
            }
        }
    }
</script>

<style scoped>
    /deep/.el-upload--picture-card {
        background-color: #fbfdff;
        border: 1px dashed #c0ccda;
        border-radius: 6px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        width: 48px;
        height: 48px;
        line-height: 55px;
        vertical-align: top;
    }
    /deep/.el-upload-list--picture-card .el-upload-list__item {
        overflow: hidden;
        background-color: #fff;
        border: 1px solid #c0ccda;
        border-radius: 6px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        width: 48px;
        height: 48px;
        margin: 0 8px 8px 0;
        display: inline-block;
    }
</style>