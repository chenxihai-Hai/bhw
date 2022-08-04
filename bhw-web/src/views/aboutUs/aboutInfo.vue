<template>
    <div>
        <TopBar></TopBar>
        <el-divider></el-divider>
        <div style="background: #FBFEFF;padding-bottom: 200px;">
            <div class="breadWrap w1240">
                <span class="breakCurr"><i class="icon-bread"></i>当前位置：</span>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/about' }">关于我们</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <el-row class="about-title"><h2>关于我们</h2><p>About us</p></el-row>
            <el-row style="margin: 0 auto;">
                <el-menu class="aboutMenu" :default-active="intellectuaAboutMenuActive" mode="horizontal" @select="intellectualAboutMenuSelect">
                    <el-menu-item index="1">交易保障</el-menu-item>
                    <el-menu-item index="2">廉正举报</el-menu-item>
                    <el-menu-item index="3">法律声明</el-menu-item>
                    <el-menu-item index="4">隐私政策</el-menu-item>
                </el-menu>
            </el-row>
<!--        交易保障    -->
            <template v-if="intellectuaAboutMenuActive == 1">
                <el-row class="aboutBox">
                    <el-divider class="lowLine"></el-divider>
                    <h4>签署法律合同</h4>
                    <h3>专利《转让协议书》、《代理委托书》、《专利证书》解析</h3>
                    <div class="aboutCont" v-show="showContract">
                        <el-row>
                            <p> 专利《转让协议书》、《代理委托书》、《专利证书》解析</p>
                            <p>(1) 《转让协议书》和《代理委托书》是专利转让中上报国知识产权間的必须文件，为国家知识产权局样式文档，用于专利转让申报使用。 (《转让协议书)和《代理委托书》需要双方签字，我网报送国家知识产权局:</p>
                            <p>(2)交易成功，收到客户剩余尾款后，我网将邮寄纸质版《手楼合格通知书)、《专利证书》、《登记障副本》、官费收据等文件给到客户。 </p>
                        </el-row>
                        <el-row style="margin-top: 100px;">
                            <h3>商标《公证书原件》、《转让申请书》 、《代理委托书》 解析</h3>
                            <p>交易过程中，收到买家剩余尾款后，高航网顾问将《商标注册证康件》《公证书原件》 和经商标持有人签字或盖章的《转让申请书》《代理要托书》</p>
                            <p>(1)公证书原件作用:商标局在办理商标转让业务时，一般都要求转让双方进行公证，若不及时办理，有可能会需要补正:通过公证方式，商标执买家，确保商标转让真实有效更具法律效应，增加被公证事物证据的公信度，有效保护我网客户的利益。</p>
                            <p>(2)转让申请书、代理委托书是商标转让中上报国家商标局的必须文件，为国家商标局样式文档，用于商标转让申报使用。其中转让申请书、 慨签字，我网报送国家商标局。</p>
                        </el-row>
                    </div>
                    <el-row style="margin-top: 57px;"><el-button class="changeShow" @click="showBox('showContract')"><i :class="'el-icon-d-arrow-left '+[showContract==false?'boxHide':'']"></i></el-button></el-row>
                </el-row>
                <el-row class="aboutBox">
                    <el-divider class="lowLine"></el-divider>
                    <h4>服务协议</h4>
                    <h3>北部湾国际技术转移转化中心公共服务平台开户协议</h3>
                    <div class="aboutCont xieyBox" v-show="showAgreement">
                        <el-row>
<!--                            <p>欢迎您注册北部湾国际技术转移转化中心公共服务平台！</p>-->
<!--                            <p>为使用北部湾国际技术转移转化中心公共服务平台（以下简称“本平台”），用户应当阅读并遵守《北部湾国际技术转移转化中心公共服务平台开户协议》（以下简称“本协议”）。请用户务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款，限制、免责条款可能以加粗形式提示用户注意。</p>-->
<!--                            <p>除非您已阅读并接受本协议所有条款，否则您不能注册本平台或使用本平台相关服务。如您通过注册程序或任何其他方式使用或接受本平台的任何服务即视为您已阅读并同意上述协议的约束。</p>-->
<!--                            <p>如果您未满18周岁，请在法定监护人的陪同下阅读本协议，并在进行注册、下单、支付等任何行为或使用本平台的其他任何服务前，事先征得您的法定监护人的同意。</p>-->
<!--                            <p>一、协议的范围</p>-->
<!--                            <p>1.1 协议适用主体范围</p>-->
<!--                            <p>本协议是用户与本平台之间关于使用本平台相关服务所订立的协议。</p>-->
<!--                            <p>1.2 协议关系及冲突条款</p>-->
<!--                            <p>本协议内容同时包括本平台可能不断发布的关于本服务的相关协议、业务规则等内容。上述内容一经正式发布，即为本协议不可分割的组成部分，与本协议具有同等法律效力，用户同样应当遵守。</p>-->
                            <agreement-info></agreement-info>
                        </el-row>
                    </div>
                    <el-row style="margin-top: 57px;"><el-button class="changeShow" @click="showBox('showAgreement')"><i :class="'el-icon-d-arrow-left '+[showAgreement==false?'boxHide':'']"></i></el-button></el-row>
                </el-row>
            </template>
<!--        廉正举报    -->
            <template v-else-if="intellectuaAboutMenuActive == 2">
                <el-row class="aboutBox">
                    <el-divider class="lowLine"></el-divider>
                    <h3 style="margin-top: 31px;">廉正举报须知</h3>
                    <div class="aboutCont">
                        <el-row>
                            <p>北部湾国际技术转移转化中心公共服务平台致力于打造公开、透明、开放的新商业文明，我们对于任何腐败的行为将予以坚决打击。</p>
                            <p>北部湾国际技术转移转化中心公共服务平台廉正合规部受理以下涉及员工违纪的举报：</p>
                            <p>（1）索取、收受贿赂；违规收受礼品或款待；</p>
                            <p>（2）违规投资、就职于北部湾国际技术转移转化中心公共服务平台供应商、商家、合作伙伴等；</p>
                            <p>（3）泄露公司保密信息等；</p>
                            <p>（4）利用职权谋取私利；</p>
                            <p>（5）其他违反法律法规的行为。</p>
                            <p>北部湾国际技术转移转化中心公共服务平台廉正合规部只受理廉正举报（内部员工违纪）。我们希望您采取实名举报，我们承诺会严格保护举报人，并对举报内容严格保密。举报内容必须客观真实。对于立案调查并有结论的举报，我们会根据您留下的联系方式进行反馈。</p>
                        </el-row>
                    </div>
                    <el-row style="margin-top: 70px;"><el-button class="btnColorOrg btnJB" @click="clickReport">内部违纪举报</el-button></el-row>
                </el-row>
            </template>
<!--        法律声明    -->
            <template v-else-if="intellectuaAboutMenuActive == 3">
                <el-row class="aboutBox">
                    <el-divider class="lowLine"></el-divider>
                    <h4>法律声明</h4>
                    <h3>北部湾国际技术转移转化中心公共服务平台法律声明</h3>
                    <div class="aboutCont" v-show="showStatement">
                        <el-row>
                            <p>若要访问和快用网站，您必须不加修改地完全接受本协议中所包含的条款、条件和网站冈时刊登的通告，并且遵守有关互联网及咸本网站的相关法律、规定与规则。一旦您访问、使用了网站，即表示您同意并接受了所有该等条款、条件及通告。</p>
                            <h5>本网站上的信息</h5>
                            <p>本网站上关于会员或他们的产品《包括但不限于公司名称、联系人及联络信息，产品的描述和说明，相关图片、视讯等)的信息均由会员自行提供，会员依法应对其提供的任何信息承担全部责任。对此等信息的准确性、完整性、合法性或真实性均不承担任何责任。此外，对任何使用或提供本网站信息的商业活动及其风险不承担任何责任。</p>
                            <p>未经合法权利人的书面许可，任何人严禁在本网站展示产品图片。任何未经授权便在本网站上使用该图片都可能违反国际法，商标法，隐私权法，通讯、通信等法律法规。</p>
                            <p>浏览者可以下载本网站上显示的资料，但这些资料只限用于个人学习研究使用，不得用于任何商业用途无论是否在资料上明示，所有此等资料都是受到版权法的法律保护。浏览者没有获得或各自的版权所有者明确的书面同意下，不得分发、修改，散布、再使用、再传递或使用本网站的内容用于任何公众商业用途.</p>
                            <h5>版权和商标</h5>
                            <p>网版权所有。所有的权利在全世界范围内受到法律保护，除非有其他的标注或在此等条款和规则中被允许使用，本网站上可阅读和可见的所有资料都受到知识产权法的保护，</p>
                            <p>一诚信通和LoGo商标或标识都是公司的商品及服务商标和商号。所有其他公司没有主张权利的商标和产品名称则可能是它们各自所有权人的商标或注册商标，未获得或其他在本网站上有权使用商标的第三方的书面同意下，本网站不应理解为授权使用被展示于本网站的任何商标。</p>
                            <p>免责声明公司在此特别声明对如下事直不承担任何法律责任:  </p>
                            <p>(一)在此声明，对您使用网站、与本网站相关的任何内容、服务或其它链接至本网站的站点、内容均不作直接、间接、法定、约定的保证。</p>
                            <p>(二)无论在任何原因下(包括但不限于疏忽原因》，对您或任何人通过使用本网站上的信息或由本网站链接的信息，或其他与本网站链按的网站信息所导致的损失或损害(包括直接、间接、特别或后果性的损失或损害，例如收入或利润之损失，电脑系统之损坏或数据丢失等后果)，责任均由使用者自行承担(包括但不限于疏忽责任)</p>
                        </el-row>
                    </div>
                    <el-row style="margin-top: 70px;"><el-button class="changeShow" @click="showBox('showStatement')"><i :class="'el-icon-d-arrow-left '+[showStatement==false?'boxHide':'']"></i></el-button></el-row>
                </el-row>
            </template>
<!--        隐私政策    -->
            <template v-else-if="intellectuaAboutMenuActive == 4">
                <el-row class="aboutBox">
                    <el-divider class="lowLine"></el-divider>
                    <h4>隐私政策</h4>
                    <h3>北部湾国际技术转移转化中心公共服务平台隐私政策</h3>
                    <div class="aboutCont" v-show="showPrivacy">
                        <el-row>
                            <p>平台尊重并保护所有使用平台网络服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，平台会按照本隐私权政策的规定使用和披露您的个人信息。但平台将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，平台不会将这些信息对外披露或向第三方提供。平台会不时更新本隐私权政策。 您在同意平台网络服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于平台网络服务使用协议不可分割的一部分。</p>
                            <p>1. 适用范围在您注册平台帐号时，您根据平台要求提供的个人注册信息（商家应法律法规要求需公示的企业名称及相关工商注册信息除外）；</p>
                            <p>b) 在您使用麦浪网网络服务，或访问平台网页时，平台自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；</p>
                            <p>c) 平台通过合法途径从商业伙伴处取得的用户个人数据。您了解并同意，以下信息不适用本隐私权政策：</p>
                            <p>d) 您在使用平台提供的搜索服务时输入的关键字信息；</p>
                            <p>e) 平台收集到的您在平台发布的有关信息数据，</p>
                            <p>f) 违反法律规定或违反平台规则行为及平台已对您采取的措施。</p>
                            <p style="margin-top: 25px;">2. 信息使用平台不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和平台（含平台关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。</p>
                            <p>b) 平台亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何平台用户如从事上述活动，一经发现，平台有权立即终止与该用户的服务协议。</p>
                            <p>c) 为服务用户的目的，平台可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与平台合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。</p>
                            <p style="margin-top: 25px;">3. 信息披露</p>
                            <p>在如下情况下，平台将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：</p>
                            <p>a) 经您事先同意，向第三方披露；</p>
                            <p>b) 为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；</p>
                            <p>c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露。</p>
                        </el-row>
                    </div>
                    <el-row style="margin-top: 70px;"><el-button class="changeShow" @click="showBox('showPrivacy')"><i :class="'el-icon-d-arrow-left '+[showPrivacy==false?'boxHide':'']"></i></el-button></el-row>
                </el-row>
            </template>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import TopBar from "../layout/TopBar";
    import FootBar from "../layout/FootBar";
    import {getToken} from '../../utils/auth';
    import agreementInfo from "../register/agreementInfo";
    export default {
        name: "aboutInfo",
        components: {FootBar, TopBar,agreementInfo},
        data(){
            return{
                intellectuaAboutMenuActive:"1",
                showContract:true,
                showAgreement:true,
                showStatement:true,
                showPrivacy:true
            }
        },
        created() {
            this.intellectuaAboutMenuActive = this.$route.query.typeId;
        },
        methods:{
            intellectualAboutMenuSelect(index){
                this.intellectuaAboutMenuActive = index;
            },
            showBox(showParam){
                if(showParam == 'showContract'){
                    this.showContract = this.showContract?false:true;
                }else if(showParam == 'showAgreement'){
                    this.showAgreement = this.showAgreement?false:true;
                }else if(showParam == "showStatement"){
                    this.showStatement = this.showStatement?false:true;
                }else if(showParam == "showPrivacy"){
                    this.showPrivacy = this.showPrivacy?false:true;
                }
            },
            clickReport(){
                if(getToken()){
                    this.$router.push({path:"/about/addReport"});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }

            }
        }
    }
</script>

<style scoped>
    @import "../../styles/about.css";
    /deep/ .el-divider--horizontal{background: linear-gradient(90deg,rgba(5,152,203,1) 0%,rgba(216,81,133,1) 99%);height: 3px;margin: 0;}
    /deep/ .el-divider--horizontal.lowLine{height: 1px;}
    /deep/ .el-button:focus,/deep/ .el-button:hover{color: #fff;}
    /deep/ .xieyBox h3{display: none;}
    /deep/ .xieyBox p{font-weight: normal!important;}
</style>