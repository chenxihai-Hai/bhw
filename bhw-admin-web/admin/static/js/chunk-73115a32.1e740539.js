(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-73115a32"],{a15b:function(e,t,n){"use strict";var r=n("23e7"),a=n("44ad"),o=n("fc6a"),l=n("b301"),s=[].join,i=a!=Object,u=l("join",",");r({target:"Array",proto:!0,forced:i||u},{join:function(e){return s.call(o(this),void 0===e?",":e)}})},b4e6:function(e,t,n){"use strict";n.d(t,"d",(function(){return a})),n.d(t,"b",(function(){return o})),n.d(t,"a",(function(){return l})),n.d(t,"c",(function(){return s})),n.d(t,"e",(function(){return i})),n.d(t,"g",(function(){return u})),n.d(t,"h",(function(){return c})),n.d(t,"f",(function(){return p})),n.d(t,"i",(function(){return d}));var r=n("dad6");function a(e){return Object(r["a"])({url:"/api/userApi/userManage/getUserList",params:e,method:"get"})}function o(e){return Object(r["a"])({url:"/api/userApi/userManage/editUser",params:e,method:"post"})}function l(e){return Object(r["a"])({url:"/api/userApi/userManage/deleteUserList",params:e,method:"post"})}function s(e){return Object(r["a"])({url:"/api/userApi/userManage/getAuditUserList",params:e,method:"get"})}function i(e){return Object(r["a"])({url:"/api/userApi/userManage/infoAudit",params:e,method:"post"})}function u(e){return Object(r["a"])({url:"/api/userApi/bhwUser/qryUser",params:e,method:"get"})}function c(e){return Object(r["a"])({url:"/api/userApi/bhwUser/qryUserMemberList",params:e,method:"get"})}function p(e){return Object(r["a"])({url:"/api/userApi/bhwUser/pushUserMemberMsg",params:e,method:"post"})}function d(e){return Object(r["a"])({url:"/api/userApi/userSysMsg/saveMsg",params:e,method:"post"})}},b7e2:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"listWarp",staticStyle:{padding:"22px 16px 0"}},[n("div",{staticClass:"bgColorF",staticStyle:{padding:"0 25px"}},[n("h3",[e._v("检索参数")]),n("el-form",{staticClass:"demo-form-inline",staticStyle:{margin:"33px 40px"},attrs:{inline:!0,model:e.formInline}},[n("el-form-item",{attrs:{label:"供需选择",prop:"resource"}},[n("el-radio-group",{model:{value:e.formInline.needType,callback:function(t){e.$set(e.formInline,"needType",t)},expression:"formInline.needType"}},[n("el-radio",{attrs:{label:"1"}},[e._v("供（我要卖）")]),n("el-radio",{attrs:{label:"2"}},[e._v("需（我要买）")])],1)],1),n("el-form-item",{attrs:{label:"所属类别：",prop:"name"}},[n("el-select",{attrs:{placeholder:"请选择 "},on:{change:e.qryIntellectualType},model:{value:e.formInline.projectType,callback:function(t){e.$set(e.formInline,"projectType",t)},expression:"formInline.projectType"}},e._l(e.projectType,(function(e){return n("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1)],1),n("el-form-item",{attrs:{label:"行业类别：",prop:"name"}},[n("el-select",{attrs:{placeholder:"请选择 "},model:{value:e.formInline.industryId,callback:function(t){e.$set(e.formInline,"industryId",t)},expression:"formInline.industryId"}},e._l(e.industryType,(function(e){return n("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1)],1),n("el-form-item",{attrs:{label:"专利号：",prop:"name"}},[n("el-input",{attrs:{placeholder:"选填"},model:{value:e.formInline.codeNumber,callback:function(t){e.$set(e.formInline,"codeNumber",t)},expression:"formInline.codeNumber"}})],1),n("el-form-item",[n("el-button",{staticClass:"btnColorOrg btnQueryList",on:{click:function(t){return e.loadOrderData()}}},[e._v("查询")])],1),n("hr"),n("el-form-item",[n("el-button",{staticClass:"btnColorOrg btnQueryList",on:{click:function(t){return e.batchUpAuditStatus(void 0,1)}}},[e._v("一键通过")])],1),n("el-form-item",[n("el-button",{staticClass:"btnColorBlue btnQueryList",attrs:{type:"primary"},on:{click:function(t){return e.batchUpAuditStatus(void 0,2)}}},[e._v("一键驳回")])],1)],1),n("el-table",{staticClass:"allTable",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:""},on:{"selection-change":e.handleSelectionChange}},[n("el-table-column",{attrs:{fixed:"",type:"selection",width:"55"}}),n("el-table-column",{attrs:{fixed:"",label:"编码",type:"index",width:"50"}}),n("el-table-column",{attrs:{fixed:"",prop:"projectName",label:"产品名称","show-overflow-tooltip":!0}}),n("el-table-column",{attrs:{label:"供/需"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(1==t.row.needType?"供":"需")+" ")]}}])}),n("el-table-column",{attrs:{prop:"unitPrice",label:"售价"}}),n("el-table-column",{attrs:{label:"所属类别"},scopedSlots:e._u([{key:"default",fn:function(t){return[1==t.row.projectType?[e._v(" 商标 ")]:e._e(),2==t.row.projectType?[e._v(" 专利 ")]:e._e(),3==t.row.projectType?[e._v(" 域名 ")]:e._e()]}}])}),n("el-table-column",{attrs:{prop:"industryName",label:"行业类别"}}),n("el-table-column",{attrs:{prop:"codeNumber",label:"注册号/专利号"}}),n("el-table-column",{attrs:{prop:"intellectualName",label:"交易类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[1==t.row.dealType?[e._v(" 转让 ")]:e._e(),2==t.row.dealType?[e._v(" 许可 ")]:e._e()]}}])}),n("el-table-column",{attrs:{label:"数量/单位"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.projectNumber)+e._s(t.row.projectUnit)+" ")]}}])}),n("el-table-column",{attrs:{prop:"userName",label:"联系人"}}),n("el-table-column",{attrs:{prop:"contactPhone",label:"联系方式"}}),n("el-table-column",{attrs:{label:"图片/视频"},scopedSlots:e._u([{key:"default",fn:function(e){return[n("el-image",{staticStyle:{width:"50px",height:"50px"},attrs:{src:e.row.homeImg,"preview-src-list":[e.row.homeImg]}})]}}])}),n("el-table-column",{attrs:{prop:"projectTag",label:"关键字"}}),n("el-table-column",{attrs:{label:"所在地"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.provinceName)+e._s(t.row.cityName)+" ")]}}])}),n("el-table-column",{attrs:{label:"置顶-精品"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(0==t.row.boutique?"否":"是")+" ")]}}])}),n("el-table-column",{attrs:{label:"详情描述"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",{staticStyle:{color:"#024ECC",cursor:"pointer"},on:{click:function(n){return e.showCt(t.row.details)}}},[e._v("预览")])]}}])}),n("el-table-column",{attrs:{prop:"createTime",label:"发布时间"}}),n("el-table-column",{attrs:{fixed:"right",label:"审核状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[0==t.row.auditStatus?[e._v(" 待审核 ")]:e._e(),1==t.row.auditStatus?[e._v(" 通过 ")]:e._e(),2==t.row.auditStatus?[e._v(" 未通过 ")]:e._e()]}}])}),n("el-table-column",{attrs:{label:"操作",fixed:"right",width:"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{staticClass:"btnColorOrg btnUser",staticStyle:{width:"58px"},attrs:{size:"mini"},on:{click:function(n){return e.batchUpAuditStatus(t.row,1)}}},[e._v("通过")]),n("el-button",{staticClass:"btnColorBlue btnUser",attrs:{size:"mini",type:"primary"},on:{click:function(n){return e.batchUpAuditStatus(t.row,2)}}},[e._v("驳回")])]}}])})],1),n("div",{staticClass:"pageWrap",staticStyle:{"text-align":"center"}},[n("el-pagination",{staticClass:"pageBox",attrs:{background:"",layout:"prev, pager, next","page-size":e.formInline.pageSize,"current-page":e.formInline.curPage,total:e.pageTotal},on:{"current-change":e.currentChange}})],1)],1),n("el-dialog",{attrs:{title:"内容预览",visible:e.showHtmlCt,width:"60%"},on:{"update:visible":function(t){e.showHtmlCt=t}}},[n("div",{domProps:{innerHTML:e._s(e.shtml)}})])],1)},a=[],o=(n("4160"),n("a15b"),n("159b"),n("95c8")),l=n("0570"),s=n("b4e6"),i={name:"IntellectualAudit",data:function(){return{projectType:[{id:"1",name:"商标"},{id:"2",name:"专利"},{id:"3",name:"域名"}],formInline:{projectType:"",industryId:"",needType:null,codeNumber:"",pageSize:3,curPage:1,auditStatus:0},pageTotal:0,options:[{id:"1",name:"测试类别"}],tableData:[],productSelect:[],shtml:"",showHtmlCt:!1,industryType:[]}},created:function(){this.loadListData()},methods:{batchUpAuditStatus:function(e,t){var n=this;if(void 0==e&&this.productSelect.length<1)this.$message.warning("请勾选需要操作的数据!");else{var r=[];e?r.push(e.id):this.productSelect.forEach((function(e){r.push(e.id)})),1==t?Object(l["b"])({pIds:r.join(","),status:t}).then((function(r){if(n.$message({type:"success",message:"操作成功!"}),n.loadListData(),e){var a={userId:e.userId,productId:e.id,content:"尊敬的用户,您您发布的产品:"+e.projectName+"已通过！",msgType:3};Object(s["i"])(a).then((function(e){}))}var l={operateName:n.$store.getters.userName,operateId:n.$store.getters.backUserId,content:void 0==e?"对知识产权下的产品进行了批量审核为"+(1==t?"通过":"驳回"):"对知识产权下的产品("+e.projectName+")进行了审核为"+(1==t?"通过":"驳回")};Object(o["e"])(l).then()})):this.$prompt("请输入驳回原因","提示",{confirmButtonText:"确定",cancelButtonText:"取消",cancelButtonClass:"btnColorOrg",confirmButtonClass:"btnColorBlue"}).then((function(a){var i=a.value;Object(l["b"])({pIds:r.join(","),status:t}).then((function(r){if(n.$message({type:"success",message:"操作成功!"}),n.loadListData(),e){var a={userId:e.userId,productId:e.id,content:"尊敬的用户,您发布的:"+e.projectName+"被驳回。原因如下:"+i+"!",msgType:3};Object(s["i"])(a).then((function(e){}))}var l={operateName:n.$store.getters.userName,operateId:n.$store.getters.backUserId,content:void 0==e?"对知识产权下的产品进行了批量审核为"+(1==t?"通过":"驳回"):"对知识产权下的产品("+e.projectName+")进行了审核为"+(1==t?"通过":"驳回")};Object(o["e"])(l).then()}))})).catch((function(){n.$message({type:"info",message:"取消输入"})}))}},loadListData:function(){var e=this;Object(l["g"])(this.formInline).then((function(t){e.tableData=t.data,e.pageTotal=t.page.counts}))},qryIntellectualType:function(e){var t=this;Object(l["f"])({intellectualType:e}).then((function(e){t.industryType=e.data}))},handleSelectionChange:function(e){this.productSelect=e},showCt:function(e){this.shtml=e,this.showHtmlCt=!0},currentChange:function(e){this.formInline.curPage=e,this.loadListData()}}},u=i,c=n("2877"),p=Object(c["a"])(u,r,a,!1,null,"6c10a5f1",null);t["default"]=p.exports}}]);
//# sourceMappingURL=chunk-73115a32.1e740539.js.map