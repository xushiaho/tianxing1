package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

     /**
     * 校验${table.comment!?substring(0,2)}名是否唯一
     * @param ${entity[3..6]?uncap_first}Name
     * @return
     */
     int check${entity}Name(String ${entity[3..6]?uncap_first}Name);
 }
</#if>
