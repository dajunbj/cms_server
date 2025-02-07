package com.cms.module.api.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.api.projects.entity.ProjectSharedInput;
import com.cms.module.api.projects.entity.ProjectSharedOutput;

@RestController
@RequestMapping("/api")
public class ProjectApiController {


    @Autowired
    private ProjectService projectService;


    // プロジェクト共有API
    @GetMapping("/projects/shared")
    public ProjectSharedOutput getProjects(ProjectSharedInput input) {
        return projectService.getProjects(input);
    }
}