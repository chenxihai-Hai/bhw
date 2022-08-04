<template>
    <div class="schoolWrap" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formSeach" class="demo-form-inline" style="margin: 33px 40px 0;">
                <el-form-item label="高校名称" style="margin-right: 60px;">
                    <el-input placeholder="请输入" v-model="formSeach.name" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="国内外" style="margin-right: 35px;">
                    <el-select v-model="formSeach.typeId" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <el-option v-for="tItem in schoolType" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnSchool" @click="btnSearch">查询</el-button>
                    <el-button type="primary" class="btnColorBlue btnSchool" @click="btnCreate">+ 高校</el-button>
                </el-form-item>
            </el-form>
            <h3>数据列表</h3>
            <div class="tabBox">
                <el-table :data="schoolData" style="width: 100%" class="allTable">
                    <el-table-column
                            type="selection"
                            width="55">
                    </el-table-column>
                    <el-table-column
                            type="index" label="编号"
                            :index="indexMethod" width="55">
                    </el-table-column>
                    <el-table-column label="高校logo" width="200px">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.photo">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="高校名称">
                    </el-table-column>
                    <el-table-column label="国内外" width="80px">
                        <template slot-scope="scope">{{scope.row.type==1?'国内':'国外'}}</template>
                    </el-table-column>
                    <el-table-column
                            label="定位">
                        <template slot-scope="scope">{{scope.row.collegeProvince}}&nbsp;{{scope.row.collegeCity}}</template>
                    </el-table-column>
                    <el-table-column label="科研成果">
                        <template slot-scope="scope"><router-link class="linkDetail" :to="{path:'/resultList',query:{param:scope.row.id,schoolName:scope.row.name}}">查看成果列表</router-link></template>
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间" width="135px">
                    </el-table-column>
                    <el-table-column label="操作" width="140px">
                        <template slot-scope="scope">
                            <el-button type="text"
                                       @click="handleEdit(scope.row)">查看详情</el-button>
                            <el-button type="text"
                                       @click="handleDelete(scope.row)" style="color: #CA0000;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pageWrap">
                <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page.sync="formSeach.curPage"
                        :page-size="formSeach.pageSize"
                        layout="total,prev, pager, next, jumper"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryCollegeList,delSchoolInfo} from "../../../api/school/school";
    export default {
        name: "schoolList",
        data(){
            return{
                pageTotal:0,
                formSeach: {name:null,type:null,curPage: 1, pageSize:5},
                schoolType:[{typeId:1,typeName:'国内'},{typeId:0,typeName:'国外'}],
                schoolData:[{id:'1',imgUrl:'',name:'中山大学',typeId:'1',areaName:'广东 中山',createDate:'2020-03-24 14:30'}]
            }
        },
        created() {
            this.qrySchoolDataList();
        },
        methods:{
            indexMethod(index) {
                return index + 1;
            },
            handleCurrentChange(val) {
                this.formSeach.curPage = val;
                this.qrySchoolDataList();
            },
            handleEdit(row) {
                this.$router.push({path:'/create',query:{schoolDet:row}})
            },
            handleDelete(row) {
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    var arr = [];
                    if(row){
                        arr.push(row.id);
                    }
                    delSchoolInfo({idList:arr.join(',')}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在资源聚集区下对高校'+ row.name+'进行了删除'
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qrySchoolDataList();
                    })
                })
            },
            btnSearch(){
                this.qrySchoolDataList();
            },
            btnCreate(){
                this.$router.push({path:'/create'})
            },
            qrySchoolDataList(){
                qryCollegeList(this.formSeach).then(response=>{
                    this.schoolData = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .schoolWrap h3{color: #000;font-size: 18px;font-weight: 400; padding-top: 34px;}
    .allIpt /deep/ .el-input__inner,.allSelect /deep/ .el-input__inner{height: 36px;line-height: 36px;}
    .btnSchool{width: 84px;height: 36px;padding: 0;border: none;}
    .linkDetail{color: #024ECC;text-decoration: none;}
</style>