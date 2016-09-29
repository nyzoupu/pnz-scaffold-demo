package org.pnz.scaffold.dao;

import org.springframework.dao.DataAccessException;

public interface UserDAO {

	public UserDO queryById(Long id) throws DataAccessException;

}



