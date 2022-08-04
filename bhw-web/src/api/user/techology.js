//技术相关供需服务接口
import fetch from '../../utils/fetch';
import qs from 'qs';
export function getBussinessType(param) {
    return fetch({
        url: '/api/techologyApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function getServerListPage(param) {
    return fetch({
        url: '/api/techologyApi/supplyMgr/getServerListPage',
        params: param,
        method: 'get'
    });
};

export function serverTakeOff(param) {
    return fetch({
        url: '/api/techologyApi/supplyMgr/serverTakeOff',
        params: param,
        method: 'post'
    });
};

export function upTechologyInfo(param) {
    return fetch({
        url: '/api/techologyApi/releaseMgr/upTechologyInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};

export function getTechologyInfo(param) {
    return fetch({
        url: '/api/techologyApi/releaseMgr/getTechologyInfo',
        params: param,
        method: 'post'
    });
};


export function delTechologyInfo(param) {
    return fetch({
        url: '/api/techologyApi/releaseMgr/delTechologyInfo',
        params: param,
        method: 'post'
    });
};


export function getLeaveMg(param) {
    return fetch({
        url: '/api/techologyApi/leaveMg/getLeaveMg',
        params: param,
        method: 'get'
    });
};

export function saveReleaseInfo(param) {
    return fetch({
        url: '/api/techologyApi/releaseMgr/releaseServer',
        data:qs.stringify(param),
        method: 'post'
    });
};