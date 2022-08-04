<template>
  <div class="about">
    <TopBar></TopBar>
    <el-main class="aboutInfo">
      <hr class="lineTop">
<!--  简介 -->
      <el-row style="width: 100%;">
        <el-col class="wdmg"><img src="../images/about/img-about_jyzx.png" style="width: 100%;" alt=""></el-col>
      </el-row>
      <div class="aboutTitle" style="margin-top: 80px;"><span>关于我们</span></div>
      <el-row style="margin-top: 50px;" class="about-jj">
        <el-col>
          <el-row>
            <el-col><img src="../images/about/img-about_cg1.png" alt=""></el-col>
            <el-col style="padding-top: 40px;">
              <h3>北部湾国际技术转移转化中心</h3>
              <p style="padding-top: 20px;">北部湾国际技术转移转化中心是北海市重点工程及科技厅厅市会商重点项目，结合广西“三百两千”工程，全面对标国家 “2+N”技术转移区域中心体系和国家科技成果转移转化示范区建设，汇集深圳等发达地区创新资源，以“一体三链五平台”架构，建设“人才、技术、资金、服务”四要素高地，培育战略性新兴产业，积极构建技术转移转化生态新体系，目标是把北部湾国际技术转移转化中心建成国家 “2+N”技术转移区域中心，并以此为基础整合区域重点产业和经济园区，探索具有广西特色的科技成果转移转化新机制、新模式，建成广西首个国家科技成果转移转化示范区。 </p>
            </el-col>
          </el-row>
        </el-col>
        <!--<el-col style="margin-top: 48px;">
          <el-row>
            <el-col><img src="../images/about/img-about_cg2.png" alt=""></el-col>
            <el-col style="padding-top: 40px;">
              <h3 style="margin-bottom: 0;">国家技术转移示范机构——对接平台</h3>
              <p>建设运营单位</p>
              <p style="margin-top: 20px;">
                对接平台是遵循国务院关于鼓励和支持科技型中小企业健康发展的举措下为进一步促进完善企业技术与资本有效对接服务，在深圳市政府有关部门指导下于2012年5月25日在深圳会展中心正式成立截止到2018年12月底，对接平台分别与美国霍尼韦尔（全球财富100强、国际技术转移巨头）新加坡创新合作与科技转化中心等多所研究院建立了深层战略合作联盟关系协助制订顶层设计，并给予技术转移服务和成果转移转化协助服务在深圳策划组织和承办大型国际技术转移活动、国际国内技术交流对接会国际国内技术推广会、高新技术交易博览会、金融博览会、微商博览文化产业博览会、国际投资贸易洽谈会等共约60场次
              </p>
            </el-col>
          </el-row>
        </el-col>-->
      </el-row>
<!--   中心风采   -->
      <div class="aboutTitle" style="margin-top: 140px;"><span>中心风采</span></div>
      <el-row class="elegantBox">
        <el-col style="margin: 86px 0 42px;text-align: center;"><span style="margin-right: 95px;">服务大厅</span><span style="margin-right: 38px;">休闲空间</span><span style="margin-right: 65px;">会议室</span><span>咖啡厅</span></el-col>
        <el-col>
          <el-row type="flex">
            <el-col><img src="../images/about/img-kaft.png" alt=""><p>咖啡厅</p></el-col>
            <el-col><img src="../images/about/img-fuw.png" alt=""><p>服务大厅</p></el-col>
            <el-col><img src="../images/about/img-huiys.png" alt=""><p>会议室</p></el-col>
            <el-col><img src="../images/about/img-xiux.png" alt=""><p>休闲空间</p></el-col>
          </el-row>
        </el-col>
      </el-row>
<!--  地图 -->
      <div class="aboutTitle" style="margin-top: 140px;"><span>联系我们</span></div>
      <div style="width: 100%;background: #FAFEFF;padding-top: 50px;margin-top: 18px;">
         <baidu-map class="bm-view" ak="65af388e4555685d6c9fbc143ab4076c" :center="center" :zoom="zoom" @ready="handler">
           <bm-marker :position="location">
             <bm-label content="北部湾国际技术转移转化中心" :labelStyle="{color: '#222', fontSize : '14px',border:'none',background:'#FFF',padding:'5px 10px'}"
                       :offset="{width: -100, height: 40}"/>
              <!--<bm-label content="深圳虚拟大学园产业化基地" :labelStyle="{color: '#222', fontSize : '14px',border:'none',background:'#FFF',padding:'5px 10px'}"
                       :offset="{width: -100, height: -40}"/>-->
           </bm-marker>
           <bm-overlay ref="customOverlay" pane="labelPane" :class="{sample: true}">
             <div>
               <h3><span>期待您的联系</span><em>欢 迎</em></h3>
               <p>公司：北部湾国际技术转移转化中心</p>
               <p>地址：广西壮族自治区北海市银海区四川南路299号穗丰金湾</p>
               <!--<p>公司：深圳市对接平台科技发展有限公司</p>
               <p>地址：深圳市南山区粤兴三道虚拟大学园产业化基地A704</p>-->
             </div>
           </bm-overlay>
         </baidu-map>
         <el-row class="contentBox">
          <el-col :span="12" style="padding-left: 340px;">
            <h5>联系我们</h5>
            <p><i class="icon-user"></i><span>陈女士</span></p>
            <p><i class="icon-emall"></i><span>314323844@qq.com</span></p>
            <p><i class="icon-phone"></i><span>13602543093</span></p>
          </el-col>
          <el-col :span="12" style="padding-left: 130px;">
            <h5>联系我们</h5>
            <p><i class="icon-user"></i><span>卢女士</span></p>
            <p><i class="icon-emall"></i><span>1106082118@qq.com</span></p>
            <p><i class="icon-phone"></i><span>18154537904</span></p>
          </el-col>
           <!--<el-col :span="12" style="padding-left: 340px;">
             <h5>联系我们</h5>
             <p><i class="icon-user"></i><span>清女士</span></p>
             <p><i class="icon-emall"></i><span>1362108214@qq.com</span></p>
             <p><i class="icon-phone"></i><span>1834786523</span></p>
           </el-col>
           <el-col :span="12" style="padding-left: 130px;">
             <h5>联系我们</h5>
             <p><i class="icon-user"></i><span>王女士</span></p>
             <p><i class="icon-emall"></i><span>wangquaner@163.com</span></p>
             <p><i class="icon-phone"></i><span>15889668989</span></p>
           </el-col>-->
        </el-row>
       </div>
<!--  中心风采 -->
      <div style="margin: 100px auto 160px;">
        <el-row type="flex" justify="center" class="btnlink">
          <el-col :span="6"><el-link @click="btnInfo('1')"><i class="img_jiaoyi"></i><p>交易保障</p></el-link></el-col>
          <el-col :span="6"><el-link @click="btnInfo('2')"><i class="img_jubao"></i><p>廉正举报</p></el-link></el-col>
          <el-col :span="6"><el-link @click="btnInfo('3')"><i class="img_falv"></i><p>法律声明</p></el-link></el-col>
          <el-col :span="6"><el-link @click="btnInfo('4')"><i class="img_yins"></i><p>隐私政策</p></el-link></el-col>
        </el-row>
      </div>
    </el-main>
    <FootBar></FootBar>
  </div>
</template>
<script>
  import TopBar from "./layout/TopBar";
  import FootBar from "./layout/FootBar";
  import BaiduMap from 'vue-baidu-map/components/map/Map.vue';
  import BmMarker from "vue-baidu-map/components/overlays/Marker.vue";
  import BmLabel from "vue-baidu-map/components/overlays/Label.vue";

  export default {
    name: "about",
    components: {FootBar, TopBar,BaiduMap,BmMarker,BmLabel},
    data(){
      return{
        zoom: 18,
        center: {lng: 0, lat: 0},
        location:{lng:109.1304,lat:21.438247}
        //   location:{lng:113.951055,lat:22.532917}
      }
    },
    methods:{
      handler ({BMap, map}) {
        this.center.lng = 109.160266;
        this.center.lat = 21.440464;
        /*  this.center.lng = 113.952641,22.532751;
          this.center.lat = 22.532751;*/

      },
      btnInfo(val){
        this.$router.push({
          path: '/about/detail',
          query:{typeId:val}
        });
      }
    }
  }
</script>
<style scoped>
  @import "../styles/about.css";
  /deep/ .bm-view{height: 576px;position: relative;}
  .sample {
    width: 50%;height: 576px;
    background: rgba(247,74,13,0.85);
    color: #fff;
    padding-left: 177px;
    padding-top: 240px;
    position: absolute;
    top: 0;
    right: 0;
  }
  .sample h3{font-size: 24px;margin-bottom: 40px;position: relative;}
  .sample h3 em{width: 338px;height: 155px;background: url("../images/about/icon_welcome.png") no-repeat;color: #C53705;font-size: 60px;font-style: normal;text-align: center;display: block;padding-top: 18px;padding-right:30px;position: absolute;left: 148px;bottom: 0;}
  .sample p{color: #FFF;font-size: 14px;line-height: 33px;}
  .btnlink /deep/ .el-col{text-align: center;}
  .btnlink /deep/ .el-link.el-link--default:hover{color: #024ecc;}
  @media screen and (max-width: 1300px) {
    .sample{padding-left: 160px;}
  }
</style>