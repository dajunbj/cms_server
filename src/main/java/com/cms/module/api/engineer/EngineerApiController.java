package com.cms.module.api.engineer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.api.engineer.entity.EngineerSharedInput;
import com.cms.module.api.engineer.entity.EngineerSharedOutput;

/**
 * http://localhost:8080/api/engineer/shared?companyId=123&skill=Java&availability=2025-02-15&location=Tokyo
 **/
@RestController
@RequestMapping("/api")
public class EngineerApiController {

    @Autowired
    private EngineerService service;

    // 技術者共有API
    @GetMapping("/engineer/shared")
    public EngineerSharedOutput getTechnicians(EngineerSharedInput input) {
        return service.getSharedEngineer(input);
    }

}