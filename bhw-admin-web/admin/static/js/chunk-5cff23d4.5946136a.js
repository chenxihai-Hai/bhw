(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5cff23d4"],{a15b:function(t,e,a){"use strict";var l=a("23e7"),n=a("44ad"),i=a("fc6a"),r=a("b301"),o=[].join,s=n!=Object,c=r("join",",");l({target:"Array",proto:!0,forced:s||c},{join:function(t){return o.call(i(this),void 0===t?",":t)}})},a1cc:function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"listWarp",staticStyle:{padding:"22px 16px 0"}},[a("div",{staticClass:"bgColorF",staticStyle:{padding:"0 25px"}},[a("h3",[t._v("检索参数")]),a("el-form",{staticClass:"demo-form-inline",staticStyle:{margin:"33px 40px"},attrs:{inline:!0,model:t.formInline}},[a("el-form-item",{attrs:{label:"供需选择",prop:"resource"}},[a("el-radio-group",{model:{value:t.formInline.checkType,callback:function(e){t.$set(t.formInline,"checkType",e)},expression:"formInline.checkType"}},[a("el-radio",{attrs:{label:"1"}},[t._v("供（我要卖）")]),a("el-radio",{attrs:{label:"2"}},[t._v("需（我要买）")])],1)],1),a("el-form-item",{attrs:{label:"发布人：",prop:"name"}},[a("el-input",{attrs:{placeholder:"选填"},model:{value:t.formInline.publisher,callback:function(e){t.$set(t.formInline,"publisher",e)},expression:"formInline.publisher"}})],1),a("el-form-item",{attrs:{label:"业务类型",prop:"resource"}},[a("el-radio-group",{model:{value:t.formInline.bussinessType,callback:function(e){t.$set(t.formInline,"bussinessType",e)},expression:"formInline.bussinessType"}},[a("el-radio",{attrs:{label:"1"}},[t._v("技术合同登记")]),a("el-radio",{attrs:{label:"2"}},[t._v("科技成果认定登记")])],1)],1),a("el-form-item",[a("el-button",{staticClass:"btnColorOrg btnQueryList",on:{click:function(e){return t.loadOrderData()}}},[t._v("查询")])],1)],1),a("el-row",{staticStyle:{"margin-bottom":"32px"}},[a("el-col",[a("el-button",{staticClass:"btnColorOrg btnQueryList",on:{click:function(e){return t.handleDel(void 0)}}},[t._v("一键下架")]),a("el-button",{staticClass:"btnColorBlue btnQueryList",attrs:{type:"primary"},on:{click:t.addSupplyBtn}},[t._v("添加")])],1)],1),a("el-table",{staticClass:"allTable",staticStyle:{width:"100%"},attrs:{data:t.tableData,border:""},on:{"selection-change":t.handleSelectionChange}},[a("el-table-column",{attrs:{fixed:"",type:"selection",width:"55"}}),a("el-table-column",{attrs:{fixed:"",label:"编码",type:"index",width:"50"}}),a("el-table-column",{attrs:{fixed:"",prop:"serverName",label:"服务标题","show-overflow-tooltip":!0}}),a("el-table-column",{attrs:{prop:"bussinessName",label:"业务类型"}}),a("el-table-column",{attrs:{prop:"checkTypeName",label:"供/需"}}),a("el-table-column",{attrs:{prop:"originalPrice",label:"原价"}}),a("el-table-column",{attrs:{prop:"price",label:"售价"}}),a("el-table-column",{attrs:{prop:"budget",label:"预算"}}),a("el-table-column",{attrs:{prop:"publisher",label:"发布人/企业"}}),a("el-table-column",{attrs:{prop:"contactName",label:"联系人"}}),a("el-table-column",{attrs:{prop:"contactPhone",label:"联系方式"}}),a("el-table-column",{attrs:{prop:"contactUnit",label:"联系单位"}}),a("el-table-column",{attrs:{prop:"keyWord",label:"关键字"}}),a("el-table-column",{attrs:{prop:"remark",label:"详情介绍"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{staticStyle:{color:"#024ECC",cursor:"pointer"},on:{click:function(a){return t.showCt(e.row.remark)}}},[t._v("预览")])]}}])}),a("el-table-column",{attrs:{prop:"address",label:"所在地"}}),a("el-table-column",{attrs:{label:"状态",width:"120",fixed:"right"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-switch",{attrs:{value:e.row.status+"","inactive-value":"3","active-value":"2","active-color":"#024ECC","inactive-color":"#DCDCDC"},on:{change:function(a){return t.switchCk(e.row.status,e.row)}}}),2==e.row.status?[t._v(" 正常 ")]:t._e(),3==e.row.status?[t._v(" 已下架 ")]:t._e()]}}])}),a("el-table-column",{attrs:{label:"操作",fixed:"right",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{staticClass:"btnColorOrg btnUser",staticStyle:{width:"58px"},attrs:{size:"mini",type:"primary"},on:{click:function(a){return t.handleUp(e.row)}}},[t._v("编辑")]),2==e.row.status?a("el-button",{staticClass:"btnColorBlue btnUser",attrs:{size:"mini",type:"primary"},on:{click:function(a){return t.handleDel(e.row)}}},[t._v("下架")]):t._e(),3==e.row.status?a("el-button",{staticClass:"btnColorBlue btnUser",attrs:{size:"mini",type:"primary"},on:{click:function(a){return t.upHandleDel(e.row)}}},[t._v("上架")]):t._e(),a("br"),a("el-button",{staticClass:"btnColorOrg btnUser",staticStyle:{width:"58px","margin-top":"5px"},attrs:{size:"mini"},on:{click:function(a){return t.delInfo(e.row)}}},[t._v("删除")])]}}])})],1),a("div",{staticClass:"pageWrap",staticStyle:{"text-align":"center"}},[a("el-pagination",{staticClass:"pageBox",attrs:{background:"",layout:"prev, pager, next","page-size":t.formInline.pageSize,"current-page":t.formInline.curPage,total:t.pageTotal},on:{"current-change":t.currentChange}})],1)],1),a("el-dialog",{staticClass:"serviceDialog",attrs:{title:"内容预览",visible:t.showHtmlCt,width:"60%"},on:{"update:visible":function(e){t.showHtmlCt=e}}},[a("div",{domProps:{innerHTML:t._s(t.shtml)}})]),t.addSupplyFlag?a("addSupply",{ref:"addSupplyRef"}):t._e()],1)},n=[],i=(a("4160"),a("a15b"),a("159b"),a("95c8")),r=a("861c"),o=a("7984"),s=a("a30d"),c={name:"techologySupplyList",comments:{addSupply:s["default"]},data:function(){return{addSupplyFlag:!1,showHtmlCt:!1,addDialogVisible:!1,formInline:{checkType:"",bussinessType:"",publisher:null,pageSize:3,curPage:1},pageTotal:0,leixinselect:[{value:"1",label:"类型"}],tableData:[{id:"",serverName:"",checkTypeName:"",originalPrice:"",price:"",budget:"",businessName:"",publisher:"",contactName:"",contactPhone:"",contactUnit:"",remark:"",keyWord:"",address:""}],shtml:"",idList:[],productSelect:[]}},created:function(){var t=this;Object(o["a"])().then((function(e){t.leixinselect=e.data})),this.loadOrderData()},methods:{delInfo:function(t){var e=this;this.$confirm("此操作将永久删除该数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(r["a"])({ids:t.id}).then((function(t){e.loadOrderData(),e.$message({type:"success",message:"删除成功!"})}))})).catch((function(){}))},addSupplyBtn:function(){this.addSupplyFlag=!0,this.$nextTick((function(){this.$refs.addSupplyRef.supplyDialogVisible=!0}))},handleSelectionChange:function(t){this.productSelect=t},showCt:function(t){this.shtml=t,this.showHtmlCt=!0},currentChange:function(t){this.formInline.curPage=t,this.loadOrderData()},loadOrderData:function(){var t=this;Object(r["b"])(this.formInline).then((function(e){t.tableData=e.data,t.pageTotal=e.page.counts}))},handleUp:function(t){var e=this;Object(r["c"])({id:t.id}).then((function(t){e.addSupplyFlag=!0,e.$nextTick((function(){this.$refs.addSupplyRef.productInfo=t.data,this.$refs.addSupplyRef.cityId=null,this.$refs.addSupplyRef.supplyDialogVisible=!0}))}))},upHandleDel:function(t){var e=this;Object(r["g"])({id:t.id,status:2}).then((function(t){e.$message({type:"success",message:"操作成功!"}),e.loadOrderData()}))},switchCk:function(t,e){2==t?this.handleDel(e):this.upHandleDel(e)},handleDel:function(t){var e=this;void 0==t&&this.productSelect.length<1?this.$message.warning("请勾选需要操作的数据!"):this.$confirm("此操作将下架选择信息, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",cancelButtonClass:"btnColorOrg",confirmButtonClass:"btnColorBlue",type:"warning"}).then((function(){t?e.idList.push(t.id):e.productSelect.forEach((function(t){e.idList.push(t.id)})),Object(r["f"])({idList:e.idList.join(",")}).then((function(a){e.idList=[],e.$message({type:"success",message:"操作成功!"});var l={operateName:e.$store.getters.userName,operateId:e.$store.getters.backUserId,content:void 0!=t?"对技术合同下的产品("+t.serverName+")进行了下架":"对技术合同下的产品进行了批量下架"};Object(i["e"])(l).then(),e.loadOrderData()}))})).catch((function(){}))}}},u=c,p=a("2877"),d=Object(p["a"])(u,l,n,!1,null,"12a310bc",null);e["default"]=d.exports}}]);
//# sourceMappingURL=chunk-5cff23d4.5946136a.js.map