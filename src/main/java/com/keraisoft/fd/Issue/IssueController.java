package com.keraisoft.fd.Issue;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class IssueController {
    private final IssueRepository repository;
    private final IssueModelAssembler assembler;

    IssueController(IssueRepository repository, IssueModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/issues")
    public String listAll (Model model) {
        List<Issue> listIssues = repository.findAll();
        model.addAttribute("listIssues", listIssues);
        return "issues";
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/issues")
    CollectionModel<EntityModel<Issue>> all() {
        List<EntityModel<Issue>> issues = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return  CollectionModel.of(issues, linkTo(methodOn(IssueController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @GetMapping("/api/issue/types")
    List<String> allTypes() {

        return repository.types();
    }

    @GetMapping("/api/issue/origins")
    List<String> allOrigins() {

        return repository.origins();
    }

    @PostMapping("/api/issue")
    ResponseEntity<?>newIssue(@RequestBody Issue newIssue) {
        EntityModel<Issue> issueModel = assembler.toModel(repository.save(newIssue));

        return  ResponseEntity.created(issueModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(issueModel);
    }

    @GetMapping("/api/issue/{id}")
    EntityModel<Issue> one(@PathVariable Long id) {
        Issue issue = repository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));

        return assembler.toModel(issue);
    }

    @PutMapping("/api/issue/{id}")
    ResponseEntity<?> replaceIssue(@RequestBody Issue newIssue, @PathVariable Long id) {

        Issue updatedIssue = repository.findById(id).map( issue -> {
            issue.setName(newIssue.getName());
            issue.setType(newIssue.getType());
            issue.setPrice(newIssue.getPrice());
            issue.setOrigin(newIssue.getOrigin());
            issue.setRecordedDate(newIssue.getRecordedDate());
            return repository.save(issue);
        }).orElseGet(() -> {
            newIssue.setId(id);
            return  repository.save(newIssue);
        });

        EntityModel<Issue> issueModel = assembler.toModel(updatedIssue);
        return ResponseEntity.created(issueModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(issueModel);
    }

    @DeleteMapping("/api/issue/{id}")
    ResponseEntity<?> deleteIssue(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
