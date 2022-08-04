import fetch from '../../util/fetch';
import qs from 'qs';
export function qryNewsList(param) {
    return fetch({
        url: '/api/adminApi/news/qryNewsList',
        params:param,
        method: 'get'
    });
};


export function saveNewsInfo(param) {
    return fetch({
        url: '/api/adminApi/news/saveNewsInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};

export function upNewsInfo(param) {
    return fetch({
        url: '/api/adminApi/news/upNewsInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};

export function delNewsInfo(param) {
    return fetch({
        url: '/api/adminApi/news/delNewsInfo',
        params:param,
        method: 'post'
    });
};