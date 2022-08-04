import fetch from '../../util/fetch';

export function qryBackUserList(param) {
    return fetch({
        url: '/api/adminApi/backUser/qryBackUserList',
        params:param,
        method: 'get'
    });
};

export function saveBackUser(param) {
    return fetch({
        url: '/api/adminApi/backUser/saveBackUser',
        params:param,
        method: 'post'
    });
};

export function delBackUser(param) {
    return fetch({
        url: '/api/adminApi/backUser/delBackUser',
        params:param,
        method: 'get'
    });
};


export function upBackUser(param) {
    return fetch({
        url: '/api/adminApi/backUser/upBackUser',
        params:param,
        method: 'post'
    });
};