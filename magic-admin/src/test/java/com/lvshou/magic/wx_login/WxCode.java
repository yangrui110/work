package com.lvshou.magic.wx_login;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.zxing.WriterException;
import com.lvshou.magic.util.QrCodeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxCode {

	@Test
	public void createCode() {
		try {
			QrCodeUtils.createQrBackground("D:\\veer-100586530.jpg", "D:\\qrcode.jpg", "D:\\out.jpg", 3);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
