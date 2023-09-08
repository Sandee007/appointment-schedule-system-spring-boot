package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.Role;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.security.ERole;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.base.auth.service.EmailService;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.CountryService;
import com.sandee007.appointmentScheduleSystem.service.IndustryService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("admin/consultant")
public class ConsultantCrudController {
    private UserService userService;
    private EmailService emailService;
    private Environment environment;
    private CountryService countryService;
    private ConsultantService consultantService;
    private IndustryService industryService;

    public ConsultantCrudController(
            UserService userService, EmailService emailService, Environment environment,
            CountryService countryService,
            ConsultantService consultantService,
            IndustryService industryService
    ) {
        this.userService = userService;
        this.emailService = emailService;
        this.environment = environment;
        this.countryService = countryService;
        this.consultantService = consultantService;
        this.industryService = industryService;
    }

    //    ! upgraded as a ControllerAdvice
    //    @InitBinder
    //    public void initBinder(WebDataBinder webDataBinder) {
    //        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    //        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    //    }

    @GetMapping("create")
    public String create(Model model) {

        Consultant consultant = new Consultant();
        consultant.setFirstname(null);
        consultant.setLastname(null);
        consultant.setCountries(new ArrayList<>());

        User user = new User();
        user.setConsultant(consultant);
        model.addAttribute("user", user);

        //        * props
        model.addAttribute("propCountries", countryService.findAll());
        model.addAttribute("propIndustries", industryService.findAll());

        return "admin/crud/consultant/create";
    }

    //    * REDIRECT ATTRIBUTES
    //    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/RedirectAttributes.html
    @PostMapping("store")
    public String store(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            // ! important - RequestParam must be below binding result  argument , required must be false
            @RequestParam(value = "countries", required = false) String countriesString,
            @RequestParam(value = "industries", required = false) String industriesString,
            Model model
    ) {

        if (user.getConsultant().getFirstname() == null) {
            bindingResult.rejectValue(
                    "consultant.firstname", "error.consultant.firstname", ValidationMessages.REQUIRED
            );
        }
        if (user.getConsultant().getLastname() == null) {
            bindingResult.rejectValue(
                    "consultant.lastname", "error.consultant.lastname", ValidationMessages.REQUIRED
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("propCountries", countryService.findAll());
            model.addAttribute("propIndustries", industryService.findAll());
            model.addAttribute("countriesString", countriesString);
            return "admin/crud/consultant/create";
        }
//
//        //        * role
        Role role = new Role(ERole.ROLE_CONSULTANT.name());
        role.setUser(user);
        user.setRole(role);


        //        * consultant
        Consultant consultant = user.getConsultant();
        consultant.setUser(user);
        consultant.setCountries(this.getCountriesList(countriesString));
        consultant.setIndustries(this.getIndustriesList(industriesString));

        //        * gen password
        String password = RandomStringUtils.randomAlphanumeric(8);
        user.setUsername(user.getUsername() + consultantService.generateConsultantEmailDomain());
        user.setPassword(password);
        userService.save(user);

        //        * send mail
        emailService.sendMailConsultantAccountCreated(user, password);

        redirectAttributes.addFlashAttribute("success", "Consultant Created.");
        return "redirect:/admin/dashboard";
    }


    @GetMapping("edit")
    public String edit(
            @RequestParam("id") int id,
            Model model
    ) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());

            //        * props
            model.addAttribute("propCountries", countryService.findAll());
            model.addAttribute("propIndustries", industryService.findAll());

            model.addAttribute("MODE", "UPDATE");
            return "admin/crud/consultant/create";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("update")
    public String update(
            @ModelAttribute("user") User user, // ! not using model validations here - creates problems
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "countries", required = false) String countriesString,
            @RequestParam(value = "industries", required = false) String industriesString,
            Model model

    ) {
        if (user.getConsultant().getFirstname() == null) {
            bindingResult.rejectValue(
                    "consultant.firstname", "error.consultant.firstname", ValidationMessages.REQUIRED
            );
        }
        if (user.getConsultant().getLastname() == null) {
            bindingResult.rejectValue(
                    "consultant.lastname", "error.consultant.lastname", ValidationMessages.REQUIRED
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("propCountries", countryService.findAll());
            model.addAttribute("propIndustries", industryService.findAll());
            model.addAttribute("countriesString", countriesString);
            model.addAttribute("MODE", "UPDATE");
            return "admin/crud/consultant/create";
        }

        //        * update consultants table
        Optional<User> tempUser = userService.findById(user.getId());
        tempUser.ifPresent(u -> {
            Optional<Consultant> consultant = Optional.ofNullable(consultantService.findByUser(u));
            consultant.ifPresent(c -> {
                c.setFirstname(user.getConsultant().getFirstname());
                c.setLastname(user.getConsultant().getLastname());
                c.setCountries(this.getCountriesList(countriesString));
                c.setIndustries(this.getIndustriesList(industriesString));
                consultantService.save(c);
            });
        });

        redirectAttributes.addFlashAttribute("success", "Consultant Updated");
        return "redirect:/admin/dashboard";
    }

    private List<Country> getCountriesList(String countriesString) {
        //        * consultant countries
        //       ! PARSE JSON STRINGIFY DATA
        //        https://www.baeldung.com/jackson-object-mapper-tutorial
        List<Country> tempCountries = new ArrayList<>();
        if (countriesString != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                tempCountries = objectMapper.readValue(countriesString, new TypeReference<List<Country>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //        ! My solution for Detached Entity Passed to Persist
        //       TODO look for alternatives
        List<Country> countries = new ArrayList<>();
        tempCountries.forEach(country -> {
            Optional<Country> c = countryService.findById(country.getId());
            c.ifPresent(countries::add);
        });

        return countries;
    }

    private List<Industry> getIndustriesList(String industriesString) {
        List<Industry> tempIndustries = new ArrayList<>();
        if (industriesString != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                tempIndustries = objectMapper.readValue(industriesString, new TypeReference<List<Industry>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        List<Industry> industries = new ArrayList<>();
        tempIndustries.forEach(industry -> {
            Optional<Industry> c = industryService.findById(industry.getId());
            c.ifPresent(industries::add);
        });

        return industries;
    }

    //    * function generic types
    //    https://stackoverflow.com/questions/18276285/method-returning-dynamic-type-in-java
//    public <T, Service> List<T> getDynamicList(
//            Class<T> entity,
//            String string,
//            Class<Service> service,
//            Callable<T> findById
//    ) throws Exception {
//        List<T> tempList = new ArrayList<>();
//        if (string != null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                tempList = objectMapper.readValue(string, new TypeReference<List<T>>() {
//                });
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        List<T> list = new ArrayList<>();
//        for (T i : tempList) {
//            Optional<T> optional = (Optional<T>) findById.call();
//            optional.ifPresent(list::add);
//        }
//        return list;
//    }
}
