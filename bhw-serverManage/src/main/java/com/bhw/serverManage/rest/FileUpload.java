package com.bhw.serverManage.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Response;
import com.bhw.common.util.HttpUtils;
import com.bhw.serverManage.mapper.*;
import com.bhw.serverManage.pojo.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Controller
@RequestMapping("/fileUpload")
public class FileUpload {
	Logger logger = LoggerFactory.getLogger(FileUpload.class);

	@Resource
	BigDataMapper bigDataMapper;

	@Resource
	BigDataDetMapper bigDataDetMapper;

	@Resource
	BigDataSbMapper bigDataSbMapper;

	@Resource
	BigDataSbDetMapper bigDataSbDetMapper;

	@Resource
	BigDataBqMapper bigDataBqMapper;

	@Resource
	BigDataBqDetMapper bigDataBqDetMapper;

	@RequestMapping(value = "/upload")
	@ResponseBody
	public Response<Object> upload(@RequestParam("file") MultipartFile cmf, HttpServletRequest request) {
		Response<Object> response = new Response<Object>(); 
		File file = null;
		try {
			String extension = FilenameUtils.getExtension(cmf.getOriginalFilename());
			ClassPathResource classPathResource = new ClassPathResource("bootstrap.yml");
			String path1 =  classPathResource.getURI().toString();
			path1 = path1.replace("jar:file:", "");
			int index = path1.lastIndexOf("/bhw-serverManage.jar")>-1?path1.lastIndexOf("/bhw-serverManage.jar"):path1.lastIndexOf("/bhw-serverManage.jar!");
			index = index<0?path1.lastIndexOf("/bhwserverManage.jar")>-1?path1.lastIndexOf("/bhwserverManage.jar"):path1.lastIndexOf("/bhwserverManage.jar!"):-1;
			if( index>-1){
				path1 = path1.substring(0,index);
				path1 = path1+"/temp."+extension;
			}else if(path1.lastIndexOf("/bootstrap.yml")>-1){
				path1 =  FileUpload.class.getResource("/").getFile().split("WEB-INF/")[0]+"temp."+extension;
			}
			file = new File(path1);
			cmf.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		response.setData(upServer(file));
		return response;
	}

	public String upServer(File f){
		String fileName = f.getName();
		try {
			String addr ="http://yhtg.huayunstar.com:9999/media/upload";
			Map<String, String> params = new HashMap<String, String>();
			params.put("fid", "v_watchAudio");// 存放文件服务器地址
			params.put("uid", "0");
			params.put("filename",fileName);
			String result = HttpUtils.post(addr, params, 60, f, "file");
			JSONObject object = JSON.parseObject(result);
			if(object.getIntValue("retCode")==0){
				JSONObject parseObject = JSON.parseObject(object.getString("data"));
				result = parseObject.getString("host")+parseObject.getString("path")+parseObject.getString("name");
				f.deleteOnExit();
			}
			return result;
		}catch(Exception e){
		}
		return "";
	}

	@RequestMapping(value = "/downDetData")
	@ResponseBody
	public void downDetData(Integer page) {

		ExecutorService executorService = Executors.newWorkStealingPool();
		executorService.execute(new Runnable() {
			public void run() {
				Wrapper<BigData> queryWrapper =  new QueryWrapper<BigData>();
				((QueryWrapper<BigData>) queryWrapper).orderByAsc("id");
				IPage<BigData> brokerAdvisoryIPage = bigDataMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BigData>(page, 200),queryWrapper);
				List<BigData> records = brokerAdvisoryIPage.getRecords();
				String addr ="http://114.251.8.193/api/patent/detail/catalog";
				Map<String, String> params = new HashMap<String, String>();
				params.put("client_id", "16252655ac110004666d997195ac0d0e");
				params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
				params.put("scope","read_cn");
				List<String> list = new ArrayList<>();
				records.forEach((bg)->{
					JSONObject jsonObject = JSON.parseObject(bg.getContent());
					String pid = jsonObject.getString("pid");
					params.put("pid",pid);
					params.put("lang","cn");
					HttpUtils httpUtils = new HttpUtils();
					String result = null;
					try {
						result = httpUtils.post(addr,params);
						System.out.println(result);
						JSONObject object = JSON.parseObject(result);
						JSONObject context = object.getJSONObject("context");
						if(null != context && !context.isEmpty()){
							JSONArray records2 = context.getJSONArray("records");
							records2.forEach((re)->{
								BigDataDet bigData = new BigDataDet();
								bigData.setPid(pid);
								bigData.setContent(re.toString());
								bigDataDetMapper.insert(bigData);
							});
						}else{
							list.add(pid);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				System.out.println("z暂无详情:"+JSON.toJSONString(list));
			}
		});
	}

	@RequestMapping(value = "/downSbDetData")
	@ResponseBody
	public void downSbDetData(Integer page) {

		ExecutorService executorService = Executors.newWorkStealingPool();
		executorService.execute(new Runnable() {
			public void run() {
				Wrapper<BigDataSb> queryWrapper =  new QueryWrapper<BigDataSb>();
				((QueryWrapper<BigDataSb>) queryWrapper).orderByAsc("id");
				IPage<BigDataSb> brokerAdvisoryIPage = bigDataSbMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BigDataSb>(page, 200),queryWrapper);
				List<BigDataSb> records = brokerAdvisoryIPage.getRecords();
				String addr ="http://114.251.8.193/api/trademark/detail/china";
				Map<String, String> params = new HashMap<String, String>();
				params.put("client_id", "16252655ac110004666d997195ac0d0e");
				params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
				params.put("scope","read_cn");
				List<String> list = new ArrayList<>();
				records.forEach((bg)->{
					JSONObject jsonObject = JSON.parseObject(bg.getContent());
					String pid = jsonObject.getString("tid");
					params.put("id",pid);
					HttpUtils httpUtils = new HttpUtils();
					String result = null;
					try {
						result = httpUtils.post(addr,params);
						JSONObject object = JSON.parseObject(result);
						JSONObject context = object.getJSONObject("context");
						if(null != context && !context.isEmpty()){
							JSONArray records2 = context.getJSONArray("records");
							records2.forEach((re)->{
								BigDataSbDet bigData = new BigDataSbDet();
								bigData.setSbid(pid);
								bigData.setContent(re.toString());
								bigDataSbDetMapper.insert(bigData);
							});
						}else{
							list.add(pid);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				System.out.println("z暂无详情:"+JSON.toJSONString(list));
			}
		});
	}

	@RequestMapping(value = "/downBqData")
	@ResponseBody
	public void downBqData( String page){
		String[] types = new String[]{"10000 ","10100","10101","10102","10109","10200","10300","10400","10900","20000","20100","20200","20300","20900","30000","30100","30101",
				"30102","30103","30104","30105","30106","30107","30108","30109","30900","30200","30204","30205","30206","30207","30208",
				"30209","30210","30211","30212","30213","30219","30300","30304","30305","30309","0000","0100","0200","0300","0400",
				"0500","0600","0700","0800","0900","1000","1100","1300","1400","1500","1600","1700","1800","1900","2000","2100","2200",
				"2300","2400","2500","2600","2700","2800","2900","3000","3100","3200","3300","3400","3500","3600","3700","3900","4000",
				"4100","4200","4300","4400","4500","4600","4700","4800","4900","5000","5100","5200","5300","5400","5500","5600","5700",
				"5800","5900","6000","6100","6200","6300","6500","6600","6700","6800","6900","7000","7100","7200","7300","7400","7500",
				"7600","7700","7800","7900","8000","8100","8200","8300","8400","8500","8600","8700","8800","8900","9000","9100","9200",
				"9300","9400","9500","9600","9700","9800"
		};

		ExecutorService executorService = Executors.newWorkStealingPool();
		executorService.execute(new Runnable() {
			public void run() {

				String addr ="http://114.251.8.193/api/copyrightrzw/search/expression";
				Map<String, String> params = new HashMap<String, String>();
				params.put("client_id", "16252655ac110004666d997195ac0d0e");
				params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
				params.put("scope","read_cn");
				for (String type: types) {
					params.put("express","分类号="+type);
					params.put("page",page);
					params.put("page_row","10");
					HttpUtils httpUtils = new HttpUtils();
					String result = null;
					try {
						result = httpUtils.post(addr,params);
						JSONObject object = JSON.parseObject(result);
						JSONObject context = object.getJSONObject("context");
						if(null == context || context.isEmpty()){
							continue;
						}
						JSONArray records = context.getJSONArray("records");
						records.forEach((re)->{
							BigDataBq bigData = new BigDataBq();
							bigData.setTypeCode(type);
							bigData.setContent(re.toString());
							bigDataBqMapper.insert(bigData);
						});

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}


	@RequestMapping(value = "/downSbData")
	@ResponseBody
	public void downSbData( String page){
		String[] types = new String[]{"0101","0102","0103","0104","0105","0106","0107","0108","0109","0110","0111","0112","0113","0114","0115","0116","0117",
				"0201","0202","0203","0204","0205","0206","0207","0301","0302","0303","0304","0305","0306","0307","0308"
		};

		ExecutorService executorService = Executors.newWorkStealingPool();
		executorService.execute(new Runnable() {
			public void run() {

				String addr ="http://114.251.8.193/api/trademark/search/expression";
				Map<String, String> params = new HashMap<String, String>();
				params.put("client_id", "16252655ac110004666d997195ac0d0e");
				params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
				params.put("scope","read_cn");
				for (String type: types) {
					params.put("express","分类号="+type);
					params.put("page",page);
					params.put("page_row","10");
					HttpUtils httpUtils = new HttpUtils();
					String result = null;
					try {
						result = httpUtils.post(addr,params);
						JSONObject object = JSON.parseObject(result);
						JSONObject context = object.getJSONObject("context");
						if(null == context || context.isEmpty()){
							continue;
						}
						JSONArray records = context.getJSONArray("records");
						records.forEach((re)->{
							BigDataSb bigData = new BigDataSb();
							bigData.setTypeCode(type);
							bigData.setContent(re.toString());
							bigDataSbMapper.insert(bigData);
						});

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@RequestMapping(value = "/downData")
	@ResponseBody
	public void downData( String page){
		String[] types = new String[]{"A01","A01B","A01C","A01D","A01F","A01G","A01H","A01J","A01K","A01L","A01M","A01N","A01P","A21","A21B","A21C","A21D","A22","A22B","A22C",
				"A23","A23B","A23C","A23D","A23F","A23G","A23H","A23J","A23K","A23L","A23M","A23N","A23P","A24","A24B","A24C","A24D","A24F","A41","A41B","A41C","A41D","A41F",
				"A41G","A41H","A42","A42B","A42C","A43","A43B","A43C","A43D","A44","A44B","A44C","A45","A45B","A45C","A45D","A45F","A46","A46B","A46C",
				"A47","A47B","A47C","A47D","A47F","A47G","A47H","A47J","A47K","A47L","A61","A61B","A61C","A61D","A61F","A61G","A61H","A61J","A61K","A61L","A61M","A61N","A61P","A61Q",
				"A62","A62B","A62C","A62D","A63","A63B","A63C","A63D","A63F","A63G","A63H","A63J","A63K"
		};

		ExecutorService executorService = Executors.newWorkStealingPool();
		executorService.execute(new Runnable() {
			public void run() {

				String addr ="http://114.251.8.193/api/patent/search/expression";
				Map<String, String> params = new HashMap<String, String>();
				params.put("client_id", "16252655ac110004666d997195ac0d0e");
				params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
				params.put("scope","read_cn");
				for (String type: types) {
					params.put("express","分类号="+type);
					params.put("page",page);
					params.put("page_row","50");
					HttpUtils httpUtils = new HttpUtils();
					String result = null;
					try {
						result = httpUtils.post(addr,params);
						JSONObject object = JSON.parseObject(result);
						JSONObject context = object.getJSONObject("context");
						if(null == context || context.isEmpty()){
							continue;
						}
						JSONArray records = context.getJSONArray("records");
						records.forEach((re)->{
							BigData bigData = new BigData();
							bigData.setTypeCode(type);
							bigData.setContent(re.toString());
							bigDataMapper.insert(bigData);
						});

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
//		String[] types = new String[]{"A01","A01B","A01C","A01D","A01F","A01G","A01H","A01J","A01K","A01L","A01M","A01N","A01P","A21","A21B","A21C","A21D","A22","A22B","A22C",
//				"A23","A23B","A23C","A23D","A23F","A23G","A23H","A23J","A23K","A23L","A23M","A23N","A23P","A24","A24B","A24C","A24D","A24F","A41","A41B","A41C","A41D","A41F",
//				"A41G","A41H","A42","A42B","A42C","A43","A43B","A43C","A43D","A44","A44B","A44C","A45","A45B","A45C","A45D","A45F","A46","A46B","A46C",
//				"A47","A47B","A47C","A47D","A47F","A47G","A47H","A47J","A47K","A47L","A61","A61B","A61C","A61D","A61F","A61G","A61H","A61J","A61K","A61L","A61M","A61N","A61P","A61Q",
//				"A62","A62B","A62C","A62D","A63","A63B","A63C","A63D","A63F","A63G","A63H","A63J","A63K"
//		};
//		System.out.print(types.length);
		String addr ="http://114.251.8.193/api/patent/search/expression";
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", "16252655ac110004666d997195ac0d0e");
		params.put("access_token", "43c63a8d-7898-4176-afba-45e678e5ced7");
		params.put("scope","read_cn");
		params.put("express","分类号=A21B");
		params.put("page","1");
		params.put("page_row","50");
		HttpUtils httpUtils = new HttpUtils();

		String result = null;
		try {
			result = httpUtils.post(addr,params);
			JSONObject object = JSON.parseObject(result);
			JSONObject context = object.getJSONObject("context");
			JSONArray records = context.getJSONArray("records");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result);
	}
}
