package edu.ucsb.cs156.team02.entities;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ucsb_requirements")
public class UCSBRequirement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String requirementCode;
  private String requirementTranslation;
  private String collegeCode;
  private String objCode;
  private int courseCount;
  private int units;
  private boolean inactive;

  public UCSBRequirement (long idInput, UCSBRequirementNoId t) {
      id = idInput;
      requirementCode = t.requirementCode;
      requirementTranslation = t.requirementTranslation;
      collegeCode = t.collegeCode;
      objCode = t.objCode;
      courseCount = t.courseCount;
      units = t.units;
      inactive = t.inactive;
  
  }
  
}
