package com.keraisoft.fd;

public class IssueNotFoundException extends  RuntimeException{
    IssueNotFoundException(Long id) {
        super("Could not find Issue " + id);
    }
}
