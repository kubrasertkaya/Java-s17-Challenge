package com.workintech.course.controller;

import com.workintech.course.entity.Course;
import com.workintech.course.entity.CourseGpa;
import com.workintech.course.exceptions.CourseException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa lowGpa;
    private CourseGpa mediumGpa;
    private CourseGpa highGpa;


    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumGpa,
                            @Qualifier("highCourseGpa") CourseGpa highGpa) {
        this.lowGpa = lowGpa;
        this.mediumGpa = mediumGpa;
        this.highGpa = highGpa;
    }

    @PostConstruct
    public void init(){
           courses=new ArrayList<>();

    }

    @GetMapping("/")
    public List<Course> get(){
        return courses;
    }

    @GetMapping("/{name}")
    public Course getById(@PathVariable String name){
       List<Course> foundCourses= courses.stream().filter(course -> course.getName().equals(name)).
                collect(Collectors.toList());

       if(foundCourses.size()==0){
          throw new CourseException("Name is not valid", HttpStatus.BAD_REQUEST);
       }
       return foundCourses.get(0);
    }

    @PostMapping("/")
    public Course save(@RequestBody Course course){
        //TODO course is valid check;
        courses.add(course);
        return course;

    }

    @PutMapping("/{id}")
    public Course update(@RequestBody Course course,@PathVariable int id){
        //TODO is valis id?
        Optional<Course> foundCourse=  courses.stream().filter(c ->c.getId()==id ).findFirst();
        if(foundCourse.isPresent()){
            int index=courses.indexOf(foundCourse.get());
            courses.set(index,course);
            return course;
        }else{
            //TODO throw expection
            return  null;
        }
    }

    @DeleteMapping("/{id}")
    public Course delete(int id){
        Optional<Course> foundCourse=  courses.stream().filter(c ->c.getId()==id ).findFirst();
        if(foundCourse.isPresent()){
            int index=courses.indexOf(foundCourse.get());
            courses.remove(index);
            return foundCourse.get();
            }  else {
            //TODO throw expection
            return null;
        }

    }










}
