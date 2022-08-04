package com.bhw.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.dto.ResourceAreaRangeReq;
import com.bhw.admin.mapper.ResourceAreaMapper;
import com.bhw.admin.pojo.ResourceAreaBean;
import com.bhw.admin.vo.AreaTopFive;
import com.bhw.common.base.Page;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResourceAreaService {
    @Resource
    ResourceAreaMapper areaMapper;

    public int getResourceCount(Integer type){
        int count=areaMapper.selectCountByType(type);
        return count;
    }

    public  List<AreaTopFive> getResourceProvince(Integer type){
        List<AreaTopFive> list=areaMapper.selectProvinceRank(type);
        return list;
    }

    public List<ResourceAreaBean> getResourceAreaRange(ResourceAreaRangeReq req, Page page){

        IPage<ResourceAreaBean> model=areaMapper.selectResourceAreaRange(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ResourceAreaBean>(page.getCurPage(),page.getPageSize()),req);
        List<ResourceAreaBean> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public boolean addData(MultipartFile file,String fileName)throws IOException{
        if(fileName.endsWith("XLS")){
            InputStream inputStream=file.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(inputStream);
            HSSFSheet sheetAt=wb.getSheetAt(0);
            List<ResourceAreaBean> list=new ArrayList<>();
            for(Row row:sheetAt){
                ResourceAreaBean item=new ResourceAreaBean();
                item.setFullName(row.getCell(0).getStringCellValue());
                item.setShortName(row.getCell(1).getStringCellValue());
                item.setProfile(row.getCell(2).getStringCellValue());
                item.setLegalPerson(row.getCell(2).getStringCellValue());
                item.setRegisteredCapital(row.getCell(3).getStringCellValue());
                item.setCreateTime(row.getCell(4).getStringCellValue());
                item.setAddress(row.getCell(5).getStringCellValue());
                item.setIndustey(row.getCell(6).getStringCellValue());
                item.setType(Integer.parseInt(row.getCell(7).getStringCellValue()));
                item.setImg(row.getCell(8).getStringCellValue());
                item.setCityId(Integer.parseInt(row.getCell(10).getStringCellValue()));
                list.add(item);
            }
        }
        return false;
    }
}
