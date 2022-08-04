import fetch from '../../util/fetch';

export function getUserList(param) {
    return fetch({
        url: '/api/userApi/userManage/getUserList',
        params:param,
        method: 'get'
    });
};

export function editUser(param) {
    return fetch({
        url: '/api/userApi/userManage/editUser',
        params:param,
        method: 'post'
    });
};

export function deleteUserList(param) {
    return fetch({
        url: '/api/userApi/userManage/deleteUserList',
        params:param,
        method: 'post'
    });
};

export function getAuditUserList(param) {
    return fetch({
        url: '/api/userApi/userManage/getAuditUserList',
        params:param,
        method: 'get'
    });
};

export function infoAudit(param) {
    return fetch({
        url: '/api/userApi/userManage/infoAudit',
        params:param,
        method: 'post'
    });
};


export function qryUser(param) {
    return fetch({
        url: '/api/userApi/bhwUser/qryUser',
        params:param,
        method: 'get'
    });
};

export function qryUserMemberList(param) {
    return fetch({
        url: '/api/userApi/bhwUser/qryUserMemberList',
        params:param,
        method: 'get'
    });
}


export function pushUserMemberMsg(param) {
    return fetch({
        url: '/api/userApi/bhwUser/pushUserMemberMsg',
        params:param,
        method: 'post'
    });
}

export function saveMsg(param) {
    return fetch({
        url: '/api/userApi/userSysMsg/saveMsg',
        params:param,
        method: 'post'
    });
}