<template>
    <div style="padding: 0 17px 100px;">
        <el-row style="margin-bottom: 4px; position: relative;" >
            <el-menu default-active="1" class="el-menu-demo" mode="horizontal" >
                <el-menu-item index="1">我的收藏</el-menu-item>
            </el-menu>
            <el-link class="btnDelALl"  @click="showDel=!showDel">批量删除</el-link>
        </el-row>
        <template v-if="collectionList.length>0 || delList.length>0 ">
            <el-row>
                <el-col class="historyBox">
                    <el-row>
                        <el-col v-for="colle in collectionList" :key="colle.id">
                            <el-card :body-style="{padding: '0px' }" style="position: relative;">
                                <img @click="toDet(colle)" :src="colle.img" class="image" v-if="colle.img!=''">
                                <img @click="toDet(colle)" src="../../../images/img-zhuanl.png" class="image" v-else/>
                                <div style="padding: 10px;font-size: 12px;">
                                    <!--<span style="color: #CA0000;font-weight:normal;">{{colle.price}}元</span>-->
                                    <span style="color: #CA0000;font-weight:normal;" v-if="colle.price">{{colle.price}}元</span>
                                    <span style="color: #CA0000;font-weight:normal;" v-else>面议</span>
                                    <p v-if=" undefined != colle.serverName && colle.serverName!='' && colle.serverName.length>6">名称：{{colle.serverName.substring(0,6)+'...'}}</p>
                                    <p v-else>名称：{{colle.serverName}}</p>
                                </div>
                                <el-link class="btnDel"  @click="delInfo(colle.id)" v-show="showDel"><i class="el-icon-delete"></i></el-link>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row>
                <el-col  v-show="showDel" style="text-align: right;margin-top: 15px;"><el-button @click="submitDel" class="btnDelCmt btnColorOrg">完成</el-button></el-col>
            </el-row>
        </template>
        <template v-else>
            <el-row type="flex" align="middle" style="margin-top: 90px;">
                <el-col style="text-align: right;"><img src="../../../images/user/img-noData.png" alt=""></el-col>
                <el-col style="text-align: left;padding-left: 28px;"><span class="nodata">您还没有发掘喜好哦~</span></el-col>
            </el-row>
        </template>
    </div>
</template>

<script>
    import {qryRecordList,deleteRecordByIds} from "../../../api/user/serverOperate";
    export default {
        name: "myCollection",
        data(){
            return{
                collectionList:[],
                showDel:false,
                pageTotal:0,
                delList:[],
                qryParam:{
                    userId:this.$store.state.user.userInfo.id,
                    operateType:2,
                    pageSize:25,
                    curPage:1
                }
            }
        },
        created(){
            this.qryRecordList();
        },
        methods:{
            submitDel(){
                if(this.delList.length>0){
                    deleteRecordByIds({ids:this.delList.join(",")}).then(response=>{
                        this.delList = [];
                        this.showDel = !this.showDel;
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.qryRecordList();
                    });
                }
            },
            delInfo(id){
                this.delList.push(id);
                this.collectionList = this.collectionList.filter(t => t.id != id);
            },
            toDet(obj){
                this.$router.push({path:obj.path,query:{hisProductId:obj.serverId,hisProductType:obj.type}});
            },
            qryRecordList(){
                qryRecordList(this.qryParam).then(response=>{
                    this.collectionList = response.data;
                    this.pageTotal = response.page.counts;
                    console.log(this.collectionList );
                });
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-link.el-link--default.btnDelALl{color: #024ECC; position: absolute;right: 0;bottom: 10px;}
    .historyBox{background: rgba(2,78,204,0.05); padding: 23px 30px;margin-top: 35px;}
    .historyBox .el-col{width: 157px;height: 200px;margin-right: 33px;margin-bottom: 27px;}
    .historyBox .el-col:nth-child(5n){margin-right: 0;}
    .image{width: 157px;height: 146px;cursor: pointer}
    .historyBox p{color: #1D1D1D;font-size: 12px;margin-top: 8px;}
    .btnDelCmt{width: 48px;height: 29px;padding: 0;border: none;}
    .btnDel{background: #024ECC;width:23px;height: 23px;text-align: center; position: absolute;top:0;right: 0;}
    .btnDel i{color: #FFF;}
</style>