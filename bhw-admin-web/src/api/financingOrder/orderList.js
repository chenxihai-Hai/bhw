import fetch from '../../util/fetch';

export function backQryOrderList(param) {
    return fetch({
        url: '/api/serverManage/dw/backQryOrderList',
        params:param,
        method: 'get'
    });
}

export function backQryPayDealList(param) {
    return fetch({
        url: '/api/userApi/userWallet/backQryPayDealList',
        params:param,
        method: 'get'
    });
}

export function upOrderCloseSt(param) {
    return fetch({
        url: '/api/payApi/order/upOrderCloseSt',
        params:param,
        method: 'post'
    });
}

export function qryUserWithdrawList(param) {
    return fetch({
        url: '/api/userApi/userWallet/qryUserWithdrawList',
        params:param,
        method: 'get'
    });
}

export function upUserWithdraw(param) {
    return fetch({
        url: '/api/userApi/userWallet/upUserWithdraw',
        params:param,
        method: 'post'
    });
}