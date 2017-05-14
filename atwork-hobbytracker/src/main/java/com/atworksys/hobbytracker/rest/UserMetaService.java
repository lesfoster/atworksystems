package com.atworksys.hobbytracker.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.atworksys.hobbytracker.middletier.UserCRUD;
import com.atworksys.hobbytracker.middletier.UserHobbyCRUD;
import com.atworksys.hobbytracker.middletier.UserphoneCRUD;
import com.atworksys.hobbytracker.middletier.UserroleCRUD;
import com.atworksys.hobbytracker.model.User;
import com.atworksys.hobbytracker.model.Userhobby;
import com.atworksys.hobbytracker.model.Userphone;

/*
 * Using the EJBs developed above develop a simple Rest API that includes the following methods signatures:
   public void addUser(User user);
   public void deletePhone(Long userId, String type,);
   public void updatePhone(Long userId, String type, String phone);
   public List<UserHobby> getHobbies(Long userId);
   public List<User> getUsers();
 */
@RequestScoped
@Path("/usermeta")
public class UserMetaService {
	private UserCRUD userCrud;
	private UserHobbyCRUD userHobbyCrud;
	private UserphoneCRUD userPhoneCrud;
	//private UserroleCRUD userRoleCrud;
	
	/**
	 * Establish all the EJB injection points.
	 * 
	 * @param userCrud
	 */
	@EJB
	public void setUserCRUD(UserCRUD userCrud) {
		this.userCrud = userCrud;
	}

	@EJB
	public void setUserHobbyCRUD(UserHobbyCRUD userHobbyCrud) {
		this.userHobbyCrud = userHobbyCrud;
	}

	@EJB
	private void setUserPhoneCrud(UserphoneCRUD userPhoneCrud) {
		this.userPhoneCrud = userPhoneCrud;
	}

	//@EJB
	//private void setUserRoleCrud(UserroleCRUD userRoleCrud) {
	//	this.userRoleCrud = userRoleCrud;
	//}

	/**
	 * Flesh out the exposed interface.
	 */
    @POST
	@Path("addUser")    
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user) {
    	//Response rtnVal = null;
    	try {
    		userCrud.addUser(user);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
	}
    
    @GET
    @Path("deletePhone/{user_id}/{type}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void deletePhone(@PathParam("user_id") Long userId, @PathParam("type") String type) {
    	try {
    		if (userId != null  &&  type != null) {
        		List<Userphone> userPhones = userPhoneCrud.getUserPhones();
        		for (Userphone phone: userPhones) {
        			if (type.equals(phone.getType())  &&  phone.getUser().getUserId().equals(userId)) {
                		userPhoneCrud.deleteUserPhone(phone);        				
        			}
        		}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @GET
    @Path("updatePhone/{user_id}/{type}/{phone_number}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void updatePhone(@PathParam("user_id") Long userId, @PathParam("type") String type, @PathParam("phone_number") String phone) {
    	try {
    		// We can set the phone number to empty/null, but need to find it.
    		if (userId != null  &&  type != null) {
    			for (Userphone userPhone: userPhoneCrud.getUserPhones()) { // TODO: add method to pull by user id.
    				// Avoid NPEs
    				if (phone == null   &&  (userPhone.getPhoneNumber() != null)) {
    					continue;
    				}
    				else if (phone != null  &&  userPhone.getPhoneNumber() == null) {
    					continue;
    				}
    				
    				// Takes both-numbers-are-null or both are equal into account
    				if (type.equals(userPhone.getType())  &&
    				    (phone == userPhone.getPhoneNumber() ||
    				     phone.equals(userPhone.getPhoneNumber()) ) ) {
    					userPhoneCrud.deleteUserPhone(userPhone);
    				}
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    @GET
    @Path("getHobbies/{user_id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Userhobby> getHobbies(@PathParam("user_id") Long userId) {
    	try {
    		List<Userhobby> rtnVal = new ArrayList<>();
    		if (userId != null) {
        		for (Userhobby hobby: userHobbyCrud.getUserHobbies()) {   // TODO: add method to pull by user id.
        			if (hobby.getUser().getUserId().equals(userId)) {
        				rtnVal.add(hobby);
        			}
        		}
    		}
    		return rtnVal;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    @GET
    @Path("getUsers")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	try {
    		return userCrud.getUsers();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
}