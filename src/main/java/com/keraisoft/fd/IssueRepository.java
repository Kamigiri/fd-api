package com.keraisoft.fd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("SELECT DISTINCT type from Issue" )
    public List<String> types();

    @Query("SELECT DISTINCT origin from Issue" )
    public List<String> origins();
}
