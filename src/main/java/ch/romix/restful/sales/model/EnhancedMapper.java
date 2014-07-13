package ch.romix.restful.sales.model;

import java.util.ArrayList;
import java.util.Collection;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class EnhancedMapper {

  public static <S extends Object, T extends Object> Collection<T> map(
      Collection<S> sourceCollection, Class<T> targetClass) {
    Mapper mapper = new DozerBeanMapper();
    Collection<T> targetCollection = new ArrayList<T>();
    for (S item : sourceCollection) {
      targetCollection.add(mapper.map(item, targetClass));
    }
    return targetCollection;
  }

  public static <S extends Object, T extends Object> T map(S source, Class<T> targetClass) {
    Mapper mapper = new DozerBeanMapper();
    T target = mapper.map(source, targetClass);
    return target;
  }

  public static <S extends Object, T extends Object> void map(Collection<S> source,
      Collection<T> target) {
    DozerBeanMapper mapper = new DozerBeanMapper();
    mapper.map(source, target);
  }
}
