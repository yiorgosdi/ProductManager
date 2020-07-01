package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List; 

@Controller 
public class AppController {
	
	@Autowired 
	private ProductService pService;
	
	@RequestMapping("/") 
	public String viewHomePage(Model model) {
		List<Product> listProducts = pService.listAll(); 
		model.addAttribute("listProducts", listProducts);  
		
		return "index"; 
	}
	
	@RequestMapping("/new") 
	public String showNewProductForm(Model model) throws Exception {
		Product product = new Product();
		model.addAttribute("product", product); 
		
		return "new_product";
	}
	
	//@RequestMapping(value = "/save", method = RequestMethod.POST)
	//@PostMapping(value = "/save")
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product,
			@RequestParam("imageFile") MultipartFile mFile) throws IOException {
		
		String imageFileName = StringUtils.cleanPath(mFile.getOriginalFilename()); 
		product.setProductphoto(imageFileName);  
		Product savedProduct = pService.save(product);    
		
		//System.out.println("image name: " + imageFileName.toString());	 
		//System.out.println("saved product: " + savedProduct.getId().toString());	
		
		String uploadDir = "./product-photos/" + savedProduct.getId(); // stores photo in project's folder 
		//String uploadDir = "/product-photos/" + savedProduct.getId(); // stores in C:\product-photos 
		
		//System.out.println("upload dir: " + uploadDir.toString());
		
		//Path uploadPath = Paths.get(uploadDir + "/" + imageFileName); 
		Path uploadPath = Paths.get(uploadDir); 
		//System.out.println("upload Path is the uploadDir ID: " + uploadPath.toString());
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath); 			
		}
		
		//System.out.println("created upload path: " + uploadPath);
		
		try (InputStream inStream = mFile.getInputStream()) { 
			Path filePath = uploadPath.resolve(imageFileName); // accessing path 
			//--System.out.println("tries to access - file path: " + filePath.toFile().getAbsolutePath());
			//System. out.println("imageFileName " + imageFileName.toString()); 
			Files.copy(inStream, filePath, StandardCopyOption.REPLACE_EXISTING);  
	
		} catch (IOException e) {
			throw new IOException("Could not save uploaded file" + imageFileName); 
		}		
		
		return "redirect:/"; 		
	}
	
	@RequestMapping("/edit/{id}") 
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView modelAndView =  new ModelAndView("edit_product");
		Product product = pService.get(id); 
		modelAndView.addObject("product", product); 
		
		return modelAndView;
	}
	
	@RequestMapping("/delete/{id}") 
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		pService.delete(id);
		
		return "redirect:/"; 		
	}
	
}
