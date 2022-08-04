import fetch from '../utils/fetch';

export function getNewsManager(param) {
    return fetch({
        url: '/api/adminApi/first/getNewsManager',
        params: param,
        method: 'get'
    });
};


export function qryNewsList(param) {
    return fetch({
        url: '/api/adminApi/news/qryNewsList',
        params: param,
        method: 'get'
    });
};

export function coreServerReq(param) {
    return fetch({
        url: '/api/adminApi/first/getCoreServer',
        params: param,
        method: 'get'
    });
};

export function getUserManagerReq(param) {
   /* return fetch({
        url: '/api/adminApi/first/getUserManager',
        params: param,
        method: 'get'
    });*/
    return fetch({
        url: '/api/adminApi/broker/qryBrokerList',
        params:param,
        method: 'get'
    });
};

export function getListingExpertReq(param) {
    return fetch({
        url: '/api/adminApi/first/getListingExpert',
        params: param,
        method: 'get'
    });
};

