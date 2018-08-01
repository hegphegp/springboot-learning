package com.hegp.service.basic;

import com.hegp.domain.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/** 这套封装只能用于单数据库的应用，如果多数据库应用，可能不适用，等以后能力提升了，再来改造成多数据库的 */
/**
 * 参考的博客<url>https://blog.csdn.net/u012706811/article/details/53218102</url>
 * 现阶段知道的官方JPA提供的复杂条件封装有两种方式
 * ========>>>>>>>> 1) 使用CriteriaBuilder构建JPQL(不是SQL语句)
 * 例子 //select u from User u where u.id = 1
 * CriteriaBuilder cb = entityManager.getCriteriaBuilder();
 * CriteriaQuery<User> cq = cb.createQuery(User.class);
 * // Root<User> root = cq.from(User.class); //from User
 * // cq.select(root); //select * from User
 * javax.persistence.criteria.Predicate pre = cb.equal(root.get("id").as(Integer.class),id);//id=1
 * cq.where(pre);//where id=1
 * Query query = entityManager.createQuery(cq);//select u from User u where u.id = 1
 * query.getResultList();
 * 使用CriteriaBuilder的缺点:
 * 1)代码量多
 * 2)不易维护
 * 3)条件复杂的话,则会显得很混乱
 *
 *
 * ========>>>>>>>> 2) 使用Specification查询, Specification封装大量条件
 * 官方JpaSpecificationExecutor接口封装了以下几个方法:
 *
 * public interface JpaSpecificationExecutor<T> {
 *     T findOne(Specification<T> spec);
 *     List<T> findAll(Specification<T> spec);
 *     Page<T> findAll(Specification<T> spec, Pageable pageable);
 *     List<T> findAll(Specification<T> spec, Sort sort);
 *     long count(Specification<T> spec);
 * }
 * 我看了https://blog.csdn.net/u012706811/article/details/53218102博客，发现博主自己封装了大量的方法，个人认为，官方肯定封装过博主自主写的代码，但是官方封装在哪里，怎么
 */
public class BasicServiceImpl<T> implements IBasicService<T> {

	Pageable pageable = new PageRequest(0,1); //page, size);

	/**
	 * user me=entityManager.find(User.class,integer(2));
	 * 根据主键查询：entityManager.find(实体类名.class，主键值)
	 * 删除(要先查询)：entityManager.remove(me);
	 * 更新(要先查询)：entityManager.merge(me);
	 * 查询：entityManager.creatQuery(me);
	 */

	@PersistenceContext
	private EntityManager em;

	public SessionFactory getSessionFactory() {
		Session session = (Session) em.getDelegate();
		return session.getSessionFactory();
	}

	@Override
	@Transactional(readOnly=true)
	public void save(Object entity) {
		Assert.notNull(entity, "保存的对象不允许为空");
		em.persist(entity);
		em.flush();
	}

	@Override
	@Transactional(readOnly=true)
	public boolean update(Object entity) {
		Assert.notNull(entity, "更新的对象不允许为空");
		em.merge(entity);
		return true;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean deleteById(Object id) {
		Assert.notNull(id, "id不允许为空");
		Class entityClass = currentModelClass();
		em.remove(em.getReference(entityClass, id));
		return true;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean deleteByIds(Object[] ids) {
		Assert.notEmpty(ids, "ids数组不能为空");
		Class entityClass = currentModelClass();
		for (Object id : ids) {
			Assert.notNull(id, "ids数组包含空的id");
			em.remove(em.getReference(entityClass, id));
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
		Class<T> domainClass = currentModelClass();
		cb.createQuery(domainClass);
//		CriteriaQuery<T> cq = cb.createQuery(User.class);
		return true;
	}

//	private void deleteById(Class entityClass, Object id) {
//		em.remove(em.getReference(entityClass, id));
//	}

	@Override
	public Page<T> selectByPage(Pageable pageable) {
		return null;
	}

	@Override
	public Page<T> selectByPage(int page, int size) {
//		em.
		return null;
	}

	@Override
	public List<T> selectAll() {
		Class currentModelClass = currentModelClass();
		String entityName = currentModelClass.getName();
		Query query=em.createQuery("from "+entityName);
		return query.getResultList();
	}

	protected Class currentModelClass() {
		int index = 1;
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType))
			return Object.class;
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0)
			return Object.class;
		if (!(params[index] instanceof Class))
			return Object.class;
		return (Class) params[index];
	}

}
