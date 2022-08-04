import fetch from '../../utils/fetch';
export function qryOperateRecord(param) {
    return fetch({
        url: '/api/serverManage/operate/qryOperateRecord',
        params:param,
        method: 'get'
    });
}

export function saveOperateRecord(param) {
    return fetch({
        url: '/api/serverManage/operate/saveOperateRecord',
        params:param,
        method: 'post'
    });
}

export function delOperateRecord(param) {
    return fetch({
        url: '/api/serverManage/operate/delOperateRecord',
        params:param,
        method: 'post'
    });
}