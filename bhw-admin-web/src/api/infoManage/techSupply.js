//技术合同登记与科技成果认定登记服务接口
import fetch from '../../util/fetch';
import qs from 'qs';
export function getInfoList(param) {
    return fetch({
        url: '/api/techSupplyApi/infoManage/getInfoList',
        params: param,
        method: 'get'
    });
};

export function infoAudit(param) {
    return fetch({
        url: '/api/techSupplyApi/infoManage/infoAudit',
        params: param,
        method: 'post'
    });
};

export function serverTakeOff(param) {
    return fetch({
        url: '/api/techSupplyApi/supplyMgr/serverTakeOff',
        params: param,
        method: 'post'
    });
};

export function saveReleaseInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/releaseServer',
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


export function upTechSupplyInfo(param) {
    return fetch({
        url: '/api/techSupplyApi/releaseMgr/upTechSupplyInfo',
        data:qs.stringify(param),
        method: 'post'
    });
};
