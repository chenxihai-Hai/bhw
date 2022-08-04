import fetch from '../../utils/fetch';
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


export function batchUpStatus(param) {
    return fetch({
        url: '/api/serverManage/intellectua/batchUpStatus',
        params:param,
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

export function qryRefundRecordList(param) {
    return fetch({
        url: '/api/serverManage/refundRecord/qryRefundRecordList',
        params:param,
        method: 'post'
    });
}

export function upRefundRecord(param) {
    return fetch({
        url: '/api/serverManage/refundRecord/upRefundRecord',
        data:qs.stringify(param),
        method: 'post'
    });
}

