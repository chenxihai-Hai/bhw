import fetch from '../../util/fetch';
import qs from 'qs';

export function addProject(param) {
    return fetch({
        url: '/api/serverManage/intellectua/addProject',
        data:qs.stringify(param),
        method: 'post'
    });
}

export function upProject(param) {
    return fetch({
        url: '/api/serverManage/intellectua/upProject',
        data:qs.stringify(param),
        method: 'post'
    });
}

export function delServerProject(param) {
    return fetch({
        url: '/api/serverManage/intellectua/delServerProject',
        params:param,
        method: 'post'
    });
}

export function batchUpStatus(param) {
    return fetch({
        url: '/api/serverManage/intellectua/batchUpStatus',
        params:param,
        method: 'post'
    });
}

export function batchUpBoutique(param) {
    return fetch({
        url: '/api/serverManage/intellectua/batchUpBoutique',
        params:param,
        method: 'post'
    });
}


export function batchUpAuditStatus(param) {
    return fetch({
        url: '/api/serverManage/intellectua/batchUpAuditStatus',
        params:param,
        method: 'post'
    });
}

export function qryProjectList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryProjectList',
        params:param,
        method: 'get'
    });
}

export function qryIntellectualType(param) {
    return fetch({
        url: '/api/serverManage/dataType/qryIntellectualType',
        params:param,
        method: 'get'
    });
}