import fetch from '../../utils/fetch';

export function getBussinessType(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/getBussinessType',
        params: param,
        method: 'get'
    });
};

export function getServerListPage(param) {
    return fetch({
        url: '/api/fiscalTaxApi/supplyMgr/getServerListPage',
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

export function getLeaveMg(param) {
    return fetch({
        url: '/api/fiscalTaxApi/leaveMg/getLeaveMg',
        params: param,
        method: 'get'
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


export function getFiscalTaxInfo(param) {
    return fetch({
        url: '/api/fiscalTaxApi/releaseMgr/getFiscalTaxInfo',
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