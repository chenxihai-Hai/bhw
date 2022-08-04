import fetch from '../../util/fetch';

export function getInfoList(param) {
    return fetch({
        url: '/api/fiscalTaxApi/supplyMgr/backSelectServerList',
        params: param,
        method: 'get'
    });
};

export function infoAudit(param) {
    return fetch({
        url: '/api/fiscalTaxApi/supplyMgr/infoAudit',
        params: param,
        method: 'post'
    });
};

export function saveReleaseInfo(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/releaseTaxServer',
        params: param,
        method: 'post'
    });
};


export function upFiscalTaxInfo(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/upFiscalTaxInfo',
        params: param,
        method: 'post'
    });
};



export function delFiscalTaxInfo(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/delFiscalTaxInfo',
        params: param,
        method: 'post'
    });
};


export function getBussinessType(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function serverTakeOff(param) {
    return fetch({
        url: '/api/fiscalTaxApi/supplyMgr/serverTakeOff',
        params: param,
        method: 'post'
    });
};