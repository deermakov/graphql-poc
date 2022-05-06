package ru.lanit.research.graphql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Session;
import ru.lanit.research.graphql.app.impl.BeanMerger;

import javax.persistence.*;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    abstract Object getBusinessKey();

    // Ище в БД свою версию по id и бизнес-ключу.
    // Если нашел - вычитывает ее из БД, делает merge себя в неё и возвращает результат
    //  - обновленную (merged) сущность в состоянии managed entity
    // Если в БД версия не нашлась, то возвращает себя
    public DomainObject mergeToDb(EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        // вычитаем свое состояние из БД
        DomainObject base;
        Object businessKey = getBusinessKey();
        if (businessKey != null) { // ... по бизнес-ключам
            base = session.bySimpleNaturalId(getClass()).load(businessKey);
        } else { // ... по id
            base = session.byId(getClass()).load(getId());
        }

        // если в БД не нашлось - создаем новую пустую сущность
        if (base == null) {
            try {
                base = this.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // merge себя в base
        BeanMerger.shallowMerge(this, base);

        return base;
    }
}
