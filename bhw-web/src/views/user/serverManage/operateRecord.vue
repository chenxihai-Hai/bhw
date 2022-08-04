<template>
    <div style="padding-left: 51px;">
        <el-row>
            <el-col :span="4">
                <div class="cleanOperate" @click="delAll">
                    清除所有操作历史
                </div>
            </el-col>
            <el-col :span="4">
                <div class="cleanSelect" @click="delInfo">
                    清除所选项
                </div>
            </el-col>
        </el-row>
        <ul style="margin-top: 82px;">
            <li v-for="op in operateList" :key="op.dates" style="margin-top: 77px;">
                <label class="dateLabel">{{op.dates}}</label>
                <span class="msgList">你有（{{op.list.length}}条）操作历史</span>
                <ul style="margin-top: 19px;">
                    <li v-for="(ls,index) in op.list" :key="ls.createTime">
                        <span v-if="index==0"><el-checkbox v-model="op.checkFlag"></el-checkbox></span>
                        <label :style="index==0?'margin-left: 31px;':'margin-left: 45px;'" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">{{ls.createTime.split(" ")[1]}}</label>
                        <span class="liSpan" v-if="ls.type==1">上传了</span>
                        <span class="liSpan" v-if="ls.type==2 || ls.type==3">进行了</span>
                        <span class="liSpan" v-if="ls.type==4">处理了</span>
                        <span class="liSpan" v-if="ls.type==5">删除了</span>
                        <label style="color: red">”{{ls.title}}”</label>
                        <label v-if="ls.type!=2 && ls.type!=3">的{{ls.content}}！</label>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>

<script>
    import {qryOperateRecord,delOperateRecord} from "../../../api/intellectual/operateRecord";
    export default {
        name: "operateRecord",
        data() {
            return {
                operateList:[]
            }
        },
        created(){
            this.qryOperateRecord();
        },
        methods:{
            delInfo(){
                var dIds = '';
                for(var i=0;i<this.operateList.length;i++){
                    if(this.operateList[i].checkFlag){
                        for(var h=0;h<this.operateList[i].list.length;h++){
                            dIds=dIds+this.operateList[i].list[h].id+",";
                        }
                    }
                }
                delOperateRecord({ids:dIds}).then(response=>{
                    this.qryOperateRecord();
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                })
            },
            delAll(){
                delOperateRecord({userId:this.$store.getters.userInfo.id}).then(response=>{
                    this.qryOperateRecord();
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                })
            },
            qryOperateRecord(){
                this.operateList=[];
                qryOperateRecord({userId:this.$store.getters.userInfo.id}).then(response=>{
                    var list = response.data;
                    var arrs =[];
                    for(var i=0;i<list.length;i++){
                        var dates = list[i].createTime.split(" ")[0];
                        if( arrs[dates] == undefined || null == arrs[dates]  ){
                            arrs[dates] = {dates:dates,list:[list[i]]};
                        }else{
                            arrs[dates].list.push(list[i]);
                        }
                    }
                    for(var a in arrs){
                        this.operateList.push(arrs[a]);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .cleanOperate{
        cursor: pointer;
        line-height: 29px;padding-left: 21px;
        font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);
        width:149px;height:29px;background:linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);border-radius:3px;
    }
    .cleanSelect{
        cursor: pointer;
        line-height: 29px;padding-left: 21px;
        font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);
        width:113px;
        height:29px;
        background:rgba(2,78,204,1);
        opacity:0.8;
        border-radius:3px;
    }
    .dateLabel{
        font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color: red;
    }
    .msgList{
        margin-left: 29px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);
    }
    .liSpan{
        margin-left: 49px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);
    }

</style>