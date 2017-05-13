package com.atworksys.hobbytracker.middletier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public List<Userrole> getUserPhones() {
    	return em.createNamedQuery(USERROLE_ALL_QRY, Userrole.class).getResultList();
    }
}
