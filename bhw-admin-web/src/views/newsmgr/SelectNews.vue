<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="名称">
                    <el-input v-model="formInline.title" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <el-input v-model="formInline.type" placeholder="请输入类型"></el-input>
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker
                            v-model="formInline.date"
                            type="daterange"
                            value-format="yyyy-MM-dd"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button  class="btnColorOrg btnSchool" @click="qryNewsList">查询</el-button>
                    <el-button type="primary"  class="btnColorBlue btnQueryList" @click="addNews">添加</el-button>
                </el-form-item>
            </el-form>
            <el-table
                    :data="tableData"
                    class="allTable"
                    border
                   >
                <el-table-column
                        prop="subject"
                        label="标题"
                        :show-overflow-tooltip="true"
                        >
                </el-table-column>
                <el-table-column label="类型" width="100">
                    <template slot-scope="scope">
                        <template v-if="scope.row.type==1">
                            国际动态
                        </template>
                        <template v-else-if="scope.row.type==2">
                            国家动态
                        </template>
                        <template v-else-if="scope.row.type==3">
                            地方动态
                        </template>
                        <template v-else-if="scope.row.type==4">
                            通知通告
                        </template>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="picture"
                        label="图片" align="center">
                    <template slot-scope="scope">
                        <el-image
                                style="width: 50px; height: 50px"
                                :src="scope.row.img"
                                :preview-src-list="[scope.row.img]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column
                        label="内容" width="80">
                    <template slot-scope="scope">
                        <span style="color: #024ECC;cursor: pointer;" @click="showCt(scope.row.html)">预览</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="userName"
                        label="发表人"
                       >
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="发表时间"
                        >
                </el-table-column>
                <el-table-column label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                style="width: 59px;height: 24px;"
                                class="btnColorOrg"
                                @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <el-button
                                class="btnColorBlue"
                                style="width: 59px;height: 24px;"
                                size="mini"
                                type="primary"
                                @click="handleDelete(scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal"  class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
            <el-dialog
                    title="内容预览"
                    :visible.sync="showHtmlCt"
                    width="60%"
                    class="serviceDialog">
                <div v-html="shtml" class="ql-editor"></div>
            </el-dialog>
            <insertNews v-if="insertNewsFlag" ref="insertNewRef"></insertNews>
        </div>
    </div>
</template>

<script>
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {saveBackOperate} from "../../api/admin/index";
    import {qryNewsList,delNewsInfo} from "../../api/news/newsList";
    import insertNews from "../newsmgr/insertNews";
    export default {
        name: "select-news",
        comments: { insertNews },
        data() {
            return {
                showHtmlCt:false,
                insertNewsFlag:false,
                shtml:'',
                formInline:
                    {
                        title: null,
                        type: null,
                        date: '',
                        pageSize:5,
                        curPage:1
                    },
                pageTotal:0,
                tableData: []
            }
        },
        mounted(){
            this.qryNewsList();
        },
        methods: {
            showCt(html){
                this.shtml = html;
                this.showHtmlCt = true;
            },
            addNews(){
                this.insertNewsFlag = true;
                this.$nextTick(function() {
                    this.$refs.insertNewRef.newsDialogVisible = true;
                });
            },
            qryNewsList(){
                qryNewsList(this.formInline).then(response=>{
                    this.tableData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.qryNewsList();
            },
            handleSelect() {
            },
            handleEdit(obj) {
               /* this.$router.push({
                    name: "update-news",
                    params: {
                        title: row.title,
                        type: row.type,
                        pictureName: row.pictureName,
                        picturePath: row.picturePath,
                        href: row.href,
                        content: row.content
                    }
                });*/
                this.insertNewsFlag = true;
                this.$nextTick(function() {
                    this.$refs.insertNewRef.insertData = obj;
                    this.$refs.insertNewRef.insertData.img = undefined==this.$refs.insertNewRef.insertData.img?'':this.$refs.insertNewRef.insertData.img
                    this.$refs.insertNewRef.newsDialogVisible = true;
                });
            },
            handleDelete(row) {
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var arra = [];
                    arra.push(row.id);
                    delNewsInfo({ids:arra.join(",")}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在新闻通告栏下删除了'+row.subject+''
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qryNewsList();
                    });
                }).catch(() => {
                });
            }
        }
    }
</script>



