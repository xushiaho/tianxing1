package ${package.ServiceImpl};

import com.tianxing.common.utils.CheckInformation;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private ${table.mapperName} ${table.mapperName?uncap_first};

    /**
    * 校验${table.comment!?substring(0,2)}名是否唯一
    * @param ${entity[3..6]?uncap_first}Name
    * @return
    */
    @Override
    public String check${entity}Name(String ${entity[3..6]?uncap_first}Name) {
        int count = ${table.mapperName?uncap_first}.check${entity}Name(${entity[3..6]?uncap_first}Name);
        if (count > 0){
            return CheckInformation.${entity[3..6]?upper_case}_NAME_NOT_UNIQUE;
        }
        return CheckInformation.${entity[3..6]?upper_case}_NAME_UNIQUE;
    }

}
</#if>
