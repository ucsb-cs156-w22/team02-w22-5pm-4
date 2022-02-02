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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ucsb_subjects")
public class UCSBSubject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // This establishes that many UCSBSubjects can belong to one user
  // Only the user_id is stored in the table, and through it we
  // can access the user's details

  // @ManyToOne
  // @JoinColumn(name = "user_id")
  
  private boolean inactive;
  private String subjectCode;
  private String subjectTranslation;
  private String deptCode;
  private String collegeCode;
  private String relatedDeptCode;
}