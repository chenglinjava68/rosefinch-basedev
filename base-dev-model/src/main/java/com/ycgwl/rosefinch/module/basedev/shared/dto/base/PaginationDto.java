package com.ycgwl.rosefinch.module.basedev.shared.dto.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 228238
 *
 */
public class PaginationDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8636124435633654516L;

	@SuppressWarnings("rawtypes")
    protected List paginationDtos = new ArrayList();
    
    protected Long totalCount = 0l;
    
    /**
     * @return  the paginationDtos
     */
    @SuppressWarnings("rawtypes")
    public List getPaginationDtos() {
        return paginationDtos;
    }
    
    /**
     * @param paginationDtos the paginationDtos to set
     */
    public void setPaginationDtos(@SuppressWarnings("rawtypes") List paginationDtos) {
	this.paginationDtos = paginationDtos;
    }
    
    /**
     * @return  the totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }
    
    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    } 
}
