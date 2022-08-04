<template>
    <div class="detailWrap orderDetail" style="padding-bottom: 80px;">
        <!--<div style="width:1247px;height:241px;background:rgb(193, 224, 234);margin: 50px auto;">
            <el-row justify="center" type="flex" style="padding-top: 68px;">
                <el-col :span="4">
                    <img :src="require('@/images/pay/jurassic_warn.png')"/>
                </el-col>
                <el-col :span="6" style="margin-top: 45px;">
                    <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#1D1D1D;">您已付款成功！</span>
                    <router-link :to="{path:'/userCenter'}" tag="a" class="routerLink-a" style="font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:#CA0000;">点击查看我的订单</router-link>
                </el-col>
            </el-row>
        </div>-->
    </div>
</template>

<script>
    export default {
        name: "return_url",
        data() {
            return {}
        },
        created(){
            this.$router.push({path:"/orderPay",query:{orderPayFlag:false}});
            console.log('this.$route.query',this.$route.query)
        }
    }
</script>

<style scoped>

</style>