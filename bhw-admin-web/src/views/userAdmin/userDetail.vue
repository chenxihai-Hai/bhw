<template>
    <div style="padding:22px 16px;">
        <div class="bgColorF" style="padding: 38px 25px;">
            <p><span>{{vipUserInfo.type==1?'标准会员':'高级会员'}}</span><span style="color:#AC0000;">{{vipUserInfo.userName}}</span>所开通的会员服务（增值服务）详情</p>
            <div>
                <el-table :data="tableData" border style="width: 100%" class="allTable">
                    <el-table-column prop="title" label="所享服务内容"></el-table-column>
                    <el-table-column
                            prop="content"
                            label="会员权益">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="已使用权益">
                    </el-table-column>
                    <el-table-column
                            prop="useDate"
                            label="使用时间">
                    </el-table-column>
                    <el-table-column
                            prop="userType"
                            label="剩余权益">
                    </el-table-column>
                    <el-table-column label="权益使用提醒">
                        <template slot-scope="scope">
                            <el-button type="text">未自动提醒</el-button>
                            <el-button type="text" class="linkNomal" @click="btnUseRemind(scope.$index, scope.row,'1')">手动提醒</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="time"
                            label="到期时间">
                    </el-table-column>
                    <el-table-column label="到期续费提醒">
                        <template slot-scope="scope">
                            <el-button type="text">未自动提醒</el-button>
                            <el-button type="text" class="linkNomal" @click="btnUseRemind(scope.$index, scope.row,'2')">手动提醒</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
<!--        提醒弹框-->
        <el-dialog :title="dialogTitle" :visible.sync="dialogReminVisible" class="allDialog" width="990px">
            <div style="padding: 0 34px;">
                <div class="dialogInfo" style="padding-top: 0; border-bottom: 1px dashed #000;">
                    <h4>会员基本信息</h4>
                    <el-row type="flex">
                        <el-col style="width: 225px;">
                            <img src="" style="width: 149px;height: 204px;" alt="">
                        </el-col>
                        <el-col>
                            <el-row class="uInfo">
                                <el-col><span>会员姓名：</span><span>{{vipUserInfo.userName}}</span></el-col>
                                <el-col><span>会员类型：</span><span>{{vipUserInfo.type==1?'标准会员':'高级会员'}}</span></el-col>
                                <el-col><span>邮箱号：</span><span>{{vipUserInfo.email}}</span></el-col>
                                <el-col><span>手机号：</span><span>{{vipUserInfo.phoneNo}}</span></el-col>
                                <el-col><span>性别：</span><span>{{vipUserInfo.sex==1?'男':'女'}}</span></el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
                <div class="dialogInfo reminInfo">
                    <h4>选择要发送的信息</h4>
                    <el-row type="flex" style="margin-bottom: 30px;">
                        <el-col>选择信息类型：</el-col>
                        <el-col>
                            <el-radio v-model="reminType" label="1">发送模版</el-radio>
                            <el-radio v-model="reminType" label="2">重新编辑</el-radio>
                        </el-col>
                    </el-row>
                    <el-row type="flex">
                        <el-col>选择要发送的信息：</el-col>
                        <el-col>
                            <template v-if="reminType==1">
                                <template v-if="reminStatus=='1'">
                                    <div>
                                        <el-checkbox v-model="reminContent">
                                            <p>尊敬的会员您好！</p>
                                            <p>您于2020·03·24·14：30使用<em>超级会员权益</em>-免费登记技术合同<em>1次</em>，剩余免费登记技术合同次数<em>2次</em>，感谢您对北部湾中心服务平台的支持！详情请查看北部湾国际技术转移转化中心服务平台-用户中心-【我的增值服务】</p>
                                        </el-checkbox>
                                    </div>
                                </template>
                                <template v-else>
                                    <div>
                                        <el-radio v-model="DueToType" label="1">即将到期提醒</el-radio>
                                        <el-radio v-model="DueToType" label="2">已到期提醒</el-radio>
                                    </div>
                                    <div style="margin-top: 20px;">
                                        <el-checkbox v-model="reminContent">
                                            <p>尊敬的会员您好！</p>
                                            <p>您于{{vipUserInfo.updateTime}}开通本平台标准会员服务，将于（{{vipUserInfo.indate}}）后到期，为保障可以继续为您提供服务，请您尽快去官网【我的增值服务】续费，或点击链接</p>
                                        </el-checkbox>
                                    </div>
                                </template>
                            </template>
                            <template v-else>
                                <quill-editor v-model="content" ref="myQuillEditor" style="margin-bottom:60px;height: 120px;width: 100%;" :options="editorOption">
                                </quill-editor>
                            </template>
                        </el-col>
                    </el-row>
                </div>
            </div>
            <div slot="footer" class="dialog-footer" style="padding: 20px 104px;">
                <el-button class="btnColorOrg btnUser" @click="saveInfo">保存</el-button>
                <el-button class="btnColorBlue btnUser" @click="dialogReminVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {quillEditor} from 'vue-quill-editor';
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {pushUserMemberMsg} from '../../api/user/userManage';
    export default {
        name: "userDetail",
        components: {
            quillEditor
        },
        data(){
            return{
                tableData:[{id:1,title:'技术合同登记',content:'免费3次+全部业务9折'},{id:2,title:'技术成果认定登记',content:'全部业务9折'},{id:3,title:'高企认定',content:'全部业务9折'}],
                dialogReminVisible:false,
                dialogTitle:'',
                reminType:'1', //1.模版  2.编辑
                reminStatus:'1',//1.续费   2.到期
                DueToType:'1',//1.即将到期  2.已到期
                content:'',
                reminContent:'',
                vipUserInfo:{}
            }
        },
        created(){
            // vipParam
            if(this.$route.query.vipParam){
                this.vipUserInfo = this.$route.query.vipParam;
            }
        },
        methods:{
            btnUseRemind(id,rowID,val){
                this.reminStatus = val;
                if(val == '1'){
                    this.dialogTitle = '会员权益使用情况提醒';
                }else{
                    this.dialogTitle='会员续费提醒';
                }

                this.dialogReminVisible = true;
            },
            saveInfo(){
                var par = {
                    phone:this.vipUserInfo.phoneNo,
                    msg:'会员'+(this.DueToType==1?'即将到期提醒':'已到期提醒')+',尊敬的会员您好！您于'+this.vipUserInfo.updateTime+'开通本平台标准会员服务，将于（'+vipUserInfo.indate+'）后到期，为保障可以继续为您提供服务，请您尽快去官网【我的增值服务】续费。'
                };
                pushUserMemberMsg(par).then(response=>{
                    if(response.data==0){
                        this.$message.success("操作成功");
                        this.dialogReminVisible = false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    /deep/ .bgColorF p{color: #000; font-size: 18px;font-weight: 400;margin-bottom: 40px;}
    /deep/ .el-button{color: #1D1D1D; font-weight: 400;}
    /deep/ .el-button--text.linkNomal{color: #1D1D1D}
    .dialogInfo{color: #000; padding: 30px 70px 40px;}
    .dialogInfo h4{color: #000;font-size: 18px;font-weight: 400;padding-bottom: 30px;}
    .uInfo .el-col{line-height: 35px;}
    .uInfo span:first-child{width: 80px;text-align: right;display: inline-block;}
    .reminInfo .el-row .el-col:first-child{width: 225px;text-align: right;padding-right: 30px;}
    .reminInfo .el-row .el-col:last-child{width: calc(100% - 225px);}
    .reminInfo em{color: #AC0000;font-style: normal;}
    .reminInfo /deep/ .el-checkbox__input{vertical-align: top;}
    .reminInfo /deep/ .el-checkbox__label{white-space: initial;}
    .btnColorBlue.btnUser,.btnColorOrg.btnUser{font-weight:400;width: 70px;height: 27px;padding: 0;}
</style>