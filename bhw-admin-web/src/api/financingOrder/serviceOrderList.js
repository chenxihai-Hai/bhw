import fetch from '../../util/fetch';


export function backQryWalletOrderList(param) {
    return fetch({
        url: '/api/userApi/userWallet/backQryWalletOrderList',
        params:param,
        method: 'get'
    });
}