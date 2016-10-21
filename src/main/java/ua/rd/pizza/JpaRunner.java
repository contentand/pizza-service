package ua.rd.pizza;

import ua.rd.pizza.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaRunner {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
            EntityManager entityManager = null;
            try {
                entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction tx = null;

                try {
                    tx = entityManager.getTransaction();
                    tx.begin();

                    // BEGIN
                    Employee empl1 = new Employee("John Smeets", 2);
                    Employee empl2 = new Employee("Walter Scott", 1);

                    entityManager.persist(empl1);
                    entityManager.persist(empl2);
                    // END

                    tx.commit();
                } finally {
                    if (tx != null && tx.isActive()) {
                        tx.rollback();
                    }
                }
            } finally {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                }
            }
        } finally {
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }

        }
    }
}
