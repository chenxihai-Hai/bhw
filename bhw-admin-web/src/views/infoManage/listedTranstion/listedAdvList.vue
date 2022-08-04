<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>挂牌交易咨询信息检索</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="编号">
                    <el-input v-model="formInline.listedNumber" placeholder="编号"></el-input>
                </el-form-item>
                <el-form-item label="委托方">
                    <el-input v-model="formInline.client" placeholder="委托方"></el-input>
                </el-form-item>
                <el-form-item label="摘牌人">
                    <el-input v-model="formInline.notDelister" placeholder="摘牌人"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList" @click="backQryListedList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-top: 18px;">
                <el-col>
                    <el-table :data="listedList" class="allTable" >
                        <el-table-column
                                label="ID" type="index"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="咨询人"
                                :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column
                                prop="phone"
                                label="联系方式">
                        </el-table-column>
                        <el-table-column
                                prop="remark"
                                label="需求信息">
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button v-if="undefined==scope.row.opreateId || null == scope.row.opreateId "
                                        size="mini"
                                        type="text" @click="upAdvSt(scope.row.id)"
                                        >联系他/她</el-button>
                                <el-button v-else
                                           size="mini"
                                           type="text"
                                >已联系</el-button>
                            </template>
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
    import {listedAdvisoryList,uplistedAdvisory} from "../../../api/infoManage/listedTranstion";
    export default {
        name: "listedAdvList",
        data(){
            return {
                pageTotal:0,
                formInline:{
                    listedNumber:null,
                    client:null,
                    notDelister:null,
                    pageSize:5,
                    curPage:1
                },
                listedList:[]
            }
        },
        mounted(){
            this.backQryListedList();
        },
        methods:{
            upAdvSt(id){
                this.$confirm('是否确认联系客户?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var par = {
                        opreateName: this.$store.getters.userName,
                        opreateId: this.$store.getters.backUserId,
                        id:id
                    }
                    uplistedAdvisory(par).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        this.backQryListedList();
                    });
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryListedList();
            },
            backQryListedList(){
                listedAdvisoryList(this.formInline).then(response=>{
                    this.listedList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>

</style>