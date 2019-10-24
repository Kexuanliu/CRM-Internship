package com.xuebei.crm.excelImport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckRegionMapper {
	
	void insertProvince(Province province);
	
	void insertCity(City city);
	
	String getPidByCName(@Param("cityName") String cityName);
	
	String getPidByPName(@Param("provinceName") String provinceName);
	
}
