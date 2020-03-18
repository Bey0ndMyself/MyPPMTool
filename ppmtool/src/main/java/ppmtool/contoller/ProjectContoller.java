package ppmtool.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ppmtool.domain.Project;
import ppmtool.service.ProjectService;
import ppmtool.service.ValidationService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/project")
public class ProjectContoller {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ValidationService validationService;

    @PostMapping
    public ResponseEntity createProject(@Valid @RequestBody Project project, BindingResult result) {
        Optional<ResponseEntity> errors = validationService.validateFieldsErrors(result);
        if (errors.isPresent()) {
            return errors.get();
        }

        Project savedProject = projectService.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity getProjectById(@PathVariable String projectId) {
        Project project = projectService.findByProjectIdentifier(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
