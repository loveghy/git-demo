package com.qn.computersell.mapper;

import com.qn.computersell.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapper
public interface UserDao extends ElasticsearchRepository<UserEntity,Integer> {

}
