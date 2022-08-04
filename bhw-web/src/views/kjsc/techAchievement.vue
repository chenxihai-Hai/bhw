<template>
    <div class="w1240" style="margin: 43px auto 0;">
        <div class="tachCategoryBox">
            <el-row type="flex">
                <el-col :span="2" style="text-align:right;font-size: 16px;font-weight:normal;padding-top:16px;">技术类别</el-col>
                <el-col :span="22" style="text-align: left;">
                    <el-link @click="seachCate(null)" style="font-weight:normal;">全部</el-link>
                    <el-link v-for="cItem in categoryList" :key="cItem.id" @click="seachCate(cItem.id)" :class="checkId==cItem.id?'check':''">{{cItem.name}}</el-link>
                </el-col>
            </el-row>
        </div>
        <div style="padding-top: 40px;">
            <el-row type="flex" justify="space-between" align="bottom" style="padding-bottom: 8px;">
                <el-col style="padding-left: 16px;text-align: left;">默认排序</el-col>
                <el-col style="padding-right: 30px;text-align: right;">
                    <el-input placeholder="按标题模糊搜索" v-model="qryParam.techName" class="keySearch">
                        <el-button slot="append" @click="qryRequireList" icon="el-icon-search"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <div class="techListBox" v-for="item in techList" :key="item.techName">
                <h3><em>【{{item.typeName}}】</em>{{item.techName}}</h3>
                <p>{{item.introduce.substring(0,150)+'...'}}<router-link :to="{path:'/kjsc/techDetail',query:{requireObj:item}}">[详情]</router-link></p>
                <div><span>权利人/发明人：<em>{{item.source}}</em></span>
                    <span>联系人：<em>{{undefined==item.contactName || ''==item.contactName?'翁治林':item.contactName}}</em>
                        | 联系方式：<em>{{undefined==item.contactPhone || ''==item.contactPhone?'18589029528':item.contactPhone}}</em></span>
                </div>
            </div>
        </div>
        <div class="pageWrap">
            <el-pagination
                    background
                    layout="prev, pager, next,total"
                    :page-size="qryParam.pageSize"
                    :current-page="qryParam.curPage"
                    @current-change="currentChange"
                    :total="pageTotal" class="pageBox">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {qryTechAchievemnetList,qryScienceRequireTypeList} from "../../api/projectDw/projectDw";
    export default {
        name: "techAchievement",
        data(){
            return{
                pageTotal:0,
                checkId:'0',
                categoryList:[/*{id:'1',name:'先进制造'}*/],
                qryParam:{techType:null,techName:'',pageSize:10,curPage:1},
                techList:[/*{id:0,title:'复杂修形齿轮精密数控加工关键技术与装备',categoryName:'先进制造',
                    content:'复杂修形齿轮是克服高速重载工况下力热耦合形变影响的高端齿轮，直接决定装备传动系统的振动、噪声、寿命等服役性能及其核心竞争力，广泛应用于航母、潜艇、汽车等。针对复杂修形齿面精密制造面临全齿面修形加工存在原理误差、传统试错修调法提升加工精度 困难、齿面淬硬层均匀性及级理难以调控等问题，在国家科技重大专项、863计划等支持下，开展复杂修形齿轮精密数控加工关键技术与装备研究，成果获2018年国家科技进步二等奖。',
                    inventor:'重庆大学',contactName:'翁治林',contactPhone:'18589029528'}*/]
            }
        },
        created(){
          this.qryRequireList();
          this.qryScienceRequireTypeList();
        },
        methods:{
            seachCate(val){
                this.checkId = val;
                this.qryParam.techType = val;
                this.qryParam.curPage=1;
                this.qryRequireList();
            },
            currentChange(val){
                this.qryParam.curPage = val;
                this.qryRequireList();
            },
            qryScienceRequireTypeList(){
                qryScienceRequireTypeList().then(response=>{
                    this.categoryList = response.data;
                });
            },
            qryRequireList(){
                qryTechAchievemnetList(this.qryParam).then(response=>{
                    this.techList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .tachCategoryBox{background:rgba(2,78,204,0.05);color: #000; padding: 14px 0 5px;border-top: 2px solid #024ECC;}
    .tachCategoryBox /deep/ .el-link{color: #000;padding: 16px 26px;}
    .tachCategoryBox /deep/ .el-link+.el-link:hover{color: #ca0000;}
    .tachCategoryBox /deep/ .el-link.check{color: #ca0000;}
    .techListBox{text-align: left; padding: 39px 30px 43px 37px;border-top: 1px solid rgba(204,204,204,1);}
    .techListBox h3{color: #024ECC;font-size: 16px;}
    .techListBox em{color: #ca0000;font-style: normal;}
    .techListBox p{color: #000; text-indent: 2em;margin: 20px 0;line-height: 24px;}
    .techListBox span{margin-right: 30px;}
    .techListBox /deep/ a{color: #024ECC;}
</style>