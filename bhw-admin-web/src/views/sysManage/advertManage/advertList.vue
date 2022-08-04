<template>
    <div class="advertWrap" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>检索参数</h3>
            <el-form :inline="true" :model="formSeach" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="标题">
                    <el-input placeholder="请输入" v-model="formSeach.title" class="allIpt"></el-input>
                </el-form-item>
                <el-form-item label="广告类型">
                    <el-select v-model="formSeach.advertType" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <el-option v-for="tItem in typeList" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="广告位置">
                    <el-select v-model="formSeach.typeId" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <template v-if="formSeach.advertType">
                            <el-option v-for="litem in typeList[formSeach.advertType-1].lactionList" :key="litem.lId"
                                       :label="litem.lName"
                                       :value="litem.lId">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnAdvert" @click="qryAdvertList">查询</el-button>
                    <el-button type="primary" class="btnColorBlue btnAdvert" @click="dialogFormVisible=true">+ 添加</el-button>
                </el-form-item>
            </el-form>
            <h3>数据列表</h3>
            <div class="tabBox">
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
                    <el-table-column label="图片" width="220px">
                        <template slot-scope="scope">
                            <img style="width: 129px; height: 78px" :src="scope.row.imgUrl">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="keyword"
                            label="关键字">
                    </el-table-column>
                    <el-table-column
                            prop="title"
                            label="标题" :show-overflow-tooltip="true">
                    </el-table-column>
                    <el-table-column
                            prop="orderNo"
                            label="排序">
                    </el-table-column>
                    <el-table-column label="状态" width="140px">
                        <template slot-scope="scope">
                            <el-switch
                                    :value="scope.row.status+''"
                                    inactive-value="0"
                                    active-value="1"
                                    active-color="#024ECC"
                                    inactive-color="#CCCCCC" @change="changeStatus(scope.$index,scope.row.status)">
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间" width="135px">
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-button type="text"
                                    @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button type="text"
                                    @click="handleDelete(scope.row)" style="color: #CA0000;">删除</el-button>
                            <el-button type="text" v-if="scope.row.status==1"
                                       @click="handleUp(0,scope.row)" style="color: #CA0000;">下架</el-button>
                            <el-button type="text" v-if="scope.row.status==0"
                                       @click="handleUp(1,scope.row)" style="color: #CA0000;">上架</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pageWrap">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="formSeach.curPage"
                        :page-size="formSeach.pageSize"
                        layout="prev, pager, next, jumper"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </div>
        </div>
<!--        添加/编辑 弹框-->
        <el-dialog title="添加广告图" :visible.sync="dialogFormVisible" :close-on-click-modal="false" class="allDialog" width="500px">
            <el-form :model="form">
                <el-form-item label="标题" :label-width="formLabelWidth">
                    <el-input v-model="form.title" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="关键字" :label-width="formLabelWidth">
                    <el-input v-model="form.keyword" autocomplete="off" style="width: 250px;"></el-input>
                </el-form-item>
                <el-form-item label="类型区域" :label-width="formLabelWidth">
                    <el-select v-model="form.advertType" placeholder="请选择" class="allSelect" style="width: 120px;margin-right: 10px;">
                        <el-option v-for="tItem in typeList" :key="tItem.typeId"
                                   :label="tItem.typeName"
                                   :value="tItem.typeId">
                        </el-option>
                    </el-select>
                    <el-select v-model="form.typeId" placeholder="请选择" class="allSelect" style="width: 120px;">
                        <template v-if="form.advertType!=''">
                            <el-option v-for="litem in typeList[form.advertType-1].lactionList" :key="litem.lId"
                                       :label="litem.lName"
                                       :value="litem.lId">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item label="图片" :label-width="formLabelWidth" class="uploadBox">
                    <el-upload
                            class="upload-demo"
                            action="/api/serverManage/fileUpload/upload"
                            :on-preview="handlePreview"
                            :before-upload="beforeUpload"
                            :on-success="handleSuccess"
                            :show-file-list="false"
                            list-type="picture">
                        <el-button size="small" type="primary" class="btnColorOrg">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                    <el-image v-if="form.imgUrl"
                              style="width: 100px; height: 100px"
                              :src="form.imgUrl"
                              :preview-src-list="[form.imgUrl]">
                    </el-image>
                </el-form-item>
                <el-form-item label="排序" :label-width="formLabelWidth">
                    <el-input v-model="form.orderNo" autocomplete="off" style="width: 140px;"></el-input>
                </el-form-item>
                <el-form-item label="状态" :label-width="formLabelWidth">
                    <el-checkbox v-model="form.statusFlag">显示到网站</el-checkbox>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center;">
                <el-button type="primary" class="btnColorOrg" @click="saveInfo">保存</el-button>
                <el-button class="btnColorBlue" @click="dialogFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {saveBackOperate} from "../../../api/admin/index";
    import {qryAdvertInfo,saveAdvertInfo,upAdvertInfo,delAdvertInfo} from "../../../api/advert/advertList";
    export default {
        name: "advertList",
        data(){
            return{
                formLabelWidth:'120px',
                dialogFormVisible:false,
                pageTotal:0,
                formSeach:{
                    title:null,
                    advertType:null,
                    typeId:null,
                    curPage: 1,
                    pageSize:10
                },
                form:{
                    title:'',
                    keyword:'',
                    imgUrl:'',
                    orderNo:0,
                    status:null,
                    advertType:'',
                    typeId:'',
                    statusFlag:false,
                    backUserId:this.$store.state.user.id
                },
                typeList:[{typeId:1,typeName:'banner',lactionList:[{lId:1,lName:'首页'}, {lId:2,lName:'科技市场'}, {lId:3,lName:'挂牌交易'}, {lId:4,lName:'技术经济人'}, {lId:5,lName:'资源集聚区'}, {lId:6,lName:'增值服务'}]},
                    {typeId: 2,typeName:'广告图',lactionList:[{lId:11,lName:'首页招商'}, {lId:12,lName:'首页广告'}, {lId:21,lName:'科技市场-服务管理'}, {lId:22,lName:'科技市场-政策补贴'}, {lId:23,lName:'科技市场-技术需求'}, {lId:24,lName:'科技市场-服务需求'}, {lId:25,lName:'科技市场-认证需求详情'}]}],
                dataList:[{imgUrl:['https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'],keyword:'广告',title:'首页广告图',status:true,orderNo:'1',createDate:'2020-03-24-14:30'}],
                fileList: [{url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
            }
        },
        mounted(){
            this.qryAdvertList();
        },
        methods:{
            indexMethod(index) {
                return index + 1;
            },
            handleSizeChange(val) {
                this.formSeach.curPage = val;
                this.qryAdvertList();
            },
            handleCurrentChange(val) {
                this.formSeach.curPage = val;
                this.qryAdvertList();
            },
            handleEdit(row) {
                this.form = row;
                this.dialogFormVisible = true;
            },
            handleUp(st,row){
                if(st==0){
                    this.$confirm('确认下架该信息吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        cancelButtonClass:'btnColorOrg',
                        confirmButtonClass:'btnColorBlue',
                        type: 'warning'
                    }).then(() => {
                        upAdvertInfo({id:row.id,status:st}).then(response=>{
                            this.$message.success("操作成功");
                            this.qryAdvertList();
                        })
                    })
                }else{
                    upAdvertInfo({id:row.id,status:st}).then(response=>{
                        this.$message.success("操作成功");
                        this.qryAdvertList();
                    })
                }
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
                    delAdvertInfo({ids:arr.join(",")}).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在广告管理下删除了'+row.title+''
                        };
                        saveBackOperate(par).then();
                        this.$message.success("删除成功");
                        this.qryAdvertList();
                    });
                });
            },
            changeStatus(index,val){
              console.log(index,val);
            },
            beforeUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handlePreview(file) {
                console.log(file);
            },
            handleSuccess(res){
                this.form.imgUrl = res.data;
                if(this.form.imgUrl.indexOf("-zip")>-1){
                    this.form.imgUrl = this.form.imgUrl.replace("-zip","");
                }
            },
            saveInfo(){
                this.form.status = this.form.statusFlag?1:0;
                if(this.form.id){
                    upAdvertInfo(this.form).then(response=>{
                        this.$message.success("修改成功");
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在广告管理下编辑了'+this.form.title+''
                        };
                        saveBackOperate(par).then();
                        this.dialogFormVisible = false;
                        this.qryAdvertList();
                    });
                }else{
                    saveAdvertInfo(this.form).then(response=>{
                        this.$message.success("添加成功");
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在广告管理下发布了'+this.form.title+''
                        };
                        saveBackOperate(par).then();
                        this.dialogFormVisible = false;
                        this.qryAdvertList();
                    });
                }

            },
            qryAdvertList(){
                qryAdvertInfo(this.formSeach).then(response=>{
                    this.dataList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .advertWrap h3{color: #000;font-size: 18px;font-weight: 400; padding-top: 34px;}
    .allIpt /deep/ .el-input__inner,.allSelect /deep/ .el-input__inner{height: 36px;line-height: 36px;}
    .btnAdvert{width: 84px;height: 36px;padding: 0;border: none;}
    .uploadBox /deep/ .el-form-item__content{line-height: inherit;}
    .upload-demo /deep/ .el-upload-list__item{width: 60%;}
</style>