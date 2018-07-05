package ff.letsgo.smr.dal.mapper;

import ff.letsgo.smr.dal.po.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    Product select(@Param("id") long id);

    void insert(Product product);

    void update(Product product);

    void delete(@Param("id") long id);

}
