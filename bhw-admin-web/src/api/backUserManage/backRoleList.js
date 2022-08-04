import fetch from '../../util/fetch';

export function qryBackRoleList(param) {
    return fetch({
        url: '/api/adminApi/backRole/qryBackRoleList',
        params:param,
        method: 'get'
    });
};

export function saveBackRole(param) {
    return fetch({
        url: '/api/adminApi/backRole/saveBackRole',
        params:param,
        method: 'post'
    });
};


export function delBackRole(param) {
    return fetch({
        url: '/api/adminApi/backRole/delBackRole',
        params:param,
        method: 'post'
    });
};

export function upBackRole(param) {
    return fetch({
        url: '/api/adminApi/backRole/upBackRole',
        params:param,
        method: 'post'
    });
};

export function saveRoleMenus(param) {
    return fetch({
        url: '/api/adminApi/backRole/saveRoleMenus',
        params:param,
        method: 'post'
    });
};


export function qryBackRoleParams(param) {
    return fetch({
        url: '/api/adminApi/backRole/qryBackRoleParams',
        params:param,
        method: 'post'
    });
};