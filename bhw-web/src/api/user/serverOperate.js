import fetch from '../../utils/fetch';

export function qryRecordList(param) {
    return fetch({
        url: '/api/userApi/serverOperate/qryRecordList',
        params:param,
        method: 'get'
    });
}

export function qryRecordDateList(param) {
    return fetch({
        url: '/api/userApi/serverOperate/qryRecordDateList',
        params:param,
        method: 'get'
    });
}

export function deleteRecordByIds(param) {
    return fetch({
        url: '/api/userApi/serverOperate/deleteRecordByIds',
        params:param,
        method: 'get'
    });
}

export function saveRecord(param) {
    return fetch({
        url: '/api/userApi/serverOperate/saveRecord',
        params:param,
        method: 'post'
    });
}