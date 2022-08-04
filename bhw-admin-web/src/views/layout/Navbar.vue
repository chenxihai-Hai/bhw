<template>
    <div class="navbar-main navbar" ref="navHeight">
        <el-button type="text" :class="'showOrHideMenu ' + [isCollapse==true?'hideMenu':'']" @click="btnShowOrHide"><i class="icon-open"></i></el-button>
        <div class="logoWrap" v-show="!isCollapse">
            <el-avatar :size="71" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            <p style="margin-top: 15px;color:#FFF;">OMS-admin v0.1</p>
        </div>
        <el-menu  class="el-menu-vertical-demo" default-active="/home" @open="handleOpen" :unique-opened="true" @close="handleClose"
                  :collapse="isCollapse" :style="isCollapse==true?'padding-top:173px':''" @select="handleSelect" >
            <el-submenu  v-for="item in routes" :key="item.code==undefined?item.id+'':item.code" :index="item.href==undefined?item.id+'':item.href">
                <template v-if="item.code=='home'">
                    <template slot="title">
                        <i class="menu-icon"></i>
                        <router-link to="/home" tag="span"><span class="iconfont"></span>{{item.title}}</router-link>
                    </template>
                </template>
                <template v-else>
                    <template slot="title">
                        <div class="menu-icon"><i :class="item.icon"></i></div>
                        <span slot="title">{{item.title}}</span>
                    </template>
                    <el-submenu v-for="ch in item.children" :key="ch.code==undefined?ch.id:ch.code" :index="ch.href==undefined?ch.id+'':ch.href" >
                        <template slot="title" >
                            <span slot="title">{{ch.title}}</span>
                        </template>
                        <el-menu-item v-for="sch in ch.children" :key="sch.code==undefined?sch.id:sch.code" :index="sch.href==undefined?ch.id+'':sch.href" class="sonMenu" >
                            {{sch.title}}
                        </el-menu-item>
                    </el-submenu>
                   <!-- <el-menu-item-group>
                        <el-menu-item v-for="ch in item.children" :key="ch.code==undefined?ch.id:ch.code" :index="ch.href==undefined?ch.id+'':ch.href" class="sonMenu" >
                            {{ch.title}}
                            <el-menu-item v-for="sch in ch.children" :key="sch.code==undefined?sch.id:sch.code" :index="sch.href==undefined?sch.id+'':sch.href">
                                {{sch.title}}
                            </el-menu-item>
                        </el-menu-item>
                    </el-menu-item-group>-->
                   <!-- <el-menu-item-group>
                        <el-menu-item v-for="ch in item.children" :key="ch.code==undefined?ch.id:ch.code" :index="ch.href==undefined?ch.id+'':ch.href" class="sonMenu" >
                            {{ch.title}}
                        </el-menu-item>
                    </el-menu-item-group>-->
                </template>
            </el-submenu>
        </el-menu>
    </div>
</template>
<style scoped>
    .showOrHideMenu{position: absolute;top: 1px;left: 256px;animation:open 0.2s linear;z-index: 999;}
    .showOrHideMenu.hideMenu{left: 84px;animation:hide 0.2s linear;}
    @keyframes hide {
        0% { left: 256px;}
        100% { left: 84px;}
    }
    @keyframes open {
        0% {left: 84px;}
        100% {left: 256px;}
    }
    .showOrHideMenu .icon-open{width: 15px;height: 11px;background: url("../../images/imgIcon/icon-menu_open.png") no-repeat;display: block;}
    .navbar{background: linear-gradient(180deg,#141414 0%,#333 100%);height: 100%;}
    /deep/ .el-menu-vertical-demo:not(.el-menu--collapse) {width: 237px;}
    /deep/ .el-menu-vertical-demo.el-menu,.el-menu-vertical-demo /deep/ .el-menu{background: transparent;border-right:none;}
    .el-menu-vertical-demo /deep/ .el-submenu__title,.el-menu-vertical-demo /deep/ .el-submenu__title i,.el-menu-vertical-demo /deep/ .el-menu-item{color: #FFF;}
    .el-menu-vertical-demo /deep/ .el-menu-item, .el-menu-vertical-demo /deep/ .el-submenu__title{height: 44px;line-height: 44px;}
    .el-menu-vertical-demo /deep/ .el-submenu__title:focus,.el-menu-vertical-demo /deep/  .el-submenu__title:hover{background: transparent;}
    .el-menu-vertical-demo /deep/ .el-menu-item:focus,.el-menu-vertical-demo /deep/ .el-menu-item:hover{background: #024ECC;}
    .el-menu-vertical-demo /deep/ .el-menu-item-group__title{padding: 0;padding-left: 20px;}
    .el-menu-vertical-demo /deep/ .el-menu-item.is-active{background: #024ECC;color: #FFF;}
    /deep/ .sonMenu.el-menu-item{padding-left: 58px!important;}
    .logoWrap{padding: 48px 0 20px;text-align: center;}
    .logoWrap /deep/ .el-avatar{background: #FFF;}
</style>
<script>
    export default {
        name: "navbar",
        data() {
            return {
                isCollapse: false,
                clientHeight:''
            }
        },
        props: {
            routes: {
                type: Array
            }
        },
        mounted() {
            this.clientHeight = `${document.documentElement.clientHeight}`;
            this.$refs.navHeight.style.minHeight = this.clientHeight+'px';
        },
        methods: {
            handleOpen(key, keyPath) {
                if(key.indexOf("/")>-1){
                    this.$router.push({path:key});
                }
            },
            handleClose(key, keyPath) {
            },
            btnShowOrHide(){
                this.isCollapse = !this.isCollapse;
            },
            handleSelect(key, keyPath) {
                this.$router.push({path:key});
            }
        }
    };
</script>
