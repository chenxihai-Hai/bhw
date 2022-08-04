<template>
    <el-dialog title="新闻通告"
            :visible.sync="newsDialogVisible"
            :close-on-click-modal="false" @close="closeData"
            width="80%" class="serviceDialog">
        <el-form :model="insertData" label-width="80px"  >
            <el-form-item label="标题">
                <el-input v-model="insertData.subject" style="width:400px;"></el-input>
            </el-form-item>
            <el-form-item label="副标题">
                <el-input v-model="insertData.subTitle" style="width:400px;"></el-input>
            </el-form-item>
            <el-form-item label="作者">
                <el-input v-model="insertData.author" style="width:400px;"></el-input>
            </el-form-item>
            <el-form-item label="文章来源">
                <el-input v-model="insertData.articleSource" style="width:400px;"></el-input>
            </el-form-item>
            <el-form-item label="类型">
                <el-select v-model="insertData.type" style="width:400px;">
                    <el-option v-for="(k,v) in newsType" :label="k" :value="v" :key="k">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="发布日期">
                <el-input v-model="insertData.uploadTime" style="width:400px;"></el-input>
            </el-form-item>
            <br/>
            <el-form-item label="图片" class="newsImgCls">
                <el-upload
                        class="avatar-uploader"
                        action="/api/serverManage/fileUpload/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                    <img v-if="insertData.img" :src="insertData.img" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <div class="btnDel"  @click="delImg" style="margin-left: 10px;margin-top: -10px;cursor: pointer;"  v-show="insertData.img"><i class="el-icon-delete"></i></div>
            </el-form-item>
            <el-form-item label="内容" class="fromContent"  >
                <el-upload
                        class="quill-avatar-uploader"
                        action="/api/serverManage/fileUpload/upload"
                        :show-file-list="false"
                        :on-success="uploadSuccess"
                        :on-error="uploadError"
                        :before-upload="beforeUpload">
                </el-upload>
                <div>
                    <quill-editor class="ql-editor"  v-model="insertData.html" ref="myQuillEditor" :options="editorOption"
                                  @change="onEditorChange($event)">
                    </quill-editor>
                </div>

            </el-form-item>
            <el-form-item size="large" style="margin-top: 120px;">
                <el-button class="btnColorOrg" @click="resetForm">返回</el-button>
                <el-button type="primary" class="btnColorBlue" @click="submitForm">确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
    import {saveBackOperate} from "../../api/admin/index";
    import {
        quillEditor
    } from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {saveNewsInfo,upNewsInfo} from "../../api/news/newsList";
    const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],  // toggled buttons
        [{'header': 1}, {'header': 2}],    // custom button values
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'indent': '-1'}, {'indent': '+1'}],   // outdent/indent
        [{'direction': 'rtl'}],       // text direction
        [{'size': ['small', false, 'large', 'huge']}], // custom dropdown
        [{'header': [1, 2, 3, 4, 5, 6, false]}],
        [{'color': []}, {'background': []}],   // dropdown with defaults from theme
        [{'font': []}],
        [{'align': []}],
        ['link', 'image'],
        ['clean']

    ];
    export default {
        name: "insertNews",
        components: {
            quillEditor
        },
        data() {
            return {
                quillUpdateImg:false,
                newsDialogVisible: false,
                newsType:{1:'国际动态',2:'国家动态',3:'地方动态',4:'通知通告'},
                editorOption: {
                    placeholder: '',
                    theme: 'snow', // or 'bubble'
                    modules: {
                        toolbar: {
                            container: toolbarOptions,
                            handlers: {
                                'image': function (value) {
                                    if (value) {
                                        // 触发input框选择图片文件
                                        document.querySelector('.quill-avatar-uploader input').click()
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        }
                    }
                },
                insertData: {
                    subject: '',
                    type: '',
                    img: '',
                    html: '',
                    author:null,
                    articleSource:null,
                    webEditor:null,
                    uploadTime:'',
                    userName:this.$store.state.user.userName,
                    userId:this.$store.state.user.id
                },
                content:''
            }
        },
        methods: {
            delImg(){
                this.insertData.img='';
            },
            onEditorChange({editor, html, text}){
                this.content = html;
            },
            // 富文本图片上传前
            beforeUpload(file) {
                // 显示loading动画
                this.quillUpdateImg = true;
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
            uploadSuccess(res, file) {
                // res为图片服务器返回的数据
                // 获取富文本组件实例
                let quill = this.$refs.myQuillEditor.quill;
                // 如果上传成功
                if (res.data ) {
                    // 获取光标所在位置
                    let length = quill.getSelection().index;
                    // 插入图片 res.url为服务器返回的图片地址
                    quill.insertEmbed(length, 'image', res.data)
                    // 调整光标到最后
                    quill.setSelection(length + 1)
                } else {
                    this.$message.error('图片插入失败')
                }
                // loading动画消失
                this.quillUpdateImg = false
            },
            // 富文本图片上传失败
            uploadError() {
                // loading动画消失
                this.quillUpdateImg = false
                this.$message.error('图片插入失败')
            },
            handleAvatarSuccess(res, file) {
                this.$set(this.insertData,'img', res.data);
                if(this.insertData.img.indexOf("-zip")>-1){
                    this.insertData.img = this.insertData.img.replace("-zip","");
                }
                this.$forceUpdate();
            },
            beforeAvatarUpload(file) {
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
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            resetForm() {
                this.newsDialogVisible = false;
                this.insertData={};
            },
            closeData(){
                this.insertData={};
            },
            submitForm() {
                if(this.insertData.subject.trim()==''){
                    this.$message.error('请输入标题!!');
                    return;
                }
                if(this.insertData.id){
                    this.insertData.createTime = undefined;
                    upNewsInfo(this.insertData).then(response=>{
                        this.newsDialogVisible = false;
                        this.$parent.qryNewsList();
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在新闻通告栏下对'+this.insertData.subject+'进行了编辑'
                        };
                        this.newsDialogVisible = false;
                        this.insertData={};
                        saveBackOperate(par).then();
                    });
                }else{
                    saveNewsInfo(this.insertData).then(response=>{
                        var par = {
                            operateName:this.$store.getters.userName,
                            operateId:this.$store.getters.backUserId,
                            content:'在新闻通告栏下发布了'+this.insertData.subject+''
                        };
                        saveBackOperate(par).then();
                        this.insertData={};
                        this.newsDialogVisible = false;
                        this.$parent.qryNewsList();
                    });
                }
            }
        }
    }
</script>

<style scoped>
   /* .avatar {
        width: 78px;
        height: 78px;
        display: block;
    }*/
   .serviceDialog /deep/ .el-form-item{margin-bottom: 15px;}
    .serviceDialog /deep/ .el-form-item.fromContent .el-form-item__content{margin-left: 40px!important;}
    /deep/.ql-toolbar.ql-snow + .ql-container.ql-snow {
        border-top: 0px;
        min-height: 300px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
   .newsImgCls /deep/.el-form-item__content{
        display: flex;
    }
</style>
