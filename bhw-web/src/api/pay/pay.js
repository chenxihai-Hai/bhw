import fetch from '../../utils/fetch';

export function wxPay(param) {
    return fetch({
        url: '/api/payApi/pay/wxPay',
        params:param,
        method: 'get'
    });
}
export function aliPay(param) {
    return fetch({
        url: '/api/payApi/aliPay/pay',
        params:param,
        method: 'get'
    });
}

export function refund(param) {
    return fetch({
        url: '/api/payApi/order/refund',
        params:param,
        method: 'post'
    });
}

export function upOrderSt(param) {
    return fetch({
        url: '/api/payApi/order/upOrderSt',
        params:param,
        method: 'post'
    });
}

export function qryOrderInfo(param) {
    return fetch({
        url: '/api/payApi/order/qryOrderInfo',
        params:param,
        method: 'get'
    });
}

export function saveOrder(param) {
    return fetch({
        url: '/api/payApi/order/saveOrder',
        params:param,
        method: 'post'
    });
}

export function saveWalletOrder(param) {
    return fetch({
        url: '/api/payApi/order/saveWalletOrder',
        params:param,
        method: 'post'
    });
}

export function qryWalletOrderInfo(param) {
    return fetch({
        url: '/api/payApi/order/qryWalletOrderInfo',
        params:param,
        method: 'get'
    });
}

