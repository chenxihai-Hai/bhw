(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-65f24921"],{"69e0":function(t,e,n){"use strict";n.d(e,"a",(function(){return a})),n.d(e,"d",(function(){return r})),n.d(e,"e",(function(){return l})),n.d(e,"c",(function(){return s})),n.d(e,"b",(function(){return o})),n.d(e,"f",(function(){return c}));var i=n("dad6");function a(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/backQryListedList",params:t,method:"get"})}function r(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/saveListedTran",params:t,method:"post"})}function l(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/upListedTran",params:t,method:"post"})}function s(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/saveListedNot",params:t,method:"post"})}function o(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/listedAdvisoryList",params:t,method:"get"})}function c(t){return Object(i["a"])({url:"/api/adminApi/listedTranstion/uplistedAdvisory",params:t,method:"post"})}},b932:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"listWarp",staticStyle:{padding:"22px 16px 0"}},[n("div",{staticClass:"bgColorF",staticStyle:{padding:"0 25px"}},[n("h3",[t._v("挂牌交易咨询信息检索")]),n("el-form",{staticClass:"demo-form-inline",staticStyle:{margin:"33px 40px"},attrs:{inline:!0,model:t.formInline}},[n("el-form-item",{attrs:{label:"编号"}},[n("el-input",{attrs:{placeholder:"编号"},model:{value:t.formInline.listedNumber,callback:function(e){t.$set(t.formInline,"listedNumber",e)},expression:"formInline.listedNumber"}})],1),n("el-form-item",{attrs:{label:"委托方"}},[n("el-input",{attrs:{placeholder:"委托方"},model:{value:t.formInline.client,callback:function(e){t.$set(t.formInline,"client",e)},expression:"formInline.client"}})],1),n("el-form-item",{attrs:{label:"摘牌人"}},[n("el-input",{attrs:{placeholder:"摘牌人"},model:{value:t.formInline.notDelister,callback:function(e){t.$set(t.formInline,"notDelister",e)},expression:"formInline.notDelister"}})],1),n("el-form-item",[n("el-button",{staticClass:"btnColorOrg btnQueryList",on:{click:t.backQryListedList}},[t._v("查询")])],1)],1),n("el-row",{staticStyle:{"margin-top":"18px"}},[n("el-col",[n("el-table",{staticClass:"allTable",attrs:{data:t.listedList}},[n("el-table-column",{attrs:{label:"ID",type:"index",align:"center"}}),n("el-table-column",{attrs:{prop:"name",label:"咨询人","show-overflow-tooltip":!0}}),n("el-table-column",{attrs:{prop:"phone",label:"联系方式"}}),n("el-table-column",{attrs:{prop:"remark",label:"需求信息"}}),n("el-table-column",{attrs:{label:"操作",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[void 0==e.row.opreateId||null==e.row.opreateId?n("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(n){return t.upAdvSt(e.row.id)}}},[t._v("联系他/她")]):n("el-button",{attrs:{size:"mini",type:"text"}},[t._v("已联系")])]}}])})],1)],1)],1),n("el-row",{staticStyle:{"text-align":"center",margin:"30px auto 50px"}},[n("el-col",{staticClass:"pageWrap"},[n("el-pagination",{staticClass:"pageBox",attrs:{background:"",layout:"prev, pager, next","page-size":t.formInline.pageSize,"current-page":t.formInline.curPage,total:t.pageTotal},on:{"current-change":t.currentChange}})],1)],1)],1)])},a=[],r=n("69e0"),l={name:"listedAdvList",data:function(){return{pageTotal:0,formInline:{listedNumber:null,client:null,notDelister:null,pageSize:5,curPage:1},listedList:[]}},mounted:function(){this.backQryListedList()},methods:{upAdvSt:function(t){var e=this;this.$confirm("是否确认联系客户?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",cancelButtonClass:"btnColorOrg",confirmButtonClass:"btnColorBlue",type:"warning"}).then((function(){var n={opreateName:e.$store.getters.userName,opreateId:e.$store.getters.backUserId,id:t};Object(r["f"])(n).then((function(t){e.$message({type:"success",message:"操作成功!"}),e.backQryListedList()}))}))},currentChange:function(t){this.formInline.curPage=t,this.backQryListedList()},backQryListedList:function(){var t=this;Object(r["b"])(this.formInline).then((function(e){t.listedList=e.data,t.pageTotal=e.page.counts}))}}},s=l,o=n("2877"),c=Object(o["a"])(s,i,a,!1,null,"4350a0f3",null);e["default"]=c.exports}}]);
//# sourceMappingURL=chunk-65f24921.f5efc263.js.map