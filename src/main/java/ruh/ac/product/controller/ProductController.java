package ruh.ac.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ruh.ac.product.entity.Product;
import ruh.ac.product.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired // for constructor injection
    private ProductService productService;

    @RequestMapping
    public String viewHomePage(Model model) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "index";
    }

    @RequestMapping("/new")
    public String showProductById(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "new_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit{id}")
    public ModelAndView editProduct(@PathVariable(name = "id") int id){
        ModelAndView naw = new ModelAndView("edit_product");
        Product product = productService.getProductById((long) id);
        naw.addObject("product",product);
        return naw;
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id){
        productService.delete(id);
        return "redirect:/";
    }

}
