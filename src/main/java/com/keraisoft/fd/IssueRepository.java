package com.keraisoft.fd;

import org.springframework.data.jpa.repository.JpaRepository;
interface IssueRepository extends JpaRepository<Issue, Long> {
}
