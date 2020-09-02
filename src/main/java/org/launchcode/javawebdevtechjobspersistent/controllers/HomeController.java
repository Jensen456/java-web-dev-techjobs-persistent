package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController<employerRepository, skillRepository, jobRepository> {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    public EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        Employer employer = employerRepository.findById(employerId).orElse(new Employer());
        newJob.setSkills(skillObjs);
        jobRepository.save(newJob);
        //model.addAttribute("employer", employerRepository.findById(employerId));
        //model.addAttribute("skills", skillRepository.findById(skills));
        //model.addAttribute("jobs", newJob);
        //model.addAttribute(new SkillRepository());

       // if (errors.hasErrors()) {
       //     model.addAttribute("title", "Add Job");
       //     return "add";
       // }

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
    Optional result = jobRepository.findById(jobId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Job ID: " + jobId);
        } else {
            Job job = (Job) result.get();
            //SkillRepositroy SkillRepository = new SkillRepository();
            //JobSkillDTO.getJob(job);
            //skill skills = jobSkillsDTO.getSkill();

            model.addAttribute("job", job);
            //model.addAttribute("skills", skills);
        }

        return "view";
    }

}
