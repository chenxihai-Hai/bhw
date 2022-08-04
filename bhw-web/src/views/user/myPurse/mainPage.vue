<template>
    <div class="mainPage" style="padding: 0 17px 100px">
        <el-row :style="'margin-bottom: 4px;'+displaydata">
            <el-menu :default-active="mainPageMenu" class="el-menu-demo" mode="horizontal" @select="MenuSelect">
                <el-menu-item index="0">账户管理</el-menu-item>
                <el-menu-item index="1">资金流水</el-menu-item>
                <el-menu-item index="-1">支付密码设置</el-menu-item>
            </el-menu>
        </el-row>
        <template v-if="MenuActive=='-1'">
            <el-row style="margin-top: 32px;margin-left: 21px">
                <el-col :span="6">
                    <el-radio-group class="allRadio">
                        <el-radio >支付密码</el-radio>
                    </el-radio-group>
                    <span style="margin-left: 6px;width:28px;height:11px;font-size:10px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,165,85,1);">
                        <template v-if="flag==0">
                            未启用
                        </template>
                        <template v-else>
                             已启用
                        </template>
                    </span>
                </el-col>
            </el-row>
            <el-row style="margin-left: 80px;margin-top: 12px">
                <span style="width:450px;height:12px;font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">保护账户资金安全，在使用服务购买、T币充值、账户余额等支出时需要验证支付密码</span>
            </el-row>
            <el-row style="margin-left: 80px;margin-top: 35px">
                <el-button class="btnColorOrg" style="width:91px;height:34px;border:none;border-radius:5px;padding: 0;" @click="btclick()">
                    <template v-if="flag==0">
                        立即设置
                    </template>
                    <template v-else>
                        修改
                    </template>
                </el-button>
            </el-row>
        </template>
        <template v-else>
            <component :is="subMenuComponents[MenuActive]" v-on:fun="change"></component>
        </template>

    </div>
</template>

<script>
    import ACCOUNTMGR from "./accountMgr";
    import CAPITALFLOW from "./capitalFlow";
    import SETPAYPWD from "./payPwdSet/setPayPwd";
    import SETPAYPWDSUCCESS from "./payPwdSet/setPayPwdSuccess";
    import UPDATEPATPWD from "./payPwdSet/updatePayPwd";
    import UPDATEBYPWDPHONE from "./payPwdSet/updateByPwdPhone";
    import UPDATEBYPHONEBKCARD from "./payPwdSet/updateByPhoneBkCard";
    import ACCOUNTAPPEAL from "./payPwdSet/accountAppeal";
    import PHONEAPPEAL from "./payPwdSet/phoneAppeal";
    import BANKCARDYZ from "./payPwdSet/bankcardYz";
    import YZACCOUNTUSER from "./payPwdSet/yzAccoutUser";
    import PWDAPPEAL from "./payPwdSet/pwdAppeal";
    import PWDAPPEALYZ from "./payPwdSet/pwdAppealYz";
    import {getAccountStatus} from "../../../api/user/payPwdSet";
    export default {
        name: "mainPage",
        components:{
            "accountMgr":ACCOUNTMGR,
            "capitalFlow":CAPITALFLOW,
            "setPayPwd":SETPAYPWD,
            "setPayPwdSuccess":SETPAYPWDSUCCESS,
            "updatePayPwd":UPDATEPATPWD,
            "updateByPwdPhone":UPDATEBYPWDPHONE,
            "updateByPhoneBkCard":UPDATEBYPHONEBKCARD,
            "accountAppeal":ACCOUNTAPPEAL,
            "phoneAppeal":PHONEAPPEAL,
            "bankcardYz":BANKCARDYZ,
            "yzAccoutUser":YZACCOUNTUSER,
            "pwdAppeal":PWDAPPEAL,
            "pwdAppealYz":PWDAPPEALYZ
        },
        data(){
            return{
                mainPageMenu:'0',
                displaydata:"display:block",
                subMenuComponents:["accountMgr","capitalFlow","setPayPwd","setPayPwdSuccess","updatePayPwd","updateByPwdPhone",
                                    "updateByPhoneBkCard","accountAppeal","phoneAppeal","bankcardYz","yzAccoutUser","pwdAppeal","pwdAppealYz"],
                MenuActive:'0',
                flag:0,
                userid:this.$store.state.user.userInfo.id
            }
        },
        created(){
            if(this.$route.query.myPurseFlag){
                this.mainPageMenu = this.$route.query.myPurseFlag+'';
                this.MenuActive = this.$route.query.myPurseFlag;
            }
        },
        methods:{
            MenuSelect(index,indexPath){
                this.MenuActive = index;
                if (this.MenuActive ==-1) {
                    getAccountStatus({userid:this.userid}).then(response => {
                        this.flag=response.data;
                    });
                }
            },
            btclick(){
                if (this.flag==0) {
                    this.MenuActive="2";
                }else if (this.flag==1) {
                    this.MenuActive="4";
                }
            },
            change(data){
                if (data ==9) {
                    this.displaydata="display:none";
                }else{
                    this.displaydata="display:block";
                }
                this.MenuActive=data;
            }

        }
    }
</script>

<style scoped>

</style>