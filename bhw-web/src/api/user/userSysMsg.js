import fetch from '../../utils/fetch';

export function saveMsg(param) {
    return fetch({
        url: '/api/userApi/userSysMsg/saveMsg',
        params:param,
        method: 'post'
    });
}

export function qryUserSysMsgList(param) {
    return fetch({
        url: '/api/userApi/userSysMsg/qryUserSysMsgList',
        params:param,
        method: 'post'
    });
}


export function qryUserSysMsgCounts(param) {
    return fetch({
        url: '/api/userApi/userSysMsg/qryUserSysMsgCounts',
        params:param,
        method: 'get'
    });
}
