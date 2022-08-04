<template>
    <div class="bankcardYz">
        <el-row type="flex" align="middle" class="div1">
            <el-col style="width: 100px;"><span ><i></i>当前位置：</span></el-col>
            <el-col :span="18"><el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/userCenter' }">用户中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{path:'/userCenter',query:{menuActiveFlag:'myPurse'}}">我的钱包</el-breadcrumb-item>
                <el-breadcrumb-item>账户申诉</el-breadcrumb-item>
            </el-breadcrumb></el-col>
        </el-row>
        <div style="margin: 0 auto;width: 445px">
            <el-steps :active="active" class="stepBox" style="margin-top: 58px;">
                <el-step title="身份认证" :class="active>1?'stepLineBox':''" ></el-step>
                <el-step title="设置操作" :class="active>1?'stepLineBox':''"  ></el-step>
                <el-step title="完成" :class="active>1?'stepLineBox':''" ></el-step>
            </el-steps>
            <div style="margin-top: 98px">
                <label style="width:91px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">设置新密码</label>
                <el-input v-model="newpwd" placeholder="请输入新密码" type="password" style="width:425px;height:54px;margin-top: 22px" show-password></el-input>
                <button class="btnColorOrg bt" >提交</button>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "bankcardYz",
        data(){
            return{
                newpwd:""
            }
        }
    }
</script>

<style scoped>
div.div1{
    width:574px;
    height:18px;
    font-size:18px;
    font-weight:normal;
    color:rgba(29,29,29,1);
}
.btnColorOrg.bt{
    width:425px;
    height:54px;
    margin-top: 41px;padding: 0;border: none;
}
.stepBox /deep/.is-finish>.is-text {
    border-radius: 50%;
    border: 2px solid;
    border-color:#F7490D;
}
.stepBox /deep/.is-finish>.is-text>.el-step__icon-inner {
    color: #F7490D;
}
.stepBox /deep/.el-step__head.is-finish.el-step__line{
    background: #F7490D;
}
.stepLineBox /deep/.el-step__head.is-finish>.el-step__line>.el-step__line-inner{
    border-color: #F7490D;
}
.stepBox /deep/.el-step__title.is-finish{
    color: #F7490D;
}
.stepBox /deep/.is-process>.el-step__icon.is-text {
    border-radius: 50%;
    border: 2px solid;
    border-color:#CCCCCC;
}
.stepBox /deep/.is-process>.is-text>.el-step__icon-inner {
    color: #CCCCCC;
}
.stepBox /deep/.el-step__head.is-process .el-step__line{
    background: #CCCCCC;
}
.stepBox /deep/ .el-step__title.is-process{
    color: #CCCCCC;
}
</style>