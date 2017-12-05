package com.weipai.mapper;

import java.util.List;
import java.util.Map;

import com.weipai.model.Manager;

public interface ManagerMapper {
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKeyUpdateChild(Integer id);
    int save(Manager record);

    int saveSelective(Manager record);

    Manager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);
    /**
     * 根据用户名得到整个对象
     * @param username
     * @return
     */
    Manager selectObjectByUsername(String username);
    
    Manager selectManagerByInvoteCode(int inviteCode);
    
    
    /**
     * 根据不同条件得到代理商/零售商列表
     * @param map
     * @return
     */
    List<Manager> selectObjectsByMap(Map<String ,Integer> map);
    
    
    List<Manager> selectManagerInfoByMap(Map<String ,Object> map);
    List<Manager> selectManagerInfoByMapStats(Map<String ,Object> map);
    int selectManagerInfoByMapCount(Map<String ,Object> map);
    
    int selectByMidTimeMountSum(Map<String ,Object> map);
    int selectByAllTimeMountSum(Map<String ,Object> map);
    
    /**
     * 修改管理员房卡数量
     * @param map
     * @return
     */
    int updateActualcard(Map<String,Integer> map);
    /**
     * 得到管理元下面的代理商(根据 manager_up_id 和手机号码)
     * @param tel
     * @return
     */
    List<Manager> selectManagerByTel(Map<String,Object> map);
    
    int updateManagerStatus(Integer id);
    int updateManagerLoginTime(Integer id);
    int updateByMap(Map<String,Object> map);
    int updatePowerIdByMap(Map<String,Object> map);
    int selectSizeByManagerId(int id);

    Manager selectManagerByInviteCode(Map<String ,Object> map);

}