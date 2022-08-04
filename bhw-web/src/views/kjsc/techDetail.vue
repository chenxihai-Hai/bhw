<template>
    <div class="techDetailBox">
        <div><img src="../../images/zichanjy/jscg.png" width="100%" alt=""></div>
        <div style="margin-top: 68px;">
            <h3><em>【{{requireObj.typeName}}】</em>{{requireObj.techName}}</h3>
            <p style="text-align: center;">权利人/发明人：<em>{{requireObj.source}}</em></p>
            <el-divider></el-divider>
            <div class="content">
                <p v-if="requireObj.introduce.indexOf('<')<0">{{requireObj.introduce}}</p>
                <p v-html="requireObj.introduce"  v-else></p>
                <div><span>联系人：<em>{{undefined==requireObj.contactName || ''==requireObj.contactName?'翁治林':requireObj.contactName}}</em>
                        | 联系方式：<em>{{undefined==requireObj.contactPhone || ''==requireObj.contactPhone?'18589029528':requireObj.contactPhone}}</em></span></div>
            </div>
            <div style="text-align: center;">
                <el-button type="primary" class="btnColorOrg" @click="backKjsc" >返回</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "techDetail",
        data(){
            return{
                requireObj:{id:0,title:'',
                    content:'',categoryName:'',
                    inventor:'',contactName:'',contactPhone:''},
            }
        },
        created(){
            if(this.$route.query.requireObj){
                this.requireObj = this.$route.query.requireObj;
            }
        },
        methods:{
            backKjsc(){
                this.$router.push({path:"/kjsc",query:{chengguoActiveName:3}});
            }
        }
    }
</script>

<style scoped>
    .techDetailBox h3{color: #024ECC;font-size: 16px;text-align: center;margin-bottom: 10px;}
    .techDetailBox em{color: #ca0000;font-style: normal;}
    .techDetailBox .content{width: 912px;margin: 48px auto;}
    .techDetailBox .content p{text-indent: 2em;line-height: 24px;margin-bottom: 50px;}
</style>