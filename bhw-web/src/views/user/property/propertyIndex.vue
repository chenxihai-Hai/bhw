<template>
    <div class="propertyIndex">
        <el-row><h2 class="useTitle">财税服务</h2></el-row>
        <el-row style="padding-left: 20px;">
            <el-menu :default-active="tabPosition" class="useMenu smallMenu" mode="horizontal" @select="iconToggle">
                <el-menu-item index="0">上传发布</el-menu-item>
                <el-menu-item index="1">供需管理</el-menu-item>
                <el-menu-item index="2">谁联系了我</el-menu-item>
            </el-menu>
        </el-row>
        <div style="margin-top: 50px;">
            <component :is="subMenuComponents[iconToggleFlag]" :serverType="serverType"  :upInfoObj="upInfoObj"  v-on:upInfoAc-event="upInfoAc"  v-on:oprVisibleId-event="oprV"></component>
        </div>
    </div>
</template>

<script>
    import uploading from "../../../views/user/property/Uploading";
    import demandAdmin from "../../../views/user/property/demandAdmin";
    import relation from "../../../views/user/property/relation";

    export default {
        name: "propertyIndex",
        components: {
            "uploading": uploading,
            "demandAdmin": demandAdmin,
            "relation": relation
        },
        data() {
            return {
                upInfoObj:{},
                serverType:14,
                tabPosition: '0',
                subMenuComponents: ["uploading", "demandAdmin", "relation"],
                iconToggleFlag: 0,

            };
        },
        methods: {
            iconToggle(val) {
                this.iconToggleFlag = val;
                this.tabPosition = val;
            },
            oprV(){
                this.$emit("oprVisibleId-event",1);
            },
            upInfoAc(obj){
                this.upInfoObj = obj;
                this.tabPosition = '0';
                this.iconToggleFlag = 0;
            }
        },
    }
</script>

<style>
    .el-radio-button__inner {
        padding-top: 20px;
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight: 400;
        color: rgba(29, 29, 29, 1);
        width: 243px;
        height: 59px;
        background: rgba(255, 255, 255, 1);
        text-align: center;
    }

    .el-radio-button__orig-radio:checked + .el-radio-button__inner {
        width: 243px;
        height: 59px;
        background: rgba(11, 109, 185, 1);
        border: 0px solid rgba(125, 125, 125, 1);
        box-shadow: 0px 8px 7px 0px rgba(0, 0, 0, 0.35);
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight: 400;
        color: rgba(255, 255, 255, 1);
    }

    .propertyIndex {
        padding: 0 17px;
    }
</style>