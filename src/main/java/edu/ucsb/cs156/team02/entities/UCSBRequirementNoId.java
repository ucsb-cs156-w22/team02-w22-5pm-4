package edu.ucsb.cs156.team02.entities;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ucsb_requirement_no_id")
public class UCSBRequirementNoId {
  @Id
  public String requirementCode;
  public String requirementTranslation;
  public String collegeCode;
  public String objCode;
  public int courseCount;
  public int units;
  public boolean inactive;
} 
