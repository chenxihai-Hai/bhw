<template>
    <div class="dataStatistics" style="padding:22px 16px 0px;">
        <div class="title">
            <div class="blue">
                <img src="../../images/yonghu.png" class="tu">
                <div class="xian"></div>
                <span class="num"><strong>{{count.all}}</strong></span>
                <span class="name" style="left:75px;">用户总数</span>
            </div>
            <div class="orange">
                <img src="../../images/yonghu.png" class="tu">
                <div class="xian"></div>
                <span class="num"><strong>{{count.superMember}}</strong></span>
                <span class="name">超级会员总数</span>
            </div>
            <div class="purple">
                <img src="../../images/yonghu.png" class="tu">
                <div class="xian"></div>
                <span class="num"><strong>{{count.standardMembet}}</strong></span>
                <span class="name">标准会员总数</span>
            </div>
            <div class="green">
                <img src="../../images/yonghu.png" class="tu">
                <div class="xian"></div>
                <span class="num"><strong>{{count.MerchantUser}}</strong></span>
                <span class="name">商家用户</span>
            </div>
        </div>
        <div class="data">
            <el-row type="flex" align="middle" justify="space-between">
                <el-col :span="3" style="text-align: center;">
                    <span class="text"><strong>最新一周新增用户</strong></span>
                </el-col>
                <el-col :span="21">
                    <div id="daysAddUser" style="width: 100%;height:380px;"></div>
                </el-col>
            </el-row>
        </div>
        <div class="statistics">
            <el-row type="flex" align="middle" justify="space-between">
                <el-col :span="3" style="text-align: center;">
                    <span class="text"><strong>最新一周商家入驻量</strong></span>
                </el-col>
                <el-col :span="21">
                    <div id="daysAddMerchant" style="width: 100%;height:380px;"></div>
                </el-col>
            </el-row>
        </div>
        <div class="data">
            <el-row type="flex" align="middle" justify="space-between">
                <el-col :span="3" style="text-align: center;">
                    <span class="text"><strong>最新一周新增超级会员量</strong></span>
                </el-col>
                <el-col :span="21">
                    <div id="daysAddSuperMember" style="width: 100%;height:380px;"></div>
                </el-col>
            </el-row>
        </div>
        <div class="statistics" style="margin-bottom: 33px;" >
            <el-row type="flex" align="middle" justify="space-between">
                <el-col :span="3" style="text-align: center;">
                    <span class="text"><strong>最新一周新增标准会员量</strong></span>
                </el-col>
                <el-col :span="21">
                    <div id="daysAddStandardMembet" style="width: 100%;height:380px;"></div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {getCount,getAddUser} from '../../api/user/userCount'
    export default {
        name: "dataStatistics",
        data() {
            return {
                num: '8888',
                count:{
                    all:"",
                    standardMembet:"",
                    superMember:"",
                    MerchantUser:""
                },
                temp:[]
            }
        },
        created(){
            getCount().then(response => {
                this.count=response.data;
            })
            for (let i = 7; i >0; i--) {
                const time = new Date(new Date().setDate(new Date().getDate() - i));
                const month = `0${time.getMonth() + 1}`.slice(-2);
                const strDate = `0${time.getDate()}`.slice(-2);
                this.temp.push(`${month}-${strDate}`)
            }
        },
        methods:{
            loadDaysSt(data){
                var datas = [];
                for(var i=0;i<this.temp.length;i++){
                    datas[i] = data[this.temp[i]]||0;
                }
                var myChart = this.$echarts.init(document.getElementById('daysAddUser'));
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.temp
                    },
                    yAxis: {
                        minInterval : 1,
                        type: 'value'
                    },
                    series: [{
                        data: datas,
                        type: 'line',
                        smooth: true
                    }]
                };
                myChart.setOption(option);
            },
            loadDaysMerchant(data){
                var datas = [];
                for(var i=0;i<this.temp.length;i++){
                    datas[i] = data[this.temp[i]]||0;
                }
                var myChart = this.$echarts.init(document.getElementById('daysAddMerchant'));
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.temp
                    },
                    yAxis: {
                        minInterval : 1,
                        type: 'value'
                    },
                    series: [{
                        data: datas,
                        type: 'line',
                        smooth: true
                    }]
                };
                myChart.setOption(option);
            },
            loadDaysSuperMember(data){
                var datas = [];
                for(var i=0;i<this.temp.length;i++){
                    datas[i] = data[this.temp[i]]||0;
                }
                var myChart = this.$echarts.init(document.getElementById('daysAddSuperMember'));
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.temp
                    },
                    yAxis: {
                        minInterval : 1,
                        type: 'value'
                    },
                    series: [{
                        data: datas,
                        type: 'line',
                        smooth: true
                    }]
                };
                myChart.setOption(option);
            },
            loadDaysStandardMembet(data){
                var datas = [];
                for(var i=0;i<this.temp.length;i++){
                    datas[i] = data[this.temp[i]]||0;
                }
                var myChart = this.$echarts.init(document.getElementById('daysAddStandardMembet'));
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.temp
                    },
                    yAxis: {
                        minInterval : 1,
                        type: 'value'
                    },
                    series: [{
                        data: datas,
                        type: 'line',
                        smooth: true
                    }]
                };
                myChart.setOption(option);
            }
        },
        mounted(){
            getAddUser({}).then(response => {
                var data=response.data;
                this.loadDaysSt(data);
            })
            //
            getAddUser({merchantFlag:1}).then(response => {
                var data=response.data;
               /* var result=new Array();
                var list=this.temp;
                for (var i=0;i<list.length;i++) {
                    result.push(list[i],data[list[i]]);
                }*/
                this.loadDaysMerchant(data);
            })
            getAddUser({member:2}).then(response => {
                var data=response.data;
                this.loadDaysSuperMember(data);
            })
            //
            getAddUser({member:1}).then(response => {
                var data=response.data;
               /* var result=new Array();
                var list=this.temp;
                for (var i=0;i<list.length;i++) {
                    result.push(list[i],data[list[i]]);
                }*/
                this.loadDaysStandardMembet(data);
            })

        }
    }
</script>

<style>
    .title {
        padding: 15px 18px;
        height: 112px;
        background: rgba(255, 255, 255, 1);
        color: white;
    }

    .blue {
        float: left;
        width: 25%;
        height: 82px;
        background: rgba(2, 78, 204, 0.8);
    }

    .orange {
        float: left;
        width: 25%;
        height: 82px;
        background: rgba(247, 74, 13, 0.8);
    }

    .purple {
        float: left;
        width: 25%;
        height: 82px;
        background: rgba(125, 80, 202, 0.8);
    }

    .green {
        float: left;
        width: 25%;
        height: 82px;
        background: rgba(86, 189, 69, 0.8);
    }

    .xian {
        position: relative;
        left: 70px;
        bottom: 17px;
        width: 1px;
        height: 47px;
        background: rgba(255, 255, 255, 1);
    }

    .tu {
        position: relative;
        top: 26px;
        left: 23px;
    }

    .num {
        position: relative;
        left: 90px;
        bottom: 64px;
    }

    .name {
        position: relative;
        left: 80px;
        bottom: 36px;
    }

    .data {
        float: left;
        margin-top: 25px;
        width: 49%;
        height: 382px;
        background: rgba(255, 255, 255, 1);
    }

    .statistics {
        margin-left: 2%;
        float: left;
        margin-top: 25px;
        width: 49%;
        height: 382px;
        background: rgba(255, 255, 255, 1);
    }

    .text {
        position: relative;
        top: 30%;
        left: 15%;
        font-size: 16px;
        font-family:'Microsoft YaHei';
        font-weight: 400;
        color: rgba(29, 29, 29, 1);
        writing-mode: tb;
    }
</style>