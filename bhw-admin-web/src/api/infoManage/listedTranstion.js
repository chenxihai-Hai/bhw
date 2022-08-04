import fetch from '../../util/fetch';

export function backQryListedList(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/backQryListedList',
        params:param,
        method: 'get'
    });
};

export function saveListedTran(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/saveListedTran',
        params:param,
        method: 'post'
    });
};

export function upListedTran(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/upListedTran',
        params:param,
        method: 'post'
    });
};


export function saveListedNot(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/saveListedNot',
        params:param,
        method: 'post'
    });
};

export function listedAdvisoryList(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedAdvisoryList',
        params:param,
        method: 'get'
    });
};


export function uplistedAdvisory(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/uplistedAdvisory',
        params:param,
        method: 'post'
    });
};