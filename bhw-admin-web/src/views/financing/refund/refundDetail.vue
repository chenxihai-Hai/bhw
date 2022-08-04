<template>
    <div>
        <el-row class="refund-det">
            <el-col :span="4" class="col-suc">
                <div class="col-suc-div">1</div>
                <div style="margin-left: 7px;">
                    买家申请退货退款
                </div>
            </el-col>
            <el-col :span="4" class="col-def" >
                <div class="col-def-div">2</div>
                <div style="margin-left: 7px;">
                    卖家处理退货申请
                </div>
            </el-col>
            <el-col :span="4"  class="col-def" >
                <div class="col-def-div">3</div>
                <div style="margin-left: 7px;">
                    买家退货
                </div>
            </el-col>
            <el-col :span="4"  class="col-def" >
                <div class="col-def-div">4</div>
                <div style="margin-left: 7px;">
                    退款完毕
                </div>
            </el-col>
        </el-row>
        <ul style="margin-top: 72px;margin-left: 34px;">
            <li style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">买家申请退货退款</li>
            <li style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(102,102,102,1);">申请退货退款时间：{{refundDetInfo.createTime}}</li>
            <li style="margin-top: 19px;"><el-divider></el-divider></li>
            <li>
                <label style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">申请退货退款总金额：</label>
                <label style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:#CA0000;">¥{{refundDetInfo.refundPrice}}</label>
            </li>
            <li style="margin-top: 47px; font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(111,111,111,1);">退回原付款地址</li>
        </ul>
        <!--<el-button type="primary" style="width: 216px;margin-top: 75px;margin-left: 311px;" @click="backList">返回</el-button>-->
    </div>
</template>

<script>
    export default {
        name: "refundDetail",
        props:["refundDetInfo"],
        methods:{
            backList(){
                this.$emit("backlist-event",'');
            }
        }
    }
</script>

<style scoped>
    .col-suc{
        background: url("../../../images/refund/jindu.png") center no-repeat;
        background-size: contain;
        width: 270px;
        height: 46px;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(255,255,255,1);
        line-height: 46px;
        text-align: center;
        display: flex;
    }
    .col-suc-div{
        border-radius: 50%;
        color: red;
        width: 20px;
        height: 20px;
        background: white;
        margin-top: 12px;
        line-height: 20px;
        margin-left: 66px;
    }
    .col-def{
        background: url("../../../images/refund/jindu2.png") center no-repeat;
        background-size: contain;
        width: 270px;
        height: 46px;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(255,255,255,1);
        line-height: 46px;
        text-align: center;
        display: flex;
    }
    .col-def-div{
        border-radius: 50%;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(204,204,204,1);
        width: 20px;
        height: 20px;
        background: white;
        margin-top: 12px;
        line-height: 20px;
        margin-left: 66px;
    }
</style>