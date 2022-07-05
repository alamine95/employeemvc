package sn.esmt.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sn.esmt.departement.Departement;
import sn.esmt.departement.DepartementRpository;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartementRpository departementRpository;

    @GetMapping("/products/new")
    public String showNewProductForm(Model model) {
        List<Departement> listDepartements = departementRpository.findAll();

        model.addAttribute("employee", new Employee());
        model.addAttribute("listDepartements", listDepartements);

        return "product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Employee employee) {
        employeeRepository.save(employee);

        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        //List<Employee> listEmployees = employeeRepository.findAll();
        //model.addAttribute("listEmployees", listEmployees);
        //return "products";
        return findPaginated(1, model);
    }

    @GetMapping("products/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("employee", employee);

        List<Departement> listDepartements = departementRpository.findAll();

        model.addAttribute("listDepartements", listDepartements);

        return "product_form";
    }

    @GetMapping("products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model) {

        employeeRepository.deleteById(id);

        return "redirect:/products";
    }

    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.employeeRepository.findAll(pageable);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 6;
        Page<Employee> page = findPaginated(pageNo, pageSize);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);

        return "products";
    }

    //Get employees by keyword
    public List<Employee> findByKeyword(String keyword) {
        return employeeRepository.findByKeyword(keyword);
    }

    @GetMapping("/search")
    public String searchEmployees(Model model, String keyword) {
        model.addAttribute("employees", findByKeyword(keyword));

        return "search";
    }
}
