package org.pnz.scaffold.common.dao;

import org.springframework.dao.DataAccessException;

public interface UserDAO {

	public UserDO queryById(Long id) throws DataAccessException;

}



