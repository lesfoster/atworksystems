package com.atworksys.hobbytracker.middletier;

import static com.atworksys.hobbytracker.common.CommonUtil.USERHOBBY_ALL_QRY;
import static com.atworksys.hobbytracker.common.CommonUtil.USERHOBBY_BY_ID_QRY;
import static com.atworksys.hobbytracker.common.CommonUtil.USERHOBBY_BY_UID_NAME_QRY;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.atworksys.hobbytracker.model.User;
import com.atworksys.hobbytracker.model.Userhobby;

@Stateless
public class UserHobbyCRUD {
	private EntityManager em;
	
    @PersistenceContext(unitName="primary")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }	
	
	/**
	 * Fetch all user-hobby entities.
	 * 
	 * @return exhaustive list.
	 */
	public List<Userhobby> getUserHobbies() {
    	return em.createNamedQuery(USERHOBBY_ALL_QRY, Userhobby.class).getResultList();
	}
	
	/**
	 * Someone has a new hobby.
	 * 
	 * @param userHobby what to add for user.
	 * @throws CRUDArgumentException if hobby was already there.
	 */
    public Userhobby addUserHobby(Userhobby userHobby) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(userHobby);
    	if (userHobbyExists(userHobby.getUser(), userHobby.getHobby())) {
    		throw new CRUDArgumentException("User Hobby already exists.");
    	}
    	    	
    	Userhobby rtnVal = em.merge(userHobby);
    	em.flush();
    	
    	return rtnVal;
    }
    
    /**
     * Attempt to remove a user's hobby from database.
     * 
     * @param userHobby which to be deleted.
     * @throws CRUDArgumentException if deletion failed (possibly for not-found).
     */
    public void deleteUserhobby(Userhobby userHobby) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(userHobby);
    	try {
    		em.remove(userHobby);
    		em.flush();
    	} catch (IllegalArgumentException iae) {
    		throw new CRUDArgumentException(iae.getMessage());
    	}
    }
    
    /**
     * Change text of an existing hobby.
     * 
     * @param userHobby with stale description.
     * @throws CRUDArgumentException thrown if arguments useless.
     */
    public void updateUserHobby(Userhobby userHobby) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(userHobby);
    	if (! userHobbyExists(userHobby)) {
    		throw new CRUDArgumentException("User Hobby does not exist.");
    	}
    }
    
    private boolean userHobbyExists(Userhobby userHobby) {
    	List<Userhobby> resultList = em.createNamedQuery(USERHOBBY_BY_ID_QRY, Userhobby.class)
    			.setParameter("u",  userHobby.getId())
    			.getResultList();
		return resultList != null  &&  resultList.size() > 0;
    }

    private boolean userHobbyExists(User user, String hobbyDescription) {
    	List<Userhobby> resultList = em.createNamedQuery(USERHOBBY_BY_UID_NAME_QRY, Userhobby.class)
    			.setParameter("u", user)
    			.setParameter("h", hobbyDescription)
    			.getResultList();
		return resultList != null  &&  resultList.size() > 0;
    }
}
