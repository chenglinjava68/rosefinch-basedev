/*
 * Copyright (C) 2016 YcExpress.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.server.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.kafka.server.logger.Logger;
import com.ycgwl.kafka.server.logger.LoggerFactory;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCommonConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;

/**
 * @Title:
 * @Description:
 * @Company: 远成快运
 * @author guoh.mao
 * @date 2016年11月28日 上午11:36:22
 */
public class SyncActiveDirectoryBean {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private LdapContext ctx;
	
	private String host;
	private String port;
	private String url;
	private String username;
	private String password;
	
	private static final String ROOT_OU_DN = "OU=远成集团,DC=ycgwl,DC=com";  // ROOT OU
	private static final String OU_SEARCH_FILTER = "objectClass=organizationalUnit";
	// 在职员工
//	private static final String USER_SEARCH_FILTER = "(&(objectClass=User)(|(userAccountControl=512)(userAccountControl=66048)))";
	
	// 所有状态的员工
	private static final String USER_SEARCH_FILTER = "objectClass=User";
	
	
	//------------UserAccountControl-------------
	// 账户正常
	private static final Integer NORMAL_ACCOUNT = 512;
	// 密码永不过期
	private static final Integer DONT_EXPIRE_PASSWORD = 66048;
	
	
	
	private static final String ADMIN = "000000";
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final String YC_EXPRESS = "OU=远成快运,";
	private static final String OU_JOIN = "OU=加盟网点,";
	
	
	private static final String TOP_CODE = "-1";   // “远成集团”的父节点的编码
	private static final String ROOT_OU_CODE = "YCG";   // “远成集团”编码
	
	private static final String DEFAULT_CURRENCY_RMB = "1";   // 人民币
	
	private static int count = 0;
	
	@Autowired
	private IBaseOrgService baseOrgService;

	public SyncActiveDirectoryBean(String host, String port, String username,
			String password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		
		setUrl(new String("ldap://" + this.host));   // 设置URL
		setCtx(initLdap());   // 初始化LDAP上下文
	}

	
	/**
	 * 主执行方法
	 *
	 * @author guoh.mao
	 */
	public void execute() {
		try {
			log.info("sync active directory start.");
			Date startTime = new Date();
			
			// 首先同步“远成集团”
			SearchResult rootOu = getRootOu();
			insertOrUpdateRootOu(rootOu);
			
			// ---------同步--------
			loopOU(ROOT_OU_DN);
			System.out.println("+++++++++++++++++++++++++"+count);
			
			// 关闭LDAP
//			closeLdap();
			
			// 清零
			count = 0;
			
			Date endTime = new Date();
			System.out.println("+++++++++++++本次同步耗时++++++++++++："+(endTime.getTime()-startTime.getTime())+"毫秒");
			
			log.info("sync active directory end.");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("执行同步AD域数据任务失败。详情："+e.getMessage());
		}
	}
	
	
	/**
	 * 同步根节点“远成集团”
	 *
	 * @param ou
	 * @throws NamingException
	 * @author guoh.mao
	 */
	private void insertOrUpdateRootOu(SearchResult ou) throws NamingException{
		if (null != ou) {
			BaseOrgEntity baseOrgEntity = toBaseOrgEntity(TOP_CODE, ou);
			baseOrgEntity.setOrgCode(ROOT_OU_CODE);  // YCG
			
			baseOrgService.insertOrUpdateRootOu(baseOrgEntity);
		}
	}
	
	/**
	 * 获得“远成集团”
	 *
	 * @return
	 * @throws NamingException
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	private SearchResult getRootOu() throws NamingException{
		NamingEnumeration answer = search(ROOT_OU_DN, OU_SEARCH_FILTER, SearchControls.OBJECT_SCOPE);
		SearchResult rootOu = null;
		while (answer.hasMoreElements()) {
			rootOu = (SearchResult) answer.next();
		}
		return rootOu;
	}
	
	
	/**
	 * 数据同步主方法
	 *
	 * @param DN
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	private void loopOU(String DN) {   // answer：所有子OU
		try {
			NamingEnumeration parentAnswer = search(DN, OU_SEARCH_FILTER, SearchControls.OBJECT_SCOPE);
			if (null != parentAnswer) {
				SearchResult parentResult = null;
				while (parentAnswer.hasMore()) {    // 是否有子OU
					parentResult = (SearchResult) parentAnswer.next();
				}
				
				//---------------user----------------
				NamingEnumeration usersAnswer = search(DN, USER_SEARCH_FILTER, SearchControls.ONELEVEL_SCOPE);
				List<SearchResult> userList = new ArrayList<>();
				
				if (null != usersAnswer) {
					while (usersAnswer.hasMore()) {    // 是否有子OU
						SearchResult result = (SearchResult) usersAnswer.next();
						userList.add(result);
					}
				}
				
				
				//----------------OU-----------------
				// 获取当前组织结构的所有直接下级组织机构
				NamingEnumeration answer = search(DN, OU_SEARCH_FILTER, SearchControls.ONELEVEL_SCOPE);
				
				List<SearchResult> ouList = new ArrayList<>();
				
				if (null != answer) {
					if (answer.hasMoreElements()) {   // 有下级
						while (answer.hasMore()) {    // 是否有子OU
							SearchResult result = (SearchResult) answer.next();
							ouList.add(result);
						}
					}
				}
				
				//---------------同步-----------------
				batchInsertOrUpdateOneLevelOuAndUser(parentResult, ouList, userList);  // 同步
				
				
				//---------------递归-----------------
				if (CollectionUtils.isNotEmpty(ouList)) {
					for (SearchResult ou : ouList) {
						// DN
						Attribute dnAttr = ou.getAttributes().get("distinguishedName");
						loopOU(dnAttr.get().toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AD域数据同步报错。详情："+e.getMessage());
		}
	}
	
	/**
	 * 批量插入或更新组织机构/员工
	 *
	 * @param parentResult
	 * @param ouList
	 * @param userList
	 * @throws NamingException
	 * @author guoh.mao
	 */
	private void batchInsertOrUpdateOneLevelOuAndUser(SearchResult parentResult, List<SearchResult> ouList, List<SearchResult> userList) throws NamingException{
		Attribute objectGUIDAttr = parentResult.getAttributes().get("objectGUID");
		String parentGuid = octetToString(objectGUIDAttr.get().toString().getBytes());
		
		// DN
		Attribute dnAttr = parentResult.getAttributes().get("distinguishedName");
		String DN = dnAttr.get().toString();
		
		List<BaseOrgEntity> orgList = new ArrayList<>();
		List<BaseEmployeeEntity> empList = new ArrayList<>();
		
		
		if (CollectionUtils.isNotEmpty(ouList)) {
			for (SearchResult ou : ouList) {
				BaseOrgEntity baseOrgEntity = toBaseOrgEntity(parentGuid, ou);
				orgList.add(baseOrgEntity);
			}
		}
		
		if (CollectionUtils.isNotEmpty(userList)) {
			count += userList.size();
			
			for (SearchResult user : userList) {
				BaseEmployeeEntity baseEmployeeEntity = toBaseEmployeeEntity(parentGuid, user, DN);
				empList.add(baseEmployeeEntity);
			}
		}
		
		// 批量插入
		baseOrgService.batchInsertOrUpdateOneLevelOuAndUser(orgList, empList);
		
		System.out.println("========================="+count);
//		log.debug("========================="+count);
	}
	
	/**
	 * 转换为组织机构
	 *
	 * @param parentOuCode
	 * @param ou
	 * @return
	 * @throws NamingException
	 * @author guoh.mao
	 */
	private BaseOrgEntity toBaseOrgEntity(String parentOuCode, SearchResult ou) throws NamingException{
		BaseOrgEntity baseOrgEntity = new BaseOrgEntity();
		
		// DN
		Attribute dnAttr = ou.getAttributes().get("distinguishedName");
		String DN = dnAttr.get().toString();
		
		Attribute objectGUIDAttr = ou.getAttributes().get("objectGUID");
		String objectGUID = octetToString(objectGUIDAttr.get().toString().getBytes());
		
		Attribute nameAttr = ou.getAttributes().get("name");
		String name = nameAttr.get().toString();
		
		Attribute uSNCreatedAttr = ou.getAttributes().get("uSNCreated");
		Integer uSNCreated = Integer.valueOf(uSNCreatedAttr.get().toString());
		
		
		Attribute whenCreatedAttr = ou.getAttributes().get("whenCreated");
		String whenCreated = whenCreatedAttr.get().toString();
		
		Attribute whenChangedAttr = ou.getAttributes().get("whenChanged");
		String whenChanged = whenChangedAttr.get().toString();
		
		
		baseOrgEntity.setOrgId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseOrgEntity.setOrgCode(objectGUID);
		baseOrgEntity.setOrgName(name);
		baseOrgEntity.setOrgNameShort(name);
		baseOrgEntity.setOrderBy(uSNCreated);
		baseOrgEntity.setUpOrgCode(parentOuCode);
		
		baseOrgEntity.setOrgType(getOrgType(DN).toString());   // 组织类型
		
		// 同步时是否忽略
		baseOrgEntity.setIgnore(isIgnore(DN));
		
		// GUID
		baseOrgEntity.setGuid(objectGUID);
		
		// 本位币币别
		baseOrgEntity.setDefaultCurrency(DEFAULT_CURRENCY_RMB); 
		baseOrgEntity.setCountry(BaseOrgConstant.REGION_CODE_CHINA);
		baseOrgEntity.setProvince("TODO");
		baseOrgEntity.setCity("TODO");
		baseOrgEntity.setCounty("TODO");
		baseOrgEntity.setOrgAddress("TODO");
		
		baseOrgEntity.setBlFlag(BaseCommonConstant.INT_ONE);
		baseOrgEntity.setDelFlag(BaseCommonConstant.INT_ZERO);
		baseOrgEntity.setCreateTime(toDate(whenCreated));
		baseOrgEntity.setCreateUserCode(ADMIN);
		baseOrgEntity.setModifyTime(toDate(whenChanged));
		baseOrgEntity.setModifyUserCode(ADMIN);
		
		return baseOrgEntity;
	}
	
	/**
	 * 转换为员工
	 *
	 * @param parentOuCode
	 * @param user
	 * @return
	 * @throws NamingException
	 * @author guoh.mao
	 */
	private BaseEmployeeEntity toBaseEmployeeEntity(String parentOuCode, SearchResult user, String parentOuDn) throws NamingException{
		BaseEmployeeEntity baseEmployeeEntity = new BaseEmployeeEntity();
		
		// 唯一标识
//		Attribute objectSidAttr = user.getAttributes().get("objectSid");
//		String userCode = octetToString(objectSidAttr.get().toString().getBytes());
		
		// 工号
		Attribute sAMAccountNameAttr = user.getAttributes().get("sAMAccountName");
		String sAMAccountName = null;
		if (null != sAMAccountNameAttr) {
			sAMAccountName = sAMAccountNameAttr.get().toString();
		}
		
		// 姓名
		Attribute displayNameAttr = user.getAttributes().get("displayName");
		String displayName = null;
		if (null != displayNameAttr) {
			displayName = displayNameAttr.get().toString();
		}
		
		Attribute whenCreatedAttr = user.getAttributes().get("whenCreated");
		String whenCreated = null;
		if (null != whenCreatedAttr) {
			whenCreated = whenCreatedAttr.get().toString();
		}
		
		Attribute whenChangedAttr = user.getAttributes().get("whenChanged");
		String whenChanged = null;
		if (null != whenChangedAttr) {
			whenChanged = whenChangedAttr.get().toString();
		}
		
		// 地址
		Attribute jtdzAttr = user.getAttributes().get("sfzdz");
		String jtdz = null;
		if (null != jtdzAttr) {
			jtdz = jtdzAttr.get().toString();
		}
		
		// mobile
		Attribute mobileAttr = user.getAttributes().get("mobile");
		String mobile = null;
		if (null != mobileAttr) {
			mobile = mobileAttr.get().toString();
		}
		
		// 员工级别
		Attribute xzjbAttr = user.getAttributes().get("xzjb");
		String xzjb = null;
		if (null != xzjbAttr) {
			xzjb = xzjbAttr.get().toString();
		}
		
		// 员工职位
		Attribute titleAttr = user.getAttributes().get("title");
		String title = null;
		if (null != titleAttr) {
			title = titleAttr.get().toString();
		}
		
		// DN
		Attribute dnAttr = user.getAttributes().get("distinguishedName");
		String DN = dnAttr.get().toString();
		
		
		// userAccountControl
		Attribute userAccountControlAttr = user.getAttributes().get("userAccountControl");
		String userAccountControl = null;
		if (null != userAccountControlAttr) {
			userAccountControl = userAccountControlAttr.get().toString();
		}
		
		baseEmployeeEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseEmployeeEntity.setEmployeeCode(sAMAccountName);
		baseEmployeeEntity.setEmployeeName(displayName);
		
		
		String objectGUID = getSubCompanyObjectGUID(DN);
		if (StringUtils.isBlank(objectGUID)) {
			baseEmployeeEntity.setOrgCode(parentOuCode);
			baseEmployeeEntity.setFlag(false);
		}else {
			// 分公司下级部门的员工，所属部门设置为分公司
			baseEmployeeEntity.setOrgCode(objectGUID);
			baseEmployeeEntity.setFlag(true);
		}
		
		baseEmployeeEntity.setAddress(jtdz);
		baseEmployeeEntity.setPhone(mobile);
		baseEmployeeEntity.setOwnerRange(xzjb);
		baseEmployeeEntity.setOwnerRange(xzjb);
		
		// 员工职位
		baseEmployeeEntity.setTitle(title);
		
		
		// 特殊处理：CN=三门一部,OU=加盟网点,OU=快运华东大区,OU=远成快运,OU=远成集团,DC=ycgwl,DC=com，“三门一部”是部门而不是员工
		if (StringUtils.isNotBlank(DN) && DN.contains(OU_JOIN) && DN.contains(YC_EXPRESS)) {
			baseEmployeeEntity.setIgnore(true);
		} else {
			baseEmployeeEntity.setIgnore(false);
		}
		
//		baseEmployeeEntity.setEmployeeType(employeeType);
		
		baseEmployeeEntity.setDelFlag(BaseCommonConstant.INT_ZERO);
		
		// 通过员工状态设置启用状态
		if (isUserAccountValid(userAccountControl)) {
			baseEmployeeEntity.setBlFlag(BaseCommonConstant.INT_ONE);
		}else {
			baseEmployeeEntity.setBlFlag(BaseCommonConstant.INT_ZERO);
		}
		
		baseEmployeeEntity.setCreateTime(toDate(whenCreated));
		baseEmployeeEntity.setCreateUserCode(ADMIN);
		baseEmployeeEntity.setModifyTime(toDate(whenChanged));
		baseEmployeeEntity.setModifyUserCode(ADMIN);
		
		return baseEmployeeEntity;
	}
	
	/**
	 * 获得组织类型
	 *
	 * @param DN
	 * @return
	 * @author guoh.mao
	 */
	private Integer getOrgType(String DN) {
		Integer orgType = 2;
		if (StringUtils.isNotBlank(DN)) {
			if (DN.contains(YC_EXPRESS)) { // 包含“远成快运”
				String[] arr = DN.split(",");
				if (arr.length == 4) {
					orgType = BaseOrgConstant.ORG_TYPE_YCEXPRESS; // 本部
				} else if (arr.length == 5 && arr[0].contains("大区")) {
					orgType = BaseOrgConstant.ORG_TYPE_BIG_AREA; // 大区
				} else if (arr.length == 5 && arr[0].trim().equals("OU=快运本部")) {
					orgType = BaseOrgConstant.ORG_TYPE_DEPT; // 职能部门
				} else if (arr.length == 6 && arr[0].trim().endsWith("快运")) {
					orgType = BaseOrgConstant.ORG_TYPE_COMPANY; // 公司
				} else {
					orgType = BaseOrgConstant.ORG_TYPE_DEPT;; // 职能部门
				}
			}
		}

		return orgType;
	}
	
	
	/**
	 * 是否忽略（组织架构同步到分公司，分公司之下的部门不同步到基础数据）
	 *
	 * @param DN
	 * @return
	 * @author guoh.mao
	 */
	private Boolean isIgnore(String DN) {
		// 默认为false
		boolean ignore = false;
		
		if (StringUtils.isNotBlank(DN)) {
			if (DN.contains(YC_EXPRESS)) { // 包含“远成快运”，表明是远成快运下面的组织
				String[] arr = DN.split(",");
				
				if (arr.length >= 7) {
					ignore = true;
				}
			}
		}
		
		return ignore;
	}
	
	
	/**
	 * search OU或者User
	 *
	 * @param DN
	 * @param objectClass
	 * @param scope
	 * @return
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	private NamingEnumeration search(String DN, String searchFilter,
			int scope) {
		if (DN.contains("/")) {
			DN = DN.replace("/", "\\/");
		}
		
		String searchBase = DN;
//		String searchFilter = "objectClass=" + objectClass;

		// 创建搜索控制器
		SearchControls searchCtls = new SearchControls();
		// 设置搜索范围
		searchCtls.setSearchScope(scope);

		// TODO
		// String returnedAtts[] = { "memberOf" }; // 定制返回属性
		// searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集
		// 不设置则返回所有属性

		// 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
		NamingEnumeration answer = null;
		try {
			answer = ctx.search(searchBase, searchFilter,
					searchCtls);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	
	/**
	 * TODO: DOCUMENT ME!
	 *
	 * @param k
	 * @return
	 * @author guoh.mao
	 */
	private String addLeadingZero(int k) {
		return (k <= 0xF)? "0" + Integer.toHexString(k) : Integer.toHexString(k);
	}
	
	/**
	 * TODO: DOCUMENT ME!
	 *
	 * @param GUID
	 * @return
	 * @author guoh.mao
	 */
	private String octetToString(byte[] GUID) {
		String strGUID = "";

		strGUID = strGUID + addLeadingZero((int) GUID[3] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[2] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[1] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[0] & 0xFF);
		strGUID = strGUID + "-";
		strGUID = strGUID + addLeadingZero((int) GUID[5] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[4] & 0xFF);
		strGUID = strGUID + "-";
		strGUID = strGUID + addLeadingZero((int) GUID[7] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[6] & 0xFF);
		strGUID = strGUID + "-";
		strGUID = strGUID + addLeadingZero((int) GUID[8] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[9] & 0xFF);
		strGUID = strGUID + "-";
		strGUID = strGUID + addLeadingZero((int) GUID[10] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[11] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[12] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[13] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[14] & 0xFF);
		strGUID = strGUID + addLeadingZero((int) GUID[15] & 0xFF);
		return strGUID;
	}
	
	/*private static String getGUID(byte[] inArr) {
		StringBuffer guid = new StringBuffer();

		for (int i = 0; i < inArr.length; i++) {
			StringBuffer dblByte = new StringBuffer(
					Integer.toHexString(inArr[i] & 0xff));
			if (dblByte.length() == 1) {
				guid.append("0");
			}
			guid.append(dblByte);
		}
		return guid.toString();
	}*/
	
	
	
	/**
	 * 根据员工的distinguishedName获得分公司GUID
	 *
	 * @param DN 员工的distinguishedName
	 * @return
	 * @author guoh.mao
	 * @throws NamingException
	 */
	private String getSubCompanyObjectGUID(String DN) throws NamingException{
		String objectGUID = null;
		
		if (StringUtils.isNotBlank(DN)) {
			if (DN.contains(YC_EXPRESS)) { // 包含“远成快运”，表明是远成快运下面的组织
				String[] arr = DN.split(",");
				
				if (arr.length >= 8) {
					List<String> list1 = new ArrayList<>();
					
					List<String> list = Arrays.asList(arr);
					// 反转
					Collections.reverse(list);
					for (int i = 0; i < 6; i++) {
						list1.add(list.get(i));
					}
					
					// 再次反转
					Collections.reverse(list1);
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < 6; j++) {
						sb.append(list1.get(j));
						if (j != 5) {
							sb.append(",");
						}
					}
					
					
					String subCompanyDN = sb.toString();
					
					Integer orgType = getOrgType(subCompanyDN);
					
					// 分公司
					if (orgType.equals(BaseOrgConstant.ORG_TYPE_COMPANY)) {
						NamingEnumeration subCompany = search(subCompanyDN, OU_SEARCH_FILTER, SearchControls.OBJECT_SCOPE);
						SearchResult subCompanyOu = null;
						while (subCompany.hasMoreElements()) {
							subCompanyOu = (SearchResult) subCompany.next();
						}
						
						Attribute objectGUIDAttr = subCompanyOu.getAttributes().get("objectGUID");
						objectGUID = octetToString(objectGUIDAttr.get().toString().getBytes());
					}
				}
			}
		}
		
		return objectGUID;
	}
	
	
	/**
	 * 通过userAccountControl的值判断员工状态
	 *
	 * @param userAccountControl
	 * @return
	 * @author guoh.mao
	 */
	private boolean isUserAccountValid(String userAccountControl){
		if (StringUtils.isNotBlank(userAccountControl)) {
			Integer uac = Integer.valueOf(userAccountControl);
			if (NORMAL_ACCOUNT.equals(uac) || DONT_EXPIRE_PASSWORD.equals(uac)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 初始化LDAP
	 *
	 * @return
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	public LdapContext initLdap() {
		Hashtable HashEnv = new Hashtable();
		HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
		HashEnv.put(Context.SECURITY_PRINCIPAL, getUsername()); // AD User
		HashEnv.put(Context.SECURITY_CREDENTIALS, getPassword()); // AD Password
		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
		HashEnv.put(Context.PROVIDER_URL, getUrl());

		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(HashEnv, null);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ctx;
	}
	
	/**
	 * 时间字符串转换为Date
	 * whenCreated  =  20160225065106.0Z
	 * whenChanged  =  20160225065106.0Z
	 *
	 * @param dateStr
	 * @return
	 * @author guoh.mao
	 */
	private Date toDate(String dateStr){
		Date ct = new Date();
		
		if (StringUtils.isNotBlank(dateStr)) {
			String[] arr = dateStr.split("\\.");
			dateStr = arr[0];
			
			try {
				ct = FORMAT.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				log.error("日期解析异常");
			}
		}
		
		return ct;
	}
	
	/**
	 * 关闭LDAP
	 *
	 * @author guoh.mao
	 */
	private void closeLdap() {
		try {
			this.ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LdapContext getCtx() {
		return ctx;
	}
	public String getUrl() {
		return url;
	}
	public void setCtx(LdapContext ctx) {
		this.ctx = ctx;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}


