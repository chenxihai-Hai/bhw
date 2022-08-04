import fetch from '../util/fetch';

export function getBackMenu(param) {
    return fetch({
        url: '/api/adminApi/backUser/getBackMenu',
        params:param,
        method: 'get'
    });
};

export function getBackUserMenu(param) {
    return fetch({
        url: '/api/adminApi/backUser/getBackUserMenu',
        params:param,
        method: 'get'
    });
};

export function backUserLogin(param) {
    return fetch({
        url: '/api/adminApi/backUser/backUserLogin',
        params:param,
        method: 'post'
    });
};