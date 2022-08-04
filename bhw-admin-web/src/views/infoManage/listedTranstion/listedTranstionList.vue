<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>挂牌交易信息检索</h3>
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
                    <el-button type="primary"  class="btnColorOrg btnQueryList" @click="backQryListedList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-button type="primary" class="btnColorBlue btnQueryList"  @click="addListedBt">发布信息</el-button>
                </el-col>
            </el-row>
            <el-row style="margin-top: 18px;">
                <el-col>
                    <el-table :data="listedList" class="allTable" >
                        <el-table-column
                                label="ID" type="index"
                                align="center">
                        </el-table-column>
                        <el-table-column
                                prop="listedName"
                                label="交易名称"
                                :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column
                                prop="listedNumber"
                                label="编号">
                        </el-table-column>
                        <el-table-column
                                prop="client"
                                label="委托方">
                        </el-table-column>
                        <el-table-column
                                prop=""
                                label="距离截至时间">
                        </el-table-column>
                        <el-table-column
                                label="交易状态">
                            <template slot-scope="scope">
                                {{scope.row.closestatus==0?'已成交':'进行中'}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="delister" :show-overflow-tooltip="true"
                                label="摘牌候选人">
                        </el-table-column>
                        <el-table-column
                                prop="notDelister" :show-overflow-tooltip="true"
                                label="摘牌人">
                        </el-table-column>
                        <el-table-column
                                label="公告状态">
                            <template slot-scope="scope">
                                {{scope.row.notDelister!=undefined?'已公告':'未公告'}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="infoTitle" :show-overflow-tooltip="true"
                                label="公告标题">
                        </el-table-column>
                        <el-table-column
                                prop="content" :show-overflow-tooltip="true"
                                label="公告详情">
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button v-if="undefined == scope.row.notDelister"
                                        size="mini"
                                        type="text"
                                        @click="handlePush(scope.row)">发布公告</el-button>
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
        <el-dialog
                title="发布交易"
                :visible.sync="addDialogVisible"
                width="50%" class="serviceDialog">
            <el-form label-position="right" label-width="120px" :model="listedInfo" ref="listedInfo">
                <el-form-item label="交易状态">
                    <el-select v-model="listedInfo.closeStatus" placeholder="交易状态">
                        <el-option v-for="(k,v) in closeStatusOpt" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="挂牌交易名称">
                    <el-input v-model="listedInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="编号">
                    <el-input v-model="listedInfo.number" ></el-input>
                </el-form-item>
                <el-form-item label="委托方">
                    <el-input v-model="listedInfo.client"></el-input>
                </el-form-item>
                <el-form-item label="交易起止时间">
                    <el-date-picker
                            format="yyyy-MM-dd HH:mm:ss"
                            v-model="Time"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="请选择时间范围起始"
                            end-placeholder="请选择时间范围结束">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="摘牌候选人">
                    <el-input v-model="listedInfo.delister"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="btnColorOrg" @click="resetForm('listedInfo')">取消</el-button>
                    <el-button type="primary" @click="submitForm">确定</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog
                title="发布公告"
                :visible.sync="addNotDialogVisible"
                width="50%">
            <el-form label-position="right" label-width="80px" :model="listedNotInfo" ref="listedNotInfo">
                <el-form-item label="交易状态">
                    <el-select v-model="listedNotInfo.closeStatus" placeholder="交易状态">
                        <el-option v-for="(k,v) in {0:'已成交'}" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="摘牌人">
                    <el-input v-model="listedNotInfo.delister"></el-input>
                </el-form-item>
                <el-form-item label="公告标题">
                    <el-input v-model="listedNotInfo.infoTitle"></el-input>
                </el-form-item>
                <el-form-item label="公告简介">
                    <el-input v-model="listedNotInfo.content"></el-input>
                </el-form-item>
                <el-form-item label="公告详情">
                    <quill-editor v-model="listedNotInfo.remark" ref="myQuillEditor" style="height: 200px;" :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item style="margin-top: 150px;">
                    <el-button type="primary" class="btnColorOrg" @click="submitNotForm">确定</el-button>
                    <el-button class="btnColorBlue"  @click="resetForm('listedNotInfo')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {
        quillEditor
    } from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {backQryListedList,saveListedTran,saveListedNot,upListedTran} from "../../../api/infoManage/listedTranstion";
    export default {
        name: "listedTranstionList",
        components: {
            quillEditor
        },
        data(){
            return {
                addNotDialogVisible:false,
                addDialogVisible:false,
                pageTotal:0,
                closeStatusOpt:{1:'未成交',0:'已成交'},
                formInline:{
                    listedNumber:null,
                    client:null,
                    notDelister:null,
                    pageSize:5,
                    curPage:1
                },
                editorOption:{},
                Time:[],
                listedInfo:{closeStatus:null,name:null,number:null,client:null,delister:null},
                listedNotInfo:{closeStatus:null,delister:null,infoTitle:null,content:null,remark:null},
                listedList:[],
                listedId:null
            }
        },
        mounted(){
            this.backQryListedList();
        },
        methods:{
            addListedBt(){
                this.addDialogVisible = true;
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryListedList();
            },
            backQryListedList(){
                backQryListedList(this.formInline).then(response=>{
                    this.listedList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            handlePush(row){
                this.listedNotInfo.number = row.listedNumber;
                this.listedId = row.id;
                this.addNotDialogVisible = true;
            },
            submitNotForm(){
                upListedTran({number:this.listedNotInfo.number,closeStatus:this.listedNotInfo.closeStatus,status:1,id:this.listedId});
                saveListedNot(this.listedNotInfo).then(response=>{
                    this.$message.success("添加成功");
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'发布了挂牌交易公告('+this.listedInfo.infoTitle+')'
                    };
                    saveBackOperate(par).then();
                    this.listedNotInfo={closeStatus:null,delister:null,infoTitle:null,content:null,remark:null};
                    this.addNotDialogVisible = false;
                    this.backQryListedList();
                });
            },
            submitForm(){
                if(this.Time.length>1){
                    this.listedInfo.startTime=this.Time[0].format("yyyy-MM-dd hh:mm:ss");
                    this.listedInfo.endTime=this.Time[1].format("yyyy-MM-dd hh:mm:ss");
                }
                saveListedTran(this.listedInfo).then(response=>{
                    this.$message.success("添加成功");
                    this.addDialogVisible = false;
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'发布了挂牌交易信息('+this.listedInfo.name+')'
                    };
                    saveBackOperate(par).then();
                    this.listedInfo={closeStatus:null,name:null,number:null,client:null,delister:null};

                    this.backQryListedList();
                });
            },
            resetForm(formName) {
                this.addDialogVisible = false;
                this.$refs[formName].resetFields();
            },
        }
    }
</script>

<style scoped>

</style>