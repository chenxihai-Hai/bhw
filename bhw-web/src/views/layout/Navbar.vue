<template>
    <div class="navbar-main navbar">
        <el-menu :default-active="activePathName"
                 class="el-topMenu-demo" mode="horizontal" @select="handleSelect">
            <template v-for="item in routes">
                <template v-if="item.code=='allPro' ">
                    <el-submenu v-if="$route.path!='/home'" :index="item.href" :key="item.href" class="topmenu"
                                style="width: 246px;position: relative;text-align: center;" :popper-append-to-body="false">
                        <template slot="title"><span slot="title">{{item.title}}</span></template>
                        <el-menu-item v-for="(ch,chIndex) in item.children" :key="ch.name" style="width: 246px;"  :index="ch.href" class="sonMenuOther">
                            <div class="sonInfo" style="height: calc(100% - 1px);">
                                <h5 class="mnu-ti">{{ch.title}}</h5>
                                <div class="sonLink">
                                    <p v-for="markItem in ch.mark" :key="markItem.name+'_'+ch.title"><el-link class="mnu-mark" @click="btClick(markItem.path,markItem.param)">{{markItem.name}}</el-link><span style="font-size: 12px;vertical-align: 5px;padding: 0 2px;color: #FFF;">{{markItem.lineM}}</span></p>
                                </div>
                            </div>
                            <div v-if="chIndex<2" style="border-bottom: 1px solid #DCDCDC;width: 92%;margin: auto;"></div>
                        </el-menu-item>
                    </el-submenu>
                    <el-menu-item :index="item.href" :key="item.href" class="topmenu otherMenu" style="position: relative;width: 246px;text-align: center;"  v-else>
                        {{item.title}}
                        <template>
                            <div class="sonMenu" @mouseenter="changeShow($event)" @mouseout="changeHide($event)" v-show="showSonMenu">
                                <el-row v-for="(ch,chIndex) in item.children" :key="ch.name"  :index="ch.href" style="height: 168px;">
                                    <div class="sonInfo">
                                        <h5 class="mnu-ti">{{ch.title}}</h5>
                                        <div class="sonLink">
                                            <p v-for="markItem in ch.mark" :key="markItem.name+'_'+ch.title"><el-link class="mnu-mark" @click="btClick(markItem.path,markItem.param)">{{markItem.name}}</el-link><span style="font-size: 12px;vertical-align: 5px;padding: 0 2px;color: #FFF;">{{markItem.lineM}}</span></p>
                                        </div>
                                    </div>
                                    <div v-if="chIndex<2" style="border-bottom: 1px solid #DCDCDC;width: 92%;margin: auto;"></div>
                                </el-row>
                            </div>
                        </template>
                    </el-menu-item>
                </template>
                <template v-else>
                    <el-menu-item :index="item.href" :key="item.code" style="float: right;margin-right: -28px;" class="topmenu otherMenu" v-if="item.title=='增值服务'">
                        {{item.title}}
                    </el-menu-item>
                    <el-menu-item :index="item.href" :key="item.code"  class="topmenu otherMenu" v-else>
                        {{item.title}}
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
        <div class="line"></div>
    </div>
</template>
<script>
    import {
        getToken
    } from '../../utils/auth';
    export default {
        name: "navbar",
        data() {
            return {
                activePathName:'/allPro',
                activeIndex:'',
                isCollapse:false,
                defaultOpeneds:['/allPro'],
                showSonMenu:true,
                showSonMenuTemp:false,
            }
        },
        props: {
            routes: {
                type: Array
            }
        },
        watch:{
            '$route.path':function (newVal,oldVal) {
                if(newVal!='/home'){
                    console.log('newVal',newVal);
                    this.activePathName = newVal;
                    if(newVal=='/listedContent'){
                        this.activePathName = '/listedTransaction';
                    }else if(newVal=='/middlemanDetail'){
                        this.activePathName = '/middleman';
                    }else if(newVal=='/kjsc/requireDetail'){
                        this.activePathName = '/kjsc';
                    }
                    if(newVal.lastIndexOf("/")!=0){
                        this.activePathName = newVal.substring(0,newVal.lastIndexOf("/"));
                    }
                    this.defaultOpeneds=[];
                    this.showSonMenu = false;
                }else{
                    this.activePathName = '/allPro';
                    this.showSonMenu = true;
                }
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                if(key=='/allPro'){
                    return;
                }
                if(key=='spMarket'){
                    const url = window.location.href;
                    const index = url.lastIndexOf("\/");
                    const str = url.substring(index + 1,url.length);
                    // window.open("https://cloud.ejdrone.com/static/screen.html");
                    window.open("http://www.traup.net/largeScreen/index.html");
                }else{
                    if(key){
                        this.$router.push({path:key});
                    }
                }
            },
            btClick(urlPath,param){
                if(urlPath=='userCenter') {
                    if(getToken()){
                        this.$router.push({path:"/userCenter"});
                    }else{
                        this.$message.error('您还未登录,请登录后再操作！');
                    }
                }else{
                    this.activeIndex = '/'+urlPath;
                    if(param){
                        this.$router.push({path:"/"+urlPath,query:{chengguoActiveName:1}});
                    }else {
                        this.$router.push({path:"/"+urlPath});
                    }
                }
            },
            changeShow:function () {
                this.showSonMenu = true;
            },
            changeHide:function(e) {
            },
        }
    };
</script>
<style scoped>
    /deep/ .el-menu--popup-bottom-start{margin-top:3px;background: #4A5767;width: 178px;}
    /deep/ .el-menu--horizontal .el-menu .el-menu-item,/deep/ .el-menu--horizontal .el-menu .el-submenu__title{background: none; height: auto;line-height: inherit;}
    /deep/ .el-menu--horizontal > .topmenu.el-submenu.is-active .el-submenu__title{border-bottom: 2px solid transparent;}
    /deep/ el-menu--collapse .el-menu .el-submenu,/deep/ .el-menu--popup{min-width: auto;}
    /deep/ .el-menu--popup{padding: 0;}
    /deep/ .el-menu--horizontal > .topmenu.el-menu-item.is-active{background-color:#024ECC;color: #FFF;}
    /deep/ .el-menu--popup-bottom-start{width: 233px;}
    .topmenu /deep/ .el-menu--horizontal{position: absolute;top: 41px!important;z-index: 3000;}
    .topmenu /deep/ .el-menu{width: 246px; background: linear-gradient(135deg,rgba(84,48,252,1) 0%,rgba(127,21,181,1) 100%);}
    .topmenu /deep/ .el-menu li:nth-child(2) h5{margin-bottom: 20px;}
    .topmenu /deep/ .el-menu li:nth-child(2) .sonLink{padding: 0 11px;justify-content: flex-start;-webkit-justify-content: flex-start;}
    /deep/ .topmenu .sonMenuOther{height: 168px!important;}
    /*/deep/ .sonMenu .sonLink .mnu-mark:hover,/deep/ .sonMenuOther .sonLink .mnu-mark:hover{color: #ca0000!important;transform: scale(1.2);font-weight:normal;}*/
</style>
