<template>
    <div class="forgetPassword" >
        <div class="forgetBox">
            <el-row type="flex" justify="space-between" class="topWrap" style="margin-top: 46px;">
                <el-col :span="12"><span style="margin-right: 40px;">北部湾国际技术转移转化中心公共服务平台</span><span>账号安全</span></el-col>
                <el-col :span="12" style="text-align: right;font-size: 14px;">
                    <template v-if="$store.state.user.userInfo">
                        <span>欢迎用户:</span>
                        <router-link :to="{path:'/userCenter'}" tag="a" class="routerLink-a" style="color: #CA0000">{{$store.state.user.userInfo.userName}}</router-link>
                    </template>
                    <template v-else>
                        <span>已有账号，</span>
                        <router-link :to="{path:'/viplogin'}">马上登录</router-link>
                    </template>
                    <span style="margin: 0 10px;">|</span>
                    <router-link :to="{path:'/home'}">返回首页</router-link>
                </el-col>
            </el-row>
            <div class="backPwd" style="margin-top: 56px">
                <h2>账号安全</h2>
                <div style="width: 475px;margin: 86px auto 0;">
                    <h3>找回密码</h3>
                    <el-form :model="pwdForm" ref="pwdForm"  :rules="rules" style="margin-top: 46px;" class="demo-ruleForm" v-show="showForm">
                        <el-form-item prop="phone" :rules="[{required:true,message:'手机号码不能为空'}]">
                            <el-input style="width: 100%" v-model="pwdForm.phone"  placeholder="请输入手机号码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <div class="drag" ref="dragDiv">
                                <div class="drag_bg"></div>
                                <div class="drag_text">{{confirmWords}}</div>
                                <div ref="moveDiv" @mousedown="mousedownFn($event)" :class="{'handler_ok_bg':confirmSuccess}" class="handler handler_bg" style="position: absolute;top: 0px;left: 0px;"></div>
                            </div>
                        </el-form-item>
                        <el-form-item prop="verifyCode" :rules="[{required:true,message:'手机验证码不能为空'}]">
                            <el-input style="width: 72%" v-model="pwdForm.verifyCode" type="phoneCaptcha"  placeholder="请输入验证码"></el-input>
                            <el-button class="btnNoBgdBule" style="margin-left: 10px;" @click="Yzmsubmit()" v-if="disabledFlag==false">{{yzmTitle}}</el-button>
                            <el-button class="btnColorBlue" style="width: 122px;height: 40px;padding: 0; margin-left: 10px;cursor: default;"  v-if="disabledFlag">{{yzmTitle}}</el-button>
                        </el-form-item>
                        <el-form-item  prop="newPwd">
                            <el-input style="width: 100%" v-model="pwdForm.newPwd"  placeholder="请输入新密码" type="password" show-password></el-input>
                        </el-form-item>
                        <el-form-item prop="checkPwd" style="margin-top: 5px;">
                            <el-input style="width: 100%" v-model="pwdForm.checkPwd" placeholder="请输入确认密码" type="password" show-password></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button class="btnColorOrg" style="margin-top: 14px;width: 100%;height:46px;border: none;padding: 0;"  @click="submitForm('pwdForm',pwdForm)">下一步</el-button>
                        </el-form-item>
                    </el-form>
                    <div style="text-align: center;" v-show="showSucessBox">
                        <p style="margin: 62px 0 123px;">您已成功设置密码，请使用新密码登录</p>
                        <el-button style="color: #FFF; margin-top: 14px;width: 100%;height:46px;background:#FB7D26;border: none;" @click="setSuccess">马上登录</el-button>
                    </div>
                </div>
            </div>
            <div class="footWrap">
                <p>Copyright2017-2020 one All Rights Reserved.版权所有@广西对接平台科技发展有限公司 </p>
                <p>地址：广西壮族自治区北海市银海区四川路299号穗丰金湾1栋0201 | 邮编：530000 </p>
            </div>
        </div>
    </div>
</template>

<script>
    import {forgetPassword,checkCaptchaReq,getUserByPhone} from "../../api/register/register";
    export default {
        name: "forgetPassword",
        data() {
            var validatePass2 = (rule, value, callback) => {
                console.log(value);
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.pwdForm.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                disabledFlag:false,
                yzmTitle:"发送验证码",
                pwdForm: {newPwd: '', checkPwd: '',phone:"",
                    verifyCode:''},
                rules: {
                    newPwd: [{required: true, message: '请输入密码', trigger: 'blur' }],
                    checkPwd: [{required: true,validator: validatePass2, trigger: 'blur'}]
                },
                showForm:true,
                showSucessBox:false,
                yzmTime:120,
                yzmTimer:null,
                beginClientX:0,           /*距离屏幕左端距离*/
                mouseMoveStata:false,     /*触发拖动状态  判断*/
                maxwidth:'',               /*拖动最大宽度，依据滑块宽度算出来的*/
                confirmWords:'拖动滑块验证',   /*滑块文字*/
                confirmSuccess:false           /*验证成功判断*/
            }
        },
        mounted(){
            this.maxwidth = this.$refs.dragDiv.clientWidth - this.$refs.moveDiv.clientWidth;
            document.getElementsByTagName('html')[0].addEventListener('mousemove',this.mouseMoveFn);
            document.getElementsByTagName('html')[0].addEventListener('mouseup',this.moseUpFn)
        },
        methods: {
            mousedownFn:function (e) {
                if(this.pwdForm.phone.trim()==''){
                    this.$message({
                        message: '请先输入手机号码!',
                        type: 'warning'
                    });
                    return;
                }
                getUserByPhone({phoneNo:this.pwdForm.phone}).then(response=> {
                    if (response.data.length < 1) {
                        this.$message.error('手机号码不存在!');
                        return;
                    }
                    if(!this.confirmSuccess){
                        e.preventDefault && e.preventDefault();   //阻止文字选中等 浏览器默认事件
                        this.mouseMoveStata = true;
                        this.beginClientX = e.clientX;
                    }
                });
            },        //mousedoen 事件
            successFunction(){
                this.confirmSuccess = true;
                this.confirmWords = '验证通过';
                if(window.addEventListener){
                    document.getElementsByTagName('html')[0].removeEventListener('mousemove',this.mouseMoveFn);
                    document.getElementsByTagName('html')[0].removeEventListener('mouseup',this.moseUpFn);
                }else {
                    document.getElementsByTagName('html')[0].removeEventListener('mouseup',()=>{});
                }
                document.getElementsByClassName('drag_text')[0].style.color = '#fff'
                document.getElementsByClassName('handler')[0].style.left = this.maxwidth + 'px';
                document.getElementsByClassName('drag_bg')[0].style.width = this.maxwidth + 'px';
            },
            mouseMoveFn(e){

                if(this.mouseMoveStata){
                    let width = e.clientX - this.beginClientX;
                    if(width>0 && width<=this.maxwidth){
                        document.getElementsByClassName('handler')[0].style.left = width + 'px';
                        document.getElementsByClassName('drag_bg')[0].style.width = width + 'px';
                    }else if(width>this.maxwidth){
                        this.successFunction();
                    }
                }
            },
            moseUpFn(e){
                this.mouseMoveStata = false;
                var width = e.clientX - this.beginClientX;
                if(width<this.maxwidth){
                    document.getElementsByClassName('handler')[0].style.left = 0 + 'px';
                    document.getElementsByClassName('drag_bg')[0].style.width = 0 + 'px';
                }
            },
            Yzmsubmit(){
                if(!this.confirmSuccess){
                    this.$message({
                        message: '请先拖动滑块验证!',
                        type: 'warning'
                    });
                    return;
                }
                if(this.pwdForm.phone.trim()==''){
                    this.$message({
                        message: '请输入手机号码!',
                        type: 'warning'
                    });
                    return;
                }
                checkCaptchaReq({randomCode:1234,vCode:1234,phone:this.pwdForm.phone}).then(response => {
                    if (response.data) {
                        var that = this;
                        this.yzmTimer = setInterval(function () {
                            that.disabledFlag = true;
                            if (that.yzmTime == 1) {
                                that.yzmTitle = "发送验证码";
                                that.disabledFlag = false;
                                window.clearInterval(that.yzmTimer);
                                that.yzmTimer = null;
                                that.yzmTime = 120;
                            } else {
                                that.yzmTitle = "请在" + that.yzmTime + "秒内输入";
                                that.yzmTime = that.yzmTime - 1;
                            }
                        }, 1000);
                    } else {
                        this.$message({
                            message: '验证码发送失败!',
                            type: 'warning'
                        });
                    }
                })
            },
            submitForm(formName,pwdForm){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        getUserByPhone({phoneNo:this.pwdForm.phone}).then(response=>{
                            if(response.data.length<1){
                                this.$message.error('手机号码不存在!');
                                return;
                            }
                            var req={newPwd:this.pwdForm.newPwd,phoneCaptch:this.pwdForm.verifyCode,phone:this.pwdForm.phone,userId:response.data[0].id}
                            forgetPassword(req).then(response => {
                                if (response.data) {
                                    this.showSucessBox =true;
                                    this.showForm = false;
                                }else{
                                    this.$message.error('验证码错误!');
                                }
                            });
                        });
                    }
                })
            },
            setSuccess(){
                this.$router.push({path:'/viplogin'});
            }
        },
    }
</script>

<style scoped>
    .demo-ruleForm /deep/ .el-input{width: 45%;}
    .forgetBox{width: 1240px;margin: 0 auto;}
    .topWrap{color: #000;font-size: 18px;}
    .topWrap a{color: #CA0000;text-decoration:none;}
    .backPwd{width:100%;height:628px;box-shadow:rgba(0, 0, 0, 0.07) 0px 4px 8px 1px;text-align: center;}
    .backPwd h2{width: 100%;height: 83px;line-height: 83px;background: #3466FF;color: #FFF;font-size: 30px;text-align: center;}
    .backPwd h3{color: #000;font-size: 24px;}
    .footWrap{font-size: 14px;color: #7D7D7D;text-align: center;margin: 110px 0 150px;line-height: 24px;}
    .sendCode{height: 38px;background: #FFCD55;margin-left: 20px;border: 1px solid #FFCD55;}
    /deep/ .sendCode.el-button.el-button--default span{color: #CA0000; }
    .drag{
        position: relative;
        background-color: #e8e8e8;
        width: 100%;
        height: 34px;
        line-height: 34px;
        text-align: center;
    }
    .handler{
        width: 40px;
        height: 32px;
        border: 1px solid #ccc;
        cursor: move;
    }
    .handler_bg{
        background: #fff url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NTEyNTVEMURGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NTEyNTVEMUNGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo2MTc5NzNmZS02OTQxLTQyOTYtYTIwNi02NDI2YTNkOWU5YmUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+YiRG4AAAALFJREFUeNpi/P//PwMlgImBQkA9A+bOnfsIiBOxKcInh+yCaCDuByoswaIOpxwjciACFegBqZ1AvBSIS5OTk/8TkmNEjwWgQiUgtQuIjwAxUF3yX3xyGIEIFLwHpKyAWB+I1xGSwxULIGf9A7mQkBwTlhBXAFLHgPgqEAcTkmNCU6AL9d8WII4HOvk3ITkWJAXWUMlOoGQHmsE45ViQ2KuBuASoYC4Wf+OUYxz6mQkgwAAN9mIrUReCXgAAAABJRU5ErkJggg==") no-repeat center;
    }
    .handler_ok_bg{
        background: #fff url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NDlBRDI3NjVGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDlBRDI3NjRGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDphNWEzMWNhMC1hYmViLTQxNWEtYTEwZS04Y2U5NzRlN2Q4YTEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+k+sHwwAAASZJREFUeNpi/P//PwMyKD8uZw+kUoDYEYgloMIvgHg/EM/ptHx0EFk9I8wAoEZ+IDUPiIMY8IN1QJwENOgj3ACo5gNAbMBAHLgAxA4gQ5igAnNJ0MwAVTsX7IKyY7L2UNuJAf+AmAmJ78AEDTBiwGYg5gbifCSxFCZoaBMCy4A4GOjnH0D6DpK4IxNSVIHAfSDOAeLraJrjgJp/AwPbHMhejiQnwYRmUzNQ4VQgDQqXK0ia/0I17wJiPmQNTNBEAgMlQIWiQA2vgWw7QppBekGxsAjIiEUSBNnsBDWEAY9mEFgMMgBk00E0iZtA7AHEctDQ58MRuA6wlLgGFMoMpIG1QFeGwAIxGZo8GUhIysmwQGSAZgwHaEZhICIzOaBkJkqyM0CAAQDGx279Jf50AAAAAABJRU5ErkJggg==") no-repeat center;
    }
    .drag_bg{
        background-color: #7ac23c;
        height: 34px;
        width: 0px;
    }
    .drag_text{
        position: absolute;
        top: 0px;
        width: 100%;text-align: center;
        -moz-user-select: none;
        -webkit-user-select: none;
        user-select: none;
        -o-user-select:none;
        -ms-user-select:none;
    }
</style>