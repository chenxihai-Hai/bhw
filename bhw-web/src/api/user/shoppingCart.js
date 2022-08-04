import fetch from '../../utils/fetch';

export function qryUserShoppingCartList(param) {
    return fetch({
        url: '/api/userApi/shoppingCart/qryUserShoppingCartList',
        params:param,
        method: 'get'
    });
}

export function saveUserShopping(param) {
    return fetch({
        url: '/api/userApi/shoppingCart/saveUserShopping',
        params:param,
        method: 'post'
    });
}

export function delUserShopping(param) {
    return fetch({
        url: '/api/userApi/shoppingCart/delUserShopping',
        params:param,
        method: 'post'
    });
}