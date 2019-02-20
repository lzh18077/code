package com.zs.pms.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 * @author Administrator
 *
 */
@Controller
public class UploadController {
	@RequestMapping("/upload/common.do")
	@ResponseBody
	/**
	 * 普通文件上传
	 * @param 上传的文件=input名字
	 * @return 返回我呢间路径
	 */
	public String commonUpload(MultipartFile file,HttpServletRequest req) {
		//UUID算法生成前缀
		String pfix= UUID.randomUUID().toString();
		//生成文件名 前缀+原始文件名
		String filename=pfix+file.getOriginalFilename();
		//获得upload文件夹的物理地址
		String path=req.getRealPath("/upload");
		//创建目标文件  File.separator:分隔符
		File dfile=new File(path+File.separator+filename);
		try {
			//将上传的文件写入到目标文件
			file.transferTo(dfile);
			return filename;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
}
