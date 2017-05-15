package com.atworksys.hobbytracker.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * The persistent class for the userhobby database table.
 * 
 */
@Entity
@NamedQueries(
		value={
				@NamedQuery(name=USERHOBBY_ALL_QRY, query="SELECT u FROM Userhobby u"),
				@NamedQuery(name=USERHOBBY_BY_ID_QRY, query="SELECT u FROM Userhobby u where u.id=:i"),
				@NamedQuery(name=USERHOBBY_BY_UID_NAME_QRY, query="SELECT u FROM Userhobby u where u.user=:u AND u.hobby=:h")
		}
)

public class Userhobby implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	private String hobby;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Userhobby() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}