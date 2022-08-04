import Vue from "vue";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import ECharts from 'echarts';
import 'echarts/lib/chart/bar';
import insertNews from './views/newsmgr/insertNews';
import addSupply from './views/infoManage/techologySupply/addSupply';
import LegalUploading from './views/infoManage/legalServer/LegalUploading';
import technicalUploading from './views/infoManage/techology/technicalUploading';
import intellectual from './views/infoManage/Intellectual/intellectual';
import fiscalUploading from './views/infoManage/fiscalTaxInfo/fiscalUploading';
import addBorker from './views/infoManage/borker/addBorker';
import App from "./App.vue";
import router from "./router";
import store from "./store";
import {getToken} from './util/auth';

Vue.component('insertNews', insertNews);
Vue.component('addSupply', addSupply);
Vue.component('LegalUploading', LegalUploading);
Vue.component('technicalUploading', technicalUploading);
Vue.component('intellectual', intellectual);
Vue.component('fiscalUploading', fiscalUploading);
Vue.component('addBorker', addBorker);
Vue.use(ElementUI);
Vue.prototype.$echarts = ECharts;
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
const whiteList = ['/login']; // 不重定向白名单
router.beforeEach((to, from, next) => {
    if (getToken()) { // 判断是否有token
        if (to.path === '/login') {
            next();
        } else {
            if (store.getters.permissionMenus === undefined || store.getters.permissionMenus.length<1) { // 判断当前用户是否已拉取完user_info信息
                store.dispatch('getBackMenu', {}).then(response => {
                    next();
                }).catch(() => {
                    next('/login'); // 否则全部重定向到登录页
                });
            } else {
                // Vue.set(store.state,'user',JSON.parse(getToken()));
                next();
            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next()
        } else {
            next('/login'); // 否则全部重定向到登录页
        }
    }
});



Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
