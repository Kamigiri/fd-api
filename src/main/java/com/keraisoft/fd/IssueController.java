package com.keraisoft.fd;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/issues")
    CollectionModel<EntityModel<Issue>> all() {
        List<EntityModel<Issue>> issues = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return  CollectionModel.of(issues, linkTo(methodOn(IssueController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/issue")
    ResponseEntity<?>newIssue(@RequestBody Issue newIssue) {
        EntityModel<Issue> issueModel = assembler.toModel(repository.save(newIssue));

        return  ResponseEntity.created(issueModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(issueModel);
    }

    @GetMapping("issue/{id}")
    EntityModel<Issue> one(@PathVariable Long id) {
        Issue issue = repository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));

        return assembler.toModel(issue);
    }

    @PutMapping("/issue/{id}")
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

    @DeleteMapping("/issue/{id}")
    ResponseEntity<?> deleteIssue(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
