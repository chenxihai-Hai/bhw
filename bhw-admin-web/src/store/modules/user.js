import {
  Message
} from 'element-ui';
import {
    backUserLogin,
    getBackUserMenu
} from '../../api/login'
import {
  getToken,
  setToken,
  removeToken
} from '../../util/auth'
const user = {
  state: {
    user:JSON.parse(sessionStorage.getItem(`backUserSion`))||'',
    status: '',
    code: '',
    token: getToken(),
    name: '',
    id: '',
    avatar: '',
    introduction: '',
    roles: [],
    menus: undefined,
    permissionMenus: JSON.parse(sessionStorage.getItem(`permissionMenus`))||[],
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER: (state, user) => {
        state.user = user
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSION_MENUS: (state, permissionMenus) => {
      state.permissionMenus = permissionMenus
    }
  },

  actions: {
    // 登录
    Login ({
      commit,
      state
    }, userInfo) {
      commit('SET_TOKEN', '');
      commit('SET_ROLES', []);
      commit('SET_USER', '');
      removeToken()
      return new Promise((resolve, reject) => {
          backUserLogin(userInfo).then(response => {
              if (undefined != response.data) {
                  setToken(response.data.userInfo);
                  commit('SET_USER', response.data.userInfo);
                  commit('SET_TOKEN', response.data.userInfo);
                  commit('SET_PERMISSION_MENUS', response.data.menus);
                  sessionStorage.setItem("permissionMenus",JSON.stringify(response.data.menus));
                  sessionStorage.setItem("backUserSion",JSON.stringify(response.data.userInfo));
                  resolve(response);
              }else{
                  reject(error)
              }
        }).catch(error => {
          reject(error)
        })
      })
    },
    getBackMenu ({
               commit,
               state
           }, userInfo) {
        return new Promise((resolve, reject) => {
            getBackUserMenu({roleId:JSON.parse(getToken()).roleId}).then(response => {
                if (undefined != response.data) {
                    commit('SET_PERMISSION_MENUS', response.data);
                    sessionStorage.setItem("permissionMenus",JSON.stringify( response.data));
                    sessionStorage.setItem("backUserSion",getToken());
                }
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    }
  }
}

export default user
