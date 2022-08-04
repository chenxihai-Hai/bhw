import fetch from '../../util/fetch';

export function getInfoList(param) {
    return fetch({
        url: '/api/legalServerApi/infoManage/getInfoList',
        params: param,
        method: 'get'
    });
};

export function infoAudit(param) {
    return fetch({
        url: '/api/legalServerApi/infoManage/infoAudit',
        params: param,
        method: 'post'
    });
};

export function saveReleaseInfo(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/releaseServer',
        params: param,
        method: 'post'
    });
};


export function upLegalServerInfo(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/upLegalServerInfo',
        params: param,
        method: 'post'
    });
};

export function delLegalServerInfo(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/delLegalServerInfo',
        params: param,
        method: 'post'
    });
};


export function getLegalServerInfo(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/getLegalServerInfo',
        params: param,
        method: 'post'
    });
};


export function getBussinessType(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function serverTakeOff(param) {
    return fetch({
        url: '/api/legalServerApi/supplyMgr/serverTakeOff',
        params: param,
        method: 'post'
    });
};