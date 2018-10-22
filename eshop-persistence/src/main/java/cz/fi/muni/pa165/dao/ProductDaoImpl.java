package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Product p) {
        em.persist(p);
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    @Override
    public void remove(Product p) {
        //TODO SPYTAT SA
        em.remove(em.contains(p) ? p : em.merge(p));
    }

    @Override
    public List<Product> findByName(String name) {
        TypedQuery<Product> query = em.createQuery("SELECT p from Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
