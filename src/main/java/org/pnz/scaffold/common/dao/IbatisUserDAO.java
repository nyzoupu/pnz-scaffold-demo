package org.pnz.scaffold.common.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("deprecation")
public class IbatisUserDAO extends SqlMapClientDaoSupport
                                            implements UserDAO {

    public UserDO queryById(Long id) throws DataAccessException {
        return (UserDO) getSqlMapClientTemplate()
            .queryForObject("user.queryById", id);
    }

}
