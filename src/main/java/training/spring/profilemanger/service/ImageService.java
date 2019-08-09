package training.spring.profilemanger.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

	public void storeImage(MultipartFile image, HttpServletRequest request) {
		String filePath = request.getServletContext().getRealPath("/static/user_images/");
		try {
			image.transferTo(new File(filePath + image.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
