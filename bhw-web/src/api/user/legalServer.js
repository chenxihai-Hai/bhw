//法律服务接口
import fetch from '../../utils/fetch';

export function getBussinessType(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function getServerListPage(param) {
    return fetch({
        url: '/api/legalServerApi/supplyMgr/getServerListPage',
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

export function getLeaveMg(param) {
    return fetch({
        url: '/api/legalServerApi/leaveMg/getLeaveMg',
        params: param,
        method: 'get'
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

export function getLegalServerInfo(param) {
    return fetch({
        url: '/api/legalServerApi/releaseMgr/getLegalServerInfo',
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