import fetch from '../../utils/fetch';

export function qryWxAccessTokenApi(param) {
    return fetch({
        url: '/api/userApi/txApi/getWxAccessTokenApi',
        params:param,
        method: 'get'
    });
}

export function qryQqUserInfo(param) {
    return fetch({
        url: '/api/userApi/txApi/qryQqUserInfo',
        params:param,
        method: 'get'
    });
}

export function bindOpenId(param) {
    return fetch({
        url: '/api/userApi/txApi/bindOpenId',
        params:param,
        method: 'post'
    });
}

export function saveUserInfo(param) {
    return fetch({
        url: '/api/userApi/txApi/saveUserInfo',
        params:param,
        method: 'post'
    });
}

export function unbind(param) {
    return fetch({
        url: '/api/userApi/txApi/unbind',
        params:param,
        method: 'post'
    });
}