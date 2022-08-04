import fetch from "../../utils/fetch";

export function getResourceCount(param){
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceCount',
        params:param,
        method: 'get'
    });
};

export function getResourceProvince(param){
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceProvince',
        params:param,
        method: 'get'
    });
};

export function getResourceAreaRange(param){
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceAreaRange',
        params:param,
        method: 'get'
    });
};