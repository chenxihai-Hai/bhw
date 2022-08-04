//技术合同登记与科技成果认定登记服务接口
import fetch from '../../utils/fetch';
import qs from 'qs';
export function getBussinessType(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function getServerListPage(param) {
    return fetch({
        url: '/api/techSupplyApi/supplyMgr/getServerListPage',
        params: param,
        method: 'get'
    });
};

export function serverTakeOff(param) {
    return fetch({
        url: '/api/techSupplyApi/supplyMgr/serverTakeOff',
        params: param,
        method: 'post'
    });
};

export function getLeaveMg(param) {
    return fetch({
        url: '/api/techSupplyApi/leaveMg/getLeaveMg',
        params: param,
        method: 'get'
    });
};

export function saveReleaseInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/releaseServer',
        data:qs.stringify(param),
        method: 'post'
    });
};

export function upTechSupplyInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/upTechSupplyInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};

export function delTechSupplyInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/delTechSupplyInfo',
        params: param,
        method: 'post'
    });
};

export function getTechSupplyInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/getTechSupplyInfo',
        params: param,
        method: 'get'
    });
};