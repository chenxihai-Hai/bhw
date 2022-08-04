<template>
    <div class="topWrap">
        <el-row type="flex" justify="space-between" align="middle">
            <el-col :span="12" style="padding-left: 70px">
                <el-button type="text" class="btnRefresh"><i class="icon-refresh"></i></el-button>
                <el-button type="text" class="btnTopSearch">搜索</el-button>
            </el-col>
            <el-col :span="12" style="padding-right: 53px;text-align:right;">
                <el-link><i class="icon-bell"></i></el-link>
                <el-dropdown @command="handleCommand" trigger="click" class="top-userMg">
                    <span class="el-dropdown-link">{{$store.getters.userName}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="a">个人信息</el-dropdown-item>
                        <el-dropdown-item command="b">切换账号</el-dropdown-item>
                        <el-dropdown-item command="c">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-link>前台首页</el-link>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {
        removeToken
    } from '../../util/auth'
    export default {
        name: "Topbar",
        data(){
            return{

            }
        },
        methods:{
            handleCommand(command) {
                if('c'==command){
                    this.outWeb();
                }
            },
            outWeb(){
                removeToken();
                this.$router.push({path:'/login'});
            }
        }
    }
</script>

<style scoped>
    .btnRefresh .icon-refresh{width: 13px;height: 13px;background: url("../../images/imgIcon/icon-top_shuaxin.png") no-repeat;display: block;}
    /deep/ .btnTopSearch.el-button--text{color: #B1B1B1;font-weight: 400;border: none; margin-left: 36px;}
    .topWrap /deep/ .el-link.el-link--default{color: #FFF;}
    .top-userMg{margin-right: 60px;}
    /deep/ .top-userMg.el-dropdown{color: #FFF;}
    .top-userMg /deep/ .el-icon--right{margin-left: 20px;}
    /deep/ .el-dropdown-menu__item{padding: 0 12px;}
</style>