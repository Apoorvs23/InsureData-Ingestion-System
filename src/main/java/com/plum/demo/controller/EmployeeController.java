package com.plum.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"api/employees", "api/v1/employees"})
public class EmployeeController extends BaseController {

}
