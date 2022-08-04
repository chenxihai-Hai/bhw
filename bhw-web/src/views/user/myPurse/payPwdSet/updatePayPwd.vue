<template>
    <div class="updatePayPwd">
        <div style="width:980px;height:52px;background:rgba(248,255,208,1);margin:0 auto;padding-top: 17px;padding-left: 27px">
            <span style="width:110px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">
                修改支付密码
            </span>
            <span>  您正在为为账户 <span style="color: #CA0000">{{username}}</span> 修改支付密码，请选择修改方式</span>
        </div>
        <div style="margin-top: 59px;margin-left: 27px">
            <el-row>
                <el-col :span="8">
                    <p class="p1">通过支付密码＋手机修改</p>
                    <p class="p2">如记得支付密码，手机可接收短信验证码，请选择此
                        方式</p>
                    <el-button class="btnColorOrg bt" @click="change('5')">立即修改</el-button>
                </el-col>
                <el-col :span="8" style="margin-left: 122px">
                    <p class="p1">通过手机＋银行卡修改</p>
                    <p class="p2">如忘记支付密码，手机可接收短信验证码，记得银行
                        卡信息，请选择此方式</p>
                    <el-button class="btnColorOrg bt" @click="change('6')">立即修改</el-button>
                </el-col>
            </el-row>
        </div>
        <hr style="width:980px;height:1px;border:1px solid rgba(204,204,204,1);margin-top: 47px"/>
        <div style="margin-top: 41px;margin-left: 27px">
            <p class="p1">通过人工服务</p>
            <p class="p2">如无法自助修改，请点击<el-link type="danger" @click="change('7')">账号申诉</el-link>审核客服协助</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "updatePayPwd",
    data(){
        return{
            username:"18473434527"
        }
    },
    created(){
    },
    methods:{
       change(data){
           this.$emit('fun',data);
       }
    }
    }
</script>

<style scoped>
p.p1{
    width:200px;
    height:14px;
    font-size:14px;
    font-family:'Microsoft YaHei';
    font-weight:normal;
    color:rgba(29,29,29,1);
}
p.p2{
    width:350px;
    height:25px;
    font-size:12px;
    font-family:'Microsoft YaHei';
    font-weight:400;
    color:rgba(125,125,125,1);
    margin-top: 25px;
}
.btnColorOrg.bt{
    width:80px;
    height:31px;
    border-radius:5px;
    margin-top: 31px;
    border: none;padding: 0;
}
</style>