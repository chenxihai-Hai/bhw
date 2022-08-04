<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px 100px;">
            <h3>订单分析</h3>
            <el-row type="flex" justify="space-around" class="statisticBox" style="margin-top: 68px;">
                <el-col>
                    <el-card class="box-card" shadow="never">
                        <div style="margin: 50px 0;">
                            <span>本周订单数</span>
                            <label style="font-size:30px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(2,78,204,1);">
                                {{weekMaxOrders}}
                            </label>
                        </div>
                    </el-card>
                </el-col>
                <el-col>
                    <el-card class="box-card" shadow="never">
                        <div style="margin: 50px 0;">
                            <span>本周支付订单总量</span>
                            <label style="font-size:30px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(247,74,13,1);">
                                {{weekDelaOrders.toFixed(2)}}
                            </label>
                        </div>
                    </el-card>
                </el-col>
                <el-col>
                    <el-card class="box-card" shadow="never">
                        <div style="margin: 50px 0;">
                            <span>本周取消订单量</span>
                            <label style="font-size:30px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(247,74,13,1);">
                                {{weekRefunds}}
                            </label>
                        </div>
                    </el-card>
                </el-col>
                <el-col>
                    <el-card class="box-card" shadow="never">
                        <div style="margin: 50px 0;">
                            <span>本周被投诉订单量</span>
                            <label style="font-size:30px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(247,74,13,1);">
                                {{weekRefunds}}
                            </label>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-row type="flex" justify="space-around" class="echartBox">
                <el-col>
                    <div id="daysSt" style="width: 100%;height:400px; padding: 20px 10px 0px 30px;"></div>
                </el-col>
                <el-col>
                    <div id="weekSt" style="width: 100%;height:400px; padding: 20px 10px 0px 20px;"></div>
                </el-col>
            </el-row>
            <div class="mark"><p>注：环比增长率（本期数-上期数）/上期数X100%  反映本期比上期订单数的增长速度</p></div>
        </div>
    </div>
</template>

<script>
    import {backQryPayDealList} from "../../../api/financingOrder/orderList";
    export default {
        name: "orderStatistics",
        data(){
            return {
                daysOrders:[],
                hOrders:[],
                weekMaxOrders:0,
                weekDelaOrders:0,
                weekRefunds:0,
                yWeeks:[],
                yWeeksDatas:[]
            }
        },
        methods:{
            dateAddDays(dayCount,date) {
                var tempDate=date || new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                // var resultDateStr=resultDate.getFullYear()+"-"+(resultDate.getMonth()+1)+"-"+(resultDate.getDate());//将日期转化为字符串格式
                return resultDate;
            },
            loadDaysData(){
               var qryPar = {
                   createTime:this.dateAddDays(0,null).format('yyyy-MM-dd'),
                   updateTime : this.dateAddDays(0,null).format('yyyy-MM-dd')
                }
                var qryPar2 = {
                    createTime:this.dateAddDays(-1,null).format('yyyy-MM-dd'),
                    updateTime : this.dateAddDays(-1,null).format('yyyy-MM-dd')
                }
                var promList=[];
                var par = new Promise((resolve, reject) => {
                    backQryPayDealList(qryPar).then(response=>{
                        resolve(response.data);
                    });
                });
                promList.push(par);
                var par2 = new Promise((resolve, reject) => {
                    backQryPayDealList(qryPar2).then(response=>{
                        resolve(response.data);
                    });
                });
                promList.push(par2);
                Promise.all(promList).then(response=>{
                    var pCount=0,tCount=0,sCount=0,fpCount=0,ftCount=0,fsCount=0;
                    response.forEach((item,index)=>{
                        item.forEach(data=>{
                            if(data.dealType==0){
                                index == 0? pCount++:fpCount++;
                            }
                            if(data.dealType==1){
                                index == 0? tCount++:ftCount++;
                            }
                            if(data.dealType==3){
                                index == 0? sCount++:fsCount++;
                            }
                        });
                    })
                    var ph = (pCount-fpCount)/fpCount*100;
                    var th = (tCount-ftCount)/ftCount*100;
                    var sh = (sCount-fsCount)/fsCount*100;
                    this.daysOrders.push(tCount);
                    this.daysOrders.push(sCount);
                    this.daysOrders.push(pCount);
                    this.hOrders.push(th);
                    this.hOrders.push(sh);
                    this.hOrders.push(ph);
                    this.loadDaysSt();
                });
            },
            loadDaysSt(){
                var myChart = this.$echarts.init(document.getElementById('daysSt'));
                var option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {
                            type : 'shadow'
                        }
                    },
                    legend: {
                        data:['每日订单环比', '订单量']
                    },
                    toolbox: {
                        show : false
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    yAxis : [
                        {
                            type : 'category',
                            data : ['充值T币','增值服务','产品交易']
                        }
                    ],
                    series : [
                        {
                            name:'每日订单环比',
                            type:'bar',
                            stack: '总量',
                            itemStyle : { normal:{color:'#024ECC', label : {show: true, position: 'insideRight'}}},
                            data:this.hOrders
                        },
                        {
                            name:'订单量',
                            type:'bar',
                            stack: '总量',
                            itemStyle : { normal: {color:'#F74A0D',label : {show: true, position: 'insideRight'}}},
                            data: this.daysOrders
                        }
                    ]
                };
                myChart.setOption(option);
            },
            loadWeekData(){
                var date = new Date();
                var weekday = date.getDay()||7;
                date.setDate(date.getDate()-weekday+1);
                var qryPar = {
                    createTime:date.format('yyyy-MM-dd'),
                    updateTime : this.dateAddDays(6,date).format('yyyy-MM-dd')
                }
                backQryPayDealList(qryPar).then(response=>{
                    this.weekMaxOrders = response.page.counts;
                    var tp = 0,rfCounts=0;
                    response.data.forEach(item=>{
                        tp+=parseFloat(item.totalPrice);
                        if(item.refundStatus>0){
                            rfCounts++;
                        }
                    })
                    this.weekDelaOrders = tp;
                    this.weekRefunds = rfCounts;
                    var d1 = new Date();
                    var d2 = new Date();
                    d2.setMonth(0);
                    d2.setDate(1);
                    var rq = d1-d2;
                    var s1 = Math.ceil(rq/(24*60*60*1000));
                    var s2 = Math.ceil(s1/7);
                    var promList=[];
                    this.yWeeks.push('本周');
                    this.yWeeksDatas.push(this.weekMaxOrders);
                    for(var i=s2;i>0;i--){
                        this.yWeeks.push('本年第'+i+'周');
                        var par = new Promise((resolve, reject) => {
                            date = this.dateAddDays(-1, date);
                            var weekday = date.getDay() || 7;
                            date.setDate(date.getDate() - weekday + 1);
                            qryPar = {
                                createTime: date.format('yyyy-MM-dd'),
                                updateTime: this.dateAddDays(6, date).format('yyyy-MM-dd')
                            }
                            backQryPayDealList(qryPar).then(response => {
                                resolve(response.page.counts);
                            })
                        })
                        promList.push(par);
                    }
                    Promise.all(promList).then(response=>{
                        this.yWeeksDatas =  this.yWeeksDatas.concat(response);
                        this.loadWeekSt();
                    });
                });
            },
            loadWeekSt(){
                var myChart = this.$echarts.init(document.getElementById('weekSt'));
                var option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data:['订单总量']
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center'
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            data :this.yWeeks
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'订单总量',
                            type:'bar',
                            data: this.yWeeksDatas,
                            itemStyle:{
                                normal:{
                                    color:'#024ECC'
                                }
                            }
                        }/*,
                        {
                            name:'日均订单量',
                            type:'bar',
                            data:[862, 1018, 964, 1026, 1679],
                            markLine : {
                                itemStyle:{
                                    normal:{
                                        lineStyle:{
                                            type: 'dashed'
                                        }
                                    }
                                },
                                data : [
                                    [{type : 'min'}, {type : 'max'}]
                                ]
                            }
                        }*/
                    ]
                };
                myChart.setOption(option);
            }
        },
        mounted(){
            this.loadDaysData();
            this.loadWeekData();
        }
    }
</script>

<style scoped>
    .box-card {
        height: 196px;
        text-align: center;
    }
    .statisticBox .el-col{width: calc((100% - 33px)/4);margin-right: 11px;}
    .statisticBox span{vertical-align: 5px;}
    .echartBox{margin-top: 15px;}
    .echartBox .el-col{width: calc((100% - 11px)/2);border: 1px solid #EBEEF5;text-align: center; margin-right: 11px;}
    .statisticBox .el-col:last-child,.echartBox .el-col:last-child{margin-right: 0;}
    /deep/ .mark{color: #ca0000;font-size: 12px;margin-top: 15px;}
</style>