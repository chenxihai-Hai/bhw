import fetch from '../../util/fetch';

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

export function qryRequireList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryRequireList',
        params:param,
        method: 'get'
    });
}

export function qryScienceRequireList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryScienceRequireList',
        params:param,
        method: 'get'
    });
}

export function qryRequireTypeList(param) {
    return fetch({
        url: '/api/serverManage/dataType/qryRequireTypeList',
        params:param,
        method: 'get'
    });
}

export function qryScienceRequireTypeList(param) {
    return fetch({
        url: '/api/serverManage/dataType/qryScienceRequireTypeList',
        params:param,
        method: 'get'
    });
}


export function qryProjectOrderList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryProjectOrderList',
        params:param,
        method: 'get'
    });
}

export function qryRequireAdvisory(param) {
    return fetch({
        url: '/api/serverManage/requireAdvisory/qryRequireAdvisory',
        params:param,
        method: 'get'
    });
}

export function saveRequireAdvisory(param) {
    return fetch({
        url: '/api/serverManage/requireAdvisory/saveRequireAdvisory',
        params:param,
        method: 'post'
    });
}


export function qryServiceDealList(param) {
    return fetch({
        url: '/api/serverManage/dw/qryServiceDealList',
        params:param,
        method: 'post'
    });
}


export function qryServerBusiness(param) {
    return fetch({
        url: '/api/serverManage/dw/qryServerBusiness',
        params:param,
        method: 'post'
    });
}


export function saveLeaveMsg(param) {
    return fetch({
        url: '/api/techologyApi/leaveMg/saveLeaveMsg',
        params:param,
        method: 'post'
    });
}


export function qryLeaveMsg(param) {
    return fetch({
        url: '/api/techologyApi/leaveMg/qryLeaveMsg',
        params:param,
        method: 'post'
    });
}
