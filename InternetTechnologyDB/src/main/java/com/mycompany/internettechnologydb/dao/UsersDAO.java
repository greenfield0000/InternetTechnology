package com.mycompany.internettechnologydb.dao;

import com.mycompany.internettechnologydb.dao.exceptions.NonexistentEntityException;
import com.mycompany.internettechnologydb.entity.Users;
import com.mycompany.internettechnologydb.entity.Users_;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author roma
 */
public class UsersDAO implements Serializable {

    private EntityManagerFactory emf = null;

    public UsersDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Создание пользователя при регистрации
     *
     * @param name
     * @param login
     * @param password
     */
    public boolean registryUser(String name, String login, String password) {
        EntityManager em = null;
        boolean succsesRegistry = false;
        boolean userIsExist = (boolean) userIsExist(login, password).get("userIsExist");

        if (!userIsExist) {
            try {
                Users user = new Users();
                user.setName(name);
                user.setLogin(login);
                user.setPassword(password);
                em = getEntityManager();
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
                succsesRegistry = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }

        return succsesRegistry;
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            users = em.merge(users);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getHid();
                if (findUsersById(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getHid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Users findUsersById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param login
     * @param password
     * @return true - авторизован false - не авторизован
     */
    public Map<String, Object> userAutorization(String login, String password) {

        // результат<имя,авторизовался (да или нет)>
        Map<String, Object> result = new HashMap<>();

        result = this.userIsExist(login, password);
        if ((boolean) result.get("userIsExist")) {
            return result;
        }
        return null;
    }

    /**
     *
     * @param login -
     * @param password
     * @return true - пользователь существует false - пользователь не существует
     */
    private Map<String, Object> userIsExist(String login, String password) {

        Map<String, Object> result = new HashMap<>();
        EntityManager entityManger = getEntityManager();

        try {

            CriteriaQuery criteriaQuery = entityManger.getCriteriaBuilder().createQuery();

            //initialize root xml class
            Root<Users> rootXmlUser = criteriaQuery.from(Users.class);

            // build restrict
            Predicate predicatRestrict = entityManger
                    .getCriteriaBuilder().and(
                            entityManger.getCriteriaBuilder().equal(rootXmlUser.get(Users_.login), login),
                            entityManger.getCriteriaBuilder().equal(rootXmlUser.get(Users_.password), password)
                    );

            //Build criteria query
            criteriaQuery.from(Users.class);
            criteriaQuery.where(predicatRestrict);

            //Query
            Query q = entityManger.createQuery(criteriaQuery);

            result.put("userIsExist", Boolean.FALSE);
            if ((!q.getResultList().isEmpty()) && (q.getResultList().size() == 1)) {

                List<Users> userList = q.getResultList();

                result.put("hid", userList.get(0).getHid());
                result.put("login", userList.get(0).getLogin());
                result.put("pass", userList.get(0).getPassword());
                result.put("name", userList.get(0).getName());
                result.put("userIsExist", Boolean.TRUE);
                String sessionID = userList.get(0).getName() + '%'
                        + userList.get(0).getLogin() + '%'
                        + userList.get(0).getPassword();
                result.put("sesID", sessionID);
                result.put("sesName", userList.get(0).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return result;
    }
}
