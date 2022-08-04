package com.bhw.user.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bhw.common.base.Response;
import com.bhw.common.util.HttpUtils;
import com.bhw.common.util.IdCardUtil;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/fileUpload")
public class FileUpload {
	Logger logger = LoggerFactory.getLogger(FileUpload.class);

	@RequestMapping(value = "/idCardUpload")
	@ResponseBody
	public Response<Object> idCardUpload(@RequestParam("file") MultipartFile cmf, HttpServletRequest request,String idCard,String side){
		Response<Object> response = new Response<>();
		logger.info("|身份证上传|"+idCard);
		String front = null;
		try {
			side = StringUtils.isEmpty(side)?"front":side;
			front = IdCardUtil.requestIdCard(side, cmf.getBytes(), IdCardUtil.getAccessToken());
			logger.info("|图像识别结果|"+front);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(front.equals(idCard) || front.equals(side)){
			return upload(cmf,request);
		}
		response.setRetCode(-1);
		return response;
	}

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
			String markImgPath = path1;
			int index = path1.lastIndexOf("/bhw-user.jar")>-1?path1.lastIndexOf("/bhw-user.jar"):path1.lastIndexOf("/bhwuser.jar!");
			if( index>-1){
				path1 = path1.substring(0,index);
				markImgPath = path1+"/markbg.png";
				path1 = path1+"/temp."+extension;
			}else if(path1.lastIndexOf("/bootstrap.yml")>-1){
				path1 =  FileUpload.class.getResource("/").getFile().split("WEB-INF/")[0]+"temp."+extension;
				markImgPath = FileUpload.class.getResource("/").getFile().split("WEB-INF/")[0]+"markbg.png";
			}
			file = new File(path1);
			cmf.transferTo(file);
			createMark(file,markImgPath,new Color(255, 200, 0, 118),extension);
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setData(upServer(file));
		return response;
	}

	/**
	 * 给图片添加水印
	 *
	 * @param filePath
	 *             需要添加水印的图片的路径
	 *             水印的文字
	 * @param markContentColor
	 *             水印文字的颜色
	 * @return
	 */
	public void createMark(File filePath, String markImgPath,
						   Color markContentColor,String extension) {
		try {
			Image theImg = ImageIO.read(filePath);
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			BufferedImage bimage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

			Graphics2D g = bimage.createGraphics();
			g.setColor(markContentColor);
			g.drawImage(theImg, 0, 0, width,height,null);

			// 水印文件
			File _filebiao = new File(markImgPath);
			Image src_biao = ImageIO.read(_filebiao);
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			// 水印坐标
			int x = 0, y = 0;
			g.drawImage(src_biao, x, y, width_biao, height_biao, null);

			/*Font font = new Font("微软雅黑", Font.PLAIN, 50);
			g.setFont(font);
			int x = width*19/20 -getwaterMarkLength(markContent, g);
			int y = height*9/10;
			g.drawString(markContent, x, y); // 添加水印的文字和设置水印文字出现的内容*/
			g.dispose();
			logger.info("图片高宽|"+width+"|"+height);
			FileOutputStream out = new FileOutputStream(filePath);
			ImageIO.write(bimage,extension,out);

			out.flush();
			out.close();

		} catch (Exception e) {
			return ;
		}
	}
	public int getwaterMarkLength(String watermarkContent,Graphics2D g) {

		return	g.getFontMetrics(g.getFont()).charsWidth(watermarkContent.toCharArray(), 0, watermarkContent.length());

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
}
