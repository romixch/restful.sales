package ch.romix.restful.sales.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class EnhancedMapper {

  public static <S extends Object, T extends Object> Collection<T> map(
      Collection<S> sourceCollection, Class<T> targetClass) {
    Mapper mapper = createMapper();
    Collection<T> targetCollection = new ArrayList<T>();
    for (S item : sourceCollection) {
      targetCollection.add(mapper.map(item, targetClass));
    }
    return targetCollection;
  }


  public static <S extends Object, T extends Object> T map(S source, Class<T> targetClass) {
    Mapper mapper = createMapper();
    T target = mapper.map(source, targetClass);
    return target;
  }

  public static <S extends Object, T extends Object> void map(Collection<S> source,
      Collection<T> target) {
    Mapper mapper = createMapper();
    mapper.map(source, target);
  }

  private static Mapper createMapper() {
    List<String> mappingFiles = new ArrayList<String>();
    mappingFiles.add("META-INF/mappings/dozer.xml");
    Mapper mapper = new DozerBeanMapper(mappingFiles);
    return mapper;
  }
}
