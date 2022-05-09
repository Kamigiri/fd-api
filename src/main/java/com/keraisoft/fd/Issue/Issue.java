package com.keraisoft.fd.Issue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue {
    private @Id @GeneratedValue Long id;
    private String name;
    private String type;
    private BigDecimal price;
    private String origin;
    private Date recordedDate;


    Issue(String name, String type, BigDecimal price, String origin, Date recordedDate) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.origin = origin;
        this.recordedDate = recordedDate;
    }

    public Issue() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id.equals(issue.id) && name.equals(issue.name) && type.equals(issue.type) && price.equals(issue.price) && origin.equals(issue.origin) && recordedDate.equals(issue.recordedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, price, origin, recordedDate);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", recordedDate=" + recordedDate +
                '}';
    }
}
