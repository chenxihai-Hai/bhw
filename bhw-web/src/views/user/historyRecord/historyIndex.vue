<template>
    <div style="padding: 0 17px 185px;">
        <el-row style="margin-bottom: 4px; position: relative;" >
            <el-menu default-active="1" class="el-menu-demo" mode="horizontal">
                <el-menu-item index="1">浏览历史</el-menu-item>
            </el-menu>
            <el-link class="btnDelALl" @click="showDel=!showDel">批量删除</el-link>
        </el-row>
        <template v-if="historyDateList.length>0 || delList.length>0">
            <el-row v-for="(hisDate,pIndex) in historyDateList" :key="hisDate.createTime">
                <el-col class="historyMark"><span>{{hisDate.createTime.split(" ")[0]}}</span><span>{{hisDate.counts}}件产品</span></el-col>
                <el-col class="historyBox">
                    <el-row>
                        <el-col v-for="(his,cIndex) in hisDate.list" :key="his.id" >
                            <el-card :body-style="{padding: '0px' }" style="position: relative;">
                                <img @click="toDet(his)" :src="his.img" class="image" v-if="his.img!=''">
                                <img @click="toDet(his)" src="../../../images/img-zhuanl.png" class="image" v-else/>
                                <div style="padding: 10px;font-size: 12px;">
                                    <span style="color: #CA0000;font-weight:normal;" v-if="his.price">{{his.price}}元</span>
                                    <span style="color: #CA0000;font-weight:normal;" v-else>面议</span>
                                    <p v-if=" undefined != his.serverName && his.serverName!='' && his.serverName.length>6">名称：{{his.serverName.substring(0,6)+'...'}}</p>
                                    <p v-else>名称：{{his.serverName}}</p>
                                </div>
                                <el-link class="btnDel" @click="delInfo(his.id,pIndex,cIndex)" v-show="showDel"><i class="el-icon-delete"></i></el-link>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row>
                <el-col style="text-align: right;margin-top: 15px;" v-show="showDel"><el-button @click="submitDel" class="btnDelCmt btnColorOrg">完成</el-button></el-col>
            </el-row>
            <el-row style="margin: 70px auto 100px;text-align: center;">
                <el-col>
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="qryDateParam.pageSize"
                            :current-page="qryDateParam.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </template>
        <template v-else>
            <el-row type="flex" align="middle" style="margin-top: 90px;">
                <el-col style="text-align: right;"><img src="../../../images/user/img-noData.png" alt=""></el-col>
                <el-col style="text-align: left;padding-left: 28px;"><span class="nodata">您还没有好好观察我哦~</span></el-col>
            </el-row>
        </template>
    </div>
</template>

<script>
    import {qryRecordList,deleteRecordByIds,qryRecordDateList} from "../../../api/user/serverOperate";
    export default {
        name: "historyIndex",
        data(){
            return{
                showDel:false,
                historyDateList:[],
                pageTotal:0,
                delList:[],
                qryParam:{
                    userId:this.$store.state.user.userInfo.id,
                    operateType:1,
                    pageSize:3,
                    date:'',
                    curPage:1
                },
                qryDateParam:{
                    userId:this.$store.state.user.userInfo.id,
                    operateType:1,
                    pageSize:2,
                    curPage:1
                }
            }
        },
        created(){
            this.qryRecordDateList();
        },
        methods:{
            currentChange(val){
                this.qryDateParam.curPage = val;
                this.qryRecordDateList();
            },
            submitDel(){
              if(this.delList.length>0){
                  deleteRecordByIds({ids:this.delList.join(",")}).then(response=>{
                      this.delList = [];
                      this.showDel = !this.showDel;
                      this.$message({
                          message: '删除成功',
                          type: 'success'
                      });
                      this.qryRecordDateList();
                  });
              }
            },
            delInfo(id,pIndex,cIndex){
                this.delList.push(id);
                this.historyDateList[pIndex].list = this.historyDateList[pIndex].list.filter(t => t.id != id);
                this.$set(this.historyDateList,pIndex,this.historyDateList[pIndex]);
            },
            toDet(obj){
                this.$router.push({path:obj.path,query:{hisProductId:obj.serverId,hisProductType:obj.type}});
            },
            qryRecordDateList(){
                qryRecordDateList(this.qryDateParam).then(response=>{
                    this.historyDateList = response.data;
                    this.pageTotal = response.page.counts;
                    for(var i=0;i<this.historyDateList.length;i++){
                        this.qryRecordList(i);
                    }
                });
            },
            qryRecordList(index){
                this.historyDateList[index].qryParam={
                    userId:this.$store.state.user.userInfo.id,
                    operateType:1,
                    pageSize:10,
                    date:this.historyDateList[index].createTime.split(" ")[0],
                    curPage:1
                }
                qryRecordList(this.historyDateList[index].qryParam).then(response=>{
                    this.historyDateList[index].list = response.data;
                    this.historyDateList[index].pageTotal = response.page.counts;
                    this.$set(this.historyDateList,index,this.historyDateList[index]);
                });
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-link.el-link--default.btnDelALl{color: #024ECC; position: absolute;right: 0;bottom: 10px;}
    .historyMark{font-size:14px;color:#1D1D1D;margin-top: 33px;padding-bottom: 6px;position: relative;}
    .historyMark::after{content: "";width: 85%; height: 0.5px;background: #CCC;position: absolute;bottom: 0;right: 0;display: block;}
    .historyMark span:first-child{color: #CA0000;font-size: 18px;margin-right: 10px;}
    .historyMark span:last-child{font-size: 12px;margin-left: 10px;}
    .historyBox{background: rgba(2,78,204,0.05); padding: 23px 30px;margin-top: 35px;}
    .historyBox .el-col{width: 157px;height: 200px;margin-right: 33px;margin-bottom: 27px;}
    .historyBox .el-col:nth-child(5n){margin-right: 0;}
    .image{width: 157px;height: 146px;cursor: pointer;}
    .historyBox p{color: #1D1D1D;font-size: 12px;margin-top: 8px;}
    .btnDelCmt{width: 48px;height: 29px;padding: 0;border: none;}
    .btnDel{background: #024ECC;width:23px;height: 23px;text-align: center; position: absolute;top:0;right: 0;}
    .btnDel i{color: #FFF;}
    /deep/ .el-menu--horizontal>.el-menu-item.is-active {
        border-bottom: 2px solid #C90000;
        color: #303133;
    }
</style>