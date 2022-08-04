<template>
    <div>
        <div class="footWrap">
            <div class="footBar w1240">
                <dl><dt>核心服务</dt><dd><a href="https://www.tech110.net" target="_blank" style="text-decoration: none">技术成果认定登记</a></dd>
                    <dd><a href="http://210.76.97.54" target="_blank" style="text-decoration: none">技术合同登记</a></dd>
                    <dd><a href="http://gxgq.gxinfo.org.cn/ms/login.aspx" target="_blank" style="text-decoration: none">高企认定</a></dd>
                    <dd><router-link :to="{path:'/kjsc'}" style="text-decoration: none;"> 知识产权</router-link></dd></dl>
                <dl><dt style="visibility:hidden;">aa</dt><dd><router-link :to="{path:'/kjsc',query:{chengguoActiveName:1}}" style="text-decoration: none;">科技金融</router-link></dd><dd><dd><router-link :to="{path:'/kjsc',query:{chengguoActiveName:1}}" style="text-decoration: none;">财税服务</router-link></dd><dd><router-link :to="{path:'/kjsc',query:{chengguoActiveName:1}}" style="text-decoration: none;">法律服务</router-link></dd><dd></dd></dl>
                <dl><dt>关于我们</dt><dd><router-link :to="{path: '/about/detail',query:{typeId:'1'}}" tag="a" style="text-decoration: none;">交易保障</router-link></dd>
                    <dd><router-link :to="{path: '/about/detail',query:{typeId:'2'}}" tag="a" style="text-decoration: none;">廉政举报</router-link></dd>
                    <dd><router-link :to="{path: '/about/detail',query:{typeId:'3'}}" tag="a" style="text-decoration: none;">法律声明</router-link></dd>
                    <dd><router-link :to="{path: '/about/detail',query:{typeId:'4'}}" tag="a" style="text-decoration: none;">隐私政策</router-link></dd></dl>
                <!--北部湾样式-->
                <dl style="flex:2.6">
                    <dt>联系我们</dt><dd>联系电话： 0779-3186130</dd><dd>邮箱：gxdjpt2020@163.com，15112696510@163.com</dd>
                    <dd>地址：广西壮族自治区北海市银海区四川路299号穗丰金湾1幢0201</dd><dd></dd>
                </dl>
                <div class="img-ewm"><img src="../../images/img-ewm.jpeg" alt=""></div>
                <!--<dl style="flex:2.6">
                    <dt>联系我们</dt><dd>联系电话： 26920086</dd><dd>邮箱：wangquaner@163.com</dd>
                    <dd>地址：深圳市南山区粤兴三道虚拟大学园产业化基地A704</dd><dd></dd>
                </dl>
                <div class="img-ewm"><img src="../../images/qgewm.jpg" alt=""></div>-->
            </div>
        </div>
        <hr>
        <div class="foot-link">
            <el-row type="flex" justify="space-around" class="linkMark w1240">
                <el-col :span="2"><h4>友情链接</h4></el-col>
                <el-col :span="20">
                    <a class="el-link" href="http://www.most.gov.cn/" target="_blank">中华人民共和国科学技术部</a>
                    <a class="el-link" href="http://kjt.gxzf.gov.cn/" target="_blank">广西科技厅</a>
                    <a class="el-link" href="http://gdstc.gd.gov.cn/" target="_blank">广东科技厅</a>
                    <a class="el-link" href="http://www.beihai.gov.cn/" target="_blank">北海市人民政府</a>
                    <a class="el-link" href="http://stic.sz.gov.cn/" target="_blank">深圳市科创委</a>
                    <a class="el-link" href="https://baike.baidu.com/item/国家技术转移南方中心/16217117?fr=aladdin" target="_blank">国家技术转移南方中心</a>
                    <a class="el-link" href="https://www.netcchina.com/" target="_blank" >国家技术转移东部中心</a>
                    <a class="el-link" href="http://www.51kehui.com:90/myjeecms/" target="_blank">国家技术转移中部中心</a>
                </el-col>
            </el-row>
        </div>
        <div class="copyright w1240"><p>Copyright 2017-2020 one All Rights Reserved.版权所有© 广西对接平台科技发展有限公司<!--广西对接平台科技发展有限公司  深圳市对接平台科技发展有限公司--> </p>
            <p style="margin-top: -20px;">地址：广西壮族自治区北海市银海区四川路299号穗丰金湾1幢0201|邮编：530000</p>
            <!--<p style="margin-top: -20px;">地址：深圳市南山区粤兴三道虚拟大学园产业化基地A704|邮编：518000</p>-->
            <p  style="margin-top: -20px;"><a href="https://beian.miit.gov.cn" target="_blank">粤ICP备2020082694号</a></p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "footBar",
        methods: {
            jumpURL(jurl){
                console.log(jurl)
                window.open(jurl);
            }
        }
    }
</script>

<style scoped>
    hr{width: 100%;height: 1px;background:linear-gradient(90deg,rgba(0,153,204,1) 0%,rgba(216,81,133,1) 100%);}
    .img-ewm{width: 144px;height: 144px;}
    .img-ewm img{width: 100%;height: 100%;}
</style>