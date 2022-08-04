<template>
    <div style="background: #EEEEEE;">
        <!-- 搜索条件 -->
        <el-row class="searchBox bgColorF">
            <el-col class="w1240" style="margin:30px auto;float: inherit;">
                <el-row style="border:1px solid #ccc;">
                    <el-col>
                        <dl>
                            <dt>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</dt>
                            <dd><el-link>全部</el-link><el-link>广西（<em>10</em>）</el-link><el-link>深圳（<em>10</em>）</el-link><el-link>上海（<em>10</em>）</el-link><el-link>北京（<em>10</em>）</el-link><el-link>湖北（10）</el-link></dd>
                        </dl>
                    </el-col>
                    <el-col>
                        <dl>
                            <dt>注册资本：</dt>
                            <dd><el-link>全部</el-link><el-link>0-100万</el-link><el-link>100-200万</el-link><el-link>200-500万</el-link><el-link>500-1000万</el-link><el-link>1000万以上</el-link></dd>
                        </dl>
                    </el-col>
                    <el-col>
                        <dl>
                            <dt>成立时间：</dt>
                            <dd><el-link>全部</el-link><el-link>1年内</el-link><el-link>1-5年</el-link><el-link>5-10年</el-link><el-link>10-15年</el-link><el-link>15年以上</el-link></dd>
                        </dl>
                    </el-col>
                </el-row>
            </el-col>
            <el-col><p style="width: 1240px;margin:0 auto 27px;">以为您筛选<em style="color: #CA0000;font-style: normal;">000000</em>条数据样本，请查收！</p></el-col>
        </el-row>
        <el-row class="w1240" style="margin:27px auto;">
            <el-col :span="18">
                <el-row class="enterInfo">
                    <el-col>
                        <div><img src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" alt=""></div>
                        <div>
                            <h3>广西盛和大数据服务有限公司</h3>
                            <el-tag>盛和大数据</el-tag>
                            <dl><dd>法定代表人：<span>谢洵</span></dd><dd>注册资本：<span>200万(元)</span></dd><dd>成立时间：<span>2019-10-12</span></dd></dl>
                            <dl><dd>地址：<span>广西壮族自治区北海市云南路286号北部湾海洋科技创新港2幢三楼BHGD0021号</span></dd></dl>
                            <dl><dd>行业：<span>计算机、通信和其他电子设备制造业</span></dd></dl>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="4"></el-col>
        </el-row>
        <el-row style="margin-bottom: 73px;"></el-row>
    </div>
</template>

<script>
    export default {
        name: "highEnterprise"
    }
</script>

<style scoped>
    .searchBox .el-row{}
    .searchBox dl{height: 75px;line-height:75px;border-bottom:1px solid #CCC; display: flex;display: -webkit-flex;}
    .searchBox .el-col:last-child dl{border-bottom: none;}
    .searchBox dl dt{width: 224px;color: #1D1D1D;font-size: 18px;font-weight:normal; text-align: center;background: #EEEEEE;}
    .searchBox dl dd{font-size: 14px;font-weight: normal; flex: 1;-webkit-flex: 1;}
    .searchBox dl dd .el-link{color: #1D1D1D;font-weight:normal;margin-right: 99px;}
    .searchBox dl dd .el-link:first-child{width: 62px;height: 36px;background: #FDA65C;color: #fff;font-weight:normal;margin:0 50px 0 22px;border-radius: 2px; }
    .searchBox em{color: #CA0000;font-style: normal;}
    .enterInfo .el-col{background: #FFF;padding:30px 36px;margin-bottom: 26px; display: flex;display: -webkit-flex;}
    .enterInfo .el-col div{width: 131px;height: 131px;margin-right: 40px;}
    .enterInfo .el-col div:last-child{flex: 1;-webkit-flex: 1;}
    .enterInfo .el-col img{width: 100%;height: 100%;padding: 6px 0;}
    .enterInfo .el-tag{background: #B7D3FD;color: #0066FF;height: 22px;line-height: 20px;margin:5px 0 10px;}
    .enterInfo .el-col h3{color: #1D1D1D;font-size: 18px;font-weight:normal;}
    .enterInfo dl>dd{color: #7D7D7D;display: inline-block;margin-right: 78px;line-height: 28px;}
    .enterInfo dl>dd span{color: #1D1D1D;}
</style>