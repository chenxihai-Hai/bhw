import fetch from '../../util/fetch';

export function qryAdvertInfo(param) {
    return fetch({
        url: '/api/adminApi/advert/qryAdvertInfo',
        params:param,
        method: 'get'
    });
};

export function saveAdvertInfo(param) {
    return fetch({
        url: '/api/adminApi/advert/saveAdvertInfo',
        params:param,
        method: 'post'
    });
};

export function upAdvertInfo(param) {
    return fetch({
        url: '/api/adminApi/advert/upAdvertInfo',
        params:param,
        method: 'post'
    });
};

export function delAdvertInfo(param) {
    return fetch({
        url: '/api/adminApi/advert/delAdvertInfo',
        params:param,
        method: 'post'
    });
};