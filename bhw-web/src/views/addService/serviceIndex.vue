<template>
    <div class="sMain">
        <div class="sImg">
<!--            <h2>什么是增值服务?</h2>-->
<!--            <p>增值服务（Value-added service，VAS），即是将某项非核心技术、产品或服务利用新方式加以修正改善，以创造更高的价值。此概念容易被广泛应用于各产业及研究领域，以通讯及网络产业为首的例子分别为Web2.0应用及移动上网等。</p>-->
        </div>
        <div class="titleBox"><h3>增值服务项目</h3><p>VALUE ADDED AERVICE</p></div>
        <div class="imgBox"><img src="../../images/img-serve_type.png" alt=""></div>
        <div class="titleBox"><h3>开通增值服务</h3><p>Open value-added service</p></div>
        <div class="addServiceBox">
            <table class="service-table">
                <tr>
                    <th style="width: 229px;">服务内容</th>
                    <th><span style="vertical-align: 10px;">标准会员</span><span style="font-size: 41px;">/</span><span style="vertical-align: -5px;">2888元</span></th>
                    <th><span style="vertical-align: 10px;">高级会员</span><span style="font-size: 41px;">/</span><span style="vertical-align: -5px;">8888元</span></th>
                </tr>
                <tr><td>技术合同认定登记</td><td>免费<em>3</em>次+全部业务<em>9</em>折</td><td>免费<em>3</em>次+全部业务<em>8</em>折</td></tr>
                <tr><td>技术成果登记</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>高企认定</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>知识产权</td><td>全部业务<em>9</em>折</td><td>免费<em>1</em>次+全部业务<em>8</em>折</td></tr>
                <tr><td>法律服务</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>科技金融</td><td>全部业务<em>9</em>折</td><td>全部业务<em>8</em>折</td></tr>
                <tr><td>科技咨询</td><td>免费<em>2</em>次+全部业务<em>9</em>折</td><td>免费<em>2</em>次+全部业务<em>8</em>折</td></tr>
                <tr><td>财税服务</td><td>一年免费</td><td>一年免费</td></tr>
                <tr><td>开通年限</td><td>一年</td><td>一年</td></tr>
                <tr><td></td><td><el-button class="openHy" @click="openVIP('1')">开通会员</el-button></td><td><el-button class="openHy gjHy" @click="openVIP('2')">开通会员</el-button></td></tr>
            </table>
            <p class="warnBox"><i class="icon-warn"></i>以上增值服务收费标准，平台具有最终解释权，并保留修改权利。</p>
        </div>
    </div>
</template>

<script>
    import {getToken} from "../../utils/auth";
    export default {
        name: "serviceIndex",
        data(){
            return{
            }
        },
        methods:{
            openVIP(typeID){
                if(getToken()){
                    this.$router.push({path:"/userCenter",query:{menuActiveFlag:'addServiceIndex'}});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            }

        }
    }
</script>

<style scoped>
    .sImg{width: 100%;height: 752px;background:url("../../images/addService@2x.png") no-repeat;background-size: 100% 100%; text-align: center;margin-bottom: 116px;}
    .sImg h2{color: #FFF; font-size: 60px;padding-top: 236px;letter-spacing: 3px;}
    .sImg p{width: 771px;color: #fff;font-size: 18px;font-weight:normal; line-height: 25px;text-align: center;margin:50px auto 0;}
    .titleBox{text-align: center;}
    .titleBox h3{color: #000;font-size: 30px;font-weight:normal;letter-spacing: 4px;display: inline-block; position: relative;}
    .titleBox h3::before{content: '';width: 635px;height: 20px;background: url("../../images/img-line_right.png") no-repeat;background-size: contain; position: absolute;right: 150px;bottom:5px;}
    .titleBox h3::after{content: '';width: 538px;height: 20px;background: url("../../images/img-line_left.png") no-repeat;background-size: contain; position: absolute;left:205px;bottom:5px;}
    .titleBox p{color: #B7B7B7;margin-top:5px;}
    .imgBox{width: 1242px;height: 695px;margin:80px auto 150px;}
    .warnBox{color: #6C6C6C;font-size: 18px;font-weight:normal;margin-top:40px;}
    .icon-warn{width: 17px;height: 15px;background: url("../../images/user/icon-warn.png") no-repeat;display: inline-block;margin-right: 18px;}
    .addServiceBox{width: 878px;margin: 65px auto 200px;}
    .service-table{width: 100%;margin: 0 auto;border-collapse:collapse;}
    .service-table tr{height: 68px;}
    .service-table,.service-table tr th,.service-table tr td{border: 0.5px solid #B5B5B5}
    .service-table tr th{color: #fff;font-size: 24px;font-weight:normal;text-align: center;background:linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);}
    .service-table tr th:nth-child(2){background: linear-gradient(to right, #F44C7D , #F67A4A);}
    .service-table tr th:last-child{background: linear-gradient(to right, #A650F9 , #F778AC);}
    .service-table tr td{color: #1D1D1D; background: #FFFBFB;font-size: 18px;font-weight:normal;text-align: center;}
    .service-table tr td:nth-child(2){background: #FFE8F5;}
    .service-table tr td:last-child{background: #FFEDE6;}
    .service-table tr td em{color: #ED7799;font-size: 36px;font-style: normal;}
    .service-table tr td:last-child em{color: #B067F7;}
    .service-table tr:last-child>td{background: #FFF;}
    .openHy{width: 155px;height: 40px; font-size: 16px;font-weight:normal;border-radius: 15px;padding: 0; background: linear-gradient(to right, #F44C7D , #F67A4A);border: none;}
    /deep/ .el-button.el-button--default.openHy span{color: #FFF!important;}
    .openHy.gjHy{background: linear-gradient(to right, #A650F9 , #F778AC);}
</style>