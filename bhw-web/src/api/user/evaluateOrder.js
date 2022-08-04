import fetch from '../../utils/fetch';

export function qryEvaluateOrderList(param) {
    return fetch({
        url: '/api/userApi/evaluateOrder/qryEvaluateOrderList',
        params:param,
        method: 'get'
    });
}

export function statisticsEvaluate(param) {
    return fetch({
        url: '/api/userApi/evaluateOrder/statisticsEvaluate',
        params:param,
        method: 'get'
    });
}

export function saveEvaluateOrder(param) {
    return fetch({
        url: '/api/userApi/evaluateOrder/saveEvaluateOrder',
        params:param,
        method: 'post'
    });
}

export function upEvaluateOrder(param) {
    return fetch({
        url: '/api/userApi/evaluateOrder/upEvaluateOrder',
        params:param,
        method: 'post'
    });
}

export function delEvaluateOrder(param) {
    return fetch({
        url: '/api/userApi/evaluateOrder/delEvaluateOrder',
        params:param,
        method: 'post'
    });
}