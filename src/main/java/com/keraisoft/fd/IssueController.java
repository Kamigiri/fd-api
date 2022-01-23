package com.keraisoft.fd;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {
    private final IssueRepository repository;

    IssueController(IssueRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/issues")
    List<Issue> all() {
        return  repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/issue")
    Issue newIssue(@RequestBody Issue newIssue) {
        return repository.save(newIssue);
    }

    @PutMapping("/issue/{id}")
    Issue replaceIssue(@RequestBody Issue newIssue, @PathVariable Long id) {
        return repository.findById(id).map( issue -> {
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
    }

    @DeleteMapping("/issue/{id}")
    void deleteIssue(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
