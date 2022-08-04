<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>技术经纪人管理-企业</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="发布时间">
                    <el-input v-model="formInline.pushTime" placeholder="发布时间"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="formInline.name" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg btnQueryList" @click="backQryBrokerList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row style="margin-bottom: 32px;">
                <el-col>
                    <el-button class="btnColorOrg btnQueryList" @click="delBorker(undefined)">删除</el-button>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addBorkerBt">添加</el-button>
                    <el-button class="btnNoBgdBlue btnQueryList"  @click="batchUpAuditStatus(1,undefined)">一键驳回</el-button>
                    <el-button class="btnNoBgdOrg btnQueryList"  @click="batchUpAuditStatus(3,undefined)">一键通过</el-button>
                </el-col>
            </el-row>
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
                                label="发布人信息" >
                            <el-table-column
                                    label="用户名-昵称" prop="userName"
                                    align="center">
                            </el-table-column>
                            <el-table-column
                                    label="认证"
                                    align="center">
                                <template slot-scope="scope">
                                    <template v-if="undefined != scope.row.userStatus">
                                        {{scope.row.userStatus==1?'已认证':'未认证'}}
                                    </template>
                                </template>
                            </el-table-column>
                        </el-table-column>
                        <el-table-column
                                label="经纪人信息" >
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
                                    label="真实名称" prop="name"
                                    align="center">
                            </el-table-column>
                            <el-table-column
                                    label="性别"
                                    align="center">
                                <template slot-scope="scope">
                                    {{scope.row.sex==1?'男':'女'}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="邮箱" prop="email"
                                    align="center">
                            </el-table-column>
                            <el-table-column
                                    label="手机号码" prop="phone"
                                    align="center">
                            </el-table-column>
                            <el-table-column
                                    label="从事行业领域" prop="brokerName"
                                    align="center">
                            </el-table-column>
                            <el-table-column
                                    label="从事工龄" prop="seniority"
                                    align="center">
                            </el-table-column>
                           <!-- <el-table-column
                                    label="成交量" prop="turnover"
                                    align="center">
                                <template slot-scope="scope" >
                                    <template v-if="scope.row.turnoverFlag==true || undefined == scope.row.turnoverFlag ">
                                        {{scope.row.turnover}}
                                    </template>
                                    <template v-if="scope.row.turnoverFlag==false">
                                        <el-input v-model="scope.row.turnover" placeholder="成交量"></el-input>
                                    </template>
                                    <el-button
                                            style="width: 58px;"
                                            class="btnColorOrg btnUser"
                                            size="mini"
                                            @click="upTurnoverFlag(scope.row)">{{scope.row.turnoverFlag==true || undefined == scope.row.turnoverFlag?'修改':'确认'}}</el-button>
                                </template>
                            </el-table-column>-->
                            <el-table-column label="操作" align="center" width="180">
                                    <template slot-scope="scope" >
                                        <el-button
                                                style="width: 58px;"
                                                class="btnColorOrg btnUser"
                                                size="mini"
                                                @click="upBorker(scope.row)">编辑</el-button>
                                        <el-button
                                                class="btnColorBlue btnUser"
                                                size="mini"
                                                type="primary"
                                                @click="delBorker(scope.row)">删除</el-button>
                                        <br/>
                                        <el-button
                                                style="width: 58px;margin-top: 5px;"
                                                class="btnNoBgdOrg btnUser"
                                                size="mini"
                                                @click="batchUpAuditStatus(3,scope.row)">通过</el-button>
                                        <el-button
                                                class="btnNoBgdBlue btnUser"
                                                size="mini"
                                                @click="batchUpAuditStatus(1,scope.row)">驳回</el-button>
                                    </template>
                            </el-table-column>
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
        <addBorker ref="addBorkerRef"></addBorker>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {backQryBrokerList,delBrokerRegister,upBrokerAdvisory} from "../../../api/infoManage/borker";
    import addBorker from "../borker/addBorker";
    export default {
        name: "borkerList-m",
        comments: { addBorker },
        data(){
            return {
                formInline:{
                    name:null,
                    pushTime:null,
                    userType:2,
                    pageSize:3,
                    curPage:1
                },
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
                    upBrokerAdvisory({id:row.id,turnover:row.turnover}).then(response=>{
                        this.$message.success("修改成功");
                    });
                }
                if(undefined==row.turnoverFlag ){
                    this.$set(row,'turnoverFlag',true);
                }
                row.turnoverFlag = !row.turnoverFlag;

            },
            addBorkerBt(){
                this.$nextTick(function() {
                    this.$refs.addBorkerRef.borkerDialogVisible = true;
                });
            },
            backQryBrokerList(){
                backQryBrokerList(this.formInline).then(response=>{
                    this.backBorkerList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            batchUpAuditStatus(st,row){
                var arr = [];
                if(row){
                    arr.push(row.id);
                }else{
                    this.selectList.forEach(item=>{
                        arr.push(item.id);
                    })
                }
                if(arr.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return ;
                }
                if(st==3){
                    var promList=[];
                    arr.forEach(id=>{
                        var par = new Promise((resolve, reject) => {
                            upBrokerAdvisory({id:id,status:st}).then(response=>{
                                resolve(response.data);
                            });
                        });
                        promList.push(par);
                    })
                    Promise.all(promList).then(response=>{
                        this.$message.success("操作成功");
                        var ct = '';
                        if(row){
                            ct = st==3?'对'+row.name+'经纪人的审核结果为通过':'对'+row.name+'经纪人的审核进行了驳回';
                        }else{
                            ct = st==3?'对经纪人的审核结果进行了批量通过':'对经纪人的审核进行了批量驳回';
                        }
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:ct
                        };
                        saveBackOperate(par).then();
                        this.backQryBrokerList();
                    });
                }else{
                    this.$prompt('请输入驳回原因', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        cancelButtonClass:'btnColorOrg',
                        confirmButtonClass:'btnColorBlue'
                    }).then(({ value }) => {
                        var promList=[];
                        arr.forEach(id=>{
                            var par = new Promise((resolve, reject) => {
                                upBrokerAdvisory({id:id,status:st,result:value}).then(response=>{
                                    resolve(response.data);
                                });
                            });
                            promList.push(par);
                        })
                        Promise.all(promList).then(response=>{
                            this.$message.success("操作成功");
                            var ct = '';
                            if(row){
                                ct = st==3?'对'+row.name+'经纪人的审核结果为通过':'对'+row.name+'经纪人的审核进行了驳回,驳回原因为:'+value;
                            }else{
                                ct = st==3?'对经纪人的审核结果进行了批量通过':'对经纪人的审核进行了批量驳回';
                            }
                            var par = {
                                operateName:this.$store.getters.userName,
                                operateId:this.$store.getters.backUserId,
                                content:ct
                            };
                            saveBackOperate(par).then();
                            this.backQryBrokerList();
                        });
                    }).catch(()=>{})
                }
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryBrokerList();
            },
            handleSelectionChange(val){
                this.selectList = val;
            },
            upBorker(row){
                this.$nextTick(function() {
                    this.$refs.addBorkerRef.manInfo = row;
                    this.$refs.addBorkerRef.borkerDialogVisible = true;
                });
            },
            delBorker(row){
                var arr = [];
                if(row){
                    arr.push(row.id);
                }else{
                    this.selectList.forEach(function(item,index){
                        arr.push(item.id);
                    })
                }
                if(arr.length<1){
                    this.$message.warning('请勾选需要操作的数据!');
                    return;
                }
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {

                    delBrokerRegister({idList:arr.join(',')}).then(response=>{
                        this.$message.success("删除成功");
                        this.backQryBrokerList();
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'删除了'+row.name+'经纪人'
                        };
                        saveBackOperate(par).then();
                    });
                }).catch(() => {
                });
            }
        }
    }
</script>

<style scoped>

</style>