package com.atworksys.hobbytracker.middletier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.atworksys.hobbytracker.model.User;
import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * EJB for CRUD operations against the user model entity.
 * 
 * @author Leslie L Foster
 */
@Stateless
public class UserCRUD {
	private EntityManager em;
	
    @PersistenceContext(unitName="primary")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    /**
     * Fetch all users.
     * 
     * @return
     */
    public List<User> getUsers() {
    	return em.createNamedQuery(USER_ALL_QRY, User.class).getResultList();
    }
    
    /**
     * Add a user.
     * 
     * @param user must have username.
     * @throws CRUDArgumentException if null, or username is null.
     */
    public User addUser(User user) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(user);
    	CRUDArgumentException.throwIfNull(user.getUsername());
    	    	
    	if (userExists(user)) {
    		throw new CRUDArgumentException("User by username " + user.getUsername() + " already exists.");
    	}
    	
    	User rtnVal = em.merge(user);
    	em.flush();
    	
    	return rtnVal;
    }

    /**
     * Modify an existing user.
     * 
     * @param user must have username.
     * @throws CRUDArgumentException if null, or username is null.
     */
    public void updateUser(User user) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(user);
    	CRUDArgumentException.throwIfNull(user.getUsername());
    	
    	// user should first exist, prior to this operation.
    	em.persist(user);
    	em.flush();
    }
    
    /**
     * Attempt to remove a user from database.
     * 
     * @param user which to be deleted.
     * @throws CRUDArgumentException
     */
    public void deleteUser(User user) throws CRUDArgumentException {
    	// Only care if there is no id.
    	CRUDArgumentException.throwIfNull(user);
    	try {
    		em.remove(user);
    		em.flush();
    	} catch (IllegalArgumentException iae) {
    		throw new CRUDArgumentException(iae.getMessage());
    	}
    }
    
    private boolean userExists(User user) {
    	List<User> resultList = em.createNamedQuery(USER_BY_UNAME_QRY, User.class)
    			.setParameter("u",  user.getUsername())
    			.getResultList();
		return resultList != null  &&  resultList.size() > 0;
    }
}
