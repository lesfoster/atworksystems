package com.atworksys.hobbytracker.model;

import java.io.Serializable;
import javax.persistence.*;

import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * The persistent class for the userrole database table.
 * 
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name=USERROLE_ALL_QRY, query="SELECT u FROM Userrole u"),
		@NamedQuery(name=USERROLE_NAME_QRY, query="SELECT u FROM Userrole u WHERE u.rolename=:n AND u.user=:u")
	}
)

public class Userrole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private String roleId;

	private String rolename;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Userrole() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}