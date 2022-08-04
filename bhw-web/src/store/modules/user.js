import {userLoginReq} from "../../api/register/register";
import {getUserInfoByToken,getUserDetailByToken} from "../../api/user/user";

import {
    getToken,
    setToken,
    removeToken
} from '../../utils/auth';
const user = {
  state: {
      permissionMenus: [
          {
              children: [
                  {
                      children: [{
                          children:[],
                          href:"",
                          id:30,
                          parentId:2,

                      }],
                      code: "zcjy",
                      href: "",
                      icon: "fa-user",
                      iconClass: "icon-menu",
                      id: 19,
                      parentId: 1,
                      path: "",
                      spread: false,
                      title: "资产交易",
                      type: "dirt",
                      mark: [{id:0,name:'商标',lineM:'|',path:'kjsc',param:""},
                          {id:1,name:'版权',lineM:'|',path:'kjsc',param:""},
                          {id:2,name:'专利',lineM:'|',path:'kjsc',param:""},
                          {id:3,name:'域名',lineM:'|',path:'kjsc',param:""},
                          {id:4,name:'其他',lineM:'',path:'kjsc',param:""}]
                  },
                  {
                      children: [],
                      code: "fwjy",
                      href: "",
                      icon: "fa-user",
                      iconClass: "icon-menu",
                      id: 20,
                      parentId: 1,
                      path: "",
                      spread: false,
                      title: "服务交易",
                      type: "dirt",
                      mark:[{id:0,name:'技术成果认定登记',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:1,name:'技术合同认定',lineM:'',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:2,name:'科技金融',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:3,name:'法律服务',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:4,name:'科技咨询',lineM:'',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:1,name:'财税服务',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:2,name:'高企认定',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:3,name:'规划设计',lineM:'|',path:'kjsc',param:{chengguoActiveName:1}},
                          {id:4,name:'其他',lineM:'',path:'kjsc',param:{chengguoActiveName:1}}]
                  },
                  {
                      children: [],
                      code: "gxfb",
                      href: "",
                      icon: "fa-user",
                      iconClass: "icon-menu",
                      id: 21,
                      parentId: 1,
                      path: "",
                      spread: false,
                      title: "供需发布",
                      type: "dirt",
                      mark:[{id:0,name:'技术供需发布',lineM:'|',path:'userCenter'},
                          {id:1,name:'产品、服务供需发布',lineM:'',path:'userCenter'}]
                  }
              ],
              code: "allPro",
              href: "/allPro",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 1,
              parentId: -1,
              path: "/allPro",
              spread: false,
              title: "全部产品/服务分类",
              type: "dirt"
          },
          {
              children: [],
              code: "home",
              href: "/home",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 10,
              parentId: -1,
              path: "",
              spread: false,
              title: "首页",
              type: "dirt"
          },
          {
              children: [],
              code: "kjsc",
              href: "/kjsc",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 2,
              parentId: -1,
              path: "",
              spread: false,
              title: "科技市场",
              type: "dirt"
          },
          {
              children: [],
              code: "spMarket",
              href: "spMarket",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 3,
              parentId: -1,
              path: "",
              spread: false,
              // title: "成果超市",
              title:"成果动态",
              type: "dirt"
          },
          {
              children: [],
              code: "listedTransaction",
              href: "/listedTransaction",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 4,
              parentId: -1,
              path: "",
              spread: false,
              title: "挂牌交易",
              type: "dirt"
          },
          {
              children: [],
              code: "AIbk",
              href: "/wikipedia",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 5,
              parentId: -1,
              path: "",
              spread: false,
              // title: "AI百科",
              title:"大数据智库",
              type: "dirt"
          },
          {
              children: [],
              code: "middleman",
              href: "/middleman",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 6,
              parentId: -1,
              path: "",
              spread: false,
              title: "技术经纪人",
              type: "dirt"
          },
          {
              children: [],
              code: "zyjjq",
              href: "/gx",
              icon: "fa-user",
              iconClass: "icon-home",
              id: 7,
              parentId: -1,
              path: "",
              spread: false,
              title: "资源集聚区",
              type: "dirt"
          },
          {
            children: [],
            code: "zzfw",
            href: "/serviceIndex",
            icon: "fa-user",
            iconClass: "icon-home",
            id: 8,
            parentId: -1,
            path: "",
            spread: false,
            title: "增值服务",
            type: "dirt"
        }],
      userInfo:JSON.parse(sessionStorage.getItem(`WebUserSion`))||null,
      token:null,
      // randomCodeUrl:'http://bhw.traup.net/api/userApi/payAccountMsg/captcha?randomCode=',
      randomCodeUrl:'http://www.traup.net/api/userApi/payAccountMsg/captcha?randomCode=',//商用
      wxAppid:'wx5cac8389704b294e',// 测试 wx3bfa010b497322b6  //商用 wx5cac8389704b294e
      wx_redirect_uri:"http://www.traup.net/home",
      qqAppid:'101867791',//101871056 测试
      qqredirectURI:'http://www.traup.net/viplogin'
  },

  mutations: {
      SET_USER_INFO: (state, userInfo) => {
          state.userInfo = userInfo
      },
      SET_TOKEN: (state, token) => {
          state.token = token
      }
  },
    actions: {
        userLoginReq({
                         commit,
                         state
                     }, userInfo) {
            commit('SET_TOKEN', '')
            commit('SET_USER_INFO', '')
            removeToken()
            return new Promise((resolve, reject) => {
                var that = this;
                userLoginReq(userInfo).then(response => {
                    if (undefined != response.data && '' != response.data) {
                        setToken(response.data)
                        commit('SET_TOKEN', response.data)
                        resolve()
                    }else{
                        reject(error)
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取用户信息
        GetInfo ({
                     commit,
                     state
                 }) {
            return new Promise((resolve, reject) => {
                state.token = getToken();
                commit('SET_TOKEN', state.token)
                getUserDetailByToken({token:state.token}).then(response => {
                    const data = response.data;
                    if(response.retCode==-1 && (this.$route.path=='/viplogin' || this.$route.path=='/register') ){
                        removeToken();
                        reject();
                    }else if(response.retCode==-1){
                        this.$message({
                            message: "登录已失效,请重新登录!",
                            type: 'warning'
                        });
                        removeToken();
                        reject();
                    }else if(response.retCode==0){
                        if(data){
                            sessionStorage.setItem("WebUserSion",JSON.stringify(data));
                            commit('SET_USER_INFO', data)
                            resolve(data)
                        }
                    }
                    reject();
                }).catch(error => {
                    reject(error)
                })
            })
        }
    }
}

export default user
