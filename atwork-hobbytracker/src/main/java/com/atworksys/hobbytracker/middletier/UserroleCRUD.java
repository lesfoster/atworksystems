package com.atworksys.hobbytracker.middletier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.atworksys.hobbytracker.model.Userphone;
import com.atworksys.hobbytracker.model.Userrole;

import static com.atworksys.hobbytracker.common.CommonUtil.*;

/**
 * Create/Read/Update/Delete ops for user's phone.
 * 
 * @author Leslie L Foster
 */
@Stateless
public class UserroleCRUD {
	private EntityManager em;
	
    @PersistenceContext(unitName="primary")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }	

    /**
     * Get and return all the phones known to the system.
     * 
     * @return all user/phone objects.
     */
    public List<Userrole> getUserPhones() {
    	return em.createNamedQuery(USERROLE_ALL_QRY, Userrole.class).getResultList();
    }
    
    /**
     * Adding a role for the user.
     * 
     * @param role populated for add back.
     * @throws CRUDArgumentException for bad inputs.
     */
    public Userrole addUserRole(Userrole role) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(role);
    	CRUDArgumentException.throwIfNull(role.getRolename());
    	//if (roleNameExistsForUser(role)) {
    	//	throw new CRUDArgumentException("Role " + role.getRolename() + " exists for user");
    	//}
    	Userrole ur = em.merge(role);
    	em.flush();
    	return ur;
    }
    
    /**
     * Ensure that the role is being updated to db.
     * 
     * @param role with mods
     * @throws CRUDArgumentException thrown for nulls.
     */
    public void updateUserRole(Userrole role) throws CRUDArgumentException {
    	CRUDArgumentException.throwIfNull(role);
    	CRUDArgumentException.throwIfNull(role.getRolename());
    	if (null == em.find(Userphone.class, role.getRoleId())) {
    		throw new CRUDArgumentException("Not Found");
    	}
    	
    	em.persist(role);
    	em.flush();
    }

    /**
     * Remove the role from the database.
     * 
     * @param role what to remove.
     * @throws CRUDArgumentException on fails.
     */
    public void deleteUserRole(Userrole role) throws CRUDArgumentException {
    	try {
    		em.remove(role);
    	} catch (IllegalArgumentException iae) {
    		throw new CRUDArgumentException(iae.getMessage());
    	}    	

    }
    
    //   Uncomment if 'no dups' requested.
    //private boolean roleNameExistsForUser(Userrole role) {
    //	List<Userrole> roles = em.createNamedQuery(USERROLE_NAME_QRY, Userrole.class)
    //		.setParameter("u", role.getUser())
    //		.setParameter("n", role.getRolename())
    //		.getResultList();
    //	return (roles != null  &&  roles.size() > 0);
    //	
    //}
}
