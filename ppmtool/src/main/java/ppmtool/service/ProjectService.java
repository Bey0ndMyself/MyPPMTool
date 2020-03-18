package ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppmtool.domain.Project;
import ppmtool.exception.ProjectIdException;
import ppmtool.repository.ProjectRepository;

import java.util.Objects;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project save(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("ProjectIdentifier '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    public Project findByProjectIdentifier(String projectIdentifier){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(Objects.isNull(project)){
            throw new ProjectIdException("ProjectIdentifier '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
        return project;
    }
}
