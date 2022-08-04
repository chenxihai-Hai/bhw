<template>
    <div class="middlemanIndex">
        <div><img src="../../images/middleman/jinjiren.png" width="100%"></div>
        <div style="margin: 152px auto;width: 1240px;position: relative;">
            <el-row>
                <el-col style="width: 128px;">
                    <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleBrokerClick">
                        <el-tab-pane label="知识产权" name="1"></el-tab-pane>
                        <el-tab-pane label="财税服务" name="2"></el-tab-pane>
                        <el-tab-pane label="科技咨询" name="3"></el-tab-pane>
                        <el-tab-pane label="科技金融" name="4"></el-tab-pane>
                        <el-tab-pane label="法律服务" name="5"></el-tab-pane>
                        <el-tab-pane label="技术合同登记" name="6"></el-tab-pane>
                        <el-tab-pane label="成果认定" name="7"></el-tab-pane>
                        <el-tab-pane label="商务代写" name="8"></el-tab-pane>
                        <el-tab-pane label="策划设计" name="9"></el-tab-pane>
                        <el-tab-pane label="技术开发" name="10"></el-tab-pane>
                        <el-tab-pane label="教育培训" name="11"></el-tab-pane>
                        <el-tab-pane label="投资孵化" name="12"></el-tab-pane>
                        <el-tab-pane label="其他" name="13"></el-tab-pane>
                    </el-tabs>
                </el-col>
                <el-col style="width: calc(100% - 128px);">
                    <el-row style="padding-left:24px;" class="manInfo">
                        <template v-for="temp in manList">
                            <el-col class="cq" :key="temp.id" style="width: 260px;">
                                <div class="jinjiren">
                                    <router-link class="jieshao" :to="{path:'/middlemanDetail',query:{detailInfo:temp}}">
                                        <img :src="temp.photo" class="photo">
                                    </router-link>
                                    <p class="name">{{temp.name}}</p>
                                    <p class="chanquan">{{typeName}}</p>
                                    <nav class="xian"></nav>
                                    <el-button round class="lianxi" @click="sendMsg(temp.id)">联系Ta
                                    </el-button>
                                </div>
                            </el-col>
                        </template>
                    </el-row>
                    <div style="margin :0px auto;text-align: center;" v-if="manList.length>0">
                        <el-pagination
                                background
                                layout="prev, pager, next"
                                :page-size="queryParam.pageSize"
                                :current-page="queryParam.curPage"
                                @current-change="currentChange"
                                :total="8" class="pageBox">
                        </el-pagination>
                    </div>
                    <div class="chenggong" v-show="liuyanchenggong">
                        <nav class="chenggongphoto"></nav>
                        <span style="position: relative;left: 66px;bottom: 22px;font-size:16px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(202,0,0,1)">留言成功</span>
                    </div>
                    <div class="dialogSmall" v-show="dialogSmallShow">
                        <h3>请你留言</h3>
                        <el-button type="text" class="el-icon-plus" @click="showDialog"></el-button>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-dialog :visible.sync="dialogFormVisible" class="dialogMsg">
            <div slot="title" >
                <span>请你留言</span>
                <el-button type="text" class="el-icon-minus" @click="minDialog"></el-button>
            </div>
            <el-form>
                <el-input type="textarea" v-model="leaveMsg.content" :rows="4" placeholder="请在此输入留言内容，我们会尽快与您联系。（必填）"
                          class="neirong"></el-input>
                <el-input style="margin-top: 27px;" placeholder="姓名（必填）" v-model="leaveMsg.userName" prefix-icon="el-icon-search"
                          class="xingming">
                </el-input>
                <el-input placeholder="电话（必填）" v-model="leaveMsg.phoneNo" prefix-icon="el-icon-search" class="dianhua">
                </el-input>
                <el-input placeholder="需求（必填）" v-model="leaveMsg.requireMsg" prefix-icon="el-icon-search" class="xuqiu">
                </el-input>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitMsg"
                           style="width:77px;background:linear-gradient(90deg,#f74a0d 0%,#fa963d 100%)">发送
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {
        getUserManagerReq,
        coreServerReq,
        getNewsManager,
        getListingExpertReq,
    } from "../../api/login";
    import {
        saveBrokerLeaveMsg
    } from "../../api/admin/index";
    import {queryManList} from "../../api/middleman/middelman";
    import {saveServerAdvisory} from "../../api/admin/index";
    import FootBar from "../../views/layout/FootBar";
    import TopBar from "../../views/layout/TopBar";

    export default {
        name: "middlemanIndex",
        components: {
            TopBar,
            FootBar,
        },
        data() {
            return {
                tabPosition: 'left',
                activeName:'1',
                pageTotal: 1,
                queryParam: {pageSize: 8,curPage: 1,borkerId: "1",status:3},
                manList:[],
                typeName:'知识产权经纪人',
                dialogTableVisible: false,
                dialogFormVisible: false,
                liuyanchenggong: false,
                dialogSmallShow:false,
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                formLabelWidth: '120px',
                leaveMsg: {
                    brokerId: null,
                    content: null,
                    userName: null,
                    phoneNo: null,
                    requireMsg: null
                },
            };
        },
        created() {
            this.queryData();
        },
        methods: {
            handleBrokerClick(tab) {
                this.typeName = tab.label+"经纪人";
                this.queryParam.borkerId = tab.name;
                queryManList(this.queryParam).then(response => {
                    this.manList = response.data;
                    this.pageTotal = response.page.totalPage * 10;
                });
            },
            sendMsg(id) {
                this.leaveMsg.brokerId = id;
                this.dialogFormVisible = true;
            },
            queryData(){
                queryManList(this.queryParam).then(response => {
                    this.manList = response.data;
                    this.pageTotal = response.page.totalPage * 10;
                });
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.queryData();
            },
            submitMsg() {
                if (null == this.leaveMsg.userName || this.leaveMsg.userName.trim() == '') {
                    this.$message.error('用户姓名不能为空！');
                    return;
                }
                if (null == this.leaveMsg.phoneNo || this.leaveMsg.phoneNo.trim() == '') {
                    this.$message.error('手机号码不能为空！');
                    return;
                }
                if (!(/^1[3456789]\d{9}$/.test(this.leaveMsg.phoneNo))) {
                    this.$message.error('手机号格式错误！')
                    return;
                }
                if (null == this.leaveMsg.requireMsg || this.leaveMsg.requireMsg.trim() == '') {
                    this.$message.error('需求不能为空！');
                    return;
                }
                // saveServerAdvisory({serverRemark:this.leaveMsg.content,userName:this.leaveMsg.userName,phoneNo:this.leaveMsg.phoneNo,serverName:this.leaveMsg.requireMsg,borkerId:this.leaveMsg.brokerId})
                saveBrokerLeaveMsg(this.leaveMsg).then(response => {
                    if (response.retCode == 0){
                        this.dialogFormVisible = false;
                        this.liuyanchenggong = true;
                        this.leaveMsg= {
                            brokerId: null,
                            content: null,
                            userName: null,
                            phoneNo: null,
                            requireMsg: null
                        };
                        this.$message({
                            message: '发送成功',
                            type: 'success'
                        });
                        setTimeout(()=> {
                            this.liuyanchenggong = false;
                        }, 2000);
                    }
                });
            },

            minDialog(){
                this.dialogFormVisible = false;
                this.dialogSmallShow = true;
            },
            showDialog(){
                this.dialogFormVisible = true;
                this.dialogSmallShow = false;
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-tabs__item {
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(149, 149, 149, 1);
        padding: 0;padding-right: 18px;
    }

    /deep/  .el-tabs__item:hover {
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(0, 0, 0, 1);
        text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.13);
    }

    /deep/ .el-tabs__item.is-active {
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(0, 0, 0, 1);
        text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.13);
        border-right: 4px solid rgba(202,0,0,1);
    }

    /deep/ .el-tabs__active-bar {
        background-color: rgba(202,0,0,1);
        width: 4px;
        height: 20px;
    }

    .jinjiren {
        padding-top: 35px;
        width: 260px;
        height: 300px;
        background: rgba(247, 247, 247, 1);
        box-shadow: 0px 2px 7px 0px rgba(0, 0, 0, 0.22);
        margin: 0px auto;
        text-align: center;
        line-height: 1.2;
    }

    .jieshao {
        margin: 0px auto;
        width: 80px;
        height: 80px;
    }

    .name {
        margin-top: 21px;
        font-size: 14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(0, 0, 0, 1);
    }

    .xian {
        width: 160px;
        height: 1px;
        border: 1px solid rgba(202, 0, 0, 1);
    }

    .Aname {
        font-size: 12px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(149, 149, 149, 1);
    }

    .chanquan {
        margin-top: 15px;
        font-size: 14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(0, 0, 0, 1);
        margin-bottom: 5px;
    }

    .xian {
        margin: 0px auto;
    }

    /deep/ .el-button.lianxi {
        width: 109px;
        margin-top: 20px;
        color: white;
        background: linear-gradient(90deg,#f74a0d 0%,#fa963d 100%);border: none;
    }
    /deep/ .el-button.lianxi span{color: #FFF;}
    .cq {
        margin-bottom: 137px;
    }

    .photo {
        width: 80px;
        height: 80px;
        border-radius: 50%;
    }


    .neirong {
        width: 275px;
        background: rgba(251, 251, 251, 1);
        border: 1px solid rgba(169, 169, 169, 1);
    }

    /deep/ .el-input__inner {
        margin-top: 10px;
        width: 275px;
        height: 44px;
        background: rgba(251, 251, 251, 1);
        border: 1px solid rgba(169, 169, 169, 1);
        background: white;
        border-radius: 0px;
    }

    /deep/  .el-input__icon {
        margin-top: 8px;
    }

    .dianhua  /deep/ .el-icon-search:before {
        content: url("../../images/middleman/dianhua.png");
    }

    .xingming  /deep/ .el-icon-search:before {
        content: url("../../images/middleman/xingming.png");
    }

    .xuqiu  /deep/ .el-icon-search:before {
        content: url("../../images/middleman/icon_xuqiu.png");
    }

    /deep/ .el-dialog {
        width: 309px;
        height: 479px;
        background: url("../../images/middleman/liuyan.png");
    }

    /deep/  .el-dialog__header {
        /*background: url("../../images/middleman/liuyanred.png") no-repeat;*/
        background: #024ecc;
        border-radius: 10px 10px 0 0;
    }

    /deep/  .el-dialog__title {
        position: relative;
        top: -12px;
        font-size: 14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(255, 255, 255, 1);
    }

    /deep/ .el-button--primary {
        border: none;
    }

    /deep/ .el-icon-close:before {
        content: url("../../images/middleman/guanbicuowu.png");
        position: relative;
        top: -10px;
    }

    .chenggong {
        padding-top: 12px;
        width: 164px;
        height: 74px;
        background: url("../../images/middleman/chenggong.png") no-repeat;
        margin: 0px auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        -webkit-transform: translate(-50%,-50%);
    }

    .chenggongphoto {
        margin-left: 27px;
        width: 23px;
        height: 23px;
        background: url("../../images/middleman/chenggongphoto.png");
    }
    .dialogSmall{width: 309px;height: 35px;background: #024ECC;color: #fff;padding: 0 20px;border-top-left-radius: 10px;border-top-right-radius: 10px; position: absolute;right: 0;top: 40%;transform: translateY(-50%); display: flex;display: -webkit-flex;justify-content: space-between;-webkit-justify-content: space-between;align-items: center;-webkit-align-items: center;}
    .dialogSmall h3{font-size: 14px;}
    .dialogSmall .el-button--text{font-size: 18px;color: #FFF;font-weight:normal;}
    .dialogMsg /deep/ .el-dialog{border-radius: 10px;}
    .dialogMsg /deep/ .el-dialog .el-dialog__header{color: #FFF; font-size: 14px;font-weight:normal; padding:0 0 0 20px;}
    /deep/ .el-icon-minus.el-button--text{color: #FFF;font-size: 18px;font-weight:normal;margin-left: 156px;}
    .manInfo{}
    .manInfo /deep/ .el-col{margin-right: 16px;}
    .manInfo /deep/ .el-col:nth-child(4n){margin-right: 0;}
</style>