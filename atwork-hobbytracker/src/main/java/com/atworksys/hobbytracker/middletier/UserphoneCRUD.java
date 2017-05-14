package com.atworksys.hobbytracker.middletier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.atworksys.hobbytracker.model.Userphone;

import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * Create/Read/Update/Delete ops for user's phone.
 * 
 * @author Leslie L Foster
 */
@Stateless
public class UserphoneCRUD {
	private EntityManager em;
	
    @PersistenceContext(unitName="primary")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }	

    /**
     * Return all of the user/phone table members.
     * @return exhaustive list.
     */
    public List<Userphone> getUserPhones() {
    	return em.createNamedQuery(USERPHONE_ALL_QRY, Userphone.class).getResultList();    	
    }
    
    /**
     * User adds a new phone.  No attempt made to make phones unique by user/type!
     * 
     * @param userPhone what to add.
     * @return the newly-added phone.
     * @throws CRUDArgumentException
     */
    public Userphone addUserPhone(Userphone userPhone) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(userPhone);
    	
    	em.persist(userPhone);
    	em.flush();
    	return userPhone;
    }
    
    /**
     * Will attempt to delete the user/phone combination.
     * 
     * @param userphone unwanted handset
     * @throws CRUDArgumentException if detached/nonexistent.
     */
    public void deleteUserPhone(Userphone userphone) throws CRUDArgumentException {
    	try {
    		Userphone toDel = em.merge(userphone);
    		em.remove(toDel);
    		em.flush();
    	} catch (IllegalArgumentException iae) {
    		throw new CRUDArgumentException(iae.getMessage());
    	}    	
    	
    }
    
    /**
     * Modify parts of the user-phone.
     * @param userphone with new values, old id.
     * @throws CRUDArgumentException if bad args.
     */
    public void updateUserPhone(Userphone userphone) throws CRUDArgumentException {
    	Userphone existingPhone = null;
    	if (null == (existingPhone = em.find(Userphone.class, userphone.getId())) ) {
    		throw new CRUDArgumentException("Not Found");
    	}
    	
    	em.merge(userphone);
    	em.flush();
    }
}
