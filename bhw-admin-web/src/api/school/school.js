import fetch from "../../util/fetch";
import qs from 'qs';

//获取高校列表、
export function qryCollegeList(param){
    return fetch({
        url: '/api/adminApi/college/qryCollegeList',
        params:param,
        method: 'get'
    });
};

export function saveSchoolInfo(param){
    return fetch({
        url: '/api/adminApi/college/saveSchoolInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};



export function upSchoolInfo(param){
    return fetch({
        url: '/api/adminApi/college/upSchoolInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};


export function delSchoolInfo(param){
    return fetch({
        url: '/api/adminApi/college/delSchoolInfo',
        params:param,
        method: 'post'
    });
};


export function savePatentBean(param) {
    return fetch({
        url: '/api/adminApi/patent/savePatentBean',
        data:qs.stringify(param),
        method: 'post'
    });
}

export function upPatentBean(param) {
    return fetch({
        url: '/api/adminApi/patent/upPatentBean',
        data:qs.stringify(param),
        method: 'post'
    });
}

export function delPatentBean(param) {
    return fetch({
        url: '/api/adminApi/patent/delPatentBean',
        params: param,
        method: 'get'
    });
}
//获取高校详情成果列表、
export function getPatentList(param) {
    return fetch({
        url: '/api/adminApi/patent/qryPatentList',
        params: param,
        method: 'get'
    });
}

export function getResourceAreaRange(param) {
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceAreaRange',
        params: param,
        method: 'get'
    });
};


export function saveResourceArea(param) {
    return fetch({
        url: '/api/adminApi/resourceArea/saveResourceArea',
        params: param,
        method: 'post'
    });
};


export function upResourceArea(param) {
    return fetch({
        url: '/api/adminApi/resourceArea/upResourceArea',
        params: param,
        method: 'post'
    });
};

export function delResourceArea(param) {
    return fetch({
        url: '/api/adminApi/resourceArea/delResourceArea',
        params: param,
        method: 'post'
    });
};