package com.ycgwl.rosefinch.module.basedev.server.services.base;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseConfigEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseConfigVo;
/**
 *
 * Title: 操作T_BASE_CONFIG表的服务接口
 * Description:
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年8月11日  上午10:54:56
 *
 */
public interface IBaseConfigService extends IBaseService<BaseConfigEntity, Long>{
	//新增
	BaseConfigEntity insertConfig(BaseConfigVo baseConfigVo, String currentUserCode);
	//删除
	void deleteConfig(Long configId, String currentUserCode)throws BusinessException;
	//修改
	BaseConfigEntity editConfig(BaseConfigVo baseConfigVo , String currentUserCode);
	//查询
	Pagination<BaseConfigEntity> getConfig(QueryPageVo queryPageVo);
	//检测配置名称的唯一性
	int uniqueCheckByConfigName(Map<String, Object> map);
	//检测配置编码的唯一性
	int uniqueCheckByConfigCode(String configCode);
    void removeBaseConfigByIdList(List<Long> list);

}
