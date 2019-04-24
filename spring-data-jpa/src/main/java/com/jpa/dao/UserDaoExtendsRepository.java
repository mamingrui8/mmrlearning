package com.jpa.dao;

import com.jpa.pojo.User;
import org.springframework.data.repository.Repository;

/**
 * User的接口
 *
 * Repository接口讲解
 * Reporsitory<访问数据库时操作的java实体类型， 实体中主键的类型>
 */
public interface UserDaoExtendsRepository extends Repository<User, Integer> {

}
