(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3be0baf6"],{"45fc":function(t,e,a){"use strict";var n=a("23e7"),l=a("b727").some,r=a("b301");n({target:"Array",proto:!0,forced:r("some")},{some:function(t){return l(this,t,arguments.length>1?arguments[1]:void 0)}})},a3a1:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",[a("el-header",[a("h1",[t._v("修改信息")])]),a("el-main",[a("el-form",{staticClass:"demo-ruleForm",staticStyle:{width:"700px",margin:"0 auto"},attrs:{model:t.updateData,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"标题"}},[a("el-input",{model:{value:t.updateData.title,callback:function(e){t.$set(t.updateData,"title",e)},expression:"updateData.title"}})],1),a("el-form-item",{attrs:{label:"类型"}},[a("el-input",{model:{value:t.updateData.type,callback:function(e){t.$set(t.updateData,"type",e)},expression:"updateData.type"}})],1),a("el-form-item",{attrs:{label:"图片"}},[a("el-upload",{attrs:{action:"https://jsonplaceholder.typicode.com/posts/","list-type":"picture-card",limit:1,"file-list":t.fileList}},[a("i",{staticClass:"el-icon-plus"})])],1),a("el-form-item",{attrs:{label:"链接"}},[a("el-input",{model:{value:t.updateData.href,callback:function(e){t.$set(t.updateData,"href",e)},expression:"updateData.href"}})],1),a("el-form-item",{attrs:{label:"内容"}},[a("el-input",{attrs:{type:"textarea"},model:{value:t.updateData.content,callback:function(e){t.$set(t.updateData,"content",e)},expression:"updateData.content"}})],1),a("el-form-item",{attrs:{size:"large"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submitForm("updateData")}}},[t._v("更新")]),a("el-button",{on:{click:function(e){return t.resetForm()}}},[t._v("返回")])],1)],1)],1)],1)},l=[],r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("section",{staticClass:"el-container",class:{"is-vertical":t.isVertical}},[t._t("default")],2)},i=[],o=(a("45fc"),{name:"ElContainer",componentName:"ElContainer",props:{direction:String},computed:{isVertical:function(){return"vertical"===this.direction||"horizontal"!==this.direction&&(!(!this.$slots||!this.$slots.default)&&this.$slots.default.some((function(t){var e=t.componentOptions&&t.componentOptions.tag;return"el-header"===e||"el-footer"===e})))}}}),s=o,u=a("2877"),c=Object(u["a"])(s,r,i,!1,null,null,null),p=c.exports,m=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("main",{staticClass:"el-main"},[t._t("default")],2)},d=[],f={name:"ElMain",componentName:"ElMain"},h=f,b=Object(u["a"])(h,m,d,!1,null,null,null),$=b.exports,v=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("header",{staticClass:"el-header",style:{height:t.height}},[t._t("default")],2)},D=[],y={name:"ElHeader",componentName:"ElHeader",props:{height:{type:String,default:"60px"}}},_=y,E=Object(u["a"])(_,v,D,!1,null,null,null),x=E.exports,g={components:{ElHeader:x,ElMain:$,ElContainer:p},name:"update-news",data:function(){return{updateData:{title:this.$route.params.title,type:this.$route.params.type,href:this.$route.params.href,content:this.$route.params.content},fileList:[{name:this.$route.params.pictureName,url:this.$route.params.picturePath}]}},methods:{submitForm:function(t){},resetForm:function(){this.$router.push("select-news")}},mounted:function(){}},w=g,k=Object(u["a"])(w,n,l,!1,null,"6082f74f",null);e["default"]=k.exports}}]);
//# sourceMappingURL=chunk-3be0baf6.08a2d4ea.js.map