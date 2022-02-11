package hu.agilexpert.axtracker.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WorkLogEntry {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String username;

	@Column
	private String description;

	@Column
	private Date targetDate;

	@Column
	private boolean isDone;
}
