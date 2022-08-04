<template>
    <div>
        <div class="img-home-head"><img src="../../images/kjsc.png" alt="" style="width: 100%;"></div>
        <div class="allWrap w1240">
            <div class="breadWrap">
                <span class="breakCurr"><i></i>当前位置：</span>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/kjsc' }">科技市场</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/kjsc' }">资产交易</el-breadcrumb-item>
                    <el-breadcrumb-item>所有产品</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <hr class="lineB">
            <div class="productWrap">
                <el-row class="sBox bgColor">
                    <el-col class="condi">
                        <dl><dt>已选条件：</dt>
                            <dd>
                                <el-tag
                                        v-for="(tag,index) in tags"
                                        :key="tag.name"
                                        closable
                                        :type="tag.type"
                                        @close="closeTag(index,tag)"
                                >
                                    {{tag.name}}
                                </el-tag>
                            </dd>
                        </dl>
                    </el-col>
                    <el-col>
                        <dl>
                            <dt>交易金额：</dt>
                            <dd><el-link @click="filterParam(item.val,item.name,4)" v-for="item in priceList" :key="item.name">{{item.name}}</el-link></dd>
                        </dl>
                        <template v-if="projectTypeQry=='1'">
                            <dl >
                                <dt style="width:400px;">商标分类：</dt>
                                <dd><el-link @click="filterParam(spItem.id,spItem.name,1)" v-for="spItem in spType" :key="spItem.name">{{spItem.name}}</el-link></dd>
                            </dl>
                        </template>
                        <template v-else>
                            <dl >
                                <dt style="width:100px;">行业分类：</dt>
                                <dd><el-link @click="filterParam(spItem.id,spItem.name,2)" v-for="spItem in industryType" :key="spItem.name">{{spItem.name}}</el-link></dd>
                            </dl>
                            <dl >
                                <dt>专利分类：</dt>
                                <dd><el-link @click="filterParam(spItem.id,spItem.name,3)" v-for="spItem in patentType" :key="spItem.name">{{spItem.name}}</el-link></dd>
                            </dl>
                        </template>
                    </el-col>
                </el-row>
                <el-row class="orderBox">
                    <el-col><el-link class="check">默认</el-link><el-link>人气<i class="el-icon-top"></i></el-link><el-link>发布时间<i class="el-icon-top"></i></el-link><el-link>价格<i class="el-icon-top"></i></el-link></el-col>
                    <el-col></el-col>
                </el-row>
                <el-row class="productList" style="margin-top: 19px;">
                    <el-col class="pBox" v-for="pInfo in proList" :key="pInfo.id" >
                        <!--<el-image :src="pInfo.homeImg" @click="showDetail(pInfo)"></el-image>-->
                        <img style="width: 100%;height: 198px;margin-top: 11px;cursor: pointer;" :src="pInfo.homeImg" @click="showDetail(pInfo)"/>
                        <h3 style="cursor: pointer;" @click="showDetail(pInfo)" :title="pInfo.projectName">
                            <!--{{pInfo.projectName.substring(0,12)+'...'}}-->
                            <template v-if="pInfo.projectName.length>10">
                                {{pInfo.projectName.substring(0,10)+'...'}}
                            </template>
                            <template v-else>
                                {{pInfo.projectName}}
                            </template>
                        </h3>
                        <!--<p><em class="priceMark">{{pInfo.unitPrice}}</em>¥</p>-->
                        <!--<p><em class="priceMark">{{pInfo.unitPrice>10000?pInfo.unitPrice/10000+'万':pInfo.unitPrice}}</em>¥</p>-->
                        <p>
                            <template v-if="undefined==pInfo.unitPrice||''==pInfo.unitPrice">
                                <em class="priceMark">面议</em>
                            </template>
                            <template v-else>
                                <em class="priceMark">
                                    {{pInfo.unitPrice>10000?pInfo.unitPrice/10000+''+'万':pInfo.unitPrice}}</em>¥
                            </template>
                        </p>
                        <div class="btnBox">
                           <!-- <div><i class="el-icon-star-off" style="color:#CA0000;"></i><el-link>收藏宝贝</el-link></div>-->
                            <div><i class="el-icon-map-location" style="color: #CC3333;"></i>{{pInfo.provinceName}} {{pInfo.cityName}}</div>
                            <span :title="pInfo.source">
                                 数据来源:{{undefined== pInfo.source || pInfo.source==''?'发布者':pInfo.source.length>3?pInfo.source.substring(0,3)+'...':pInfo.source}}
                             </span>
                        </div>
                    </el-col>
                </el-row>
                <div style="text-align: center; margin-top: 69px;">
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
        </div>
    </div>
</template>

<script>
    import {qryProjectList,qryIntellectualType} from "../../api/projectDw/projectDw";
    export default {
        name: "allProduct",
        data() {
            return {
                projectTypeQry:'2',
                priceList:[{id:1,name:"1万以下",val:"10000"},{id:2,name:"1-2万",val:"10000-20000"},{id:3,name:"2-3万",val:"20000-30000"},{id:4,name:"3-5万",val:"30000-40000"},
                    {id:5,name:"5-10万",val:"50000-100000"},{id:6,name:"10-20万",val:"100000-200000"},{id:7,name:"20-50万",val:"200000-500000"},{id:8,name:"50-100万",val:"500000-1000000"},{id:9,name:"100万以上",val:"1000000-100000000"}],
                spType:[{id:1,name:" 01类-化学原料 "},{id:2,name:"02类-颜料油漆"},{id:3,name:"03类-日化用品"},{id:4,name:"04类-燃料油脂"}],
                industryType:[{id:1,name:"农林渔牧/食品饮料"},{id:2,name:"交通/运输/包装"},{id:3,name:"化工/材料/冶金/生物"},{id:4,name:"纺织/造纸"}],
                patentType:[{id:1,name:"发明专利"},{id:2,name:"实用新型专利"},{id:3,name:"外观专利"}],
                proList:[],
                tags:[],
                pageTotal:0,
                qryParam:{
                    projectType:null,
                    industryId:null,
                    patentType:null,
                    pageSize:20,
                    status:2,
                    priceFilter:null,
                    curPage:1,
                    needType:1
                }
            }
        },
        created(){
            this.qryParam.projectType = this.$route.query.projectType;
            this.projectTypeQry = this.$route.query.projectType;
            this.qryIntellectualType();
            this.qryProduct();
        },
        methods:{
            currentChange(val){
                this.qryParam.curPage = val;
                this.qryProduct();
            },
            qryProduct(){
                qryProjectList(this.qryParam).then(response => {
                    this.proList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            showDetail(obj){
                this.$router.push({path:"/kjsc/detail",query:{projectObj:obj}});
            },
            qryIntellectualType(){
                qryIntellectualType({"intellectualType":this.projectTypeQry}).then(response => {
                    if(this.projectTypeQry=='2'){
                        this.industryType = response.data;
                    }else{
                        this.spType = response.data;
                    }
                });
            },
            filterParam(id,name,type){
                var flag = true;
                this.tags.forEach(item=>{
                    if(item.filterType==type){
                        item.name = name;
                        flag = false;
                    }
                })
                if(flag){
                    this.tags.push({'name':name,'type':'warning','filterType':type});
                }
                if(type==4){
                    this.qryParam.priceFilter = id;
                }else if(type==3){
                    this.qryParam.patentType = id;
                }else{
                    this.qryParam.industryId = id;
                }
                this.qryParam.curPage=1;
                this.qryProduct();
            },
            closeTag(index,tag){
                this.tags.splice(index, 1);
                if(tag.filterType==4){
                    this.qryParam.priceFilter=null;
                }else if(tag.filterType==3){
                    this.qryParam.patentType = null;
                }else{
                    this.qryParam.industryId = null;
                }
                this.qryProduct();
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
    .allWrap{margin: 80px auto 100px;}
    .lineB{width: 100%;height: 0.5px;background: #CCCCCC;margin:17px 0 13px;}
    .sBox.bgColor{padding: 0;padding-bottom: 10px;}
    .sBox dl{line-height: 25px;margin: 8px 30px;padding-bottom: 8px; display: flex;display: -webkit-flex;border-bottom: 0.1px solid #CCCCCC;}
    .sBox .condi dl{padding: 8px 30px;margin: 0;}
    .sBox dl dt{width: 80px;text-align: right;margin-right: 10px;}
    .sBox dl dd>a{margin: 0 15px;}
</style>