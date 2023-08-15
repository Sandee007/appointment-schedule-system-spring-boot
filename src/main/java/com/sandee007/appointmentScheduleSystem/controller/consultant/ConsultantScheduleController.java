package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.dto.CreateScheduleDto;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.service.ConsultantScheduleDateService;
import com.sandee007.appointmentScheduleSystem.service.ConsultantScheduleDateTimeslotService;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("consultant/schedule")
public class ConsultantScheduleController {
    private TimeslotService timeslotService;
    private UtilThymeleaf utilThymeleaf;
    private ConsultantScheduleDateService consultantScheduleDateService;
    private ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService;

    public ConsultantScheduleController(
            TimeslotService timeslotService, UtilThymeleaf utilThymeleaf,
            ConsultantScheduleDateService consultantScheduleDateService,
            ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService
    ) {
        this.timeslotService = timeslotService;
        this.utilThymeleaf = utilThymeleaf;
        this.consultantScheduleDateService = consultantScheduleDateService;
        this.consultantScheduleDateTimeslotService = consultantScheduleDateTimeslotService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<ConsultantScheduleDate> consultantScheduleDates = consultantScheduleDateService.findAllByConsultant(
                utilThymeleaf.getConsultant());
        model.addAttribute("consultantScheduleDates", consultantScheduleDates);
        return "consultant/schedule/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        CreateScheduleDto createScheduleDto = new CreateScheduleDto();
        createScheduleDto.setSelectedDate(null);
        createScheduleDto.setTimeslotString(null);
        createScheduleDto.setId(0);

        model.addAttribute("createScheduleDto", createScheduleDto);

        return "consultant/schedule/create";
    }

    @PostMapping("store")
    public String store(
            @Valid CreateScheduleDto createScheduleDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws JsonProcessingException {

        if (bindingResult.hasErrors()) return "consultant/schedule/create";

        List<TimeSlot> timeslots = timeslotService.getTimeslotsByString(createScheduleDto.getTimeslotString());

        ConsultantScheduleDate consultantScheduleDate = new ConsultantScheduleDate();
        consultantScheduleDate.setDate(createScheduleDto.getSelectedDate());
        consultantScheduleDate.setConsultant(utilThymeleaf.getConsultant());
        consultantScheduleDate.setConsultantScheduleDateTimeslots(this.generateConsultantScheduleDateTimeslots(
                timeslots,
                consultantScheduleDate
        ));
        consultantScheduleDateService.save(consultantScheduleDate);

        redirectAttributes.addFlashAttribute("success", "Schedule Created");
        return "redirect:/consultant/dashboard";
    }

    @GetMapping("edit")
    public String edit(
            @RequestParam("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<ConsultantScheduleDate> consultantScheduleDate = consultantScheduleDateService.findById(id);

        if (consultantScheduleDate.isPresent()) {
            CreateScheduleDto createScheduleDto = new CreateScheduleDto();
            createScheduleDto.setSelectedDate(consultantScheduleDate.get().getDate());
            createScheduleDto.setId(id);

            model.addAttribute("createScheduleDto", createScheduleDto);
            model.addAttribute("consultantScheduleDate", consultantScheduleDate.get());
            model.addAttribute("MODE", "UPDATE");
            return "consultant/schedule/create";
        }
        redirectAttributes.addFlashAttribute("error", "Schedule Not Found");
        return "redirect:/consultant/dashboard";
    }

    @PostMapping("update")
    public String update(
            @Valid CreateScheduleDto createScheduleDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("MODE", "UPDATE");
            return "consultant/schedule/create";
        }

        List<TimeSlot> timeslots = timeslotService.getTimeslotsByString(createScheduleDto.getTimeslotString());

        Optional<ConsultantScheduleDate> consultantScheduleDate = consultantScheduleDateService.findById(
                createScheduleDto.getId()
        );
        consultantScheduleDate.ifPresent(c -> {
            //            * delete all existing consultant_schedule_date_timeslots
            //            ! seeker reserved slots won't be deleted, manual delete query is applied on the entity
            consultantScheduleDateTimeslotService.deleteAllByConsultantScheduleDate(c);

            //            * apply new appointment_schedule_system
            List<ConsultantScheduleDateTimeslot> pendingScheduleDateTimeslots = this.generateConsultantScheduleDateTimeslots(
                    timeslots,
                    c
            );

            //            * filter out if already existing
            pendingScheduleDateTimeslots.removeIf(
                    i -> consultantScheduleDateTimeslotService.existsByConsultantScheduleDateAndTimeslot(
                            c,
                            i.getTimeslot()
                    )
            );
            c.setConsultantScheduleDateTimeslots(pendingScheduleDateTimeslots);
            consultantScheduleDateService.save(c);
        });

        redirectAttributes.addFlashAttribute("success", "Schedule Updated");
        return "redirect:/consultant/dashboard";

    }

    public List<ConsultantScheduleDateTimeslot> generateConsultantScheduleDateTimeslots(
            List<TimeSlot> timeslots,
            ConsultantScheduleDate consultantScheduleDate
    ) {
        return timeslots.stream()
                        .map(i -> {
                            var t = new ConsultantScheduleDateTimeslot();
                            t.setConsultantScheduleDate(consultantScheduleDate);
                            t.setTimeslot(i);
                            return t;
                        })
                        .collect(Collectors.toList());
    }
}
