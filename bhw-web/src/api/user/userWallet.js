import fetch from '../../utils/fetch';

export function qryUserWallet(param) {
    return fetch({
        url: '/api/userApi/userWallet/qryUserWallet',
        params:param,
        method: 'get'
    });
}

export function saveUserWithdraw(param) {
    return fetch({
        url: '/api/userApi/userWallet/saveUserWithdraw',
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


export function upUserWalletRemaining(param) {
    return fetch({
        url: '/api/userApi/userWallet/upUserWalletRemaining',
        params:param,
        method: 'get'
    });
}

export function qryPayDealList(param) {
    return fetch({
        url: '/api/userApi/userWallet/qryPayDealList',
        params:param,
        method: 'get'
    });
}

export function qryUserWalletOrderList(param) {
    return fetch({
        url: '/api/userApi/userWallet/qryUserWalletOrderList',
        params:param,
        method: 'get'
    });
}


export function saveBankCardInfo(param) {
    return fetch({
        url: '/api/userApi/payBankCard/saveBankCardInfo',
        params:param,
        method: 'post'
    });
}

export function bankCardVerify(param) {
    return fetch({
        url: '/api/userApi/payBankCard/bankCardVerify',
        params:param,
        method: 'post'
    });
}


export function qryBankCardList(param) {
    return fetch({
        url: '/api/userApi/payBankCard/qryBankCardList',
        params:param,
        method: 'get'
    });
}

export function delBankCard(param) {
    return fetch({
        url: '/api/userApi/payBankCard/delBankCard',
        params:param,
        method: 'get'
    });
}


