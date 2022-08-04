import fetch from '../../util/fetch';

export function qryProvinceList() {
    return fetch({
        url: '/api/adminApi/city/qryProvinceList',
        method: 'get'
    });
}

export function qryCityList(param) {
    return fetch({
        url: '/api/adminApi/city/qryCityList',
        params:param,
        method: 'get'
    });
}


export function qryServiceDwList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryServiceDwList',
        params:param,
        method: 'get'
    });
}


export function saveBackOperate(param) {
    return fetch({
        url: '/api/adminApi/backHome/saveBackOperate',
        params:param,
        method: 'post'
    });
}

export function qryBackOperate(param) {
    return fetch({
        url: '/api/adminApi/backHome/qryBackOperate',
        params:param,
        method: 'post'
    });
}