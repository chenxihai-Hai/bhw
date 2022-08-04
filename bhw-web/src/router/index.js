import Vue from "vue";
import VueRouter from "vue-router";
import Layout from "../views/layout/Layout.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        component: Layout,
        redirect: '/home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () =>
                    import("../views/Home.vue")
            },
            {
                path: '/listedTransaction',
                name: 'listedTransaction',
                component: () =>
                    import("../views/listedTransaction/listedTransaction.vue")
            },
            {
                path: '/listedContent',
                name: 'listedContent',
                component: () =>
                    import("../views/listedTransaction/listedContent.vue")
            },
            {
                path: '/listedTransaction/listedMore',
                name: 'listedMore',
                component: () =>
                    import("../views/listedTransaction/listedMore.vue")
            },
            {
                path: '/listedTransaction/listedNotice',
                name: 'listedNotice',
                component: () =>
                    import("../views/listedTransaction/listedNotice.vue")
            },
            {
                path: '/listedTransaction/listedNoticeInfo',
                name: 'listedNoticeInfo',
                component: () =>
                    import("../views/listedTransaction/listedNoticeInfo.vue")
            },
            {
                path: '/kjsc',
                name: 'kjsc',
                component: () =>
                    import("../views/kjsc/kjscIndex.vue")
            },
            {
                path: '/kjsc/detail',
                name: 'ksjcDetail',
                component: () =>
                    import("../views/kjsc/kjscDetail.vue")
            },
            {
                path: '/kjsc/requireDetail',
                name: 'requireDetail',
                component: () =>
                    import("../views/kjsc/requireDetail.vue")
            },
            {
                path: '/kjsc/techDetail',
                name: 'techDetail',
                component: () =>
                    import("../views/kjsc/techDetail.vue")
            },
            {
                path: '/orderDetail',
                name: 'orderDetail',
                component: () =>
                    import("../views/orderPay/orderDetail.vue")
            },
            {
                path: '/orderPay',
                name: 'orderPay',
                component: () =>
                    import("../views/orderPay/orderPay.vue")
            },
            {
                path: "/gx",
                component: () =>
                    import("../views/gx/gxIndex.vue")
            },
            {
                path: '/kjsc/all',
                name: 'allProduct',
                component: () =>
                    import("../views/kjsc/allProduct.vue")
            },
            {
                path: '/newsList',
                name: 'news',
                component: () =>
                    import("../views/news/news.vue")
            },
            {
                path: '/newsContent',
                name: 'news-content',
                component: () =>
                    import("../views/news/newsContent.vue")
            },
            {
                path: "/gxIndex",
                name: 'gxIndex',
                component: () =>
                    import("../views/gx/gxIndex.vue")
            }, {
                path: "/middleman",
                name: 'middlemanIndex',
                component: () =>
                    import("../views/middleman/middlemanIndex.vue")
            }, {
                path: "/middlemanDetail",
                name: 'middlemanDetail',
                component: () =>
                    import("../views/middleman/middlemanDetail.vue")
            }, {
                path: "/serviceIndex",
                name: 'serviceIndex',
                component: () =>
                    import("../views/addService/serviceIndex.vue")
            },
            {
                path: "/guide",
                name: 'guideIndex',
                component: () =>
                    import("../views/guide/guideIndex.vue")
            },
            {
                path: "/return_url",
                name: 'return_url',
                component: () =>
                    import("../views/orderPay/return_url.vue")
            },
            {
                path: "/wt_return_url",
                name: 'wt_return_url',
                component: () =>
                    import("../views/orderPay/wt_return_url.vue")
            },
            {
                path: "/wikipedia",
                name: 'wikipediaIndex',
                component: () =>
                    import("../views/AIWikipedia/wikipediaIndex.vue")
            }
        ]
    },
    {
        path: "/userCenter",
        /*children:[
            {
                path: '/orderDetail',
                name: 'orderDetail',
                component: () =>
                    import("../views/user/order/orderDetail.vue")
            }
        ],*/
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/userCenter/orderDetail",
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/userCenter/payOrderDetail",
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/userCenter/txOrderDetail",
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/userCenter/tbOrderDetail",
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/userCenter/evaluateOrder",
        component: () =>
            import("../views/user/userIndex.vue")
    },
    {
        path: "/viplogin",
        component: () =>
            import("../views/register/vipLogin.vue")
    }, {
        path: "/forgetPassword",
        component: () =>
            import("../views/register/forgetPassword.vue")
    }, {
        path: "/register",
        component: () =>
            import("../views/register/register.vue")
    }, {
        path: "/agreementInfo",
        component: () =>
            import("../views/register/agreementInfo.vue")
    }, {
        path: "/about",
        component: () =>
            import("../views/About.vue")
    }, {
        path: "/about/detail",
        component: () =>
            import("../views/aboutUs/aboutInfo.vue")
    }, {
        path: "/about/addReport",
        component: () =>
            import("../views/aboutUs/addReport.vue")
    }, {
        path: "/gx/detail",
        component: () =>
            import("../views/gx/schoolDetail.vue")
    }, {
        path: "/gx/result",
        component: () =>
            import("../views/gx/resultDetail.vue")
    }, {
        path: "/propertyIndex",
        component: () =>
            import("../views/user/property/propertyIndex")
    }, {
        path: "/LegalServiceIndex",
        component: () =>
            import("../views/user/legalService/LegalServiceIndex")
    }

];

const router = new VueRouter({
    mode: "history",
    // mode: "hash",
    // base: '/bhw/',
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
});

export default router;
