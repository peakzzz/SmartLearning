package edu.sjsu.assess.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Niveditha
 *
 */

@Controller
@RequestMapping("/recommendation")
public class RecommendationControllerImpl implements RecommendationController{

	@Override
	@RequestMapping(value = "/view/{moduleName}/{fileName}", method = RequestMethod.GET)
	public String downloadFile(HttpServletResponse response, Model model,@PathVariable("moduleName") String moduleName,@PathVariable("fileName") String name) {

		String fileName = "file/material/"+moduleName+"/"+name+".pptx";
		ClassLoader classLoader = getClass().getClassLoader();
		URL filenameee = classLoader.getResource(fileName);
		File file = new File(classLoader.getResource(fileName).getFile());
		
		if(!file.exists()){
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		try {

			String mimeType= URLConnection.guessContentTypeFromName(file.getName());
			if(mimeType==null){
				System.out.println("mimetype is not detectable, will take default");
				mimeType = "application/octet-stream";
			}

			System.out.println("mimetype : "+mimeType);

			response.setContentType(mimeType);

			/* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
                while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));


			/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
			//response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

			response.setContentLength((int)file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			//Copy bytes from source to destination(outputstream in this example), closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());



		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
