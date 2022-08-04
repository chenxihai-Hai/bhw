<template>
    <div class="setPayPwd">
        <div style="width:980px;height:52px;background:rgba(248,255,208,1);margin:0 auto;padding-top: 17px;padding-left: 27px">
            <span style="width:109px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">设置支付密码</span>
        </div>
        <el-form :label-position="labelPosition"   label-width="200px" v-model="form" style="margin-top: 55px;margin-left: 97px">
            <el-form-item label="请设置6位数字支付密码:" prop="password">
                <el-input  type="password" v-model="form.pwd" show-password style="width: 211px;height: 32px"></el-input>
            </el-form-item>
            <el-form-item label="确认6位数字支付密码:" prop="ckeckPassword">
                <el-input  type="password" v-model="ckeckPassword" show-password style="width: 211px;height: 32px"></el-input>
            </el-form-item>
            <el-form-item label="验证码:" prop="vCode" >
                <el-row>
                    <el-col :span="3">
                        <el-input  type="text" v-model="form.vCode" style="width: 98px;height: 32px"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-image style="width: 70px;height: 32px;margin-left: 30px" :src="src"></el-image>
                    </el-col>
                    <el-col :span="4">
                        <span>看不清？<el-link  @click="captcha()" style="vertical-align: 0px;">换一张</el-link></span>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-button class="btnColorOrg button" @click="submit()">提交</el-button>
            </el-form-item>
        </el-form>
        <p class="pclass" style="margin-top: 42px">1、避免使用连续或重复的数字，类似“123456”、“112233”</p>
        <p class="pclass" style="margin-top: 15px">2、避免使用手机号、证件号中的连续数字</p>
    </div>
</template>

<script>
    import {setPayPwd} from "../../../../api/user/payPwdSet";
    export default {
        name: "setPayPwd",
        data(){
            return{
                labelPosition:'right',
                src:"",
                ckeckPassword:"",
                form:{
                    pwd:"",
                    randomCode:"",
                    vCode:"",
                    userid:this.$store.state.user.userInfo.id
                }
            }
        },
        created(){
            var randomCode=Math.random();
            this.form.randomCode =randomCode;
            this.src=this.$store.state.user.randomCodeUrl+randomCode;
        },
        methods:{
            submit(){
                if (this.form.pwd.trim()=='') {
                    this.$message.error('请输入6位数字支付密码!');
                }else if (this.ckeckPassword.trim()=='') {
                    this.$message.error('请确认输入6位数字支付密码!');
                }else if (this.form.pwd.trim()==this.ckeckPassword.trim()) {
                    if(this.form.vCode.trim()==''){
                        this.$message.error('请输入验证码!');
                    }else{
                        setPayPwd(this.form).then(response => {
                            if (response.data) {
                                this.$emit('fun','3')
                            }else{
                                this.$message.error('修改密码失败!');
                            }
                        });
                    }
                }else{
                    this.$message.error('两次输入的密码不一致!');
                }
            },
            captcha(){
                var randomCode=Math.random();
                this.form.randomCode =randomCode;
                this.src=this.$store.state.user.randomCodeUrl+randomCode;
            }
        }
    }
</script>

<style scoped>
p.pclass{
    width:400px;
    height:12px;
    font-size:12px;
    font-family:'Microsoft YaHei';
    font-weight:400;
    color:rgba(125,125,125,1);
    margin-left: 145px;
}
.btnColorOrg.button{
    width:132px;height:37px;border-radius:5px;margin-top: 69px;padding: 0;border: none;
}
</style>