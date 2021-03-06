package edu.ucsb.cs56.ucsb_open_lab_scheduler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import edu.ucsb.cs56.ucsb_open_lab_scheduler.entities.Tutor;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.entities.TutorAssignment;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.entities.CourseOffering;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.repositories.TutorAssignmentRepository;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.repositories.TutorRepository;
import edu.ucsb.cs56.ucsb_open_lab_scheduler.repositories.CourseOfferingRepository;

import edu.ucsb.cs56.ucsb_open_lab_scheduler.advice.AuthControllerAdvice;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Controller
public class QuarterController {

  private Logger logger = LoggerFactory.getLogger(TutorAssignmentController.class);
  private final TutorRepository tutorRepository;
  private final CourseOfferingRepository courseOfferingRepository;
  private final TutorAssignmentRepository tutorAssignmentRepository;

  @Autowired
  private AuthControllerAdvice authControllerAdvice;

  public QuarterController(TutorAssignmentRepository tutorAssignmentRepository, TutorRepository tutorRepository,
  CourseOfferingRepository courseOfferingRepository) {
    this.tutorAssignmentRepository = tutorAssignmentRepository;
    this.tutorRepository = tutorRepository;
    this.courseOfferingRepository = courseOfferingRepository;
  }

  @GetMapping("/quarter")
  public String dashboard(Model model, OAuth2AuthenticationToken token, RedirectAttributes redirAttrs) {
    String role = authControllerAdvice.getRole(token);
    if (!(role.equals("Admin"))) {
      redirAttrs.addFlashAttribute("alertDanger", "You do not have permission to access that page");
      return "redirect:/";
    }
      
    Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
    model.addAttribute("courseOfferings",  courseOfferings);
    Vector<String> quarters = new Vector();
    for (CourseOffering course : courseOfferings){
          if(!(quarters.contains(course.getQuarter()))){
              quarters.add(course.getQuarter());
          }
    }
    model.addAttribute("quarters", quarters);
    return "quarter/quarter";
  }

  @GetMapping("/quarter/{quarter}/courseLeads")
  public String showCourseLeads(@PathVariable("quarter") String quarter, Model model, OAuth2AuthenticationToken token,
      RedirectAttributes redirAttrs) {
    String role = authControllerAdvice.getRole(token);
    if (!(role.equals("Admin"))) {
      redirAttrs.addFlashAttribute("alertDanger", "You do not have permission to access that page");
      return "redirect:/";
    }
    
    Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
    ArrayList<CourseOffering> courseOfferingsInQuarter = new ArrayList();
      
    for (CourseOffering course : courseOfferings){
        if((course.getQuarter().equals(quarter))){
            courseOfferingsInQuarter.add(course);
        }
    }
    
    if (courseOfferingsInQuarter.size() <= 0) {
      redirAttrs.addFlashAttribute("alertDanger", "Course Lead in Quarter  " + quarter + " not found");
      return "redirect:/";
    }
    

      //ArrayList<TutorAssignment> tutorAssignments = new ArrayList();
      HashMap<CourseOffering,String> courseLeads = new HashMap<CourseOffering,String> ();
      
      for(CourseOffering course: courseOfferingsInQuarter){
        List<TutorAssignment> tA = tutorAssignmentRepository.findByCourseOffering(course);
          boolean hasCourseLead = false;
          String courseLeadsString = "";
        for(TutorAssignment tutorAssigned : tA){
            if(tutorAssigned.getIsCourseLead()){
                //tutorAssignments.add(tutorAssigned);
                courseLeadsString += tutorAssigned.getTutor().getFirstName() + " " + tutorAssigned.getTutor().getLastName() + ", ";
                hasCourseLead = true;
            }
        }
          
          if(hasCourseLead){
              
              courseLeads.put(course, courseLeadsString.substring(0, courseLeadsString.length()-2));
          }else{
              courseLeads.put(course,"no course leads");
          }
      }
    
    model.addAttribute("courseOfferingsInQuarter", courseOfferingsInQuarter);
    model.addAttribute("courseLeads", courseLeads);
    
    
    

    return "quarter/courseLeads";
  }
                           

}

