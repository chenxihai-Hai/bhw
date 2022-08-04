<template>
    <div class="chengguo">
        <el-row justify="center" type="flex">
            <el-col :span="16" class="minW1240">
                <el-tabs v-model="activeName" @tab-click="handleClick" class="tabKjsc" >
                    <el-tab-pane label="产品、技术交易" name="0">
                    </el-tab-pane>
                    <el-tab-pane label="服务交易" name="1">
                    </el-tab-pane>
                    <el-tab-pane label="技术成果" name="3">
                    </el-tab-pane>
                    <el-tab-pane label="政策补贴" name="2">
                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </el-row>
        <template v-if="activeName=='0'">
            <el-row justify="center" type="flex">
                <el-col :span="16" class="minW1240">
                    <div style="margin-top: 75px;width:79px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(125,125,125,1);">精品推荐</div>
                    <el-row type="flex" justify="space-between" style="margin-top: 27px;">
                        <el-col  v-for="bout in boutiqueList" :key="bout.projectName" :span="4">
                            <el-row style="background:rgba(255,255,255,1);box-shadow:1px 2px 6px 1px rgba(8,1,3,0.5);height: 255px;">
                                <el-col style="margin-top: 17px;">
                                    <router-link :to="{path:'/kjsc/detail',query:{projectObj:bout}}" v-if="bout.homeImg != ''" tag="a" class="list-link-a">
                                        <img style="width: 187px; height: 102px;margin-top: 11px;" :src="bout.homeImg" />
                                    </router-link>
                                    <router-link :to="{path:'/kjsc/detail',query:{projectObj:bout}}" v-else tag="a" class="list-link-a">
                                        <img style="width: 187px; height: 102px;margin-top: 11px;" src="../../images/img-zhuanl.png" />
                                    </router-link>
                                </el-col>
                                <el-col style="margin-top: 23px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);" :title="bout.projectName">
                                    <template v-if="bout.projectName.length>10">
                                        {{bout.projectName.substring(0,10)+'...'}}
                                    </template>
                                    <template v-else>
                                        {{bout.projectName}}
                                    </template>
                                </el-col>
                                <el-col style="margin-top: 16px;height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);cursor: pointer;">
                                    <span  @click="saveRecord(2,bout)">收藏</span>
                                    <span style="margin-left: 20px;" @click="shareInfo(bout.id)">分享</span>
                                </el-col>
                                <el-col style="cursor: pointer;margin-top: 15px;height:39px;border:0px solid rgba(255,131,21,1);background:linear-gradient(0deg,rgba(253,64,6,1) 1%,rgba(255,155,58,1) 100%);font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);line-height: 35px;">
                                    <router-link :to="{path:'/kjsc/detail',query:{projectObj:bout}}" tag="a" class="routerLink-a">查看详情</router-link>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="margin: 69px auto 0;width: 1240px;text-align: right;" type="flex" :gutter="20">
                <el-col>
                    <el-link :underline="false" @click="showMoreProduct('2')"  style="height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgb(202, 0, 0);">查看更多</el-link>
                </el-col>
            </el-row>
            <el-row style="margin: 15px auto 0;" justify="center" type="flex" class="w1240">
                <el-col style="width: 267px;">
                    <img style="height: 677px;height: 100%;"
                         src="../../images/zichanjy/zhuanlizhuanrang.png"/>
                </el-col>
                <el-col :span="19">
                    <el-row class="chengguotList zhunList" type="flex" justify="end" style="margin-top: -11px;flex-wrap: wrap;-webkit-flex-wrap: wrap;">
                        <el-col class="cgBox"  v-for="(patent) in patentList" :key="patent.id+'_patent'" >
                            <!--<el-image :src="pInfo.homeImg" @click="showDetail(pInfo)"></el-image>-->
                           <!-- <router-link :to="{path:'/kjsc/detail',query:{projectObj:patent}}" tag="a" class="list-link-a">
                                <img style="width: 187px; height: 198px;margin-top: 11px;" :src="patent.homeImg" />
                            </router-link>-->
                            <router-link :to="{path:'/kjsc/detail',query:{projectObj:patent}}" v-if="patent.homeImg != ''" tag="a" class="list-link-a">
                                <img style="width: 187px; height: 198px;margin-top: 11px;" :src="patent.homeImg" />
                            </router-link>
                            <router-link :to="{path:'/kjsc/detail',query:{projectObj:patent}}" v-else tag="a" class="list-link-a">
                                <img style="width: 187px; height: 198px;margin-top: 11px;" src="../../images/img-zhuanl.png" />
                            </router-link>
                            <router-link :to="{path:'/kjsc/detail',query:{projectObj:patent}}" tag="a" class="list-link-a" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 28px;">
                              <h3 :title="patent.projectName">
                                  <template v-if="patent.projectName.length>10">
                                      {{patent.projectName.substring(0,10)+'...'}}
                                  </template>
                                  <template v-else>
                                      {{patent.projectName}}
                                  </template>
                              </h3>
                            </router-link>
                            <!--if(response.data.tremaining>10000){
                            this.userWalletInfo.tremaining = parseInt(response.data.tremaining/10000)+'.'+(response.data.tremaining%10000)+'万';
                            }-->
                            <p>
                                <template v-if="undefined==patent.unitPrice||''==patent.unitPrice">
                                    <em class="priceMark">面议</em>
                                </template>
                                <template v-else>
                                    <em class="priceMark">
                                        {{patent.unitPrice>10000?patent.unitPrice/10000+'万':patent.unitPrice}}</em>¥
                                </template>
                            </p>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="margin: 69px auto 0;width: 1240px;text-align: right;" type="flex" :gutter="20" v-if="brandList.length>0">
                <el-col>
                    <el-link :underline="false" @click="showMoreProduct('1')"  style="height:14px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgb(202, 0, 0);">查看更多</el-link>
                </el-col>
            </el-row>
            <el-row style="margin: 15px auto 0;" justify="center" type="flex" class="w1240" v-if="brandList.length>0">
                <el-col :span="19">
                    <el-row class="chengguotList" style="margin-top: -11px;" >
                        <el-col class="cgBox"  v-for="brand in brandList" :key="brand.id+'brand'" >
                            <!--<el-image :src="pInfo.homeImg" @click="showDetail(pInfo)"></el-image>-->
                            <router-link :to="{path:'/kjsc/detail',query:{projectObj:brand}}" tag="a" class="list-link-a">
                                <img style="width: 187px; height: 198px;margin-top: 11px;" :src="brand.homeImg" />
                            </router-link>
                            <router-link :to="{path:'/kjsc/detail',query:{projectObj:brand}}" tag="a" class="list-link-a" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 28px;">
                                <h3 :title="brand.projectName">
                                    <template v-if="brand.projectName.length>10">
                                        {{brand.projectName.substring(0,10)+'...'}}
                                    </template>
                                    <template v-else>
                                        {{brand.projectName}}
                                    </template>
                                </h3>
                            </router-link>
                            <!--<p><em class="priceMark">{{brand.unitPrice}}</em>¥</p>-->
                            <p>
                                <template v-if="undefined==brand.unitPrice||''==brand.unitPrice">
                                    <em class="priceMark">面议</em>
                                </template>
                                <template v-else>
                                    <em class="priceMark">
                                        {{brand.unitPrice>10000?brand.unitPrice/10000+'万':brand.unitPrice}}</em>¥
                                </template>
                            </p>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col style="width: 267px;">
                    <img style="height: 677px;"
                         :src="require('@/images/zichanjy/shangbiaozhuanrang.png')"/>
                </el-col>
            </el-row>
        </template>
        <template v-else-if="activeName=='1'">
            <template v-if="!serviceDetFlag">
                <el-row type="flex" justify="space-between" class="serveBox">
                    <el-col class="el-fuwu-col-bg">
                        <p>服务类别</p>
                    </el-col>
                    <el-col v-for="flb in fwlbList" :key="flb.name" class="el-fuwu-col-info">
                        <el-row class="row-img">
                            <el-col>
                                <img :src="flb.photo" />
                            </el-col>
                        </el-row>
                        <el-row class="row-name">
                            <el-col style="">
                                {{flb.name}}
                            </el-col>
                        </el-row>
                        <el-row class="row-btnZx" type="flex" justify="center">
                            <el-col>
                                <div class="btnZx" @click="showZxDialog(undefined)">
                                    立即咨询
                                </div>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
                <el-row style="margin:118px auto 0;" type="flex" class="w1240" >
                    <el-col :span="4">
                        <span style="width:47px;height:34px;font-size:48px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1)">C1</span>
                        <span style="line-height:53px;width:123px;height:29px;font-size:30px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">代办服务</span>
                    </el-col>
                    <el-col :span="4"></el-col>
                </el-row>
                <el-row class="el-row-rzdj-bg" type="flex" justify="end">
                    <el-col  :span="2" style="cursor: pointer; line-height: 74px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);">
                        <span @click="showServiceDet">查看详情</span>
                    </el-col>
                </el-row>
                <el-row class="w1240" style="margin: 0 auto;">
                    <el-col style="width: 252px;">
                        <img style="height: 354px;" :src="require('@/images/fuwujy/images/rzdjc1.png')"/>
                    </el-col>
                    <el-col style="width: calc(100% - 252px);"><!--width: 53.16667%;-->
                        <el-row v-for="(arr,index) in brokerList" :key="index" type="flex" justify="space-around" >
                            <el-col v-for="broker in arr" :key="broker.id" :span="20"  style="height: 176px; background:rgba(255,255,255,0.1);box-shadow:0px 8px 8px 0px rgba(8,1,3,0.2);">
                                <el-row style="margin-top: 28px;">
                                    <el-col :span="10">
                                        <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">{{broker.brokerType}}</span>
                                    </el-col>
                                    <el-col :span="12">
                                        <!--<el-avatar fit="contain" :size="80" :src="broker.photo"></el-avatar>-->
                                        <img :src="broker.photo" style="border-radius: 50%;width: 86px;height: 86px;"/>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="10" style="margin-top: -30px;">
                                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">办事高效快捷</span>
                                    </el-col>
                                    <el-col :span="14" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-left: 38%;">{{broker.userName}}</el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="11">
                                        <div class="btnZx" style="margin-left: 60px;" @click="showZxDialog(broker.id)">
                                            立即咨询
                                        </div>
                                    </el-col>
                                    <el-col :span="10" style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-bottom: 24px;margin-top: 15px;">已成交{{broker.turnover}}笔</el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                        <div style="position: initial;margin-top: -20%;float: right;cursor: pointer; z-index: 999999;margin-right: 10px;opacity: 0.3;">
                            <img :src="require('@/images/fuwujy/next.png')"/>
                        </div>
                    </el-col>
                </el-row>
                <!--<el-row style="height: 60px;background: #EEE;margin-top: 50px;"></el-row>-->
                <el-row style="margin: 100px auto 0;" type="flex" class="w1240">
                    <el-col :span="4">
                        <span style="width:47px;height:34px;font-size:48px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1)">C2</span>
                        <span style="line-height:53px;width:123px;height:29px;font-size:30px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">代办服务</span>
                    </el-col>
                    <el-col :span="4"></el-col>
                </el-row>
                <el-row class="el-row-rzdj2-bg" type="flex" justify="end">
                    <el-col  :span="2" style="cursor: pointer; line-height: 74px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(255,255,255,1);">
                        <span @click="showServiceDet">查看详情</span>
                    </el-col>
                </el-row>
                <el-row class="w1240" style="margin: 0 auto;">
                    <el-col style="width: 252px;">
                        <img style="height: 354px;" :src="require('@/images/fuwujy/images/rzdjc2.png')"/>
                    </el-col>
                    <el-col style="width: calc(100% - 252px);">
                        <el-row v-for="(arr,index) in brokerCList" :key="index+'_a'" type="flex" justify="space-around" >
                            <el-col v-for="broker in arr" :key="broker.id" :span="20"  style="height: 176px; background:rgba(255,255,255,0.1);box-shadow:0px 8px 8px 0px rgba(8,1,3,0.2);">
                                <el-row style="margin-top: 28px;">
                                    <el-col :span="10">
                                        <span style="font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">{{broker.brokerType}}</span>
                                    </el-col>
                                    <el-col :span="12">
                                        <img :src="broker.photo" style="border-radius: 50%;width: 86px;height: 86px;"/>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="10" style="margin-top: -30px;">
                                        <span style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">办事高效快捷</span>
                                    </el-col>
                                    <el-col :span="14" style="font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-left: 38%;">{{broker.userName}}</el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="11">
                                        <div class="btnZx" style="margin-left: 60px;" @click="showZxDialog(broker.id)">
                                            立即咨询
                                        </div>
                                    </el-col>
                                    <el-col :span="10" style="font-size:12px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-bottom: 24px;margin-top: 15px;">已成交{{broker.turnover}}笔</el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                        <div style="position: initial;margin-top: -20%;float: right;cursor: pointer; z-index: 999999;margin-right: 10px;opacity: 0.3">
                            <img :src="require('@/images/fuwujy/next.png')"/>
                        </div>
                    </el-col>
                </el-row>
            </template>
            <template v-else>
                <SERVICEDEAL></SERVICEDEAL>
            </template>
        </template>
        <template v-else-if="activeName=='3'">
            <techachievement></techachievement>
        </template>
        <template v-else>
            <el-row style="margin-top: 95px;" justify="center" type="flex">
                <el-col :span="4" style="width: 98px;">
                    <img :src="require('@/images/zhengcebt/zcbt1.png')"/>
                </el-col>
                <el-col :span="3"  style="width: 136px;margin-right: 80px;margin-top: 24px;">
                    政策解读
                    <br/>
                    实用政策，全面解读
                </el-col>
                <el-col :span="4"  style="width: 98px;">
                    <img :src="require('@/images/zhengcebt/zcbt2.png')"/>
                </el-col>
                <el-col :span="3"  style="width: 136px;margin-right: 80px;margin-top: 24px;">
                    免费评估
                    <br/>
                    科学测评，免费分析
                </el-col>
                <el-col :span="4"  style="width: 98px;">
                    <img :src="require('@/images/zhengcebt/zcbt3.png')"/>
                </el-col>
                <el-col :span="3" style="width: 136px;margin-right: 80px;margin-top: 24px;">
                    申报管理
                    <br/>
                    项目申报，免费体验
                </el-col>
                <el-col :span="3" style="width: 98px;">
                    <img :src="require('@/images/zhengcebt/zcbt4.png')"/>
                </el-col>
                <el-col :span="2"  style="width: 136px;margin-top: 24px;">
                    全程辅导
                    <br/>
                    专业团队，负责到底
                </el-col>
            </el-row>
            <el-row style="margin-top: 199px;">
                <el-col style="font-size:29px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">关于申请政策补贴，您是否遇到这些问题？</el-col>
            </el-row>
            <el-row style="margin-top: 29px;" type="flex" justify="center">
                <el-col style="width: 53px;">
                    <div style="height: 4px;border: 1px solid #e24515;background-color: #e24515;"></div>
                </el-col>
            </el-row>
            <el-row type="flex" justify="center" style="margin-top: 49px;">
                <el-col :span="5">
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 54px;">申报政策不太理解?</p>
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 93px;">申报流程太过复杂?</p>
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 99px;">担心项目评审无法通过?</p>
                </el-col>
                <el-col :span="8">
                    <img :src="require('@/images/zhengcebt/question-mark-1019820_960_720.png')"/>
                </el-col>
                <el-col :span="5">
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 54px;">项目规划不清晰?</p>
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 93px;">材料编制过于困难?</p>
                    <p style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);margin-top: 99px;">思绪杂乱不知从何开始?</p>
                </el-col>
            </el-row>
            <el-row style="margin-top: 22px;" >
                <el-col style="float:inherit;">
                    <div style="width: 772px;height: 76px; margin: 0 auto; text-align: left;background-color: #024ECC;border-radius: 50px;">
                            <span style="font-size: 23px;margin-left: 107px;line-height: 75px;color: white;">
                                联系我们，提高申报成功率！
                            </span>
                        <div @click="showZxDialog(undefined)" style="cursor: pointer; margin-left: 50px;background-color: white;border-radius: 50px;width: 173px;height: 49px;float: right;margin-right: 108px;line-height: 82px;margin-top: 14px;">
                            <img :src="require('@/images/zhengcebt/kefu.png')" style="margin-left: 22px;"/>
                        </div>
                        <div @click="showZxDialog(undefined)" style="cursor: pointer; float: right;margin-right: 135px;margin-top: -45px;">
                            <span style="font-size: 18px;color: #024ECC;margin-left: 8px;font-weight:normal;">立即咨询</span>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <el-row style="margin-top: 117px;">
                <el-col style="font-size:23px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);">我们的服务流程</el-col>
            </el-row>
            <el-row style="margin-top: 28px;">
                <el-col style="font-size:22px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29, 29, 29, 0.35);">专业化服务，保证成功率</el-col>
            </el-row>
            <el-row style="margin-top: 27px;" type="flex" justify="center">
                <el-col style="width: 53px;">
                    <div style="height: 4px;border: 1px solid #e24515;background-color: #e24515;"></div>
                </el-col>
            </el-row>
            <el-row style="margin-top: 79px;" type="flex" justify="center">
                <el-col :span="16" class="minW1240">
                    <el-row type="flex" justify="center">
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng1.png')"/>
                            <br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                                <img :src="require('@/images/zhengcebt/xiaotiao2.png')" style="width:1093px;margin-left: 80px;" />
                            </div>
                        </el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng2.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng3.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng4.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng5.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng6.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng7.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng8.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                        <el-col :span="liuchengspan"><img :src="require('@/images/zhengcebt/liucheng9.png')"/><br/>
                            <div  class="xiaotiao-bg">
                                <img :src="require('@/images/zhengcebt/tuoyuan.png')" style="margin-top: 20px;"/>
                            </div></el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="margin-top: 13px;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color: #6C6C6C" type="flex" justify="center" class="processBox">
                <el-col :span="16" class="minW1240">
                    <el-row type="flex" justify="center">
                        <el-col :span="liuchengspan" style="padding-left: 0px;">现场调研</el-col>
                        <el-col :span="liuchengspan">制定规划</el-col>
                        <el-col :span="liuchengspan">项目评估</el-col>
                        <el-col :span="liuchengspan">材料编制</el-col>
                        <el-col :span="liuchengspan">装订提交</el-col>
                        <el-col :span="liuchengspan">答辩辅导</el-col>
                        <el-col :span="liuchengspan">立项辅导</el-col>
                        <el-col :span="liuchengspan">跟踪管理</el-col>
                        <el-col :span="liuchengspan">验收辅导</el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row type="flex" justify="center" style="margin-top: 99px;">
                <el-col :span="4">
                    <div @click="showZxDialog(undefined)" style="cursor: pointer;background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);width: 277px;height: 55px;color: white;font-size: 18px;border-radius: 5px;line-height: 55px;">
                        咨询政府补贴申请条件
                    </div>
                </el-col>
            </el-row>
        </template>
        <el-dialog title="服务交易" :visible.sync="dialogFormVisible" class="requireAskBox allDialog">
<!--            <el-divider></el-divider>-->
            <el-row type="flex" style="margin-top: 10px;">
                <el-col :span="4" style="margin-top: 10px;">
                    服务名称:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.serverName" class="el-input__inner" style="border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    服务类型:
                </el-col>
                <el-col :span="20">
                    <el-select v-model="serverForm.serverType" placeholder="请选择" style="width: 100%;">
                        <el-option
                                v-for="item in serverTypeList"
                                :key="item.title"
                                :label="item.title"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-col>
            </el-row>
            <el-row type="flex" style="height: 80px;padding-top: 10px;" >
                <el-col :span="4">
                    服务需求:
                </el-col>
                <el-col :span="20">
                    <el-input type="textarea" :row="4" v-model="serverForm.serverRemark" class="el-input__inner" style="width: 100%;height: 100%; border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    姓名:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.userName" placeholder="您的姓名" class="el-input__inner" style="border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    手机号码:
                </el-col>
                <el-col :span="14">
                    <input type="text" v-model="serverForm.phoneNo" class="el-input__inner" placeholder="您的手机号吗" style="font-size: 12px; border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
                <el-col :span="6" style="margin-top: 3px;text-align: right;">
                    <el-button class="btnNoBgdBule sendCode" @click="pushCode" size="small" v-if="disabledFlag==false">发送验证码</el-button>
                    <el-button class="btnColorBlue" style="cursor: default;" v-if="disabledFlag">{{yzmTitle}}</el-button>
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    验证码:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.verifyCode" placeholder="请输入验证码" class="el-input__inner" style=" border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer" style="text-align: center">
                <el-button type="warning" @click="submitServerAdv" class="btnColorOrg" style="width: 199px;font-size: 16px;">确 定</el-button>
                <el-row style="margin-top: 20px;border-bottom: 0;text-align: center;">
                    <el-col style="text-align: center;"><i class="icon-shield"></i>信息保护中,请放心填写</el-col>
                </el-row>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {qryProjectList} from "../../api/projectDw/projectDw";
    import {coreServerReq,getUserManagerReq} from "../../api/login";
    import {sendPhoneCode,saveServerAdvisory} from "../../api/admin/index";
    import {saveRecord} from "../../api/user/serverOperate";
    import SERVICEDEAL from "../../views/kjsc/serviceDeal";
    import TECHACHIEVEMENT from "../../views/kjsc/techAchievement"
    export default {
        name: "chengguo",
        components: {
            SERVICEDEAL,
            "techachievement":TECHACHIEVEMENT
        },
        data() {
          return {
              serverForm:{
                  serverName:"",
                  serverType:"",
                  serverRemark:"",
                  userName:"",
                  phoneNo:"",
                  verifyCode:''
              },
              yzmTime:120,
              yzmTimer:null,
              yzmTitle:"发送验证码",
              disabledFlag:false,
              serviceDetFlag:false,
              serverTypeList:[],
              dialogFormVisible:false,
              activeName:'0',
              liuchengspan:3,
              brandTemp:[],
              patentTemp:[],
              boutiqueList:[],
              patentList:[],
              brandList:[],
              brokerList:[],
              brokerCList:[],
              fwlbList:[
                  {
                      name:"技术合同登记服务",
                      photo:require('@/images/fuwujy/lb1.png'),
                      url:''
                  },
                  {
                      name:"技术成果认定登记服务",
                      photo:require('@/images/fuwujy/lb2.png'),
                      url:''
                  },
                  {
                      name:"高企认定服务",
                      photo:require('@/images/fuwujy/lb3.png'),
                      url:''
                  },
                  {
                      name:"知识产权服务",
                      photo:require('@/images/fuwujy/lb4.png'),
                      url:''
                  },
                  {
                      name:"金融服务",
                      photo:require('@/images/fuwujy/lb5.png'),
                      url:''
                  },
                  {
                      name:"财税服务",
                      photo:require('@/images/fuwujy/lb6.png'),
                      url:''
                  },
                  {
                      name:"法律服务",
                      photo:require('@/images/fuwujy/lb7.png'),
                      url:''
                  }
              ]
          }
        },
        created(){
            this.boutiqueQry();
            if(this.$route.query.chengguoActiveName){
                this.activeName = this.$route.query.chengguoActiveName+'';
            }
            if(this.$route.query.aiServiceDetFlag){
               this.showServiceDet();
            }
        },
        mounted(){
            this.brandQry();
            this.patentQry();
            this.qryServerType();
            this.$nextTick(function () {
                this.loadBorkerData();
                this.loadBorkerC2Data();
            })
        },
        methods:{
            saveRecord(operateType,projectDetail){
                var par ={
                    userId:this.$store.state.user.userInfo.id,
                    operateType:operateType,
                    serverId:projectDetail.id,
                    serverName:projectDetail.projectName,
                    img:projectDetail.homeImg,
                    price:projectDetail.unitPrice,
                    type:projectDetail.projectType,
                    path:this.$route.path
                }
                saveRecord(par).then(response=>{
                    if(operateType==2){
                        this.$message({
                            message: '收藏成功',
                            type: 'success'
                        });
                    }
                });
            },
            showServiceDet(){
                this.serviceDetFlag = true;
            },
            loadBorkerData(){
                var promList=[];
                var par = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:6,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par);
                var par2 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:3,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par2);
                var par3 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:7,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par3);
                var par4 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:4,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par4);
                Promise.all(promList).then(response=>{
                    var arr = [];
                    response[0].brokerType="技术合同登记服务";
                    arr.push(response[0]);
                    response[1].brokerType="科技咨询服务";
                    arr.push(response[1]);
                    this.brokerList.push(arr);
                    var arr1 = [];
                    response[2].brokerType="技术成果认定登记服务";
                    arr1.push(response[2]);
                    response[3].brokerType="科技金融服务";
                    arr1.push(response[3]);
                    this.brokerList.push(arr1);
                });
            },
            loadBorkerC2Data(){
                var promList=[];
                var par = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:1,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par);
                var par2 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:2,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par2);
                var par3 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:5,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par3);
                var par4 = new Promise((resolve, reject) => {
                    getUserManagerReq({borkerId:13,pageSize:1,status:3}).then(response => {
                        resolve(response.data[0]);
                    });
                });
                promList.push(par4);
                Promise.all(promList).then(response=>{
                    var arr = [];
                    response[0].brokerType="知识产权服务";
                    arr.push(response[0]);
                    response[1].brokerType="财税服务";
                    arr.push(response[1]);
                    this.brokerCList.push(arr);
                    var arr1 = [];
                    response[2].brokerType="法律服务";
                    arr1.push(response[2]);
                    response[3].brokerType="其他服务";
                    arr1.push(response[3]);
                    this.brokerCList.push(arr1);
                });
            },
            pushCode(){
                if(!(/^1[3456789]\d{9}$/.test(this.serverForm.phoneNo))){
                    this.$message.error('手机号格式错误！')
                    return;
                }
                sendPhoneCode(this.serverForm).then(response => {
                    if(response.data==0){
                        var that = this;
                        this.yzmTimer = setInterval(function(){
                            that.disabledFlag = true;
                            if(that.yzmTime==1){
                                that.yzmTitle="发送验证码";
                                that.disabledFlag = false;
                                window.clearInterval(that.yzmTimer);
                                that.yzmTimer = null;
                                that.yzmTime = 120;
                            }else{
                                that.yzmTitle="请在"+that.yzmTime+"秒内输入";
                                that.yzmTime= that.yzmTime -1;
                            }
                        },1000);
                    }else{
                        this.$message({
                            message: '验证码发送失败!',
                            type: 'warning'
                        });
                    }
                });
            },
            submitServerAdv(){
                saveServerAdvisory(this.serverForm).then(response => {
                    if(response.retCode==0){
                        this.$confirm('<div><p>请注意接听北部湾国际技术转移转化中心专属顾问电话</p>' +
                            '<p>18347866523</p></div>', '您的资料已提交!', {
                            confirmButtonText: '确定',
                            type: 'success',
                            dangerouslyUseHTMLString: true,
                            showCancelButton:false,
                            center: true
                        }).then(() => {
                            this.serverForm={
                                serverName:"",
                                    serverType:"",
                                    serverRemark:"",
                                    userName:"",
                                    phoneNo:"",
                                    verifyCode:''
                            };
                            this.dialogFormVisible = false;
                        }).catch(() => {
                        });
                    }else{
                        this.$message({
                            message: response.message,
                            type: 'warning'
                        });
                    }
                });
            },
            qryServerType(){
                coreServerReq().then(response => {
                    this.serverTypeList = response.data;
                });
            },
            boutiqueQry(){
                qryProjectList({"boutique":1,"pageSize":5,"status":2}).then(response => {
                    this.boutiqueList = response.data;
                });
            },
            brandQry(){
                qryProjectList({"projectType":1,"status":2,"pageSize":8,"needType":1}).then(response => {
                    this.brandList = response.data;
                });
            },
            patentQry(){
                qryProjectList({"projectType":2,"status":2,"pageSize":8,"needType":1}).then(response => {
                    this.patentList = response.data;
                });
            },
            showMoreProduct(val){
                if(val==1 && this.brandList.length==0){
                    this.$message({
                        message: '商标暂无更多数据',
                        type: 'warning'
                    });
                    return;
                }
                if(val==2 && this.patentList.length==0){
                    this.$message({
                        message: '专利暂无更多数据',
                        type: 'warning'
                    });
                    return;
                }
                this.$router.push({path:"/kjsc/all",query:{projectType:val}});
            },
            handleClick(tab, event) {
                if(tab.name=='0'){
                    this.$emit("swBgImg-event","el-row-kjsc-statistic");
                }else if(tab.name=='1'){
                    this.$emit("swBgImg-event","el-row-kjsc-fwjy-statistic");
                    this.serviceDetFlag = false;
                }else if(tab.name=='2'){
                    this.$emit("swBgImg-event","el-row-kjsc-zcbt-statistic");
                }else if(tab.name=='3'){
                    this.$emit("swBgImg-event","el-row-kjsc-jscg-statistic");
                }
            },
            showZxDialog(id){
                if(undefined!=id){
                    this.serverForm.borkerId = id;
                }else{
                    this.serverForm.borkerId = null;
                }
                this.dialogFormVisible = true;
            },
            shareInfo(id){
                var href = window.location.href;
                href = href+'/detail?shareId='+id;
                this.$alert('请复制地址:'+href, '分享地址', {
                    confirmButtonText: '确定',
                    callback: action => {
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .chengguotList .cgBox{width: 237px;height: 331px;color: #1D1D1D;margin-right:5px;margin-top: 13px; box-shadow:0px 2px 6px 0px rgba(8,1,3,0.5);padding: 0 14px;text-align: center;}
    .chengguotList .cgBox:nth-child(4n){margin-right: 9px;}
    .chengguotList.zhunList .cgBox:nth-child(4n){margin-right: 0px;}
    .cgBox .img{width: 100%;height: 198px;margin-top: 11px;}
    .cgBox h3{font-size: 14px;margin-top: 24px;}
    .cgBox p{margin-top: 15px; }
    .cgBox em{font-size: 20px;color: #CA0000;margin-right: 5px;font-style: normal;}
    .chengguo{
        margin-bottom: 163px;
    }

    /deep/ .tabKjsc .el-tabs__active-bar {
        width: 189px;
        position: absolute;
        bottom: 0;
        left: 0;
        height: 2px;
        background-color: #CA0000;
        z-index: 1;
        -webkit-transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1), -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1),-webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        list-style: none;
    }
    /deep/ .tabKjsc .el-tabs__item {
        padding: 0;
        height: 40px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        line-height: 40px;
        display: inline-block;
        list-style: none;
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(29,29,29,1);
        position: relative;margin: 0 30px;
    }
    .tabKjsc /deep/ .el-tabs__item:nth-child(2){margin-left: 0;}
    /deep/ .el-tabs--bottom .el-tabs__item.is-bottom:nth-child(2), /deep/ .el-tabs--bottom .el-tabs__item.is-top:nth-child(2),
    /deep/ .el-tabs--top .el-tabs__item.is-bottom:nth-child(2), /deep/ .el-tabs--top .el-tabs__item.is-top:nth-child(2){padding:0;}
    /deep/ .el-tabs--bottom .el-tabs__item.is-bottom:last-child, /deep/ .el-tabs--bottom .el-tabs__item.is-top:last-child,
    /deep/ .el-tabs--top .el-tabs__item.is-bottom:last-child, /deep/ .el-tabs--top .el-tabs__item.is-top:last-child{padding: 0;}
    /deep/ .tabKjsc .el-tabs__item.is-active {
        font-size: 24px;
        color: rgba(29,29,29,1);
    }
    /deep/ .el-fuwu-col-bg{
        background: linear-gradient(10deg,#41333F,#954345);
        height: 296px;
        line-height: 296px;
    }
    /deep/ .el-fuwu-col-bg p{color: #FFF;font-size: 30px;font-weight:normal; text-shadow: 0.05em 0.1em 0.1em #000;}
    /deep/ .el-row-rzdj-bg{
        width: 1240px;
        background: url(../../images/fuwujy/images/fwjyrzdj.png) center no-repeat;
        background-size: contain;
        height: 74px;
        margin: 36px auto 0;
    }
    /deep/ .el-row-rzdj2-bg{
        width: 1240px;
        background: url(../../images/fuwujy/images/fwjyrzdj2.png) center no-repeat;
        background-size: contain;
        height: 74px;
        margin: 36px auto 0;
    }
    .xiaotiao-bg{
        background: url(../../images/zhengcebt/xiaotiao.png) center no-repeat;
        background-size: contain;
        height: 63px;
    }
    .fwlb-img{
        width: 130px;
    }
    .routerLink-a{
        text-decoration:none;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:rgba(255,255,255,1);
    }
    .listprice-a{
        text-decoration:none;
        font-size:20px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:rgba(202,0,0,1);
    }
    .list-link-a{
        text-decoration:none;
        font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:rgba(29,29,29,1);
    }
    .btnZx{cursor: pointer;width: 90px;height: 32px;line-height: 30px; font-size:14px;font-weight:400;color: rgba(255,255,255,1);border-radius: 5px;background: linear-gradient(90deg,#f74a0d 0%,#fa963d 100%);margin: 0 auto;}
    /deep/ .requireAskBox .el-dialog{width: 604px;height: 493px;}
    /deep/ .requireAskBox .el-dialog__header{text-align: left;}
    /deep/ .requireAskBox .el-dialog__body{padding: 0 20px 30px;}
    /deep/ .requireAskBox .el-divider--horizontal{margin: 0;}
    /deep/ .requireAskBox .el-dialog__title{font-size: 16px;color: #1D1D1D;}
    /deep/ .requireAskBox .el-row{border: 0 none;border-bottom: 1px solid #ccc;border-radius: 0px;}
    /deep/ .requireAskBox .el-row>.el-col:first-child{font-size: 14px;color: #1D1D1D;text-align: left;}
    /deep/ .requireAskBox .el-row>.el-col:last-child{font-size: 12px;}
    /deep/ .requireAskBox .el-input__inner{padding: 0;}
    /deep/ .requireAskBox .el-textarea__inner{width: 100%;height: 100%;border: none;padding: 0;}
    /deep/ .requireAskBox .el-input__inner{border: none;}
/*    服务类别*/
    /deep/ .el-fuwu-col-info{border-right:1px solid rgba(204,204,204,1);margin: 28px 0;}
    .serveBox{width: 100%;margin-top: 60px;border-top: 1px solid rgba(204,204,204,1);border-bottom: 1px solid rgba(204,204,204,1);}
    /deep/ .row-name{margin-top: 26px;}
    /deep/ .row-name .el-col{font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(29,29,29,1);}
    /deep/ .row-btnZx{margin-top: 24px;}
    /deep/ .el-fuwu-col-info:last-child{border-right: none;}
    /deep/ .row-img img{width: 68%;}
    .processBox /deep/ .el-col{padding-left: 10px;}
    /*验证码*/
    /deep/ .el-button.sendCode{width: 100px; height: 38px;padding: 0;}

    @media screen and (max-width: 1480px) {
        /deep/ .el-fuwu-col-bg{font-size: 26px;height: 260px;line-height: 260px;}
        /deep/ .row-name .el-col{font-size: 15px;}
        /deep/ .el-fuwu-col-info{margin: 20px 0;}
    }
</style>