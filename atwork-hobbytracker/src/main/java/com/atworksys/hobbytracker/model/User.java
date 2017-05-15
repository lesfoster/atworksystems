package com.atworksys.hobbytracker.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries(
		value={
				@NamedQuery(name=USER_ALL_QRY, query="SELECT u FROM User u"),
				@NamedQuery(name=USER_BY_UNAME_QRY, query="SELECT u from User u where u.username=:u")
		}
)

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	private String email;

	private String password;

	private String username;

	//bi-directional many-to-one association to Userhobby
	@OneToMany(mappedBy="user")
	private List<Userhobby> userhobbies;

	//bi-directional many-to-one association to Userphone
	@OneToMany(mappedBy="user")
	private List<Userphone> userphones;

	//bi-directional many-to-one association to Userrole
	@OneToMany(mappedBy="user")
	private List<Userrole> userroles;

	public User() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Userhobby> getUserhobbies() {
		return this.userhobbies;
	}

	public void setUserhobbies(List<Userhobby> userhobbies) {
		this.userhobbies = userhobbies;
	}

	public Userhobby addUserhobby(Userhobby userhobby) {
		getUserhobbies().add(userhobby);
		userhobby.setUser(this);

		return userhobby;
	}

	public Userhobby removeUserhobby(Userhobby userhobby) {
		getUserhobbies().remove(userhobby);
		userhobby.setUser(null);

		return userhobby;
	}

	public List<Userphone> getUserphones() {
		return this.userphones;
	}

	public void setUserphones(List<Userphone> userphones) {
		this.userphones = userphones;
	}

	public Userphone addUserphone(Userphone userphone) {
		getUserphones().add(userphone);
		userphone.setUser(this);

		return userphone;
	}

	public Userphone removeUserphone(Userphone userphone) {
		getUserphones().remove(userphone);
		userphone.setUser(null);

		return userphone;
	}

	public List<Userrole> getUserroles() {
		return this.userroles;
	}

	public void setUserroles(List<Userrole> userroles) {
		this.userroles = userroles;
	}

	public Userrole addUserrole(Userrole userrole) {
		getUserroles().add(userrole);
		userrole.setUser(this);

		return userrole;
	}

	public Userrole removeUserrole(Userrole userrole) {
		getUserroles().remove(userrole);
		userrole.setUser(null);

		return userrole;
	}

}