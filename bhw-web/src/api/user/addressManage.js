import fetch from '../../utils/fetch';

export function qryAddressManageList(param) {
    return fetch({
        url: '/api/userApi/addressManage/qryAddressManageList',
        params:param,
        method: 'get'
    });
}

export function saveAddressManage(param) {
    return fetch({
        url: '/api/userApi/addressManage/saveAddressManage',
        params:param,
        method: 'post'
    });
}

export function upAddressManage(param) {
    return fetch({
        url: '/api/userApi/addressManage/upAddressManage',
        params:param,
        method: 'post'
    });
}

export function delAddressManage(param) {
    return fetch({
        url: '/api/userApi/addressManage/delAddressManage',
        params:param,
        method: 'post'
    });
}