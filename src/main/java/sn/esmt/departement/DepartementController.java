package sn.esmt.departement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sn.esmt.employee.Employee;

import java.util.List;

@Controller
public class DepartementController {

    @Autowired
    private DepartementRpository repo;

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Departement> listDepartements = repo.findAll();
        model.addAttribute("listDepartements", listDepartements);

        return "categories";
    }

    @GetMapping("/categories/new")
    public String showCategoryNewForm(Model model) {
        model.addAttribute("departement", new Departement());

        return "category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Departement departement) {
        repo.save(departement);

        return "redirect:/categories";
    }

    @GetMapping("/departement/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Integer id, Model model) {
        Departement departement = repo.findById(id).get();
        model.addAttribute("departement", departement);


        return "category_form";
    }

    @GetMapping("/departement/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, Model model) {

        repo.deleteById(id);

        return "redirect:/categories";
    }
}
