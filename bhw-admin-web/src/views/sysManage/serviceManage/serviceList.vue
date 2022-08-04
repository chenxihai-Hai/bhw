<template>
    <div class="serviceWrap" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>增值服务（会员）开通信息列表</h3>
            <el-form :inline="true" :model="formSearch" class="demo-form-inline" style="margin-top: 33px;text-align: left;">
                <el-form-item label="服务类别">
                    <el-select v-model="formSearch.type" placeholder="请选择" style="width: 120px;margin-right: 30px;">
                        <el-option v-for="tItem in serviceType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="会员名称">
                    <el-input placeholder="请输入" v-model="formSearch.userName" class="allIpt" style="width: 150px;margin-right: 50px;"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnSchool" @click="btnSearch">查询</el-button>
                </el-form-item>
            </el-form>
            <div class="tabBox" style="padding: 20px 0;">
                <el-table
                        :data="dataList"
                        style="width: 100%" class="allTable">
                    <el-table-column
                            type="index" label="排序"
                            :index="indexMethod" width="55">
                    </el-table-column>
                    <el-table-column label="订单生成时间" prop="createDate" width="150px"></el-table-column>
                    <el-table-column
                            prop="userName"
                            label="用户名">
                    </el-table-column>
                    <el-table-column label="增值服务类型">
                        <template slot-scope="scope">{{scope.row.type==1?'普通会员':'高级会员'}}</template>
                    </el-table-column>
                    <el-table-column
                            prop="price"
                            label="开通价格/元">
                    </el-table-column>
                    <el-table-column
                            prop="amount"
                            label="实收金额/元">
                    </el-table-column>
                    <el-table-column prop="endDate" label="到期日期"></el-table-column>
                    <el-table-column label="会员状态">
                        <template slot-scope="scope">
                            <span :class="scope.row.status==1?'txtBule':'txtRed'">{{scope.row.status==1?'开通中':'已过期'}}</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pageWrap">
                <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page.sync="formSearch.currentPage"
                        :page-size="formSearch.pageSize"
                        layout="total,prev, pager, next, jumper"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "serviceList",
        data(){
            return{
                pageTotal:0,
                serviceType:[{typeId:'1',typeName:'标准会员'},{typeId: '2',typeName: '高级会员'}],
                formSearch:{userName:'',pageSize:10,currentPage:1},
                dataList:[{type:1,status:1}]
            }
        },
        created() {
            this.queryDataList();
        },
        methods: {
            indexMethod(index) {
                return index + 1;
            },
            btnSearch(){
                this.queryDataList();
            },
            queryDataList(){

            }
        }
    }
</script>

<style scoped>
    .txtBule{color: #024ECC;}
    .txtRed{color: #ca0000;}
    .serviceWrap h3{color: #000;font-size: 18px;font-weight: 400; padding-top: 34px;}
</style>