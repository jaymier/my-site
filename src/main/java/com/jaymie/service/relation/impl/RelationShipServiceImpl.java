package com.jaymie.service.relation.impl;

import com.jaymie.dao.RelationShipDao;
import com.jaymie.service.relation.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:39
 * @description : TODO
 */
@Service
public class RelationShipServiceImpl implements RelationShipService {

    @Autowired
    private RelationShipDao relationShipDao;


}
