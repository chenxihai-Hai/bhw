<template>
    <div>
        <div class="resourceWrap" style="padding: 22px 16px 0;">
            <div class="bgColorF" style="padding: 10px 25px;">
                <h3>{{$route.query.schoolName}}科研成果列表</h3>
                <div class="tabBox" style="padding: 20px 0;">
                    <el-table
                            :data="dataList"
                            style="width: 100%" class="allTable">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                type="index" label="编号"
                                :index="indexMethod" width="55">
                        </el-table-column>
                        <el-table-column label="logo" width="150px">
                            <template slot-scope="scope">
                                <img style="width: 129px; height: 78px" :src="scope.row.patentimg">
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="patentname"
                                label="成果名称">
                        </el-table-column>
                        <el-table-column
                                prop="content"
                                label="成果详情" :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column
                                prop="dailiren"
                                label="联系人">
                        </el-table-column>
                        <el-table-column prop="dailiJigou" label="联系方式">
                        </el-table-column>
                        <el-table-column
                                prop="shenqingdate"
                                label="成立时间">
                        </el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="text"
                                           @click="handleEdit(scope.row)">编辑</el-button>
                                <el-button type="text"
                                           @click="handleDelete(scope.row)" style="color: #CA0000;">删除</el-button>
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
        <el-dialog
                title="成果详情"
                :visible.sync="patentDialogVisible"
                width="60%" >
            <el-form :model="formResult" class="demo-form" label-position="right" >
                <el-form-item label="成果名称" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formResult.patentname" class="allIpt" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="专利号" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formResult.patentcode" class="allIpt" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="申请日" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formResult.shenqingdate" class="allIpt" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formResult.dailiren" class="allIpt" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="联系人电话" :label-width="formLabelWidth">
                    <el-input placeholder="请输入" v-model="formResult.dailiJigou" class="allIpt" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="成果图片" :label-width="formLabelWidth">
                    <el-upload
                            class="upload-demo"
                            action="/api/serverManage/fileUpload/upload"
                            :on-remove="handleRemove"
                            :on-success="handleMoreSuccess"
                            :file-list="fileList"
                            list-type="picture">
                        <el-button size="small" class="btnColorOrg btnClickUp" @click="recordUp(index)" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="成果详情" :label-width="formLabelWidth">
                    <quill-editor v-model="formResult.content" ref="myQuillEditor" style="height: 200px;width:80%;margin-bottom: 100px;" :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item style="margin-top: 100px;text-align: center;">
                    <el-button type="primary" class="btnColorOrg" @click="subInfo">保存</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {getPatentList,delPatentBean,upPatentBean} from "../../../api/school/school";
    import {quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    export default {
        name: "resultList",
        components: {
            quillEditor
        },
        data(){
            return{
                formLabelWidth:'150px',
                patentDialogVisible:false,
                pageTotal:0,
                formSearch:{curPage:1,pageSize:15,schoolid:null},
                dataList:[],
                formResult:{},
                fileList:[],
                editorOption:{}
            }
        },
        mounted(){
            if(this.$route.query.param){
                this.formSearch.schoolid = this.$route.query.param;
            }
            this.loadData();
        },
        methods: {
            handleRemove(file, fileList) {
                this.formResult.moreFile[file.url] = '';
            },
            handleMoreSuccess(res, file){
                this.formResult.moreFile[file.url] = res.data;
            },
            subInfo(){
                for (var p in  this.formResult.moreFile) {
                    if (this.formResult.moreFile[p] != '' && null != this.formResult.moreFile[p]) {
                        var ig = this.formResult.moreFile[p];
                        if (null == ig || '' == ig) {
                            continue;
                        }
                        if (ig.indexOf("-zip") > -1) {
                            ig = ig.replace('-zip', '');
                        }
                        this.formResult.patentimg = this.formResult.patentimg + ig + ";";
                    }
                }
                if (null == this.formResult.patentname || this.formResult.patentname.trim() == '') {
                    this.$message.error('成果名称不能为空!');
                    return;
                }
                if (null == this.formResult.patentcode || this.formResult.patentcode.trim() == '') {
                    this.$message.error('专利号不能为空!');
                }
                upPatentBean(this.formResult).then(response => {
                    this.$message.success("编辑成功");
                    this.patentDialogVisible=false;
                    this.formResult={};
                    this.loadData();
                });
            },
            loadData(){
                getPatentList(this.formSearch).then(response=>{
                    this.dataList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            indexMethod(index) {
                return index + 1;
            },
            handleCurrentChange(val) {
                this.formSearch.curPage = val;
               this.loadData();
            },
            handleEdit( row) {
                this.patentDialogVisible=true;
                this.formResult = row;
            },
            handleDelete( row) {
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var arr=[];
                    if(row){
                        arr.push(row.id);
                    }
                    delPatentBean({idList:arr.join(',')}).then(resposne=>{
                        this.$message.success("删除成功");
                        this.loadData();
                    });
                });
            },
        }
    }
</script>

<style scoped>

</style>