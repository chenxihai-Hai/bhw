<template>
    <div class="accountAppeal">
        <div style="width: 539px;height: 352px;margin-left: 240px;margin-top: 79px">
            <el-menu default-active="0" class="el-menu-demo" mode="horizontal" @select="MenuSelect" style="margin: 0 auto 0;width: 308px">
                <el-menu-item index="0" style="width: 154px;text-align: center;">账号申诉</el-menu-item>
                <el-menu-item index="1" style="width: 154px;text-align: center;">申诉查询</el-menu-item>
            </el-menu>
            <el-row style="margin-top: 47px">
                <template v-if="active==0">
                    <el-col style="width:220px;height:282px;border:1px solid rgba(204,204,204,1);">
                        <h1 style="margin:40px 82px 48px;">
                            <img style="width: 62px;height: 62px" :src="require('@/images/pwd@3x.png')">
                        </h1>
                        <h2 style="margin:0 auto;width:160px;font-size:12px;font-weight:400;color:rgba(125,125,125,1);line-height:18px;text-align: center;">忘记了登录密码?<br>无法自助修改密码?</h2>
                        <h3 style="text-align: center;margin-top: 25px;">
                            <el-button class="btnColorOrg bt" @click="accountAppeal('11')">登录密码修改申诉</el-button>
                        </h3>
                    </el-col>
                    <el-col style="width:220px;height:282px;border:1px solid rgba(204,204,204,1);margin-left: 99px">
                        <h1 style="margin:40px 82px 48px;">
                            <img style="width: 62px;height: 62px" :src="require('@/images/shouji@3x.png')">
                        </h1>
                        <h2 style="margin:0 auto;width:160px;font-size:12px;font-weight:400;color:rgba(125,125,125,1);line-height:18px;text-align: center;">想修改绑定的手机号？<br>
                            线上验证条件自己完成不了？</h2>
                        <h3 style="text-align: center;margin-top: 25px;">
                            <el-button class="btnColorOrg bt" @click="accountAppeal('8')">手机修改申诉</el-button>
                        </h3>
                    </el-col>
                </template>
                <template v-else-if="active==1">
                </template>
            </el-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "accountAppeal",
        data(){
            return{
                active:'0'
            }
        },
        created(){
        },
        methods:{
            MenuSelect(index,indexPath){
                this.active = index;
            },
            accountAppeal(data){
                this.$emit('fun',data);
            }
        }
    }
</script>

<style scoped>
.btnColorOrg.bt{
    width:128px;
    height:37px;font-size:12px;padding: 0;border: none;
    border-radius:5px;
    font-weight:400;
}
</style>