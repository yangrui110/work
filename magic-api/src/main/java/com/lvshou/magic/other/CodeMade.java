package com.lvshou.magic.other;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.AdvertiseService;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.QrCodeUtils;

@Controller
@RequestMapping("code")
public class CodeMade {
	
	@Autowired
	private AdvertiseService advertiseService;
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@GetMapping("createCode")
	public Result createShareCode(@RequestParam(value="userId")String userId) throws Exception {
		String prefixString="D://FileUpload/";
		Advertise advertise=advertiseService.findByType(2, 0, 10).get(0);
		User user=userService.findUser(new User().setId(userId));
		if(advertise==null||user==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		String path=null;
		if(user.getShareCode()==null) {
			path=MainUUID.getUUID()+".jpg";
		}else {
			path=user.getShareCode();
		}
		
		String background=advertise.getPics().substring(advertise.getPics().lastIndexOf("/"));
		System.out.println(background+"-----"+path);
		QrCodeUtils.createQrBackground(prefixString+background, prefixString+user.getQrcode(),user.getName(), prefixString+path);
		UserVo vo=new UserVo();
		User user2=new User().setId(userId).setShareCode(path);
		BeanUtils.copyProperties(user2,vo);
		userService.update(vo);
		return new Result<>(ResultEnum.OK,path);
	}
}
