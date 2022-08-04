import Vue from 'vue';
import VueRouter from 'vue-router';
import Layout from '@/views/layout/Layout';

const _import = require('./_import_production');
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        component: Layout,
        children: [
            {
                path: '/home',
                name: 'home',
                component:
                    _import("Home")
            },
            {
                path: '/about',
                name: 'about',
                component:
                    _import("About")
            },
            {
                path: '/select-news',
                name: 'select-news',
                component: _import("newsmgr/SelectNews")
            },
            {
                path: '/update-news',
                name: 'update-news',
                component: _import("newsmgr/updateNews")
            },
            {
                path: '/insert-news',
                name: 'insert-news',
                component: _import("newsmgr/insertNews")
            },
            {
                path: '/personAudit',
                name: 'personAudit',
                component: _import("userAdmin/personAudit")
            },
            {
                path: '/Person',
                name: 'Person',
                component: _import("userAdmin/Person")
            },
            {
                path: '/companyAudit',
                name: 'companyAudit',
                component: _import("userAdmin/companyAudit")
            },
            {
                path: '/salesQuantity',
                name: 'salesQuantity',
                component: _import("userAdmin/salesQuantity")
            },
            {
                path: '/dataStatistics',
                name: 'dataStatistics',
                component: _import("userAdmin/dataStatistics")
            },
            {
                path: '/VIPadmin',
                name: 'VIPadmin',
                component: _import("userAdmin/VIPadmin")
            },
            {
                path: '/detail',
                name: 'detial',
                component: _import("userAdmin/userDetail")
            },
            {
                path: '/backUserList',
                name: 'backUserList',
                component: _import("sysManage/backUserManage/backUserList")
            },
            {
                path: '/backRoleList',
                name: 'backRoleList',
                component: _import("sysManage/backUserManage/backRoleList")
            },
            {
                path: '/orderList',
                name: 'orderList',
                component: _import("financing/order/orderList")
            },
            {
                path: '/refundRecordList',
                name: 'refundRecordList',
                component: _import("financing/refund/refundRecordList")
            },
            {
                path: '/serviceOrderList',
                name: '/serviceOrderList',
                component: _import("financing/addService/serviceOrderList")
            },
            {
                path: '/fundCloseOrderList',
                name: 'fundCloseOrderList',
                component: _import("financing/fund/fundCloseOrderList")
            },
            {
                path: '/withdrawList',
                name: '/withdrawList',
                component: _import("financing/withdraw/withdrawList")
            },
            {
                path: '/fundDealList',
                name: 'fundDealList',
                component: _import("financing/fund/fundDealList")
            },
            {
                path: '/orderStatistics',
                name: 'orderStatistics',
                component: _import("financing/statistics/orderStatistics")
            },
            {
                path: '/orderEarnings',
                name: 'orderEarnings',
                component: _import("financing/statistics/orderEarnings")
            },
            {
                path: '/company',
                name: '/userAdmin/company',
                component: _import("userAdmin/company")
            },
            {
                path: 'report',
                name: 'report',
                component: _import("sysManage/report/reportList")
            },
            {
                path: '/SelectNews',
                name: 'newsmgr/SelectNews',
                component: _import("newsmgr/SelectNews")
            },
            {
                path: '/techologyList',
                name: 'techologyList',
                component: _import("infoManage/techology/techologyList")
            },
            {
                path: '/techologyAudit',
                name: 'techologyAudit',
                component: _import("infoManage/techology/techologyAudit")
            },
            {
                path: '/techologySupplyList',
                name: 'techologySupplyList',
                component: _import("infoManage/techologySupply/techologySupplyList")

            },
            {
                path: '/techologySupplyAudit',
                name: 'techologySupplyAudit',
                component: _import("infoManage/techologySupply/techologySupplyAudit")

            },
            {
                path: '/legalServerList',
                name: 'legalServerList',
                component: _import("infoManage/legalServer/legalServerList")

            },
            {
                path: '/legalServerAudit',
                name: 'legalServerAudit',
                component: _import("infoManage/legalServer/legalServerAudit")

            },
            {
                path: '/IntellectualList',
                name: 'IntellectualList',
                component: _import("infoManage/Intellectual/IntellectualList")
            },
            {
                path: '/IntellectualAudit',
                name: 'IntellectualAudit',
                component: _import("infoManage/Intellectual/IntellectualAudit")
            },
            {
                path:'/fiscalTaxInfoList',
                name:'/fiscalTaxInfoList',
                component: _import("infoManage/fiscalTaxInfo/fiscalTaxInfoList")
            },
            {
                path:'/fiscalTaxInfoAudit',
                name:'/fiscalTaxInfoAudit',
                component: _import("infoManage/fiscalTaxInfo/fiscalTaxInfoAudit")
            },
            {
                path:'/borkerListP',
                name:'/borkerListP',
                component: _import("infoManage/borker/borkerList-p")
            },
            {
                path:'/borkerListM',
                name:'/borkerListM',
                component: _import("infoManage/borker/borkerList-m")
            },
            {
                path:'/listedTranstionList',
                name:'/listedTranstionList',
                component: _import("infoManage/listedTranstion/listedTranstionList")
            },
            {
                path:'/listedAdvList',
                name:'/listedAdvList',
                component: _import("infoManage/listedTranstion/listedAdvList")
            },
            {
                path: '/advertList',
                name: '/advertList',
                component: _import("sysManage/advertManage/advertList")
            },
            {
                path: '/schoolList',
                name: '/schoolList',
                component: _import("sysManage/resources/schoolList")
            },
            {
                path: '/resourceList',
                name: '/resourceList',
                component: _import("sysManage/resources/resourceList")
            },
            {
                path: '/create',
                name: '/createSchool',
                component: _import("sysManage/resources/createSchool")
            },
            {
                path: '/resultList',
                name: '/resultList',
                component: _import("sysManage/resources/resultList")
            },
            {
                path: '/serviceList',
                name: '/serviceList',
                component: _import("sysManage/addService/serviceList")
            },
            {
                path: '/borkerTunoverList',
                name: '/borkerTunoverList',
                component: _import("userAdmin/borkerTurnoverList")
            }
        ]
    }, {
        path: '/register',
        name: 'register',
        component:
            _import("register/register")
    },
    {
        path: '/login',
        name: 'login',
        component:
            _import("register/login")
    }
];

const router = new VueRouter({
    mode: "history",
     base: '/admin/',
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
});
/*router.onError((error) => {
    const pattern = /Loading chunk (\d)+ failed/g;
    const isChunkLoadFailed = error.message.match(pattern);
    if(isChunkLoadFailed){
        // 用路由的replace方法，并没有相当于F5刷新页面，失败的js文件并没有从新请求，会导致一直尝试replace页面导致死循环，而用 location.reload 方法，相当于触发F5刷新页面，虽然用户体验上来说会有刷新加载察觉，但不会导致页面卡死及死循环，从而曲线救国解决该问题
        location.reload();
        // const targetPath = $router.history.pending.fullPath;
        // $router.replace(targetPath);
    }

});*/
export default router;
