package ru.lanit.research.graphql.domain;

import lombok.Data;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import ru.lanit.research.graphql.app.impl.BeanMerger;

/**
 * Это класс со всеми полями от Person и LegalEntity - это реализуется с помощью @Delegate.
 * Используется в mutation writeDeal() для реализации полиморфизма -
 *  все типы участников подаются в этот класс (+ поле typename), и при сохранении в БД из этого класса
 *  вытаскивается участник в виде конкретного класса - см. getParty().
 */
@Data
@Slf4j
public class PartyInput {

    @Delegate(types = {DomainObject.class, Party.class, LegalEntity.class})
    private LegalEntity legalEntity = new LegalEntity();

    @Delegate(excludes = Party.class)
    private Person person = new Person();

    private String typename;

    public Party getParty() {
        log.info("typename = {}", typename);
        Party result = null;
        try {
            // Т.к. поля раскидываются делегатами по вложенным объектам (person и legalEntity), то
            //  при отдаче надо все эти поля собрать в отдаваемой сущности, для чего выполняем merge
            if ("LegalEntity".equals(typename)){
                result = (LegalEntity) BeanMerger.deepMerge(person, legalEntity);
            } else if ("Person".equals(typename)) {
                result = (Person) BeanMerger.deepMerge(legalEntity, person);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
