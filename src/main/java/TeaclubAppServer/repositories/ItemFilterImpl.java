package TeaclubAppServer.repositories;

import org.springframework.stereotype.Component;
import TeaclubAppServer.models.Item;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemFilterImpl implements ItemFilter {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Item> findItemsByShopCartId(Long shopCartId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);

        Root<Item> item = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if (shopCartId != null) {
            predicates.add(cb.equal(item.get("shopCart_owner").get("Id"), shopCartId));
        }
        cq.orderBy(cb.asc(item.get("nameOfItem")));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findItemsByItemId(Long itemId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);

        Root<Item> item = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if (itemId != null) {
            predicates.add(cb.equal(item.get("id"), itemId));
        }

        cq.orderBy(cb.asc(item.get("shopCart_owner").get("Id")));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findItemsByNameOfItem(String nameOfItem) {
        //На будущее, если буду организовывать доп. сортировку в корзине
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);

        Root<Item> item = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nameOfItem != null) {
            predicates.add(cb.equal(item.get("nameOfItem"), nameOfItem));
        }

        cq.orderBy(cb.asc(item.get("shopCart_owner").get("Id")));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findItemsByPriceOfItem(String priceOfItem) {
        //На будущее, если буду организовывать доп. сортировку в корзине
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);

        Root<Item> item = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if (priceOfItem != null) {
            predicates.add(cb.equal(item.get("priceOfItem"), priceOfItem));
        }

        cq.orderBy(cb.asc(item.get("shopCart_owner").get("Id")));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}