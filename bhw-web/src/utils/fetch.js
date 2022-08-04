import axios from 'axios';
import {
  Message,
  MessageBox,
  Loading
} from 'element-ui';
import store from '../store';
/*import {
  getToken,
  setToken
} from 'utils/auth';*/

// 创建axios实例
let loading;
const service = axios.create({
    timeout: 60000// 请求超时时间
});

// request拦截器
service.interceptors.request.use(config => {
  /*if (store.getters.token) {
    config.headers['Authorization'] = getToken();
  }*/
    /*loading = Loading.service({
      fullscreen: true,
      text: '加载中...',
    });*/
  // }
  return config;
}, error => {
  Promise.reject(error);
})

// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if(loading){
      loading.close();
    }
    if (response.status === 401 || res.status === 40101) {
      MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('FedLogOut').then(() => {
          location.reload(); // 为了重新实例化vue-router对象 避免bug
        });
      })
      return Promise.reject('error');
    }
    if (res.status === 40301) {
      Message({
        message: '当前用户无相关操作权限！',
        type: 'error',
        duration: 5 * 1000
      });
      return Promise.reject('error');
    }
    if (res.status === 40001 || response.data.token=='') {
      Message({
        message: '账户或密码错误！',
        type: 'warning'
      });
      return Promise.reject('error');
    }
    if (response.status !== 200 && res.status !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      });
    } else {
    /*  if(undefined != response.data.token && response.data.token){
        setToken(response.data.token);
      }*/

      return response.data;
    }
  },
  error => {
    // console.log(error); // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
