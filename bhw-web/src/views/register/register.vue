<template>
    <div class="register">
        <TopBar></TopBar>
        <hr class="topLine">
        <div style="width: 1243px;border:1px solid rgba(204,204,204,1);margin: 20px auto 150px;">
            <el-row class="choiceNav">
                <el-col :span="2">
                    <el-link :underline="false" @click="personalClick()" style="width:78px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;">个人注册</el-link>
                </el-col>
                <el-col :span="2">
                    <el-link :underline="false" @click="enterpriseClick()" style="width:78px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;">企业注册</el-link>
                </el-col>
            </el-row>
            <hr style="margin-left:12px;width:617px;height:1px;background: #CCC;"/>
            <el-row style="position: relative;">
                 <el-col style="width: 754px;padding-top: 60px;">
                     <template v-if="flag">
                         <Personal></Personal>
                     </template>
                     <template v-else>
                         <Enterprise></Enterprise>
                     </template>
                 </el-col>
                 <hr style="width: 1px;height: 350px;background: #CCC;position:absolute;top: 50px;left: 755px;"/>
                 <el-col style="width: 480px;padding-top: 60px;">
                    <el-row style="padding:0 70px;">
                        <el-col >已有账号</el-col>
                        <el-col style="margin-top: 30px">
                            <router-link to="/viplogin">
                                <el-button class="btnColorBlue" style=" width:206px;height:46px;border:none;padding: 0;" >登录</el-button>
                            </router-link>
                        </el-col>
                        <el-col style="margin-top: 40px">
                            <h></h>
                        </el-col>
                    </el-row>
                 </el-col>
            </el-row>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import Enterprise from "../../views/register/enterpriseRegister";
    import Personal from "../../views/register/personalRegister";
    import TopBar from "../../views/layout/TopBar";
    import FootBar from "../../views/layout/FootBar";
    export default {
        components: {
            Enterprise,
            Personal,TopBar,FootBar},
        name: "register",
        data(){
            return{
               flag:true

            }
        },
        created(){

        },
        methods:{
            personalClick(){
                this.flag=true;
            },
            enterpriseClick(){
                this.flag=false;
            }
        }
    }
</script>

<style scoped>
    .topLine{width: 100%; height:1px;background:rgba(204,204,204,0.2);box-shadow:0px 2px 4px 1px rgba(8,1,3,0.3);}
    .choiceNav{margin: 38px 0 46px 22px;position: relative;}
    .choiceNav::before{content: "";width: 3px;height: 29px;background: #CA0000;position: absolute;top:-5px;left: -22px;}

</style>