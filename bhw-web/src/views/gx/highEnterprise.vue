<template>
    <div style="background: #EEEEEE;padding-bottom: 100px;">
        <!-- 搜索条件 -->
        <el-row class="searchBox bgColorF">
            <el-col class="w1240" style="margin:30px auto;float: inherit;">
                <el-row style="border:1px solid #ccc;">
                    <el-col>
                        <dl>
                            <dt style="background: rgba(247,255,207,1);">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</dt>
                            <dd>
                                <el-link style="background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);">全部</el-link>
                                <el-link v-for="proviceItem in proviceList" :key="proviceItem.provinceId" @click="filterParam(proviceItem.provinceId,proviceItem.provinceName,1)">{{proviceItem.provinceName}}（<em>{{proviceItem.count}}</em>）</el-link>
                            </dd>
                        </dl>
                    </el-col>
                    <el-col>
                        <dl>
                            <dt  style="background: rgba(247,255,207,1);">注册资本：</dt>
                            <dd>
                                <el-link style="background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);" @click="filterParam('','',2)">全部</el-link>
                                <el-link @click="filterParam('0','100',2)">0-100万</el-link>
                                <el-link @click="filterParam('100','200',2)">100-200万</el-link>
                                <el-link @click="filterParam('200','500',2)">200-500万</el-link>
                                <el-link @click="filterParam('500','1000',2)">500-1000万</el-link>
                                <el-link @click="filterParam('1000','',2)">1000万以上</el-link>
                            </dd>
                        </dl>
                    </el-col>
                    <el-col>
                        <dl>
                            <dt style="background: rgba(247,255,207,1);">成立时间：</dt>
                            <dd>
                                <el-link style="background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);" @click="filterParam('','',3)">全部</el-link>
                                <el-link @click="filterParam('','1',3)">1年内</el-link>
                                <el-link @click="filterParam('1','5',3)">1-5年</el-link>
                                <el-link @click="filterParam('5','10',3)">5-10年</el-link>
                                <el-link @click="filterParam('10','15',3)">10-15年</el-link>
                                <el-link @click="filterParam('15','',3)">15年以上</el-link>
                            </dd>
                        </dl>
                    </el-col>
                </el-row>
            </el-col>
            <el-col><p style="width: 1240px;margin:0 auto 27px;">以为您筛选<em style="color: #CA0000;font-style: normal;"> {{totalNum}} </em>条数据样本，请查收！</p></el-col>
        </el-row>
        <el-row class="w1240" style="margin:27px auto;">
            <el-col :span="18" v-if="allList.length>0">
                <el-row class="enterInfo">
                    <el-col v-for="(item,index) in allList" :key="item.id">
                        <div v-if="item.img != ''" class="imgBox"><img :src="item.img" alt=""></div>
                        <div v-else class="imgBox noImgBox" :style="'background:'+[colorList[index]]"><span>{{item.shortName}}</span></div>
                        <div>
                            <h3>{{item.fullName}}</h3>
                            <el-tag>{{item.industey}}</el-tag>
                            <dl class="dbBox"><dd>法定代表人：<span>{{item.legalPerson}}</span></dd><dd>注册资本：<span>{{item.registeredCapital}}万(元)</span></dd><dd>成立时间：<span>{{item.createTime}}</span></dd></dl>
                            <dl><dd>地址：<span>{{item.address}}</span></dd></dl>
                            <dl><dd>简介：<span v-html="item.profile"></span></dd></dl>
                        </div>
                    </el-col>
                </el-row>
                <el-row style="text-align: center;">
                    <el-col>
                        <el-pagination
                                background
                                layout="prev, pager, next,total"
                                :page-size="queryParam.pageSize"
                                :current-page="queryParam.curPage"
                                @current-change="currentChange"
                                :total="pageTotal" class="pageBox">
                        </el-pagination>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="18" style="text-align: center;" v-else>
                <p>暂无数据</p>
            </el-col>
            <el-col :span="5" class="noData"><p>暂无浏览数据</p></el-col>
        </el-row>
        <el-row></el-row>
    </div>
</template>

<script>
    import {getResourceCount,getResourceProvince,getResourceAreaRange} from "../../api/school/school"
    export default {
        name: "highEnterprise",
        props:["areaType"],
        data(){
            return{
                totalNum:"0",
                allList:[],
                proviceList:[],
                browseList:[],
                colorList:["#8CC99A","#8AADD9","#8E99CA","#AB8CBD","#F0A1C3","#F19C7A","#F9CE8F","#B26DF3"],
                queryParam:{
                    pageSize:8,
                    curPage:1,
                    type:this.areaType,
                    provinceId:"",
                    startCapital:"",
                    endCapital:"",
                    startDate:"",
                    endDate:""
                },
                currenTime:'',
                pageTotal:0
            }
        },
        created() {
            this.getTotalNum();
            this.getProviceNum();
            this.getAllList();
        },
        watch:{
            areaType:{
                immediate:true,
                handler(val) {
                    this.queryParam.type = val;
                    this.getTotalNum();
                    this.getProviceNum();
                    this.getAllList();
                }
            }
        },
        methods:{
            getDay(day) {
                var today = new Date();
                var targetday_milliseconds = today.getTime() - 1000 * 60 * 60 * 24 * day;
                today.setTime(targetday_milliseconds);
                var tYear = today.getFullYear();
                var tMonth = today.getMonth();
                var tDate = today.getDate();
                tMonth = this.doHandleMonth(tMonth + 1);
                tDate = this.doHandleMonth(tDate);
                return tYear;
            },
            doHandleMonth(month) {
                var m = month;
                if (month.toString().length == 1) {
                    m = "0" + month;
                }
                return m;
            },
            getTotalNum(){
                getResourceCount( {"type":this.areaType}).then(response =>{
                    this.totalNum = response.data;
                })
            },
            getProviceNum(){
                getResourceProvince({"type":this.areaType}).then(response =>{
                    this.proviceList = response.data;
                })
            },
            getAllList(){
                getResourceAreaRange(this.queryParam).then(response =>{
                    this.allList = response.data.dataList;
                    this.pageTotal = response.page.counts;
                    console.log(response.page.counts)
                })
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.getAllList();
            },
            filterParam(itemName,itemName2,typeID){
                if(typeID == 1){
                    this.queryParam.provinceId = itemName;
                }else if(typeID == 2){
                    this.queryParam.startCapital = itemName;
                    this.queryParam.endCapital = itemName2;
                }else if(typeID == 3){
                    if(itemName2 == "1"){
                        this.queryParam.startDate = this.getDay(365).toString();
                        this.queryParam.endDate = this.getDay(0).toString();
                    }else if(itemName == "1" && itemName2 == "5"){
                        this.queryParam.startDate = this.getDay(5*365);
                        this.queryParam.endDate = this.getDay(365);
                    }else if(itemName == "5" && itemName2 == "10"){
                        this.queryParam.startDate = this.getDay(10*365);
                        this.queryParam.endDate = this.getDay(5*365);
                    }else if(itemName == "10" && itemName2 == "15"){
                        this.queryParam.startDate = this.getDay(15*365);
                        this.queryParam.endDate = this.getDay(10*365);
                    }else if(itemName == "15"){
                        this.queryParam.startDate = '';
                        this.queryParam.endDate = this.getDay(15*365);
                    }else{
                        this.queryParam.startDate = '';
                        this.queryParam.endDate = '';
                    }
                }
                this.getAllList();
            }
        }

    }
</script>

<style scoped>
    .searchBox .el-row{}
    .searchBox dl{height: 75px;line-height:75px;border-bottom:1px solid #CCC; display: flex;display: -webkit-flex;}
    .searchBox .el-col:last-child dl{border-bottom: none;}
    .searchBox dl dt{width: 224px;color: #1D1D1D;font-size: 18px;font-weight:normal; text-align: center;background: #EEEEEE;}
    .searchBox dl dd{font-size: 14px;font-weight: normal; flex: 1;-webkit-flex: 1;}
    .searchBox dl dd .el-link{color: #1D1D1D;font-weight:normal;width: calc(100% / 5 - 50px);width: -webkit-calc(100% / 5 - 50px);justify-content: flex-start;-webkit-justify-content: flex-start;}
    .searchBox dl dd .el-link:first-child{width: 62px;height: 36px;background: #FDA65C;color: #fff;font-weight:normal;margin:0 50px 0 22px;border-radius: 2px; justify-content: center;-webkit-justify-content: center; }
    .searchBox em{color: #CA0000;font-style: normal;}
    .enterInfo .el-col{background: #FFF;padding:30px 36px;margin-bottom: 26px; display: flex;display: -webkit-flex;}
    .enterInfo .el-col .imgBox{width: 131px;height: 131px;margin-right: 40px;}
    .noImgBox{color: #fff;font-size: 24px;font-weight:normal; border-radius: 5px;text-align: center;padding: 15px; display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .enterInfo .el-col div:last-child{flex: 1;-webkit-flex: 1;}
    .enterInfo .el-col img{width: 100%;height: 100%;padding: 6px 0;}
    .enterInfo .el-tag{background: #B7D3FD;color: #0066FF;height: 22px;line-height: 20px;margin:5px 0 10px;}
    .enterInfo .el-col h3{color: #1D1D1D;font-size: 18px;font-weight:normal;}
    .enterInfo dl>dd{color: #7D7D7D;display: inline-block;line-height: 28px;}
    .enterInfo dl>dd span{color: #1D1D1D;}
    .enterInfo dl.dbBox>dd{margin-right: 78px;}
    .noData{height: 1000px;color: #7F7F7F; background: #fff;font-size: 18px; margin-left: 51px; display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;justify-content: center;-webkit-justify-content: center;}
</style>