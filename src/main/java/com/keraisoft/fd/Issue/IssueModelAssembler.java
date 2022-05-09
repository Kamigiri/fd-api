package com.keraisoft.fd.Issue;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class IssueModelAssembler implements RepresentationModelAssembler<Issue, EntityModel<Issue>> {

    @Override
    public EntityModel<Issue> toModel(Issue issue) {
        return  EntityModel.of(issue,
                linkTo(methodOn(IssueController.class).one(issue.getId())).withSelfRel(),
                linkTo(methodOn(IssueController.class).all()).withSelfRel());
    }
}
