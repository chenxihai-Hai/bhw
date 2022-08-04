<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>成交数量编辑</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="技术经纪人">
                    <el-input v-model="formInline.name" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="服务类型">
                    <el-select v-model="formInline.borkerId" placeholder="请选择">
                        <el-option
                                v-for="item in borkerType"
                                :key="item.name"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList" @click="backQryBrokerList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-top: 18px;">
                <el-col>
                    <el-table :data="backBorkerList" class="allTable"  @selection-change="handleSelectionChange">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                label="ID" type="index"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="头像"
                                align="center">
                            <template slot-scope="scope">
                                <el-image
                                        style="width: 50px; height: 50px"
                                        :src="scope.row.photo"
                                        :preview-src-list="[scope.row.photo]">
                                </el-image>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="技术经纪人" prop="name"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                label="服务类型" prop="name"
                                align="center">
                            <template slot-scope="scope" >
                                <el-select v-model="scope.row.seniority"  placeholder="请选择"  class="allSelect" style="width: 150px;">
                                    <el-option v-for="sItem in scope.row.typeList" :key="sItem.brokerName"
                                               :label="sItem.brokerName"
                                               :value="sItem.id">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="成交量" prop="turnover"
                                align="center">
                            <template slot-scope="scope" >
                                <div>
                                <template v-if="scope.row.turnoverFlag==true || undefined == scope.row.turnoverFlag ">
                                    <span style="margin-right: 10px;">{{scope.row.turnover}}</span>
                                </template>
                                <template v-if="scope.row.turnoverFlag==false">
                                    <el-input style="width: 100px;" v-model="scope.row.turnover" placeholder="成交量"></el-input>
                                </template>
                                <el-button
                                        style="width: 58px;float: right;"
                                        class="btnColorOrg btnUser"
                                        size="mini"
                                        @click="upTurnoverFlag(scope.row)">{{scope.row.turnoverFlag==true || undefined == scope.row.turnoverFlag?'修改':'确认'}}</el-button>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {backQryBrokerTurncverList,upBrokerAdvisory} from "../../api/infoManage/borker";
    export default {
        name: "borkerTunoverList",
        data(){
            return {
                formInline:{
                    name:null,
                    pushTime:null,
                    pageSize:3,
                    curPage:1
                },
                borkerType:[{id:1,name:'知识产权'},{id:2,name:'财税服务'},{id:3,name:'科技咨询'},{id:4,name:'科技金融'},{id:5,name:'法律服务'}
                    ,{id:6,name:'技术合同登记'},{id:7,name:'成果认定'},{id:8,name:'商务代写'},{id:9,name:'策划设计'},{id:10,name:'技术开发'},
                    {id:11,name:'教育培训'},{id:12,name:'投资孵化'}],
                turnoverFlag:true,
                backBorkerList:[],
                pageTotal:0,
                selectList:[]
            }
        },
        mounted(){
            this.backQryBrokerList();
        },
        methods:{
            upTurnoverFlag(row){
                if(row.turnoverFlag==false ){
                    const r = /^\+?[1-9][0-9]*$/;
                    upBrokerAdvisory({id:(r.test(row.seniority)?row.seniority:row.id),turnover:row.turnover}).then(response=>{
                        this.$message.success("修改成功");
                        this.backQryBrokerList();
                    });
                }
                if(undefined==row.turnoverFlag ){
                    this.$set(row,'turnoverFlag',true);
                }
                row.turnoverFlag = !row.turnoverFlag;

            },
            backQryBrokerList(){
                backQryBrokerTurncverList(this.formInline).then(response=>{
                    this.backBorkerList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryBrokerList();
            },
            handleSelectionChange(val){
                this.selectList = val;
            }
        }
    }
</script>

<style scoped>

</style>